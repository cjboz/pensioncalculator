package com.cboswell.pension;

/**
 * Factory class to handle the generation of different types of PensionForecaster
 */
public class PensionForecasterFactory {

    public static final String BASIC = "Basic";
    public static final String INFLATION_3 = "Inflation3";
    public static final String INFLATION_5 = "Inflation5";

    public static PensionForecaster createForecaster(final String type) {
        switch (type) {
            case BASIC:
                return new BasicPensionForecaster();
            case INFLATION_3:
                return new InflationPensionForecaster(1.03);
            case INFLATION_5:
                return new InflationPensionForecaster(1.05);
            default:
                throw new IllegalArgumentException("Unknown forecaster type.");
        }
    }
}