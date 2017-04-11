package edu.njit.cs673.portfoliooptimizer.optimizer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class StockStatistics {

	private String stockSymbol;

	private List<BigDecimal> monthlyReturns;

	private BigDecimal averageMonthlyReturn;

	private List<BigDecimal> excessMonthlyReturns;

	private Map<String,Double> covarience;

	private int noOfShares;
	
	private int suggestedNoOfShares;
	
	private double expectedReturns;
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public List<BigDecimal> getMonthlyReturns() {
		return monthlyReturns;
	}

	public void setMonthlyReturns(List<BigDecimal> monthlyReturns) {
		this.monthlyReturns = monthlyReturns;
	}

	public BigDecimal getAverageMonthlyReturn() {
		return averageMonthlyReturn;
	}

	public void setAverageMonthlyReturn(BigDecimal averageMonthlyReturn) {
		this.averageMonthlyReturn = averageMonthlyReturn;
	}

	public List<BigDecimal> getExcessMonthlyReturns() {
		return excessMonthlyReturns;
	}

	public void setExcessMonthlyReturns(List<BigDecimal> excessMonthlyReturns) {
		this.excessMonthlyReturns = excessMonthlyReturns;
	}

	public Map<String, Double> getCovarience() {
		return covarience;
	}

	public void setCovarience(Map<String, Double> covarience) {
		this.covarience = covarience;
	}
	
	public int getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(int noOfShares) {
		this.noOfShares = noOfShares;
	}

	public double getExpectedReturns() {
		return expectedReturns;
	}

	public void setExpectedReturns(double expectedReturns) {
		this.expectedReturns = expectedReturns;
	}

	public int getSuggestedNoOfShares() {
		return suggestedNoOfShares;
	}

	public void setSuggestedNoOfShares(int suggestedNoOfShares) {
		this.suggestedNoOfShares = suggestedNoOfShares;
	}

}
