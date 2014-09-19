<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1><spring:message code="security_log_in" /></h1>

<c:choose>
<c:when test="${!empty SPRING_SECURITY_LAST_EXCEPTION && SPRING_SECURITY_LAST_EXCEPTION.message == 'User account has expired'}">
<p><spring:message code="security_login_account_not_confirmed" /></p>
<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />
</c:when>
<c:otherwise>
<c:if test="${!empty SPRING_SECURITY_LAST_EXCEPTION}">
<p class="error"><spring:message code="security_login_unsuccessful" />
	<!-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /> -->
</p>
<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />
</c:if>

<c:if test="${!empty flashOk && flashOk == 'confirmation'}">
<p class="success"><spring:message code="security_signup_token_success" /></p>
</c:if>

<c:if test="${!empty param.r}">
<c:set value="?r=${param.r}" var="redirect" />
<p>Please log in to post a comment</p>
</c:if>

<form action="/login/authenticate${!empty redirect ? redirect : '' }" method="POST" class="formCentered">
<div>
	<label for="j_username"><spring:message	code="security_login_form_email" /></label>
	<div><input type="text" id="j_username" name="j_username" class="email" maxlength="100" /></div>
</div>
<div>
	<label for="j_password"><spring:message	code="security_login_form_password" /></label>
	<div><input type="password" id="j_password" name="j_password" maxlength="20" /></div>
</div>
<div>
	<label for="_spring_security_remember_me"><spring:message code="security_login_form_remember_me" /></label>
	<div style="padding-top: 3px; padding-bottom: 3px"><input type="checkbox" name="_spring_security_remember_me" value="true"/></div>
</div>
<div><input type="submit" class="button" value="<spring:message code="security_log_in" />" /></div>
</form>
<div class="mt25"><a href="/forgotPassword"><spring:message code="security_login_forgot_password" /></a><span class="separator"></span><a href="/signup"><spring:message code="security_login_no_account" /></a></div>
</c:otherwise>
</c:choose>
