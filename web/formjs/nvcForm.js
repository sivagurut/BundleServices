var returnURL = "";
var helpTextFormatted = "";
var helpTitleFormatted = "";
var visitId = 0;
var fieldSetArray = new Array();
// Immediate submit Flag to bypass client side validations
var immediateFlag;
//Testing purpose
$(window).load(function() {
	// Render form elements
		callAjax(xmlCustomizationInput, fieldSetArray);
	});

// Redirect to url with xml data
function redirectForm(url, xmlData) {
	document.myform.action = url;
	document.getElementById("xmlCustomizationOutput").value = xmlData;
	document.myform.submit();
	return true;
}
// Calling ExtJS to render form elements
function callEXTJS() {
	Ext.onReady(function() {

		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'under';
		// Turn on validation errors beside the field globally
			Ext.form.Field.prototype.blankText = 'Field is Required!';

			// Confirm Text Password validation
			Ext.apply(Ext.form.VTypes, {
				password : function(val, field) {
					if (field.initialPassField) {
						var pwd = Ext.getCmp(field.initialPassField);
						return (val == pwd.getValue());
					}
					return true;
				},
				passwordText : 'The passwords entered does not match!'
			});

			var onSuccessOrFail = function(form, action) {

				var formPanel = Ext.getCmp('myFormPanel');
				formPanel.el.unmask();

				// If Success from validations
				if (action.result.responseData.status == 'Success') {

					var theDiv = Ext.getDom("errorMsgDiv");
					if (theDiv.nodeType || theDiv.dom) {
						theDiv.style.display = 'none';
					}
					theDiv = Ext.getDom("separator");
					if (theDiv.nodeType) {
						theDiv.style.display = 'none';
					}

					// Creating the new form components, related to use case2
					var fieldSetArrayNew = new Array();
					processJSONResponse(action.result.cusResponse, fieldSetArrayNew);
					var formPanelItems = fieldSetArrayNew;

					// Clearing out the existing form content in the div component
					var theDivNew = Ext.get("formContent");
					theDivNew.dom.innerHTML = "";
					var nvcFormNew = new Ext.FormPanel(
							{
								frame : true,
								id : 'myFormPanel',
								title : 'Bundle Customizations - <font class="redCls">*</font> Required Fields<br/><div id="separator" class="displayNone"></div><pre id="errorMsgDiv" class="errorMsgCls"></pre>',
								bodyStyle : 'padding:5px 5px 0',
								width : 850,
								labelAlign : 'top',
								layout : 'form',
								monitorValid : true,
								items : formPanelItems,
								buttons : formPanelButtons
							});
					nvcFormNew.render("formContent");

				} else if (action.result.responseData.status == 'Error') {

					var theDiv = Ext.get("errorMsgDiv");
					theDiv.dom.style.display = 'block';
					theDiv.dom.style.color = 'red';
					theDiv.dom.innerHTML = "";
					var errorMessage = "";
					if (action.result.responseData.error != null && action.result.responseData.error != "") {
						var objJSON = action.result.responseData.error;
						var len = objJSON.length;
						var tempDiv = document.createElement("div");
						theDiv.dom.innerHTML = "";

						// Set error messages for the NVC validations
						for ( var i = 0; i < len; ++i) {
							var error = objJSON[i].split(":");
							if (error.length == 2) {
								Ext.getCmp(error[1]).el.dom.value = "";
								Ext.getCmp(error[1]).el.addClass("x-form-invalid");
								Ext.getCmp(error[1]).blankText = error[0];
							}
							errorMessage = theDiv.dom.innerHTML;
							theDiv.dom.innerHTML = errorMessage + "<br/>";
							errorMessage = theDiv.dom.innerHTML;
							theDiv.dom.innerHTML = errorMessage + " * " + error[0];
						}
						var theDiv = Ext.get("separator");
						theDiv.dom.style.display = 'block';
					}
				} else if (action.result.responseData.status == 'Redirect') {

					if (action.result.responseData.data != undefined) {
						var objJSONRedirect = action.result.responseData.data;
						redirectForm(objJSONRedirect[0], objJSONRedirect[1]);
					}
				}
			};

			// Cancel button handler
			var cancelHandler = function() {

				$.ajax( {
					url : "cancelBundle.json",
					type : "POST",
					data : "visitId=" + visitId + "&returnURL=" + returnURL,
					dataType : "text/json",
					success : function(data) {
						var objJSONRedirect = JSON.parse(data);
						redirectForm(objJSONRedirect.url, objJSONRedirect.cusResponse);
					}
				});
			};

			// continue/submit button handler
			var submitHandler = function() {

				var formPanel = Ext.getCmp('myFormPanel');
				formPanel.el.mask('Please wait.....', 'x-mask-loading');
				formPanel.getForm().submit( {
					method : 'POST',
					url : 'submitData.json',
					dataType : "text/json",
					success : onSuccessOrFail,
					failure : onSuccessOrFail

				});
			};

			// Reset Handler
			function resetHandler(obj) {
				Ext.getCmp(obj.el.dom.id).validate();
				// Ext.getCmp(SSNFieldId).validate();
			}

			// Form Buttons
			var formPanelButtons = [ {
				text : 'Continue',
				formBind : true,
				handler : submitHandler
			}, {
				text : 'Reset',
				handler : function() {
					var formPanel = Ext.getCmp('myFormPanel');
					formPanel.getForm().reset();
					formPanel.getForm().items.each(resetHandler);
				}
			}, {
				text : 'Cancel Bundle',
				handler : cancelHandler
			} ];

			var formPanelItems = [ fieldSetArray ];
			var nvcForm = new Ext.FormPanel(
					{
						frame : true,
						id : 'myFormPanel',
						title : 'Bundle Customizations - <font class="redCls">*</font> Required Fields<br/><div id="separator" class="displayNone"></div><pre id="errorMsgDiv" class="errorMsgCls"></pre>',
						bodyStyle : 'padding:5px 5px 0',
						width : 850,
						labelAlign : 'top',
						layout : 'form',
						monitorValid : true,
						items : formPanelItems,
						buttons : formPanelButtons
					});
			nvcForm.render("formContent");
		});
}

