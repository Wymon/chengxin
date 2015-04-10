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
	
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/train/toView.do" method="post">
 		<div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">姓名：</span>
			<span class="form_span_view">${trainMap.name}</span>
	    </div> 
	    <div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">应交总费用：</span>
			<span class="form_span_view">${trainMap.payable+trainMap.allincome}</span>
	    </div> 
	    <div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">应交学费：</span>
			<span class="form_span_view">${trainMap.payable}</span>
	    </div> 
	    <div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">已交：</span>
			<span class="form_span_view">${trainMap.allpaying+trainMap.allincome}</span>
	    </div> 
	    <c:if test="${fn:length(payingMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">缴费：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>已交</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>审核人</td>
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${payingMapList}" var="paying" varStatus="varStatus">
			    	<tr>
				    	<td>${paying.paying }</td>
				        <td>${paying.createUserName }</td>
				        <td><fmt:formatDate value="${paying.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>${paying.auditUserName }</td>
				        <td>
				        	<c:if test="${paying.status ==0 }">待确认</c:if>
			        		<c:if test="${paying.status ==1 }"><font color="red">是</font></c:if>
			        		<c:if test="${paying.status ==2 }"><font color="blue">否</font></c:if>
				        </td>
				        
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	    <c:if test="${fn:length(incomeMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">收入项目：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		            <td>审核人</td>
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${incomeMapList}" var="income" varStatus="varStatus">
			    	<tr>
			    		<td>${income.type }
			    			<c:if test="${income.type=='其它' }">
			    				(
				    			<c:if test="${fn:length(income.otherType)>5}">
						      	 <span title="${income.otherType }">${fn:substring(income.otherType,0,5)}...</span>
						    	</c:if>
							    <c:if test="${fn:length(income.otherType)<=5}">
							           ${income.otherType}
							    </c:if>)
			    			</c:if>
			    		</td>
				    	<td>${income.income }</td>
				        <td>${income.createUserName }</td>
				        <td><fmt:formatDate value="${income.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
					        <c:if test="${fn:length(income.note)>5}">
					      	 <span title="${income.note }">${fn:substring(income.note,0,5)}....</span>
					    	</c:if>
						    <c:if test="${fn:length(income.note)<=5}">
						           ${income.note}
						    </c:if>
				       
				        </td>
				        <td>${income.auditUserName }</td>
				        <td>
				        	<c:if test="${income.status ==0 }">待确认</c:if>
			        		<c:if test="${income.status ==1 }"><font color="red">是</font></c:if>
			        		<c:if test="${income.status ==2 }"><font color="blue">否</font></c:if>
			        		
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	    <c:if test="${fn:length(payoutMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">支出项目：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		            <td>审核人</td>
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${payoutMapList}" var="payout" varStatus="varStatus">
			    	<tr>
			    		<td>${payout.type }
			    			<c:if test="${payout.type=='其它' }">
			    				(
				    			<c:if test="${fn:length(payout.otherType)>5}">
						      	 <span title="${payout.otherType }">${fn:substring(payout.otherType,0,5)}...</span>
						    	</c:if>
							    <c:if test="${fn:length(payout.otherType)<=5}">
							           ${payout.otherType}
							    </c:if>)
			    			</c:if>
			    		</td>
				    	<td>${payout.payout }</td>
				        <td>${payout.createUserName }</td>
				        <td><fmt:formatDate value="${payout.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
					        <c:if test="${fn:length(payout.note)>5}">
					      	 <span title="${payout.note }">${fn:substring(payout.note,0,5)}....</span>
					    	</c:if>
						    <c:if test="${fn:length(payout.note)<=5}">
						           ${payout.note}
						    </c:if>
				        </td>
				        <td>${payout.auditUserName}</td>
				        <td>
				        	<c:if test="${payout.status ==0 }">待确认</c:if>
			        		<c:if test="${payout.status ==1 }"><font color="red">是</font></c:if>
			        		<c:if test="${payout.status ==2 }"><font color="blue">否</font></c:if>
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	   
	    <input type="hidden" name="formMap[auditUser]" value="${user.id }"/>
	</form>

</div>
	<br />
  	<div class="enter">
  		
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>