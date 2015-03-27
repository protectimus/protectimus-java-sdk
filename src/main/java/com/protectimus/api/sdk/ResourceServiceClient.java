/**
 * Copyright (C) 2013-2014 INSART <vsolo@insart.com>
 *
 * This file is part of Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of INSART
 */
package com.protectimus.api.sdk;

import javax.ws.rs.core.MediaType;

import com.protectimus.api.sdk.enums.ResponseFormat;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

class ResourceServiceClient extends AbstractServiceClient {

	@Override
	protected String getServiceName() {
		return "resource-service";
	}

	public ResourceServiceClient(String apiUrl, String username,
			String password, ResponseFormat responseFormat, String version)
			throws ProtectimusApiException {
		super(apiUrl, username, password, responseFormat, version);
	}

	public String getResources(String offset, String limit) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource
				.path("resources" + getExtension()).queryParam("start", offset)
                .queryParam("limit", limit).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getResource(String id) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("resources")
				.path(id + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getResourcesQuantity() throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("resources")
				.path("quantity" + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String addResource(String resourceName,
			String failedAttemptsBeforeLock) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceName", resourceName);
		form.add("failedAttemptsBeforeLock", failedAttemptsBeforeLock);
		ClientResponse response = webResource
				.path("resources" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String editResource(String id, String resourceName,
			String failedAttemptsBeforeLock) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceName", resourceName);
		form.add("failedAttemptsBeforeLock", failedAttemptsBeforeLock);
		ClientResponse response = webResource.path("resources")
				.path(id + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.put(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String deleteResource(String id) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("resources")
				.path(id + getExtension()).delete(ClientResponse.class);
		return checkResponse(response);
	}

	public String assignUserToResource(String resourceId, String resourceName,
                                       String userId, String userLogin)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("userId", userId);
        form.add("userLogin", userLogin);
		ClientResponse response = webResource.path("assign")
				.path("user" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String assignTokenToResource(String resourceId, String resourceName, String tokenId)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("assign")
				.path("token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String assignUserAndTokenToResource(String resourceId, String resourceName,
			String userId, String userLogin, String tokenId) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("userId", userId);
        form.add("userLogin", userLogin);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("assign")
				.path("user-token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String assignTokenWithUserToResource(String resourceId, String resourceName,
			String tokenId) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("assign")
				.path("token-with-user" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String unassignUserFromResource(String resourceId, String resourceName, String userId, String userLogin)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("userId", userId);
        form.add("userLogin", userLogin);
		ClientResponse response = webResource.path("unassign")
				.path("user" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String unassignTokenFromResource(String resourceId, String resourceName, String tokenId)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("unassign")
				.path("token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String unassignUserAndTokenFromResource(String resourceId, String resourceName,
			String userId, String userLogin, String tokenId) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("userId", userId);
        form.add("userLogin", userLogin);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("unassign")
				.path("user-token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String unassignTokenWithUserFromResource(String resourceId, String resourceName,
			String tokenId) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("unassign")
				.path("token-with-user" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

}