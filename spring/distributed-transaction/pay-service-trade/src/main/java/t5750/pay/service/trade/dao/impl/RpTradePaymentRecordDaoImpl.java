package t5750.pay.service.trade.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.common.core.exception.BizException;
import t5750.pay.common.core.utils.DateUtils;
import t5750.pay.service.trade.dao.RpTradePaymentRecordDao;
import t5750.pay.service.trade.entity.RpTradePaymentRecord;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Repository("rpTradePaymentRecordDao")
public class RpTradePaymentRecordDaoImpl extends
		BaseDaoImpl<RpTradePaymentRecord> implements RpTradePaymentRecordDao {
	/** 银行订单号 **/
	private static final String BANK_ORDER_NO_PREFIX = "6666";
	/** 支付流水号前缀 **/
	private static final String TRX_NO_PREFIX = "7777";

	/**
	 * 获取支付流水号
	 **/
	@Override
	public String buildTrxNo() {
		// 获取编号序列
		String trxNoSeq = null;
		String trxNo = null;
		try {
			// 获取用户编号序列
			trxNoSeq = super.getSqlSession()
					.selectOne(getStatement("buildTrxNoSeq"));
			// 20位的支付流水号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
			String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
			trxNo = TRX_NO_PREFIX + dateString + trxNoSeq
					.substring(trxNoSeq.length() - 8, trxNoSeq.length());
		} catch (Exception e) {
			LOG.error("生成用户编号异常：", e);
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		if (StringUtils.isEmpty(trxNo)) {
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		return trxNo;
	}

	/**
	 * 获取银行订单号
	 **/
	@Override
	public String buildBankOrderNo() {
		// 获取编号序列
		String bankOrderNoSeq = null;
		String bankOrderNo = null;
		try {
			bankOrderNoSeq = super.getSqlSession()
					.selectOne(getStatement("buildBankOrderNoSeq"));
			// 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
			String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
			bankOrderNo = BANK_ORDER_NO_PREFIX + dateString
					+ bankOrderNoSeq.substring(bankOrderNoSeq.length() - 8,
							bankOrderNoSeq.length());
		} catch (Exception e) {
			LOG.error("生成用户编号异常：", e);
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		if (StringUtils.isEmpty(bankOrderNo)) {
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		return bankOrderNo;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	@Override
	public int insertSelective(RpTradePaymentRecord record) {
		return 0;
	}

	@Override
	public RpTradePaymentRecord selectByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(RpTradePaymentRecord record) {
		return 0;
	}

	/**
	 * 根据银行订单号获取支付信息
	 *
	 * @param bankOrderNo
	 * @return
	 */
	@Override
	public RpTradePaymentRecord getByBankOrderNo(String bankOrderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankOrderNo", bankOrderNo);
		return super.getBy(paramMap);
	}

	/**
	 * 根据商户编号及商户订单号获取支付结果
	 *
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @return
	 */
	@Override
	public RpTradePaymentRecord getByMerchantNoAndMerchantOrderNo(
			String merchantNo, String merchantOrderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		return super.getBy(paramMap);
	}

	/**
	 * 根据支付流水号查询支付记录
	 * 
	 * @param trxNo
	 * @return
	 */
	@Override
	public RpTradePaymentRecord getByTrxNo(String trxNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxNo", trxNo);
		return super.getBy(paramMap);
	}
}
