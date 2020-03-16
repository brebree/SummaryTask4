<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="View All Users" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="content_container">
		<table>
			<thead>
				<tr>
					<th><fmt:message key="view_users.th.usersId" /></th>
					<th><fmt:message key="login.legend.login" /></th>
					<th><fmt:message key="register.legend.firstName" /></th>
					<th><fmt:message key="register.legend.lastName" /></th>
					<th><fmt:message key="view_users.th.result" /></th>
					<th><fmt:message key="view_users.th.status" /></th>
					<th><fmt:message key="view_users.th.email" /></th>
					<th><fmt:message key="view_users.th.ban" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${users }">
					<c:if test="${user.roleId ne 0 }">
						<tr>
							<td>${user.id }</td>
							<td>${user.login }</td>
							<td>${user.firstName }</td>
							<td>${user.lastName }</td>
							<td>${user.testsResult }%</td>
							<c:if test="${user.statusId == 2}">
								<td><fmt:message key="view_users.td.active" /></td>
							</c:if>
							<c:if test="${user.statusId == 1}">
								<td><fmt:message key="view_users.td.banned" /></td>
							</c:if>
							<td>${user.email }</td>
							<td><form action="controller?command=banUnban&id=${user.id}"
									method="post">
									<input type="submit"
										value="<fmt:message key="view_users.th.ban" />">
								</form></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>