// Make Ajax Call to get data for page
function callAjax(xmlCustomizationInput, fieldSetArray) {

	if (xmlCustomizationInput != "null" && xmlCustomizationInput != null && xmlCustomizationInput != undefined) {
		$.ajax( {
			url : "getData.json",
			context : document.body,
			type : "POST",
			data : {
				xmlCustomizationInput : xmlCustomizationInput
			},
			dataType : "text/json",
			success : function(data) {
				processJSONResponse(data, fieldSetArray);
			},
			// internal server error
			error : function(err) {
			},
			// Show loading gif as the search is happening
			beforeSend : function() {
			},
			// hide loading gif on complete.
			complete : function() {
				callEXTJS();
			}
		});
	} else {
		// NVCFORM page refresh xmlCustomizationInput
		redirectForm("C3Stimulator", "");
	}
}

// Parsing the JSON response data
function processJSONResponse(data, fieldSetArray) {

	var jsonDataContent = JSON.parse(data);
	var jsonHiddenData = JSON.stringify(jsonDataContent.modelData);
	visitId = jsonDataContent.modelData.visitId;
	returnURL = jsonDataContent.modelData.punchOutURL;
	// Skip the max length and regular expression validation
	immediateFlag = jsonDataContent.immediateSubmitFlag;

	$(jsonDataContent.modelData.groupList.customizationGroup).each(
			function(index, customizationGroup) {

				var fieldsArray = new Array();
				$(customizationGroup.customizationList).each(
						function(index, customizationList) {
							$(customizationList.customization).each(function(index, customization) {

								var customizationName = "<b>" + customization.name + "<b>";
								if (customization.required) {
									customizationName = "<b>" + customization.name + "<font class='redCls'>*</font> </b>";
								}

								    // stripTagsAndQuote
									helpTextFormatted = stripTagsAndQuote(customization.helpText);

									if (customization.helpTitle != null && customization.helpType == 'InlineHTML') {
										customizationName = customizationName + "<div id='helpDiv'>" + helpTextFormatted + "</div>";
									} else if (customization.helpTitle != null && customization.helpType == 'MouseOver') {
										customizationName = customizationName + "<a href='#' title='" + helpTextFormatted + "'><font class='blueCls'>" + customization.helpTitle
												+ "</font></a>";
									} else if (customization.helpType == "ModalPopup") {
										// ************ TO BE CHANGED AFTER WE GET NEW URL FROM Bridgevine ************
										if (customization.helpText.indexOf("<a") != -1) {
											// Identifying the url from helpText
											var url = customization.helpText.substring(customization.helpText.indexOf("<a href=") + 9);
											url = url.substring(0, url.indexOf(">") - 1);
											customizationName = customizationName + "&nbsp;<a href='#' onclick='modelPopupRenderer(&#39;" + customization.helpTitle
													+ "&#39;, &#39;" + url + "&#39;)'><font class='blueCls'>" + customization.helpTitle + "</font></a>";
										}
									}

									if (customization.type == 'SecureFieldType') {

										fieldsArray[index] = componentFields(customization, customizationName);
									} else if (customization.type == 'PhoneFieldType') {

										fieldsArray[index] = phoneFieldComponent(customization, customizationName);
									} else if (customization.type == 'TextBox' || customization.type == 'MaskedTextBox') {

										if (customization.code == 'DOB') {
											fieldsArray[index] = dateFieldComponent(customization, customizationName);
										} else {
											fieldsArray[index] = componentFields(customization, customizationName);
										}
									} else if (customization.type == 'MemoBox') {

										fieldsArray[index] = memoBoxComponent(customization, customizationName);
									} else if (customization.type == 'TextAndConfirmText') {

										fieldsArray[index] = confirmPasswordTextComponent(customization, customizationName);
									} else if (customization.type == 'DropDown' || customization.type == 'TextAndDropDown') {

										fieldsArray[index] = dropDownComponent(customization, customizationName);
									} else if (customization.type == 'CheckBox' || customization.type == 'CheckBoxList') {

										fieldsArray[index] = checkBoxComponent(customization, customizationName);
									} else if (customization.type == 'NoChoice') {

										fieldsArray[index] = noChoiceComponent(customization, customizationName);
									} else if (customization.type == 'RadioButtons') {

										fieldsArray[index] = radioButtonComponent(customization, customizationName);
									} else {

										var placeHolderField = {
											xtype : 'hidden',
											name : 'placeHolderInArray',
											value : 'fieldDoesntMatch',
											labelSeparator : ''
										};
										fieldsArray[index] = placeHolderField;
									}
								});
							if (customizationGroup.instructionText != null && customizationGroup.instructionText != "") {
								var instructionText = "";
								instructionText = "<font class='blueCls'>" + customizationGroup.instructionText + "</font>";
								var instructionTextField = {
									xtype : 'label',
									fieldLabel : instructionText,
									labelSeparator : ''
								};
								fieldsArray.unshift(instructionTextField);
							}
						});

				var fieldSetInfo = [ {
					bodyStyle : 'padding-right:5px;',
					items : {
						xtype : 'fieldset',
						title : customizationGroup.groupTitle,
						autoHeight : true,
						collapsed : false,
						collapsible : false,
						labelSeparator : '',
						items : fieldsArray
					}
				} ];
				fieldSetArray[index] = fieldSetInfo;
			});

	// Hidden field data
	fieldSetArray[fieldSetArray.length] = hiddenComponent(jsonHiddenData);
}

