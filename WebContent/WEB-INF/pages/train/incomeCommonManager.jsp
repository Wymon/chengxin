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
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("添加成功！");
			art.dialog.close();
			return;
		}
		if(msg!=""&&msg!=null){
			error(msg);
		}
	});
	function toAdd(){
		art.dialog.open('${path}/train/toAddIncomeCommon.do',
				{title:"添加",
				width:'600px',
				height:'350px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true,
				close:function(){
					$("#form1").submit();
				}
			});
	}
	function toExportIncomeCommonReport(){
		art.dialog.open('${path}/trainReport/toExportIncomeCommonReport.do',
				{title:"导出公共收入",
				width:'500px',
				height:'200px',
				background:'white',
				opacity: 0.6,
				lock:true,
				esc:true,
				fixed: true
		});
	}
	function toSubmit(){
		$("#searchform").submit();
	}
	function toPrint(id){
		window.open("${path}/train/toIncomeCommonPrint.do?id="+id);
	}
</script>
</head>
<body>
<div class="form_rowelem"> 
	<form action="${path}/train/toIncomeCommonManager.do" method="post" id="searchform">
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
 	<form id="form1" action="${path }/train/toIncomeCommonManager.do" method="post">
 		<table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
        <tr class="brown" >
            <td>项目</td>
            <td>金额</td>
            <td>操作人</td>
            <td>操作时间 </td>
            <td>备注 </td>
            <td>审核人</td>
            <td>审核 </td>
            <td>操作</td>
         </tr>
         <c:forEach items="${pageList.list}" var="map">
			<tr >
				<td >
					<c:if test="${map.type=='其它'}">
	    				${map.type}<c:if test="${fn:length(map.otherType)>5}"><span title="${map.otherType}">(${fn:substring(map.otherType,0,5)}...)</span></c:if><c:if test="${fn:length(map.otherType)<=5}">(${map.otherType})</c:if>)
	    			</c:if>
	    			<c:if test="${map.type!='其它'}">
	    				${map.type}
	    			</c:if>
			    </td>
				<td >${map.income}</td>
				<td >${map.createUserName}</td>
				<td><fmt:formatDate value="${map.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
			        <c:if test="${fn:length(map.note)>5}">
			      	 <span title="${map.note }">${fn:substring(map.note,0,5)}....</span>
			    	</c:if>
				    <c:if test="${fn:length(map.note)<=5}">
				           ${map.note}
				    </c:if>
		       
		        </td>
		        <td>${map.auditUserName }</td>
				<td>
		        	<c:if test="${map.status ==0 }">待确认</c:if>
	        		<c:if test="${map.status ==1 }"><font color="red">是</font></c:if>
	        		<c:if test="${map.status ==2 }"><font color="blue">否</font></c:if>
				</td>
				<td>
					<pe:permission code="${menuCodeMap.addIncomeCommon }">
						<a href="#" onclick="toPrint(${map.id})">打印</a>
					</pe:permission>
				</td>
			</tr>
			
		</c:forEach>
        </table>
        <c:if test="${fn:length(pageList.list) ==0}">
			<div style="text-align:center" >
				<strong class="red" >没有找到数据!</strong>
			</div>
		</c:if>
		<c:if test="${fn:length(pageList.list) !=0}"> 
			<div class="form_rowelem">
					
				<div class="table_page">
					<pe:permission code="${menuCodeMap.incomeExport }">
						<span class="form_button_btn" style="foalt:left;margin-top:1px; margin-left:10px;">
							<input type="button" onclick="toExportIncomeCommonReport()" class="form_button_btn_in" value="导出"/>
						</span>
					</pe:permission>
					<pe:permission code="${menuCodeMap.statistics }">
						<span class="form_button_btn" style="foalt:left;margin-top:1px; margin-left:10px;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" >合计:${allCount }</font>
						</span>
					</pe:permission>
					<c:import url="/common/plugs/pagePost.jsp">
						<c:param name="actionName">${path }/train/toIncomeCommonManager.do?1=1&formMap[type]=${queryMap.type }&formMap[createUserName]=${queryMap.createUserName }&formMap[note]=${queryMap.note }&formMap[beginTime]=${queryMap.beginTime }&formMap[endTime]=${queryMap.endTime }</c:param>
					</c:import>
				</div>
			</div> 
		</c:if>
		<br/>
	</form>

</div>
	<br />
  	<div class="enter">
	  	<pe:permission code="${menuCodeMap.addIncomeCommon }">
	  		<span class="form_button_btn"><button type="button" onclick="toAdd()"
					class="form_button_btn_in" value="添加">添加</button> </span>
		</pe:permission>
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>