package edu.njit.c673.portfoliooptimizer.model;

import java.io.Serializable;
import java.math.BigDecimal;

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

@Entity
@Table(name = "PORTFOLIO_STOCK", schema = "tmp33")
public class PortfolioStock implements Serializable {

	private static final long serialVersionUID = -6277389320797095383L;

	@Id
	@Column(name = "PFL_STOCK_ID")
	@GeneratedValue(generator = "PFL_STOCK_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(schema = "tmp33", name = "PFL_STOCK_SEQ", sequenceName = "PFL_STOCK_SEQ", allocationSize = 1)
	private int portfolioStockId;

	@JoinColumn(name = "PORTFOLIO_ID", foreignKey = @ForeignKey(name = "FK_PFL_STK") )
	@ManyToOne(fetch = FetchType.LAZY)
	private Portfolio portfolio;

	@Column(name = "STOCK_SYMBOL", nullable = false)
	private String stockSymbol;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STOCK_EXCHANGE_ID", foreignKey = @ForeignKey(name = "FK_PFL_STK_EX_TYP") )
	private StockExchangeType stockExchangeType;

	@Column(name = "SHARE_QUANTITY", precision = 10)
	private BigDecimal shareQuantity;

	@Column(name = "PURCHASE_PRICE", precision = 10,scale = 2)
	private BigDecimal purchasePrice;

	public int getPortfolioStockId() {
		return portfolioStockId;
	}

	public void setPortfolioStockId(int portfolioStockId) {
		this.portfolioStockId = portfolioStockId;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockExchangeType getStockExchangeType() {
		return stockExchangeType;
	}

	public void setStockExchangeType(StockExchangeType stockExchangeType) {
		this.stockExchangeType = stockExchangeType;
	}

	public BigDecimal getShareQuantity() {
		return shareQuantity;
	}

	public void setShareQuantity(BigDecimal shareQuantity) {
		this.shareQuantity = shareQuantity;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

}
