<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>诚信驾校财务管理系统</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/artdialog.jsp"%>
<link type="text/css" rel="stylesheet" href="${path }/style/css/index.css" />
</head>
<body>
	<div id="standardLogon" style="visibility: visible; zoom: 1; opacity: 1;">
		<form name="LoginForm" method="post" action="${path }/login/index.do" >
		<input type="hidden" name="url" value="">
		
		<input type="hidden" name="loginType" value="defaultAuthType">
		<input type="hidden" name="initUrl" value="">
			<div class="logonPanel">
				<label>用户名</label><div class="eleWrapper"><input type="text" name="userName" value="" class="textfield" id="userName"></div>
				<label>密码</label><div class="eleWrapper"><input type="password" name="password" value="" class="textfield"></div>
				
			</div>
			<div id="logonSubmit" class="logonSubmit"><input type="submit" value="登录"></div>
		</form>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var msg = "${error}";
	if(msg!=""){
		error(msg);
	}
});

</script>
</html>

