<html>
<head>
body {
	background-color : lightblue;
}
</head>
<script>

</script>
<body>

	<h1>Username: ${investorProfileVO.username }</h1>
	<h1>First Name: ${investorProfileVO.firstName }</h1>
	<h1>Last Name: ${investorProfileVO.lastName }</h1>
	<h1>Investor ID: ${investorProfileVO.investorId }</h1>




	<h1>Username: ${sessionScope.investorProfileVO.username }</h1>
	<h1>First Name: ${sessionScope.investorProfileVO.firstName }</h1>
	<h1>Last Name: ${sessionScope.investorProfileVO.lastName }</h1>
	<h1>Investor ID: ${sessionScope.investorProfileVO.investorId }</h1>

</body>
</html>