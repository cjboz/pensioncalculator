package com.cboswell.pension.reports;

import com.cboswell.pension.Person;

import java.util.List;

/**
 * Interface defining the behaviour of a Pension Report Generator
 */
public interface PensionReportGenerator {

    void generateReport(final List<Person> personList);
}
