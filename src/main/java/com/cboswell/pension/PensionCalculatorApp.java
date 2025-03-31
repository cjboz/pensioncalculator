package com.cboswell.pension;

import com.cboswell.pension.reports.PensionReportGenerator;
import com.cboswell.pension.reports.PensionReportGeneratorFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Main Application Entry Point
 */
public class PensionCalculatorApp {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();

        int threads = 4;

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        try {
            generateRandomizedPersonListInParallel(personList, 1000, threads, executorService);
        } finally {
            // Shutdown the executor service
            executorService.shutdown();
        }

        generateReports(personList);
    }

    /**
     * Adds randomized data to the person list using parallel threads
     *
     * @param total             Total number of person objects to create
     * @param threads           Number of parallel threads to use
     * @param personList        Person list to add to
     * @param executorService   The executor service
     */
    private static void generateRandomizedPersonListInParallel(final List<Person> personList, final int total,
                                                               final int threads, final ExecutorService executorService) {
        //Starting point, we will add a week to this for each new person to get a good spread
        LocalDate dob = LocalDate.of(1940, Month.JANUARY, 1);
        int niNumberDigits = 100000;

        int totalPersons = total;
        int partitionSize = totalPersons / threads;
        // List to hold future tasks
        List<Future<List<Person>>> futures = new ArrayList<>();

        // Submit tasks to the executor service
        for (int threadIndex = 0; threadIndex < threads; threadIndex++) {
            final LocalDate startDob = dob.plusWeeks(threadIndex * partitionSize);
            final int startNiNumber = niNumberDigits + threadIndex * partitionSize;
            final int startIndex = threadIndex * partitionSize;

            Callable<List<Person>> task = () -> {
                List<Person> localPersonList = new ArrayList<>();
                for (int i = 0; i < partitionSize; i++) {
                    Person.PersonBuilder builder = new Person.PersonBuilder();
                    Person person = builder.forename("Person")
                            .surname("Test" + (startIndex + i))
                            .nationalInsuranceNumber("JN" + (startNiNumber + i) + "A")
                            .workplacePensionPot(Math.random() * 1000000)
                            .employerContributionPercentage(Math.random() * 30)
                            .employeeContributionPercentage(Math.random() * 10)
                            .salary(Math.random() * 150000)
                            .nationalInsuranceYears((int) (Math.random() * 50))
                            .retirementAge((int) (50 + (Math.random() * 25)))
                            .dob(startDob.plusWeeks(i))
                            .build();
                    localPersonList.add(person);
                }
                return localPersonList;
            };
            futures.add(executorService.submit(task));
        }

        // Combine results from all threads
        for (Future<List<Person>> future : futures) {
            try {
                personList.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Generates a state pension reports and a workplace pension report for the list of persons provided
     *
     * @param personList list of persons to generate the reports for
     */
    private static void generateReports(final List<Person> personList) {
        PensionReportGenerator statePensionReportGenerator = PensionReportGeneratorFactory.createReportGenerator(PensionReportGeneratorFactory.STATE);
        PensionReportGenerator workplacePensionReportGenerator = PensionReportGeneratorFactory.createReportGenerator(PensionReportGeneratorFactory.WORKPLACE);

        statePensionReportGenerator.generateReport(personList);
        workplacePensionReportGenerator.generateReport(personList);
    }
}
