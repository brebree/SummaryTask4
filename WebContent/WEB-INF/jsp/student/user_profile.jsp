<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="My Profile" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<h2>${user.firstName}&nbsp${user.lastName}</h2>
		<h2>
			<fmt:message key="user_profile.h2.result" />
			${user.testsResult}%
		</h2>
		<h2>
			<fmt:message key="user_profile.h2.testQuantity" />${user.testsCount}
		</h2>
		<c:if test="${not empty usersTests}">
			<table>
				<thead>

					<tr>
						<th><fmt:message key="view_tests.th.testName" /></th>
						<th><fmt:message key="view_tests.th.subject" /></th>
						<th><fmt:message key="view_tests.th.complexity" /></th>
						<th><fmt:message key="view_tests.th.time" /></th>
						<th><fmt:message key="user_profile.th.result" /></th>
						<th><fmt:message key="user_profile.th.date" /></th>
					</tr>

				</thead>
				<tbody>
					<c:forEach var="test" items="${usersTests}">
						<fmt:formatDate value="${test.date}"
							pattern="HH:mm, EEEE, dd MMM yyyy" var="time" />
						<tr>
							<td>${test.testName}</td>
							<td><c:forEach var="sj" items="${subjects }">
									<c:if test="${sj.id == test.testSubjectId}">
										<c:out value="${sj.name}"></c:out>
									</c:if>
								</c:forEach></td>
							<td>${test.complexity}</td>
							<td>${test.testTime }</td>
							<td>${test.testResult }%</td>
							<td>${time}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty usersTests}">
			<h3>
				<fmt:message key="user_profile.h3.no_tests" />
			</h3>
		</c:if>
		<form action="UploadServlet" method="post">
			<strong>Download Report</strong><br> <select name="format">
				<option value="PDF">PDF</option>
				<option value="XML">XML</option>
				<option value="DOC">DOC</option>
			</select> <input type="submit" value="Get Report">
		</form>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>