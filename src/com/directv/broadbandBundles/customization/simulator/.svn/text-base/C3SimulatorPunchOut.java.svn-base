package com.directv.broadbandBundles.customization.simulator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/25/11
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3SimulatorPunchOut extends HttpServlet
{

    private final Log logger = LogFactory.getLog(getClass());

    public void service(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException
    {
        String value = null;

        try
        {
            //dump all request params for debugging
            Enumeration names = request.getParameterNames();
            while (names.hasMoreElements())
            {
                String name = (String) names.nextElement();
                value = URLDecoder.decode(request.getParameter(name), "UTF-8");
                logger.debug("********************************** parm name:" + name + "::: value:" + value);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ServletOutputStream out = response.getOutputStream();
        out.println(value);
        out.flush();


    }
}