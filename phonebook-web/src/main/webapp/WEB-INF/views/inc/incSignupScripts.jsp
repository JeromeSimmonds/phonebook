<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
$(document).ready(function() {
	$('#command').on('submit', function(e) {
		var self = this;
		e.preventDefault();
		$('span.error').text('');
		var email = $('#email').val().trim();
		var username = $('#username').val().trim();
		var password = $('#password').val().trim();
		if (email == '') {
			$('#errorEmail').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.email.required" />');
			return;
		} else if (!email.isEmail()) {
			$('#errorEmail').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.email.invalid" />');
			return;
		} else if (username == '') {
			$('#errorUsername').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.username.required" />');
			return;
		} else if (username.length < 2 || username.length > 40) {
			$('#errorUsername').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.username.invalid" />');
			return;
		} else if (password == '') {
			$('#errorPassword').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.password.required" />');
			return;
		} else if (password.length < 8 || password.length > 20) {
			$('#errorPassword').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.password.wrongsize" />');
			return;
		}
		
		$.ajax({
			url: '/R/check',
			data: {email: email, username: username}
		}).done(function(data) {
			if (data != null && data.result != null) {
				if (data.result.email == true)
					$('#errorEmail').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.email.notavailable" />');
				if (data.result.username == true)
					$('#errorUsername').text('<spring:message code="com.jeromesimmonds.phonebook.constraints.username.notavailable" />');
				if (data.result.email == false && data.result.username == false)
					self.submit();
			}
		});
	});
});
</script>