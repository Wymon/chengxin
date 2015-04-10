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
		return true;
	}
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("保存成功,请打印单据与财务核对！");
			//art.dialog.close();
			return;
		}
		if(msg!=""&&msg!=null){
			error(msg);
		}
	});
	function toPrint(id){
		window.open("${path}/train/toPayingPrint.do?id="+id);
	}
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/train/addPaying.do" method="post">
 		<div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">姓名：</span>
			<span class="form_span_view">${trainMap.name}</span>
	    </div> 
	    <div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">应总交学费：</span>
			<span class="form_span_view">${trainMap.payable+trainMap.allincome}</span>
	    </div> 
	    <div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">已交：</span>
			<span class="form_span_view">${trainMap.allpaying}</span>
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red">*</font>缴费：</label>
	        <input type="text" name="formMap[paying]" id="paying" size="20" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <input type="hidden" name="formMap[trainId]" value="${trainMap.id }"/>
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
	<br />
	<c:if test="${fn:length(payingMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">历史记录：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>已交</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>是否确认</td>
		            <td >操作</td>
		         </tr>
			    <c:forEach items="${payingMapList}" var="paying" varStatus="varStatus">
			    	<tr>
				    	<td>${paying.paying }</td>
				        <td>${paying.createUserName }</td>
				        <td><fmt:formatDate value="${paying.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				       
				        <td>
				        	<c:if test="${paying.status ==0 }">待确认</c:if>
			        		<c:if test="${paying.status ==1 }"><font color="red">是</font></c:if>
			        		<c:if test="${paying.status ==2 }"><font color="blue">否</font></c:if>
				        </td>
				        <td>
				        	<a href="#" onclick="toPrint(${paying.id})">打印单据</a>
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>

</body>

</html>