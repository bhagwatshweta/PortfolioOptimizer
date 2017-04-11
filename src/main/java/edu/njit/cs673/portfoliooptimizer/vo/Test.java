package edu.njit.cs673.portfoliooptimizer.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.StockExchangeType;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;

public class Test {

	public boolean validatePortfolio(Portfolio p) {

	/*	//Portfolio portfolio = null;
		// Portfolio portfolio = dao.getPortfolio();
		List<StockInventory> stockdow30 = new ArrayList<>();
		stockdow30 = getStocklist(1,p);
		List<StockInventory> stocknifty50 = new ArrayList<>();
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
		}*/
		return false;
	}
	
	/*public List<StockInventory> getStocklist(int stockMatketIdex,Portfolio p) {
		
		List<StockInventory> stock = new ArrayList<>();
		//stock = p.getStocks();
		
		for(StockInventory inv: p.getStocks()){
			
			if(inv.getStockExchangeType().getStockExchangeId() == stockMatketIdex)
			{
				stock.add(inv);
			}
			
		}
		
		
		return stock;
	}*/
	
	public static void main(String[] args) {
	
		Test test = new Test();
/*		Portfolio p = new Portfolio();
		Investor inv = new Investor();
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
		p.setInvestor(new Investor());
		p.setPortfolioId(1);
		p.setPortfolioName("Tejas");
		
		List<StockInventory> stocklist = new ArrayList<>();
		
		StockInventory stock = new StockInventory();
		stock.setStockId(1);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM");
		stock.setStockSymbol("IBM");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(2);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM1");
		stock.setStockSymbol("IBM1");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(3);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM3");
		stock.setStockSymbol("IBM3");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(4);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM4");
		stock.setStockSymbol("IBM4");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(8);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM8");
		stock.setStockSymbol("IBM8");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(9);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM9");
		stock.setStockSymbol("IBM9");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(10);
		stock.setStockExchangeType(st);
		stock.setStockName("IBM10");
		stock.setStockSymbol("IBM10");
		stocklist.add(stock);
		
		
		
		stock = new StockInventory();
		stock.setStockId(5);
		stock.setStockExchangeType(st1);
		stock.setStockName("IN1");
		stock.setStockSymbol("IN1");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(6);
		stock.setStockExchangeType(st1);
		stock.setStockName("IBM6");
		stock.setStockSymbol("IBM6");
		stocklist.add(stock);
		
		stock = new StockInventory();
		stock.setStockId(7);
		stock.setStockExchangeType(st1);
		stock.setStockName("IBM7");
		stock.setStockSymbol("IBM7");
		stocklist.add(stock);
		p.setCashBalance(new BigDecimal(100));
		p.setStocks(stocklist);
		
		portfolios.add(p);
		
		inv.setPortfolios(portfolios);
		
		Boolean b = test.validatePortfolio(p);
		System.out.println("portfolio is" + b);*/
		
		BigDecimal num1 = new BigDecimal("300.5");
		BigDecimal num2 = new BigDecimal("1000.5");
		
		System.out.println(num1.compareTo(num2));
	}
}
