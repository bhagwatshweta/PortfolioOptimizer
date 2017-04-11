package edu.njit.cs673.portfoliooptimizer.vo;

import java.util.List;

public abstract class GenericVO {

	private List<String> errorList;

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

}
