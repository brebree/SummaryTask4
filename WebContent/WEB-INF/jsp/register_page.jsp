<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Register Page" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<form id="login_form" action="controller" method="post"
			onsubmit="return checkPass()">

			<input type="hidden" name="command" value="submitRegister" />

			<fieldset>
				<legend>
					<fmt:message key="login.legend.login" />
				</legend>
				<input name="login" required="required" /><br>
			</fieldset>
			<c:if test="${not empty incorrectLogin}">
				<p>Login ${incorrectLogin} is already exists</p>
			</c:if>
			<br>
			<fieldset>
				<legend>
					<fmt:message key="login.legend.password" />
				</legend>
				<input id="pass1" type="password" name="password"
					required="required" />
			</fieldset>
			<br>
			<fieldset>
				<legend>
					<fmt:message key="login.legend.password" />
				</legend>
				<input id="pass2" type="password" required="required" /> <span
					id="confirmMessage" class="confirmMessage"></span>
			</fieldset>
			<br>
			<fieldset>
				<legend>
					<fmt:message key="register.legend.firstName" />
				</legend>
				<input name="firstName" pattern="[a-zA-Z_а-яА-Я]+$" />
			</fieldset>
			<br>
			<fieldset>
				<legend>
					<fmt:message key="register.legend.lastName" />
				</legend>
				<input name="lastName" pattern="[a-zA-Z_а-яА-Я]+$" />
			</fieldset>
			<br>
			<fieldset>
				<legend>
					<fmt:message key="view_users.th.email" />
				</legend>
				<input name="email" type="email">
			</fieldset>
			<br /> <input type="submit"
				value="<fmt:message key="login.a.register" />">
		</form>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>