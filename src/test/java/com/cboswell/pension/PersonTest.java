package com.cboswell.pension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.*;

/**
 * Unit Tests for the com.cboswell.pension.Person class
 */
public class PersonTest {

    private Person.PersonBuilder validPersonBuilder;
    private Person validPerson;

    @BeforeEach
    public void setup() {
        validPersonBuilder = new Person.PersonBuilder().forename("Henry")
                .surname("Smith")
                .dob(LocalDate.of(1955, Month.APRIL, 13))
                .nationalInsuranceNumber("Test NI Number 1")
                .salary(32566.75)
                .nationalInsuranceYears(37)
                .employeeContributionPercentage(4.5)
                .employerContributionPercentage(5.0)
                .workplacePensionPot(123456.78);

        validPerson = validPersonBuilder.build();
    }

    /**
     * Test that the PersonBuilder correctly populates the desired fields when construction a new Person object
     */
    @Test
    public void testPersonBuilder() {
        Assertions.assertEquals("Henry", validPerson.getForename());
        Assertions.assertEquals("Smith", validPerson.getSurname());
        Assertions.assertEquals(LocalDate.of(1955, Month.APRIL, 13), validPerson.getDob());
        Assertions.assertEquals("Test NI Number 1", validPerson.getNationalInsuranceNumber());
        Assertions.assertEquals(32566.75, validPerson.getSalary(), 0.001);
        Assertions.assertEquals(37, validPerson.getNationalInsuranceYears());
        Assertions.assertEquals(4.5, validPerson.getEmployeeContributionPercentage(), 0.001);
        Assertions.assertEquals(5.0, validPerson.getEmployerContributionPercentage(), 0.001);
        Assertions.assertEquals(123456.78, validPerson.getWorkplacePensionPot(), 0.001);
    }

    @Test
    public void testValidPersonPasses() {
        Assertions.assertDoesNotThrow(validPerson::validate);
        //Added the isValid method later, exercise it for true here
        Assertions.assertTrue(validPerson.isValid());
    }

    @Test
    public void testNullForename() {
        Person person = validPersonBuilder.forename(null).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
        //Added the isValid method later, exercise it for false here
        Assertions.assertFalse(person.isValid());
    }

    @Test
    public void testBlankForename() {
        Person person = validPersonBuilder.forename("").build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNullSurname() {
        Person person = validPersonBuilder.surname(null).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testBlankSurname() {
        Person person = validPersonBuilder.surname("").build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNullNINumber() {
        Person person = validPersonBuilder.nationalInsuranceNumber(null).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testBlankNINumber() {
        Person person = validPersonBuilder.nationalInsuranceNumber("").build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNullDob() {
        Person person = validPersonBuilder.dob(null).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNegativeSalary() {
        Person person = validPersonBuilder.salary(-1).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNegativeWorkplacePensionPot() {
        Person person = validPersonBuilder.workplacePensionPot(-1).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNegativeNIYears() {
        Person person = validPersonBuilder.nationalInsuranceYears(-1).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNegativeEmployeeContribution() {
        Person person = validPersonBuilder.employeeContributionPercentage(-1).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNegativeEmployerContribution() {
        Person person = validPersonBuilder.employerContributionPercentage(-1).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testNegativeRetirementAge() {
        Person person = validPersonBuilder.retirementAge(-1).build();
        Assertions.assertThrows(PersonValidationException.class, person::validate);
    }

    @Test
    public void testZeroSalary() {
        Person person = validPersonBuilder.salary(0).build();
        Assertions.assertDoesNotThrow(person::validate);
    }

    @Test
    public void testZeroWorkplacePensionPot() {
        Person person = validPersonBuilder.workplacePensionPot(0).build();
        Assertions.assertDoesNotThrow(person::validate);
    }

    @Test
    public void testZeroNIYears() {
        Person person = validPersonBuilder.nationalInsuranceYears(0).build();
        Assertions.assertDoesNotThrow(person::validate);
    }

    @Test
    public void testZeroEmployeeContribution() {
        Person person = validPersonBuilder.employeeContributionPercentage(0).build();
        Assertions.assertDoesNotThrow(person::validate);
    }

    @Test
    public void testZeroEmployerContribution() {
        Person person = validPersonBuilder.employerContributionPercentage(0).build();
        Assertions.assertDoesNotThrow(person::validate);
    }

    @Test
    public void testZeroRetirementAge() {
        Person person = validPersonBuilder.retirementAge(0).build();
        Assertions.assertDoesNotThrow(person::validate);
    }

    @Test
    public void testGetAgeForValidDateOfBirth() {
        Person spyPerson = spy(new Person(new Person.PersonBuilder()));
        when(spyPerson.getDob()).thenReturn(LocalDate.of(1991, Month.JUNE, 24));

        Assertions.assertEquals(33, spyPerson.getAge());
    }

    @Test
    public void testGetAgeForTenthBirthday() {
        Person spyPerson = spy(new Person(new Person.PersonBuilder()));
        when(spyPerson.getDob()).thenReturn(LocalDate.now().minusYears(10));

        Assertions.assertEquals(10, spyPerson.getAge());
    }

    @Test
    public void testGetAgeForFutureDateOfBirth() {
        Person spyPerson = spy(new Person(new Person.PersonBuilder()));
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        when(spyPerson.getDob()).thenReturn(tomorrow);

        Assertions.assertThrows(IllegalArgumentException.class, spyPerson::getAge);
    }

    @Test
    public void testGetAgeForNullDateOfBirth() {
        Person spyPerson = spy(new Person(new Person.PersonBuilder()));
        when(spyPerson.getDob()).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, spyPerson::getAge);
    }
}
