<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
		<div>
			<div style="margin-top: 20px; color: red;">
				<ul type="square">
					<c:forEach items="${errorMessages }" var="msg">
						<li>${msg}</li>
					</c:forEach>
				</ul>
			</div>

			<table>

				<h2>Cash Balance: ${portfolio.cashBalance } $</h2>

				<h2>portfolio ID: ${portfolio.portfolioId }</h2>

				<caption>					
					<h3>
						<u>Portfolio Performance!!</u>
					</h3>

				</caption>

				<tbody>

					<tr>

						<!-- <th>Name</th> -->

						<th>Symbol</th>

						<th>Purchase Price</th>
						<th>Last Price</th>

						<th>Change</th>

						<th>Shares</th>

						<th>Cost basis</th>

						<th>Mkt Value</th>

						<th>Gain</th>

						<th>Gain %</th>

						<th>Overall return</th>

					</tr>
					<c:forEach items="${performanceMatrix}" var="perf">
							<c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
								<tr style="background-color:#651fff;">								
								</c:if> <c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
								<tr style="background-color:#b388ff;">								
								</c:if>
							<%-- <th>${stock.stockSymbol }</th> --%>

							<td>${perf.stock.stockSymbol}</td>
							<td><c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
								${perf.stock.purchasePrice} $								
								</c:if> <c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
								${perf.stock.purchasePrice} $ / <fmt:formatNumber
										maxFractionDigits="2" groupingUsed="true"
										value="${perf.stock.purchasePrice * sessionScope.fxRate }" />  &#8377;								
								</c:if></td>
							<td><c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
								${perf.lastPrice} $								
								</c:if> <c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
								$<fmt:formatNumber maxFractionDigits="2"
										groupingUsed="true"
										value="${perf.lastPrice / sessionScope.fxRate }" />/ ${perf.lastPrice}    &#8377;
																
							</c:if></td>
							<td>${perf.change}</td>

							<td>${perf.stock.shareQuantity}</td>

							<td>${perf.costBasis}</td>

							<td><c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
									${perf.marketValue}
									</c:if>
									<c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
									<fmt:formatNumber maxFractionDigits="2"
										groupingUsed="true"
										value="${perf.stock.shareQuantity *( perf.lastPrice / sessionScope.fxRate) }" />
									</c:if>
									</td>

							<td><c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
									${perf.gain}
									</c:if>
									<c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
									<fmt:formatNumber maxFractionDigits="2"
										groupingUsed="true"
										value="${(perf.stock.shareQuantity *( perf.lastPrice / sessionScope.fxRate) ) - (perf.costBasis)}" />
									</c:if>
							</td>

							<td>
							<c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
									${perf.gainPercentage}
									</c:if>
									<c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
									<fmt:formatNumber maxFractionDigits="2"
										groupingUsed="true"
										value="${(((perf.stock.shareQuantity *( perf.lastPrice / sessionScope.fxRate) ) - (perf.costBasis)) * 100)/(perf.costBasis)}" />
									</c:if>%
							</td>

							<!-- <td>${perf.daysGain}</td> -->

							<td><c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 1}">
									${perf.gainPercentage}
									</c:if>
									<c:if
									test="${perf.stock.stockExchangeType.stockExchangeId == 2}">
									<fmt:formatNumber maxFractionDigits="2"
										groupingUsed="true"
										value="${(((perf.stock.shareQuantity *( perf.lastPrice / sessionScope.fxRate) ) - (perf.costBasis)) * 100)/(perf.costBasis)}" />
									</c:if>%</td>

						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
		<br /> <br />
		<table border="0">
		<tr>
		<td>
		<form action="${pageContext.request.contextPath}/addCash.htm"
			style="width: 350px; margin-top: 5px; margin-bottom: 5px; margin-left: 10px; margin-right: 10px;display: block;">
			<div style="padding: 10px;">
				<h3 align="center" style="margin-bottom: 10px;">CA$H TRAN$ACTION</h3>
				<label>Amount (in US $):</label> <input type="text" name="cashAmount"></input>
				<br/>
				<label>Transaction Type:</label>
				<input type="radio" name="withdraw" value="false" checked="checked"><label
					for="false">Deposit</label></input> <input type="radio" name="withdraw"
					value="true"><label for="true">Withdraw</label></input>
									
				<input type="hidden" value="${portfolio.portfolioId}"
					name="portfolioId" /> <br/><input type="submit" value="Submit" style="text-align: center;display: block;"></input>					
			</div>
			<%-- <input type="hidden" value="false" name="withdraw">
			
				<h3><br>
					<label>Withdraw Cash:</label> <input type="text" name="cashAmount"></input>
					<input type="submit" value="Withdraw"></input><br><br>
				</h3>
				<input type="hidden" value="${portfolio.portfolioId}"
					name="portfolioId" />
				<input type="hidden" value="true" name="withdraw"> --%>
		</form>
		</td>
		<td>
			<div style="padding: 10px; width: 350px; margin-top: 5px; 
				margin-bottom: 5px; margin-left: 10px; margin-right: 10px; 
				display: block;">
						<h3 align="center" style="margin-bottom: 10px;">$TOCK TRAN$ACTION</h3>
						<form
				action="${pageContext.request.contextPath}/BuyShare.htm?portfolioId=${portfolio.portfolioId}">
				<input type="submit" value="BuyShare"></input> <input type="hidden"
					value="${portfolio.portfolioId}" name="portfolioId" />
			</form>

			<form
				action="${pageContext.request.contextPath}/SellShare.htm?portfolioId=${portfolio.portfolioId}">
				<input type="submit" value="SellShare"></input> <input type="hidden"
					value="${portfolio.portfolioId}" name="portfolioId" />
			</form>
					
			<form
				action="${pageContext.request.contextPath}/ExportPortfolio.htm?portfolioId=${portfolio.portfolioId}&">
				<input type="submit" value="Export Portfolio"></input> <input type="hidden"
					value="${portfolio.portfolioId}" name="portfolioId" />
			</form>
			
			<form
				action="${pageContext.request.contextPath}/optimizePortfolio.htm?portfolioId=${portfolio.portfolioId}&">
				<input type="submit" value="Optimize Portfolio"></input> <input type="hidden"
					value="${portfolio.portfolioId}" name="portfolioId" />
			</form>	
			</div>
			</td>
				
					
					
				
		</tr>		
		</table>		
		</div>


</body>
</html>

