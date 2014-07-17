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