<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BundlesNVC</title>

<!-- ExtJS css -->
<link rel="stylesheet" type="text/css"
	href="ext-3.2.1/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />

<!-- jQuery js -->
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="js/json2.min.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jquery.maskedinput-1.2.2.min.js"></script>
<script type="text/javascript" src="js/jquery.password.js"></script>
<script type="text/javascript" src="js/caret.js"></script>

<!-- ExtJS js -->
<script src="ext-3.2.1/adapter/ext/ext-base.js"></script>
<script src="ext-3.2.1/ext-all.js"></script>

<script>
var xmlCustomizationInput = '<%= request.getParameter("xmlCustomizationInput") %>';
</script>

<!-- App js -->
<script src="formjs/nvcForm.js"></script>

</head>
<body>
<form name="myform" method="post">
<input type="hidden" name="xmlCustomizationOutput" id="xmlCustomizationOutput"/>
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top" class="mainBg">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top" class="topBg">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="left" valign="top">
								<div class="image"><img src="css/images/logo.jpg"
									height="88" />
								<h2><span><font color="white">Bundle</font> <font
									color="yellow">Customizer</font></span></h2>
								</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td align="center" valign="top">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" align="center" valign="top">&nbsp;</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td align="center" valign="top">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" align="center">
								<table>
									<tr>
										<td>
											<div id="formContent" align="left"></div>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td height="80" align="center" valign="top">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

</body>
</html>