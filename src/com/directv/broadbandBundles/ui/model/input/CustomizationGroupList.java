package com.directv.broadbandBundles.ui.model.input;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 3:46 PM
 * Bridgevine Customization Group List
 */
public class CustomizationGroupList
{

    private String questionType;
    private ArrayList<CustomizationGroup> customizationGroup;

    public String getQuestionType()
    {
        return questionType;
    }

    public void setQuestionType(String questionType)
    {
        this.questionType = questionType;
    }

    public ArrayList<CustomizationGroup> getCustomizationGroup()
    {
        return customizationGroup;
    }

    public void setCustomizationGroup(ArrayList<CustomizationGroup> customizationGroup)
    {
        this.customizationGroup = customizationGroup;
    }

    @Override
    public String toString()
    {
        return "CustomizationGroupList{" +
                "questionType='" + questionType + '\'' +
                ", customizationGroup=" + customizationGroup +
                '}';
    }
}
