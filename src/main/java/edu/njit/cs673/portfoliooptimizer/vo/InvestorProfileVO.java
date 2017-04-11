package edu.njit.cs673.portfoliooptimizer.vo;

import java.util.List;

public class InvestorProfileVO extends GenericVO {

	private int investorId;
	private String firstName;
	private String lastName;
	private String username;

	private List<String> portfolios;

	public InvestorProfileVO() {

	}
	
	public InvestorProfileVO(int investorId, String firstName, String lastName, String username,
			List<String> portfolios) {
		this.investorId = investorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.portfolios = portfolios;

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(List<String> portfolios) {
		this.portfolios = portfolios;
	}

	public int getInvestorId() {
		return investorId;
	}

	public void setInvestorId(int investorId) {
		this.investorId = investorId;
	}

}
