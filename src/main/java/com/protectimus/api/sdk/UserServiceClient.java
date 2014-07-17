package com.protectimus.api.sdk;

import javax.ws.rs.core.MediaType;

import com.protectimus.api.sdk.enums.ResponseFormat;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
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

	public String getUsers(String offset) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users" + getExtension())
				.queryParam("start", offset).get(ClientResponse.class);
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

	public String getUserTokens(String userId, String offset)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("users").path(userId)
				.path("tokens" + getExtension()).queryParam("start", offset)
				.get(ClientResponse.class);
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