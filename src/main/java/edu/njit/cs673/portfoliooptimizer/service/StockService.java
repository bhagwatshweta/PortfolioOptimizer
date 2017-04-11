package edu.njit.cs673.portfoliooptimizer.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import yahoofinance.Stock;


public interface StockService {

	public Map<String, Stock> getStockQuotes(String[] stockSymbols);
	
	public List<StockPerformance> getStockPerformance(List<PortfolioStock> portfolioStocks) throws IOException ;
	
	public List<StockInventory> getStockFromInventory();
	
	public Map<String,Stock> getHistoricalDataForNSEStock(Set<String> stockQuotes);
	
}
