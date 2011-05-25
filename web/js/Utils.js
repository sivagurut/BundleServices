
function submitForm() {
	document.forms[0].submit();
}
function submitURL(url) {
	document.forms[0].action = url;
	document.forms[0].submit();
}
function submitDispatchForm(dispatch) {
	document.getElementById('dispatch').value = dispatch;
	document.forms[0].submit();
}
