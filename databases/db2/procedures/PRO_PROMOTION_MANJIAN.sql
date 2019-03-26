CREATE OR REPLACE PROCEDURE DB2INST1.PRO_PROMOTION_MANJIAN
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_store_general INTEGER, -- 是否全店通用
IN in_sum_count INT, -- 购物车商品总数
INOUT inout_use_promotion INT, -- 1表示该促销可用于该购物车，0不可用
INOUT inout_use_promotion_condition VARCHAR(200), -- 满减对应的满减条件ids
INOUT inout_real_price DECIMAL(12,2)) -- 实时总金额
LANGUAGE SQL
SPECIFIC PRO_PROMOTION_MANJIAN
-- #######################################################################
-- # 只处理一个满减，先区分全店通用，后按满减条件遍历
-- #######################################################################
BEGIN ATOMIC
	DECLARE manjian_sum_discount DECIMAL(12,2); -- 记录满减的总折扣金额
	DECLARE manjian_sum_price DECIMAL(12,2); -- 记录满足满减商品的总价格
	DECLARE manjian_store_avg_discount DECIMAL(12,2); -- 用于满减分摊折扣金额，全店通用
	DECLARE manjian_goods_avg_discount DECIMAL(12,2); -- 用于满减分摊折扣金额，针对商品
	DECLARE manjian_goods_count INTEGER DEFAULT 0; -- 记录满减满足商品总数量，针对商品
	DECLARE v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT INT; -- 用于获取到最优满减条件后，跳出循环
	
SET v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT=0;

IF in_store_general=1 THEN -- # 全店通用
	--start 查询满减最优的一条记录
	FOR loop_max_condition AS select discount,id from DB2INST1.LAKECLOUD_PROMOTIONCODECONDITIONS where deletestatus=0 and condition<=inout_real_price and PROMOTION_ID=in_promotion_id order by discount desc fetch first 1 rows only
	DO
		IF in_sum_count>0 THEN
			SET manjian_store_avg_discount=loop_max_condition.discount/in_sum_count;
			CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,in_store_general,manjian_store_avg_discount,loop_max_condition.discount,in_sum_count,loop_max_condition.id,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
			SET manjian_sum_discount = loop_max_condition.discount;
			IF inout_use_promotion_condition <> '' THEN
				SET inout_use_promotion_condition = inout_use_promotion_condition || ',' || loop_max_condition.id;
			ELSE
				SET inout_use_promotion_condition = loop_max_condition.id; 
			END IF;
		END IF;
	END FOR;
	--end 查询满减最优的一条记录
ELSE -- # 指定商品
	--start 查询满减条件表多条记录
	FETCH_LOOP_CONDITIONS:
	FOR loop_conditions AS select discount,id,condition from DB2INST1.LAKECLOUD_PROMOTIONCODECONDITIONS where deletestatus=0 and condition<=inout_real_price and PROMOTION_ID=in_promotion_id order by discount desc
	DO
		--start 获取ORDERFORMITEM关联指定商品，总金额和总数量
		SELECT SUM(ITEM_CURRENT_PRICE),SUM(COUNT) INTO manjian_sum_price,manjian_goods_count FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_sc_id AND GOODS_ID IN (SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PROMOTION_ID=in_promotion_id);
		--end 获取ORDERFORMITEM关联指定商品，总金额和总数量
		IF (inout_real_price >= manjian_sum_price AND manjian_sum_price >= loop_conditions.condition AND manjian_goods_count > 0) THEN
			SET manjian_goods_avg_discount=loop_conditions.discount/manjian_goods_count;
			CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,in_store_general,manjian_goods_avg_discount,loop_conditions.discount,in_sum_count,loop_conditions.id,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
			SET manjian_sum_discount = loop_conditions.discount;
			IF inout_use_promotion_condition <> '' THEN
				SET inout_use_promotion_condition = inout_use_promotion_condition || ',' || loop_conditions.id;
			ELSE
				SET inout_use_promotion_condition = loop_conditions.id;
			END IF;
			IF v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT = 1 THEN
				LEAVE FETCH_LOOP_CONDITIONS;
			END IF;
		END IF;
	END FOR;
	--end 查询满减条件表多条记录
END IF;

IF manjian_sum_discount > 0 THEN
	SET inout_real_price = inout_real_price - manjian_sum_discount;
	SET inout_use_promotion = 1;
END IF;

END