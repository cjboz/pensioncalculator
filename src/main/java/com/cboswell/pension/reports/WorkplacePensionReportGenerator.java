package com.cboswell.pension.reports;

import com.cboswell.pension.PensionForecaster;
import com.cboswell.pension.PensionForecasterFactory;
import com.cboswell.pension.Person;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Generates a report for a list of people about their workplace pension
 */
public class WorkplacePensionReportGenerator implements PensionReportGenerator {
    Logger logger = Logger.getLogger(WorkplacePensionReportGenerator.class.getName());

    @Override
    public void generateReport(List<Person> personList) {
        if (personList == null || personList.isEmpty()) {
            logger.warning("No report subjects to generate for");
            return;
        }
        int total = personList.size();
        long invalid = personList.stream().filter(p -> !p.isValid()).count();
        logger.info("Generating Workplace Pension Report for " + total + " subjects...\n"
                + "Immediately discounting " + invalid + " invalid subjects");

        PensionForecaster basicForecaster = PensionForecasterFactory.createForecaster(PensionForecasterFactory.BASIC);
        PensionForecaster inflation3 = PensionForecasterFactory.createForecaster(PensionForecasterFactory.INFLATION_3);
        PensionForecaster inflation5 = PensionForecasterFactory.createForecaster(PensionForecasterFactory.INFLATION_5);

        //Sort by forename, surname
        personList = personList.stream().sorted(Comparator.comparing(p -> p.getForename() + " "
                + p.getSurname())).collect(Collectors.toList());

        for (Person person : personList) {
            StringBuilder sbPerson = new StringBuilder("\n");
            String fullPersonDesc = person.getForename() + " " + person.getSurname()
                    + " (" + person.getNationalInsuranceNumber() + ") - ";
            sbPerson.append(fullPersonDesc);
            sbPerson.append("Basic: ").append(basicForecaster.validateForecastPension(person));
            sbPerson.append(" 3% Inflation: ").append(inflation3.validateForecastPension(person));
            sbPerson.append(" 5% Inflation: ").append(inflation5.validateForecastPension(person));

            System.out.println(sbPerson);
        }
    }
}
