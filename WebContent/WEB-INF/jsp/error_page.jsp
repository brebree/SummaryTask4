<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib uri="/WEB-INF/tld/st4.tld" prefix="st4"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<div id="main_container">
			<h2 class="error">
				<fmt:message key="error.h2.occured" />
			</h2>

			<c:set var="code"
				value="${requestScope['javax.servlet.error.status_code']}" />
			<c:set var="message"
				value="${requestScope['javax.servlet.error.message']}" />
			<c:set var="exception"
				value="${requestScope['javax.servlet.error.exception']}" />

			<c:if test="${not empty code}">
				<h3>
					<fmt:message key="error.h3.code" />${code}</h3>
			</c:if>

			<c:if test="${not empty message}">
				<h3>${message}</h3>
			</c:if>

			<c:if test="${not empty exception}">
				<st4:printException exception="${exception }" />
			</c:if>
			<c:if test="${not empty requestScope.errorMessage}">
				<h3>${requestScope.errorMessage}</h3>
			</c:if>
		</div>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>