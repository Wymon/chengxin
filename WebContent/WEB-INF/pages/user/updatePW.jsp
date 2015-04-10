<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/artdialog.jsp" %>
<script type="text/javascript">
	function closeWin(){
		art.dialog.close();
	}
	function toSave(){
		if(checked()){
			$("#form1").submit();
		}
	}
	function checked(){
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPassword2 = $("#newPassword2").val();
		
		if(newPassword==""){
			warning("新密码不能为空!");
			return false;
		}
		if(oldPassword==newPassword){
			warning("新密码不能和旧密码一样!");
			return false;
		}
		if(newPassword2!=newPassword){
			warning("二次输入的新密码不一样!");
			return false;
		}
		return true;
	}
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("修改成功！");
			art.dialog.close();
			return;
		}
		if(msg!=""&&msg!=null){
			error(msg);
		}
	});
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/user/updatePW.do" method="post">
 		<div class="form_rowelem"> 
	        <label class="form_label">原密码：</label>
	        <input type="password" name="oldPassword" id="oldPassword" size="20" value="${oldPassword }" maxlength="20" class="maxlength_20 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">新密码：</label>
	        <input type="password" name="newPassword" id="newPassword" size="20" value="${newPassword }" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">再次输入密码：</label>
	        <input type="password" name="newPassword2" id="newPassword2" size="20" value="${newPassword }" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <input type="hidden" name="id" value="${user.id }"/>
	</form>

</div>
	<br />
  	<div class="enter">
		<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="保存"  onclick="toSave()">保存</button> </span>&nbsp;&nbsp;
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>