package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioValidationService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;

@Controller
public class CashTransactionController {

	private static final Logger log = Logger.getLogger(CashTransactionController.class);
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	PortfolioValidationService portfolioValidationService;
	
	@RequestMapping(name="/addCash.htm", method=RequestMethod.GET)
	public ModelAndView addCash(@RequestParam(name = "portfolioId") int portfolioId,
			@RequestParam(name = "cashAmount") int cashAmount, HttpSession session,
			@RequestParam(name = "withdraw") boolean withdraw) {
		
		if(withdraw){
			cashAmount = cashAmount * -1;
		}
		
		session.getAttribute("viewPortfolio");
		
		ModelAndView model = new ModelAndView("viewPortfolio");
		List<String> errorMessages = new ArrayList<String>();
		
/*		if(portfolioId == 0 || cashAmount==0)
		{
			errorMessages.add("portfolio ID or cash is null");
		}
		*/
		portfolioService.addCash(portfolioId, new BigDecimal(cashAmount), withdraw);
		
		log.debug("Getting portfolio Stocks for portfolio ID - " + portfolioId);

		List<PortfolioStock> stocks = portfolioService.getStocksByPortfolio(portfolioId);
		//model.addObject("portfolioStocks", stocks);

		List<StockPerformance> performanceMatrix = null;
		
		if(stocks != null && stocks.size() > 0)
		{
			try {
				performanceMatrix = stockService.getStockPerformance(stocks);
			} catch (IOException e) {
				log.error("Performance matrices could not be fetched.");
			}		
		}

		Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
		session.setAttribute("portfolio", portfolio);
		
		errorMessages.addAll(portfolioValidationService.validatePortfolio(portfolio));
		
		
		model.addObject("performanceMatrix", performanceMatrix);
		model.addObject("errorMessages", errorMessages);
						
		return model;
	}
	
}
