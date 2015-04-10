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
			art.dialog.through({
				title:"提示",
				icon:"warning",
				content:"录入信息一旦确定提交将不可修改，请确认录入信息无误！谢谢！",
				background: 'white',
				opacity: 0.6,
				lock:true,
				ok:true,
				fixed: true,
				esc:true,
				width:162,
				height:100,
				ok:function(){
					$("#form1").submit();
				},
				cancel:function(){
					return;
				}
			});
		}
	}
	function checked(){
		var paying = $("#paying").val();
		if(paying==""){
			warning("缴费不能为空!");
			return false;
		}
		var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
		if(!reg.test(paying)&&paying!=0){
			warning("缴费请输入数字!");
		}
		var updateNote = $("#updateNote").val();
		if(updateNote.length>300){
			warning("修改原因长度不能超过300！");
			return false;
		}
		return true;
	}
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("保存成功！待其他人员修改");
			art.dialog.close();
			return;
		}
		if(msg=="succeed2"){
			succeed("修改成功！");
			art.dialog.close();
			return;
		}
		if(msg=="fail"){
			error("修改失败，原因和别人中的数据没有对上，请检查！");
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
 	<form id="form1" action="${path }/trainBack/updatePaying.do" method="post">
 	
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red" >*</font>缴费：</label>
	        <input type="text" name="formMap[paying]" value="${payingMap.paying }" id="paying" size="20" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">修改原因：</label>
	        <textarea name="formMap[updateNote]" id="updateNote" cols="45" rows="8">${payingMap.updateNote }</textarea>
	    </div> 
	    <input type="hidden" name="formMap[updateUser]" value="${user.id }"/>
	    <input type="hidden" name="formMap[payingId]" value="${payingMap.id }"/>
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