// Hidden Field
function hiddenComponent(jsonHiddenData) {

	var hiddenFieldTemp = {
		xtype : 'hidden',
		name : 'hiddenJsonObj',
		value : jsonHiddenData
	};
	return hiddenFieldTemp;
}

// Dropdown box
function dropDownComponent(customization, customizationName) {
	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var selectedValue = "";
	var dropDownValuesArray = new Array();
	$(customization.item).each(function(index, item) {
		var keyValueArray = Array();
		keyValueArray[0] = item.text;
		keyValueArray[1] = item.id;
		dropDownValuesArray[index] = keyValueArray;
		if (item.selected === true) {
			selectedValue = item.id;
		}
	});

	var dropDownStore = new Ext.data.SimpleStore( {
		data : dropDownValuesArray,
		fields : [ 'itemValue', 'itemId' ]
	});

	var dropDownField = {
		xtype : 'combo',
		fieldLabel : customizationName,
		store : dropDownStore,
		displayField : 'itemValue',
		valueField : 'itemId',
		editable : false,
		forceSelection : true,
		typeAhead : true,
		triggerAction : 'all',
		name : customization.customizationId,
		selectedIndex : selectedValue,
		allowBlank : isAllowBlank,
		selectOnFocus:true,
		mode : 'local',
		hiddenName : customization.customizationId,
		labelSeparator : '',
		width : 250,
		emptyText : 'Choose.....'
	};
	if (customization.type == 'TextAndDropDown') {
		dropDownField.editable = true;
		dropDownField.forceSelection = false;
	}
	if (selectedValue != "") {
		dropDownField.value = selectedValue;
	}
	return dropDownField;
}

