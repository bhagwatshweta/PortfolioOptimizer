package edu.njit.cs673.portfoliooptimizer.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockExchangeType;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;

public class TestObject {

	public boolean validatePortfolio(Portfolio p) {

		//Portfolio portfolio = null;
		// Portfolio portfolio = dao.getPortfolio();
		List<PortfolioStock> stockdow30 = new ArrayList<>();
		stockdow30 = getStocklist(1,p);
		List<PortfolioStock> stocknifty50 = new ArrayList<>();
		stocknifty50 = getStocklist(2,p);
		int total = stockdow30.size() + stocknifty50.size();
		float dow30Share = ((float) stockdow30.size() / (float)total);
		dow30Share = (int) (dow30Share*100);
		float nif50Share = ((float)stocknifty50.size() /(float) total) ;
		nif50Share = (int) (nif50Share* 100);
		int res = p.getCashBalance().compareTo(new BigDecimal(1000));
		if (dow30Share == 70.00 && nif50Share == 30.00 && res == -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<PortfolioStock> getStocklist(int stockMatketIdex,Portfolio p) {
		
		List<PortfolioStock> stock = new ArrayList<>();
		//stock = p.getStocks();
		
		for(PortfolioStock inv: p.getStocks()){
			
			if(inv.getStockExchangeType().getStockExchangeId() == stockMatketIdex)
			{
				stock.add(inv);
			}
			
		}		
		return stock;
	}

	public static void main(String[] args) {
		
		//Test test = new Test();
		Investor inv = new Investor();
		Portfolio p = new Portfolio();
		
		inv.setInvestorId(1);
		inv.setFirstName("Tejas");
		inv.setLastName("Panchal");
		inv.setCreationDate(new Date());
		//inv.setPassword();
		inv.setUserName("tejas");
		
		
		StockExchangeType st = new StockExchangeType();
		st.setStockExchangeId(1);
		st.setStockExchangeName("dow30");
		st.setCurrencyName("US Dollar");
		st.setCurrencySymbol("$");
		
		StockExchangeType st1 = new StockExchangeType();
		st1.setStockExchangeId(2);
		st1.setStockExchangeName("Nif50");
		st1.setCurrencyName("INR");
		st1.setCurrencySymbol("INR");
		
		
		
		List<Portfolio>  portfolios = new ArrayList<>();
		p.setInvestor(inv);
		p.setPortfolioId(1);
		p.setPortfolioName("Tejas");
		p.setCreationDate(new Date());
		
		List<PortfolioStock> stocklist = new ArrayList<>();
		
		PortfolioStock stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(1);
		stock.setPurchasePrice(new BigDecimal(10.00));
		stock.setShareQuantity(new BigDecimal(15));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM1");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(2);
		stock.setPurchasePrice(new BigDecimal(12.00));
		stock.setShareQuantity(new BigDecimal(20));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM2");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(3);
		stock.setPurchasePrice(new BigDecimal(14.00));
		stock.setShareQuantity(new BigDecimal(10));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM3");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(4);
		stock.setPurchasePrice(new BigDecimal(16.00));
		stock.setShareQuantity(new BigDecimal(10));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM4");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(5);
		stock.setPurchasePrice(new BigDecimal(10.00));
		stock.setShareQuantity(new BigDecimal(15));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM5");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(6);
		stock.setPurchasePrice(new BigDecimal(8.00));
		stock.setShareQuantity(new BigDecimal(10));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(7);
		stock.setPurchasePrice(new BigDecimal(4.00));
		stock.setShareQuantity(new BigDecimal(10));
		stock.setStockExchangeType(st);
		stock.setStockSymbol("IBM");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(8);
		stock.setPurchasePrice(new BigDecimal(7.00));
		stock.setShareQuantity(new BigDecimal(12));
		stock.setStockExchangeType(st1);
		stock.setStockSymbol("IBM8");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(9);
		stock.setPurchasePrice(new BigDecimal(10.00));
		stock.setShareQuantity(new BigDecimal(15));
		stock.setStockExchangeType(st1);
		stock.setStockSymbol("IBM9");
		stocklist.add(stock);
		
		stock = new PortfolioStock();
		stock.setPortfolio(p);
		stock.setPortfolioStockId(10);
		stock.setPurchasePrice(new BigDecimal(4.00));
		stock.setShareQuantity(new BigDecimal(10));
		stock.setStockExchangeType(st1);
		stock.setStockSymbol("IBM10");
		stocklist.add(stock);
		
		p.setCashBalance(new BigDecimal(100));
		p.setStocks(stocklist);
		
		portfolios.add(p);
		
		inv.setPortfolios(portfolios);
		TestObject test = new TestObject();
		Boolean b = test.validatePortfolio(p);
		System.out.println("portfolio is" + b);

	}

}
