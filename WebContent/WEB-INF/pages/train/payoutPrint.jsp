<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诚信驾校财务管理系统</title>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.info_form{border-collapse: collapse;border: 1px solid #000; line-height:16px;}
.info_form tr td{height:28px;border: 1px solid #000; text-align:center; vertical-align:middle; font-size:12px; line-height:16px;}
.noborder tr th{border:none;font-size:12px; font-weight:bold; padding:0 5px; line-height:16px;}
.noborder tr td{border:none;font-size:12px; padding:0 5px; line-height:16px;}
</style>
<style type="text/css">
@media print{
.no_print{display: none;}
}
</style>

</head>
<body>
	<table class="information_form" width="620" border="1px" cellspacing="0" cellpadding="0" align="center" style="width:620px;">
	  	<tr>
		    <th colspan="4">台山市诚信机动车驾驶培训有限公司<br />
		    支出凭条(无盖财务章无效)
			</th>
		</tr>
		<tr>
			<td >开单日期</td>
			<td><fmt:formatDate value="${printMap.printDate }" pattern="yyyy-MM-dd HH:mm"/></td>
			<td>立秋编号</td>
			<td>${printMap.autumnnumber }</td>
		</tr>
		<tr>
			<td>姓名</td>
			<td>${printMap.name }</td>
			<td>身份证</td>
			<td>${printMap.idcard }</td>
		</tr>
		
		<tr>
			<td>
				项目
			</td>
			<td>
				<c:if test="${printMap.type!='其它' }">
					${printMap.type }
				</c:if>
				<c:if test="${printMap.type=='其它' }">
					${printMap.type}(${printMap.otherType})
				</c:if>
			</td>
			<td>该次支出</td>
			<td>${printMap.payout }</td>
		</tr>
		<tr>
			<td>开单人</td>
			<td>${printMap.createUserName}</td>
			<td>操作时间</td>
			<td><fmt:formatDate value="${printMap.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
			
		</tr>
		<tr>
			<td>经手人</td>
			<td>${printMap.auditUserName}</td>
			<td>经手时间</td>
			<td><fmt:formatDate value="${printMap.auditTime }" pattern="yyyy-MM-dd HH:mm"/></td>
			
		</tr>
		
		
		<tr>
   		 	<td colspan="4">
		        <div class="enter no_print">
		            <!-- <span class="form_input_button_btn"><input type="button" class="form_input_button_btn_in" value="导出" /></span> -->
		            <span class="form_input_button_btn"><input type="button" class="form_input_button_btn_in" value="打印" onclick="window.print();"/></span>
		        </div>
   			</td>
  		</tr>
	</table>
</body>

</html>