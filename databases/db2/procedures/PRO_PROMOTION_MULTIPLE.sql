CREATE OR REPLACE PROCEDURE DB2INST1.PRO_PROMOTION_MULTIPLE
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_store_general INTEGER, -- 是否全店通用
IN in_sum_count INT, -- 购物车商品总数
IN in_effective_amount DECIMAL(12,2),
IN in_discount DECIMAL(12,2),
INOUT inout_use_promotion INT, -- 1表示该促销可用于该购物车，0不可用
INOUT inout_real_price DECIMAL(12,2)) -- 实时总金额
LANGUAGE SQL
SPECIFIC PRO_PROMOTION_MULTIPLE
-- #######################################################################
-- # 只处理一个倍数满减，先区分全店通用
-- #######################################################################
BEGIN ATOMIC
	DECLARE sum_discount DECIMAL(12,2); -- 记录满减的总折扣金额
	DECLARE v_rounding BIGINT; -- 取整，不进行四舍五入
	DECLARE sum_price DECIMAL(12,2); -- 记录满足满减商品的总价格
	DECLARE avg_discount DECIMAL(12,2); -- 用于满减分摊折扣金额
	DECLARE goods_count INTEGER DEFAULT 0; -- 记录满减满足商品总数量，针对商品
	DECLARE v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT INT; -- 用于获取到最优满减条件后，跳出循环
	
SET v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT=0;

IF in_store_general = 1 THEN -- # 全店通用
	IF (in_sum_count > 0 AND inout_real_price >= in_effective_amount) THEN
		SELECT CAST(inout_real_price / in_effective_amount AS INT) INTO v_rounding FROM DB2INST1.LAKECLOUD_PROMOTION WHERE ID=in_promotion_id;
		SET sum_discount = v_rounding * in_discount;
		SET goods_count = in_sum_count;
	END IF;
ELSE -- # 指定商品
	--start 获取ORDERFORMITEM关联指定商品，总金额和总数量
	SELECT SUM(ITEM_CURRENT_PRICE),SUM(COUNT),CAST(SUM(ITEM_CURRENT_PRICE)/in_effective_amount AS INT) INTO sum_price,goods_count,v_rounding FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_sc_id AND GOODS_ID IN (SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PROMOTION_ID=in_promotion_id);
	--end 获取ORDERFORMITEM关联指定商品，总金额和总数量
	IF (inout_real_price >= sum_price AND sum_price >= in_effective_amount AND goods_count > 0) THEN
		SET sum_discount = v_rounding * in_discount;
	END IF;
END IF;

IF sum_discount > 0 THEN
	SET avg_discount = sum_discount / goods_count;
	CALL PRO_PROMOTION_SHARE_DISCOUNT(in_sc_id,in_promotion_id,in_store_general,avg_discount,sum_discount,in_sum_count,0,v_issuccess_PRO_PROMOTION_SHARE_DISCOUNT);
	SET inout_real_price = inout_real_price - sum_discount;
	SET inout_use_promotion = 1;
END IF;

END