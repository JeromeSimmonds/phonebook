<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<c:set var="sectionEmail" scope="request"><%= com.jeromesimmonds.phonebook.web.controller.AccountController.SECTION_EMAIL %></c:set>
<c:set var="sectionPassword" scope="request"><%= com.jeromesimmonds.phonebook.web.controller.AccountController.SECTION_PASSWORD %></c:set>

<h1><spring:message code="label_my_account" /></h1>

<ul class="ulContent">
	<li<c:if test="${empty section}"> class="active"</c:if>><a href="/account"><spring:message code="label_personal_info" /></a></li>
	<li<c:if test="${!empty section && (section == sectionEmail || section == sectionEmailChanged)}"> class="active"</c:if>><a href="/account${sectionEmail}"><spring:message code="label_change_email" /></a></li>
	<li<c:if test="${!empty section && section == sectionPassword}"> class="active"</c:if>><a href="/account${sectionPassword}"><spring:message code="label_change_password" /></a></li>
</ul>

<c:choose>
<c:when test="${!empty section && section == sectionEmail}">
<%-- ---------- Email address ---------- --%>
<p>If you change your email address, you'll need to confirm your new email address.<br/>
We'll send you an email with a link, please click on it to confirm your new email address and be able to log in again to your account.<br />
For security reasons we'll also send you an email to your current email address.<br />
Your current email address is <strong><sec:authentication property="principal.user.email" /></strong></p>
<form:form action="/account/email" method="POST" commandName="command" cssClass="formCentered">
<div>
	<label for="email">New email address*</label>
	<div><form:input id="email" path="email" cssClass="email" maxlength="100" /><br /><form:errors path="email" cssClass="error" element="span" /></div>
</div>
<div>
	<label for="email">New email address confirmation*</label>
	<div><form:input id="emailConfirmation" path="emailConfirmation" cssClass="email" maxlength="100" /><br /><form:errors path="emailConfirmation" cssClass="error" element="span" /></div>
</div>
<div><p>You'll be logged out after submitting the form.</p><input type="submit" value="Save" /> <input type="button" value="Cancel" onclick="window.location = '/account'" /><p style="font-size: 0.8em">* required</p></div>
</form:form>
</c:when>
<c:when test="${!empty section && section == sectionPassword}">
<%-- ---------- Password ---------- --%>
	<c:choose>
	<c:when test="${pageContext.request.queryString == 'success'}">
	<p class="success">Your password has been changed successfully!</p>
	</c:when>
	<c:otherwise>
	<p>To change your password you must enter your current password, new password and then re-enter the new password for confirmation.<br />
	Your new password must be at least 8 characters long and we recommend to use numbers and letters for increased security.</p>
	<form:form action="/account/password" method="POST" commandName="command" cssClass="formCentered">
	<div>
		<label for="password"><spring:message code="security_login_form_current_password" />*</label>
		<div><form:password id="password" path="password" maxlength="20" /><br /><form:errors path="password" cssClass="error" element="span" /></div>
	</div>
	<div>
		<label for="password"><spring:message code="security_login_form_new_password" />*</label>
		<div><form:password id="newPassword" path="newPassword" maxlength="20" /><br /><form:errors path="newPassword" cssClass="error" element="span" /></div>
	</div>
	<div>
		<label for="password"><spring:message code="security_signup_form_password_confirmation" />*</label>
		<div><form:password id="passwordConfirmation" path="passwordConfirmation" maxlength="20" /><br /><form:errors path="passwordConfirmation" cssClass="error" element="span" /></div>
	</div>
	<div><input type="submit" value="Save" /><p style="font-size: 0.8em">* required</p></div>
	</form:form>
	</c:otherwise>
	</c:choose>
</c:when>
<c:otherwise>
<%-- ---------- Personal Info ----------
<%-- <p><strong><sec:authentication property="principal.username" /></strong></p> --%>
<c:if test="${!empty flashOk && flashOk == 'success'}">
<p class="success"><spring:message code="txt_personal_info_changed" /></p>
</c:if>
<form:form action="/account" method="POST" commandName="command" cssClass="formCentered" enctype="multipart/form-data">
<div>
	<label for="username"><spring:message code="label_username" /></label>
	<div><form:input id="username" path="username"/></div>
</div>
<div><input type="submit" value="<spring:message code="label_submit" />" style="margin-top: 15px" /><!--  <input type="button" value="Cancel" /> --></div>
</form:form>
</c:otherwise>
</c:choose>


