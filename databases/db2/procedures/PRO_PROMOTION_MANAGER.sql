CREATE OR REPLACE PROCEDURE "DB2INST1"."PRO_PROMOTION_MANAGER"
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_store_id BIGINT,
IN in_user_id BIGINT,
INOUT inout_issuccess INT, -- -2：表示首次进入下单支付页面；-1：表示在下单支付页面未选择优惠券
OUT out_first_ispile INT, --首个促销的堆叠互斥性
OUT out_max_sort BIGINT, --首个促销的优先级
OUT out_promotion_ids VARCHAR(200), -- 所有促销的ids，逗号分隔
OUT out_promotion_condition_ids VARCHAR(200), -- 满减对应的满减条件ids
OUT out_use_promotion INT) -- 0：没成功使用优惠券；1：成功使用优惠券
	DYNAMIC RESULT SETS 1
LANGUAGE SQL
--READS SQL DATA
MODIFIES SQL DATA
SPECIFIC PRO_PROMOTION_MANAGER
-- #######################################################################
-- # 促销管理
-- #######################################################################
BEGIN ATOMIC
	DECLARE v_discount DECIMAL(12,2);
	DECLARE v_totalprice DECIMAL(12,2);
	DECLARE v_real_price DECIMAL(12,2); -- 实时商品总金额
	DECLARE v_at_end INTEGER DEFAULT 0;
	DECLARE v_at_end_ofi INTEGER DEFAULT 0;
	DECLARE v_at_end_pl INTEGER DEFAULT 0;
	DECLARE v_promotion_id BIGINT;
	DECLARE v_effective_amount DECIMAL(12,2);
	DECLARE v_pr_type VARCHAR(10);
	DECLARE v_ispile INT;
	DECLARE v_first_ispile INT DEFAULT -1; --首个促销的堆叠互斥性
	DECLARE v_sort INT;
	DECLARE v_store_general INTEGER;
	DECLARE ofi_sum_count INT; -- ORDERFORMITEM表中，count的总和，即购物车总件数
	DECLARE v_use_promotion INT DEFAULT 0; -- 1表示该促销可用于该购物车，0不可用
	DECLARE c_promotions_list CURSOR FOR
	select L_PRO.ID,L_PRO.EFFECTIVE_AMOUNT,L_PRO.PR_TYPE,L_PRO.ISPILE,L_PRO.SORT,L_PRO.STORE_GENERAL,L_PRO.DISCOUNT from 
		(
			select ID,EFFECTIVE_AMOUNT,PR_TYPE,ISPILE,SORT,STORE_GENERAL,DISCOUNT from LAKECLOUD_PROMOTION where deletestatus=0 and effective_amount<=v_totalprice and FIELD1=in_store_id and PR_TYPE='manzeng' and STATUS=1 and current timestamp>STARTTIME and current timestamp<ENDTIME
			union all
			select L_P.ID,0 as EFFECTIVE_AMOUNT,L_P.PR_TYPE,L_P.ISPILE,L_P.SORT,L_P.STORE_GENERAL,L_P.DISCOUNT from LAKECLOUD_PROMOTION L_P where L_P.deletestatus=0 and L_P.FIELD1=in_store_id and L_P.PR_TYPE='manjian' and STATUS=1 and current timestamp>STARTTIME and current timestamp<ENDTIME
			--select L_P.ID,L_PC.CONDITION as EFFECTIVE_AMOUNT,L_P.PR_TYPE,L_P.ISPILE,L_P.SORT,L_P.STORE_GENERAL,L_P.DISCOUNT from LAKECLOUD_PROMOTION L_P join LAKECLOUD_PROMOTIONCODECONDITIONS L_PC on L_P.ID=L_PC.PROMOTION_ID where L_P.deletestatus=0 and L_PC.CONDITION<=v_totalprice and L_P.FIELD1=in_store_id and L_P.PR_TYPE='manjian' and STATUS=1 and current timestamp>STARTTIME and current timestamp<ENDTIME
			union all
			select ID,EFFECTIVE_AMOUNT,PR_TYPE,ISPILE,SORT,STORE_GENERAL,DISCOUNT from LAKECLOUD_PROMOTION where deletestatus=0 and ID=in_promotion_id and (PR_TYPE='code' AND STATUS=3 OR PR_TYPE='store' AND STATUS=1) and current timestamp>STARTTIME and current timestamp<ENDTIME
			union all
			select ID,EFFECTIVE_AMOUNT,PR_TYPE,ISPILE,SORT,STORE_GENERAL,DISCOUNT from LAKECLOUD_PROMOTION where deletestatus=0 and FIELD1=in_store_id and PR_TYPE='multiple' AND STATUS=1 and current timestamp>STARTTIME and current timestamp<ENDTIME
		) L_PRO order by L_PRO.SORT desc
	FOR READ ONLY;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION,SQLWARNING SET inout_issuccess = 0; -- 异常处理
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_at_end = 1;
	
	IF inout_issuccess < 0 THEN -- 表示需执行新增删除ofi
		--start 删除购物车对应的订单赠品记录
		--DELETE FROM LAKECLOUD_ORDERFORMGIFT WHERE SC_ID = in_sc_id;
		UPDATE LAKECLOUD_ORDERFORMGIFT SET DELETESTATUS = 1 WHERE SC_ID = in_sc_id AND DELETESTATUS = 0;
		--end 删除购物车对应的订单赠品记录
		CALL PRO_DEL_SAVE_ORDERFORMITEM(in_sc_id,inout_issuccess);
	END IF;
	select TOTAL_PRICE into v_totalprice from LAKECLOUD_STORECART where ID=in_sc_id;
	SET v_real_price=v_totalprice;
	select sum(count) into ofi_sum_count from DB2INST1.LAKECLOUD_ORDERFORMITEM where deletestatus=0 and sc_id=in_sc_id;
	
	SET v_at_end = 0;
	SET inout_issuccess = 3;
	SET out_promotion_ids = '';
	SET out_promotion_condition_ids = '';
	IF in_promotion_id > 0 THEN
		SET out_use_promotion = 0;
	ELSE
		SET out_use_promotion = 1;-- 未选择优惠券，默认1
	END IF;
	
	--start 平台优惠券
	CALL PRO_PROMOTION_PLATFORM(in_sc_id,in_promotion_id,ofi_sum_count,v_use_promotion,v_real_price);
	IF v_use_promotion = 1 THEN
		IF out_promotion_ids <> '' THEN
			SET out_promotion_ids = out_promotion_ids || ',' || in_promotion_id;
		ELSE
			SET out_promotion_ids = in_promotion_id;
		END IF;
		SET out_use_promotion = 1;
	END IF;
	--end 平台优惠券
