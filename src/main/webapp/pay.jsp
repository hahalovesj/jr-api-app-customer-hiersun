<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>支付页面</title>
</head>
<body>
 

	<form action="api/test" method="post">
		支付方式 
		<select name = "payType">
			<option value="1">微信</option>
			<option value="2">支付宝</option>
		</select>
		订单编号<input type="text" name="orderNo">
		<input type="submit" value="提交">
	</form>


</body>
</html>
