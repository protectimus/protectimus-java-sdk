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

class AuthServiceClient extends AbstractServiceClient {

	@Override
	protected String getServiceName() {
		return "auth-service";
	}

	public AuthServiceClient(String apiUrl, String username, String apiKey,
			ResponseFormat responseFormat, String version)
			throws ProtectimusApiException {
		super(apiUrl, username, apiKey, responseFormat, version);
	}

	public String getBalance() throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("balance" + getExtension())
				.get(ClientResponse.class);
		return checkResponse(response);
	}

	public String prepare(String resourceId, String resourceName,
			String tokenId, String userId, String userLogin)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
		form.add("resourceName", resourceName);
		form.add("tokenId", tokenId);
		form.add("userId", userId);
		form.add("userLogin", userLogin);
		ClientResponse response = webResource.path("prepare" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

    public String checkEnvironment(String resourceId, String resourceName,
                                 String userId, String userLogin,
                                 String jsonEnvironment)
            throws ProtectimusApiException {
        WebResource webResource = getWebResource();
        Form form = new Form();
        form.add("resourceId", resourceId);
        form.add("resourceName", resourceName);
        form.add("userId", userId);
        form.add("userLogin", userLogin);
        form.add("jsonEnvironment", jsonEnvironment);
        ClientResponse response = webResource
                .path("authenticate/check-environment" + getExtension())
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, form);
        return checkResponse(response);
    }

    public String saveEnvironment(String userId, String userLogin,
                                 String jsonEnvironment)
            throws ProtectimusApiException {
        WebResource webResource = getWebResource();
        Form form = new Form();
        form.add("userId", userId);
        form.add("userLogin", userLogin);
        form.add("jsonEnvironment", jsonEnvironment);
        ClientResponse response = webResource
                .path("authenticate/save-environment" + getExtension())
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, form);
        return checkResponse(response);
    }

    public String authenticateToken(String resourceId, String resourceName,
			String tokenId, String otp, String ip)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
		form.add("resourceName", resourceName);
		form.add("tokenId", tokenId);
		form.add("otp", otp);
		form.add("ip", ip);
		ClientResponse response = webResource
				.path("authenticate/token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String authenticateUserPassword(String resourceId,
			String resourceName, String userId, String userLogin,
			String password, String ip) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
		form.add("resourceName", resourceName);
		form.add("userId", userId);
		form.add("userLogin", userLogin);
		form.add("pwd", password);
		form.add("ip", ip);
		ClientResponse response = webResource
				.path("authenticate/user-password" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String authenticateUserToken(String resourceId, String resourceName,
			String userId, String userLogin, String otp, String ip)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
		form.add("resourceName", resourceName);
		form.add("userId", userId);
		form.add("userLogin", userLogin);
		form.add("otp", otp);
		form.add("ip", ip);
		ClientResponse response = webResource
				.path("authenticate/user-token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String authenticateUserPasswordToken(String resourceId,
			String resourceName, String userId, String userLogin, String otp,
			String password, String ip) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("resourceId", resourceId);
		form.add("resourceName", resourceName);
		form.add("userId", userId);
		form.add("userLogin", userLogin);
		form.add("otp", otp);
		form.add("pwd", password);
		form.add("ip", ip);
		ClientResponse response = webResource
				.path("authenticate/user-password-token" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

}