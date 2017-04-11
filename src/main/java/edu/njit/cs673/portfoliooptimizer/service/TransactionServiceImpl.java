package edu.njit.cs673.portfoliooptimizer.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.Transaction;
import edu.njit.c673.portfoliooptimizer.model.TransactionType;
import edu.njit.cs673.portfoliooptimizer.dao.TransactionDao;
import edu.njit.cs673.portfoliooptimizer.vo.SellVO;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionDao transactionDao;

	@Override
	public void addTransaction(Portfolio portfolio, TransactionType transactionType, Date transactionDate,
			BigDecimal transactionAmount, int shareQuantity, BigDecimal unitSharePrice, String stockSymbol) {

		transactionDao.addTransaction(portfolio, transactionType, transactionDate, transactionAmount, shareQuantity,
				unitSharePrice, stockSymbol);
	}
	
	public List<SellVO> getAllPortfolioTransactions(Portfolio portfolio){
		
		return transactionDao.getAllPortfolioTransactions(portfolio);
	}
	
	public void updateShareQuantity(Portfolio portfolio, String stockSymbol, BigDecimal sharePrice, int quantityChange ){
		
		transactionDao.updateShareQuantity(portfolio, stockSymbol, sharePrice, quantityChange);
		
	}
}
