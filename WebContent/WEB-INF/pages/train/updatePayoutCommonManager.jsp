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

	
	function toUpdatePayout(id){
		art.dialog.open('${path}/trainBack/toUpdatePayoutCommon.do?id='+id,
				{title:"修改公共支出",
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
				width:'600px',
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
	function toSubmit(){
		$("#searchform").submit();
	}
</script>
</head>
<body>
<div class="form_rowelem"> 
<form action="${path}/trainBack/toUpdatePayoutCommonManager.do" method="post" id="searchform">
        <div id="firstDiv" >
            <div class="form_rowelem">
            	<div class="table_page_search" style="height: 50px">
            		<div class="table_page_center">
                		<span class="form_rowelem_span" style="margin-left:20px;">项目：</span>
                       	<input type="text" class="form_input_text" value="${queryMap.type}" size="20" maxlength="20" id="type" name="formMap[type]"/>
                       	<span class="form_rowelem_span" style="margin-left:20px;">操作人：</span>
                       	<input type="text" class="form_input_text" value="${queryMap.createUserName}" size="20" maxlength="20" id="createUserName" name="formMap[createUserName]"/>
                		<span class="form_rowelem_span" style="margin-left:20px;">备注：</span>
                       	<input type="text" class="form_input_text" value="${queryMap.note}" size="20" maxlength="20" id="note" name="formMap[note]"/>
                	</div>
                	<br/>
                	<div class="table_page_center">

                       	<span class="form_rowelem_span" style="margin-left:20px;">操作开始时间：</span>
                       	<input type="text" class="form_input_text" size="30" name="formMap[beginTime]" value="${queryMap.beginTime }" id="d4321" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',maxDate:'#F{$dp.$D(\'d4322\');}'})" readonly="readonly" />
                       	<span class="form_rowelem_span" style="margin-left:20px;">操作结束时间：</span>
                       	<input type="text" class="form_input_text" size="30" name="formMap[endTime]" value="${queryMap.endTime }" id="d4322" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',minDate:'#F{$dp.$D(\'d4321\');}'})" readonly="readonly" />
                		<span class="form_button_btn" style="margin-top:1px; margin-left:10px;"><input type="button" onclick="toSubmit()" class="form_button_btn_in" value="搜索"></input></span>
                	</div>
                </div>
            </div>
        </div>
    </form>
 	<form id="form1" action="${path }/trainBack/toUpdatePayoutCommonManager.do" method="post">
 		
 		 <c:if test="${fn:length(pageList.list) !=0}"> 
			
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作时间 </td>
		            <td>备注</td>
		            <td>操作</td>
		         </tr>
			    <c:forEach items="${pageList.list}" var="payout" varStatus="varStatus">
			    	<tr>
			    		<td>
			    			<c:if test="${payout.type=='其它'}">
			    				${payout.type}<c:if test="${fn:length(payout.otherType)>5}"><span title="${payout.otherType}">(${fn:substring(payout.otherType,0,5)}...)</span></c:if><c:if test="${fn:length(payout.otherType)<=5}">(${payout.otherType})</c:if>)
			    			</c:if>
			    			<c:if test="${payout.type!='其它'}">
			    				${payout.type}
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
				        
				        	<pe:permission code="${menuCodeMap.updatePayoutCommon }">
				        		<c:if test="${payout.backId == null || payout.updateStatus!=0}">
				        			<a href="#" onclick="toUpdatePayout(${payout.id})">修改</a> &nbsp;
				        		</c:if>
			        		</pe:permission>
			        		<pe:permission code="${menuCodeMap.updateAuditPayoutCommon }">
				        		<c:if test="${payout.backId >0 &&payout.bcreateUser!=user.id&& payout.updateStatus==0}">
				        			<a href="#" onclick="toUpdateAudit(${payout.id},'toUpdateAuditPayoutCommon')" style="color:red">审核</a> &nbsp;
				        		</c:if>
			        		</pe:permission>
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
		<c:if test="${fn:length(pageList.list) ==0}">
			<div style="text-align:center" >
				<strong class="red" >没有找到数据!</strong>
			</div>
		</c:if>
		<c:if test="${fn:length(pageList.list) !=0}"> 
			<div class="form_rowelem">
					
				<div class="table_page">
				
					<c:import url="/common/plugs/pagePost.jsp">
						<c:param name="actionName">${path }/trainBack/toUpdatePayoutCommonManager.do?1=1&formMap[type]=${queryMap.type }&formMap[createUserName]=${queryMap.createUserName }&formMap[note]=${queryMap.note }&formMap[beginTime]=${queryMap.beginTime }&formMap[endTime]=${queryMap.endTime }</c:param>
					</c:import>
				</div>
			</div> 
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