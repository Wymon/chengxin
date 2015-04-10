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
		$("#form1").submit();
	}
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("保存成功！");
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
 	<form id="form1" action="${path }/user/saveCanAddOldTrain.do" method="post">
 		<div class="form_rowelem"> 
	        <label class="form_label">录入历史学员：</label>
	        <select id="type" class="form_select"  name="formMap[value]" onchange="changeType(this)">
                 	<option value="1" <c:if test="${canAddOldTrainMap.value==1 }">selected="selected"</c:if>>是</option>
                 	<option value="0" <c:if test="${canAddOldTrainMap.value==0 }">selected="selected"</c:if>>否</option>
            </select>
            
	    </div> 
	    <input type="hidden" name="formMap[name]" value="录入历史学员"/>
	    <input type="hidden" name="formMap[code]" value="canAddOldTrain"/>
	    <input type="hidden" name="formMap[createUser]" value="${user.id }"/>
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