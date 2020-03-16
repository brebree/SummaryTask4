<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Result Page" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<p>
		<strong><fmt:message key="pass_test.strong.testName" /></strong>${test.testName}</p>
	<div id="content_container">
		<div id="main_container">
			<div class="questions">
				<c:forEach var="question" items="${test.questionList}">
					<c:forEach var="usersQuestion" items="${usersTest.questionList}">
						<c:if test="${usersQuestion.id eq question.id}">
							<div class="question">
								<p>
									<strong><fmt:message key="create_test.strong.question" />${question.id}:
									</strong>${question.questionText}</p>
								<div class="answers">
									<c:forEach var="answer" items="${question.answerList}">
										<c:forEach var="usersAnswer"
											items="${usersQuestion.answerList}">
											<c:if test="${answer.id eq usersAnswer.id}">
												<c:if
													test="${(answer.correct eq true) and (usersAnswer.correct eq true)}">
													<p class="right">
														<strong><fmt:message
																key="create_test.strong.answer" />${answer.id}: </strong>${answer.answerText}
													</p>
												</c:if>
												<c:if
													test="${(usersAnswer.correct eq true) and (answer.correct ne true)}">
													<p class="lie">
														<strong><fmt:message
																key="create_test.strong.answer" />${answer.id}: </strong>${answer.answerText}
													</p>
												</c:if>
												<c:if
													test="${(answer.correct eq true) and (usersAnswer.correct ne true)}">
													<p class="correct">
														<strong>Answer ${answer.id}: </strong>${answer.answerText}
													</p>
												</c:if>
												<c:if
													test="${(answer.correct ne true) and (usersAnswer.correct ne true)}">
													<p>
														<strong><fmt:message
																key="create_test.strong.answer" />${answer.id}: </strong>${answer.answerText}
													</p>
												</c:if>
											</c:if>
										</c:forEach>
									</c:forEach>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</c:forEach>
			</div>
			<p>
				<strong><fmt:message key="test_result.strong.result" /></strong>${usersTest.testResult}%</p>
			<br> <br> <br>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>