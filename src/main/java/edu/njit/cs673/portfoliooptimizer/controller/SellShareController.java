package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.functions.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.c673.portfoliooptimizer.model.Transaction;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioStockService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;
import edu.njit.cs673.portfoliooptimizer.service.TransactionService;
import edu.njit.cs673.portfoliooptimizer.vo.SellVO;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Transactional
@Controller
public class SellShareController {

	

	@Autowired
	StockService stockService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	PortfolioStockService portfoliostockService;
	
	@Autowired
	TransactionService transationService;
	
	Logger log = Logger.getLogger(SellShareController.class);
	
	@RequestMapping(value = "/SellShare.htm", method = RequestMethod.GET)
	public ModelAndView sellShare(@RequestParam(name = "portfolioId", required = true) int portfolioId, HttpSession session) {
		
		List<String> errorMessages = new ArrayList<String>();
		
		ModelAndView model = new ModelAndView("SellShare");		
		
	
		List<SellVO> vos = transationService.getAllPortfolioTransactions(portfolioService.getPortfolioById(portfolioId));
		
	/*	
		if(transactions == null || transactions.size()==0){
			errorMessages.add("Nothing to sell!");
		}
		
		Map<String, Map<BigDecimal,List<Transaction>>> txByStockByPrice = transactions.stream()
																					  .collect(Collectors.groupingBy(Transaction::getStockSymbol, 
																							   Collectors.groupingBy(Transaction::getUnitSharePrice)));					
		List<SellVO> sellVOList = new ArrayList<>();
		for (Map.Entry<String, Map<BigDecimal,List<Transaction>>> e:txByStockByPrice.entrySet()){
			SellVO tempVO = new SellVO();
			tempVO.setStockSymbol(e.getKey());
			for(Map.Entry<BigDecimal,List<Transaction>> e1:e.getValue().entrySet()){
				tempVO.setPurchasePrice(e1.getKey().floatValue());
				
				Optional<Float> shareQuantity = e1.getValue().stream().map((t) -> t.getShareQuantity()).reduce((t,sum)-> t+sum);
				
				tempVO.setNumberOfShares(shareQuantity.get().intValue());
				
				sellVOList.add(tempVO);
			}
		}		
*/		//model.addObject("portfolioId", portfolioId);
			

		session.setAttribute("sellVOList", vos);
		vos.forEach(System.out::println);
		//model.addObject("sellVOList", vos);
		model.addObject("errorMessages",errorMessages);
		return model;
	}

}
