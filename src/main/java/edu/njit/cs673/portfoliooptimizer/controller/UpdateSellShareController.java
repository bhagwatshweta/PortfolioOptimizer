package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioStockService;
import edu.njit.cs673.portfoliooptimizer.service.StockService;
import edu.njit.cs673.portfoliooptimizer.service.TransactionService;
import edu.njit.cs673.portfoliooptimizer.vo.SellVO;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;

@Controller
public class UpdateSellShareController {

	private static final Logger log = Logger.getLogger(UpdateSellShareController.class);

	@Autowired
	private StockService stockService;

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private PortfolioStockService portfoliostockService;
	
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/updateSellShare.htm", method = RequestMethod.POST)
	public ModelAndView sellShare( HttpSession session, HttpServletRequest request) {
		int hdportfolioId = ((Portfolio)session.getAttribute("portfolio")).getPortfolioId();
		
		List<String> errorMessages = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		List<SellVO> sharesToSell = (List<SellVO>)session.getAttribute("sellVOList");
				
		sharesToSell.stream().forEach((vo) -> {
			String stockSymbol = vo.getStockSymbol();
			log.info("Getting share quantity for UI element name - " + vo.getStockSymbol() + "@" + vo.getUnitSharePrice().setScale(2).toString());
			
			String temp = vo.getStockSymbol() + "@" + vo.getUnitSharePrice().setScale(2).toString();
			if(temp.endsWith(".00")){
				temp = temp.substring(0, temp.length()-3);
			}
			int shareQuantity = Integer.parseInt(					
					request.getParameter(temp));
			
			if(shareQuantity > 0 && vo.getNumberOfShares() > vo.getSharesToSell()){			
			BigDecimal currentPrice = null;
			try {
				try {
					currentPrice = stockService.getStockQuotes(new String[] { stockSymbol }).get(stockSymbol).getQuote(false)
							.getPrice();
				} catch (Exception e1) {
					Set<String> stocks = new HashSet<String>();
					stocks.add(stockSymbol);
					Map<String, Stock> stockMap = stockService.getHistoricalDataForNSEStock(stocks);
					Stock stock = stockMap.get(stockSymbol);

					FxQuote inrUsdFx = YahooFinance.getFx("INRUSD=X");
					currentPrice = stock.getQuote().getPrice().multiply(inrUsdFx.getPrice());
				}
						
				log.info("Trying to sell stock - " + stockSymbol+ "portfolio id - "+ hdportfolioId+ "stock quantity to sell -"+ shareQuantity);			
				
				portfoliostockService.sellStockPortfolio(vo.getStockSymbol(),  ((int)shareQuantity),
						portfolioService.getPortfolioById(hdportfolioId));
				/*if((int)vo.getNumberOfShares() > ((int)shareQuantity)){
				
				}else if((int)vo.getNumberOfShares() == ((int)shareQuantity)){
				portfoliostockService.delete(vo.getStockSymbol(), (Portfolio)session.getAttribute("portfolio"));	
				}*/
				
				BigDecimal cashBack = currentPrice.multiply(new BigDecimal(shareQuantity));
				log.info("Will be getting cash back of $"+ cashBack.floatValue());
				portfolioService.addCash(hdportfolioId,cashBack , false);
				transactionService.updateShareQuantity(portfolioService.getPortfolioById(hdportfolioId),
						vo.getStockSymbol(), vo.getUnitSharePrice(), shareQuantity);				
			} catch (Exception e) {
				log.error("Error selling stock - " + stockSymbol+ "portfolio id - "+ hdportfolioId+ "stock quantity to sell -"+ shareQuantity);
				errorMessages.add("Error while selling stock - " + stockSymbol);
			}			
			}
		});

		ModelAndView model = new ModelAndView("viewPortfolio");

		log.debug("Getting portfolio Stocks for portfolio ID - " + hdportfolioId);

		List<PortfolioStock> stocks = portfolioService.getStocksByPortfolio(hdportfolioId);		
		List<StockPerformance> performanceMatrix = null;

		if (stocks != null && stocks.size() > 0) {
			try {
				performanceMatrix = stockService.getStockPerformance(stocks);
			} catch (IOException e) {
				log.error("Performance matrices could not be fetched.");
			}

		}
		if (stocks.isEmpty()) {
			errorMessages.add("stocks or portfolio id is missing");
		}

		model.addObject("performanceMatrix", performanceMatrix);
		model.addObject("errorMessages", errorMessages);

		Portfolio portfolio = portfolioService.getPortfolioById(hdportfolioId);
		session.setAttribute("portfolio", portfolio);

		return model;
	}

}
