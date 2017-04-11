package edu.njit.cs673.portfoliooptimizer.service;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.cs673.portfoliooptimizer.dao.InvestorDao;
import edu.njit.cs673.portfoliooptimizer.exception.AuthenticationException;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	private static final Logger log = Logger.getLogger(LoginServiceImpl.class);

	@Autowired
	private InvestorDao investorDao;

	@Override
	public Investor authenticate(String username, char[] password) throws AuthenticationException {
		
		log.debug("Authenticating username -" + username+ " password - ");
		
		Investor investor = investorDao.getInvestorByUsername(username);				
		
		if (investor == null || !Arrays.equals(password, investor.getPassword())) {
			log.info("AUTH ERROR:: Invalid username or password for username - " + username);
			throw new AuthenticationException(username);
		}

		return investor;
	}

	public InvestorDao getInvestorDao() {
		return investorDao;
	}

	public void setInvestorDao(InvestorDao investorDao) {
		this.investorDao = investorDao;
	}

	@Override
	public Investor getInvestorByUsername(String username) {
		return investorDao.getInvestorByUsername(username);
	}
}
