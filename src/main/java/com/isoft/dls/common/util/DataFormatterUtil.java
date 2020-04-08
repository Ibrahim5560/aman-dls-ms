/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:11 PM  - File created.
 */

package com.isoft.dls.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Configuration provides a convenience methods for Formatting and Validating Business related data
 */
public abstract class DataFormatterUtil {

    /** UAE mobile number pattern. */
    public static final String MOBILE_NUMBER_PATTERN = "971(50|55|56|52|54|58)[0-9]{7}$";

    /** UAE phone number pattern. */
    private static final String PHONE_NUMBER_PATTERN = "0[2-9][0-9]{7}$";

    /** UAE mail number pattern. */
    private static final String EMAIL_NUMBER_PATTERN = "^[a-zA-Z0-9\\_\\-\\']+(\\.[a-zA-Z0-9\\_\\-\\']+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";

    /** Eid number pattern.*/
    private static final String EID_NUMBER_PATTERN = "^784\\-\\d{4}\\-\\d{7}\\-\\d{1}$";


    /** UAE mobile number pattern. */
//    public static final String MOBILE_NUMBER_PATTERN = "971(50|55|56|52|54|58)[0-9]{7}$";

    /** UAE  number pattern. */
    public static final String NUMBER_PATTERN ="\\d+";

    /** UAE phone number pattern. */
//    private static final String PHONE_NUMBER_PATTERN = "0[2-9][0-9]{7}$";

    /** UAE mail number pattern. */
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9\\_\\-\\']+(\\.[a-zA-Z0-9\\_\\-\\']+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";

    /** OTP pattern. */
    public static final String OTP_PATTERN = "[0-9]{6}$";

    /** Eid number pattern.*/
//    public static final String EID_NUMBER_PATTERN = "^784\\-\\d{4}\\-\\d{7}\\-\\d{1}$";

    /** Local Date pattern.*/
    public static final String LOCAL_DATE_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";

    /** Apha numeric.*/
    public static final String  ALPHA_NUMERIC = "[a-zA-Z0-9]*";

    private static final Logger log = LoggerFactory.getLogger(DataFormatterUtil.class);

    /** Passport number pattern.*/
    public static final String PASSPORT_NUMBER_PATTERN="[$&+,:;=\\\\?@#|/'<>.^*()%!-]";

    private DataFormatterUtil() {
        throw new IllegalStateException("[DataFormatterUtil] class");
    }

    /**
     * Check if input Phone is valid UAE Phone or not
     *
     * @param phone Phone to be validated
     * @return if input email is valid UAE Phone or not
     */
    public static boolean isValidUAEPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher m = pattern.matcher(phone);
        boolean isValid = m.matches();
        if(!isValid){
            return false;
        }

        String subPhone = phone.substring(2,phone.length());
        return !isZerosNumber(subPhone);
    }

    /**
     * Check if input Mobile is valid UAE Mobile or not
     *
     * @param mobile Mobile to be validated
     * @return if input email is valid UAE Phone or not
     */
    public static boolean isValidUAEMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE_NUMBER_PATTERN);
        Matcher m = pattern.matcher(mobile);
        boolean isValid = m.matches();
        if(!isValid) {
            return false;
        }
        String subMobile = mobile.substring(5,mobile.length());

        return !isZerosNumber(subMobile);
    }

    /**
     * Check if number contains of Zeros Number Only
     *
     * @param number Mobile or Phone number
     * @return
     */
    private static boolean isZerosNumber(String number) {
        return !StringUtil.isEmpty(number) && number.equals("0000000");
    }

    /**
     * Check if Eid Number matches follwoing format (NNN-NNNN-NNNNNNN-N).
     *
     * @param eidNumber : Eid Number.
     * @return true if valid eid number format.
     */
    public static boolean isValidEid(String eidNumber) {

        if (StringUtil.isBlank(eidNumber))  {
            return false;
        }

        Pattern pattern = Pattern.compile(EID_NUMBER_PATTERN);
        Matcher m = pattern.matcher(eidNumber);
        return m.matches();
    }

    /**
     * check if input email is valied or not
     *
     * @return if input email is valied or not
     * @param email
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = null;
        pattern = Pattern.compile(EMAIL_NUMBER_PATTERN);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }
}
