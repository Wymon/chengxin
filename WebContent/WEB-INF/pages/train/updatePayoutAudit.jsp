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
		//组装各数据
		//已交
		var payoutCommon = "";
		for(var i = 0 ; i < 100;i++){
			var p = document.getElementsByName("payoutCommon"+i);
			for(var j = 0 ; j < p.length;j++){
				var k = p[j];
				if(k.checked){
					payoutCommon +=k.value+";";
				}
			}
		}
		if(payoutCommon!=""){
			payoutCommon = payoutCommon.substring(0,payoutCommon.length-1);
		}
		document.getElementsByName("formMap[payoutCommon]")[0].value = payoutCommon;
		var incomeCommon = "";
		for(var i = 0 ; i < 100;i++){
			var p = document.getElementsByName("incomeCommon"+i);
			for(var j = 0 ; j < p.length;j++){
				var k = p[j];
				if(k.checked){
					incomeCommon +=k.value+";";
				}
			}
		}
		if(incomeCommon!=""){
			incomeCommon = incomeCommon.substring(0,incomeCommon.length-1);
		}
		document.getElementsByName("formMap[incomeCommon]")[0].value = incomeCommon;
		art.dialog.through({
			title:"提示",
			icon:"warning",
			content:"一但确定将不可修改，请确认信息无误！谢谢！",
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
 	<form id="form1" action="${path }/train/updatePayoutCommonAudit.do" method="post">
 		
	    <c:if test="${fn:length(auditList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">公共支出：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		        	<td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${auditList}" var="payoutCommon" varStatus="varStatus">
			    	<tr>
			    		<td>
			    			${payoutCommon.type }
			    			<c:if test="${payoutCommon.type=='其它' }">
			    				(
				    			<c:if test="${fn:length(payoutCommon.otherType)>5}">
						      	 <span title="${payoutCommon.otherType }">${fn:substring(payoutCommon.otherType,0,5)}...</span>
						    	</c:if>
							    <c:if test="${fn:length(payoutCommon.otherType)<=5}">
							           ${payoutCommon.otherType}
							    </c:if>)
			    			</c:if>
			    		</td>
				    	<td>${payoutCommon.payout }</td>
				        <td>${payoutCommon.createUserName }</td>
				        <td><fmt:formatDate value="${payoutCommon.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
					        <c:if test="${fn:length(payoutCommon.note)>5}">
					      	 <span title="${payoutCommon.note }">${fn:substring(payoutCommon.note,0,5)}....</span>
					    	</c:if>
						    <c:if test="${fn:length(payoutCommon.note)<=5}">
						           ${payoutCommon.note}
						    </c:if>
				       
				        </td>
				        <td>
			        		<span class="form_rowelem_label">是</span>
			        		<input type="radio" class="form_input_radio"  value="${payoutCommon.id }_1" name="payoutCommon${varStatus.index }" checked="checked"/>
			        		<span class="form_rowelem_label">否</span>
			        		<input type="radio" class="form_input_radio"  value="${payoutCommon.id }_2" name="payoutCommon${varStatus.index }"  />
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	     <c:if test="${fn:length(incomeList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">公共收入：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		        	<td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${incomeList}" var="incomeCommon" varStatus="varStatus">
			    	<tr>
			    		<td>
			    			${incomeCommon.type }
			    			<c:if test="${incomeCommon.type=='其它' }">
			    				(
				    			<c:if test="${fn:length(incomeCommon.otherType)>5}">
						      	 <span title="${incomeCommon.otherType }">${fn:substring(incomeCommon.otherType,0,5)}...</span>
						    	</c:if>
							    <c:if test="${fn:length(incomeCommon.otherType)<=5}">
							           ${incomeCommon.otherType}
							    </c:if>)
			    			</c:if>
			    		</td>
				    	<td>${incomeCommon.income }</td>
				        <td>${incomeCommon.createUserName }</td>
				        <td><fmt:formatDate value="${incomeCommon.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
					        <c:if test="${fn:length(incomeCommon.note)>5}">
					      	 <span title="${incomeCommon.note }">${fn:substring(incomeCommon.note,0,5)}....</span>
					    	</c:if>
						    <c:if test="${fn:length(incomeCommon.note)<=5}">
						           ${incomeCommon.note}
						    </c:if>
				       
				        </td>
				        <td>
			        		<span class="form_rowelem_label">是</span>
			        		<input type="radio" class="form_input_radio"  value="${incomeCommon.id }_1" name="incomeCommon${varStatus.index }" checked="checked"/>
			        		<span class="form_rowelem_label">否</span>
			        		<input type="radio" class="form_input_radio"  value="${incomeCommon.id }_2" name="incomeCommon${varStatus.index }"  />
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	    <input type="hidden" name="formMap[payoutCommon]" value=""/>
	    <input type="hidden" name="formMap[incomeCommon]" value=""/>
	    <input type="hidden" name="formMap[auditUser]" value="${user.id }"/>
	</form>

</div>
	<br />
  	<div class="enter">
  		<c:if test="${fn:length(auditList) !=0||fn:length(incomeList) !=0}"> 
			<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="确认批审"  onclick="toSave()">确认批审</button> </span>&nbsp;&nbsp;
		</c:if>
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>