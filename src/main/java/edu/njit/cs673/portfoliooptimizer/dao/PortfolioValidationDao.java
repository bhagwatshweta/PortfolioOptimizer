package edu.njit.cs673.portfoliooptimizer.dao;

import java.util.List;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;

public interface PortfolioValidationDao {

	public List<PortfolioStock> getPortfolioStockByStockMarketIndex(int stockMarketIndex,int portfolioID);

}
