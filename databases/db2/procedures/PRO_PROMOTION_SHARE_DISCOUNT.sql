CREATE OR REPLACE PROCEDURE DB2INST1.PRO_PROMOTION_SHARE_DISCOUNT
(IN in_sc_id BIGINT,
IN in_promotion_id BIGINT,
IN in_store_general INTEGER, -- 是否全店通用
IN in_avg_discount DECIMAL(12,2), -- 折扣平均值
IN in_discount DECIMAL(12,2), -- 总折扣
IN in_sum_count INT, -- 购物车商品总数
IN in_condition_id BIGINT, -- >0满减条件表id，0表示倍数满减，<0表示优惠券或领券
INOUT inout_issuccess INT) -- 0：异常；1：全部更新成功
	DYNAMIC RESULT SETS 1
LANGUAGE SQL
MODIFIES SQL DATA
SPECIFIC PRO_PROMOTION_SHARE_DISCOUNT
-- #######################################################################
-- # 分摊折扣金额，并遍历更新ORDERFORMITEM
-- #######################################################################
P1: BEGIN ATOMIC
DECLARE v_last_discount DECIMAL(12,2); -- 用于满减最后剩余金额
DECLARE v_current_count INTEGER DEFAULT 0; -- 用于满减，当前商品数量累计
DECLARE ofi_item_current_price DECIMAL(12,2);
DECLARE ofi_manjian_price DECIMAL(12,2);
DECLARE ofi_code_store_price DECIMAL(12,2);
DECLARE ofi_promotion_json VARCHAR(200);
DECLARE EXIT HANDLER FOR SQLEXCEPTION,SQLWARNING set inout_issuccess = 0; -- 异常处理

SET v_last_discount = in_discount;

IF in_store_general = 1 THEN -- # 全店通用
	--start 遍历ORDERFORMITEM关联商品
	FOR loop_ofi AS SELECT COUNT,GOODS_CURRENT_PRICE,ITEM_CURRENT_PRICE,PROMOTION_JSON,GOODS_ID,MANJIAN_PRICE,CODE_STORE_PRICE FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE SC_ID=in_sc_id AND DELETESTATUS=0
	DO
		SET v_current_count = v_current_count + loop_ofi.COUNT;
		IF v_current_count = in_sum_count THEN --当前商品总数==购物车商品总数，即最后一条记录
			SET ofi_item_current_price = loop_ofi.ITEM_CURRENT_PRICE - v_last_discount;
			--start 记录各个订单项的manjian_price，code_store_price
			IF in_condition_id >= 0 THEN -- 满减
				SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE;
				IF loop_ofi.MANJIAN_PRICE IS NOT NULL THEN
					SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE + v_last_discount;
				ELSE
					SET ofi_manjian_price = v_last_discount;
				END IF;
			ELSE -- 优惠券
				SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE;
				IF loop_ofi.CODE_STORE_PRICE IS NOT NULL THEN
					SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE + v_last_discount;
				ELSE
					SET ofi_code_store_price = v_last_discount;
				END IF;
			END IF;
			--end 记录各个订单项的manjian_price，code_store_price
		ELSE
			SET ofi_item_current_price = loop_ofi.ITEM_CURRENT_PRICE - (loop_ofi.COUNT * in_avg_discount);
			SET v_last_discount = v_last_discount - (loop_ofi.COUNT * in_avg_discount);
			--start 记录各个订单项的manjian_price，code_store_price
			IF in_condition_id >= 0 THEN -- 满减
				SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE;
				IF loop_ofi.MANJIAN_PRICE IS NOT NULL THEN
					SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE + (loop_ofi.COUNT * in_avg_discount);
				ELSE
					SET ofi_manjian_price = (loop_ofi.COUNT * in_avg_discount);
				END IF;
			ELSE -- 优惠券
				SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE;
				IF loop_ofi.CODE_STORE_PRICE IS NOT NULL THEN
					SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE + (loop_ofi.COUNT * in_avg_discount);
				ELSE
					SET ofi_code_store_price = (loop_ofi.COUNT * in_avg_discount);
				END IF;
			END IF;
			--end 记录各个订单项的manjian_price，code_store_price
		END IF;
		IF loop_ofi.PROMOTION_JSON IS NOT NULL THEN
			SET ofi_promotion_json = loop_ofi.PROMOTION_JSON || ',' || in_promotion_id || ':' || in_condition_id;
		ELSE
			SET ofi_promotion_json = in_promotion_id || ':' || in_condition_id;
		END IF;
		--IF ofi_item_current_price > 0 THEN
			UPDATE LAKECLOUD_ORDERFORMITEM SET ITEM_CURRENT_PRICE = ofi_item_current_price , UPDATETIME = CURRENT TIMESTAMP , PROMOTION_JSON = ofi_promotion_json , MANJIAN_PRICE = ofi_manjian_price , CODE_STORE_PRICE = ofi_code_store_price WHERE DELETESTATUS=0 AND GOODS_ID = loop_ofi.GOODS_ID AND SC_ID = in_sc_id;
			IF v_current_count = in_sum_count THEN --当前商品总数==购物车商品总数，即最后一条记录
				SET inout_issuccess = 1;
			END IF;
		--END IF;
	END FOR;
	--end 遍历ORDERFORMITEM关联商品
