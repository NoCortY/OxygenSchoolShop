/**
 * 
 */
$.ajaxSetup({"error":handleError});
function handleError(XMLHttpRequest, textStatus, errorThrown){
	if(XMLHttpRequest.status == -1006){
		window.top.location = root + "/SuperAdminEntrance.jsp";
	}
}