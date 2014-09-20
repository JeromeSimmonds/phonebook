<script>
$(document).ready(function() {
	$('#filterSort').on('change', function (e) {
		sortContacts($('#filterSort').val());
	});
});

function sortContacts(sort) {
	window.location.href = '/contacts?s=' + sort;
}
</script>