ELSE -- # 指定商品
	--start 遍历ORDERFORMITEM关联商品
	FOR loop_ofi AS SELECT COUNT,GOODS_CURRENT_PRICE,ITEM_CURRENT_PRICE,PROMOTION_JSON,GOODS_ID,MANJIAN_PRICE,CODE_STORE_PRICE FROM DB2INST1.LAKECLOUD_ORDERFORMITEM WHERE SC_ID=in_sc_id AND DELETESTATUS=0 AND GOODS_ID IN (SELECT GOODS_ID FROM DB2INST1.LAKECLOUD_PROMOTIONLEVEL WHERE DELETESTATUS=0 AND PROMOTION_ID=in_promotion_id)
	DO
		SET v_current_count = v_current_count + loop_ofi.COUNT;
		IF v_current_count = in_sum_count THEN --当前商品总数==购物车商品总数，即最后一条记录
			SET ofi_item_current_price = loop_ofi.ITEM_CURRENT_PRICE - v_last_discount;
			--start 记录各个订单项的manjian_price，code_store_price
			IF in_condition_id >= 0 THEN -- 满减
				SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE;
				IF loop_ofi.MANJIAN_PRICE IS NOT NULL THEN
					SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE + v_last_discount;
				ELSE
					SET ofi_manjian_price = v_last_discount;
				END IF;
			ELSE -- 优惠券
				SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE;
				IF loop_ofi.CODE_STORE_PRICE IS NOT NULL THEN
					SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE + v_last_discount;
				ELSE
					SET ofi_code_store_price = v_last_discount;
				END IF;
			END IF;
			--end 记录各个订单项的manjian_price，code_store_price
		ELSE
			SET ofi_item_current_price = loop_ofi.ITEM_CURRENT_PRICE - (loop_ofi.COUNT * in_avg_discount);
			SET v_last_discount = v_last_discount - (loop_ofi.COUNT * in_avg_discount);
			--start 记录各个订单项的manjian_price，code_store_price
			IF in_condition_id >= 0 THEN -- 满减
				SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE;
				IF loop_ofi.MANJIAN_PRICE IS NOT NULL THEN
					SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE + (loop_ofi.COUNT * in_avg_discount);
				ELSE
					SET ofi_manjian_price = (loop_ofi.COUNT * in_avg_discount);
				END IF;
			ELSE -- 优惠券
				SET ofi_manjian_price = loop_ofi.MANJIAN_PRICE;
				IF loop_ofi.CODE_STORE_PRICE IS NOT NULL THEN
					SET ofi_code_store_price = loop_ofi.CODE_STORE_PRICE + (loop_ofi.COUNT * in_avg_discount);
				ELSE
					SET ofi_code_store_price = (loop_ofi.COUNT * in_avg_discount);
				END IF;
			END IF;
			--end 记录各个订单项的manjian_price，code_store_price
		END IF;
		IF loop_ofi.PROMOTION_JSON IS NOT NULL THEN
			SET ofi_promotion_json = loop_ofi.PROMOTION_JSON || ',' || in_promotion_id || ':' || in_condition_id;
		ELSE
			SET ofi_promotion_json = in_promotion_id || ':' || in_condition_id;
		END IF;
		--IF ofi_item_current_price > 0 THEN
			UPDATE LAKECLOUD_ORDERFORMITEM SET ITEM_CURRENT_PRICE = ofi_item_current_price , UPDATETIME = CURRENT TIMESTAMP , PROMOTION_JSON = ofi_promotion_json , MANJIAN_PRICE = ofi_manjian_price , CODE_STORE_PRICE = ofi_code_store_price WHERE DELETESTATUS=0 AND GOODS_ID = loop_ofi.GOODS_ID AND SC_ID = in_sc_id;
			IF v_current_count = in_sum_count THEN --当前商品总数==购物车商品总数，即最后一条记录
				SET inout_issuccess = 1;
			END IF;
		--END IF;
	END FOR;
	--end 遍历ORDERFORMITEM关联商品
END IF;

END P1