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
import com.protectimus.api.sdk.filters.UserFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

class UserServiceClient extends AbstractServiceClient {

	@Override
	protected String getServiceName() {
		return "user-service";
	}

	public UserServiceClient(String apiUrl, String username, String password,
			ResponseFormat responseFormat, String version)
			throws ProtectimusApiException {
		super(apiUrl, username, password, responseFormat, version);
	}

	public String getUsers(UserFilter userFilter, String offset, String limit) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
        webResource = webResource.path("users" + getExtension())
                .queryParam("start", offset).queryParam("limit", limit);

        if (userFilter != null) {
            if (userFilter.getLogin() != null) {
                webResource = webResource.queryParam("login", userFilter.getLogin());
            }
            if (userFilter.getEmail() != null) {
                webResource = webResource.queryParam("email", userFilter.getEmail());
            }
            if (userFilter.getFirstName() != null) {
                webResource = webResource.queryParam("firstName", userFilter.getFirstName());
            }
            if (userFilter.getSecondName() != null) {
                webResource = webResource.queryParam("secondName", userFilter.getSecondName());
            }
            if (userFilter.getBlock() != null) {
                webResource = webResource.queryParam("block", userFilter.getBlock().name());
            }
            if (userFilter.getResourceIds() != null) {
                webResource = webResource.queryParam("resourceIds", userFilter.getResourceIds());
            }
        }

		ClientResponse response = webResource.get(ClientResponse.class);
		return checkResponse(response);
	}

	public String addUser(String login, String email, String phoneNumber,
			String password, String firstName, String secondName,
			String apiSupport) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("login", login);
		form.add("email", email);
		form.add("phoneNumber", phoneNumber);
		form.add("password", password);
		form.add("firstName", firstName);
		form.add("secondName", secondName);
		form.add("apiSupport", apiSupport);
		ClientResponse response = webResource.path("users" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String getUser(String id) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users")
				.path(id + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String editUser(String id, String login, String email,
			String phoneNumber, String password, String firstName,
			String secondName, String apiSupport)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("login", login);
		form.add("email", email);
		form.add("phoneNumber", phoneNumber);
		form.add("password", password);
		form.add("firstName", firstName);
		form.add("secondName", secondName);
		form.add("apiSupport", apiSupport);
		ClientResponse response = webResource.path("users")
				.path(id + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.put(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String editUsersPassword(String id, String login,
			String rawPassword, String rawSalt, String encodingType,
			String encodingFormat) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("id", id);
		form.add("login", login);
		form.add("rawPassword", rawPassword);
		form.add("rawSalt", rawSalt);
		form.add("encodingType", encodingType);
		form.add("encodingFormat", encodingFormat);
		ClientResponse response = webResource.path("users")
				.path("password" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String deleteUser(String id) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users")
				.path(id + getExtension()).delete(ClientResponse.class);
		return checkResponse(response);
	}

	public String getUsersQuantity() throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users")
				.path("quantity" + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getUserTokens(String userId, String offset, String limit)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users").path(userId)
				.path("tokens" + getExtension()).queryParam("start", offset)
                .queryParam("limit", limit).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getUserTokensQuantity(String userId)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users").path(userId)
				.path("tokens").path("quantity" + getExtension())
				.get(ClientResponse.class);
		return checkResponse(response);
	}

	public String assignUserToken(String userId, String tokenId)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("userId", userId);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("users").path(userId)
				.path("tokens").path(tokenId).path("assign" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String unassignUserToken(String userId, String tokenId)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("userId", userId);
		form.add("tokenId", tokenId);
		ClientResponse response = webResource.path("users").path(userId)
				.path("tokens").path(tokenId).path("unassign" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

}