<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h1><spring:message code="label_sign_up" /></h1>

<c:choose>
<c:when test="${!empty errorCode}">
<p><spring:message code="${errorCode}" /></p>
</c:when>
<c:when test="${!empty flashOk}">
<p>Thank you for signing up!</p>
<p>We've emailed you a link, please click it to confirm your account.<br />
<strong>The link will expire in 2 days.</strong><br />
If you don't receive it within a few minutes, check your email's spam and junk folders.</p>
</c:when>
<c:otherwise>
<form:form action="/signup" method="POST" commandName="command" cssClass="formCentered">
<form:errors path="*"><div class="error"><spring:message code="txt_form_errors" /></div></form:errors> 
<div>
	<label for="email"><spring:message code="security_signup_form_email" />*</label>
	<div><form:input id="email" path="email" cssClass="email" maxlength="100" /><br />
	<span class="error" id="errorEmail"><spring:bind path="email"><c:if test="${status.error}"><c:out value="${status.errorMessages[0]}"/></c:if></spring:bind></span></div>
</div>
<div>
	<label for="username"><spring:message code="security_signup_username" />*</label>
	<div><form:input id="username" path="username" maxlength="40" /><span class="tooltip">2 to 40 alphanumeric characters, - or _</span><br />
	<span class="error" id="errorUsername"><spring:bind path="username"><c:if test="${status.error}"><c:out value="${status.errorMessages[0]}"/></c:if></spring:bind></span></div>
</div>
<div>
	<label for="password"><spring:message code="security_signup_form_password" />*</label>
	<div><form:password id="password" path="password" maxlength="20" /><span class="tooltip">8 to 20 characters</span><br />
	<span class="error" id="errorPassword"><spring:bind path="password"><c:if test="${status.error}"><c:out value="${status.errorMessages[0]}"/></c:if></spring:bind></span></div>
</div>
<div>
	<p style="font-size: .8em">By clicking Sign Up, you agree to our Terms and that you have read<br />our Data Use Policy, including our Cookie Use.</p>
	<input type="submit" value="<spring:message code="label_sign_up" />" class="button" /><p style="font-size: 0.8em">* required</p>
</div>
</form:form>
<div><a href="/login"><spring:message code="security_signup_already_account" /></a></div>
</c:otherwise>
</c:choose>