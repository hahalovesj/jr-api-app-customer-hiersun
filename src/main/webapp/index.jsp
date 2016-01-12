<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form id="myform" name="myform" action="api/main" method="post">
		<textarea id="msg" name="msg" rows="10" cols="100"></textarea>
		<input type="submit" value="提交">
	</form>

	<form action="fileUpload/upload" method="post"
		enctype="multipart/form-data">
		<input type="text" name="a"> <input type="file" name="thefile" />
		<input type="submit" value="上传文件" />
	</form>


</body>
</html>
