<%@ include file="init.jsp" %> 
Enter ISBN Number: <input type="text" value="" name="<portlet:namespace/>isbn" id="<portlet:namespace/>isbn">
<input type="button" id="submitBtn" name="Submit" value="Submit">
<script type="text/javascript">
	jQuery(function () {
	      jQuery('#submitBtn').click(
	        function(event) {
	          var msg = jQuery("#<portlet:namespace/>isbn").val();
	          Liferay.fire('TriggerMessage', {msg: msg});
	          return false;
	        }
	      )
	});
</script>