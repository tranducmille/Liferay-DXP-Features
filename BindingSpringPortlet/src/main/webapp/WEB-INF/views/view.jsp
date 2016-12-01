<%@ include file="init.jsp" %> 
ISBN Number: <input type="text" value="" name="<portlet:namespace/>isbnReceiver" id="<portlet:namespace/>isbnReceiver">
<script type="text/javascript">
	Liferay.on(
	   'TriggerMessage',
	   function(event) {
	     var msg = event.msg;
	     jQuery('#<portlet:namespace/>isbnReceiver').val(msg);
	   }
	);
</script>