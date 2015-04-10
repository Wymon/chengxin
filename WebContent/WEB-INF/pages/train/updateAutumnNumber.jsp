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
		var autumnNumber = $("#autumnNumber").val();
		if(autumnNumber==""){
			warning("立秋编号不能为空!");
			return false;
		}
		return true;
	}
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			art.dialog.close();
			return;
		}
		if(msg!=""&&msg!=null){
			error(msg);
			art.dialog.close();
		}
	});
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/trainBack/updateAutumnNumber.do" method="post">
 		<div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">姓名：</span>
			<span class="form_span_view">${train.name}</span>
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red">*</font>立秋编号：</label>
	        <input type="text" name="formMap[autumnNumber]" value="${trainMap.autumnNumber }" id="autumnNumber" size="20" maxlength="20" class="maxlength_20 form_input_text" style="width: 200px;" />
	    </div> 
	   <div class="form_rowelem"> 
	        <label class="form_label">修改原因：</label>
	        <textarea name="formMap[updateNote]" id="updateNote" cols="45" rows="8">${incomeMap.updateNote }</textarea>
	    </div> 
	    <input type="hidden" name="formMap[backId]" value="${trainMap.id }"/>
	    <input type="hidden" name="formMap[trainId]" value="${trainMap.trainId }"/>
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