CREATE OR REPLACE PROCEDURE DB2INST1.PRO_DEL_SAVE_ORDERFORMITEM
(IN in_sc_id BIGINT,
INOUT inout_issuccess INT)
LANGUAGE SQL
SPECIFIC PRO_DEL_SAVE_ORDERFORMITEM
-- #######################################################################
-- # 删除后新增ORDERFORMITEM
-- #######################################################################
BEGIN ATOMIC
	DECLARE v_at_end INTEGER DEFAULT 0;
	DECLARE ofi_count INT;
	DECLARE ofi_goods_current_price DECIMAL(12,2);
	DECLARE ofi_item_current_price DECIMAL(12,2);
	DECLARE ofi_goods_id BIGINT;
	DECLARE ofi_gc_id BIGINT;
	DECLARE c_gc_list CURSOR FOR SELECT ID,PRICE,COUNT,GOODS_ID FROM DB2INST1.LAKECLOUD_GOODSCART WHERE SC_ID=in_sc_id AND DELETESTATUS=0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION,SQLWARNING set inout_issuccess = 0; -- 异常处理
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_at_end = 1;
	
--start 清空购物车对应的订单项表
UPDATE LAKECLOUD_ORDERFORMITEM SET DELETESTATUS=1 , UPDATETIME=CURRENT TIMESTAMP WHERE DELETESTATUS=0 AND ID IN (SELECT ID FROM LAKECLOUD_ORDERFORMITEM WHERE SC_ID=in_sc_id AND DELETESTATUS=0);
--end 清空购物车对应的订单项表
	
SET v_at_end = 0;

--start 遍历购物车
OPEN c_gc_list;
FETCH_LOOP: LOOP
FETCH c_gc_list INTO ofi_gc_id,ofi_goods_current_price,ofi_count,ofi_goods_id;
	IF v_at_end <> 0 THEN -- loop until last row of the cursor
		SET inout_issuccess = 1;
		LEAVE FETCH_LOOP;
	END IF;
	SET ofi_item_current_price = ofi_goods_current_price * ofi_count;
	INSERT INTO LAKECLOUD_ORDERFORMITEM(ADDTIME,DELETESTATUS,COUNT,GOODS_CURRENT_PRICE,ITEM_CURRENT_PRICE,GOODS_ID,GC_ID,SC_ID) VALUES(CURRENT TIMESTAMP,0,ofi_count,ofi_goods_current_price,ofi_item_current_price,ofi_goods_id,ofi_gc_id,in_sc_id);
END LOOP FETCH_LOOP;
CLOSE c_gc_list;
--end 遍历购物车

END