// Radio button
function radioButtonComponent(customization, customizationName) {

	// Allow Flag false -required
	var isAllowBlank = customization.required ? false : true;
	var radioButtonsItemsArray = checkBoxAndRadioButtonItems(customization);
	var radioButtonField = {
		xtype : 'radiogroup',
		fieldLabel : customizationName,
		id : customization.customizationId,
		name : customization.customizationId,
		columns : 1,
		allowBlank : isAllowBlank,
		validateField : true,
		items : radioButtonsItemsArray,
		labelSeparator : ''
	};
	return radioButtonField;
}

// Checkbox Field
function checkBoxComponent(customization, customizationName) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var checkBoxItemsArray = checkBoxAndRadioButtonItems(customization);
	var checkBoxField = {
		xtype : 'checkboxgroup',
		fieldLabel : customizationName,
		id : customization.customizationId,
		name : customization.customizationId,
		columns : 1,
		labelSeparator : '',
		validateField : true,
		allowBlank : isAllowBlank,
		items : checkBoxItemsArray
	};
	return checkBoxField;
}

// Check Box and Radio Button Items
function checkBoxAndRadioButtonItems(customization) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var itemsArray = new Array();
	var itemTextTemp;
	var url;
	$(customization.item).each(function(index, item) {

		var itemText = item.text;
		    // InlineHTML helpType
			if (item.helpTitle != null && item.helpType == 'InlineHTML') {
				helpTextFormatted = stripTagsAndQuote(item.helpText);
				itemText = itemText + "<br /><div id='helpDiv'>" + helpTextFormatted + "</div>";
			} // MouseOver HelpType
			else if (item.helpTitle != null && item.helpType == 'MouseOver') {
				helpTextFormatted = stripTagsAndQuote(item.helpText);
				itemText = itemText + "&nbsp;<a href='#' title='" + helpTextFormatted + "'><font class='blueCls'>" + item.helpTitle + "</font></a>";
			} // ModalPopup HelpType
			else if (item.helpTitle != null && item.helpType == "ModalPopup") {
				if (itemText.indexOf("<a") != -1) {
					itemTextTemp = itemText;
					itemTextHelp = itemText.substring(itemText.indexOf(">") + 1);
					if (itemTextHelp.substring(0, itemTextHelp.indexOf("</a>")) == item.helpTitle) {
						itemText = itemText.substring(0, itemText.indexOf("<a"));
					}
					// Identifying the url from itemText
					url = itemTextTemp.substring(itemTextTemp.indexOf("<a href=") + 9);
					url = url.substring(0, url.indexOf(">") - 1);
				}
				helpTitleFormatted = stripTagsAndQuote(item.helpTitle);
				itemText = itemText + "&nbsp;<a href='#' onclick='modelPopupRenderer(&#39;" + helpTitleFormatted + "&#39;, &#39;" + url + "&#39;)'><font class='blueCls'>"
						+ helpTitleFormatted + "</font></a>";
			}
			if (item.price != null && item.price.pricePeriodType != null) {
				itemText = itemText + "<font class='itemCls'> $" + item.price.initialAmount + "&nbsp;&nbsp;" + item.price.pricePeriodType + "</font>";
			}
			checkAndRadioitem = {
				boxLabel : itemText,
				name : customization.customizationId,
				inputValue : item.id,
				allowBlank : isAllowBlank,
				checked : item.selected,
				labelSeparator : ' '
			};
			itemsArray[index] = checkAndRadioitem;
		});
	return itemsArray;
}

