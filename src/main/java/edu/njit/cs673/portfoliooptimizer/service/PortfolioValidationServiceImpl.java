package edu.njit.cs673.portfoliooptimizer.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.dao.PortfolioDao;
import edu.njit.cs673.portfoliooptimizer.service.StockService;

@Service
public class PortfolioValidationServiceImpl implements PortfolioValidationService {

	private static final Logger log = Logger.getLogger(PortfolioValidationServiceImpl.class);

	private static final int NIFTY_50_STOCK_EXCHANGE_ID = 2;
	private static final int DOW_30_STOCK_EXCHANGE_ID = 1;

	@Autowired
	StockService stockService;
	
	@Override
	public List<String> validatePortfolio(Portfolio portfolio) {

		List<String> validationErrors = new ArrayList<>();
		
		//boolean returnType = true;
		
		/*if (portfolio == null || portfolio.getStocks() == null || portfolio.getStocks().size() == 0) {
			returnType = false;
		}*/

		log.info("Validation portfolio - " + portfolio.getPortfolioId());

		// Portfolio portfolio = dao.getPortfolio();
		/*
		 * List<PortfolioStock> stockdow30 = null; stockdow30 = getStocklist(1);
		 * List<PortfolioStock> stocknifty50 = null; stocknifty50 =
		 * getStocklist(2);
		 */

		double dow30StockCount = 0;
		double nifty50StockCount = 0;
		
		double dow30SharePercentage = 0;
		double nifty50SharePercentage = 0;
		
		
		//for cash check of 30-70
		BigDecimal dow30cash = new BigDecimal(0);
		BigDecimal niftycash = new BigDecimal(0);

		List<PortfolioStock> stocks = portfolio.getStocks();
		List<StockPerformance> stockPerformance = null;
		try {
			stockPerformance = stockService.getStockPerformance(stocks);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(stocks.isEmpty())
		{
			
			validationErrors.add("No data for portfolio selected");
		}
		
		for (PortfolioStock stock : stocks) {

			if (stock.getStockExchangeType().getStockExchangeId() == DOW_30_STOCK_EXCHANGE_ID) {
				dow30StockCount++;
				for(StockPerformance stockperf : stockPerformance)
				{
					if(stock.getStockSymbol().equalsIgnoreCase(stockperf.getStockSymbol()))
					{
						dow30cash =  dow30cash.add(stockperf.getCostBasis());
					}
				}
			} else if (stock.getStockExchangeType().getStockExchangeId() == NIFTY_50_STOCK_EXCHANGE_ID) {
				nifty50StockCount++;
				for(StockPerformance stockperf : stockPerformance)
				{
					if(stock.getStockSymbol().equalsIgnoreCase(stockperf.getStockSymbol()))
					{
						niftycash = niftycash.add(stockperf.getCostBasis());
					}
				}
			}

		}
		

		
		log.debug("DOW 30 cash total count = " + dow30cash);
		log.debug("NIFTY 50 cash total count = " + niftycash);	
		
		
		log.debug("DOW 30 Stock count = " + dow30StockCount);
		log.debug("NIFTY 50 Stock count = " + nifty50StockCount);	
		
		BigDecimal totalcash = dow30cash.add(niftycash);
		
		double totalShareCount = dow30StockCount + nifty50StockCount;

		log.debug("Total Stock count = " + totalShareCount);
		
		//double dow30SharePercentage = (dow30StockCount / totalShareCount) * 100;
		//double nifty50SharePercentage = (nifty50StockCount / totalShareCount) * 100;
		
		validationErrors.add("Nifty Cash - " + niftycash);
		validationErrors.add("Dow 30 Cash- " + dow30cash);
		log.debug("Nifty Cash - " + niftycash);
		log.debug("Dow 30 Cash- " + dow30cash);
		
		if((!(totalcash == null)) && !totalcash.equals(new BigDecimal(0)))
		{
			dow30SharePercentage = (dow30cash.doubleValue()/totalcash.doubleValue()) * 100;
		 	nifty50SharePercentage = (niftycash.doubleValue() / totalcash.doubleValue()) * 100;
		 	
		 	DecimalFormat df = new DecimalFormat("#.##");
		 	String s_dow30SharePercentage = df.format(dow30SharePercentage);
		 	String s_nifty50SharePercentage = df.format(nifty50SharePercentage);
		
		
		validationErrors.add("Non Cash value: " + totalcash);
		validationErrors.add("Nifty Cash %- " + s_nifty50SharePercentage );
		validationErrors.add("Dow 30 Cash %- " + s_dow30SharePercentage );
		
		log.debug("Non Cash value: " + totalcash);
		log.debug("Dow 30 share percentage = " + dow30SharePercentage);
		log.debug("Nifty 50 share percentage = " + nifty50SharePercentage);
		}
		
		int availBalance = Integer.parseInt(portfolio.getCashBalance().toString());

		if (availBalance > 10000) {
			
			log.debug("Portfolio unbalanced due to Cash more than $10,000 ");
			validationErrors.add("Portfolio unbalanced due to Cash more than $10,000 ");			
		} 
		if (Math.ceil(dow30SharePercentage) >= 69.0d && Math.ceil(dow30SharePercentage) <= 71.0d)
				 {
			log.debug("DOW30 percentage = "+dow30SharePercentage);			
			//validationErrors.add("Portfolio is valid By Share balance Rule(Non-Cash value--70% Dow 30 and 30% Nifty 50");
		} 
		else
		{
			validationErrors.add("Portfolio is Invalid By Share balance Rule(Non-Cash value--70% Dow 30 and 30% Nifty 50)");
		}
		/*if(Math.floor(nifty50SharePercentage) < 31.0d && Math.floor(nifty50SharePercentage) < 29.0d){
			//validationErrors.add("Nifty Cash %- " + s_nifty50SharePercentage );
		}*/
		if (totalShareCount < 7 || totalShareCount > 9)
		{
			log.debug("Portfolio unbalanced due to min-max stocks");
			validationErrors.add("Portfolio unbalanced due to min-max stocks");			
		} 
		// int total = stockdow30.size() + stocknifty50.size();
		// float dow30Share = (stockdow30.size() / total) * 100;
		// float nif50Share = (stocknifty50.size() / total) * 100;

		/*
		 * if (dow30Share == 70.00 && nif50Share == 30.00 && availBalance <
		 * 1000) { return true; } else { return false; }
		 */
		return validationErrors;
	}

	

}
