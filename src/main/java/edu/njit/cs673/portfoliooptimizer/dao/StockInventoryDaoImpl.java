package edu.njit.cs673.portfoliooptimizer.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;

@Repository
@Transactional
public class StockInventoryDaoImpl implements StockInventoryDao {

	private static final Logger log = Logger.getLogger(InvestorDaoImpl.class);

	@Autowired
	private HibernateTemplate template;

	@Override
	public List<StockInventory> getAllStockInventory() {

		Query q = template.getSessionFactory().getCurrentSession().createQuery("from StockInventory");
	    //q.setFirstResult(0); // modify this to adjust paging
	    //q.setMaxResults(100);
	    
		
		
	    return (List<StockInventory>) q.getResultList();
	    
		/*List<StockInventory> stocksList = template.get(StockInventory.class);
		if (stocksList == null || stocksList.size() == 0) {
			log.info("Stock List Not fetched.- " + stocksList.size());
			return null;
		} else {
			return stocksList;
		}*/
	}
	
	@Override
	public BigDecimal getCostBases(String StockSymbol,int PortfolioID)
	{
		String query = " select sum(t.Share_Quantity * t.UNIT_SHARE_PRICE) as CostBasis from  " + 
				" PORTFOLIO_STOCK p,TRANSACTION t where p.PORTFOLIO_ID = t.PORTFOLIO_ID  " +
				" and  p.PORTFOLIO_ID=:PortfolioID and p.STOCK_SYMBOL=:StockSymbol and t.STOCK_SYMBOL=:StockSymbol1 ";
		
		Query query1 =template.getSessionFactory().getCurrentSession().createSQLQuery(query);
		query1.setParameter("PortfolioID", PortfolioID);
		query1.setParameter("StockSymbol", StockSymbol);
		query1.setParameter("StockSymbol1", StockSymbol);
		
		List l =  query1.getResultList();
		BigDecimal cost = new BigDecimal(0);
		if(!l.isEmpty())
		{
		 	cost = (BigDecimal) l.get(0);
		}
		return cost;
	}
	

}
