package edu.njit.cs673.portfoliooptimizer.exception;

public class AuthenticationException extends Exception {

	private static final long serialVersionUID = -8209748445340814842L;
	private String username;

	public AuthenticationException(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Autentication Failed for user - " + username;
	}
}
