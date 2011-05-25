package com.directv.broadbandBundles.ui.view.form.fields;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/19/11
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class HiddenField extends BaseField
{
    public void setOptions(StringBuffer b,String name, String value,  boolean isFirst) throws Exception
    {
        if (! isFirst)
        {
            b.append("\r\n,");
        }
        b.append("\r\n{").
                append("\r\nxtype: 'hidden'").
                append("\r\n,name: '").append(name).append("'").
                append("\r\n,value: '").append(value).append("'").
                append("\r\n}");
    }

}
