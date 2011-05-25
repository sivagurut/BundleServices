package com.directv.bundlesIntegration.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.directv.bundlesIntegration.ConfigProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLUllity.
 */
public class XMLUllity {

	/** The logger. */
	private static Logger logger = Logger.getLogger(XMLUllity.class);

	/**
	 * String to document.
	 *
	 * @param xml the xml
	 * @return the document
	 */
	public static Document stringToDocument(String xml) {

		logger.debug("Entering XMLUllity :: stringToDocument(String xml) :: " + xml);
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			document = reader.read(is);
		} catch (DocumentException e) {
			logger.debug("XMLUllity :: stringToDocument(String xml) :: DocumentException :: " + e.getStackTrace());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.debug("XMLUllity :: stringToDocument(String xml) :: UnsupportedEncodingException :: " + e.getStackTrace());
			e.printStackTrace();
		}
		logger.debug("Exiting XMLUllity :: stringToDocument(String xml)");
		return document;
	}

	/**
	 * Gets the document.
	 *
	 * @param filePath the file path
	 * @param key the key
	 * @return the document
	 */
	public static Document getDocument(String filePath, String key) {

		logger.debug("Entering XMLUllity ::  getDocument(String filePath, String key)" + filePath);
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			if (ConfigProperties.getValue(key) != null && !ConfigProperties.getValue(key).equals("")) {
				document = reader.read(ConfigProperties.getValue(key));
			}
			if (document == null) {
				document = reader.read(XMLUllity.class.getResourceAsStream(filePath));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		logger.debug("Exiting XMLUllity ::  getDocument(String filePath, String key)");
		return document;
	}
}
