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
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		var email = $("#email").val();
		if(userName==""){
			warning("登录名不能为空!");
			return false;
		}
		if(realName==""){
			warning("姓名不能为空!");
			return false;
		}
		var newPassword = $("#newPassword").val();
		var newPassword2 = $("#newPassword2").val();
		
		if(newPassword==""){
			warning("新密码不能为空!");
			return false;
		}
		
		if(newPassword2!=newPassword){
			warning("二次输入的新密码不一样!");
			return false;
		}
		if(email==""){
			warning("邮箱不能为空!");
			return false;
		}
		var reg = new RegExp("^[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\.[a-z A-Z 0-9 _]+)+(\,[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\.[a-z A-Z 0-9 _]+)+)*$");
		if(!reg.test(email)){
			warning("请输入合法的电子邮箱!");
			return false;
		}
		return true;
	}
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("添加成功！");
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
 	<form id="form1" action="${path }/user/add.do" method="post">
 		<div class="form_rowelem"> 
	        <label class="form_label">登录名：</label>
	        <input type="text" name="formMap[userName]" id="userName" size="20" value="${userMap.userName }" maxlength="20" class="maxlength_20 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">姓名：</label>
	        <input type="text" name="formMap[realName]" id="realName" size="20" value="${userMap.realName }" maxlength="20" class="maxlength_20 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">新密码：</label>
	        <input type="password" name="formMap[password]" value= "${userMap.password }" id="newPassword" size="20" value="${newPassword }" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">再次输入密码：</label>
	        <input type="password" name="newPassword2" id="newPassword2" value= "${userMap.password }" size="20" value="${newPassword }" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">角色：</label>
	        <select id="type" class="form_select"  name="formMap[roleId]">
	        	<c:forEach items="${roleList }" var="role">
	        		<option value="${role.id }" <c:if test="${role.id==userMap.roleId }">selected="selected"</c:if> >${role.name }</option>
	        	</c:forEach>
	        	
             </select>
	        
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">邮箱：</label>
	        <input type="text" name="formMap[email]" id="email" size="20" value="${userMap.email }" maxlength="50" class="maxlength_50 form_input_text" style="width: 200px;" />
	    </div>
	    <div class="form_rowelem"> 
	        <label class="form_label">是否发送报表到邮箱：</label>
	        <span class="form_rowelem_label">是</span>
			<input type="radio" class="form_input_radio"  value="1" name="formMap[canEmail]" <c:if test="${userMap.canEmail==1 }">checked="checked"</c:if> />
			<span class="form_rowelem_label">否</span>
			<input type="radio" class="form_input_radio"  value="0" name="formMap[canEmail]"  <c:if test="${userMap.canEmail==0||userMap.canEmail==null||userMap.canEmail=='' }">checked="checked"</c:if>/>
	    </div>
	    <input type="hidden" name="formMap[id]" value="${userMap.id }"/>
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