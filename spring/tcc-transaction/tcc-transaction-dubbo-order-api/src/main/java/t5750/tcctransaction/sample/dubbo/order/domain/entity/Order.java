package t5750.tcctransaction.sample.dubbo.order.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 */
public class Order implements Serializable {
	private static final long serialVersionUID = -5908730245224893590L;
	private long id;
	private long payerUserId; // 付款人ID
	private long payeeUserId; // 收款人ID
	private BigDecimal redPacketPayAmount; // 红包支付金额
	private BigDecimal capitalPayAmount; // 资金账号支付金额
	/**
	 * 下完订单后，订单状态为DRAFT，
	 * 在TCC事务中TRY阶段，订单支付服务将订单状态变成PAYING，同时远程调用红包帐户服务和资金帐户服务,将付款方的余额减掉（预留业务资源);
	 * 如果在TRY阶段
	 * ，任何一个服务失败，tcc-transaction将自动调用这些服务对应的cancel方法，订单支付服务将订单状态变成PAY_FAILED
	 * ,同时远程调用红包帐户服务和资金帐户服务,将付款方余额减掉的部分增加回去；
	 * 如果TRY阶段正常完成，则进入CONFIRM阶段，在CONFIRM阶段（
	 * tcc-transaction自动调用）,订单支付服务将订单状态变成CONFIRMED
	 * ,同时远程调用红包帐户服务和资金帐户服务对应的CONFIRM方法，将收款方的余额增加。
	 * 特别说明下，由于是示例，在CONFIRM和CANCEL方法中没有实现幂等性
	 * ，如果在真实项目中使用，需要保证CONFIRM和CANCEL方法的幂等性。
	 */
	private String status = "DRAFT";
	private String merchantOrderNo; // 商户订单号
	private List<OrderLine> orderLines = new ArrayList<OrderLine>();

	public Order() {
	}

	public Order(long payerUserId, long payeeUserId) {
		this.payerUserId = payerUserId;
		this.payeeUserId = payeeUserId;
		this.merchantOrderNo = UUID.randomUUID().toString();
	}

	public long getPayerUserId() {
		return payerUserId;
	}

	public long getPayeeUserId() {
		return payeeUserId;
	}

	/**
	 * 统计订单总价
	 * 
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (OrderLine orderLine : orderLines) {
			totalAmount = totalAmount.add(orderLine.getTotalAmount());
		}
		return totalAmount;
	}

	public void addOrderLine(OrderLine orderLine) {
		this.orderLines.add(orderLine);
	}

	public List<OrderLine> getOrderLines() {
		return Collections.unmodifiableList(this.orderLines);
	}

	/**
	 * 设置订单的支付金额信息，设置订单状态为 PAYING
	 * 
	 * @param redPacketPayAmount
	 * @param capitalPayAmount
	 */
	public void pay(BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {
		this.redPacketPayAmount = redPacketPayAmount;
		this.capitalPayAmount = capitalPayAmount;
		this.status = "PAYING";
	}

	public BigDecimal getRedPacketPayAmount() {
		return redPacketPayAmount;
	}

	public BigDecimal getCapitalPayAmount() {
		return capitalPayAmount;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * 设置订单状态为CONFIRMED
	 */
	public void confirm() {
		this.status = "CONFIRMED";
	}

	public void cancelPayment() {
		this.status = "PAY_FAILED";
	}
}
