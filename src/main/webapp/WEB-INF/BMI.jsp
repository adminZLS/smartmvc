<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form action="BMI.do">
		<fieldset>
			<legend>健康指数</legend>
			身高(米)：<input name="height">
			体重(千克):<input name="weiht">
			性别：<input type="radio" name="sex" value="m">男<input type="radio" name="sex" value="w">女
			<input type="submit" value="确定">
		</fieldset>
	</form>
</body>
</html>