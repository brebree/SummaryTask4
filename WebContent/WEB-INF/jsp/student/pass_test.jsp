<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Pass Test" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<meta http-equiv="refresh"
	content="${test.testTime}; url=controller?command=viewAllTests&condition=over">
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="submitPassTest"> <input
				type="hidden" name="id" value="${test.id}">
			<p>
				<strong><fmt:message key="pass_test.strong.testName" /></strong>${test.testName}</p>
			<c:forEach var="sj" items="${subjects}">
				<c:if test="${sj.id eq test.testSubjectId }">
					<c:set var="sub" value="${sj.name}"></c:set>
				</c:if>
			</c:forEach>
			<p>
				<strong><fmt:message key="pass_test.strong.subject" /></strong>${sub}</p>
			<p>
				<strong><fmt:message key="pass_test.strong.complexity" /></strong>${test.complexity}</p>
			<p>
				<strong><fmt:message key="pass_test.strong.time" /></strong>${test.testTime}
				<fmt:message key="pass_test.seconds" />
			</p>
			<div class="questions">
				<c:forEach var="question" items="${test.questionList}">
					<div class="question">
						<p>
							<strong><fmt:message key="create_test.strong.question" />${question.id}:
							</strong>${question.questionText}</p>
						<div class="answers">
							<c:forEach var="answer" items="${question.answerList}">
								<div class="answer">
									<p>
										<strong><fmt:message key="create_test.strong.answer" />${answer.id}:
										</strong>${answer.answerText} <input type="checkbox"
											name="box_${question.id}_${answer.id}" value="correct">
									</p>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
			<input type="submit"
				value="<fmt:message key="view_tests.button.pass" />">
		</form>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>