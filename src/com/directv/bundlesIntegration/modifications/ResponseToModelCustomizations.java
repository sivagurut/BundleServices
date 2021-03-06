package com.directv.bundlesIntegration.modifications;


import com.directv.broadbandBundles.ui.model.input.*;
import com.directv.broadbandBundles.ui.model.input.CustomizationList;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.*;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/3/11
 * Time: 1:36 PM
 * Translate a Bridgevine Response to a UI Input model
 */
public class ResponseToModelCustomizations
{
    //private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.translator.ResponseToModelCustomizations");

    public static CustomizationModel getModelFromResponse(ProcessNonVideoCustomizationsResponseEntity customizationResponse,
                                                          CustomizationSelection selectionModel) throws Exception
    {
        if (customizationResponse == null) throw new Exception("Empty customization response received");

        CustomizationModel model = new CustomizationModel();

//        if (customizationResponse.getMessageList() != null &&
//                customizationResponse.getMessageList().getMessage() != null &&
//                customizationResponse.getMessageList().getMessage().length > 0 &&
//                customizationResponse.getMessageList().getMessage()[0].getMessageDetail() != null &&
//                customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getAdditionalInformation() != null
//                )
//        {
//            logger.debug("errormessage: "+ customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getAdditionalInformation());
//            model.setErrorMessage(customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getAdditionalInformation().replace('\r', ' ').replace('\n', ' '));
//        }
//        else
//        {
//            logger.debug("no error message");
//        }

        translateModel(customizationResponse, model, selectionModel);

        return model;

    }

    private static void translateModel(ProcessNonVideoCustomizationsResponseEntity customizationResponse,
                                       CustomizationModel model,
                                       CustomizationSelection selectionModel) throws Exception
    {
        //if we got a visit ID from BV, then use it
        if (customizationResponse.getVisitId() != null)
        {
            model.setVisitId(customizationResponse.getVisitId());
        }
        else
        {
            //else, get it from our last selection model
            model.setVisitId(selectionModel.getVisitId());
        }

        //move over punch-out URL
        model.setPunchOutURL(selectionModel.getPunchOutURL());

        model.setGroupList(new com.directv.broadbandBundles.ui.model.input.CustomizationGroupList());

        if (customizationResponse.getCustomizationGroupList() == null)
            throw new Exception("Empty customization Group List received");

        translateGroupList(customizationResponse.getCustomizationGroupList(), model.getGroupList());
    }

    private static void translateGroupList(com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.CustomizationGroupList docGroupList,
                                           com.directv.broadbandBundles.ui.model.input.CustomizationGroupList modelGroupList) throws Exception
    {
        modelGroupList.setQuestionType(docGroupList.getQuestionType());
        modelGroupList.setCustomizationGroup(new ArrayList<CustomizationGroup>());

        if (docGroupList.getCustomizationGroup() == null)
            throw new Exception("Empty customization Group received");
        translateGroup(docGroupList.getCustomizationGroup(), modelGroupList.getCustomizationGroup());
    }

    private static void translateGroup(NonVideoCustomizationGroupEntity[] docGroupEntity, ArrayList<CustomizationGroup> modelGroupEntity) throws Exception
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
                translateCustomizationList(docGroup.getCustomizationList(), modelGroup.getCustomizationList());
                modelGroupEntity.add(modelGroup);
            }
        }

    }

    private static void translateCustomizationList(com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.CustomizationList docCustomizationList,
                                                   ArrayList<CustomizationList> modelCustomizationList)
    {
    	//New code
		CustomizationList list = new CustomizationList();
		list.setCustomization(new ArrayList<Customization>());
		
        for (NonVideoCustomizationEntity customization : docCustomizationList.getCustomization())
        {
        	// Jim's code comment: [Reason] - ArrayList cleared out for each customization
            //CustomizationList list = new CustomizationList();
        	
            translateCustomization(customization, list);
        }
            modelCustomizationList.add(list);

       

    }

    private static void translateCustomization(NonVideoCustomizationEntity docCustomization, CustomizationList modelList)
    {
        Customization modelCustomization = new Customization();

        //logger.debug("docCustomization.getCustomizationId()= " + docCustomization.getCustomizationId());
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
        if (docCustomization.getMaximumNumericValue() != null) modelCustomization.setMaximumNumericValue(docCustomization.getMaximumNumericValue());
        if (docCustomization.getMaximumSelectionCount() != null) modelCustomization.setMaximumSelectionCount(docCustomization.getMaximumSelectionCount());
        if (docCustomization.getMaximumTextLength() != null) modelCustomization.setMaximumTextLength(docCustomization.getMaximumTextLength());
        if (docCustomization.getMinimumNumericValue() != null) modelCustomization.setMinimumNumericValue(docCustomization.getMinimumNumericValue());
        if (docCustomization.getMinimumSelectionCount() != null) modelCustomization.setMinimumSelectionCount(docCustomization.getMinimumSelectionCount());
        modelCustomization.setLengthValidationFlag(docCustomization.getLengthValidationFlag());
        modelCustomization.setImmediateSubmissionFlag(docCustomization.getImmediateSubmissionFlag());
        modelCustomization.setRangeValidationFlag(docCustomization.getRangeValidationFlag());
        modelCustomization.setRegularExpressionValidationFlag(docCustomization.getRegularExpressionValidationFlag());
        modelCustomization.setSelectionCountValidationFlag(docCustomization.getSelectionCountValidationFlag());
        modelCustomization.setSensitiveFlag(docCustomization.getSensitiveFlag());

//        logger.debug(("docCustomization.getSensitiveTextValue()= " + docCustomization.getSensitiveTextValue()));
//        logger.debug(("docCustomization.docCustomization.getTextValue()= " + docCustomization.getTextValue()));
//        logger.debug(("docCustomization.getSensitiveFlag())= " + docCustomization.getSensitiveFlag()));

        if (docCustomization.getSensitiveFlag())
        {
            modelCustomization.setTextValue(docCustomization.getSensitiveTextValue());
        }
        else
        {
            modelCustomization.setTextValue(docCustomization.getTextValue());
        }

        if (docCustomization.getValidFlag() != null) modelCustomization.setValidFlag(docCustomization.getValidFlag());
        modelCustomization.setRank(docCustomization.getRank());
        if (docCustomization.getRegularExpression() != null) modelCustomization.setRegularExpression(docCustomization.getRegularExpression());
        if (docCustomization.getValidationErrorText() != null) modelCustomization.setValidationErrorString(docCustomization.getValidationErrorText());
        if (docCustomization.getWarningText() != null) modelCustomization.setWarningText(docCustomization.getWarningText());

        if (docCustomization.getItemList() != null)
        {
            ArrayList<CustomizationItem> item = new ArrayList<CustomizationItem>();

            translateCustomizationItem(docCustomization.getItemList().getItem(), item);
            modelCustomization.setItem(item);
        }
        //Jim's code comment : [Reason] - ArrayList cleared out for each customization
        //modelList.setCustomization(new ArrayList<Customization>());
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
        if (docPrice.getInitialAmount() != null) modelPrice.setInitialAmount(docPrice.getInitialAmount());
        if (docPrice.getInitialAmountLengthMonths() != null) modelPrice.setInitialAmountLengthMonths(docPrice.getInitialAmountLengthMonths());
        if (docPrice.getPricePeriodType() != null) modelPrice.setPricePeriodType(docPrice.getPricePeriodType());
        if (docPrice.getRegularAmount() != null) modelPrice.setRegularAmount(docPrice.getRegularAmount());
    }
}


