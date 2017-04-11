package edu.njit.cs673.portfoliooptimizer.service;



import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.cs673.portfoliooptimizer.exception.AuthenticationException;

public interface LoginService {

	public Investor authenticate(String username, char[] password) throws AuthenticationException;

	public Investor getInvestorByUsername(String username);
}
