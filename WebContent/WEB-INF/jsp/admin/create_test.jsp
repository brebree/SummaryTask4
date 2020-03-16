<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Create Test" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<form name="test" action="controller" method="post">
			<input type="hidden" name="command" value="submitCreateTest" />
			<div class="create_test">
				<div class="create_edit_text">
					<strong><fmt:message key="create_test.strong.testName" /></strong><br>
					<strong><fmt:message key="create_test.strong.subject" /></strong><br>
					<strong><fmt:message key="create_test.strong.complexity" /></strong><br>
					<strong><fmt:message key="create_test.strong.time" /></strong>
				</div>
				<div class="create_input">
					<input type="text" name="testName" required="required" size="50"><br>
					<input type="text" name="subject" required="required" size="50"><br>
					<input name="complexity" pattern="^[0-9]+$" size="5" required="required"><br>
					<input name="time" pattern="^[0-9]+$" size="5" required="required">
				</div>

			</div>
			<div class="questions"
				id="<fmt:message key="create_test.strong.question" />_<fmt:message key="create_test.strong.answer" />_<fmt:message key="create_test.button.addAnswer"/>">
				<div class="question">
					<strong><fmt:message key="create_test.strong.question" />1:
					</strong> <input class="questionInput" type="text" name="question_1"
						size="45" required="required"><br>
					<div class="answers">
						<strong><fmt:message key="create_test.strong.answer" />1:
						</strong> <input class="answer" type="text" name="answer_1_1" size="45"
							required="required"><input type="checkbox" name="box_1_1"
							value="correct">
					</div>
					<input class="answers" type="button" name="answerButton_question1"
						value="<fmt:message key="create_test.button.addAnswer"/>"
						onClick="addAnswer(event);">
				</div>
			</div>
			<input class="questions" type="button" name="questionButton"
				value="<fmt:message key="create_test.button.addQuestion"/>"
				onClick="addQuestion(event);"><br /> <br /> <input
				type="submit"
				value="<fmt:message key="create_test.button.createTest"/>">
		</form>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>