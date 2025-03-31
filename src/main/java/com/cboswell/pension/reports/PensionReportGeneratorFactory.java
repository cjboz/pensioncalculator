package com.cboswell.pension.reports;

/**
 * Factory class to handle the generation of different types of PensionReportGenerator
 */
public class PensionReportGeneratorFactory {

    public static final String STATE = "State";
    public static final String WORKPLACE = "Workplace";

    public static PensionReportGenerator createReportGenerator(final String type) {
        switch (type) {
            case STATE:
                return new StatePensionReportGenerator();
            case WORKPLACE:
                return new WorkplacePensionReportGenerator();
            default:
                throw new IllegalArgumentException("Unknown report generator type.");
        }
    }
}