// Text Field
function componentFields(customization, customizationName) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var txtValue = customization.textValue;
	var fieldTemp = {
		xtype : 'textfield',
		fieldLabel : customizationName,
		inputValue : escape(txtValue),
		name : customization.customizationId,
		allowBlank : isAllowBlank,
		labelSeparator : '',
		id : customization.customizationId,
		blankText : "Field is required!",
		width : 160
	};
	if (customization.validFlag == false) {

		fieldTemp.value = "";
		if (customization.validationErrorString != "" && customization.validationErrorString != null)
			fieldTemp.blankText = customization.validationErrorString;
		fieldTemp.monitorValid = true;
	} else if (txtValue != "" && txtValue != null) {
		fieldTemp.value = escape(txtValue);
	}
	if (!(customizationName.indexOf('Email') != -1)) {
		// To display the user entered data in upper case
		fieldTemp.style = {
			textTransform : 'uppercase'
		};
	}
	// Max length validation
	if (customization.lengthValidationFlag != undefined && customization.lengthValidationFlag != null && customization.lengthValidationFlag) {
		fieldTemp = maxLengthValidation(customization, fieldTemp);
	}
	// Anchor
	if (customization.maximumTextLength != undefined && customization.maximumTextLength != null && customization.maximumTextLength != '') {
		fieldTemp.anchor = 5 + customization.maximumTextLength + "%";
	} else {
		fieldTemp.anchor = "75%";
	}
	// Regular expression validation
	if (customization.regularExpressionValidationFlag != undefined && customization.regularExpression != null && customization.regularExpressionValidationFlag) {
		fieldTemp = regularExpressionValidation(customization, fieldTemp);
	}
	return fieldTemp;
}

// Phone Field
function phoneFieldComponent(customization, customizationName) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var phoneFieldTemp = {
		xtype : 'phonefield',
		maxLength : customization.maximumTextLength,
		fieldLabel : customizationName,
		allowBlank : isAllowBlank,
		inputValue : escape(customization.textValue),
		name : customization.customizationId,
		blankText : "Field is required!",
		id : customization.customizationId,
		name : customization.customizationId,
		labelSeparator : '',
		width : 160
	};
	if (customization.validFlag === false) {
		phoneFieldTemp.value = "";
		if (customization.validationErrorString != "" && customization.validationErrorString != null)
			phoneFieldTemp.blankText = customization.validationErrorString;
	} else if (customization.textValue != "" && customization.textValue != null) {
		phoneFieldTemp.value = escape(customization.textValue);
	}
	// Max length validation
	if (customization.lengthValidationFlag != undefined && customization.lengthValidationFlag != null && customization.lengthValidationFlag) {
		phoneFieldTemp = maxLengthValidation(customization, phoneFieldTemp);
	}
	// Anchor
	if (customization.maximumTextLength != undefined && customization.maximumTextLength != null && customization.maximumTextLength != '') {
		phoneFieldTemp.anchor = 5 + customization.maximumTextLength + "%";
	} else {
		phoneFieldTemp.anchor = "75%";
	}
	// Regular expression validation
	if (customization.regularExpressionValidationFlag != undefined && customization.regularExpression != null && customization.regularExpressionValidationFlag) {
		phoneFieldTemp = regularExpressionValidation(customization, phoneFieldTemp);
	}
	return phoneFieldTemp;
}

