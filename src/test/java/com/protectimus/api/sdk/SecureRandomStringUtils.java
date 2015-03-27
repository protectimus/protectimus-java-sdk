package com.protectimus.api.sdk;

import org.apache.commons.lang.RandomStringUtils;

import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: bolkimen
 * Date: 9/3/14
 * Time: 3:58 PM
 */
public class SecureRandomStringUtils extends RandomStringUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String randomAlphabeticSecure(int count)
    {
        return randomSecure(count, true, false);
    }

    public static String randomAlphanumericSecure(int count)
    {
        return randomSecure(count, true, true);
    }

    public static String randomNumericSecure(int count)
    {
        return randomSecure(count, false, true);
    }

    public static String randomSecure(int count, boolean letters, boolean numbers)
    {
        return random(count, 0, 0, letters, numbers, null, SECURE_RANDOM);
    }
}
