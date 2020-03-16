<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Edit Test" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<form name="test" action="controller" method="post">
			<input type="hidden" name="command" value="submitEditTest" />
			<div class="create_test">
				<div class="create_edit_text">
					<strong><fmt:message key="create_test.strong.testName" /></strong><br>
					<strong><fmt:message key="create_test.strong.subject" /></strong><br>
					<strong><fmt:message key="create_test.strong.complexity" /></strong><br>
					<strong><fmt:message key="create_test.strong.time" /></strong><br>
				</div>
				<c:forEach var="sj" items="${subjects}">
					<c:if test="${sj.id eq test.testSubjectId }">
						<c:set var="sub" value="${sj.name }"></c:set>
					</c:if>
				</c:forEach>
				<div class="create_input">
					<input type="text" name="testName" placeholder="${test.testName}"
						size="50"><br> <input type="text" name="subject"
						placeholder="${sub}" size="50"><br> <input
						pattern="^[0-9]+$" name="complexity"
						placeholder="${test.complexity}" size="5"><br> <input
						pattern="^[0-9]+$" name="time" placeholder="${test.testTime}"
						size="5"><br>
				</div>
			</div>
			<div class="questions"
				id="<fmt:message key="create_test.strong.question" />_<fmt:message key="create_test.strong.answer" />_<fmt:message key="create_test.button.addAnswer"/>">
				<c:forEach var="question" items="${test.questionList}">
					<div class="question">
						<strong><fmt:message key="create_test.strong.question" />${question.id}:
						</strong> <input placeholder="${question.questionText}"
							class="questionInput" type="text" name="question_${question.id}"
							size="45"><input type="button"
							value="<fmt:message key="edit_test.button.delete" />"
							onclick="location.href='controller?command=deleteQuestion&id=${question.id}';"><br>
						<div class="answers">
							<c:forEach var="answer" items="${question.answerList}">
								<br />
								<strong><fmt:message key="create_test.strong.answer" />${answer.id}:
								</strong>
								<input placeholder="${answer.answerText}" class="answer"
									type="text" name="answer_${question.id}_${answer.id}" size="45">
								<c:if test="${answer.correct}">
									<input type="checkbox" checked="checked"
										name="box_${question.id}_${answer.id}" value="correct">
								</c:if>
								<c:if test="${!answer.correct}">
									<input type="checkbox" name="box_${question.id}_${answer.id}"
										value="correct">
								</c:if>
								<input type="button"
									value="<fmt:message key="edit_test.button.delete" />"
									onclick="location.href='controller?command=deleteAnswer&id=${question.id}_${answer.id}';">
							</c:forEach>
						</div>
						<input class="answers" type="button" name="answerButton_question1"
							value="<fmt:message key="create_test.button.addAnswer"/>"
							onClick="addAnswer(event);">
					</div>
				</c:forEach>
			</div>
			<input class="questions" type="button" name="questionButton"
				value="<fmt:message key="create_test.button.addQuestion"/>"
				onClick="addQuestion(event);"> <br /> <br /> <input
				type="submit" value="<fmt:message key="edit_test.button.editTest"/>">
		</form>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>