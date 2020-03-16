<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ tag body-content="empty" pageEncoding="utf-8"%>

<c:if test="${not empty subjects}">
	<c:forEach var="sj" items="${subjects }">
		<option value="${sj.name}">${sj.name}</option>
	</c:forEach>
</c:if>