package edu.njit.cs673.portfoliooptimizer.vo;

import java.math.BigDecimal;

public class SellVO extends GenericVO {

	private String stockSymbol;
	private BigDecimal unitSharePrice;
	private double numberOfShares;
	private int sharesToSell;
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public BigDecimal getUnitSharePrice() {
		return unitSharePrice;
	}

	public void setUnitSharePrice(BigDecimal unitSharePrice) {
		this.unitSharePrice = unitSharePrice;
	}

	public double getNumberOfShares() {
		return numberOfShares;
	}

	public void setNumberOfShares(double numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	
	public int getSharesToSell() {
		return sharesToSell;
	}

	public void setSharesToSell(int sharesToSell) {
		this.sharesToSell = sharesToSell;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Stock Symbol: "+stockSymbol)
		.append("Unit Share Price: "+unitSharePrice)
		.append("Number of shares: "+numberOfShares);
		
		return buffer.toString();
	}
}

