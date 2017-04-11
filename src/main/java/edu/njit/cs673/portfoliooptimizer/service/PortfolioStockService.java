package edu.njit.cs673.portfoliooptimizer.service;

import java.math.BigDecimal;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;

public interface PortfolioStockService {


	public PortfolioStock getPortfoliostockByStockSymbol(String stockSymbol,int portfolioID);
	
	public boolean addStocktoPortfolio(String stockSymbol,int shareQuantity,BigDecimal purchasePrice,int portfolioID);
	
	public void sellStockPortfolio(String stockSymbol,int shareQuantity,Portfolio portfolio);
	
	public boolean updateStocktoPortfolio(String stockSymbol,int shareQuantity,BigDecimal purchasePrice,int portfolioID);
	
	public void delete(String stockSymbol, Portfolio portfolio);
}