OPEN c_promotions_list;
	FETCH_LOOP: LOOP
	FETCH c_promotions_list INTO v_promotion_id,v_effective_amount,v_pr_type,v_ispile,v_sort,v_store_general,v_discount;
		IF v_at_end <> 0 THEN -- loop until last row of the cursor
			LEAVE FETCH_LOOP;
		END IF;
		IF v_first_ispile < 0 THEN
			SET v_first_ispile = v_ispile;
			SET out_first_ispile = v_ispile;
			SET out_max_sort = v_sort;
		END IF;
		
		SET inout_issuccess=2;
		SET v_use_promotion = 0;
		
		IF v_first_ispile = 0 THEN --首个促销为互斥，则循环只需执行第一个
			IF v_pr_type='manjian' THEN --满减
				CALL PRO_PROMOTION_MANJIAN(in_sc_id,v_promotion_id,v_store_general,ofi_sum_count,v_use_promotion,out_promotion_condition_ids,v_real_price);
			ELSEIF v_pr_type='manzeng' THEN --满赠
				CALL PRO_PROMOTION_MANZENG(in_sc_id,v_promotion_id,v_store_general,v_effective_amount,v_use_promotion,v_real_price);
			ELSEIF (v_pr_type='store' OR v_pr_type='code') THEN --优惠券
				CALL PRO_PROMOTION_CODE_STORE(in_sc_id,v_promotion_id,v_store_general,ofi_sum_count,v_effective_amount,v_discount,v_use_promotion,v_real_price);
				IF v_use_promotion = 1 THEN
					SET out_use_promotion = 1;
				END IF;
			ELSEIF v_pr_type='multiple' THEN --倍数满减
				CALL PRO_PROMOTION_MULTIPLE(in_sc_id,v_promotion_id,v_store_general,ofi_sum_count,v_effective_amount,v_discount,v_use_promotion,v_real_price);
			END IF;
			SET v_at_end = 1; 
		ELSEIF v_ispile = 1 THEN --首个促销为堆叠，则循环只需执行所有的堆叠促销
			IF v_pr_type='manjian' THEN --满减
				CALL PRO_PROMOTION_MANJIAN(in_sc_id,v_promotion_id,v_store_general,ofi_sum_count,v_use_promotion,out_promotion_condition_ids,v_real_price);
			ELSEIF v_pr_type='manzeng' THEN --满赠
				CALL PRO_PROMOTION_MANZENG(in_sc_id,v_promotion_id,v_store_general,v_effective_amount,v_use_promotion,v_real_price);
			ELSEIF (v_pr_type='store' OR v_pr_type='code') THEN --优惠券
				CALL PRO_PROMOTION_CODE_STORE(in_sc_id,v_promotion_id,v_store_general,ofi_sum_count,v_effective_amount,v_discount,v_use_promotion,v_real_price);
				IF v_use_promotion = 1 THEN
					SET out_use_promotion = 1;
				END IF;
			ELSEIF v_pr_type='multiple' THEN --倍数满减
				CALL PRO_PROMOTION_MULTIPLE(in_sc_id,v_promotion_id,v_store_general,ofi_sum_count,v_effective_amount,v_discount,v_use_promotion,v_real_price);
			END IF;
			SET v_at_end = 0; 
		END IF;
		
		IF v_use_promotion = 1 THEN
			IF out_promotion_ids <> '' THEN
				SET out_promotion_ids = out_promotion_ids || ',' || v_promotion_id;
			ELSE
				SET out_promotion_ids = v_promotion_id;
			END IF;
		END IF;
		
		IF v_real_price < 0 THEN -- loop until last row of the cursor
			SET inout_issuccess = -3;
			LEAVE FETCH_LOOP;
		END IF;
	END LOOP FETCH_LOOP;
CLOSE c_promotions_list;

--IF out_promotion_ids <> '' THEN
--	UPDATE LAKECLOUD_ORDERFORMITEM SET UPDATETIME = CURRENT TIMESTAMP , PROMOTION_IDS = out_promotion_ids WHERE DELETESTATUS=0 AND ID = (SELECT MIN(ID) ID FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_store_id);
--END IF;

END