package edu.njit.cs673.portfoliooptimizer.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.TransactionType;
import edu.njit.cs673.portfoliooptimizer.vo.SellVO;

public interface TransactionDao {

	public void addTransaction(Portfolio portfolio, TransactionType transactionType, Date transactionDate,
			BigDecimal transactionAmount, int shareQuantity, BigDecimal unitSharePrice, String stockSymbol);
	
	public List<SellVO> getAllPortfolioTransactions(Portfolio portfolio);
	
	public void updateShareQuantity(Portfolio portfolio, String stockSymbol, BigDecimal sharePrice, int quantityChange );
}
