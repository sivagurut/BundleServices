package com.directv.broadbandBundles.customization.translator;

import com.directv.broadbandBundles.initialize.CustomizerProperites;
import com.directv.broadbandBundles.ui.model.input.*;
import com.directv.broadbandBundles.ui.view.form.comparator.CustomizationComparator;
import com.directv.broadbandBundles.ui.view.form.comparator.GroupComparator;
import com.directv.broadbandBundles.ui.view.form.elements.*;
import com.directv.broadbandBundles.ui.view.form.fields.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashSet;


/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/19/11
 * Time: 1:36 PM
 * Builds the UI based on the Input Customization Model.
 */
public class ModelToFormCustomizations
{
    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.translator.ModelToFormCustomizations");

    public static final String VISIT_ID = "VISIT_ID";


    public static HashSet<String> setFormFromModel(CustomizationModel model, StringBuffer b) throws Exception
    {
        HashSet<String> sensitiveIds = new HashSet<String>();

        //Form Panel Config
        b.append(CustomizerProperites.getProperty(CustomizerProperites.FORM_PANEL));

        //Begin Form Fields
        b.append("\r\n,items: [");

        //ensure we have a group list - and only one
        if (model.getGroupList() == null || model.getGroupList().getCustomizationGroup() == null)
            throw new Exception("Empty Customization Group List");

        //sort customization groups by rank, lowest to highest
        Collections.sort(model.getGroupList().getCustomizationGroup(), new GroupComparator());

        //add customization groups to form
        processCustomizationGroups(model, b, sensitiveIds);

        //End Form Fields
        b.append(" ]");

        //Buttons & End Form Panel
        b.append(",");
        b.append(CustomizerProperites.getProperty(CustomizerProperites.FORM_BUTTONS));

        //Form Render
        b.append(CustomizerProperites.getProperty(CustomizerProperites.FORM_RENDER));

        return sensitiveIds;

    }


    private static void processCustomizationGroups(CustomizationModel model, StringBuffer b, HashSet<String> sensitiveIds) throws Exception
    {

        //add visit ID to form
        new HiddenField().setOptions(b, VISIT_ID, String.valueOf(model.getVisitId()), true);

        //Error Message from Bridgevine, show at top in red
        String invalidText = getInvalidMessages(model);

        if (invalidText != null)
        {
            new LabelField().setOptions(b, invalidText, LabelField.LABEL_STYLE_ERROR_TEXT, false);
        }

        //add groups as field sets
        for (CustomizationGroup group : model.getGroupList().getCustomizationGroup())
        {
            if (group.getGroupTitle() == null)
                throw new Exception("Empty Customization Group Title");

            //fieldset for group
            new FieldSet().setOptions(b, group.getGroupTitle(), "775", false);

            //ensure we have at least one customization for the group
            if (group.getCustomizationList() != null && group.getCustomizationList().size() > 0)
            {
                //sort the customizations for this group
                Collections.sort(group.getCustomizationList().get(0).getCustomization(), new CustomizationComparator());

                boolean firstFieldSetItem = true;

                if (group.getInstructionText() != null)
                {
                    // instruction text label
                    new LabelField().setOptions(b, group.getInstructionText(),
                                                LabelField.LABEL_STYLE_INSTRUCTION_TEXT, true);
                    firstFieldSetItem = false;
                }

                processCustomizations(b, group, firstFieldSetItem, sensitiveIds);

            }
            //end fieldset
            b.append("\r\n]}");

        }
    }

    private static String getInvalidMessages(CustomizationModel model)
    {
        String invalidMessages = null;

        for (CustomizationGroup group : model.getGroupList().getCustomizationGroup())
        {
            for (CustomizationList customizationList : group.getCustomizationList())
            {
                for (Customization customization : customizationList.getCustomization())
                {
                    if (!customization.isValidFlag())
                    {
                        if (invalidMessages == null)
                        {
                            invalidMessages = "The following fields are not valid.  Check for missing answers or incorrect format:<br>";
                        }
                        invalidMessages += "- " + customization.getName() + "<br>";
                    }
                }
            }
        }

        return invalidMessages;
    }

    private static void processCustomizations(StringBuffer b, CustomizationGroup group,
                                              boolean firstFieldSetItem, HashSet<String> sensitiveIds) throws Exception
    {
//        logger.debug("1 firstFieldSetItem=" + firstFieldSetItem);

        for (CustomizationList customizationList : group.getCustomizationList())
        {
            for (Customization customization : customizationList.getCustomization())
            {
//                logger.debug("2 firstFieldSetItem=" + firstFieldSetItem);
                if (customization.getType().equals("TextBox"))
                {
                    new TextField().setOptions(b, customization, firstFieldSetItem);
                }
                else if (customization.getType().equals("MaskedTextBox"))
                {
                    //treat masked text box same as text box, so user can see/verify auto-filled fields
                    new TextField().setOptions(b, customization, firstFieldSetItem);
                }
                else if (customization.getType().equals("DropDown"))
                {
                    new ComboField().setOptions(b, customization, firstFieldSetItem);
                }
                else
                if (customization.getType().equals("CheckBox"))
                {
                    //checkbox is actually a checkbox group, so we can bind to the EXT JS allowBlank
                    new CheckBoxElement().addGroupElementToForm(customization, b, firstFieldSetItem);
                }
                else if (customization.getType().equals("RadioButtons"))
                {
                    new RadioElement().addGroupElementToForm(customization, b, firstFieldSetItem);
                }
                else if (customization.getType().equals("CheckBoxList"))
                {
                    //treat checkbox list same as check box
                    //@todo handle selection count
                    new CheckBoxElement().addGroupElementToForm(customization, b, firstFieldSetItem);
                }
                else if (customization.getType().equals("NoChoice"))
                {
                    //treat NoChoice same as check box
                    new CheckBoxElement().addGroupElementToForm(customization, b, firstFieldSetItem);
                }
                else if (customization.getType().equals("MemoBox"))
                {
                    new MemoField().setOptions(b, customization, firstFieldSetItem);
                }
                else if (customization.getType().equals("TextAndDropDown"))
                {
                    new ComboTextField().setOptions(b, customization, firstFieldSetItem);
                }
                else if (customization.getType().equals("TextAndConfirmText"))
                {
                    //@todo provide two boxes and validate they match
                    new TextField().setOptions(b, customization, firstFieldSetItem);
                }
                else
                {
                    logger.fatal("Received unknown customization type from Bridgevine: " + customization.getType());
                }

                //track sensitive data elements
                if (customization.isSensitiveFlag()) sensitiveIds.add(customization.getCustomizationId());

                //reset indicator used for adding a comma to field set; i.e. is it the first field set
                firstFieldSetItem = false;
            }
        }
    }


}
