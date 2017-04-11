package edu.njit.cs673.portfoliooptimizer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.cs673.portfoliooptimizer.optimizer.Optimizer;
import edu.njit.cs673.portfoliooptimizer.optimizer.StockStatistics;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;

@Controller
public class PortfolioOptimizationController {

	@Autowired	
	PortfolioService portfolioService;
	
	@RequestMapping(value="/optimizePortfolio.htm",method=RequestMethod.GET)
	public ModelAndView optimizePortfolio(@RequestParam(name = "portfolioId", required = true) int portfolioId) {

		ModelAndView optimizedPortfolio = new ModelAndView("optimizePortfolio");

		Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
		
		Optimizer optimizer =new Optimizer();
		
		List<StockStatistics> stats=optimizer.optimize(portfolio.getStocks());
		
		optimizedPortfolio.addObject("stockStatistics", stats);
		
		return optimizedPortfolio;

	}
}
