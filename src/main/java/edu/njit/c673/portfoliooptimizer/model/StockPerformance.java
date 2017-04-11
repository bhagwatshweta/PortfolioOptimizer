package edu.njit.c673.portfoliooptimizer.model;

import java.math.BigDecimal;

public class StockPerformance {

	private PortfolioStock stock;
	private String stockSymbol;
	private BigDecimal lastPrice;
	private BigDecimal shares;
	private BigDecimal change;
	private BigDecimal costBasis;
	private BigDecimal marketValue;
	private BigDecimal gain;
	private BigDecimal gainPercentage;
	private BigDecimal daysGain;
	private BigDecimal overallReturn;

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public BigDecimal getCostBasis() {
		return costBasis;
	}

	public void setCostBasis(BigDecimal costBasis) {
		this.costBasis = costBasis;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	public BigDecimal getGain() {
		return gain;
	}

	public void setGain(BigDecimal gain) {
		this.gain = gain;
	}

	public BigDecimal getGainPercentage() {
		return gainPercentage;
	}

	public void setGainPercentage(BigDecimal gainPercentage) {
		this.gainPercentage = gainPercentage;
	}

	public BigDecimal getDaysGain() {
		return daysGain;
	}

	public void setDaysGain(BigDecimal daysGain) {
		this.daysGain = daysGain;
	}

	public BigDecimal getOverallReturn() {
		return overallReturn;
	}

	public void setOverallReturn(BigDecimal overallReturn) {
		this.overallReturn = overallReturn;
	}

	public PortfolioStock getStock() {
		return stock;
	}

	public void setStock(PortfolioStock stock) {
		this.stock = stock;
	}

	public BigDecimal getShares() {
		return shares;
	}

	public void setShares(BigDecimal shares) {
		this.shares = shares;
	}

}