// Date Component
function dateFieldComponent(customization, customizationName) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var dateFieldTemp = {
		xtype : 'datefield',
		fieldLabel : customizationName,
		name : customization.customizationId,
		id : customization.customizationId,
		name : customization.customizationId,
		allowBlank : isAllowBlank,
		width : 160,
		inputValue : escape(customization.textValue),
		blankText : "Field is required!",
		labelSeparator : '',
		maxValue : new Date()
	};
	if (customizationName.indexOf('mm-dd-yyyy') != -1 || customizationName.indexOf('MM-DD-YYYY') != -1) {
		dateFieldTemp.format = 'm-d-Y';
	} else {
		dateFieldTemp.format = 'm/d/Y';
	}
	if (customization.validFlag === false) {
		dateFieldTemp.value = "";
		if (customization.validationErrorString != null && customization.validationErrorString != "")
			dateFieldTemp.blankText = customization.validationErrorString;
	} else if (customization.textValue != "" && customization.textValue != null) {
		dateFieldTemp.value = escape(customization.textValue);
	}
	// Max length validation
	if (customization.lengthValidationFlag && customization.lengthValidationFlag != undefined && customization.lengthValidationFlag != null) {
		dateFieldTemp = maxLengthValidation(customization, dateFieldTemp);
	}
	// Anchor
	if (customization.maximumTextLength != undefined && customization.maximumTextLength != null && customization.maximumTextLength != '') {
		dateFieldTemp.anchor = 5 + customization.maximumTextLength + "%";
	} else {
		dateFieldTemp.anchor = "75%";
	}
	// Regular expression validation
	if (customization.regularExpressionValidationFlag && customization.regularExpressionValidationFlag != undefined && customization.regularExpression != null) {
		dateFieldTemp = regularExpressionValidation(customization, dateFieldTemp);
	}
	return dateFieldTemp;
}

// Memo box Field
function memoBoxComponent(customization, customizationName) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var memoBoxField = {
		xtype : 'textarea',
		grow : true,
		id : customization.customizationId,
		name : customization.customizationId,
		fieldLabel : customizationName,
		inputValue : escape(customization.textValue),
		allowBlank : isAllowBlank
	};

	if (customization.textValue != undefined && customization.textValue != null && customization.textValue != "") {
		memoBoxField.value = unescape(customization.textValue);
	}
	// Max length validation
	if (customization.lengthValidationFlag != undefined && customization.lengthValidationFlag != null && customization.lengthValidationFlag) {
		memoBoxField = maxLengthValidation(customization, memoBoxField);
	}
	// Anchor
	if (customization.maximumTextLength != undefined && customization.maximumTextLength != null && customization.maximumTextLength != '') {
		memoBoxField.anchor = 5 + customization.maximumTextLength + "%";
	} else {
		memoBoxField.anchor = "75%";
	}
	return memoBoxField;
}

// ConfirmPassword Field
function confirmPasswordTextComponent(customization, customizationName) {

	// Allow Flag false -required
	var isAllowBlank = customization.required ? false : true;
	var textField = {
		xtype : 'textfield',
		id : customization.customizationId,
		name : customization.customizationId,
		fieldLabel : customizationName,
		inputType : 'password',
		allowBlank : isAllowBlank,
		inputValue : escape(customization.textValue)
	};

	var confirmTextField = {
		xtype : 'textfield',
		id : customization.customizationId + "_confirm",
		name : customization.customizationId,
		fieldLabel : '<b>Confirm</b> ' + customizationName,
		inputType : 'password',
		allowBlank : isAllowBlank,
		inputValue : escape(customization.textValue),
		vtype : 'password',
		initialPassField : customization.customizationId
	};
	if (customization.textValue != undefined && customization.textValue != null && customization.textValue != "") {

		textField.value = unescape(customization.textValue);
		confirmTextField.value = unescape(customization.textValue);
	}
	// Max length validation for both textbox and confirmtextbox
	if (customization.lengthValidationFlag != undefined && customization.lengthValidationFlag != null && customization.lengthValidationFlag) {
		textField = maxLengthValidation(customization, textField);
		confirmTextField = maxLengthValidation(customization, confirmTextField);
	}
	// Anchor
	if (customization.maximumTextLength != undefined && customization.maximumTextLength != null && customization.maximumTextLength != '') {
		confirmTextField.anchor = 5 + customization.maximumTextLength + "%";
		textField.anchor = 5 + textField.maximumTextLength + "%";
	} else {
		confirmTextField.anchor = "75%";
		textField.anchor = "75%";
	}

	var confirmTextFields = [ textField, confirmTextField ];
	return confirmTextFields;
}

