<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="${language}">

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<form id="login_form" action="controller" method="post">

			<input type="hidden" name="command" value="login" />

			<fieldset>
				<legend>
					<fmt:message key="login.legend.login" />
				</legend>
				<input name="login"  required="required"/><br />
			</fieldset>
			<br />
			<fieldset>
				<legend>
					<fmt:message key="login.legend.password" />
				</legend>
				<input type="password" name="password" required="required"/>
			</fieldset>
			<br /> <input type="submit"
				value="<fmt:message key="login.button.submit" />"> &nbsp; <a
				href="controller?command=viewRegister"><fmt:message
					key="login.a.register" /></a>
		</form>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>