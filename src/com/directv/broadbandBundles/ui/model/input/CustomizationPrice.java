package com.directv.broadbandBundles.ui.model.input;

import java.math.BigDecimal;


/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 3:55 PM
 * Bridgevine Customization Price
 */
public class CustomizationPrice
{
    private String pricePeriodType;
    private BigDecimal initialAmount;
    private long initialAmountLengthMonths;
    private BigDecimal regularAmount;

    public String getPricePeriodType()
    {
        return pricePeriodType;
    }

    public void setPricePeriodType(String pricePeriodType)
    {
        this.pricePeriodType = pricePeriodType;
    }

    public BigDecimal getInitialAmount()
    {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount)
    {
        this.initialAmount = initialAmount;
    }

    public long getInitialAmountLengthMonths()
    {
        return initialAmountLengthMonths;
    }

    public void setInitialAmountLengthMonths(long initialAmountLengthMonths)
    {
        this.initialAmountLengthMonths = initialAmountLengthMonths;
    }

    public BigDecimal getRegularAmount()
    {
        return regularAmount;
    }

    public void setRegularAmount(BigDecimal regularAmount)
    {
        this.regularAmount = regularAmount;
    }

    @Override
    public String toString()
    {
        return "CustomizationPrice{" +
                "pricePeriodType='" + pricePeriodType + '\'' +
                ", initialAmount=" + initialAmount +
                ", initialAmountLengthMonths=" + initialAmountLengthMonths +
                ", regularAmount=" + regularAmount +
                '}';
    }
}
