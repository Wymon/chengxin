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
	function toUpdateTrain(id){
		art.dialog.open('${path}/trainBack/toUpdateTrain.do?id='+id,
				{title:"修改学员",
				width:'600px',
				height:'540px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true,
				close:function(){
					$("#form1").submit();
			}});
	}
	function toUpdatePaying(id){
		art.dialog.open('${path}/trainBack/toUpdatePaying.do?id='+id,
				{title:"修改缴费",
				width:'600px',
				height:'300px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true,
				close:function(){
					$("#form1").submit();
			}});
	}
	function toUpdateIncome(id){
		art.dialog.open('${path}/trainBack/toUpdateIncome.do?id='+id,
				{title:"修改收入",
				width:'600px',
				height:'350px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true,
				close:function(){
					$("#form1").submit();
			}});
	}
	function toUpdatePayout(id){
		art.dialog.open('${path}/trainBack/toUpdatePayout.do?id='+id,
				{title:"修改支出",
				width:'600px',
				height:'350px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true,
				close:function(){
					$("#form1").submit();
			}});
	}
	function toUpdateAudit(id,url){
		art.dialog.open('${path}/trainBack/'+url+'.do?id='+id,
				{title:"审核",
				width:'700px',
				height:'450px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true,
				close:function(){
					$("#form1").submit();
			}});
	}
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/trainBack/toUpdate.do" method="post">
 		<input type="hidden" name="id" value = "${id }"/>
 		 <div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">学员信息：</span>
		  		
		  </div> 
 		 <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
 		 		<tr class="brown" >
		            <td>立秋编号</td>
		            <td>姓名</td>
		            <td>身份证号码</td>
		            <td>应交学费 </td>
		            <td>新/旧</td>
		            <td>本校/挂靠</td>
		            <td>C1/C2</td>
		            <td>备注</td>
		            <td>注册人</td>
		            <td>注册日期</td>
            		<td>操作</td>
		         </tr>
		         <tr >
					<td >${trainMap.autumnNumber }</td>
					<td >${trainMap.name }</td>
					<td>${trainMap.idcard }</td>
					<td>${trainMap.payable }</td>
					<td>${trainMap.newOrOld }</td>
					<td>${trainMap.type }</td>
					<td>${trainMap.licenseTag }</td>
					<td>
						<c:if test="${fn:length(trainMap.note)>10}">
					       <span title="${trainMap.note }">${fn:substring(trainMap.note,0,10)}....</span>
					    </c:if>
					    <c:if test="${fn:length(trainMap.note)<=10}">
					           ${trainMap.note}
					    </c:if>
					</td>
					<td>
						${trainMap.createUserName }
					</td>
					<td>
						<fmt:formatDate value="${trainMap.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<pe:permission code="${menuCodeMap.updateTrain }">
							<c:if test="${trainMap.backId==null }">
								<a href="#" onclick="toUpdateTrain(${trainMap.id})">修改</a> &nbsp;
							</c:if>
						</pe:permission>
						<pe:permission code="${menuCodeMap.auditTrain }">
							<c:if test="${trainMap.backId>0&&trainMap.bcreateUser!=user.id }">
								<a href="#" onclick="toUpdateAudit(${trainMap.id},'toUpdateAuditTrain.do')" style="color:red">审核</a> &nbsp;
							</c:if>
						</pe:permission>
						
					</td>
				</tr>
 		 </table>
 		 
	    <c:if test="${fn:length(payingMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">缴费：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>已交</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>审核状态</td>
		            <td>操作</td>
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
				        	<pe:permission code="${menuCodeMap.updatePaying }">
					        	<c:if test="${paying.backId == null || paying.updateStatus!=0}">
					        		<a href="#" onclick="toUpdatePaying(${paying.id})">修改</a> &nbsp;
					        	</c:if>
				        	</pe:permission>
				        	<pe:permission code="${menuCodeMap.auditPaying }">
				        		<c:if test="${paying.backId >0 && paying.bcreateUser!=user.id &&paying.updateStatus==0}">
					        		<a href="#" onclick="toUpdateAudit(${paying.id},'toUpdateAuditPaying')" style="color:red">审核</a> &nbsp;
					        	</c:if>
				        	</pe:permission>
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	    <c:if test="${fn:length(incomeMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">收入：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		            <td>审核状态</td>
		            <td>操作</td>
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
				        <td>
				        	<c:if test="${income.status ==0 }">待确认</c:if>
			        		<c:if test="${income.status ==1 }"><font color="red">是</font></c:if>
			        		<c:if test="${income.status ==2 }"><font color="blue">否</font></c:if>
						</td>
				        <td>
				        	<pe:permission code="${menuCodeMap.updateIncome }">
					        	<c:if test="${income.backId == null || income.updateStatus!=0}">
				        			<a href="#" onclick="toUpdateIncome(${income.id})">修改</a> &nbsp;
				        		</c:if>
			        		</pe:permission>
			        		<pe:permission code="${menuCodeMap.updateAuditIncome }">
				        		<c:if test="${income.backId >0 &&income.bcreateUser!=user.id&& income.updateStatus==0}">
				        			<a href="#" onclick="toUpdateAudit(${income.id},'toUpdateAuditIncome')" style="color:red">审核</a> &nbsp;
				        		</c:if>
			        		</pe:permission>
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	    <c:if test="${fn:length(payoutMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">支出：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		            <td>审核状态</td>
		            <td>操作</td>
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
				        <td>
				        	<c:if test="${payout.status ==0 }">待确认</c:if>
			        		<c:if test="${payout.status ==1 }"><font color="red">是</font></c:if>
			        		<c:if test="${payout.status ==2 }"><font color="blue">否</font></c:if>
						</td>
				        <td>
				        	<pe:permission code="${menuCodeMap.updatePayout }">
					        	<c:if test="${payout.backId == null || payout.updateStatus!=0}">
				        			<a href="#" onclick="toUpdatePayout(${payout.id})">修改</a> &nbsp;
				        		</c:if>
			        		</pe:permission>
			        		<pe:permission code="${menuCodeMap.auditPaying }">
				        		<c:if test="${payout.backId >0 &&payout.bcreateUser!=user.id&& payout.updateStatus==0}">
				        			<a href="#" onclick="toUpdateAudit(${payout.id},'toUpdateAuditPayout')" style="color:red">审核</a> &nbsp;
				        		</c:if>
			        		</pe:permission>
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