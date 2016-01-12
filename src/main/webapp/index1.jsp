<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
	function sub() {
		document.myform.action = "lee/main/"
				+ document.getElementById("msg").value;
		document.myform.submit();
	}
</script>
</head>
<body>
	<form id="myform" name="myform" action="test/test" method="post">
		<textarea id="msg" name="msg" rows="10" cols="100"></textarea>
		<input type="submit"  value="提交">
	</form>
</body>
</html>