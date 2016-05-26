/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.pojo;

public class User {

	private long id;
	private String login;
	private String email;
	private String phoneNumber;
	private String firstName;
	private String secondName;
	private boolean apiSupport;
	private boolean hasTokens;
	private Long creatorId;
	private String creatorUsername;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
		this.email = email != null && email.length() == 0 ? null : email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber != null && phoneNumber.length() == 0 ? null
				: phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName != null && firstName.length() == 0 ? null
				: firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName != null && secondName.length() == 0 ? null
				: secondName;
	}

	public boolean isApiSupport() {
		return apiSupport;
	}

	public void setApiSupport(boolean apiSupport) {
		this.apiSupport = apiSupport;
	}

	public boolean isHasTokens() {
		return hasTokens;
	}

	public void setHasTokens(boolean hasTokens) {
		this.hasTokens = hasTokens;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorUsername() {
		return creatorUsername;
	}

	public void setCreatorUsername(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", firstName=" + firstName
				+ ", secondName=" + secondName + ", apiSupport=" + apiSupport
				+ ", hasTokens=" + hasTokens + ", creatorId=" + creatorId
				+ ", creatorUsername=" + creatorUsername + "]";
	}

}