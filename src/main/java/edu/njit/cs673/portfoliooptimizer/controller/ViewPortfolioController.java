package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioValidationService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;

@Transactional
@Controller
public class ViewPortfolioController {

	Logger log = Logger.getLogger(ViewPortfolioController.class);

	@Autowired
	PortfolioService portfolioService;

	@Autowired
	StockService stockService;

	@Autowired
	PortfolioValidationService portfolioValidationService;
	
	@RequestMapping(value = "/getPortfolioDetails.htm", method = RequestMethod.GET)
	public ModelAndView getStockDetails(@RequestParam(name = "portfolioId", required = true) int portfolioId,
			HttpSession session) {

		Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);			
		
		session.setAttribute("portfolio", portfolio);
		List<String> errorMessages = new ArrayList<String>();
			
		errorMessages.addAll(portfolioValidationService.validatePortfolio(portfolio));
		
		
		ModelAndView model = new ModelAndView("viewPortfolio");

		log.debug("Getting portfolio Stocks for portfolio ID - " + portfolioId);

		List<PortfolioStock> stocks = portfolioService.getStocksByPortfolio(portfolioId);

		// model.addObject("portfolioStocks", stocks);

		List<StockPerformance> performanceMatrix = null;

		if (stocks != null && stocks.size() > 0) {
			try {
				performanceMatrix = stockService.getStockPerformance(stocks);
			} catch (IOException e) {
				log.error("Performance matrices could not be fetched.");
			}
			
		}		

		model.addObject("performanceMatrix", performanceMatrix);
		model.addObject("errorMessages", errorMessages);
		return model;
	}
}
