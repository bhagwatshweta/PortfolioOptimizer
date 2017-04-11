package edu.njit.c673.portfoliooptimizer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PORTFOLIO", schema = "tmp33")
public class Portfolio implements Serializable {

	private static final long serialVersionUID = -5287002849051018683L;

	@Id
	@Column(name = "PORTFOLIO_ID")
	@GeneratedValue(generator = "PORTFOLIO_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PORTFOLIO_SEQ", sequenceName = "PORTFOLIO_SEQ", schema = "tmp33", allocationSize = 1)
	private int portfolioId;

	@JoinColumn(name = "INVESTOR_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_INVST_PFL") )
	@ManyToOne(fetch = FetchType.LAZY)
	private Investor investor;

	@Column(name = "PORTFOLIO_NAME", nullable = false)
	private String portfolioName;

	@Column(name = "CASH_BALANCE", precision = 10, nullable = false)
	private BigDecimal cashBalance;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "portfolio")
	private List<Transaction> transactions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "portfolio")
	private List<PortfolioStock> stocks;

	@Column(name = "CREATION_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationDate;

	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<PortfolioStock> getStocks() {
		return stocks;
	}

	public void setStocks(List<PortfolioStock> stocks) {
		this.stocks = stocks;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
