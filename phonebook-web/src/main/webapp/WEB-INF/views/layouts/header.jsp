<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<header>
	<div class="container">
		<ul class="nav left">
			<li><a href="/" class="btnHome<c:if test="${!empty _navItem && (_navItem == 'home' || _navItem == 'contacts')}"> active</c:if>" title="<spring:message code="label_home" />"><span><spring:message code="label_home" /></span></a></li>
			<%-- <li><a href="/">Search</a></li> --%>
		</ul>
		<ul class="nav right">
			<sec:authorize ifNotGranted="ROLE_USER">
			<li><a href="/login" class="btnConnect<c:if test="${!empty _navItem && (_navItem == 'login' || _navItem == 'forgotPassword' || _navItem == 'signup')}"> active</c:if>" title="<spring:message code="security_log_in" />"><span><spring:message code="security_log_in" /></span></a></li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_ADMIN">
			<li><a href="/admin">Admin</a></li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_USER">
			<li class="liUsername" style="color: #FFF"><sec:authentication property="principal.username" /></li>
			<li><a href="/account" class="btnConnect<c:if test="${!empty _navItem && _navItem == 'account'}"> active</c:if>" title="<spring:message code="label_my_account" />"><span><spring:message code="label_my_account" /></span></a></li>
			</sec:authorize> 
		</ul>
	</div>
</header>