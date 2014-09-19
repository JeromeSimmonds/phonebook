<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h1><spring:message code="label_password_forgotten" /></h1>

<c:choose>
<c:when test="${!empty errorCode}">
<p><spring:message code="${errorCode}" /></p>
</c:when>
<c:when test="${!empty flashOk && flashOk == 'sent'}">
<p><spring:message code="forgotPassword.sent" htmlEscape="false" /></p>
</c:when>
<c:when test="${!empty token}">
<div><spring:message code="label_enter_new_password" /></div>
<form:form action="/forgotPassword/${token}" method="POST" commandName="command" cssClass="formCentered">
<div>
	<label for="password"><spring:message code="security_signup_form_password" /></label>
	<div><form:password id="password" path="password" maxlength="15" /><br /><spring:bind path="password"><c:if test="${status.error}"><span class="error"><c:out value="${status.errorMessages[0]}"/></span></c:if></spring:bind></div>
</div>
<div>
	<label for="password"><spring:message code="security_signup_form_password_confirmation" /></label>
	<div><form:password id="passwordConfirmation" path="passwordConfirmation" maxlength="15" /><br /><spring:bind path="passwordConfirmation"><c:if test="${status.error}"><span class="error"><c:out value="${status.errorMessages[0]}"/></span></c:if></spring:bind><%-- <form:errors path="passwordConfirmation" cssClass="error" element="span" />--%></div>
</div>
<div><input type="submit" value="Submit" class="button" /></div>
</form:form>
</c:when>
<c:otherwise>
<p><spring:message code="txt_password_forgotten" htmlEscape="false" /></p>
<form:form action="/forgotPassword" method="POST" commandName="command" cssClass="formCentered">
<div>
	<label for="email"><spring:message	code="security_signup_form_email" /></label>
	<div><form:input id="email" path="email" maxlength="100" cssClass="email"/><br /><form:errors path="*" cssClass="error" element="span" /></div>
</div>
<div><input type="submit" value="<spring:message code="label_submit" />" class="button" /></div>
</form:form>
<div class="mt25"><a href="/login"><spring:message code="label_remember_password" /></a></div>
</c:otherwise>
</c:choose>