package edu.njit.cs673.portfoliooptimizer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.c673.portfoliooptimizer.model.Investor;
import edu.njit.c673.portfoliooptimizer.model.Portfolio;
import edu.njit.cs673.portfoliooptimizer.exception.AuthenticationException;
import edu.njit.cs673.portfoliooptimizer.service.LoginService;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;

@Controller
@Transactional
public class InvestorLoginController {

	private static final Logger log = Logger.getLogger(InvestorLoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") char[] password, HttpSession session) {

		// ModelAndView model = new ModelAndView("viewPortfolio");
		ModelAndView model = new ModelAndView("portfolioList");

		model.addObject("investorProfileVO", null);

		log.debug("User " + username + " trying to log in.");

		Investor investor = null;

		Set<String> errors = new HashSet<String>();
		List<String> portfolioSet = new ArrayList<String>();
		
		List<String> errorMessages = new ArrayList<String>();
		// InvestorProfileVO vo = new InvestorProfileVO();

		try {
			investor = loginService.authenticate(username, password);
		} catch (AuthenticationException e) {
			errors.add(e.toString());
			return model;
		}
		if (investor.equals(null))
		{
			errorMessages.add("Invalid user");
		}
		// vo.setFirstName(investor.getFirstName());
		// vo.setLastName(investor.getLastName());
		// vo.setInvestorId(investor.getInvestorId());
		// vo.setUsername(investor.getUserName());

		for (Portfolio portfolio : investor.getPortfolios()) {
			portfolioSet.add(portfolio.getPortfolioName());
		}
		// vo.setPortfolios(portfolioSet);

		log.debug("User " + username + " logged in successfully.");

		// model.addObject("investorProfileVO", vo);
		// session.setAttribute("investorProfileVO", vo);
		model.addObject("errorMessages",errorMessages);
		session.setAttribute("investor", investor);
		
		FxQuote inrUsdFx = null;
		try {
			inrUsdFx = YahooFinance.getFx("USDINR=X");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		session.setAttribute("fxRate", inrUsdFx.getPrice().doubleValue());
		
		return model;
	}

}
