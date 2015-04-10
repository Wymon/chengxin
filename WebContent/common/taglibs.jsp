<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 引入JSTL标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pe" uri="/permission" %>
<%-- 设置全局的访问路径 --%>
<c:set scope="application" var="path" value="${pageContext.request.contextPath}"></c:set>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	response.flushBuffer();
%>
<script type="text/javascript">
var path = "${path}";
</script>
<%-- 设置统一的jquery1.7.2.js --%>
<script type="text/javascript" src="${path}/jquery/jquery.min.js" ></script>
<link type="text/css" rel="stylesheet" href="${path}/style/css/table.css" />
<link type="text/css" rel="stylesheet" href="${path}/style/css/base.css" />
<link type="text/css" rel="stylesheet" href="${path}/style/css/form.css" />