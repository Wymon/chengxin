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
	function toSave(flag){
		$("#flag").val(flag);
		
		if(checked()){
			$("#form1").submit();
		}
	}
	function checked(){
		
		var beginTime = $("#d4321").val();
		if(beginTime==""){
			warning("审核开始日期不能为空!");
			return false;
		}
		var endTime = $("#d4322").val();
		if(endTime==""){
			warning("审核结束日期不能为空!");
			return false;
		}
		return true;
	}
	
	
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/trainReport/exportPayoutCommonReport.do" method="post">
 		      
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red">*</font>审核开始日期：</label>
	        <input type="text" class="form_input_text" size="30" name="formMap[beginTime]" value="${queryMap.beginTime }" id="d4321" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',maxDate:'#F{$dp.$D(\'d4322\');}'})" readonly="readonly" /> 
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red">*</font>审核结束日期：</label>
	        <input type="text" class="form_input_text" size="30" name="formMap[endTime]" value="${queryMap.endTime }" id="d4322" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',minDate:'#F{$dp.$D(\'d4321\');}'})" readonly="readonly" />
	    </div> 
	    <input type="hidden"  name="formMap[status]" value="1" />
	</form>
	
</div>
	<br />
  	<div class="enter">
  		
		<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="确定"  onclick="toSave()">确定</button> </span>&nbsp;&nbsp;
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>

</body>
</html>