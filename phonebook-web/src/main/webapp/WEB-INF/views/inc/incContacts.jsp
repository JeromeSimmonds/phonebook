<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="/WEB-INF/functions.tld" prefix="jspb" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<h1><spring:message code="label_contacts" /> <a href="/search" class="button right"><spring:message code="label_search_contacts" /></a><a href="/post" class="button right"><spring:message code="label_new_contact" /></a></h1>

<c:if test="${!empty flashOk}">
<c:choose>
<c:when test="${flashOk == 'confirmation'}"><p class="success"><spring:message code="txt_contact_saved" /></p></c:when>
<c:when test="${flashOk == 'deleted'}"><p class="success"><spring:message code="txt_contact_deleted" /></p></c:when>
</c:choose>
</c:if>

<c:choose>
<c:when test="${!empty contacts}">
<div id="contactsContainer">
	<h3>${contacts.totalAvailable} contact(s)
	<span id="formFilters"><spring:message code="label_sort_by" /> <select id="filterSort">
		<option value="Alpha"<c:if test="${empty param.s || param.s == 'Alpha'}"> selected="selected"</c:if>>Last Name A - Z</option>
		<option value="RevAlpha"<c:if test="${!empty param.s && param.s == 'RevAlpha'}"> selected="selected"</c:if>>Last Name Z - A</option>
		<option value="MostRecent"<c:if test="${!empty param.s && param.s == 'MostRecent'}"> selected="selected"</c:if>>Most Recent</option>
	</select>
	</span>
	</h3>
	<ul id="contactsList">
		<c:forEach items="${contacts.results}" var="contact">
		<li data-contact-id="${contact.id}">
			<div class="contactAvatar"><img src="<c:choose><c:when test="${!empty contact.avatar}">${exposedProperties.property['avatar.path']}${contact.avatar}</c:when><c:otherwise>img/avatar.png</c:otherwise></c:choose>"/></div>
			<div class="contactContainer">
				<span>
					<strong>${contact.firstName} ${contact.lastName}</strong>
					<span class="contactDetails"><c:if test="${!empty contact.email}"><br /><spring:message code="label_email" />: <strong>${contact.email}</strong></c:if>
					<c:forEach items="${contact.phoneNumbers}" var="phone"><br /><span style="text-transform: capitalize">${jspb:phoneNumberType(phone)}:</span> <strong>${phone.number}</strong></c:forEach></span>
					<br /><span class="contactDate">Created <fmt:formatDate value="${contact.created}" pattern="yyyy/MM/dd hh:mm:ss"/></span>
				</span>
				<a href="/post?id=${contact.id}" class="button right"><spring:message code="label_edit" /></a> <a href="/delete?id=${contact.id}" class="button right btnDelete"><spring:message code="label_delete" /></a>
			</div>
		</li>
		</c:forEach>
	</ul>
	<c:if test="${contacts.totalAvailable > nbPerPage}"><div id="pager">
	<c:forEach begin="1" end="${contacts.totalAvailable / nbPerPage + (contacts.totalAvailable % nbPerPage > 0 ? 1 : 0)}" var="p"><a href="/contacts?p=${p}<c:if test="${!empty param.s}">&s=${param.s}</c:if>"<c:if test="${page == p}"> class="current"</c:if>>${p}</a></c:forEach>
	</div></c:if>
</div>
</c:when>
<c:otherwise>
<h2 style="margin-top: 20px"><spring:message code="txt_no_contacts" />.</h2>
</c:otherwise>
</c:choose>
