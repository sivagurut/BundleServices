package com.directv.bundlesIntegration.modifications;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;

import com.directv.bundlesIntegration.util.XMLUllity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class C3Simulator.
 */
public class C3Simulator extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3382400336063976201L;

	/** The Constant FILE_PATH_KEY. */
	public static final String FILE_PATH_KEY = "file.path";
	
	/** The Constant CUSTOMIZATION_URL. */
	private static final String CUSTOMIZATION_URL = "NVCForm.jsp";
	
	/** The Constant CHARACTER_SET. */
	private static final String CHARACTER_SET = "UTF-8";
	
	/** The Constant CUSTOMIZATION_PARAM_NAME. */
	private static final String CUSTOMIZATION_PARAM_NAME = "xmlCustomizationInput";
	
	/** The Constant CUSTOMIZATION_RESPONSE_FILE_PATH. */
	private static final String CUSTOMIZATION_RESPONSE_FILE_PATH = "CustomizerInputResponse.xml";

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			//New code
			//Load the XML
			Document doc = XMLUllity.getDocument(CUSTOMIZATION_RESPONSE_FILE_PATH, FILE_PATH_KEY);
			//UTF-8 encode it
			String customizationXmlInput = URLEncoder.encode(doc.asXML().toString(), CHARACTER_SET);
		 
			//Jim's commented code
			//Open the file that has the XML to simulate a customization call by C3
			//String filePath = "../resources/CustomizerInputResponse.xml";
            //File file = new File("C:/TEST_SERVICE/Resources/CustomizerInputResponseQuest.xml");
            //Load the XML
            //ResponseDocument doc = ResponseDocument.Factory.parse(file);
            //UTF-8 encode it
            //String customizationXmlInput = URLEncoder.encode("" + doc, CHARACTER_SET);
            
			//build a new form for browser
			//it will post the xml input to the customizer automatically
			StringBuffer html = new StringBuffer();

            html.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>\n").
                    append("<html xmlns='http://www.w3.org/1999/xhtml'>\n").
                    append("<head>\n").
                    append("<meta http-equiv='Content-Type' content='text/html; charset=").append(CHARACTER_SET).append("'/>\n").
                    append("<SCRIPT LANGUAGE='JavaScript'>\n").
                    append(" function init() {\n").
                    append("     document.cutomizationPunchIn.submit();\n").
                    append("    }\n").
                    append("</SCRIPT>\n").
                    append("<title>Non-video Customizer Punch-In</title>\n").
                    append("</head>\n").
                    append("<BODY BGCOLOR='ffffff' onLoad='init();'>\n").
                    append(" <form action='").append(CUSTOMIZATION_URL).append("' name='cutomizationPunchIn' method='post'>\n").
                    append("   <input type='hidden' name='").append(CUSTOMIZATION_PARAM_NAME).append("' value='").append(customizationXmlInput).append("'>\n").
                    append(" </form>").
                    append("<a href='javascript:document.cutomizationPunchIn.submit();'>Loading ...</a>\n").
                    append("</BODY>\n").
                    append("</HTML>\n");

			ServletOutputStream out = response.getOutputStream();
			out.print(html.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
