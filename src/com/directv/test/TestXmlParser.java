package com.directv.test;

import org.dom4j.Document;

import com.directv.broadbandBundles.customization.translator.XmlToModelCustomizations;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.bundlesIntegration.util.XMLUllity;

public class TestXmlParser {

	public static final String FILE_PATH_KEY = "file.path";
	private static final String CUSTOMIZATION_RESPONSE_FILE_PATH = "CustomizerInputResponseQuest.xml";

	public static void main(String args[]) throws Exception {

		Document doc = XMLUllity.getDocument(CUSTOMIZATION_RESPONSE_FILE_PATH, FILE_PATH_KEY);
		CustomizationModel model = XmlToModelCustomizations.getModelFromXml(doc.asXML());
		System.out.println(model);
	}
}
