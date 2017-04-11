package edu.njit.cs673.portfoliooptimizer.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.TransactionType;
import edu.njit.cs673.portfoliooptimizer.dao.PortfolioDao;
import edu.njit.cs673.portfoliooptimizer.dao.PortfolioStockDao;

@Transactional
@Service
public class PortfolioStockServiceImpl implements PortfolioStockService {

	@Autowired
	PortfolioStockDao portfoliostockservice;

	@Autowired
	PortfolioDao portfolioDao;

	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	TransactionService transactionService;

	@Override
	public PortfolioStock getPortfoliostockByStockSymbol(String stockSymbol, int portfolioID) {

		return portfoliostockservice.getPortfoliostockByStockSymbol(stockSymbol, portfolioID);
	}

	@Override
	public boolean addStocktoPortfolio(String stockSymbol, int shareQuantity, BigDecimal purchasePrice, int portfolioID) {
		// TODO Auto-generated method stub
		TransactionType ttype = new TransactionType();
		ttype.setTransactionTypeId(1);
		ttype.setDescription("Buy");
		
		Portfolio p = portfolioService.getPortfolioById(portfolioID);
		BigDecimal buyAmount = new BigDecimal(shareQuantity).multiply(purchasePrice);
		if (buyAmount.compareTo(p.getCashBalance()) == -1) {
			portfoliostockservice.addStocktoPortfolio(stockSymbol, shareQuantity, purchasePrice, portfolioID);
			transactionService.addTransaction(p, ttype, new Date(), buyAmount, shareQuantity, purchasePrice, stockSymbol);
			portfolioDao.addCash(portfolioID, purchasePrice.multiply(new BigDecimal(shareQuantity)).negate(), true);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateStocktoPortfolio(String stockSymbol,int shareQuantity,BigDecimal purchasePrice,int portfolioID)
	{
		TransactionType ttype = new TransactionType();
		ttype.setTransactionTypeId(1);
		ttype.setDescription("Buy");
		
		Portfolio p = portfolioService.getPortfolioById(portfolioID);
		BigDecimal buyAmount = new BigDecimal(shareQuantity).multiply(purchasePrice);
		if (buyAmount.compareTo(p.getCashBalance()) == -1) {
			
			portfoliostockservice.updateStocktoPortfolio(stockSymbol, shareQuantity, purchasePrice, portfolioID);
			transactionService.addTransaction(p, ttype, new Date(), buyAmount, shareQuantity, purchasePrice, stockSymbol);
			portfolioDao.addCash(portfolioID, purchasePrice.multiply(new BigDecimal(shareQuantity)).negate(), true);
			return true;
		} else {
			return false;
		}
	}
	
	

	@Override
	public void sellStockPortfolio(String stockSymbol, int shareQuantity, Portfolio portfolio) {
		portfoliostockservice.sellStockPortfolio(stockSymbol, shareQuantity, portfolio);				
	}
	
	@Override
	public void delete(String stockSymbol, Portfolio portfolio){
		portfoliostockservice.deleteStocks(stockSymbol, portfolio);			
	}


}
