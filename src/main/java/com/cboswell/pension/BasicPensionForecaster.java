package com.cboswell.pension;

/**
 * Basic implementation of a pension forecaster which uses a very simplistic calculation
 */
public class BasicPensionForecaster implements PensionForecaster {

    @Override
    public double forecastPension(final Person person) {
        int yearsToRetirement = person.getRetirementAge() - person.getAge();
        double totalContributionMultiplier = (person.getEmployeeContributionPercentage() + person.getEmployerContributionPercentage()) / 100.0;
        double rawForecast = yearsToRetirement * person.getSalary() *
                totalContributionMultiplier;
        return Math.round(rawForecast * 100.0) / 100.0;
    }
}
