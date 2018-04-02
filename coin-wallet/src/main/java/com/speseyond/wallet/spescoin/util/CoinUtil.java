package com.speseyond.wallet.spescoin.util;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


public class CoinUtil {

    private static Logger LOGGER = Logger.getLogger(CoinUtil.class);

    public static BigDecimal DIVIDE_BY = new BigDecimal("10000000000");

    public static long getLongForText(String text) {
        text = text.replace(",", ".");
        BigDecimal decimal = new BigDecimal(text);
        decimal = decimal.multiply(DIVIDE_BY);
        return decimal.longValue();
    }

    public static String getTextForLong(Long amount) {
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setGroupingUsed(false);
        amountFormat.setMinimumFractionDigits(10);
        amountFormat.setMaximumFractionDigits(10);

        String text = amount.toString().replace(",", ".");
        BigDecimal decimal = new BigDecimal(amount.toString());
        decimal = decimal.setScale(10, RoundingMode.UP);
        decimal = decimal.divide(DIVIDE_BY, RoundingMode.UP);
        return amountFormat.format(decimal.doubleValue());
    }

}
