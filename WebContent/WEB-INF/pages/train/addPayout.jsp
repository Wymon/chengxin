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
	function toSave(flag){
		$("#flag").val(flag);
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
		var type = $("#type option:selected").text();
		if(type=="其它"){
			var otherType = $("#otherType").val();
			if(otherType==""){
				warning("其它项目不能为空!");
				return false;
			}
		}
		var payout = $("#payout").val();
		if(payout==""){
			warning("金额不能为空!");
			return false;
		}
		var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
		if(!reg.test(payout)&&payout!=0){
			warning("金额请输入数字!");
			return false;
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
			succeed("保存成功,待财务核对！");
			var flag = "${flag}";
			if(flag == "0"){
				art.dialog.close();
			}
			return;
		}
		if(msg!=""&&msg!=null){
			error(msg);
		}
	});
	function changeType(){
		var type = $("#type option:selected").val();
		if(type=="其它"){
			$("#otherItem").css("display","");
		}else{
			$("#otherItem").css("display","none");
		}
	}
	changeType();
	function toPrint(id){
		window.open("${path}/train/toPayoutPrint.do?id="+id);
	}
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/train/addPayout.do" method="post">
 		<div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">姓名：</span>
			<span class="form_span_view">${trainMap.name}</span>
	    </div> 
	   
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red">*</font>项目：</label>
	        <select id="type" class="form_select"  name="formMap[type]" onchange="changeType(this)">
                 <c:forEach items="${payoutItemList }" var="item">
                 	<option value="${item.name }">${item.name }</option>
                 </c:forEach>
            </select>
	    </div> 
	    <div class="form_rowelem" id="otherItem" style="display: none"> 
	        <label class="form_label"><font color="red">*</font>其它项目：</label>
	        <input type="text" name="formMap[otherType]" id="otherType" size="20" maxlength="50" class="maxlength_50 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label"><font color="red">*</font>金额：</label>
	        <input type="text" name="formMap[payout]" id="payout" size="20" maxlength="10" class="maxlength_10 form_input_text" style="width: 200px;" />
	    </div> 
	    <div class="form_rowelem"> 
	        <label class="form_label">备注：</label>
	        <textarea name="formMap[note]" id="note" cols="35" rows="5"></textarea>
	    </div>
	    <input type="hidden" name="formMap[trainId]" value="${trainMap.id }"/>
	    <input type="hidden" name="flag" id="flag" value="0"/>
	    <input type="hidden" name="formMap[createUser]" value="${user.id }"/>
	</form>
	
</div>
	<br />
  	<div class="enter">
		<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="保存并关闭"  onclick="toSave(0)">保存并关闭</button> </span>&nbsp;&nbsp;
		<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="保存并添加"  onclick="toSave(1)">保存并添加</button> </span>&nbsp;&nbsp;
		
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
	<c:if test="${fn:length(payoutMapList) !=0}"> 
			<div class="form_rowelem"> 
	 			<span class="form_rowelem_span" style="margin-left: 80px;">历史记录：</span>
		    </div> 
		    <table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		            <td>项目</td>
		            <td>金额</td>
		            <td>操作人</td>
		            <td>操作日期</td>
		            <td>备注</td>
		           
		            <td>是否确定</td>
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
			        		<c:if test="${payout.status=='0' }">待确认</c:if>
			        		<c:if test="${payout.status=='1' }"><font color="red">是</font></c:if>
			        		<c:if test="${payout.status=='2' }"><font color="blue">否</font></c:if>
				        </td>
				        <td><a href="#" onclick="toPrint(${payout.id})">打印</a></td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
</body>
</html>