/**
 * Copyright (C) 2013-2014 INSART <vsolo@insart.com>
 *
 * This file is part of Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of INSART
 */
package com.protectimus.api.sdk.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: bolkimen
 * Date: 6/12/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Prepare {

    private String challenge;
    private String tokenName;
    private String tokenType;

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String toString() {
        return "Prepare[ challenge=" + challenge
                + ", tokenName=" + tokenName
                + ", tokenType=" + tokenType + "]";
    }
}
