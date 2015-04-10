<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/artdialog.jsp" %>
<script type="text/javascript" src="${path}/plugs/My97DatePicker/WdatePicker.js"></script>
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
		var name = $("#name").val();
		if(name==""||name==undefined){
			warning("姓名不能为空！");
			return false;
		}
		var idcard = $("#idcard").val();
		if(idcard==""||idcard==undefined){
			warning("身份证号码不能为空！");
			return false;
		}
		var payable = $("#payable").val();
		if(payable==""||payable==undefined){
			warning("应交学费不能为空！");
			return false;
		}
		var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
		if(!reg.test(payable)&&payable!=0){
			warning("应交学费请输入数字!");
			return false;
		}
		var newOrOld = $("#newOrOld").val();
		if(newOrOld=="旧"){
			var createTime = $("#createTime").val();
			if(createTime==""){
				warning("注册日期不能为空!");
				return false;
			}
		}
		var note = $("#note").val();
		if(note.length>300){
			warning("备注长度不能超过300！");
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
		}
	});
	function changeNewOrOld(obj){
		if(obj.value=='新'){
			$("#createTime_c").css("display","none");
		}else{
			$("#createTime_c").css("display","");
		}
	}
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/train/addTrain.do" method="post">
 		<div class="form_rowelem"> 
	        <label class="form_label"><font color="red" >*</font>姓名：</label>
	        <input type="text" name="formMap[name]" value="${trainMap.name }" id="name" size="20" maxlength="20" class="maxlength_20 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red" >*</font>身份证号码：</label>
	        <input type="text" name="formMap[idcard]"  value="${trainMap.idcard }" id="idcard" size="20" maxlength="25" class="maxlength_25 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red" >*</font>应交学费：</label>
	        <input type="text" name="formMap[payable]"  value="${trainMap.payable }" id="payable" size="20" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <c:if test="${oldTrainInfoMap.value==1 }">
		    <div class="form_rowelem"> 
		        <label class="form_label"><font color="red" >*</font>新/旧：</label>
		        	<select class="form_select"  id="newOrOld" name="formMap[newOrOld]" onchange="changeNewOrOld(this)">
	                     <option value="新" <c:if test="${trainMap.newOrOld=='新'||trainMap.newOrOld=='' }">selected="selected"</c:if>  >  新  </option>
	                     <option value="旧" <c:if test="${trainMap.newOrOld=='旧'}">selected="selected"</c:if> > 旧  </option>
	                </select>
		    </div> 
		    <div class="form_rowelem" id="createTime_c" style="display:none"> 
		        <label class="form_label"><font color="red" >*</font>注册日期</label>
		        	<input type="text" class="form_input_text" size="30" name="formMap[createTime]" value="${trainMap.createTime }" id="createTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
		    </div>
	    </c:if>
	    <c:if test="${oldTrainInfoMap==null||oldTrainInfoMap.value ==0}">
	    	<input type="hidden" name="formMap[newOrOld]" value="新"/>
	    </c:if>
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red" >*</font>本校/挂靠：</label>
	        <select class="form_select"  id="type" name="formMap[type]">
                  <option value="本校" selected="selected">  本校  </option>
                  <option value="挂靠" <c:if test="${trainMap.type=='挂靠'}">selected="selected"</c:if> > 挂靠  </option>
            </select>
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red" >*</font>C1/C2：</label>
	        <select class="form_select"  id="licenseTag" name="formMap[licenseTag]">
                  <option value="C1" selected="selected"> C1  </option>
                  <option value="C2" <c:if test="${trainMap.licenseTag=='C2'}">selected="selected"</c:if>> C2  </option>
            </select>
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">备注：</label>
	        <textarea name="formMap[note]" id="note" cols="45" rows="8">${trainMap.note }</textarea>
	    </div> 
	    <input type="hidden" name="formMap[createUser]" value="${user.id }"/>
	</form>

</div>
  	<div class="enter">
		<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="保存"  onclick="toSave()">保存</button> </span>&nbsp;&nbsp;
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>