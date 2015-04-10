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
		var paying = "";
		for(var i = 0 ; i < 100;i++){
			var p = document.getElementsByName("paying"+i);
			for(var j = 0 ; j < p.length;j++){
				var k = p[j];
				if(k.checked){
					paying +=k.value+";";
				}
			}
		}
		if(paying!=""){
			paying = paying.substring(0,paying.length-1);
		}
		//收入
		var income = "";
		for(var i = 0 ; i < 100;i++){
			var p = document.getElementsByName("income"+i);
			for(var j = 0 ; j < p.length;j++){
				var k = p[j];
				if(k.checked){
					income +=k.value+";";
				}
			}
		}
		if(income!=""){
			income = income.substring(0,income.length-1);
		}
		//支出
		var payout = "";
		for(var i = 0 ; i < 100;i++){
			var p = document.getElementsByName("payout"+i);
			for(var j = 0 ; j < p.length;j++){
				var k = p[j];
				if(k.checked){
					payout +=k.value+";";
				}
			}
		}
		if(payout!=""){
			payout = payout.substring(0,payout.length-1);
		}
		document.getElementsByName("formMap[paying]")[0].value = paying;
		document.getElementsByName("formMap[income]")[0].value = income;
		document.getElementsByName("formMap[payout]")[0].value = payout;
		art.dialog.through({
			title:"提示",
			icon:"warning",
			content:"一旦确定将不可修改，请确认信息无误！谢谢！",
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
 	<form id="form1" action="${path }/train/updateAudit.do" method="post">
 		<div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">姓名：</span>
			<span class="form_span_view">${trainMap.name}</span>
	    </div> 
	    <div class="form_rowelem"> 
 			<span class="form_rowelem_span" style="margin-left: 115px;">应总交学费：</span>
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
		            <td>备注</td>
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${payingMapList}" var="paying" varStatus="varStatus">
			    	<tr>
				    	<td>${paying.paying }</td>
				        <td>${paying.createUserName }</td>
				        <td><fmt:formatDate value="${paying.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
					        <c:if test="${fn:length(paying.note)>5}">
					      	 <span title="${paying.note }">${fn:substring(paying.note,0,5)}....</span>
					    	</c:if>
						    <c:if test="${fn:length(paying.note)<=5}">
						           ${paying.note}
						    </c:if>
				       
				        </td>
				        <td>
			        		<span class="form_rowelem_label">是</span>
			        		<input type="radio" class="form_input_radio"  value="${paying.id }_1" name="paying${varStatus.index }" checked="checked"/>
			        		<span class="form_rowelem_label">否</span>
			        		<input type="radio" class="form_input_radio"  value="${paying.id }_2" name="paying${varStatus.index }"  />
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
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${incomeMapList}" var="income" varStatus="varStatus">
			    	<tr>
			    		<td>${income.type }</td>
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
			        		<span class="form_rowelem_label">是</span>
			        		<input type="radio" class="form_input_radio" value="${income.id }_1" name="income${varStatus.index }" checked="checked"/>
			        		<span class="form_rowelem_label">否</span>
			        		<input type="radio" class="form_input_radio" value="${income.id }_2" name="income${varStatus.index }"  />
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
		            <td>是否确认</td>
		         </tr>
			    <c:forEach items="${payoutMapList}" var="payout" varStatus="varStatus">
			    	<tr>
			    		<td>${payout.type }</td>
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
			        		<span class="form_rowelem_label">是</span>
			        		<input type="radio" class="form_input_radio"  value="${payout.id }_1" name="payout${varStatus.index }" checked="checked"/>
			        		<span class="form_rowelem_label">否</span>
			        		<input type="radio" class="form_input_radio" value="${payout.id }_2" name="payout${varStatus.index }"  />
				        </td>
			        </tr>
			    </c:forEach>
		 	</table>
		</c:if>
	    <input type="hidden" name="formMap[paying]" value=""/>
	    <input type="hidden" name="formMap[income]" value=""/>
	    <input type="hidden" name="formMap[payout]" value=""/>
	    <input type="hidden" name="formMap[auditUser]" value="${user.id }"/>
	</form>

</div>
	<br />
  	<div class="enter">
  		<c:if test="${fn:length(incomeMapList) !=0||fn:length(payingMapList) !=0||fn:length(payoutMapList) !=0}"> 
			<span class="form_button_btn"><button type="button"
				class="form_button_btn_in" value="确认批审"  onclick="toSave()">确认批审</button> </span>&nbsp;&nbsp;
		</c:if>
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>