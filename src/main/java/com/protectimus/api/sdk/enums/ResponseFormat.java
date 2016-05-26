/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.enums;

public enum ResponseFormat {

	XML(".xml"), JSON(".json");

	private String extension;

	private ResponseFormat(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

}