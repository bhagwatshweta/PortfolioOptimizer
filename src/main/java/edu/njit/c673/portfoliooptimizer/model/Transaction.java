package edu.njit.c673.portfoliooptimizer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TRANSACTION", schema = "tmp33")
public class Transaction implements Serializable {

	private static final long serialVersionUID = -7647742592594081433L;

	@Id
	@Column(name = "TRANSACTION_ID")
	@GeneratedValue(generator = "TRANSACTION_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(schema = "tmp33", name = "TRANSACTION_SEQ", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
	private int transactionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PORTFOLIO_ID", foreignKey = @ForeignKey(name = "FK_PFL_TXN") )
	private Portfolio portfolio;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSACTION_TYPE_ID", foreignKey = @ForeignKey(name = "FK_TXN_TXN_TYP") )
	private TransactionType transactionType;

	@Column(name = "TRANSACTION_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@Column(name = "TRANSACTION_AMOUNT", nullable = false)
	private BigDecimal transactionAmount;

	@Column(name = "SHARE_QUANTITY", precision = 10)
	private float shareQuantity;

	@Column(name = "UNIT_SHARE_PRICE", precision = 10,scale = 2)
	private BigDecimal unitSharePrice;

	@Column(name = "STOCK_SYMBOL")
	private String stockSymbol;
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public float getShareQuantity() {
		return shareQuantity;
	}

	public void setShareQuantity(float shareQuantity) {
		this.shareQuantity = shareQuantity;
	}

	public BigDecimal getUnitSharePrice() {
		return unitSharePrice;
	}

	public void setUnitSharePrice(BigDecimal unitSharePrice) {
		this.unitSharePrice = unitSharePrice;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

}
