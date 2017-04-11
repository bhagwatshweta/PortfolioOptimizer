package edu.njit.cs673.portfoliooptimizer.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.Transaction;
import edu.njit.c673.portfoliooptimizer.model.TransactionType;
import edu.njit.cs673.portfoliooptimizer.vo.SellVO;
@Repository
@Transactional
public class TransactionDaoImpl implements TransactionDao{

	@Autowired
	private HibernateTemplate template;	
	
	@Override
	public void addTransaction(Portfolio portfolio, TransactionType transactionType, Date transactionDate,
			BigDecimal transactionAmount, int shareQuantity, BigDecimal unitSharePrice, String stockSymbol) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Transaction.class);
		
		criteria.add(Restrictions.eq("portfolio", portfolio))
				.add(Restrictions.eq("stockSymbol", stockSymbol))
				.add(Restrictions.eq("unitSharePrice", unitSharePrice));
		
		List<Transaction> txList = (List<Transaction>)template.findByCriteria(criteria);
		
		if(txList != null && txList.size() > 0){
			Transaction existingTx = txList.get(0);
			existingTx.setShareQuantity(existingTx.getShareQuantity() + shareQuantity);
			template.save(existingTx);
		}
		else{		
			Transaction transaction = new Transaction();
			transaction.setPortfolio(portfolio);
			transaction.setTransactionType(transactionType);
			transaction.setTransactionDate(transactionDate);
			transaction.setShareQuantity(shareQuantity);
			transaction.setTransactionAmount(transactionAmount);
			transaction.setUnitSharePrice(unitSharePrice);
			transaction.setStockSymbol(stockSymbol);
		
			template.save(transaction);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<SellVO> getAllPortfolioTransactions(Portfolio portfolio){		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Transaction.class);
		
		Projection p = 	Projections.projectionList()
									.add(Projections.property("stockSymbol"),"stockSymbol")
									.add(Projections.property("unitSharePrice"),"unitSharePrice")									
									.add(Projections.sum("shareQuantity").as("numberOfShares"))
									.add(Property.forName("stockSymbol").group())
									.add(Property.forName("unitSharePrice").group())
									;
		
		criteria
		.add(Restrictions.eq("portfolio", portfolio))		
		.setProjection(p).setResultTransformer(Transformers.aliasToBean(SellVO.class));
		
		
		return (List<SellVO>) template.findByCriteria(criteria);
		
	}

	@Override
	public void updateShareQuantity(Portfolio portfolio, String stockSymbol, BigDecimal sharePrice,
			int quantityChange) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Transaction.class);
		
		criteria.add(Restrictions.eq("portfolio", portfolio))
				.add(Restrictions.eq("stockSymbol", stockSymbol))
				.add(Restrictions.eq("unitSharePrice", sharePrice));
		
		Transaction transaction = (Transaction)template.findByCriteria(criteria).get(0);
		
		if(transaction.getShareQuantity() == quantityChange){
			template.delete(transaction);
		}else if (transaction.getShareQuantity() > quantityChange){
		
			transaction.setShareQuantity(transaction.getShareQuantity() - quantityChange);
			transaction.setTransactionAmount(transaction.getTransactionAmount()
					.subtract(transaction.getUnitSharePrice().multiply(new BigDecimal(quantityChange))));
			template.save(transaction);
		}
		
	}

}
