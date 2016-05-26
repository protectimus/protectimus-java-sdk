/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk;

import javax.ws.rs.core.MediaType;

import com.protectimus.api.sdk.enums.ResponseFormat;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.filters.TokenFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

class TokenServiceClient extends AbstractServiceClient {

	@Override
	protected String getServiceName() {
		return "token-service";
	}

	public TokenServiceClient(String apiUrl, String username, String password,
			ResponseFormat responseFormat, String version)
			throws ProtectimusApiException {
		super(apiUrl, username, password, responseFormat, version);
	}

	public String getGoogleAuthenticatorSecretKey()
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("secret-key")
				.path("google-authenticator" + getExtension())
				.get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getProtectimusSmartSecretKey()
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("secret-key")
				.path("protectimus-smart" + getExtension())
				.get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getTokens(TokenFilter tokenFilter, String offset, String limit) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
        webResource = webResource.path("tokens" + getExtension())
                .queryParam("start", offset).queryParam("limit", limit);
        if (tokenFilter != null) {
            if (tokenFilter.getTokenName() != null) {
                webResource = webResource.queryParam("tokenName", tokenFilter.getTokenName());
            }
            if (tokenFilter.getTokenType() != null) {
                webResource = webResource.queryParam("tokenType", tokenFilter.getTokenType().name());
            }
            if (tokenFilter.getSerialNumber() != null) {
                webResource = webResource.queryParam("serialNumber", tokenFilter.getSerialNumber());
            }
            if (tokenFilter.getEnabled() != null) {
                webResource = webResource.queryParam("enabled", "" + tokenFilter.getEnabled());
            }
            if (tokenFilter.getBlock() != null) {
                webResource = webResource.queryParam("block", tokenFilter.getBlock().name());
            }
            if (tokenFilter.getUsername() != null) {
                webResource = webResource.queryParam("username", tokenFilter.getUsername());
            }
            if (tokenFilter.getClientStaffUsername() != null) {
                webResource = webResource.queryParam("clientStaffUsername", tokenFilter.getClientStaffUsername());
            }
            if (tokenFilter.getResourceIds() != null) {
                webResource = webResource.queryParam("resourceIds", tokenFilter.getResourceIds());
            }
            if (tokenFilter.getUseBlankNames() != null) {
                webResource = webResource.queryParam("useBlankNames", "" + tokenFilter.getUseBlankNames());
            }
            if (tokenFilter.getClientName() != null) {
                webResource = webResource.queryParam("clientName", tokenFilter.getClientName());
            }
            if (tokenFilter.getUseBlankNames() != null) {
                webResource = webResource.queryParam("useBlankClientName", "" + tokenFilter.getUseBlankNames());
            }

        }
		ClientResponse response = webResource.get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getTokensQuantity() throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("tokens")
				.path("quantity" + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String getSecretKey() throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path(
				"secret-key" + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

    public String addUnifyToken(String userId, String userLogin,
                                String unifyTokenType, String unifyKeyAlgo, String unifyKeyFormat,
                                String serialNumber, String tokenName,
                                String secret, String otp, String otpLength,
                                String pin, String pinOtpFormat,
                                String counter, String challenge) throws ProtectimusApiException {


        WebResource webResource = getWebResource();
        Form form = new Form();
        form.add("userId", userId);
        form.add("userLogin", userLogin);
        form.add("unifyType", unifyTokenType);
        form.add("unifyKeyAlgo", unifyKeyAlgo);
        form.add("unifyKeyFormat", unifyKeyFormat);
        form.add("serial", serialNumber);
        form.add("name", tokenName);
        form.add("secret", secret);
        form.add("otp", otp);
        form.add("otpLength", otpLength);
        form.add("pin", pin);
        form.add("pinOtpFormat", pinOtpFormat);
        form.add("counter", counter);
        form.add("challenge", challenge);
        ClientResponse response = webResource.path("tokens")
                .path("unify" + getExtension())
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, form);
        return checkResponse(response);
    }

    public String addSoftwareToken(String userId, String userLogin,
			String type, String serialNumber, String name, String secret,
			String otp, String otpLength, String keyType, String pin,
			String pinOtpFormat) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("userId", userId);
		form.add("userLogin", userLogin);
		form.add("type", type);
		form.add("serial", serialNumber);
		form.add("name", name);
		form.add("secret", secret);
		form.add("otp", otp);
		form.add("otpLength", otpLength);
		form.add("keyType", keyType);
		form.add("pin", pin);
		form.add("pinOtpFormat", pinOtpFormat);
		ClientResponse response = webResource.path("tokens")
				.path("software" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String addHardwareToken(String userId, String userLogin,
			String type, String serialNumber, String name, String secret,
			String otp, String isExistedToken, String pin, String pinOtpFormat)
			throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("userId", userId);
		form.add("userLogin", userLogin);
		form.add("type", type);
		form.add("serial", serialNumber);
		form.add("name", name);
		form.add("secret", secret);
		form.add("otp", otp);
		form.add("existed", isExistedToken);
		form.add("pin", pin);
		form.add("pinOtpFormat", pinOtpFormat);
		ClientResponse response = webResource.path("tokens")
				.path("hardware" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String getToken(String id) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("tokens")
				.path(id + getExtension()).get(ClientResponse.class);
		return checkResponse(response);
	}

	public String editToken(String id, String name, String enabled,
			String apiSupport) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		form.add("name", name);
		form.add("enabled", enabled);
		form.add("apiSupport", apiSupport);
		ClientResponse response = webResource.path("tokens")
				.path(id + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.put(ClientResponse.class, form);
		return checkResponse(response);
	}

	public String deleteToken(String id) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		ClientResponse response = webResource.path("tokens")
				.path(id + getExtension()).delete(ClientResponse.class);
		return checkResponse(response);
	}

	public String unassignToken(String tokenId) throws ProtectimusApiException {
		WebResource webResource = getWebResource();
		Form form = new Form();
		ClientResponse response = webResource.path("tokens").path(tokenId)
				.path("unassign" + getExtension())
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		return checkResponse(response);
	}

    public String signTransaction(long tokenId, String transactionData, String hash) throws ProtectimusApiException {
        WebResource webResource = getWebResource();
        Form form = new Form();
        form.add("tokenId", tokenId);
        form.add("transactionData", transactionData);
        form.add("hash", hash);
        ClientResponse response = webResource.path("tokens/sign-transaction" + getExtension())
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, form);
        return checkResponse(response);
    }

    public String verifySignedTransaction(long tokenId, String transactionData,
                                          String hash, String otp) throws ProtectimusApiException {
        WebResource webResource = getWebResource();
        Form form = new Form();
        form.add("tokenId", tokenId);
        form.add("transactionData", transactionData);
        form.add("hash", hash);
        form.add("otp", otp);
        ClientResponse response = webResource.path("tokens/verify-signed-transaction" + getExtension())
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, form);
        return checkResponse(response);
    }

}