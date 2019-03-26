CREATE OR REPLACE PROCEDURE DB2INST1.PRO_PROMOTION_MANZENG
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_store_general INTEGER, -- 是否全店通用
IN in_effective_amount DECIMAL(12,2), -- 优惠券或会员领券，生效金额
INOUT inout_use_promotion INT, -- 1表示该促销可用于该购物车，0不可用
INOUT inout_real_price DECIMAL(12,2)) -- 实时总金额
	DYNAMIC RESULT SETS 1
LANGUAGE SQL
MODIFIES SQL DATA
SPECIFIC PRO_PROMOTION_MANZENG
-- #######################################################################
-- # 只处理一个满赠，先区分全店通用，后遍历PROMOTIONLEVEL
-- #######################################################################
BEGIN ATOMIC
	DECLARE v_promotion_ids VARCHAR(200);
	DECLARE sum_price DECIMAL(12,2); -- 记录满足商品的总价格
	DECLARE goods_count INTEGER DEFAULT 0; -- 记录满足商品总数量，针对商品
	DECLARE v_issuccess_PRO_SAVE_UPDATE_ORDERFORMGIFT INT; -- 用于更新inout_real_price

IF in_store_general = 1 THEN -- # 全店通用
	IF inout_real_price >= in_effective_amount THEN
		CALL PRO_SAVE_UPDATE_ORDERFORMGIFT(in_sc_id,in_promotion_id,v_issuccess_PRO_SAVE_UPDATE_ORDERFORMGIFT);
	END IF;
ELSE -- # 指定商品
	--start 获取ORDERFORMITEM关联指定商品，总金额和总数量
	SELECT SUM(ITEM_CURRENT_PRICE),SUM(COUNT) INTO sum_price,goods_count FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE DELETESTATUS=0 AND SC_ID=in_sc_id AND GOODS_ID IN (SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PRODUCT IS NULL AND PROMOTION_ID=in_promotion_id);
	--end 获取ORDERFORMITEM关联指定商品，总金额和总数量
	IF (inout_real_price >= sum_price AND sum_price >= in_effective_amount AND goods_count > 0) THEN
		CALL PRO_SAVE_UPDATE_ORDERFORMGIFT(in_sc_id,in_promotion_id,v_issuccess_PRO_SAVE_UPDATE_ORDERFORMGIFT);
	END IF;
END IF;

IF v_issuccess_PRO_SAVE_UPDATE_ORDERFORMGIFT = 1 THEN
	SET inout_use_promotion = 1;
END IF;

END