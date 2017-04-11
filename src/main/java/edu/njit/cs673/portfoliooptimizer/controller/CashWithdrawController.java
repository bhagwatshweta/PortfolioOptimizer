package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;

@Controller
public class CashWithdrawController {
	
private static final Logger log = Logger.getLogger(CashWithdrawController.class);
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	StockService stockService;
	
/*	@RequestMapping(name="/removeCash.htm", method=RequestMethod.GET)
	public ModelAndView removeCash(@RequestParam int portfolioId, @RequestParam int cashAmount, HttpSession session){
		
		session.getAttribute("viewPortfolio");
		
		ModelAndView model = new ModelAndView("viewPortfolio");
		
		
		
		portfolioService.removeCash(portfolioId, new BigDecimal(cashAmount));
		
		log.debug("Getting portfolio Stocks for portfolio ID - " + portfolioId);
		
		List<PortfolioStock> stocks = portfolioService.getStocksByPortfolio(portfolioId);
		//model.addObject("portfolioStocks", stocks);

		List<StockPerformance> performanceMatrix = null;
		try {
			performanceMatrix = stockService.getStockPerformance(stocks);
		} catch (IOException e) {
			log.error("Performance matrices could not be fetched.");
		}		
		
		model.addObject("performanceMatrix", performanceMatrix);
		
		return model;
	}
*/
}
