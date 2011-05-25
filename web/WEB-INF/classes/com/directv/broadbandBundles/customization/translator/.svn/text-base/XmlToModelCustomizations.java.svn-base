package com.directv.broadbandBundles.customization.translator;


import com.directv.broadbandBundles.ui.model.input.*;
import com.directv.ei.schemas.entities.v31.NameValuePairEntity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.*;


import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/8/11
 * Time: 11:08 AM
 * Translates the XML received by the Edge System into the model required to build the UI
 */
public class XmlToModelCustomizations
{
    public static final String PUNCH_OUT_URL = "ReturnURL";

    public static CustomizationModel getModelFromXml(String xml) throws Exception
    {
        ResponseDocument doc = ResponseDocument.Factory.parse(xml);

        if (doc == null || doc.getResponse() == null) throw new Exception("Empty customization document received");

        CustomizationModel model = new CustomizationModel();

        translateModel(doc, model);

        return model;

    }

    private static void translateModel(ResponseDocument doc, CustomizationModel model) throws Exception
    {
        model.setVisitId(doc.getResponse().getVisitId());

        if (doc.getResponse().getCustomData() == null)
            throw new Exception("Empty custom data received- ReturnURL required.");
        if (doc.getResponse().getCustomData().getParamKVPairArray() == null)
            throw new Exception("Empty customization key value pair received");

        for (NameValuePairEntity nvp : doc.getResponse().getCustomData().getParamKVPairArray())
        {
            if (nvp.getName() != null && nvp.getName().equalsIgnoreCase(PUNCH_OUT_URL) &&
                    nvp.getValueArray() != null && nvp.getValueArray()[0] != null)
            {
                model.setPunchOutURL(nvp.getValueArray()[0]);
            }
        }
        if (model.getPunchOutURL() == null) throw new Exception("Empty customization punch out URL received");

        if (doc.getResponse().getCustomizationGroupList() == null)
            throw new Exception("Empty customization Group List received");

        model.setGroupList(new CustomizationGroupList());

        translateGroupList(doc.getResponse().getCustomizationGroupList(), model.getGroupList(), model);
    }

    private static void translateGroupList(ProcessNonVideoCustomizationsResponseEntity.CustomizationGroupList docGroupList,
                                           CustomizationGroupList modelGroupList,
                                           CustomizationModel model) throws Exception
    {
        modelGroupList.setQuestionType(docGroupList.getQuestionType());
        modelGroupList.setCustomizationGroup(new ArrayList<CustomizationGroup>());

        if (docGroupList.getCustomizationGroupArray() == null)
            throw new Exception("Empty customization Group received");
        translateGroup(docGroupList.getCustomizationGroupArray(), modelGroupList.getCustomizationGroup(), model);
    }

    private static void translateGroup(NonVideoCustomizationGroupEntity[] docGroupEntity,
                                       ArrayList<CustomizationGroup> modelGroupEntity,
                                       CustomizationModel model) throws Exception
    {
        for (NonVideoCustomizationGroupEntity docGroup : docGroupEntity)
        {
            CustomizationGroup modelGroup = new CustomizationGroup();
            modelGroup.setGroupTitle(docGroup.getGroupTitle());
            modelGroup.setInstructionText(docGroup.getInstructionText());
            modelGroup.setRank(docGroup.getRank());
            modelGroup.setCustomizationList(new ArrayList<CustomizationList>());
            if (docGroup.getCustomizationList() != null)
            {
                translateCustomizationList(docGroup.getCustomizationList(), modelGroup.getCustomizationList(), model);
                modelGroupEntity.add(modelGroup);
            }
        }
    }

    private static void translateCustomizationList(NonVideoCustomizationGroupEntity.CustomizationList docCustomizationList,
                                                   ArrayList<CustomizationList> modelCustomizationList,
                                                   CustomizationModel model)
    {
        for (NonVideoCustomizationEntity customization : docCustomizationList.getCustomizationArray())
        {
            CustomizationList list = new CustomizationList();

            translateCustomization(customization, list, model);

            modelCustomizationList.add(list);
        }
    }

