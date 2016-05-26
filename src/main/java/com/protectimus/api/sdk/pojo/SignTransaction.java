/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.pojo;

/**
 * Created by shokotko on 1/30/15.
 */
public class SignTransaction{

    private String challenge;
    private String transactionData;
    private String tokenName;
    private long tokenId;
    private String tokenType;

    public SignTransaction() {
    }

    public SignTransaction(String ocraQuestion, String transactionData, String tokenName, long tokenId, String tokenType) {
        this.challenge = ocraQuestion;
        this.transactionData = transactionData;
        this.tokenName = tokenName;
        this.tokenId = tokenId;
        this.tokenType = tokenType;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getTransactionData() {
        return transactionData;
    }

    public String getTokenName() {
        return tokenName;
    }

    public long getTokenId() {
        return tokenId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
