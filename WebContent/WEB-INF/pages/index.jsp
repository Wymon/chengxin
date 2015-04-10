<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>诚信驾校财务管理系统</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/artdialog.jsp"%>
<script type="text/javascript" src="${path}/plugs/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.wrapper{ height:100%; width:100%; margin:0 auto;}
.header_main{ height:110px; background:url(${path}/images/header.jpg) 0 0 no-repeat; }
.welcome{height: 72px;position: absolute;top: 35px;right: 30px;color:red;font-size:14px;font-weight: bold;text-align:right;}
.exit{height: 82px;position: absolute;top: 75px;right: 30px;color:red;font-size:14px;font-weight: bold;text-align:right;}
.navigate{top: 35px; height:32px; width:100%; min-width:980px; background-color:#CEC4B6;}
.navigate_main{ height:32px; background-color:#CEC4B6;}
.navigate ul{height: 32px;padding:0 20px;list-style-type: none;display: block;}
.navigate li{display: inline;list-style-type: none;float:left;position: relative;}
.navigate li a{color:#fff; text-decoration: none;font-size:14px;line-height: 32px;padding:0 10px 0 33px;display: block;position: relative; font-weight:bold;}
</style>
</head>
<body>
<!--头部开始-->
<div class="header" id="header">
    <div class="wrapper" style="background:url(${path}/images/header.jpg) 0 0 no-repeat;">
    	<div style="position: relative;"> 
        <div class="header_main"></div>
        <div class="welcome">
      			 您好，${sessionScope.user.realName}<br />
      			 角色：${sessionScope.user.roleName}
      			 <br />
      			 <br />
      			 <pe:permission code="${menuCodeMap.userManager }">
      				 <a onclick="javascript:toUserManager();" href="javascript:void(0);"><font color="blue">用户管理</font></a>
      			 </pe:permission>
      			 <pe:permission code="${menuCodeMap.oldTrainInfoMap }">
      				 <a onclick="javascript:toCanAddOldTrain();" href="javascript:void(0);"><font color="blue">学员信息权限控制</font></a>
      			 </pe:permission>
      			 <a onclick="javascript:toUpdatePW();" href="javascript:void(0);"><font color="blue">修改密码</font></a>
      			 <a onclick="javascript:exit();" href="javascript:void(0);"><font color="blue">退出</font></a>
        </div>
        </div>
    </div>
</div>

<div class="main_r" >
     <div class="content" >
       <div style="overflow-x: auto; overflow-y: auto; height: 500px;">
	 <form action="${path}/train/toList.do" method="post" id="trainform">
        <div id="firstDiv" >
            <div class="form_rowelem">
            	<div class="table_page_search">
            		<div class="table_page_left">
            			<pe:permission code="${menuCodeMap.addTrain }">
	            			<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
	            				<input type="button" class="form_button_btn_in" value="注册学员" onclick="toAdd()"/>
	            			</span>
            			</pe:permission>
            			<pe:permission code="${menuCodeMap.exportReport }">
	            			<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
	            				<input type="button" class="form_button_btn_in" value="财务日报" onclick="toExportReport()"/>
	            			</span>
            			</pe:permission>
            			<pe:permission code="${menuCodeMap.exportReportDetail }">
	            			<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
		            			<input type="button" class="form_button_btn_in" value="财务日报明细" onclick="toExportReportDetail()"/>
		            		</span>
	            		</pe:permission>
	            		<pe:permission code="${menuCodeMap.toPayoutCommonManager }">
	            			<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
		            			<input type="button" class="form_button_btn_in" value="公共支出" onclick="toPayoutCommonManager()"/>
		            		</span>
	            		</pe:permission>
	            		
	            		<pe:permission code="${menuCodeMap.toUpdatePayoutCommonManager }">
		            		<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
		            			<input type="button" class="form_button_btn_in" <c:if test="${hasPayoutCommonAudit == 1 }">style="color:red"</c:if> value="公共支出修改" onclick="toUpdatePayoutCommon()"/>
		            		</span>
	            		</pe:permission>
	            		
	            		<pe:permission code="${menuCodeMap.toIncomeCommonManager }">
	            			<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
		            			<input type="button" class="form_button_btn_in" value="公共收入" onclick="toIncomeCommonManager()"/>
		            		</span>
	            		</pe:permission>
	            		
	            		<pe:permission code="${menuCodeMap.toUpdateIncomeCommonManager }">
		            		<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
		            			<input type="button" <c:if test="${hasIncomeCommonAudit == 1 }">style="color:red"</c:if> class="form_button_btn_in" value="公共收入修改" onclick="toUpdateIncomeCommon()"/>
		            		</span>
	            		</pe:permission>
	            		
	            		<pe:permission code="${menuCodeMap.auditPaycomon }">
		            		<span class="form_button_btn" style="margin-top:1px; margin-left:10px;">
		            			<input type="button" class="form_button_btn_in" <c:if test="${hasCommonAudit==true }">style="color:red"</c:if> value="财务审核" onclick="toUpdatePayoutCommonAudit()"/>
		            		</span>
	            		</pe:permission>
            		</div>
            		<br/>
                	<div class="table_page_center">
                		<span class="form_rowelem_span" style="margin-left:20px;">编号：</span>
                        <input type="text" class="form_input_text" value="${queryMap.id}" size="20" maxlength="20" id="id" name="formMap[id]"/>
                		<span class="form_rowelem_span" style="margin-left:20px;">立秋编号：</span>
                        <input type="text" class="form_input_text" value="${queryMap.autumnNumber}" size="20" maxlength="20" id="autumnNumber" name="formMap[autumnNumber]"/>
                        <span class="form_rowelem_span" style="margin-left:20px;">姓名：</span>
                        <input type="text" class="form_input_text" value="${queryMap.name}" size="20" maxlength="20" id="realName" name="formMap[name]"/>
                        <span class="form_rowelem_span" style="margin-left:20px;">新旧：</span>
                        <select class="form_select"  id="newOrOld" name="formMap[newOrOld]">
                        	<option value="" >-请选择-</option>
		                    <option value="新" <c:if test="${queryMap.newOrOld=='新'}">selected="selected"</c:if>  >  新  </option>
		                    <option value="旧" <c:if test="${queryMap.newOrOld=='旧'}">selected="selected"</c:if> > 旧  </option>
                		</select>
                        <span class="form_rowelem_span" style="margin-left:20px;">欠费：</span>
                        <input type="text" class="form_input_text" name="formMap[canPayingTo]" value="${queryMap.canPayingTo }"/> 
						<span class="form_rowelem_span" style="margin-left:6px;">至</span>
                        <input type="text" class="form_input_text" name="formMap[canPayingForm]" value="${queryMap.canPayingForm }" />
                        
                        <span  style="margin-top:1px; margin-left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                	</div>
                	
                	<div class="table_page_center">
                		<span class="form_rowelem_span" style="margin-left:20px;">注册人：</span>
                       	<input type="text" class="form_input_text" value="${queryMap.createUserName}" size="20" maxlength="20" id="createUserName" name="formMap[createUserName]"/>
                		<span class="form_rowelem_span" style="margin-left:20px;">注册日期：</span>
                        <input type="text" class="form_input_text" name="formMap[beginTime]" value="${queryMap.beginTime }" id="d4321" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',maxDate:'#F{$dp.$D(\'d4322\');}'})" readonly="readonly" /> 
						<span class="form_rowelem_span" style="margin-left:6px;">至</span>
                        <input type="text" class="form_input_text" name="formMap[endTime]" value="${queryMap.endTime }" id="d4322" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',minDate:'#F{$dp.$D(\'d4321\');}'})" readonly="readonly" />
                        
                		
                        <span class="form_rowelem_span" style="margin-left:20px;">本校/挂靠：</span>
                        <select class="form_select"  id="type" name="formMap[type]">
                        	 <option value="" >-请选择-</option>
			                 <option value="本校" <c:if test="${queryMap.type=='本校'}">selected="selected"</c:if>>  本校  </option>
			                 <option value="挂靠" <c:if test="${queryMap.type=='挂靠'}">selected="selected"</c:if> > 挂靠  </option>
			            </select>
                        
                        <span class="form_rowelem_span" style="margin-left:20px;">C1/C2：</span>
                        <select class="form_select"  id="licenseTag" name="formMap[licenseTag]">
                        	 <option value="" >-请选择-</option>
			                 <option value="C1" <c:if test="${queryMap.licenseTag=='C1'}">selected="selected"</c:if>> C1  </option>
			                 <option value="C2" <c:if test="${queryMap.licenseTag=='C2'}">selected="selected"</c:if>> C2  </option>
			            </select>
			            <span class="form_rowelem_span" style="margin-left:20px;">备注：</span>
                        <input type="text" class="form_input_text" value="${queryMap.note}" size="20" maxlength="20" id="note" name="formMap[note]"/>
                        <span class="form_button_btn" style="margin-top:1px; margin-left:10px;"><input type="button" onclick="toSubmit()" class="form_button_btn_in" value="搜索"></input></span>
                	</div>
                </div>
            </div>
        </div>
    </form>
    
	<table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
        <tr class="brown" >
        	<td>编号</td>
        	<td>立秋编号</td>
            <td>姓名</td>
            <td>身份证号码</td>
            <td>应交学费 </td>
            <td>应交总费用 </td>
            <td>已交</td>
            <td>总欠费</td>
            <td>新/旧</td>
            <td>本校/挂靠</td>
            <td>C1/C2</td>
            <td>备注</td>
            <td>操作人</td>
            <td>注册日期</td>
            <td>操作</td>
         </tr>
		<c:forEach items="${pageList.list}" var="map">
			<tr >
				<td >${map.id }</td>
				<td >${map.autumnNumber }</td>
				<td >${map.name }</td>
				<td>${map.idcard }</td>
				<td>${map.payable }</td>
				<td>${map.payable+map.allincome}</td>
				<td>${map.allpaying+map.allincome}</td>
				<td>${map.payable-map.allpaying }</td>
				<td>${map.newOrOld }</td>
				<td>${map.type }</td>
				<td>${map.licenseTag }</td>
				<td>
					<c:if test="${fn:length(map.note)>10}">
				       <span title="${map.note }">${fn:substring(map.note,0,10)}....</span>
				    </c:if>
				    <c:if test="${fn:length(map.note)<=10}">
				           ${map.note}
				    </c:if>
				</td>
				<td>
					${map.createUserName }
				</td>
				<td>
					<fmt:formatDate value="${map.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td> 
				<c:if test="${map.allpaying>0 }">
					<pe:permission code="${menuCodeMap.autumnNumber }">
						<a href="#" onclick="toAddAutumnNumber(${map.id})">立秋编号</a>&nbsp;
					</pe:permission>
					<pe:permission code="${menuCodeMap.updateAutumnNumber }">
						<c:if test="${map.abcreateUser==null && map.autumnNumber!=null }">
							<a href="#" onclick="toUdpateAutumnNumber(${map.id})">修改立秋编号</a>&nbsp;
						</c:if>
					</pe:permission>
					<pe:permission code="${menuCodeMap.updateAudtiAutumnNumber }">
						<c:if test="${map.abcreateUser!=null&&map.abcreateUser!=user.id }">
							<a href="#" onclick="toUdpateAuditAutumnNumber(${map.abId})">审核立秋编号</a>&nbsp;
						</c:if>
					</pe:permission>
				</c:if>
				
				<pe:permission code="${menuCodeMap.paying }">
					<a href="#" onclick="toAddPaying(${map.id})">续缴学费</a> &nbsp;
				</pe:permission>
				<pe:permission code="${menuCodeMap.income }">
					<a href="#" onclick="toAddIncome(${map.id})">收入项目</a> &nbsp;
				</pe:permission>
				<pe:permission code="${menuCodeMap.payout }">
					<a href="#" onclick="toAddPayout(${map.id})">支出</a> &nbsp;
				</pe:permission>
				
				<pe:permission code="${menuCodeMap.update }">
					<a href="#" onclick="toUpdate(${map.id})" <c:if test="${map.canUpdateAudit == 1 }">style="color:red"</c:if> >修改</a> &nbsp;
				</pe:permission>
				<pe:permission code="${menuCodeMap.audit }">
					<a href="#" onclick="toUpdateAudit(${map.id})" > <font <c:if test="${map.hasAudit==1 }">color="red"</c:if> >财务审核</font></a> &nbsp;
				</pe:permission>
					<a href="#" onclick="toView(${map.id})">查看</a> &nbsp;
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
				<pe:permission code="${menuCodeMap.export }">
					<span class="form_button_btn" style="foalt:left;margin-top:1px; margin-left:10px;">
						<input type="button" onclick="toExport()" class="form_button_btn_in" value="导出"/>
					</span>
				</pe:permission>
				<c:import url="/common/plugs/pagePost.jsp">
					<c:param name="actionName">${path }/train/toList.do?1=1&formMap[autumnNumber]=${queryMap.autumnNumber}&formMap[name]=${queryMap.name}&formMap[beginTime]=${queryMap.beginTime }&formMap[endTime]=${queryMap.endTime }&formMap[canPayingTo]=${queryMap.canPayingTo }&formMap[canPayingForm]=${queryMap.canPayingForm }&formMap[createUserName]=${queryMap.createUserName }&formMap[newOrOld]=${queryMap.newOrOld }&formMap[type]=${queryMap.type }&formMap[licenseTag]=${queryMap.licenseTag }&formMap[id]=${queryMap.id}&formMap[note]=${queryMap.note}</c:param>
				</c:import>
			</div>
		</div> 
	</c:if>
	<br/>
	
	<pe:permission code="${menuCodeMap.statistics }">
		<c:if test="${fn:length(pageList.list)!=0}">
			<table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
		        <tr class="brown" >
		        	<td colspan="7">费用统计</td>
		        </tr>
		        <tr >
		        	<td>应交学费</td>
		        	<td>已交学费</td>
		        	<td>收入项目</td>
		        	<td>支出项目</td>
		        	<td>收入总额</td>
		        	<td>欠学费</td>
		        	<td>应交总学费</td>
		        </tr>
		        <tr >
		        	<td>${statisticsMap.payable}</td>
		        	<td>${statisticsMap.paying}</td>
		        	<td>${statisticsMap.allincome}</td>
		        	<td>${statisticsMap.allpayout }</td>
		        	<td>${statisticsMap.allpaying }</td>
		        	<td>${statisticsMap.payable-statisticsMap.paying}</td>
		        	<td>${statisticsMap.allpay }</td>
		        </tr>
		    </table>
		  <br/>
	      <br/>
		</c:if>
	</pe:permission>
</div>

 </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	var msg = "${msg}";
	if(msg!=""&&msg!=null){
		error(msg);
	};
});
function toUpdatePayoutCommon(){
	art.dialog.open('${path}/trainBack/toUpdatePayoutCommonManager.do',
			{title:"公共支出修改",
			width:'850px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
	
}
function toUpdateIncomeCommon(){
	art.dialog.open('${path}/trainBack/toUpdateIncomeCommonManager.do',
			{title:"公共收入修改",
			width:'850px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
	
}
function toCanAddOldTrain(){
	art.dialog.open('${path}/user/toCanAddOldTrain.do',
			{title:"增加历史学员设置",
			width:'400px',
			height:'150px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true
	});
}
function toUpdatePayoutCommonAudit(){
	art.dialog.open('${path}/train/toUpdatePayoutCommonAudit.do',
			{title:"财务审核",
			width:'650px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}
function toPayoutCommonManager(){
	art.dialog.open('${path}/train/toPayoutCommonManager.do',
			{title:"公共支出",
			width:'850px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}
function toIncomeCommonManager(){
	art.dialog.open('${path}/train/toIncomeCommonManager.do',{title:"公共收入",
			width:'850px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}
function toExportReport(){
	art.dialog.open('${path}/trainReport/toExportReport.do',
			{title:"财务日报",
			width:'500px',
			height:'200px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				
			}
	});
}
function toExportReportDetail(){
	art.dialog.open('${path}/trainReport/toExportReportDetail.do',
			{title:"财务日报明细",
			width:'500px',
			height:'200px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				
			}
	});
}
function toUserManager(){
	art.dialog.open('${path}/user/toUserManager.do',
			{title:"用户管理",
			width:'750px',
			height:'400px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			});
}
function toUpdatePW(){
	art.dialog.open('${path}/user/toUpdatePW.do',
			{title:"修改密码",
			width:'600px',
			height:'200px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}

function exit(){
	art.dialog.confirm("是否确定退出系统？",function(){
		window.location.href="${path}/login/loginOut.do";
	},function(){
	});
}
function toSubmit(){
	$("#trainform").attr("action","${path}/train/toList.do");
	$("#trainform").submit();
}
function toExport(){
	$("#trainform").attr("action","${path}/trainReport/toExport.do");
	$("#trainform").submit();
	$("#trainform").attr("action","${path}/train/toList.do");
}
function toAdd(){
	art.dialog.open('${path}/train/toAddTrain.do',
		{title:"注册学员",
		width:'600px',
		height:'450px',
		background:'white',
		opacity: 0.6,
		lock:true,
		esc:true,
		fixed: true,
		close:function(){
			$("#trainform").submit();
		}
	});
}
function toUdpateAutumnNumber(id){
	art.dialog.open('${path}/train/toUpdateAutumnNumber.do?id='+id,
			{title:"修改立秋编号",
			width:'600px',
			height:'300px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
		});
}
function toUdpateAuditAutumnNumber(id){
	art.dialog.open('${path}/trainBack/toUpdateAudtiAutumnNumber.do?id='+id,
			{title:"审核立秋编号",
			width:'600px',
			height:'300px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
		});
}
function toAddAutumnNumber(id){
	art.dialog.open('${path}/train/toAddAutumnNumber.do?id='+id,
			{title:"立秋编号",
			width:'450px',
			height:'200px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
		});
}
function toAddPaying(id){
	art.dialog.open('${path}/train/toAddPaying.do?id='+id,
			{title:"续缴学费",
			width:'600px',
			height:'400px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}
function toAddIncome(id){
	art.dialog.open('${path}/train/toAddIncome.do?id='+id,
			{title:"收入项目",
			width:'600px',
			height:'400px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}
function toAddPayout(id){
	art.dialog.open('${path}/train/toAddPayout.do?id='+id,
			{title:"支出项目",
			width:'650px',
			height:'400px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
		}
	});
}
function toUpdate(id){
	art.dialog.open('${path}/trainBack/toUpdate.do?id='+id,
			{title:"修改",
			width:'965px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
		}
	});
}
function toView(id){
	art.dialog.open('${path}/train/toView.do?id='+id,
			{title:"查看",
			width:'720px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				
		}
	});
}
function toUpdateAudit(id){
	art.dialog.open('${path}/train/toUpdateAudit.do?id='+id,
			{title:"财务审核",
			width:'720px',
			height:'450px',
			background:'white',
			opacity: 0.6,
			lock:true,
			esc:true,
			fixed: true,
			close:function(){
				$("#trainform").submit();
			}
	});
}

</script>
</body>
</html>