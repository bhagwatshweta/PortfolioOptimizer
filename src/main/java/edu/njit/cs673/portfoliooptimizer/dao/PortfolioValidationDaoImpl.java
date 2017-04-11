package edu.njit.cs673.portfoliooptimizer.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;

public class PortfolioValidationDaoImpl implements PortfolioValidationDao {

	private static final Logger log = Logger.getLogger(InvestorDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private HibernateTemplate template;
	
	@Override
	public List<PortfolioStock> getPortfolioStockByStockMarketIndex(int stockMarketIndex, int portfolioID) {
		
		log.debug("Finding stockMarketIndex - " + stockMarketIndex);

		@SuppressWarnings("unchecked")
		List<PortfolioStock> portfolioStocks = (List<PortfolioStock>) template
				.findByCriteria(DetachedCriteria.forClass(PortfolioStock.class).add(Restrictions.eq("", stockMarketIndex)));

		if (portfolioStocks == null || portfolioStocks.size() == 0) {
			log.info("No Stoclk found matching Portfolio- " + portfolioID);
			return null;
		} else {
			return portfolioStocks;
		}
	}

}
