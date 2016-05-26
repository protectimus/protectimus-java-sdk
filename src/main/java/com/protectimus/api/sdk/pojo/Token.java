/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.pojo;

import com.protectimus.api.sdk.enums.TokenType;

public class Token {

	private long id;
	private String name;
	private TokenType type;
	private String serialNumber;
	private boolean enabled;
	private boolean apiSupport;
	private Long userId;
	private Long clientStaffId;
	private Long creatorId;
	private String username;
	private String clientStaffUsername;
	private String creatorUsername;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isApiSupport() {
		return apiSupport;
	}

	public void setApiSupport(boolean apiSupport) {
		this.apiSupport = apiSupport;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getClientStaffId() {
		return clientStaffId;
	}

	public void setClientStaffId(Long clientStaffId) {
		this.clientStaffId = clientStaffId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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

	public String getCreatorUsername() {
		return creatorUsername;
	}

	public void setCreatorUsername(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", name=" + name + ", type=" + type
				+ ", serialNumber=" + serialNumber + ", enabled=" + enabled
				+ ", apiSupport=" + apiSupport + ", userId=" + userId
				+ ", clientStaffId=" + clientStaffId + ", creatorId="
				+ creatorId + ", username=" + username
				+ ", clientStaffUsername=" + clientStaffUsername
				+ ", creatorUsername=" + creatorUsername + "]";
	}

}