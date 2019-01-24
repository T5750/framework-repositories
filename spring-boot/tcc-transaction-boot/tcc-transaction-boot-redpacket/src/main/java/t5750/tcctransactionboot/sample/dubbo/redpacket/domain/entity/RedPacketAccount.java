package t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity;

import java.math.BigDecimal;

import t5750.tcctransactionboot.sample.exception.InsufficientBalanceException;

/**
 */
public class RedPacketAccount {
	private long id;
	private long userId;
	private BigDecimal balanceAmount;

	public long getUserId() {
		return userId;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void transferFrom(BigDecimal amount) {
		this.balanceAmount = this.balanceAmount.subtract(amount);
		if (BigDecimal.ZERO.compareTo(this.balanceAmount) > 0) {
			throw new InsufficientBalanceException();
		}
	}

	public void transferTo(BigDecimal amount) {
		this.balanceAmount = this.balanceAmount.add(amount);
	}

	public void cancelTransfer(BigDecimal amount) {
		transferTo(amount);
	}
}
