package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.dao.StockInventoryDao;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioStockService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioValidationService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.fx.FxQuote;

//@Transactional
@Controller
public class AddShareController {
	Logger log = Logger.getLogger(AddShareController.class);

	@Autowired
	StockService stockService;

	@Autowired
	PortfolioService portfolioService;

	@Autowired
	PortfolioStockService portfoliostockService;
	
	@Autowired
	PortfolioValidationService portfolioValidationService;
	
	@Autowired
	StockInventoryDao stockinventory;

	@RequestMapping(value = "/addShare.htm", method = RequestMethod.POST)
	public ModelAndView addShare(@RequestParam(name = "hdportfolioId") int hdportfolioId,
			@RequestParam(name = "stocklist") String stockSymbol,
			@RequestParam(name = "shareQuantity") int shareQuantity, HttpSession session) throws IOException {

		
		PortfolioStock portfoliostock = portfoliostockService.getPortfoliostockByStockSymbol(stockSymbol, hdportfolioId);
		// error list for validation
		List<String> errorMessages = new ArrayList<String>();
		boolean b = true;
		String msg = "Insufficient Balance";
		
	
		
		
		
		if (stockSymbol == null || shareQuantity == 0) {
			errorMessages.add("stock symbol or quantity is not specified");
		}
		BigDecimal price = new BigDecimal(10) ;
		if (portfoliostock == null) {
			/*URL	url = new URL(" http://finance.yahoo.com/quote/"+stockSymbol+"/history?period1=1473652800&period2=1473652800&interval=1d&filter=history&frequency=1d");
			BufferedReader br= new BufferedReader(new InputStreamReader(url.openStream()));
			String s = br.toString();*/
			
			Calendar from = Calendar.getInstance();
			from.set(2016, 8, 12);
			
			System.out.println(from.getTime());
			Calendar to = Calendar.getInstance();
			from.add(Calendar.HOUR, -1); // from 1 year ago
			
			try{
			Stock stock = YahooFinance.get(stockSymbol);			
			List<HistoricalQuote> googleHistQuotes = stock.getHistory(from, to, Interval.DAILY);
								
			List<StockInventory> stocks = stockService.getStockFromInventory();
			for (StockInventory stockInventory : stocks) {
				
				if(stockInventory.getStockSymbol().equals(stockSymbol))
				{
				if(stockInventory.getStockExchangeType().getStockExchangeId() == 2 )
				{
					//portfolioStock.setStockExchangeType(stockInventory.getStockExchangeType());
					throw new Exception();
				}
				}
			}
			
			price=googleHistQuotes.get(googleHistQuotes.size()-1).getClose();
			}catch( Exception e){								
				Set<String> stocks = new HashSet<String>();
				stocks.add(stockSymbol);
				Map<String,Stock>  stockMap = stockService.getHistoricalDataForNSEStock(stocks);
				Stock stock = stockMap.get(stockSymbol);
				price = stock.getQuote().getPrice();
				FxQuote inrUsdFx = YahooFinance.getFx("INRUSD=X");
				log.debug("INR USD VALUE Tejas: " + inrUsdFx.getPrice());
				price = stock.getQuote().getPrice().multiply(inrUsdFx.getPrice());
			}
			b = portfoliostockService.addStocktoPortfolio(stockSymbol, shareQuantity, price, hdportfolioId);
			
			
				
		} else {
			try{
				
			Stock stock = YahooFinance.get(stockSymbol);
			price = stock.getQuote().getPrice();
			List<StockInventory> stocks = stockService.getStockFromInventory();
			for (StockInventory stockInventory : stocks) {
				
				if(stockInventory.getStockSymbol().equals(stockSymbol))
				{
				if(stockInventory.getStockExchangeType().getStockExchangeId() == 2 )
				{
					Set<String> stocks1 = new HashSet<String>();
					stocks1.add(stockSymbol);
					Map<String,Stock>  stockMap = stockService.getHistoricalDataForNSEStock(stocks1);
					stock = stockMap.get(stockSymbol);
					
					FxQuote inrUsdFx = YahooFinance.getFx("INRUSD=X");
					price = stock.getQuote().getPrice().multiply(inrUsdFx.getPrice());
				}
				}
			}
			if(price ==null)
			{
				
			}
			}
			catch (Exception e) {
				/*Set<String> stocks = new HashSet<String>();
				stocks.add(stockSymbol);
				Map<String,Stock>  stockMap = stockService.getHistoricalDataForNSEStock(stocks);
				Stock stock = stockMap.get(stockSymbol);
				
				FxQuote inrUsdFx = YahooFinance.getFx("INRUSD=X");
				price = stock.getQuote().getPrice().multiply(inrUsdFx.getPrice());*/
			}			
			
			b = portfoliostockService.updateStocktoPortfolio(stockSymbol, shareQuantity, price, hdportfolioId);
		}
		

		/// http://finance.yahoo.com/quote/MS/history?period1=1473652800&period2=1473652800&interval=1d&filter=history&frequency=1d

		Portfolio portfolio = portfolioService.getPortfolioById(hdportfolioId);
		session.setAttribute("portfolio", portfolio);

		ModelAndView model = new ModelAndView("viewPortfolio");

		log.debug("Getting portfolio Stocks for portfolio ID - " + hdportfolioId);

		List<PortfolioStock> stocks = portfolioService.getStocksByPortfolio(hdportfolioId);

		// model.addObject("portfolioStocks", stocks);

		List<StockPerformance> performanceMatrix = null;

		if (stocks != null && stocks.size() > 0) {
			try {
				performanceMatrix = stockService.getStockPerformance(stocks);
			} catch (IOException e) {
				log.error("Performance matrices could not be fetched.");
			}

		} else {
			errorMessages.add("Server Error loading the page");
		}
		if(!b)
			errorMessages.add(msg);
		else
		{
			msg = "Successfull Buy..!";
			//model.addObject("BuyFail",msg);
			errorMessages.add(msg);
		}
		model.addObject("performanceMatrix", performanceMatrix);
		model.addObject("errorMessages", errorMessages);
		
		errorMessages.addAll(portfolioValidationService.validatePortfolio(portfolio));

		return model;
	}

}
