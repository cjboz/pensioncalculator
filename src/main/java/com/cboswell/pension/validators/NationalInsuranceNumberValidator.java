package com.cboswell.pension.validators;

import java.util.regex.Pattern;

/**
 * Validator Class for UK National Insurance Numbers
 */
public class NationalInsuranceNumberValidator {

    // Simplified regular expression for NI Number validation. The actual logic is slightly more nuanced than this.
    private static final String NI_REGEX = "^(?!BG|GB|KN|NK|NT|TN|ZZ)[A-CEGHJ-PRSTW-Z]{2}\\d{6}[A-D]?$";
    private static final Pattern NI_PATTERN = Pattern.compile(NI_REGEX);

    /**
     * Validates a UK National Insurance number.
     *
     * @param niNumber The National Insurance number to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidNINumber(String niNumber) {
        if (niNumber == null || niNumber.isEmpty()) {
            return false;
        }

        //Remove spaces before validating. We will allow any number of whitespace characters for this implementation.
        return NI_PATTERN.matcher(niNumber.replace(" ", "")).matches();
    }
}