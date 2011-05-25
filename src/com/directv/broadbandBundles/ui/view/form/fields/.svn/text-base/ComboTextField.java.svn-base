package com.directv.broadbandBundles.ui.view.form.fields;

import com.directv.broadbandBundles.ui.model.input.Customization;

import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/1/11
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComboTextField extends ComboField
{
    public void setOptions(StringBuffer b, Customization customization, boolean isFirst) throws Exception
    {

        LinkedHashMap<String, String> store = getCustomizationStore(customization);

        String label;
        String labelStyle;

        if (customization.getHelpType()!= null && customization.getHelpType().equals("InlineHTML"))
        {
            label = escapeQuote(stripTags(customization.getHelpText()));
            labelStyle = LabelField.LABEL_STYLE_HELP;


            if (customization.getName() != null)
            {
                //add label, always provided
                new LabelField().setOptions(b, getCustomizationName(customization), LabelField.LABEL_STYLE_LABEL, isFirst);
                isFirst = false;
            }
        }
        else
        {
            label = escapeQuote(getCustomizationName(customization));
            labelStyle = LabelField.LABEL_STYLE_LABEL;
        }


        if (! isFirst)
        {
            b.append("\r\n,");
        }
        b.append("\r\n{").
                append("\r\nxtype: 'combo'").
                append("\r\n,fieldLabel: '").append(label).append("'").
                append("\r\n,labelStyle: '").append(labelStyle).append("'").
                append("\r\n,allowBlank: ").append(!customization.isRequired()).
                //for some reason EXT-JS returns the display name instead of value unless a hiddenname is
                //specified, so fake the real name
                append("\r\n,name: '").append("X").append(customization.getCustomizationId()).append("'").
                append("\r\n,hiddenName: '").append(customization.getCustomizationId()).append("'").
                append("\r\n,anchor: '").append(getCustomizationAnchor(customization)).append("'").
                append("\r\n,typeAhead: true").
                append("\r\n,mode: 'local'").
                append("\r\n,triggerAction: 'all'").
                append("\r\n,emptyText:'Choose ...'").
                append("\r\n,selectOnFocus:true").
                append("\r\n,forceSelection: false").
                append("\r\n,editable: true");
        setStore(b, store, customization);
        b.append("\r\n}");
    }

    private void setStore(StringBuffer b, LinkedHashMap<String, String> store, Customization customization)
    {

        boolean firstDataRow = true;

        b.append("\r\n,store\t\t\t: new Ext.data.SimpleStore ({")
                .append("\r\nfields\t\t\t: ['name', 'value']")
                .append("\r\n,data\t\t\t: [");

        for (String row : store.keySet())
        {
            b.append("\r\n");
            if (!firstDataRow) b.append(",");
            firstDataRow = false;
            b.append("['").append(row).append("', '").append(store.get(row)).append("']");
        }

        b.append("\r\n]")
                .append("\r\n})")
                .append("\r\n,displayField\t\t\t: 'name'")
                .append("\r\n,valueField\t\t\t: 'value'");

        String defaultValue = getCustomizationStoreSelected(customization);
        if (defaultValue != null)
        {
            b.append("\r\n,value\t\t\t: '").append(defaultValue).append("'");
        }


    }


}
