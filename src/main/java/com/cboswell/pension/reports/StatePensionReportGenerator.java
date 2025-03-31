package com.cboswell.pension.reports;

import com.cboswell.pension.Person;

import java.util.List;
import java.util.logging.Logger;

/**
 * Generates a report for a list of people about their eligibility for state pension
 */
public class StatePensionReportGenerator implements PensionReportGenerator {

    public static final int NATIONAL_INSURANCE_YEARS_FULL = 35;
    public static final int NATIONAL_INSURANCE_YEARS_MIN = 10;
    Logger logger = Logger.getLogger(StatePensionReportGenerator.class.getName());

    @Override
    public void generateReport(List<Person> personList) {
        if (personList == null || personList.isEmpty()) {
            logger.warning("No report subjects to generate for");
            return;
        }
        int total = personList.size();
        int fullyEligible = 0;
        int partiallyEligible = 0;
        int notEligible = 0;
        long invalid = personList.stream().filter(p -> !p.isValid()).count();
        logger.info("Generating State Pension Report for " + total + " subjects...\n" + "Immediately discounting " + invalid + " invalid subjects");

        for (Person person : personList) {
            StringBuilder sbPerson = new StringBuilder("\n");
            String fullPersonDesc = person.getForename() + " " + person.getSurname()
                    + " (" + person.getNationalInsuranceNumber() + ") ";
            sbPerson.append(fullPersonDesc);
            int nationalInsuranceYears = person.getNationalInsuranceYears();
            if (nationalInsuranceYears > NATIONAL_INSURANCE_YEARS_FULL) {
                sbPerson.append("is eligible for full state pension.");
                fullyEligible++;
            } else if (nationalInsuranceYears > NATIONAL_INSURANCE_YEARS_MIN) {
                sbPerson.append("is eligible for partial state pension");
                partiallyEligible++;
            } else {
                sbPerson.append("is not eligible for state pension");
                notEligible++;
            }

            //Using sout for simple report output
            System.out.println(sbPerson);
        }

        String summary = "\n\n************* Summary *************\nTotal subjects: " + total +
                "\nInvalid subjects: " + invalid +
                "\nNumber fully eligible: " + fullyEligible +
                "\nNumber partially eligible: " + partiallyEligible +
                "\nNumber not eligible: " + notEligible;
        System.out.println(summary);
    }
}