// Nochoice Field
function noChoiceComponent(customization, customizationName) {

	// Allow Flag false -requried
	var isAllowBlank = customization.required ? false : true;
	var itemName = customization.item[0].text;
	var checkBoxField = {
		xtype : 'checkbox',
		fieldLabel : customizationName,
		boxLabel : itemName,
		name : customization.customizationId,
		id : customization.customizationId,
		name : customization.customizationId,
		inputValue : customization.item[0].id,
		allowBlank : isAllowBlank,
		checked : customization.item[0].selected,
		validateValue : function() {
			return this.getValue();
		}
	};
	return checkBoxField;
}

// Model PopUp Field
function modelPopupWindow(helpTitle, helpText) {

	var modelPopupField = new Ext.Window( {
		width : 500,
		height : 300,
		id : 'myPopup',
		title : helpTitle,
		html : helpText,
		autoScroll : true,
		resizable : false,
		draggable : false,
		close : function() {
			var formPanel = Ext.getCmp('myFormPanel');
			formPanel.el.unmask();
			if (this.fireEvent('beforeclose', this) !== false) {
				if (this.hidden) {
					this.doClose();
				} else {
					this.hide(null, this.doClose, this);
				}
			}
		}
	});
	var formPanel = Ext.getCmp('myFormPanel');
	formPanel.el.mask(modelPopupField.show(), 'x-mask');
	var f = Ext.get('myPopup');
	f.focus.defer(500, f);
}

// Max length validation
function maxLengthValidation(customization, maxFieldTemp) {

	// immediateFlag == true then no EXTJS validation required
	if ((immediateFlag == 'false') && (customization.lengthValidationFlag != undefined && customization.lengthValidationFlag != null && customization.lengthValidationFlag)) {
		if (customization.maximumTextLength != undefined && customization.maximumTextLength != null && customization.maximumTextLength != '') {
			maxFieldTemp.maxLength = customization.maximumTextLength;
			maxFieldTemp.maxLengthText = 'Max ' + customization.maximumTextLength + ' characters';
		}
	}
	return maxFieldTemp;
}

// Regular expression validation
function regularExpressionValidation(customization, regFieldTemp) {

	// immediateFlag == true then no EXTJS validation required
	if ((immediateFlag == 'false')
			&& (customization.regularExpressionValidationFlag != undefined && customization.regularExpression != null && customization.regularExpressionValidationFlag)) {
		// Convert string to regular expression object
		var regExpString = new RegExp(customization.regularExpression);
		regFieldTemp.regex = regExpString;
		regFieldTemp.regexText = customization.validationErrorText != undefined ? validationErrorText : 'Enter the valid ' + customization.name;
	}
	return regFieldTemp;
}

// Model Popup Renderer
function modelPopupRenderer(title, urlTemp) {

	var formPanel = Ext.getCmp('myFormPanel');
	formPanel.el.mask('Please wait.....', 'x-mask-loading');

	$.ajax( {
		url : urlTemp,
		context : document.body,
		type : "GET",
		dataType : "text/plain",
		success : function(data) {
			modelPopupWindow(title, data);
		},
		// internal server error
		error : function(err) {
		},
		// Show loading gif as the search is happening
		beforeSend : function() {
		},
		// hide loading gif on complete.
		complete : function() {
		}
	});
}

// To stripTagsAndQuote
function stripTagsAndQuote(textValue) {
	if (textValue == null)
		return null;
	// To remove the tags
	// As of now max 10 tags can be removed from textValue
	// Assumed not more than 10 tags in textValue
	for ( var i = 0; i < 10; i++) {
		var lt = textValue.indexOf("<");
		var gt = textValue.indexOf(">");
		if (gt > -1) {
			if (lt > 0) {
				textValue = textValue.substring(0, lt) + textValue.substring(gt + 1);
			} else {
				textValue = textValue.substring(gt + 1);
			}
		}
	}
	// To escapeQuote
	textValue = textValue.replace(/"/g, "&#34;");
	// To escapeSingleQuote
	textValue = textValue.replace(/'/g, "&#39;");
	return textValue;
}
