package com.cboswell.pension;

/**
 * Interface defining the behaviour of a Pension Forecaster
 */
public interface PensionForecaster {

    public static final int DEFAULT_MAXIMUM_RETIREMENT_AGE = 90;

    double forecastPension(final Person person);

    default double validateForecastPension(final Person person) throws PensionForecastException {
        int maxRetirementAge = getMaxRetirementAge();
        if (person == null) {
            throw new PensionForecastException("No person supplied");
        }
        try {
            person.validate();
        } catch (PersonValidationException pve) {
            throw new PensionForecastException("Unable to validate person details supplied", pve);
        }
        int retirementAge = person.getRetirementAge();
        if (retirementAge > maxRetirementAge) {
            throw new PensionForecastException("Retirement age above "
                    + maxRetirementAge + " is not supported");
        }
        int age = person.getAge();
        if (retirementAge < age) {
            throw new PensionForecastException("Retirement age is below the person's current age of " + age);
        }
        return forecastPension(person);
    }

    default int getMaxRetirementAge() {
        return DEFAULT_MAXIMUM_RETIREMENT_AGE;
    }
}
