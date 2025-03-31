package com.cboswell.pension;

import com.cboswell.pension.validators.NationalInsuranceNumberValidator;

import java.time.LocalDate;
import java.time.Period;

/**
 * Person Class - Holds details about a person relevant to calculating or forecasting their pension
 */
public class Person {

    private String forename;
    private String surname;
    private LocalDate dob;
    private String nationalInsuranceNumber;
    private int nationalInsuranceYears;
    private double workplacePensionPot;
    private double salary;
    private double employeeContributionPercentage;
    private double employerContributionPercentage;
    private int retirementAge;

    public Person(final PersonBuilder builder) {
        this.forename = builder.forename;
        this.surname = builder.surname;
        this.dob = builder.dob;
        this.nationalInsuranceNumber = builder.nationalInsuranceNumber;
        this.nationalInsuranceYears = builder.nationalInsuranceYears;
        this.workplacePensionPot = builder.workplacePensionPot;
        this.salary = builder.salary;
        this.employeeContributionPercentage = builder.employeeContributionPercentage;
        this.employerContributionPercentage = builder.employerContributionPercentage;
        this.retirementAge = builder.retirementAge;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDob() {
        return dob;
    }

    /**
     * @return The number of years of qualifying national insurance contributions made to date
     */
    public int getNationalInsuranceYears() {
        return nationalInsuranceYears;
    }

    public double getWorkplacePensionPot() {
        return workplacePensionPot;
    }

    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public double getSalary() {
        return salary;
    }

    public double getEmployeeContributionPercentage() {
        return employeeContributionPercentage;
    }

    public double getEmployerContributionPercentage() {
        return employerContributionPercentage;
    }

    public int getRetirementAge() {
        return retirementAge;
    }

    /**
     * Performs some basic validation on the Person
     *
     * @throws PersonValidationException - provides details of the validation error
     */
    public void validate() throws PersonValidationException {
        if (forename == null || forename.isEmpty()) {
            throw new PersonValidationException("No forename specified");
        }
        if (surname == null || surname.isEmpty()) {
            throw new PersonValidationException("No surname specified");
        }
        if (dob == null) {
            throw new PersonValidationException("No date of birth specified");
        }
        if (nationalInsuranceNumber == null || nationalInsuranceNumber.isEmpty()) {
            throw new PersonValidationException("No NI number specified");
        }
        if (!NationalInsuranceNumberValidator.isValidNINumber(nationalInsuranceNumber)) {
            throw new PersonValidationException("Invalid NI number");
        }
        if (nationalInsuranceYears < 0) {
            throw new PersonValidationException("National Insurance Years cannot be negative");
        }
        if (salary < 0) {
            throw new PersonValidationException("Salary cannot be negative");
        }
        if (workplacePensionPot < 0) {
            throw new PersonValidationException("Workplace Pension Pot cannot be negative");
        }
        if (employeeContributionPercentage < 0) {
            throw new PersonValidationException("Employee Contribution Percentage cannot be negative");
        }
        if (employerContributionPercentage < 0) {
            throw new PersonValidationException("Employee Contribution Percentage cannot be negative");
        }
        if (retirementAge < 0) {
            throw new PersonValidationException("Retirement Age cannot be negative");
        }
    }

    /**
     * Is this a valid person object?
     *
     * @return true if no validation exception is thrown
     */
    public boolean isValid() {
        try {
            validate();
        } catch (PersonValidationException pve) {
            return false;
        }
        return true;
    }

    /**
     * @return The person's current age in years
     */
    public int getAge() {
        LocalDate dobToUse = getDob();
        if (dobToUse == null) {
            throw new IllegalArgumentException("Date of Birth cannot be null");
        }
        LocalDate currentDate = LocalDate.now();
        if (dobToUse.isAfter(currentDate)) {
            throw new IllegalArgumentException("Date of Birth cannot be in the future");
        }
        return Period.between(dobToUse, currentDate).getYears();
    }

    /**
     * PersonBuilder - Iteratively constructs a Person object
     */
    public static class PersonBuilder {

        private String forename;
        private String surname;
        private LocalDate dob;
        private String nationalInsuranceNumber;
        private int nationalInsuranceYears;
        private double workplacePensionPot;
        private double salary;
        private double employeeContributionPercentage;
        private double employerContributionPercentage;
        private int retirementAge;

        public PersonBuilder forename(final String forename) {
            this.forename = forename;
            return this;
        }

        public PersonBuilder surname(final String surname) {
            this.surname = surname;
            return this;
        }

        public PersonBuilder dob(final LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public PersonBuilder nationalInsuranceNumber(final String nationalInsuranceNumber) {
            this.nationalInsuranceNumber = nationalInsuranceNumber;
            return this;
        }

        public PersonBuilder nationalInsuranceYears(final int nationalInsuranceYears) {
            this.nationalInsuranceYears = nationalInsuranceYears;
            return this;
        }

        public PersonBuilder workplacePensionPot(final double workplacePensionPot) {
            this.workplacePensionPot = workplacePensionPot;
            return this;
        }

        public PersonBuilder salary(final double salary) {
            this.salary = salary;
            return this;
        }

        public PersonBuilder employeeContributionPercentage(final double employeeContributionPercentage) {
            this.employeeContributionPercentage = employeeContributionPercentage;
            return this;
        }

        public PersonBuilder employerContributionPercentage(final double employerContributionPercentage) {
            this.employerContributionPercentage = employerContributionPercentage;
            return this;
        }

        public PersonBuilder retirementAge(final int retirementAge) {
            this.retirementAge = retirementAge;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

}
