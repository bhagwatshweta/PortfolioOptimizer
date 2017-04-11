<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Portfolio List</title>
<style>
html { font-family: "calibri", Garamond, 'Comic Sans MS'; }
body {
                background-image: url("${pageContext.request.contextPath}/images/c.jpg");
                background-repeat: no-repeat;
                background-size: cover; 
                background-position: center;  
}
</style>
</head>
<body>
	<div>
		<form action="${pageContext.request.contextPath}/CreatePortfolio.htm"
			method="post">
			<div style="width: 500px;margin-left: 200px;margin-top: 50px;">
			<h1>Hello ${sessionScope.investor.firstName }</h1>						
			<input type="submit" title="Create Portfolio" name="btnPortfolio" value="New Portfolio">

			<p>Please select from the list of portfolios below.</p>
			<ul>
				<c:forEach items="${sessionScope.investor.portfolios}"
					var="portfolio">
					<li><a
						href="${pageContext.request.contextPath}/getPortfolioDetails.htm?portfolioId=${portfolio.portfolioId}">${portfolio.portfolioName }</a></li>
				</c:forEach>
			</ul>
			</div>
		</form>
	</div>

</body>
</html>