CREATE OR REPLACE PROCEDURE PRO_PROMOTION_CODE_STORE
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_store_general INTEGER, -- 是否全店通用
IN in_sum_count INT, -- 购物车商品总数
IN in_effective_amount DECIMAL(12,2), -- 优惠券或会员领券，生效金额
IN in_discount DECIMAL(12,2), -- 优惠券或会员领券，折扣金额
INOUT inout_use_promotion INT, -- 1表示该促销可用于该购物车，0不可用
INOUT inout_real_price DECIMAL(12,2)) -- 实时总金额
	DYNAMIC RESULT SETS 1
LANGUAGE SQL
--READS SQL DATA
SPECIFIC PRO_PROMOTION_CODE_STORE
-- #######################################################################
-- # 只处理一个优惠券（status=3）或会员领券（status=1），先判断优惠金额是否大于订单实际金额，再区分全店通用，后对较生效金额
-- #######################################################################
P1: BEGIN ATOMIC
	DECLARE v_real_price_temp DECIMAL(12,2); -- 实时总金额缓存，用于判断优惠金额大于总金额的情况
	DECLARE sum_price DECIMAL(12,2); -- 记录满足商品的总价格
	DECLARE store_avg_discount DECIMAL(12,2); -- 用于分摊折扣金额，全店通用
	DECLARE goods_avg_discount DECIMAL(12,2); -- 用于分摊折扣金额，针对商品
	DECLARE goods_count INTEGER DEFAULT 0; -- 记录满足商品总数量，针对商品
	DECLARE v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT INTEGER DEFAULT 0; -- 用于获取到最优条件后，跳出循环

SET v_real_price_temp = inout_real_price - in_discount;
IF v_real_price_temp < 0 THEN
	SET inout_use_promotion = 0;
ELSE
	IF in_store_general = 1 THEN -- # 全店通用
		IF inout_real_price >= in_effective_amount and in_sum_count > 0 THEN
			SET store_avg_discount = in_discount / in_sum_count;
			CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,in_store_general,store_avg_discount,in_discount,in_sum_count,-1,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
		END IF;
	ELSE -- # 指定商品
		--start 获取ORDERFORMITEM关联指定商品，总金额和总数量
		SELECT SUM(ITEM_CURRENT_PRICE),SUM(COUNT) INTO sum_price,goods_count FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_sc_id AND GOODS_ID IN (SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PROMOTION_ID=in_promotion_id);
		--end 获取ORDERFORMITEM关联指定商品，总金额和总数量
		IF (inout_real_price >= sum_price AND sum_price >= in_effective_amount AND goods_count > 0) THEN
			SET goods_avg_discount = in_discount / goods_count;
			CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,in_store_general,goods_avg_discount,in_discount,goods_count,-1,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
		END IF;
	END IF;
	
	IF v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT = 1 THEN
		SET inout_use_promotion = 1;
		SET inout_real_price = inout_real_price - in_discount;
	END IF;
END IF;
END P1