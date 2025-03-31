package com.cboswell.pension.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests to exercise the com.cboswell.pension.validators.NationalInsuranceNumberValidator class
 */
public class NationalInsuranceNumberValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "AB123456C, true", //Valid NI Number
            "ZZ123456A, false", //ZZ is not an allowed prefix
            "AB123456E, false", //Only letters A to D allowed in suffix
            "ABC12345D, false", //Too many letters in prefix
            "AB12345C, false", //Fewer than 6 digits
            "AB1234567C, false", //More than 6 digits
            "AB123456CD, false", //Too many letters in suffix
            "AB 12     34 56 C, true", //Valid NI Number with whitespace should be allowed
            ", false" //Blank String... There are many more edge cases to test here, but you get the idea :)
    })

    public void testNationalInsuranceNumberValidation(final String niNumber, final boolean expectedResult) {
        Assertions.assertEquals(NationalInsuranceNumberValidator.isValidNINumber(niNumber), expectedResult);
    }

    @Test
    public void testNationalInsuranceNumberValidatorHandlesNulls() {
        Assertions.assertFalse(NationalInsuranceNumberValidator.isValidNINumber(null));
    }
}
