<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
html { font-family: "calibri", Garamond, 'Comic Sans MS'; }
</style>

<style type="text/css">
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

	<div style="margin-top: 20px; color: red;">
		<ul type="square">
			<c:forEach items="${errorMessages }" var="msg">
				<li>${msg}</li>
			</c:forEach>
		</ul>
	</div>
	<form action="${pageContext.request.contextPath}/updateSellShare.htm"
		method="post" style="margin-top: 50px; width: 500px; padding-left: 100px; height: 500px;">
			<table>
				<tr>
					<th>Stock Symbol</th>
					<th>Share Price</th>
					<th>Number of Shares</th>
					<th>Number of shares to sell</th>
				</tr>
				<c:forEach items="${sessionScope.sellVOList}" var="vo">
				<tr>
					<td>${vo.stockSymbol }</td>
					<td>${vo.unitSharePrice }</td>
					<td>${vo.numberOfShares }</td>
					<td style="text-align: center;">
						<input type="text" value="${vo.sharesToSell }"  size="5" width="10px;" style="text-align: right;" name="${vo.stockSymbol }@${vo.unitSharePrice }"/>
					</td>
				</tr>
				</c:forEach>
			</table>
			<br/><br/>		
			<input type="submit" value="Sell!" style="text-align: center; display: block; width: 500px;" />
	</form>
</body>
</html>