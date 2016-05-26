/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.filters;

import com.protectimus.api.sdk.enums.UserBlockType;

/**
 * Created with IntelliJ IDEA.
 * User: bolkimen
 * Date: 11/12/14
 * Time: 12:52 PM
 */
public class UserFilter {

    private String login;
    private String email;
    private String firstName;
    private String secondName;
    private UserBlockType block;
    private String resourceIds;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public UserBlockType getBlock() {
        return block;
    }

    public void setBlock(UserBlockType block) {
        this.block = block;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
