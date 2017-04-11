package edu.njit.cs673.portfoliooptimizer.dao;

import edu.njit.c673.portfoliooptimizer.model.Investor;

public interface InvestorDao {

	public Investor getInvestorByUsername(String username);

}
