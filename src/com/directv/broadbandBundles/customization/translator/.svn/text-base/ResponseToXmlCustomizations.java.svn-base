package com.directv.broadbandBundles.customization.translator;

import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.ei.schemas.entities.v31.MessageCategoryTypesEntity;
import com.directv.ei.schemas.entities.v31.MessageDetailEntity;
import com.directv.ei.schemas.entities.v31.MessageEntity;
import com.directv.ei.schemas.entities.v31.MessageListEntity;
import com.directv.ei.schemas.entities.v31.MessageSeverityTypesEntity;
import com.directv.ei.schemas.entities.v31.MessageStatusTypesEntity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.*;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoUpdatedCartEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.CartPromotion;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.NonVideoProductComponentEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.NonVideoReceiptItemEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.NonVideoStandardAttributeEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsResponseEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ReceiptItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/3/11
 * Time: 9:31 AM
 * Translate a Bridgevine Customization Response into the XML required for punch out
 */
public class ResponseToXmlCustomizations
{
    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.translator.ResponseToXmlCustomizations");

    public static ResponseDocument getXmlFromResponse(ProcessNonVideoCustomizationsResponseEntity customizationResponse)
    {
        ResponseDocument doc = ResponseDocument.Factory.newInstance();

        com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ProcessNonVideoCustomizationsResponseEntity response = doc.addNewResponse();

        //set required fields
        response.setVisitId(customizationResponse.getVisitId());
        response.setCreditCardRequiredFlag(customizationResponse.getCreditCardRequiredFlag());
        response.setCustomizationsCompletedFlag(customizationResponse.getCustomizationsCompletedFlag());

        //check for optional cart
        if (customizationResponse.getUpdatedCart() != null)
        {
            response.addNewUpdatedCart();

            response.getUpdatedCart().setInvoiceAmountTotal(customizationResponse.getUpdatedCart().getInvoiceAmountTotal());
            response.getUpdatedCart().setInvoiceMonthlyAmountTotal(customizationResponse.getUpdatedCart().getInvoiceAmountTotal());
            response.getUpdatedCart().setInvoiceOneTimeAmountTotal(customizationResponse.getUpdatedCart().getInvoiceOneTimeAmountTotal());

            //if promotion list is not empty add it to document
            if (customizationResponse.getUpdatedCart().getCartPromotionList() != null
                    && customizationResponse.getUpdatedCart().getCartPromotionList().getCartPromotion() != null)
            {
                response.getUpdatedCart().addNewCartPromotionList();
                for (CartPromotion promotion : customizationResponse.getUpdatedCart().getCartPromotionList().getCartPromotion())
                {
                    NonVideoUpdatedCartEntity.CartPromotionList.CartPromotion cart = response.getUpdatedCart().getCartPromotionList().addNewCartPromotion();
                    cart.setCode(promotion.getCode());
                    cart.setId(promotion.getId());
                    cart.setName(promotion.getName());
                    cart.setRank(promotion.getRank());
                }
            }

            //if receipt invoice list not empty add it to document
            if (customizationResponse.getUpdatedCart().getReceiptInvoiceItemList() != null
                    && customizationResponse.getUpdatedCart().getReceiptInvoiceItemList().getReceiptItem() != null)
            {
                response.getUpdatedCart().addNewReceiptInvoiceItemList();
                for (NonVideoReceiptItemEntity receipt : customizationResponse.getUpdatedCart().getReceiptInvoiceItemList().getReceiptItem())
                {
                    com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoReceiptItemEntity item = response.getUpdatedCart().getReceiptInvoiceItemList().addNewReceiptItem();
                    item.setName(receipt.getName());
                    item.setProductId(receipt.getProductId());
                    item.setLineItemId(receipt.getLineItemId());
                    item.setAmount(receipt.getAmount());
                    item.setPricePeriodType(receipt.getPricePeriodType());
                    item.setPriceType(receipt.getPriceType());
                    item.setProviderId(receipt.getProviderId());
                    item.setProviderName(receipt.getProviderName());
                    item.setVerticalId(receipt.getVerticalId());
                    item.setVerticalName(receipt.getVerticalName());
                    item.setCategoryId(receipt.getCategoryId());
                    item.setCategoryName(receipt.getCategoryName());

                    //if attribute list is not empty add it to document
                    if (receipt.getStandardAttributeList() != null && receipt.getStandardAttributeList().getStandardAttribute() != null)
                    {
                        item.addNewStandardAttributeList();
                        for (NonVideoStandardAttributeEntity attribute : receipt.getStandardAttributeList().getStandardAttribute())
                        {
                            com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoStandardAttributeEntity itemAttribute = item.getStandardAttributeList().addNewStandardAttribute();
                            itemAttribute.setDataType(attribute.getDataType());
                            itemAttribute.setName(attribute.getName());
                            itemAttribute.setValue(attribute.getValue());
                        }
                    }
                    //if component list is not empty add it to document
                    if (receipt.getComponentList() != null && receipt.getComponentList().getComponent() != null)
                    {
                        item.addNewComponentList();
                        for (NonVideoProductComponentEntity component : receipt.getComponentList().getComponent())
                        {
                            com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoProductComponentEntity itemComponent = item.getComponentList().addNewComponent();
                            itemComponent.setProductId(component.getProductId());
                            itemComponent.setLineItemId(component.getLineItemId());
                            itemComponent.setVerticalId(component.getVerticalId());
                            itemComponent.setVerticalName(component.getVerticalName());
                            itemComponent.setCategoryId(component.getCategoryId());
                            itemComponent.setCategoryName(component.getCategoryName());
                        }
                    }

                    //if child item list is not empty add it to the document
                    if (receipt.getChildItemList() != null && receipt.getChildItemList().getReceiptItem() != null)
                    {
                        item.addNewChildItemList();
                        for (ReceiptItem receiptItem : receipt.getChildItemList().getReceiptItem())
                        {
                            com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoReceiptItemEntity.ChildItemList.ReceiptItem childItem = item.getChildItemList().addNewReceiptItem();
                            childItem.setName(receiptItem.getName());
                            childItem.setAmount(receiptItem.getAmount());
                            childItem.setPriceType(receiptItem.getPriceType());
                            childItem.setPricePeriodType(receiptItem.getPricePeriodType());
                            childItem.setPricePeriodTypeId(receiptItem.getPricePeriodTypeId());
                        }
                    }
                }
            }
        }

        return doc;

    }

