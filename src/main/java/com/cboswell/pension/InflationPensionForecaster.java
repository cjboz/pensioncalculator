package com.cboswell.pension;

/**
 * An alternative Pension Forecaster that adds an amount to the total to account for inflation
 */
public class InflationPensionForecaster implements PensionForecaster {

    //Crude multiplier to account for inflation
    private double annualInflationMultiplier;

    public InflationPensionForecaster(final double annualInflationMultiplier) {
        this.annualInflationMultiplier = annualInflationMultiplier;
    }

    @Override
    public double forecastPension(Person person) {
        int yearsToRetirement = person.getRetirementAge() - person.getAge();
        double totalContributionMultiplier = (person.getEmployeeContributionPercentage() + person.getEmployerContributionPercentage()) / 100.0;
        double rawForecast = yearsToRetirement * person.getSalary() *
                totalContributionMultiplier * (Math.pow(annualInflationMultiplier, yearsToRetirement));
        return Math.round(rawForecast * 100.0) / 100.0;
    }
}
