/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.pojo;

public class Resource {

	private long id;
	private String name;
	private short failedAttemptsBeforeLock;
	private Long geoFilterId;
	private String geoFilterName;
	private Boolean geoFilterEnabled;
	private Long timeFilterId;
	private String timeFilterName;
	private Boolean timeFilterEnabled;
	private Long creatorId;
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

	public short getFailedAttemptsBeforeLock() {
		return failedAttemptsBeforeLock;
	}

	public void setFailedAttemptsBeforeLock(short failedAttemptsBeforeLock) {
		this.failedAttemptsBeforeLock = failedAttemptsBeforeLock;
	}

	public Long getGeoFilterId() {
		return geoFilterId;
	}

	public void setGeoFilterId(Long geoFilterId) {
		this.geoFilterId = geoFilterId;
	}

	public String getGeoFilterName() {
		return geoFilterName;
	}

	public void setGeoFilterName(String geoFilterName) {
		this.geoFilterName = geoFilterName;
	}

	public Boolean getGeoFilterEnabled() {
		return geoFilterEnabled;
	}

	public void setGeoFilterEnabled(Boolean geoFilterEnabled) {
		this.geoFilterEnabled = geoFilterEnabled;
	}

	public Long getTimeFilterId() {
		return timeFilterId;
	}

	public void setTimeFilterId(Long timeFilterId) {
		this.timeFilterId = timeFilterId;
	}

	public String getTimeFilterName() {
		return timeFilterName;
	}

	public void setTimeFilterName(String timeFilterName) {
		this.timeFilterName = timeFilterName;
	}

	public Boolean getTimeFilterEnabled() {
		return timeFilterEnabled;
	}

	public void setTimeFilterEnabled(Boolean timeFilterEnabled) {
		this.timeFilterEnabled = timeFilterEnabled;
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
		return "Resource [id=" + id + ", name=" + name
				+ ", failedAttemptsBeforeLock=" + failedAttemptsBeforeLock
				+ ", geoFilterId=" + geoFilterId + ", geoFilterName="
				+ geoFilterName + ", geoFilterEnabled=" + geoFilterEnabled
				+ ", timeFilterId=" + timeFilterId + ", timeFilterName="
				+ timeFilterName + ", timeFilterEnabled=" + timeFilterEnabled
				+ ", creatorId=" + creatorId + ", creatorUsername="
				+ creatorUsername + "]";
	}

}