    public static ResponseDocument getErrorXmlFromModel(CustomizationSelection selectionModel, String errorMessage)
    {
        ResponseDocument doc = ResponseDocument.Factory.newInstance();

        try
        {

            com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ProcessNonVideoCustomizationsResponseEntity response = doc.addNewResponse();

            //set required fields
            response.setVisitId(selectionModel.getVisitId());
            //this implies we had a fatal error
            response.setCustomizationsCompletedFlag(false);

            response.addNewMessageList();

            MessageListEntity messageList = response.getMessageList();
            messageList.addNewMessage();

            MessageEntity message = messageList.getMessageArray(0);
            message.setMessageStatus(MessageStatusTypesEntity.FAILURE);
            message.setProvider("NVC");
            message.addNewMessageDetail();

            MessageDetailEntity messageDetail = message.getMessageDetail();
            messageDetail.setMessageCode("1");
            messageDetail.setMessageText("NVC failed with a fatal error from EI Web Service ProcessNonVideoCustomizations.");
            messageDetail.setMessageSeverity(MessageSeverityTypesEntity.FATAL);
            messageDetail.setMessageCategory(MessageCategoryTypesEntity.SYSTEM);
            messageDetail.setAdditionalInformation(errorMessage);
        }
        catch (Exception e)
        {
            logger.fatal("Failed to generate punch out error from fatal error from EI Web Service:", e);
            StackTraceElement[] steArray = e.getStackTrace();
            if (steArray != null)
            {
                for (StackTraceElement ste : steArray)
                {
                    logger.fatal("at " + ste.toString());
                }
            }
        }

        return doc;

    }

    public static ResponseDocument getErrorXmlFromResponse(
            ProcessNonVideoCustomizationsResponseEntity customizationResponse,
            CustomizationSelection selectionModel
    )
    {
        ResponseDocument doc = ResponseDocument.Factory.newInstance();

        try
        {
            com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ProcessNonVideoCustomizationsResponseEntity response = doc.addNewResponse();

            //set required fields
            response.setVisitId(selectionModel.getVisitId());
            //this implies we had a fatal error
            response.setCustomizationsCompletedFlag(false);

            MessageListEntity messageList = response.addNewMessageList();

            for (com.directv.ei.schemas.entities.v3_1.MessageEntity eMessage : customizationResponse.getMessageList().getMessage())
            {
                MessageEntity message = messageList.addNewMessage();
                message.setMessageStatus(MessageStatusTypesEntity.PARTIAL);
                message.setProvider(eMessage.getProvider());
                MessageDetailEntity messageDetail = message.addNewMessageDetail();
                messageDetail.setMessageSeverity(MessageSeverityTypesEntity.ERROR);
                messageDetail.setMessageCategory(MessageCategoryTypesEntity.BUSINESS);
                if (eMessage.getMessageDetail() != null)
                {
                    messageDetail.setMessageCode(eMessage.getMessageDetail().getMessageCode());
                    messageDetail.setMessageText(eMessage.getMessageDetail().getMessageText());
                    messageDetail.setAdditionalInformation(eMessage.getMessageDetail().getAdditionalInformation());
                }
            }
        }
        catch(Exception e)
        {
            logger.fatal("Failed to generate punch out error from message list:", e);
            StackTraceElement[] steArray = e.getStackTrace();
            if (steArray != null)
            {
                for (StackTraceElement ste : steArray)
                {
                    logger.fatal("at " + ste.toString());
                }
            }
        }


        return doc;
    }
}
