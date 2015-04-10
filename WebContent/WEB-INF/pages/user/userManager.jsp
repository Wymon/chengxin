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
	function toAuditDelete(id){
		art.dialog.open('${path}/user/toAuditDelete.do?id='+id,
				{title:"审核删除 用户",
				width:'600px',
				height:'300px',
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
	function toAuditAddUser(id){
		art.dialog.open('${path}/user/toAuditAddUser.do?id='+id,
				{title:"审核添加 用户",
				width:'600px',
				height:'300px',
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
	function toAuditUser(id){
		art.dialog.open('${path}/user/toUpdateAudit.do?id='+id,
				{title:"审核",
				width:'600px',
				height:'300px',
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
	function toUpdate(id){
		art.dialog.open('${path}/user/toUpdate.do?id='+id,
				{title:"修改用户",
				width:'600px',
				height:'300px',
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
	function toResetPW(id){
		art.dialog.open('${path}/user/toResetPW.do?id='+id,
				{title:"重置密码",
				width:'550px',
				height:'400px',
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
	$(document).ready(function() {
		var msg = "${msg}";
		if(msg=="succeed"){
			succeed("修改成功！");
			art.dialog.close();
			return;
		}
		if(msg!=""&&msg!=null){
			error(msg);
		}
	});
	function toAddUser(){
		art.dialog.open('${path}/user/toAdd.do',
				{title:"添加用户",
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
	function toDelete(id){
		art.dialog.confirm("你确定删除此用户",function(){
			$("#form1").attr("action","${path}/user/delete.do?id="+id);
			$("#form1").submit();
		},function(){
		});
	}
	function toAuditUserPW(id){
		art.dialog.open('${path}/user/toAuditUserPW.do?id='+id,{
				title:"审核密码变更",
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
</script>
</head>
<body>
<div class="form_rowelem"> 
 	<form id="form1" action="${path }/user/toUserManager.do" method="post">
 		<table id="table_list" class="tablecss"  border="1" style="overflow-x: auto;" >
        <tr class="brown" >
        	<td>登录名</td>
            <td>姓名</td>
            <td>角色</td>
            <td>邮箱</td>
            <td>是否发送报表到邮箱 </td>
            <td>操作</td>
         </tr>
         <c:forEach items="${userList}" var="map">
			<tr >
				<td >${map.userName}</td>
				<td >${map.realName}</td>
				<td >${map.roleName}</td>
				<td>${map.email}</td>
				<td>
					<c:if test="${map.canEmail=='0'}">否</c:if>
					<c:if test="${map.canEmail=='1'}">是</c:if>
				</td>
				<td>
				<pe:permission code="${menuCodeMap.updateUser }">
					<c:if test="${map.bcreateUser==null&&map.status==1 }">
						<a href="#" onclick="toUpdate(${map.id})">修改</a> &nbsp;
					</c:if>
				</pe:permission>
				<pe:permission code="${menuCodeMap.updateUserAudit }">
					<c:if test="${map.bcreateUser!=null&&map.bcreateUser!=user.id&&map.status!=2}">
						<a href="#" onclick="toAuditUser(${map.bid})">审核修改</a> &nbsp;
					</c:if>
				</pe:permission>
				<!-- 增加的审核 -->
				<pe:permission code="${menuCodeMap.auditAddUser }">
					<c:if test="${map.createUser!=user.id&&map.status==2}">
						<a href="#" onclick="toAuditAddUser(${map.id})">审核添加</a> &nbsp;
					</c:if>
				</pe:permission>
				<pe:permission code="${menuCodeMap.updateResetPW }">
					<c:if test="${map.pbcreateUser==null&&map.status==1 }">
						<a href="#" onclick="toResetPW(${map.id})">重置密码</a> &nbsp;
					</c:if>
				</pe:permission>
				<pe:permission code="${menuCodeMap.auditUserPW }">
					<c:if test="${map.pbcreateUser!=null&&map.pbcreateUser!=user.id }">
						<a href="#" onclick="toAuditUserPW(${map.pbid})">审核密码变更</a> &nbsp;
					</c:if>
				</pe:permission>
				<pe:permission code="${menuCodeMap.updateUser }">
					<c:if test="${map.deleteUser!=user.id && map.status==3}">
						<a href="#" onclick="toAuditDelete(${map.id})">审核删除</a> &nbsp;
					</c:if>
				</pe:permission>
				<pe:permission code="${menuCodeMap.deleteUser }">
					<c:if test="${ map.status==1}">
						<a href="#" onclick="toDelete(${map.id})">删除</a> &nbsp;
					</c:if>
				</pe:permission>
				</td>
			</tr>
			
		</c:forEach>
        </table>
	</form>

</div>
	<br />
  	<div class="enter">
  	<pe:permission code="${menuCodeMap.addUser }">
  		<span class="form_button_btn"><button type="button" onclick="toAddUser()"
				class="form_button_btn_in" value="添加">添加</button> </span>
	</pe:permission>
		<span class="form_button_btn"><button type="button" onclick="closeWin()"
				class="form_button_btn_in" value="关闭">关闭</button> </span>
	</div>
</body>
</html>