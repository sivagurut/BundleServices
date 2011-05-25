package com.directv.broadbandBundles.ui.view.form.fields;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/22/11
 * Time: 1:20 PM
 * Field Sets wrap a group of associated form elements
 */
public class FieldSet
{
    public void setOptions(StringBuffer b, String title, String width, boolean isFirst)
    {
        if (! isFirst)
        {
            b.append("\r\n,");
        }
        b.append("\r\n{").
                append("\r\nxtype: 'fieldset'").
                append("\r\n,title: '").append(title).append("'").
                append("\r\n,width: ").append(width).
                append("\r\n,items: [");


    }

}
