/**
 * Copyright (C) 2013-2014 INSART <vsolo@insart.com>
 *
 * This file is part of Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of INSART
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