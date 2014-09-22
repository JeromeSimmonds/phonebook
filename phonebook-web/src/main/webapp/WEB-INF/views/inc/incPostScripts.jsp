<%@page import="com.jeromesimmonds.phonebook.core.be.PhoneNumber"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="/js/vendor/mustache.js"></script>
<script id="tplPhoneNumber" type="text/template">
<div class="phoneContainer">
	<label><spring:message code="label_phone_number" /></label>
	<div><select name="phoneNumbers[{{index}}].type">
		<option value="<%= PhoneNumber.TYPE_HOME %>" label="Home"/>
		<option value="<%= PhoneNumber.TYPE_OFFICE %>" label="Office"/>
		<option value="<%= PhoneNumber.TYPE_MOBILE %>" label="Mobile"/>
		<option value="<%= PhoneNumber.TYPE_OTHER %>" label="Other"/>
	</select><input type="text" id="phone{{index}}" name="phoneNumbers[{{index}}].number" maxlength="20" /></div>
</div>
</script>
<script>
var tplPhoneNumberCompiled;

$(document).ready(function() {
	$('#addPhoneNumber').click(function(e) {
		e.preventDefault();
		var newField = true;
		var elts = $('input[name^="phone"]');
		$(elts).each(function(index) {
			if ($(this).val() == '') {
				newField = false;
				return false;
			}
		});
		if (newField == false)
			alert('<spring:message code="txt_fill_phone" />');
		else {
			if (tplPhoneNumberCompiled == null) {
				tplPhoneNumberCompiled = $('#tplPhoneNumber').html();
				Mustache.parse(tplPhoneNumberCompiled);
			}
			$('.phoneContainer:last').after(Mustache.render(tplPhoneNumberCompiled, {'index': elts.length}));
		}
	});
});
</script>