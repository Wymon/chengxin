<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<script>
	function splitPage(up){
		var urlAndParam = up.split("?");
		var u = urlAndParam[0];
		var p = urlAndParam[1];
		postUrlParam(u,p);
	}
	function postUrlParam(url,param){
		
		$("#default_Form").remove();
		$("body").append('<form id="default_Form" method="post"></form>');
		$("#default_Form").attr('action',url);
		var arr = param.split("&");
		for(var i=0;i<arr.length;i++){
			var p = arr[i];
			var pMap = p.split("=");
			var pname = pMap[0];
			var pvalue = pMap[1];
			$("#default_Form").append($('<input type="hidden" id="'+pname+'" name="'+pname+'" value="'+pvalue+'"/>'));
		}
		$("#default_Form").submit();
	}
</script> 
		<div class="table_page_right">
			<c:if test="${pageList.hasFirst}"><a href="#" onclick="javascript:splitPage('${param.actionName }&currentPage=1&pageSize=${pageList.pageSize}')">首页 </a></c:if>
			<c:if test="${pageList.hasFirst==false}"><a class="a_hover">首页</a></c:if>
			<c:if test="${pageList.hasPrevious }"><a href="#" onclick="javascript:splitPage('${param.actionName }&currentPage=${pageList.currentPage - 1 }&pageSize=${pageList.pageSize}')">上一页</a></c:if>
			<c:if test="${pageList.hasPrevious==false }" ><a class="a_hover">上一页</a></c:if>
			<c:if test="${pageList.hasNext }"><a href="#" onclick="javascript:splitPage('${param.actionName }&currentPage=${pageList.currentPage + 1 }&pageSize=${pageList.pageSize}')">下一页</a></c:if>
			<c:if test="${pageList.hasNext==false }"><a class="a_hover">下一页</a></c:if>
			<c:if test="${pageList.hasLast }"><a href="#" onclick="javascript:splitPage('${param.actionName }&currentPage=${pageList.totalPage }&pageSize=${pageList.pageSize}')">尾页</a></c:if>
			<c:if test="${pageList.hasLast==false }"><a class="a_hover">尾页</a></c:if> 
			<select name="currentPage" onchange='javascript:splitPage("${param.actionName }&pageSize=${pageList.pageSize}&currentPage="+this.value)'>
				<c:forEach items="${pageList.numbers}" var="st">
					<c:set var="number" value="${st}"></c:set>	
					<option value="${number}" <c:if test="${pageList.currentPage == number }">selected="selected"</c:if> >
					第${number}页</option>			
				</c:forEach>
			</select>
	每页显示:
	 	<select class="selectPageSize" style="margin-top: 3px;" name="pageSize" onchange='javascript:splitPage("${param.actionName }&pageSize="+this.value)'>
			<option value="10" <c:if test="${pageList.pageSize == 10 }">selected="selected"</c:if> > 10 </option>
			<option value="15" <c:if test="${pageList.pageSize == 15 }">selected="selected"</c:if> > 15 </option>
			<option value="20" <c:if test="${pageList.pageSize == 20 }">selected="selected"</c:if> > 20 </option>
			<option value="50" <c:if test="${pageList.pageSize == 50 }">selected="selected"</c:if> > 50 </option>
			<option value="100" <c:if test="${pageList.pageSize == 100 }">selected="selected"</c:if> > 100 </option>
		</select>
		</div>
	 <!--right-->