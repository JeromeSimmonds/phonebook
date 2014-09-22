<%@page import="com.jeromesimmonds.phonebook.core.be.PhoneNumber"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/functions.tld" prefix="jspb" %>

<h1><c:choose><c:when test="${!empty command && command.id > 0}"><spring:message code="label_edit" /></c:when><c:otherwise><spring:message code="label_new" /></c:otherwise></c:choose> <spring:message code="label_contact" /></h1>

<form:form action="/post" method="POST" commandName="command" cssClass="formCentered" enctype="multipart/form-data">
<form:hidden path="id"/>
<form:hidden path="photo"/>
<spring:hasBindErrors name="command"><c:forEach items="${errors.globalErrors}" var="error">  
<div class="error"><spring:message code="${error.code}" /></div>  
</c:forEach></spring:hasBindErrors>  
<div>
	<label for="firstName"><spring:message code="label_first_name" /></label>
	<div><form:input id="firstName" path="firstName" maxlength="30" /><br /><form:errors path="firstName" cssClass="error" element="span" /></div>
</div>
<div>
	<label for="lastName"><spring:message code="label_last_name" /></label>
	<div><form:input id="lastName" path="lastName" maxlength="30" /><br /><form:errors path="lastName" cssClass="error" element="span" /></div>
</div>
<div>
	<label for="email"><spring:message code="label_email" /></label>
	<div><form:input id="email" path="email" cssClass="email" maxlength="100" /><br /><form:errors path="email" cssClass="error" element="span" /></div>
</div>
<c:forEach items="${command.phoneNumbers}" var="phone" varStatus="status">
<div class="phoneContainer">
	<label><spring:message code="label_phone_number" /><!-- : ${jspb:phoneNumberType(phone)} --></label>
	<div><form:select path="phoneNumbers[${status.index}].type">
		<form:option value="<%= PhoneNumber.TYPE_HOME %>" label="Home"/>
		<form:option value="<%= PhoneNumber.TYPE_OFFICE %>" label="Office"/>
		<form:option value="<%= PhoneNumber.TYPE_MOBILE %>" label="Mobile"/>
		<form:option value="<%= PhoneNumber.TYPE_OTHER %>" label="Other"/>
	</form:select><form:input id="phone${status.index}" path="phoneNumbers[${status.index}].number" maxlength="20" /></div>
</div>
</c:forEach>
<c:if test="${fn:length(command.phoneNumbers) == 0}">
<div class="phoneContainer">
	<label><spring:message code="label_phone_number" /></label>
	<div><form:select path="phoneNumbers[0].type">
		<form:option value="<%= PhoneNumber.TYPE_HOME %>" label="Home"/>
		<form:option value="<%= PhoneNumber.TYPE_OFFICE %>" label="Office"/>
		<form:option value="<%= PhoneNumber.TYPE_MOBILE %>" label="Mobile"/>
		<form:option value="<%= PhoneNumber.TYPE_OTHER %>" label="Other"/>
	</form:select><form:input id="phone0" path="phoneNumbers[0].number" maxlength="20" /></div>
</div>
</c:if>
<div><a href="" id="addPhoneNumber"><spring:message code="label_add_number" /></a></div>
<div>
	<label for=""><spring:message code="label_photo" /></label>
	<div><c:if test="${!empty command.photo}"><img src="${exposedProperties.property['avatar.path']}${command.photo}" width="40" height="40" style="margin-bottom: 8px"/><br /><a href="deletePhoto?contactId=${command.id}">Delete photo</a> or, to change it:<br /></c:if><input type="file" name="photoFile" id="photoFile" style="padding: 0"/></div>
</div>
<div><input type="submit" value="<spring:message code="label_submit" />" /></div>
</form:form>
