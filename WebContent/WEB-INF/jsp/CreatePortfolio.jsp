<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>



th, td {
    padding: 8px;
    text-align: left;
}

tr:hover{background-color:#f5f5f5}

table.center {
    text-align: center;
    color: red;
}
body {
    background-image: url("${pageContext.request.contextPath}/images/a.gif");
}
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath}/SavePortfolio.htm"
		method="get">
		<table align="center">
			<tr>
				<td><label for= "txtPortfolioName">Portfolio Name:</label></td>
				<td><input type="text" id="txtPortfolioName"
					name="portfolioname"></td>
			</tr>

			<tr>
				<td><label for="txtPortfolioDescription">Portfolio
						Description:</label></td>
				<td><input type="text" id="txtPortfolioDescription"
					name="portfoliodescription" /></td>
			</tr>
			<tr>
				
				<td colspan="2"><input type="submit" id="btnSavePortfolio"
					name="createPortfolio" value="Create Portfolio" /></td>
			</tr>

		</table>
	</form>
</body>
</html>