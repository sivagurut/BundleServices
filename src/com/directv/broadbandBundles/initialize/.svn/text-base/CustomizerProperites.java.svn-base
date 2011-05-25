package com.directv.broadbandBundles.initialize;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 3/29/11
 * Time: 10:22 AM
 * Loads and serves up application property values
 */
public class CustomizerProperites
{
    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.initialize.CustomizerProperites");

    //properties for this application
    public static final String FORM_PANEL = "form.panel";
    public static final String FORM_RENDER = "form.render";
    public static final String FORM_RELOAD_INTERVAL_MINUTES = "form.reload.interval.minutes";
    public static final String FORM_BUTTONS = "form.buttons";
    public static final String HTML_PREFIX = "html.prefix";

    //application configuration
    private static ResourceBundle bundle;
    //time to reload properties
    private static Calendar reloadTime;
    //synchronize Object
    private static final Object loadSyncObject = new Object();

    static Class type = ResourceBundle.class;
    static Field cacheList;

    static
    {
        try
        {
            cacheList = type.getDeclaredField("cacheList");
            cacheList.setAccessible(true);
            loadProperties();
        }
        catch (Exception e)
        {
            logger.fatal("Can't load application properites: ", e);
        }

    }

    private static void loadProperties() throws NoSuchFieldException, IllegalAccessException
    {
        //load the bundle
        bundle = ResourceBundle.getBundle("customizer");

        //log bundle updates in case someone screws something up we can prove it later
        Enumeration<String> properties = bundle.getKeys();
        while (properties.hasMoreElements())
        {
            String key = properties.nextElement();
            logger.debug("Property :: " + key + " =<" + bundle.getString(key) + ">");
        }

        //clear the cache so we can reload the bundle when we need to
        ((Map) cacheList.get(ResourceBundle.class)).clear();
        //reset the reload Timer
        reloadTime = Calendar.getInstance();
        reloadTime.add(Calendar.SECOND, Integer.parseInt(bundle.getString(FORM_RELOAD_INTERVAL_MINUTES)));
        logger.debug("Next Property Reload Time :: " + reloadTime.getTime());

    }

    public static String getProperty(String propertyName) throws NoSuchFieldException, IllegalAccessException
    {
        //if it's time to reload properties, do so now
        if (Calendar.getInstance().after(reloadTime))
        {
            //Synchronized because we don't want a thread looking at the properties while another thread is loading them.
            //This is because form elements depend on each other and need to be loaded as a group.
            //Better here than at the method level as that would cause congestion continuously
            //This will cause congestion only when it is time to reload, but that will be a short time compared to
            //most of the time
            synchronized (loadSyncObject)
            {
                loadProperties();
            }
        }
        //return property value
        return bundle.getString(propertyName);
    }

}
