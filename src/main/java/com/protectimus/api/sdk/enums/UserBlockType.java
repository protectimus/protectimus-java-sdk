/**
 * Copyright (C) 2013-2014 INSART <vsolo@insart.com>
 *
 * This file is part of Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of INSART
 */
package com.protectimus.api.sdk.enums;

/**
 * Created with IntelliJ IDEA.
 * User: bolkimen
 * Date: 11/24/14
 * Time: 1:11 PM
 */
public enum UserBlockType {
    NONE_BLOCKED, BLOCKED_BY_ADMIN, TOO_MANY_LOGIN_FAILED_ATTEMPTS_BLOCKED, TOO_MANY_OTP_FAILED_ATTEMPTS_BLOCKED, TOO_MANY_EMAIL_FAILED_ATTEMPTS_BLOCKED, TOO_MANY_PIN_FAILED_ATTEMPTS_BLOCKED
}
