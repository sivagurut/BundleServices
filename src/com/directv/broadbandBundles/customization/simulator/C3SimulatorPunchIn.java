package com.directv.broadbandBundles.customization.simulator;

import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 10:08 AM
 * Simulates a C3/OMS redirect to the Non-video Customizer
 */
public class C3SimulatorPunchIn extends HttpServlet
{

    private final Log logger = LogFactory.getLog(getClass());

    private static final String CUSTOMIZATION_URL = "http://den-e44237.la.frd.directv.com:7001/NonVideoCustomizer/NonVideoCustomizerPunchIn.dtv";
    // D1 Testing URL
    // private static final String CUSTOMIZATION_URL = "http://d3apdw01.directv.com:8970/NonVideoCustomizer/NonVideoCustomizerPunchIn.dtv";
    private static final String CHARACTER_SET = "UTF-8";
    private static final String CUSTOMIZATION_PARAM_NAME = "xmlCustomizationInput";

    public void service(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            //Open the file that has the XML to simulate a customization call by C3
            File file = new File("custom/ws/CustomizerInputResponse.xml");

            //Load the XML
            ResponseDocument doc = ResponseDocument.Factory.parse(file);
            //logger.debug("doc=" + doc);

            //UTF-8 encode it
            String customizationXmlInput = URLEncoder.encode("" + doc, CHARACTER_SET);

            logger.debug("forwarding request");// + doc);

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

            logger.debug("page=" + html);

            ServletOutputStream out = response.getOutputStream();
            out.print(html.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
