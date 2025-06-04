<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style >
body{
font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
           justify-content: center;
           text-align: center;
}
div{
heigth:200px;
width:200px;
border: soild 1px yellow;
font-size:16px;
padding:25px 50px;
justify-content: center;
align-items: center;
border-color: red;
}
input[type=number]{
padding:5px;
font-size: 16px;
margin-bottom: 15px;
font-weight: bold;

}
input[type=text]{
padding:5px;
font-size: 16px;
margin-bottom: 15px;
font-weight: bold;

}
</style>
</head>
<body>
	<h2>Add Beneficiary</h2>
	
	<form action="addBeneficiaryy" method="post">
	<div>
		Your Account ID: <input type="number" name="account_id" required><br>
		Beneficiary Account ID: <input type="number" name="beneficiary_id"
			required><br> Name:<input type="text" name="name"><br>
		<input type="submit" value="Add Beneficiary">
	</div>
	</form>
</body>
</html>