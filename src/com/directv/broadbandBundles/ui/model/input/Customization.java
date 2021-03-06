package com.directv.broadbandBundles.ui.model.input;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 4:01 PM
 * Bridgevine Customization
 */
public class Customization
{
    private String customizationId;
    private String code;
    private String type;
    private String name;
    private String label;
    private String description;
    private String helpText;
    private String helpTitle;
    private String helpType;
    private boolean isRequired;
    private ArrayList<CustomizationItem> item;

    private long maximumNumericValue;
    private long maximumSelectionCount;
    private long maximumTextLength;
    private long minimumNumericValue;
    private long minimumSelectionCount;
    private boolean lengthValidationFlag;
    private boolean immediateSubmissionFlag;
    private boolean rangeValidationFlag;
    private boolean regularExpressionValidationFlag;
    private boolean selectionCountValidationFlag;
    private boolean sensitiveFlag;
    private boolean validFlag;

    private long rank;
    private String regularExpression;
    private String textValue;
    private String validationErrorString;
    private String warningText;

    public ArrayList<CustomizationItem> getItem()
    {
        return item;
    }

    public void setItem(ArrayList<CustomizationItem> item)
    {
        this.item = item;
    }

    public String getCustomizationId()
    {
        return customizationId;
    }

    public void setCustomizationId(String customizationId)
    {
        this.customizationId = customizationId;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getHelpText()
    {
        return helpText;
    }

    public void setHelpText(String helpText)
    {
        this.helpText = helpText;
    }

    public String getHelpTitle()
    {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle)
    {
        this.helpTitle = helpTitle;
    }

    public String getHelpType()
    {
        return helpType;
    }

    public void setHelpType(String helpType)
    {
        this.helpType = helpType;
    }

    public boolean isRequired()
    {
        return isRequired;
    }

    public void setRequired(boolean required)
    {
        isRequired = required;
    }

    public long getMaximumNumericValue()
    {
        return maximumNumericValue;
    }

    public void setMaximumNumericValue(long maximumNumericValue)
    {
        this.maximumNumericValue = maximumNumericValue;
    }

    public long getMaximumSelectionCount()
    {
        return maximumSelectionCount;
    }

    public void setMaximumSelectionCount(long maximumSelectionCount)
    {
        this.maximumSelectionCount = maximumSelectionCount;
    }

    public long getMaximumTextLength()
    {
        return maximumTextLength;
    }

    public void setMaximumTextLength(long maximumTextLength)
    {
        this.maximumTextLength = maximumTextLength;
    }

    public long getMinimumNumericValue()
    {
        return minimumNumericValue;
    }

    public void setMinimumNumericValue(long minimumNumericValue)
    {
        this.minimumNumericValue = minimumNumericValue;
    }

    public long getMinimumSelectionCount()
    {
        return minimumSelectionCount;
    }

    public void setMinimumSelectionCount(long minimumSelectionCount)
    {
        this.minimumSelectionCount = minimumSelectionCount;
    }

    public boolean isLengthValidationFlag()
    {
        return lengthValidationFlag;
    }

    public void setLengthValidationFlag(boolean lengthValidationFlag)
    {
        this.lengthValidationFlag = lengthValidationFlag;
    }

    public boolean isImmediateSubmissionFlag()
    {
        return immediateSubmissionFlag;
    }

    public void setImmediateSubmissionFlag(boolean immediateSubmissionFlag)
    {
        this.immediateSubmissionFlag = immediateSubmissionFlag;
    }

    public boolean isRangeValidationFlag()
    {
        return rangeValidationFlag;
    }

    public void setRangeValidationFlag(boolean rangeValidationFlag)
    {
        this.rangeValidationFlag = rangeValidationFlag;
    }

    public boolean isRegularExpressionValidationFlag()
    {
        return regularExpressionValidationFlag;
    }

    public void setRegularExpressionValidationFlag(boolean regularExpressionValidationFlag)
    {
        this.regularExpressionValidationFlag = regularExpressionValidationFlag;
    }

    public boolean isSelectionCountValidationFlag()
    {
        return selectionCountValidationFlag;
    }

    public void setSelectionCountValidationFlag(boolean selectionCountValidationFlag)
    {
        this.selectionCountValidationFlag = selectionCountValidationFlag;
    }

    public boolean isSensitiveFlag()
    {
        return sensitiveFlag;
    }

    public void setSensitiveFlag(boolean sensitiveFlag)
    {
        this.sensitiveFlag = sensitiveFlag;
    }

    public boolean isValidFlag()
    {
        return validFlag;
    }

    public void setValidFlag(boolean validFlag)
    {
        this.validFlag = validFlag;
    }

    public long getRank()
    {
        return rank;
    }

    public void setRank(long rank)
    {
        this.rank = rank;
    }

    public String getRegularExpression()
    {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression)
    {
        this.regularExpression = regularExpression;
    }

    public String getTextValue()
    {
        return textValue;
    }

    public void setTextValue(String textValue)
    {
        this.textValue = textValue;
    }

    public String getValidationErrorString()
    {
        return validationErrorString;
    }

    public void setValidationErrorString(String validationErrorString)
    {
        this.validationErrorString = validationErrorString;
    }

    public String getWarningText()
    {
        return warningText;
    }

    public void setWarningText(String warningText)
    {
        this.warningText = warningText;
    }

    @Override
    public String toString()
    {
        return "Customization{" +
                "customizationId='" + customizationId + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", helpText='" + helpText + '\'' +
                ", helpTitle='" + helpTitle + '\'' +
                ", helpType='" + helpType + '\'' +
                ", isRequired=" + isRequired +
                ", item=" + item +
                ", maximumNumericValue=" + maximumNumericValue +
                ", maximumSelectionCount=" + maximumSelectionCount +
                ", maximumTextLength=" + maximumTextLength +
                ", minimumNumericValue=" + minimumNumericValue +
                ", minimumSelectionCount=" + minimumSelectionCount +
                ", lengthValidationFlag=" + lengthValidationFlag +
                ", immediateSubmissionFlag=" + immediateSubmissionFlag +
                ", rangeValidationFlag=" + rangeValidationFlag +
                ", regularExpressionValidationFlag=" + regularExpressionValidationFlag +
                ", selectionCountValidationFlag=" + selectionCountValidationFlag +
                ", sensitiveFlag=" + sensitiveFlag +
                ", validFlag=" + validFlag +
                ", rank=" + rank +
                ", regularExpression='" + regularExpression + '\'' +
                ", textValue='" + textValue + '\'' +
                ", validationErrorString='" + validationErrorString + '\'' +
                ", warningText='" + warningText + '\'' +
                '}';
    }
}
