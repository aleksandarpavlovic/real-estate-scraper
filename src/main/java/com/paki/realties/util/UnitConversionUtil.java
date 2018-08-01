package com.paki.realties.util;

import com.paki.realties.enums.AreaMeasurementUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitConversionUtil {

    public static BigDecimal ARE_TO_M2 = new BigDecimal(100);
    public static BigDecimal HECTARE_TO_M2 = new BigDecimal(10000);

    public static BigDecimal convertArea(BigDecimal value, AreaMeasurementUnit fromUnit, AreaMeasurementUnit toUnit) {
        BigDecimal multiplier = m2Multiplier(fromUnit);
        BigDecimal divisor = m2Multiplier(toUnit);
        return value.multiply(multiplier).divide(divisor, 2, RoundingMode.CEILING);
    }

    private static BigDecimal m2Multiplier(AreaMeasurementUnit unit) {
        switch (unit) {
            case SQUARE_METER:
                return BigDecimal.ONE;
            case ARE:
                return ARE_TO_M2;
            case HECTARE:
                return HECTARE_TO_M2;
            default:
                throw new RuntimeException();
        }
    }
}
