<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Optimize Portfolio</title>
<style>
html { font-family: "calibri", Garamond, 'Comic Sans MS'; }
div.container {
	width: 100%;	
}

header, footer {
	padding: 1em;
	color: white;
	clear: left;
	text-align: center;
}

nav {
	float: left;
	max-width: 160px;
	margin: 0;
	padding: 1em;
}

nav ul {
	list-style-type: none;
	padding: 0;
}

nav ul a {
	text-decoration: none;
}

article {
	margin-left: 200px;	
	padding: 5em;
	overflow: hidden;
}
</style>
<meta name="generator" content="Google Web Designer 1.6.2.0916">

<style type="text/css" id="gwd-text-style">
p {
	margin: 0px;
}

h1 {
	margin: 0px;
}

h2 {
	margin: 0px;
}

h3 {
	margin: 0px;
}

table, th, td {
	border: 1px solid black;
	border-spacing: 5px;
	border-collapse: collapse;
	text-align: left;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}
</style>

<style type="text/css">
body {	
	background-image:
		url(" ${pageContext.request.contextPath}/images/e.jpg");
	background-size: 100% 100%;
	background-repeat: no-repeat;
	background-position: bottom;
}
</style>
</head>
<body>
	<div class="container">
		<table>
			
			<tr>
				<th>Stock Symbol</th>
				<th>Current Quantity</th>
				<th>Optimized Quantity</th>
				<th>Suggested Action</th>
			</tr>
			<c:forEach items="${stockStatistics }" var="stats">
			<tr>			
				<td>${stats.stockSymbol }</td>
				<td>${stats.noOfShares }</td>
				<td>${stats.suggestedNoOfShares }</td>			
				<td>
				<c:if test="${stats.noOfShares > stats.suggestedNoOfShares }"> SELL ${stats.noOfShares - stats.suggestedNoOfShares }</c:if>
				<c:if test="${stats.noOfShares < stats.suggestedNoOfShares }"> BUY ${stats.suggestedNoOfShares - stats.noOfShares }</c:if>
				<c:if test="${stats.noOfShares == stats.suggestedNoOfShares }">-</c:if>
				</td>
			</tr>
			</c:forEach>
		
		</table>
	</div>
</body>
</html>
