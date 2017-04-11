package edu.njit.cs673.portfoliooptimizer.dao;

import java.math.BigDecimal;
import java.util.List;

import edu.njit.c673.portfoliooptimizer.model.StockInventory;

public interface StockInventoryDao {
	
	public List<StockInventory> getAllStockInventory();
	
	public BigDecimal getCostBases(String StockSymbol,int PortfolioID);
	

}
