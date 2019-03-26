CREATE OR REPLACE PROCEDURE PRO_PROMOTION_PLATFORM
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_sum_count INT, -- 购物车商品总数
INOUT inout_use_promotion INT, -- 1表示该促销可用于该购物车，0不可用
INOUT inout_real_price DECIMAL(12,2)) -- 实时总金额
	DYNAMIC RESULT SETS 1
LANGUAGE SQL
--READS SQL DATA
SPECIFIC PRO_PROMOTION_PLATFORM
-- #######################################################################
-- # 只处理一个平台优惠券（status=1），先判断优惠金额是否大于订单实际金额，
-- # 再区分（1全平台通用，2针对商品，3针对店铺），后对较生效金额，对较店铺
-- #######################################################################
P1: BEGIN ATOMIC
	DECLARE v_real_price_temp DECIMAL(12,2); -- 实时总金额缓存，用于判断优惠金额大于总金额的情况
	DECLARE v_effective_amount DECIMAL(12,2); -- 优惠券，生效金额
	DECLARE v_discount DECIMAL(12,2); -- 优惠券，折扣金额
	DECLARE v_platform_general DECIMAL(12,2); -- 1全平台通用，2针对商品，3针对店铺
	DECLARE v_field1 VARCHAR(10);
	DECLARE v_rounding BIGINT; -- 取整，不进行四舍五入
	DECLARE v_pr_type VARCHAR(10);
	DECLARE v_store_general INTEGER;
	DECLARE sum_price DECIMAL(12,2); -- 记录满足商品的总价格
	DECLARE store_avg_discount DECIMAL(12,2); -- 用于分摊折扣金额，全店通用
	DECLARE goods_avg_discount DECIMAL(12,2); -- 用于分摊折扣金额，针对商品
	DECLARE goods_count INTEGER DEFAULT 0; -- 记录满足商品总数量，针对商品
	DECLARE v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT INTEGER DEFAULT 0; -- 用于获取到最优条件后，跳出循环

	--SELECT T1.ID,T1.EFFECTIVE_AMOUNT,T1.PR_TYPE,T1.ISPILE,T1.SORT,T1.STORE_GENERAL,T1.DISCOUNT,T1.PLATFORM_GENERAL,T1.FIELD1
	--SELECT T1.EFFECTIVE_AMOUNT,T1.PR_TYPE,T1.STORE_GENERAL,T1.DISCOUNT,T1.PLATFORM_GENERAL,T1.FIELD1 INTO v_effective_amount,v_pr_type,v_store_general,v_discount,v_platform_general,v_field1 FROM LAKECLOUD_PROMOTION T1 LEFT JOIN LAKECLOUD_PROMOTIONCODEUSERS T2 ON T2.PROMOTION_ID=T1.ID WHERE T1.DELETESTATUS=0 AND T1.PR_TYPE='admin' AND T1.STATUS=1 AND CURRENT TIMESTAMP>T1.STARTTIME AND CURRENT TIMESTAMP<T1.ENDTIME AND T2.USER_ID=in_user_id AND T1.PLATFORM_GENERAL>0 AND T1.ID=in_promotion_id;
	SELECT T1.EFFECTIVE_AMOUNT,T1.PR_TYPE,T1.STORE_GENERAL,T1.DISCOUNT,T1.PLATFORM_GENERAL,T1.FIELD1 INTO v_effective_amount,v_pr_type,v_store_general,v_discount,v_platform_general,v_field1 FROM LAKECLOUD_PROMOTION T1 WHERE T1.DELETESTATUS=0 AND T1.PR_TYPE='admin' AND T1.STATUS=1 AND CURRENT TIMESTAMP>T1.STARTTIME AND CURRENT TIMESTAMP<T1.ENDTIME AND T1.PLATFORM_GENERAL>0 AND T1.ID=in_promotion_id;

SET v_real_price_temp = inout_real_price - v_discount;
IF v_real_price_temp < 0 THEN
	SET inout_use_promotion = 0;
ELSE
	IF v_platform_general = 1 THEN
		IF inout_real_price >= v_effective_amount AND in_sum_count > 0 THEN
			SET store_avg_discount = v_discount / in_sum_count;
			SET v_store_general = 1;
			CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,v_store_general,store_avg_discount,v_discount,in_sum_count,-1,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
		END IF;
	ELSEIF v_platform_general = 2 THEN
		--start 获取ORDERFORMITEM关联指定商品，总金额和总数量
		SELECT CASE WHEN SUM(ITEM_CURRENT_PRICE) IS NULL THEN 0 ELSE SUM(ITEM_CURRENT_PRICE) END,CASE WHEN SUM(COUNT) IS NULL THEN 0 ELSE SUM(COUNT) END INTO sum_price,goods_count FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_sc_id AND GOODS_ID IN(SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PROMOTION_ID=in_promotion_id);
		--end 获取ORDERFORMITEM关联指定商品，总金额和总数量
		IF (inout_real_price >= sum_price AND sum_price >= v_effective_amount AND goods_count > 0) THEN
			SET goods_avg_discount = v_discount / goods_count;
			CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,v_store_general,goods_avg_discount,v_discount,goods_count,-1,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
		END IF;
	ELSEIF v_platform_general = 3 THEN
		IF v_store_general = 1 THEN -- # 全店通用
			IF inout_real_price >= v_effective_amount AND in_sum_count > 0 THEN
				SET store_avg_discount = v_discount / in_sum_count;
				CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,v_store_general,store_avg_discount,v_discount,in_sum_count,-1,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
			END IF;
		ELSE -- # 指定商品
			--start 获取ORDERFORMITEM关联指定商品，总金额和总数量
			SELECT CASE WHEN SUM(ITEM_CURRENT_PRICE) IS NULL THEN 0 ELSE SUM(ITEM_CURRENT_PRICE) END,CASE WHEN SUM(COUNT) IS NULL THEN 0 ELSE SUM(COUNT) END INTO sum_price,goods_count FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_sc_id AND GOODS_ID IN(SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PROMOTION_ID=in_promotion_id);
			--end 获取ORDERFORMITEM关联指定商品，总金额和总数量
			IF (inout_real_price >= sum_price AND sum_price >= v_effective_amount AND goods_count > 0) THEN
				SET goods_avg_discount = v_discount / goods_count;
				CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,v_store_general,goods_avg_discount,v_discount,goods_count,-1,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
			END IF;
		END IF; 
	END IF;
	
	IF v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT = 1 THEN
		SET inout_use_promotion = 1;
		SET inout_real_price = inout_real_price - v_discount;
	END IF;
END IF;
END P1