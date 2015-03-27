/**
 * Copyright (C) 2013-2014 INSART <vsolo@insart.com>
 *
 * This file is part of Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of INSART
 */
package com.protectimus.api.sdk.filters;

import com.protectimus.api.sdk.enums.TokenBlockType;
import com.protectimus.api.sdk.enums.TokenType;

/**
 * Created with IntelliJ IDEA.
 * User: bolkimen
 * Date: 11/24/14
 * Time: 3:17 PM
 */
public class TokenFilter {
    private String tokenName;
    private TokenType tokenType;
    private String serialNumber;
    private Boolean enabled;
    private TokenBlockType block;
    private String username;
    private String clientStaffUsername;
    private String resourceIds;
    private Boolean useBlankNames;
    private String clientName;
    private Boolean useBlankClientName;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public TokenBlockType getBlock() {
        return block;
    }

    public void setBlock(TokenBlockType block) {
        this.block = block;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientStaffUsername() {
        return clientStaffUsername;
    }

    public void setClientStaffUsername(String clientStaffUsername) {
        this.clientStaffUsername = clientStaffUsername;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Boolean getUseBlankNames() {
        return useBlankNames;
    }

    public void setUseBlankNames(Boolean useBlankNames) {
        this.useBlankNames = useBlankNames;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Boolean getUseBlankClientName() {
        return useBlankClientName;
    }

    public void setUseBlankClientName(Boolean useBlankClientName) {
        this.useBlankClientName = useBlankClientName;
    }
}
