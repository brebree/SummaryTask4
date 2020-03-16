<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="View Tests" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<c:if test="${not empty condition}">
			<h3>
				<fmt:message key="view_tests.over" />
			</h3>
		</c:if>
		<c:if test="${not empty tests }">
			<form action="controller">
				<input type="hidden" name="command" value="viewTestsBySubject">
				<select name="subject">
					<c:forEach var="sj" items="${subjects }">
						<option value="${sj.id}">${sj.name}</option>
					</c:forEach>
				</select> <input type="submit"
					value='<fmt:message key="view_tests.button.getBySubject" />'>
			</form>
			<table>
				<thead>

					<tr>
						<th><fmt:message key="view_tests.th.testName" /></th>
						<th><fmt:message key="view_tests.th.subject" /></th>
						<th><fmt:message key="view_tests.th.complexity" /></th>
						<th><fmt:message key="view_tests.th.time" /></th>
						<th><fmt:message key="view_tests.th.quantity" /></th>
						<th><fmt:message key="view_tests.th.action" /></th>
						<th>
							<form action="controller">
								<input type="hidden" name="command" value="viewAllTests">
								<input type="hidden" name="prefix" value="s">
								<select name="comparator">
									<option value="sortByName"><fmt:message
											key="view_tests.th.sortByName" /></option>
									<option value="sortByComplexity"><fmt:message
											key="view_tests.th.sortByComplexity" /></option>
									<option value="sortByQuestionsQuantity"><fmt:message
											key="view_tests.th.sortByQuestionsQuantity" /></option>
								</select> <input type="submit"
									value='<fmt:message key="view_tests.button.sort" />'>
							</form>
						</th>

					</tr>

				</thead>
				<tbody>
					<c:forEach var="test" items="${tests }">
						<tr>
							<td>${test.testName}</td>
							<td><c:forEach var="sj" items="${subjects}">
									<c:if test="${sj.id == test.testSubjectId}">
										<c:out value="${sj.name}"></c:out>
									</c:if>
								</c:forEach></td>
							<td>${test.complexity }</td>
							<td>${test.testTime }</td>
							<td>${test.questionList.size() }</td>
							<c:if test="${userRole.name == 'admin' }">
								<td><form action="controller">
										<input type="hidden" name="command" value="viewEditTest">
										<input type="hidden" name="id" value="${test.id}"> <input
											type="submit"
											value='<fmt:message key="view_tests.button.edit" />'>
									</form></td>
								<td><form action="controller" method="post">
										<input type="hidden" name="command" value="removeTest">
										<input type="hidden" name="id" value="${test.id}"> <input
											type="submit"
											value="<fmt:message key="view_tests.button.remove"/>">
									</form></td>
							</c:if>
							<c:if test="${userRole.name == 'student' }">
								<td><form action="controller" method="get">
										<input type="hidden" name="command" value="viewTestToPass">
										<input type="hidden" name="id" value="${test.id}"> <input
											type="submit"
											value="<fmt:message key="view_tests.button.pass"/>">
									</form></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty tests}">
			<h3>
				<fmt:message key="user_profile.h3.no_tests" />
			</h3>
		</c:if>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>