    private static void translateCustomization(NonVideoCustomizationEntity docCustomization,
                                               CustomizationList modelList,
                                               CustomizationModel model)
    {
        Customization modelCustomization = new Customization();

        modelCustomization.setCustomizationId(docCustomization.getCustomizationId());
        modelCustomization.setCode(docCustomization.getCode());
        modelCustomization.setType(docCustomization.getType());
        modelCustomization.setName(docCustomization.getName());
        modelCustomization.setLabel(docCustomization.getLabel());
        modelCustomization.setDescription(docCustomization.getDescription());
        modelCustomization.setHelpText(docCustomization.getHelpText());
        modelCustomization.setHelpTitle(docCustomization.getHelpTitle());
        modelCustomization.setHelpType(docCustomization.getHelpType());
        modelCustomization.setRequired(docCustomization.getRequiredFlag());
        modelCustomization.setMaximumNumericValue(docCustomization.getMaximumNumericValue());
        modelCustomization.setMaximumSelectionCount(docCustomization.getMaximumSelectionCount());
        modelCustomization.setMaximumTextLength(docCustomization.getMaximumTextLength());
        modelCustomization.setMinimumNumericValue(docCustomization.getMinimumNumericValue());
        modelCustomization.setMinimumSelectionCount(docCustomization.getMinimumSelectionCount());
        modelCustomization.setLengthValidationFlag(docCustomization.getLengthValidationFlag());
        modelCustomization.setImmediateSubmissionFlag(docCustomization.getImmediateSubmissionFlag());
        modelCustomization.setRangeValidationFlag(docCustomization.getRangeValidationFlag());
        modelCustomization.setRegularExpressionValidationFlag(docCustomization.getRegularExpressionValidationFlag());
        modelCustomization.setSelectionCountValidationFlag(docCustomization.getSelectionCountValidationFlag());
        modelCustomization.setSensitiveFlag(docCustomization.getSensitiveFlag());
        if (docCustomization.getSensitiveFlag())
        {
            modelCustomization.setTextValue(docCustomization.getSensitiveTextValue());
        }
        else
        {
            modelCustomization.setTextValue(docCustomization.getTextValue());
        }
        modelCustomization.setValidFlag(docCustomization.getValidFlag());
        modelCustomization.setRank(docCustomization.getRank());
        modelCustomization.setRegularExpression(docCustomization.getRegularExpression());
        modelCustomization.setValidationErrorString(docCustomization.getValidationErrorText());
        modelCustomization.setWarningText(docCustomization.getWarningText());

        //keep the immediate submission flag when we send request containing answers to Bridgevine
        if (docCustomization.getImmediateSubmissionFlag()) model.setImmediateSubmissionFlag(true);

        if (docCustomization.getItemList() != null)
        {
            ArrayList<CustomizationItem> item = new ArrayList<CustomizationItem>();

            translateCustomizationItem(docCustomization.getItemList().getItemArray(), item);
            modelCustomization.setItem(item);
        }
        modelList.setCustomization(new ArrayList<Customization>());
        modelList.getCustomization().add(modelCustomization);
    }

    private static void translateCustomizationItem(NonVideoCustomizationItemEntity[] itemList, ArrayList<CustomizationItem> modelItem)
    {

        for (NonVideoCustomizationItemEntity entity : itemList)
        {
            CustomizationItem item = new CustomizationItem();
            item.setCode(entity.getCode());
            item.setHelpText(entity.getHelpText());
            item.setHelpTitle(entity.getHelpTitle());
            item.setHelpType(entity.getHelpType());
            item.setId(entity.getId());
            if (entity.getPriceList() != null)
            {
                CustomizationPrice price = new CustomizationPrice();
                item.setPrice(price);
                translateCustomizationPrice(entity.getPriceList().getPrice(), price);
            }
            item.setSelected(entity.getSelectedFlag());
            item.setText(entity.getText());
            item.setZeroText(entity.getZeroText());

            modelItem.add(item);
        }
    }

    private static void translateCustomizationPrice(NonVideoBasePriceEntity docPrice, CustomizationPrice modelPrice)
    {
        modelPrice.setInitialAmount(docPrice.getInitialAmount());
        modelPrice.setInitialAmountLengthMonths(docPrice.getInitialAmountLengthMonths());
        modelPrice.setPricePeriodType(docPrice.getPricePeriodType());
        modelPrice.setRegularAmount(docPrice.getRegularAmount());

    }
}
