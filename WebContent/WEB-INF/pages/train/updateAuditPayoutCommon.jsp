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
		var note = $("#note").val();
		if(note.length>300){
			warning("备注长度不能超过300！");
			return false;
		}
		$("#form1").submit();
	}
	
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("保存成功");
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
 	<form id="form1" action="${path }/trainBack/updateAuditPayoutCommon.do" method="post">
 		<input type="hidden" name="id" value = "${payoutCommonMap.id }"/>
 		 <div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">修改前信息：</span>
		  </div> 
 		 <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
 		 		<tr class="brown" >
 		 			<td>收入项目</td>
		            <td>金额</td>
		            <td>备注</td>
		         </tr>
		         <tr >
		         	<td>${payoutCommonMap.type }
		         		<c:if test="${payoutCommonMap.type=='其它' }">
			    				(
				    			<c:if test="${fn:length(payoutCommonMap.otherType)>5}">
						      	 <span title="${payoutCommonMap.otherType }">${fn:substring(payoutCommonMap.otherType,0,5)}...</span>
						    	</c:if>
							    <c:if test="${fn:length(payoutCommonMap.otherType)<=5}">
							           ${payoutCommonMap.otherType}
							    </c:if>)
			    			</c:if>
		         	</td>
					<td >${payoutCommonMap.payout }</td>
					
					<td>
						 <c:if test="${fn:length(payoutCommonMap.note)>5}">
					      	 <span title="${payoutCommonMap.note }">${fn:substring(payoutCommonMap.note,0,5)}...</span>
					    	</c:if>
						    <c:if test="${fn:length(payoutCommonMap.note)<=5}">
						           ${payoutCommonMap.note}
						    </c:if>
					</td>
				
				</tr>
 		 </table>
 		 <div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">修改后信息：</span>
		  </div> 
 		 <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
 		 		<tr class="brown" >
 		 			<td>收入项目</td>
		            <td>金额</td>
		            <td>备注</td>
		         </tr>
		         <tr >
		         	<td>${payoutCommonBackMap.type }
		         		<c:if test="${payoutCommonBackMap.type=='其它' }">
			    				(
				    			<c:if test="${fn:length(payoutCommonBackMap.otherType)>5}">
						      	 <span title="${payoutCommonBackMap.otherType }">${fn:substring(payoutCommonBackMap.otherType,0,5)}...</span>
						    	</c:if>
							    <c:if test="${fn:length(payoutCommonBackMap.otherType)<=5}">
							           ${payoutCommonBackMap.otherType}
							    </c:if>)
			    			</c:if>
		         	</td>
					<td >${payoutCommonBackMap.payout }</td>
					
					<td>
						 <c:if test="${fn:length(payoutCommonBackMap.note)>5}">
					      	 <span title="${payoutCommonBackMap.note }">${fn:substring(payoutCommonBackMap.note,0,5)}...</span>
					    	</c:if>
						    <c:if test="${fn:length(payoutCommonBackMap.note)<=5}">
						           ${payoutCommonBackMap.note}
						    </c:if>
					</td>
					
				</tr>
 		 </table>
 		 <div class="form_rowelem" style="text-align: left; text-indent: 2em; word-break: break-all;white-space: pre-wrap;font-size:12px; letter-spacing:1px;line-height:180%;">修改意见:${payoutCommonBackMap.updateNote}</div>
 		 <div class="form_rowelem"> 
 		 	<label class="form_label">审核：</label>
	        <span class="form_rowelem_label">通过</span>
		 	<input type="radio" class="form_input_radio"  value="1" name="formMap[updateStatus]" checked="checked"/>
		 	<span class="form_rowelem_label">不通过</span>
		 	<input type="radio" class="form_input_radio"  value="2" name="formMap[updateStatus]"  />
	    </div> 
 		 <div class="form_rowelem"> 
	        <label class="form_label">审核意见：</label>
	        <textarea name="formMap[auditNote]" id="note" cols="45" rows="5"></textarea>
	    </div> 
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