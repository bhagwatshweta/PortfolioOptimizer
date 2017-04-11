package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioStockService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;
import edu.njit.cs673.portfoliooptimizer.service.TransactionService;
import edu.njit.cs673.portfoliooptimizer.vo.SellVO;

@Transactional
@Controller
public class ExportPortfolioController {

	@Autowired
	StockService stockService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	PortfolioStockService portfoliostockService;
	
	@Autowired
	TransactionService transationService;
	
	Logger log = Logger.getLogger(SellShareController.class);
	
	@RequestMapping(value = "/ExportPortfolio.htm", method = RequestMethod.GET)
	public ModelAndView ExportPortfolio(@RequestParam(name = "portfolioId", required = true) int portfolioId, HttpSession session ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("application/csv");   
		response.setHeader("content-disposition","attachment;filename =filename.csv"); 
		ServletOutputStream  writer = response.getOutputStream();  
		            log.info("downloading contents to csv");

		            
		            writer.print("portfolioId");
		            writer.print(',');
		            writer.print("Stock Symbol");
		            writer.print(',');
		            writer.print("Purchase Price");
		            writer.print(',');
		            writer.print("Stock Quantity");		            
		            writer.println();
		            List<PortfolioStock> portfolioStock = portfolioService.getStocksByPortfolio(portfolioId);

		            for (PortfolioStock stock : portfolioStock) 
		            {
		            	writer.print(portfolioId);
		            	writer.print(',');
		            	writer.print(stock.getStockSymbol());
		            	writer.print(',');
		            	writer.print(""+stock.getPurchasePrice());
		            	writer.print(',');
			            writer.println(""+stock.getShareQuantity());
			            writer.println();
		            }
		            
		       
		        writer.flush();
		        writer.close();

	return null;
	}
}
