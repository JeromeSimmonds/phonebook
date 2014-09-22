<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
$(document).ready(function() {
	$('#filterSort').on('change', function (e) {
		sortContacts($('#filterSort').val());
	});
	
	$('.btnDelete').click(function(e) {
		e.preventDefault();
		if (confirm('<spring:message code="txt_q_delete" />'))
			window.location.href = $(this).attr('href');
	});
});

function sortContacts(sort) {
	window.location.href = '/contacts?s=' + sort;
}
</script>