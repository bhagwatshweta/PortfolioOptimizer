package edu.njit.cs673.portfoliooptimizer.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.cs673.portfoliooptimizer.exception.AuthenticationException;
import edu.njit.cs673.portfoliooptimizer.service.LoginService;
import edu.njit.cs673.portfoliooptimizer.service.PortfolioService;
import edu.njit.cs673.portfoliooptimizer.vo.InvestorProfileVO;

@Controller
public class CreatePortfolioController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private PortfolioService portfolioService;
	
	private static final Logger log = Logger.getLogger(CreatePortfolioController.class);

	@RequestMapping(value = "/CreatePortfolio.htm", method = RequestMethod.POST)
	public ModelAndView CreatePortfolio(HttpSession session) {

		ModelAndView model = new ModelAndView("CreatePortfolio");

		model.addObject("investorID", session.getAttribute("investorID"));

		return model;
	}

	@RequestMapping(value = "/SavePortfolio.htm", method = RequestMethod.GET)
	public ModelAndView SavePortfolio(@RequestParam(name = "portfolioname") String portfolioname,
			@RequestParam(name = "portfoliodescription") String portfoliodescription, HttpSession session) {

		ModelAndView model = new ModelAndView("portfolioList");
		String currency = "usd";
		List<String> errorMessages = new ArrayList<String>();
		int investorID = ((Investor) session.getAttribute("investor")).getInvestorId();
		log.debug("User with investorID" + investorID + " has created portfolio of name" + portfolioname);
		if(portfolioname.isEmpty())
		{
			errorMessages.add("portfolio name is null");
		}
		
		portfolioService.SavePortfolio(portfolioname, currency, portfoliodescription, investorID);
		model.addObject("errorMessages", errorMessages);

		session.setAttribute("investor",
				loginService.getInvestorByUsername(((Investor) session.getAttribute("investor")).getUserName()));

		return model;
	}

}
