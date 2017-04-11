package edu.njit.cs673.portfoliooptimizer.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;

@Repository
@Transactional
public class PortfolioDaoImpl implements PortfolioDao {

	private static final Logger log = Logger.getLogger(PortfolioDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private HibernateTemplate template;

	@Override
	public List<PortfolioStock> getStocksByPortfolio(int portfolioId) {

		log.debug("getting stocks under portfolio: " + portfolioId);

		Portfolio portfolio = template.get(Portfolio.class, portfolioId);

		if (portfolio == null) {
			log.debug("Invalid portfolio ID - " + portfolioId);
			return null;
		}

		return portfolio.getStocks();

	}

	@Override
	public void addCash(int portfolioId, BigDecimal cash, boolean remove) {

		if(remove){
			
			Portfolio portfolio = template.get(Portfolio.class, portfolioId);
			
			BigDecimal cashBalance = portfolio.getCashBalance();
			if(cashBalance.compareTo(cash.negate()) >= 0)
			{
				cashBalance = cashBalance.add(cash);
			portfolio.setCashBalance(cashBalance);

			template.save(portfolio);
			}
			else 
			{
				log.error("Cannot withdraw cash greater than deposited amount : " +cash);				
			}			
		}else{		
			
		Portfolio portfolio = template.get(Portfolio.class, portfolioId);

		portfolio.setCashBalance(portfolio.getCashBalance().add(cash));

		template.save(portfolio);
		}
	}
	
	
	@Override
	public void removeCash(int portfolioId, BigDecimal cash, boolean remove) {

		if(remove){
			
			Portfolio portfolio = template.get(Portfolio.class, portfolioId);
			
			BigDecimal cashBalance = portfolio.getCashBalance();
			if(cashBalance.compareTo(cash.negate()) >= 0)
			{
				cashBalance = cashBalance.add(cash);
			portfolio.setCashBalance(cashBalance);

			template.save(portfolio);
			}
			else 
			{
				log.error("Cannot withdraw cash greater than deposited amount : " +cash);				
			}			
		}else{				
		Portfolio portfolio = template.get(Portfolio.class, portfolioId);

		portfolio.setCashBalance(portfolio.getCashBalance().add(cash));

		template.save(portfolio);
		}
	}

	public Portfolio getPortfolioById(int portfolioId) {

		return template.get(Portfolio.class, portfolioId);

	}

	public void SavePortfolio(String portfolioName,String currency, String portfolioDescription,int investorID) {
		
		String sql1 = "insert into Portfolio (portfolio_ID,portfolio_Name,Creation_Date,CASH_BALANCE,INVESTOR_ID) values(portfolio_seq.nextval, :portfolioName,:creationDate,:cashbalance,:investorID)";
		Query sql = sessionFactory.getCurrentSession().createSQLQuery(sql1);
		sql.setParameter("portfolioName",portfolioName);
		sql.setParameter("creationDate",new Date());
		sql.setParameter("cashbalance",0);
		sql.setParameter("investorID",investorID);
		sql.executeUpdate();
		
	}
/*
	@Override
	public void removeCash(int portfolioId, BigDecimal cash) {
		// TODO Auto-generated method stub
		
		Portfolio portfolio = template.get(Portfolio.class, portfolioId);
		
		BigDecimal cashBalance = portfolio.getCashBalance();
		if(cashBalance.compareTo(cash) < 1)
		{
			cashBalance = cashBalance.subtract(cash);
		portfolio.setCashBalance(cashBalance);

		template.save(portfolio);
		}
		else
		{
			log.error("Cannot withdraw cash greater than deposited amount : " +cash);
			
		}
		
	}
*/
	
}
