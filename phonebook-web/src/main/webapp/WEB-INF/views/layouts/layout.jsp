<jsp:directive.page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="_bodyOnLoad"><tiles:getAsString name="bodyOnLoad"/></c:set>
<c:set var="_bodyOnUnLoad"><tiles:getAsString name="bodyOnUnLoad"/></c:set>
<c:set var="_bodyId"><tiles:getAsString name="bodyId"/></c:set>
<c:set var="_navItem" scope="request"><tiles:getAsString name="navItem"/></c:set>
<c:set var="_containerClass"><tiles:getAsString name="containerClass"/></c:set>
<sec:authorize ifAnyGranted="ROLE_USER">
<sec:authentication property="principal.user" var="currentUser" scope="request" />
</sec:authorize>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><tiles:getAsString name="title"/></title>
	<meta name="description" content="<tiles:getAsString name="metaDescription"/>">
	<meta name="keywords" content="<tiles:getAsString name="metaKeywords"/>">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="/css/normalize.css">
	<link rel="stylesheet" href="/css/main.css">
	<link rel="stylesheet" href="/css/styles.css">
	<script src="/js/vendor/modernizr-2.6.2.min.js"></script>
</head>
<body<c:if test="${!empty _bodyOnLoad}"> onload="${_bodyOnLoad}"</c:if><c:if test="${!empty _bodyOnUnload}"> onunload="${_bodyOnUnload}"</c:if> id="${_bodyId}">
<tiles:insertAttribute name="header"/>
<div class="container <c:if test="${!empty _containerClass}">${_containerClass}</c:if>">
	<tiles:insertAttribute name="body"/>
</div>
<tiles:insertAttribute name="footer"/>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-1.10.2.min.js"><\/script>')</script>
<script src="js/plugins.js"></script>
<script src="js/main.js"></script>
<tiles:insertAttribute name="footerScripts"/>
</body>
</html>