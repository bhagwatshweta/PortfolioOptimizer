package edu.njit.cs673.portfoliooptimizer.dao;

import java.math.BigDecimal;
import java.util.List;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;

public interface PortfolioDao {

	public Portfolio getPortfolioById(int portfolioId);
	
	public List<PortfolioStock> getStocksByPortfolio(int portfolioId);
	
	public void addCash(int portfolioId, BigDecimal cash, boolean remove);
	
	public void removeCash(int portfolioId, BigDecimal cash, boolean remove);
	
	public void SavePortfolio(String portfolioName,String currency, String portfolioDescription,int investorID);

	//public void removeCash(int portfolioId, BigDecimal cash);
}
