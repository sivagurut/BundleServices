package com.directv.broadbandBundles.ui.view.form.fields;

import com.directv.broadbandBundles.ui.view.form.elements.GenericGroupField;
import com.directv.broadbandBundles.ui.view.form.groups.BoxGroup;
import com.directv.broadbandBundles.ui.view.form.groups.BoxGroupItem;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 3/29/11
 * Time: 4:15 PM
 * Creates an EXT-JS CheckBox Group
 * Note: Checkbox group is used even if a single checkbox because the group version works with allowBlank
 * allowblank is used to keep the continue button grayed out until the checkbox is selected.
 */
public class CheckBoxGroupField extends BaseField implements GroupType, GenericGroupField
{

    public void setOptions(StringBuffer b, BoxGroup group, boolean isFirst) throws Exception
    {
        if (!isFirst)
        {
            b.append("\r\n,");
        }
        b.append("\r\n{").
                append("\r\nxtype: '").append(getGroupType()).append("'");
                if (group.getLabel() != null && group.getLabel().length() > 0)
                {
                    b.append("\r\n,fieldLabel: '").append(group.getLabel()).append("'");
                }
                else
                {
                    b.append("\r\n,hideLabel: true");
                }
                b.append("\r\n,labelStyle: '").append(group.getLabelStyle()).append("'").
                append("\r\n,blankText: 'Selecting this checkbox is required to continue.'").
                append("\r\n,allowBlank: ").append(group.isAllowBlank()).
                append("\r\n,columns: 1").
                append("\r\n,items: [");

        boolean isFirstItem = true;

        for (BoxGroupItem item : group.getCheckboxItems())
        {
            if (!isFirstItem)
            {
                b.append("\r\n,");
            }
            b.append("\r\n{").
                    append("\r\nxtype: '").append(getItemType()).append("'").
                    append("\r\n,boxLabel: '").append(item.getBoxLabel()).append("'").
                    append("\r\n,checked: ").append(item.isChecked()).
                    append("\r\n,inputValue: '").append(item.getInputValue()).append("'").
                    append("\r\n,name: '").append(item.getName()).append("'");

            if (item.getHelpText() != null)
            {
                b.append("\r\n,helpLinkText: \"").append("what's this?").append("\"").
                append("\r\n,helpLink: '#'").
                append("\r\n,helpTitle: \"").append(item.getHelpTitle()).append("\"").
                append("\r\n,helpText: \"").append(item.getHelpText()).append("\"");
            }

            if (item.getHtmlText() != null)
            {
                b.append("\r\n,htmlText: '").append(item.getHtmlText()).append("'");
            }
            b.append("\r\n}");

            isFirstItem = false;
        }
        b.append("\r\n]}");
    }

    public String getGroupType()
    {
        return "checkboxgroup";
    }

    public String getItemType()
    {
        return "linkcheck";
    }

}

