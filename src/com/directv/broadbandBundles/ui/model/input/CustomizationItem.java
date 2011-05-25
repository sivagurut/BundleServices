package com.directv.broadbandBundles.ui.model.input;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 3:54 PM
 * Bridgevine Customization Item
 */
public class CustomizationItem
{
    private String code;
    private String helpText;
    private String helpTitle;
    private String helpType;
    private String id;
    private boolean isSelected;
    private CustomizationPrice price;
    private String text;
    private String zeroText;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public CustomizationPrice getPrice()
    {
        return price;
    }

    public void setPrice(CustomizationPrice price)
    {
        this.price = price;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getZeroText()
    {
        return zeroText;
    }

    public void setZeroText(String zeroText)
    {
        this.zeroText = zeroText;
    }

    @Override
    public String toString()
    {
        return "CustomizationItem{" +
                "code='" + code + '\'' +
                ", helpText='" + helpText + '\'' +
                ", helpTitle='" + helpTitle + '\'' +
                ", helpType='" + helpType + '\'' +
                ", id='" + id + '\'' +
                ", isSelected=" + isSelected +
                ", price=" + price +
                ", text='" + text + '\'' +
                ", zeroText='" + zeroText + '\'' +
                '}';
    }
}
