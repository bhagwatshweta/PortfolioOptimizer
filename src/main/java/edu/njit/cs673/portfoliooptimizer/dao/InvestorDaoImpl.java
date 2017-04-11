package edu.njit.cs673.portfoliooptimizer.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.Investor;

@Repository
@Transactional
public class InvestorDaoImpl implements InvestorDao {

	private static final Logger log = Logger.getLogger(InvestorDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private HibernateTemplate template;

	@Override
	public Investor getInvestorByUsername(String username) {

		log.debug("Finding investor by username - " + username);

		@SuppressWarnings("unchecked")
		List<Investor> investors = (List<Investor>) template
				.findByCriteria(DetachedCriteria.forClass(Investor.class).add(Restrictions.eq("userName", username)));

		if (investors == null || investors.size() == 0) {
			log.info("No investor found matching username - " + username);
			return null;
		} else {
			return investors.get(0);
		}

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public HibernateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

}
