package edu.njit.c673.portfoliooptimizer.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "INVESTOR", schema = "tmp33")
public class Investor implements Serializable {

	private static final long serialVersionUID = 7736966193418047297L;

	@Id
	@Column(name = "INVESTOR_ID")
	@GeneratedValue(generator = "INVESTOR_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(schema = "tmp33", name = "INVESTOR_SEQ", sequenceName = "INVESTOR_SEQ", allocationSize = 1)
	private int investorId;

	@Column(name = "FIRST_NAME", length = 20)
	private String firstName;

	@Column(name = "LAST_NAME", length = 20)
	private String lastName;

	@Column(name = "USERNAME", length = 20, unique=true)
	private String userName;

	@Column(name = "PASSWORD", length = 20)
	private char[] password;

	@Column(name = "CREATION_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationDate;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "investor")
	private List<Portfolio> portfolios;

	public int getInvestorId() {
		return investorId;
	}

	public void setInvestorId(int investorId) {
		this.investorId = investorId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

}