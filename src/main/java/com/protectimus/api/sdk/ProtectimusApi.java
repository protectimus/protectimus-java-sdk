/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.List;

import com.protectimus.api.sdk.enums.*;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException.ErrorCode;
import com.protectimus.api.sdk.filters.TokenFilter;
import com.protectimus.api.sdk.filters.UserFilter;
import com.protectimus.api.sdk.pojo.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * The main class for Protectimus API calls
 */
public class ProtectimusApi {

	private String apiUrl;
	private String username;
	private String apikey;
	private String version;
    private String additional;

    public ProtectimusApi(String username, String apikey) {
        this.username = username;
        this.apikey = apikey;
        this.version = "v1";
        this.apiUrl = "https://api.protectimus.com";
    }

	public ProtectimusApi(String apiUrl, String username, String apikey) {
		this.apiUrl = apiUrl;
		this.username = username;
		this.apikey = apikey;
		this.version = "v1";
	}

	public ProtectimusApi(String apiUrl, String username, String apikey,
			String version) {
		this.apiUrl = apiUrl;
		this.username = username;
		this.apikey = apikey;
		this.version = version;
	}

    public String getAdditional() {
        return additional;
    }

	/**
	 * 
	 * Gets current balance of the client
	 * 
	 * @return Current balance of the client
	 * @throws ProtectimusApiException
	 */
	public BigDecimal getBalance() throws ProtectimusApiException {
		AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseBalance(authServiceClient.getBalance());
	}

	/**
	 * 
	 * Prepares token for authentication. In case of use tokens with type such
	 * as SMS, MAIL or PROTECTIMUS_ULTRA this method must be called before
	 * authentication to send sms for SMS-token or send e-mail for MAIL-token or
	 * get challenge string for PROTECTIMUS_ULTRA-token.
	 * 
	 * @param resourceId
	 * @param tokenId
	 * @param userId
	 * @param userLogin
	 * @return Challenge string for PROTECTIMUS_ULTRA-token or empty string for
	 *         SMS and MAIL tokens
	 * @throws ProtectimusApiException
	 */
	public Prepare prepareAuthentication(long resourceId, Long tokenId,
			Long userId, String userLogin) throws ProtectimusApiException {
		AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parsePrepareResult(authServiceClient.prepare(String
                .valueOf(resourceId), null,
                tokenId != null ? String.valueOf(tokenId) : "",
                userId != null ? String.valueOf(userId) : "",
                userLogin != null ? String.valueOf(userLogin) : ""));
	}

    /**
     *
     * Performs static environment check for user with id
     * <code>userId</code> or login <code>userLogin</code>, which is assigned to
     * resource with id <code>resourceId</code>.
     *
     * @param resourceId
     * @param userId
     * @param userLogin
     * @param jsonEnvironment
     *            - user environment in JSON format (see documentation)
     * @return match percent for current environment in users list.
     * @throws ProtectimusApiException
     */
    public int checkEnvironment(long resourceId, long userId,
                                            String userLogin, String jsonEnvironment)
            throws ProtectimusApiException {
        AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseQuantity(authServiceClient
                .checkEnvironment(String.valueOf(resourceId), null,
                        String.valueOf(userId), userLogin, jsonEnvironment));
    }

    /**
     *
     * Performs static environment save for user with id
     * <code>userId</code> or login <code>userLogin</code>
     *
     * @param userId
     * @param userLogin
     * @param jsonEnvironment
     *            - user environment in JSON format (see documentation)
     * @return true if current environment was saved for user; false otherwise.
     * @throws ProtectimusApiException
     */
    public boolean saveEnvironment(long userId, String userLogin, String jsonEnvironment)
            throws ProtectimusApiException {
        AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseAuthenticationResult(authServiceClient
                .saveEnvironment(String.valueOf(userId), userLogin, jsonEnvironment));
    }

    /**
	 * 
	 * Performs authentication for token with id <code>tokenId</code>, which is
	 * assigned to resource with id <code>resourceId</code>.
	 * 
	 * @param resourceId
	 * @param tokenId
	 * @param otp
	 *            - one-time password from token
	 * @param ip
	 *            - IP-address of the end user. Must be specified to perform the
	 *            validation of geo-filter.
	 * @return true if authentication was successful; false otherwise.
	 * @throws ProtectimusApiException
	 */
	public boolean authenticateToken(long resourceId, long tokenId, String otp,
			String ip) throws ProtectimusApiException {
		AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseAuthenticationResult(authServiceClient
				.authenticateToken(String.valueOf(resourceId), null,
						String.valueOf(tokenId), otp, ip));
	}

	/**
	 * 
	 * Performs static password authentication for user with id
	 * <code>userId</code> or login <code>userLogin</code>, which is assigned to
	 * resource with id <code>resourceId</code>.
	 * 
	 * @param resourceId
	 * @param userId
	 * @param userLogin
	 * @param password
	 *            - password of the user
	 * @param ip
	 *            - IP-address of the end user. Must be specified to perform the
	 *            validation of geo-filter.
	 * @return true if authentication was successful; false otherwise.
	 * @throws ProtectimusApiException
	 */
	public boolean authenticateUserPassword(long resourceId, long userId,
			String userLogin, String password, String ip)
			throws ProtectimusApiException {
		AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseAuthenticationResult(authServiceClient
				.authenticateUserPassword(String.valueOf(resourceId), null,
						String.valueOf(userId), userLogin, password, ip));
	}

    /**
	 * 
	 * Performs one-time password authentication for user with id
	 * <code>userId</code> or login <code>userLogin</code>, which is assigned
	 * with token to resource with id <code>resourceId</code>.
	 * 
	 * @param resourceId
	 * @param userId
	 * @param userLogin
	 * @param otp
	 *            - one-time password from token
	 * @param ip
	 *            - IP-address of the end user. Must be specified to perform the
	 *            validation of geo-filter.
	 * @return true if authentication was successful; false otherwise.
	 * @throws ProtectimusApiException
	 */
	public boolean authenticateUserToken(long resourceId, Long userId,
			String userLogin, String otp, String ip)
			throws ProtectimusApiException {
		AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseAuthenticationResult(authServiceClient
				.authenticateUserToken(String.valueOf(resourceId), null,
						userId != null ? String.valueOf(userId) : "",
						userLogin, otp, ip));
	}

	/**
	 * 
	 * Performs one-time password and static password authentication for user
	 * with id <code>userId</code> or login <code>userLogin</code>, which is
	 * assigned with token to resource with id <code>resourceId</code>.
	 * 
	 * @param resourceId
	 * @param userId
	 * @param userLogin
	 * @param otp
	 *            - one-time password from token
	 * @param password
	 *            - password of the user
	 * @param ip
	 *            - IP-address of the end user. Must be specified to perform the
	 *            validation of geo-filter.
	 * @return true if authentication was successful; false otherwise.
	 * @throws ProtectimusApiException
	 */
	public boolean authenticateUserPasswordToken(long resourceId, long userId,
			String userLogin, String otp, String password, String ip)
			throws ProtectimusApiException {
		AuthServiceClient authServiceClient = new AuthServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseAuthenticationResult(authServiceClient
				.authenticateUserPasswordToken(String.valueOf(resourceId),
						null, String.valueOf(userId), userLogin, otp, password,
						ip));
	}

	/**
	 * 
	 * Gets a list of resources descending (10 records starting from
	 * <code>offset</code>)
	 * 
	 * @param offset
     * @param limit
     * @return list of resources
	 * @throws ProtectimusApiException
	 */
	public List<Resource> getResources(int offset, int limit)
			throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseResources(resourceServiceClient
				.getResources(String.valueOf(offset), String.valueOf(limit)));
	}

	/**
	 * 
	 * Gets a resource by <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @return resource
	 * @throws ProtectimusApiException
	 */
	public Resource getResource(long resourceId) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseResource(resourceServiceClient.getResource(String
				.valueOf(resourceId)));
	}

	/**
	 * 
	 * Gets quantity of resources
	 * 
	 * @return quantity of resources
	 * @throws ProtectimusApiException
	 */
	public int getResourcesQuantity() throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseQuantity(resourceServiceClient
				.getResourcesQuantity());
	}

	/**
	 * 
	 * Adds a new resource
	 * 
	 * @param resourceName
	 * @param failedAttemptsBeforeLock
	 * @return id of a new resource
	 * @throws ProtectimusApiException
	 */
	public long addResource(String resourceName, short failedAttemptsBeforeLock)
			throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseId(resourceServiceClient.addResource(resourceName,
				String.valueOf(failedAttemptsBeforeLock)));
	}

    /**
     *
     * Adds a new resource
     *
     * @param resourceName
     * @return id of a new resource
     * @throws ProtectimusApiException
     */
    public long addResource(String resourceName)
            throws ProtectimusApiException {
        ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
                apiUrl, username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseId(resourceServiceClient.addResource(resourceName, null));
    }

	/**
	 * Edits an existing resource with <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param failedAttemptsBeforeLock
	 * @return edited resource
	 * @throws ProtectimusApiException
	 */
	public Resource editResource(long resourceId, String resourceName,
			short failedAttemptsBeforeLock) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseResource(resourceServiceClient.editResource(
				String.valueOf(resourceId), resourceName,
				String.valueOf(failedAttemptsBeforeLock)));
	}

	/**
	 * Edits an existing resource
	 * 
	 * @param resource
	 * @return edited resource
	 * @throws ProtectimusApiException
	 */
	public Resource editResource(Resource resource)
			throws ProtectimusApiException {
		return editResource(resource.getId(), resource.getName(),
				resource.getFailedAttemptsBeforeLock());
	}

	/**
	 * 
	 * Deletes an existing resource with <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @return id of deleted resource
	 * @throws ProtectimusApiException
	 */
	public Resource deleteResource(long resourceId)
			throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseResource(resourceServiceClient
				.deleteResource(String.valueOf(resourceId)));
	}

	/**
	 * 
	 * Deletes an existing resource
	 * 
	 * @param resource
	 * @return id of deleted resource
	 * @throws ProtectimusApiException
	 */
	public Resource deleteResource(Resource resource)
			throws ProtectimusApiException {
		return deleteResource(resource.getId());
	}

	/**
	 * 
	 * Assigns user with <code>userId</code> to resource with
	 * <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param userId
	 * @param userLogin
	 * @throws ProtectimusApiException
	 */
	public void assignUserToResource(Long resourceId, String resourceName,
			Long userId, String userLogin) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient.assignUserToResource(
				String.valueOf(resourceId), resourceName,
				String.valueOf(userId), userLogin));
	}

	/**
	 * 
	 * Assigns token with <code>tokenId</code> to resource with
	 * <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void assignTokenToResource(Long resourceId, String resourceName,
			Long tokenId) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient.assignTokenToResource(
				String.valueOf(resourceId), resourceName,
				String.valueOf(tokenId)));
	}



	/**
	 * 
	 * Assigns together user with <code>userId</code> and token with
	 * <code>tokenId</code> to resource with <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param userId
	 * @param userLogin
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void assignUserAndTokenToResource(Long resourceId,
			String resourceName, Long userId, String userLogin, long tokenId)
			throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient
				.assignUserAndTokenToResource(String.valueOf(resourceId),
						resourceName, String.valueOf(userId), userLogin,
						String.valueOf(tokenId)));
	}

	/**
	 * 
	 * Assigns together token with <code>tokenId</code> and user, which has
	 * given token, to resource with <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void assignTokenWithUserToResource(Long resourceId,
			String resourceName, long tokenId) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient
				.assignTokenWithUserToResource(String.valueOf(resourceId),
						resourceName, String.valueOf(tokenId)));
	}

	/**
	 * 
	 * Unassigns user with <code>userId</code> from resource with
	 * <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param userId
	 * @param userLogin
	 * @throws ProtectimusApiException
	 */
	public void unassignUserFromResource(Long resourceId, String resourceName,
			Long userId, String userLogin) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient.unassignUserFromResource(
				String.valueOf(resourceId), resourceName,
				String.valueOf(userId), userLogin));
	}

	/**
	 * 
	 * Unassigns token with <code>tokenId</code> from resource with
	 * <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void unassignTokenFromResource(Long resourceId, String resourceName,
			long tokenId) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient.unassignTokenFromResource(
				String.valueOf(resourceId), resourceName,
				String.valueOf(tokenId)));
	}

	/**
	 * 
	 * Unassigns together user with <code>userId</code> and token with
	 * <code>tokenId</code> from resource with <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param userId
	 * @param userLogin
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void unassignUserAndTokenFromResource(Long resourceId,
			String resourceName, Long userId, String userLogin, long tokenId)
			throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient
				.unassignUserAndTokenFromResource(String.valueOf(resourceId),
						resourceName, String.valueOf(userId), userLogin,
						String.valueOf(tokenId)));
	}

	/**
	 * 
	 * Unassigns together token with <code>tokenId</code> and user, which has
	 * given token, from resource with <code>resourceId</code>
	 * 
	 * @param resourceId
	 * @param resourceName
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void unassignTokenWithUserFromResource(Long resourceId,
			String resourceName, long tokenId) throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(resourceServiceClient
				.unassignTokenWithUserFromResource(String.valueOf(resourceId),
						resourceName, String.valueOf(tokenId)));
	}

	/**
	 * 
	 * Gets secret key for Google Authenticator application
	 * 
	 * @return secret key
	 * @throws ProtectimusApiException
	 */
	public String getGoogleAuthenticatorSecretKey()
			throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseSecretKey(tokenServiceClient
				.getGoogleAuthenticatorSecretKey());
	}

	/**
	 * 
	 * Gets secret key for ProtectimusSmart application
	 * 
	 * @return secret key
	 * @throws ProtectimusApiException
	 */
	public String getProtectimusSmartSecretKey() throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseSecretKey(tokenServiceClient
				.getProtectimusSmartSecretKey());
	}

	/**
	 * 
	 * Gets a list of tokens descending (10 records starting from
	 * <code>offset</code>)
	 * 
	 * @param offset
     * @param limit
     * @return list of tokens
	 * @throws ProtectimusApiException
	 */
	public List<Token> getTokens(TokenFilter tokenFilter, int offset, int limit) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseTokens(tokenServiceClient.getTokens(tokenFilter, String
				.valueOf(offset), String.valueOf(limit)));
	}

	/**
	 * 
	 * Gets a token by <code>tokenId</code>
	 * 
	 * @param tokenId
	 * @return token
	 * @throws ProtectimusApiException
	 */
	public Token getToken(long tokenId) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseToken(tokenServiceClient.getToken(String
				.valueOf(tokenId)));
	}

	/**
	 * 
	 * Gets quantity of tokens
	 * 
	 * @return quantity of tokens
	 * @throws ProtectimusApiException
	 */
	public int getTokensQuantity() throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseQuantity(tokenServiceClient.getTokensQuantity());
	}

	/**
	 * 
	 * Adds unify token
	 * 
	 * @param userId
	 *            - id of the user to whom the token will be assigned
	 * @param userLogin
	 *            - login of the user to whom the token will be assigned
	 * @param unifyType
	 *            - uniry token type
	 * @param unifyKeyAlgo
	 *            - token key algorythm
	 * @param unifyKeyFormat
	 *            - token key algorythm
	 * @param serialNumber
	 *            - token serial number
	 * @param name
	 *            - token name
	 * @param secret
	 *            - token secret key
	 * @param otp
	 *            - one-time password from token
	 * @param otpLength
	 *            - length of the one-time password (6 or 8 digits)
	 * @param pin
	 *            - pin-code (optional)
	 * @param pinOtpFormat
	 *            - usage of a pin-code with one-time password (adding pin-code
	 *            before or after one-time password)
	 * @param counter
	 *            - counter for token
	 * @param challenge
	 *            - challenge for token
	 * @return id of a new token
	 * @throws ProtectimusApiException
	 */
	public long addUnifyToken(Long userId, String userLogin,
			UnifyTokenType unifyType, UnifyKeyAlgo unifyKeyAlgo,
			UnifyKeyFormat unifyKeyFormat, String serialNumber, String name,
			String secret, String otp, Short otpLength, String pin,
			PinOtpFormat pinOtpFormat, String counter, String challenge)
			throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		if (unifyType == null) {
			throw new ProtectimusApiException("Unify token type is requried",
					null, ErrorCode.MISSING_PARAMETER);
		}
		if (unifyKeyAlgo == null) {
			throw new ProtectimusApiException(
					"Unify key algorithm is requried", null,
					ErrorCode.MISSING_PARAMETER);
		}
		if (unifyKeyFormat == null) {
			throw new ProtectimusApiException("Unify key format is requried",
					null, ErrorCode.MISSING_PARAMETER);
		}

		return XmlUtils.parseId(tokenServiceClient.addUnifyToken(
				userId != null ? String.valueOf(userId) : "", userLogin,
				String.valueOf(unifyType), String.valueOf(unifyKeyAlgo),
				String.valueOf(unifyKeyFormat), serialNumber, name, secret,
				otp, otpLength != null ? String.valueOf(otpLength) : "", pin,
				pinOtpFormat != null ? String.valueOf(pinOtpFormat) : null,
				counter, challenge));
	}


    /**
     *
     * Adds token, that will send one-time passwords to specified e-mail.
     * Throw this method created token can be assigned to user.
     *
     * @param userId - id of the user to whom the token will be assigned
     * @param userLogin - login of the user to whom the token will be assigned
     * @param email - the address to which OTP will be send
     * @param name - the name of the created token
     * @param otpLength - length of the one-time password (6 or 8 digits)
     * @param pin - pin-code, that user will have enter with OTP
     * @param pinFormat - where the pin is attached: before or after OTP
     *
     * @return id of the created token
     * @throws ProtectimusApiException
     */
    public long addMailToken(Long userId, String userLogin, String email, String name, Short otpLength, String pin, PinOtpFormat pinFormat) throws ProtectimusApiException {
        if (email == null || email.trim().length() == 0) {
            throw new ProtectimusApiException("Email is required", null, ErrorCode.INVALID_PARAMETER);
        }

        return addSoftwareToken(userId, userLogin, TokenType.MAIL, email, name, "123456", "123456", otpLength, null, pin, pinFormat);
    }

    /**
     *
     * Adds token, that will send one-time passwords to specified e-mail.
     *
     * @param email - the address to which OTP will be send
     * @param name - the name of the created token
     * @param otpLength - length of the one-time password (6 or 8 digits)
     * @param pin - pin-code, that user will have enter with OTP (optional)
     * @param pinFormat - where the pin is attached: before or after OTP
     *
     * @return id of the created token
     * @throws ProtectimusApiException
     */
    public long addMailToken(String email, String name, Short otpLength, String pin, PinOtpFormat pinFormat) throws ProtectimusApiException {
        return addMailToken(null, null, email,name,otpLength, pin, pinFormat);
    }

    /**
     *
     * Adds token, that will send one-time passwords to specified e-mail.
     *
     * @param email - the address to which OTP will be send
     * @param name - the name of the created token
     * @param otpLength - length of the one-time password (6 or 8 digits)
     *
     * @return id of the created token
     * @throws ProtectimusApiException
     */
    public long addMailToken(String email, String name, Short otpLength) throws ProtectimusApiException {
        return addMailToken(null, null, email,name,otpLength, null, null);
    }

    /**
     *
     * Adds token, that will send one-time passwords in SMS to specified phone.
     * Throw this method created token can be assigned to user.
     *
     * @param userId - id of the user to whom the token will be assigned
     * @param userLogin - login of the user to whom the token will be assigned
     * @param phoneNumber - the phone number to which SMS with OTP will be send
     * @param name - the name of the created token
     * @param otpLength - length of the one-time password (6 or 8 digits)
     * @param pin - pin-code, that user will have enter with OTP
     * @param pinFormat - where the pin is attached: before or after OTP
     *
     * @return id of the created token
     * @throws com.protectimus.api.sdk.exceptions.ProtectimusApiException
     */
    public long addSMSToken(Long userId, String userLogin, String phoneNumber, String name, Short otpLength, String pin, PinOtpFormat pinFormat) throws ProtectimusApiException {
        if (phoneNumber == null || phoneNumber.trim().length() == 0) {
            throw new ProtectimusApiException("Phone number is required", null, ErrorCode.INVALID_PARAMETER);
        }

        return addSoftwareToken(userId, userLogin, TokenType.SMS, phoneNumber, name, "123456", "123456", otpLength, null, pin, pinFormat);
    }

    /**
     *
     * Adds token, that will send one-time passwords in SMS to specified phone.
     *
     *  @param phoneNumber - the phone number to which SMS with OTP will be send
     * @param name - the name of the created token
     * @param otpLength - length of the one-time password (6 or 8 digits)
     * @param pin - pin-code, that user will have enter with OTP
     * @param pinFormat - where the pin is attached: before or after OTP
     *
     * @return id of the created token
     * @throws com.protectimus.api.sdk.exceptions.ProtectimusApiException
     */
    public long addSMSToken(String phoneNumber, String name, Short otpLength, String pin, PinOtpFormat pinFormat) throws ProtectimusApiException {
        return addSMSToken(null, null, phoneNumber,name, otpLength, pin, pinFormat);
    }

    /**
     *
     * Adds token, that will send one-time passwords in SMS to specified phone.
     *
     *  @param phoneNumber - the phone number to which SMS with OTP will be send
     * @param name - the name of the created token
     * @param otpLength - length of the one-time password (6 or 8 digits)
     *
     * @return id of the created token
     * @throws com.protectimus.api.sdk.exceptions.ProtectimusApiException
     */
    public long addSMSToken(String phoneNumber, String name, Short otpLength) throws ProtectimusApiException {
        return addSMSToken(null, null, phoneNumber,name, otpLength, null, null);
    }




	/**
	 * 
	 * Adds software token
	 * 
	 * @param userId
	 *            - id of the user to whom the token will be assigned
	 * @param userLogin
	 *            - login of the user to whom the token will be assigned
	 * @param type
	 *            - token type
	 * @param serialNumber
	 *            - token serial number
	 * @param name
	 *            - token name
	 * @param secret
	 *            - token secret key
	 * @param otp
	 *            - one-time password from token
	 * @param otpLength
	 *            - length of the one-time password (6 or 8 digits), parameter
	 *            is required for PROTECTIMUS_SMART token
	 * @param keyType
	 *            - type of key for PROTECTIMUS_SMART token, allowed values
	 *            "HOTP" (counter-based) or "TOTP" (time-based), parameter is
	 *            required for PROTECTIMUS_SMART token
	 * @param pin
	 *            - pin-code (optional)
	 * @param pinOtpFormat
	 *            - usage of a pin-code with one-time password (adding pin-code
	 *            before or after one-time password)
	 * @return id of a new token
	 * @throws ProtectimusApiException
	 */
	public long addSoftwareToken(Long userId, String userLogin, TokenType type,
			String serialNumber, String name, String secret, String otp,
			Short otpLength, OtpKeyType keyType, String pin,
			PinOtpFormat pinOtpFormat) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		if (type == null) {
			throw new ProtectimusApiException("Token type is requried", null,
					ErrorCode.MISSING_PARAMETER);
		}
		if (type.getType() != TokenType.Type.SOFTWARE) {
			throw new ProtectimusApiException(
					"Token of this type is not a software token", null,
					ErrorCode.INVALID_PARAMETER);
		}
        checkIfPinAndPinFormatIsCorrectlySpecified(pin, pinOtpFormat);
        if (type == TokenType.PROTECTIMUS_SMART && keyType == null) {
            throw  new ProtectimusApiException("Key type required for token Protectimus SMART", null, ErrorCode.MISSING_PARAMETER);
        }
		switch (type) {
		case SMS:
		case MAIL:
			otp = "123456";
			secret = otp;
			break;
		default:
			break;
		}
		return XmlUtils.parseId(tokenServiceClient.addSoftwareToken(
				userId != null ? String.valueOf(userId) : "", userLogin,
				String.valueOf(type), serialNumber, name, secret, otp,
				otpLength != null ? String.valueOf(otpLength) : "",
				keyType != null ? String.valueOf(keyType) : null, pin,
				pinOtpFormat != null ? String.valueOf(pinOtpFormat) : null));
	}

    /**
     *
     * Adds software token
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param name
     *            - token name
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param otpLength
     *            - length of the one-time password (6 or 8 digits), parameter
     *            is required for PROTECTIMUS_SMART token
     * @param keyType
     *            - type of key for PROTECTIMUS_SMART token, allowed values
     *            "HOTP" (counter-based) or "TOTP" (time-based), parameter is
     *            required for PROTECTIMUS_SMART token
     * @param pin
     *            - pin-code (optional)
     * @param pinOtpFormat
     *            - usage of a pin-code with one-time password (adding pin-code
     *            before or after one-time password)
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareToken(TokenType type,
                                 String serialNumber, String name, String secret, String otp,
                                 Short otpLength, OtpKeyType keyType, String pin,
                                 PinOtpFormat pinOtpFormat) throws ProtectimusApiException {
       return addSoftwareToken(null, null, type, serialNumber, name, secret, otp, otpLength, keyType, pin, pinOtpFormat);
    }

    /**
     *
     * Adds software token
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param otpLength
     *            - length of the one-time password (6 or 8 digits), parameter
     *            is required for PROTECTIMUS_SMART token
     * @param keyType
     *            - type of key for PROTECTIMUS_SMART token, allowed values
     *            "HOTP" (counter-based) or "TOTP" (time-based), parameter is
     *            required for PROTECTIMUS_SMART token
     * @param pin
     *            - pin-code (optional)
     * @param pinOtpFormat
     *            - usage of a pin-code with one-time password (adding pin-code
     *            before or after one-time password)
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareToken(TokenType type, String serialNumber, String secret, String otp,
                                 Short otpLength, OtpKeyType keyType, String pin,
                                 PinOtpFormat pinOtpFormat) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, otpLength, keyType, pin, pinOtpFormat);
    }

    /**
     *
     * Adds software token
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param otpLength
     *            - length of the one-time password (6 or 8 digits), parameter
     *            is required for PROTECTIMUS_SMART token
     * @param keyType
     *            - type of key for PROTECTIMUS_SMART token, allowed values
     *            "HOTP" (counter-based) or "TOTP" (time-based), parameter is
     *            required for PROTECTIMUS_SMART token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareToken(TokenType type, String serialNumber, String secret, String otp,
                                 Short otpLength, OtpKeyType keyType) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, otpLength, keyType, null, null);
    }

    /**
     *
     * Adds software time-based token (keyType parameter is TOTP)
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param otpLength
     *            - length of the one-time password (6 or 8 digits), parameter
     *            is required for PROTECTIMUS_SMART token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareTimeBasedToken(TokenType type, String serialNumber, String secret, String otp,
                                 Short otpLength) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, otpLength, OtpKeyType.TOTP, null, null);
    }

    /**
     *
     * Adds software counter-based token (keyType parameter is HOTP)
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param otpLength
     *            - length of the one-time password (6 or 8 digits), parameter
     *            is required for PROTECTIMUS_SMART token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareCounterBasedToken(TokenType type, String serialNumber, String secret, String otp,
                                     Short otpLength) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, otpLength, OtpKeyType.HOTP, null, null);
    }


    /**
     *
     * Adds software time-based token (keyType parameter is TOTP), that generate 6-digit one-time password.
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareSixDigitTimeBasedToken(TokenType type, String serialNumber, String secret, String otp) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, (short) 6, OtpKeyType.TOTP, null, null);
    }

    /**
     *
     * Adds software time-based token (keyType parameter is TOTP), that generate 8-digit one-time password.
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareEightDigitTimeBasedToken(TokenType type, String serialNumber, String secret, String otp) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, (short) 8, OtpKeyType.TOTP, null, null);
    }

    /**
     *
     * Adds software counter-based token (keyType parameter is HOTP), that generate 8-digit one-time password.
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareEightDigitCounterBasedToken(TokenType type, String serialNumber, String secret, String otp) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, (short) 8, OtpKeyType.HOTP, null, null);
    }

    /**
     *
     * Adds software counter-based token (keyType parameter is HOTP), that generate 6-digit one-time password.
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addSoftwareSixDigitCounterBasedToken(TokenType type, String serialNumber, String secret, String otp) throws ProtectimusApiException {
        return addSoftwareToken(null, null, type, serialNumber, null, secret, otp, (short) 6, OtpKeyType.HOTP, null, null);
    }



	/**
	 * 
	 * Adds hardware token
	 * 
	 * @param userId
	 *            - id of the user to whom the token will be assigned
	 * @param userLogin
	 *            - login of the user to whom the token will be assigned
	 * @param type
	 *            - token type
	 * @param serialNumber
	 *            - token serial number
	 * @param name
	 *            - token name
	 * @param secret
	 *            - token secret key
	 * @param otp
	 *            - one-time password from token
	 * @param isExistedToken
	 *            - false indicates that you are adding your own token, true
	 *            indicates that you are adding token, which is provided by
	 *            Protectimus
	 * @param pin
	 *            - pin-code (optional)
	 * @param pinOtpFormat
	 *            - usage of a pin-code with one-time password (adding pin-code
	 *            before or after one-time password)
	 * @return id of a new token
	 * @throws ProtectimusApiException
	 */
	public long addHardwareToken(Long userId, String userLogin,
			TokenType type, String serialNumber, String name, String secret,
			String otp, boolean isExistedToken, String pin, PinOtpFormat pinOtpFormat)
			throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		if (type == null) {
			throw new ProtectimusApiException("Token type is requried", null,
					ErrorCode.MISSING_PARAMETER);
		}
		if (type.getType() != TokenType.Type.HARDWARE) {
			throw new ProtectimusApiException(
					"Token of this type is not a hardware token", null,
					ErrorCode.INVALID_PARAMETER);
		}
        checkIfPinAndPinFormatIsCorrectlySpecified(pin, pinOtpFormat);
        return XmlUtils.parseId(tokenServiceClient.addHardwareToken(
				userId != null ? String.valueOf(userId) : "", userLogin, String
						.valueOf(type), serialNumber, name, secret, otp, String
						.valueOf(isExistedToken), pin,
				pinOtpFormat != null ? String.valueOf(pinOtpFormat) : null));
	}

    /**
     *
     * Adds hardware token
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param name
     *            - token name
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param isExistedToken
     *            - false indicates that you are adding your own token, true
     *            indicates that you are adding token, which is provided by
     *            Protectimus
     * @param pin
     *            - pin-code (optional)
     * @param pinOtpFormat
     *            - usage of a pin-code with one-time password (adding pin-code
     *            before or after one-time password)
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareToken(TokenType type, String serialNumber, String name, String secret,
                                 String otp, boolean isExistedToken, String pin, PinOtpFormat pinOtpFormat)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, name,secret,otp,isExistedToken,pin,pinOtpFormat);
    }

    /**
     *
     * Adds hardware token
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param isExistedToken
     *            - false indicates that you are adding your own token, true
     *            indicates that you are adding token, which is provided by
     *            Protectimus
     * @param pin
     *            - pin-code (optional)
     * @param pinOtpFormat
     *            - usage of a pin-code with one-time password (adding pin-code
     *            before or after one-time password)
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareToken(TokenType type, String serialNumber, String secret, String otp, boolean isExistedToken, String pin, PinOtpFormat pinOtpFormat)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, null, secret,otp,isExistedToken,pin,pinOtpFormat);
    }

    /**
     *
     * Adds hardware token, that wasn't offered from Protectimus (parameter "existed" is false).
     *
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @param pin
     *            - pin-code (optional)
     * @param pinOtpFormat
     *            - usage of a pin-code with one-time password (adding pin-code
     *            before or after one-time password)
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareToken(TokenType type, String serialNumber, String secret, String otp, String pin, PinOtpFormat pinOtpFormat)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, null, secret,otp,false,pin,pinOtpFormat);
    }

    /**
     *
     * Adds hardware token, that wasn't offered from Protectimus (parameter "existed" is false).
     *
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareToken(TokenType type, String serialNumber, String secret, String otp)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, null, secret,otp, false, null, null);
    }

    /**
     *
     * Adds hardware token, that wasn't offered from Protectimus (parameter "existed" is false).
     *
     *
     * @param type
     *            - token type
     * @param name - the name of the created token
     * @param serialNumber
     *            - token serial number
     * @param secret
     *            - token secret key
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareToken(TokenType type, String name, String serialNumber, String secret, String otp)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, name, secret,otp, false, null, null);
    }

    /**
     *
     * Adds hardware token, that was offered from Protectimus (parameter "existed" is true).
     *
     * @param type
     *            - token type
     * @param name - the name of the created token
     * @param serialNumber
     *            - token serial number
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareProtectimusToken(TokenType type, String name, String serialNumber, String otp)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, name, null, otp, true, null, null);
    }

    /**
     *
     * Adds hardware token, that was offered from Protectimus (parameter "existed" is true).
     *
     * @param type
     *            - token type
     * @param serialNumber
     *            - token serial number
     * @param otp
     *            - one-time password from token
     * @return id of a new token
     * @throws ProtectimusApiException
     */
    public long addHardwareProtectimusToken(TokenType type, String serialNumber, String otp)
            throws ProtectimusApiException {
        return addHardwareToken(null, null, type, serialNumber, null, null, otp, true, null, null);
    }

	/**
	 * 
	 * Edits an existing token with <code>tokenId</code>
	 * 
	 * @param tokenId
	 * @param name
	 * @param enabled
	 * @param apiSupport
	 * @return edited token
	 * @throws ProtectimusApiException
	 */
	public Token editToken(long tokenId, String name, boolean enabled,
			boolean apiSupport) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseToken(tokenServiceClient.editToken(
				String.valueOf(tokenId), name, String.valueOf(enabled),
				String.valueOf(apiSupport)));
	}

	/**
	 * 
	 * Edits an existing token
	 * 
	 * @param token - token to edit
	 * @return edited token
	 * @throws ProtectimusApiException
	 */
	public Token editToken(Token token) throws ProtectimusApiException {
		return editToken(token.getId(), token.getName(), token.isEnabled(),
				token.isApiSupport());
	}

	/**
	 * 
	 * Deletes an existing token with <code>tokenId</code>
	 * 
	 * @param tokenId
	 * @return id of deleted token
	 * @throws ProtectimusApiException
	 */
	public Token deleteToken(long tokenId) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseToken(tokenServiceClient.deleteToken(String
				.valueOf(tokenId)));
	}

	/**
	 * 
	 * Deletes an existing token
	 * 
	 * @param token
	 * @return id of deleted token
	 * @throws ProtectimusApiException
	 */
	public Token deleteToken(Token token) throws ProtectimusApiException {
		return deleteToken(token.getId());
	}

	/**
	 * 
	 * Unassigns token with <code>tokenId</code> from user
	 * 
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void unassignToken(long tokenId) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(tokenServiceClient.unassignToken(String
				.valueOf(tokenId)));
	}

	/**
	 * 
	 * Unassigns token from user
	 * 
	 * @param token
	 * @throws ProtectimusApiException
	 */
	public void unassignToken(Token token) throws ProtectimusApiException {
		unassignToken(token.getId());
	}

    /**
     *
     * Allows to generate OTP based on some user's specific data, f.e. on transaction id, amount, receiver and so on. <br/>
     * After call of this method you will receive passed data back and special challenge, based on this data.<br/>
     * User have to check those data and use the chellange for OTP generating if data is ok.
     *
     * @param tokenId - id of the token, with which transaction must be signed.
     * @param transactionData - transaction specific data, that must be used in OTP generation.
     *                        The data must be presented in a key-value format, splitted with the pipe symbol, f.e.: "key1=value1|key2=value2..keyN=valueN"
     * @throws ProtectimusApiException
     */
    public SignTransaction signTransaction(long tokenId, String transactionData) throws ProtectimusApiException {
        TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseSignTransaction(tokenServiceClient.signTransaction(tokenId, transactionData, getMacSha256Hash(transactionData)));
    }

    private String getMacSha256Hash(String transactionData) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec macKey = new SecretKeySpec(apikey.getBytes(), "RAW");
            hmac.init(macKey);
            return byteArrayToHexString(hmac.doFinal(transactionData.getBytes()));
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     *
     * Checks OTP, that was generated with usage of {@link com.protectimus.api.sdk.ProtectimusApi#signTransaction(long, String)} method
     * on the base of transaction-specific data.
     *
     * @param tokenId
     * @param transactionData
     * @param otp
     * @throws ProtectimusApiException
     */
    public boolean verifySignedTransaction(long tokenId, String transactionData, String otp) throws ProtectimusApiException {
        TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseAuthenticationResult(
                tokenServiceClient.verifySignedTransaction(tokenId, transactionData, getMacSha256Hash(transactionData), otp));
    }

	/**
	 * 
	 * Gets a list of users descending (10 records starting from
	 * <code>offset</code>)
	 * 
	 * @param offset
     * @param limit
     * @return list of users
	 * @throws ProtectimusApiException
	 */
	public List<User> getUsers(UserFilter userFilter, int offset, int limit) throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseUsers(userServiceClient.getUsers(userFilter, String
				.valueOf(offset), String.valueOf(limit)));
	}

	/**
	 * 
	 * Gets a user by <code>userId</code>
	 * 
	 * @param userId
	 * @return user
	 * @throws ProtectimusApiException
	 */
	public User getUser(long userId) throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseUser(userServiceClient.getUser(String
				.valueOf(userId)));
	}

	/**
	 * 
	 * Gets quantity of users
	 * 
	 * @return quantity of users
	 * @throws ProtectimusApiException
	 */
	public int getUsersQuantity() throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseQuantity(userServiceClient.getUsersQuantity());
	}

	/**
	 * 
	 * Adds a new user
	 * 
	 * @param login
	 * @param email
	 * @param phoneNumber
	 * @param password
	 * @param firstName
	 * @param secondName
	 * @param apiSupport
	 * @return id of a new user
	 * @throws ProtectimusApiException
	 */
	public long addUser(String login, String email, String phoneNumber,
			String password, String firstName, String secondName,
			boolean apiSupport) throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseId(userServiceClient.addUser(login, email,
				phoneNumber, password, firstName, secondName,
				String.valueOf(apiSupport)));
	}

    /**
     *
     * Adds a new user
     *
     * @param login
     * @param phoneNumber
     * @param password
     * @param firstName
     * @param secondName
     * @param apiSupport
     * @return id of a new user
     * @throws ProtectimusApiException
     */
    public long addUser(String login, String phoneNumber,
                        String password, String firstName, String secondName,
                        boolean apiSupport) throws ProtectimusApiException {
        UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseId(userServiceClient.addUser(login, null,
                phoneNumber, password, firstName, secondName,
                String.valueOf(apiSupport)));
    }

    /**
     *
     * Adds a new user
     *
     * @param login
     * @param password
     * @param firstName
     * @param secondName
     * @param apiSupport
     * @return id of a new user
     * @throws ProtectimusApiException
     */
    public long addUser(String login, String password, String firstName, String secondName,
                        boolean apiSupport) throws ProtectimusApiException {
        UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseId(userServiceClient.addUser(login, null,
                null, password, firstName, secondName,
                String.valueOf(apiSupport)));
    }

    /**
     *
     * Adds a new user
     *
     * @param login
     * @param firstName
     * @param secondName
     * @param apiSupport
     * @return id of a new user
     * @throws ProtectimusApiException
     */
    public long addUser(String login, String firstName, String secondName,
                        boolean apiSupport) throws ProtectimusApiException {
        UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseId(userServiceClient.addUser(login, null,
                null, null, firstName, secondName,
                String.valueOf(apiSupport)));
    }

    /**
     *
     * Adds a new user
     *
     * @param login
     * @param apiSupport
     * @return id of a new user
     * @throws ProtectimusApiException
     */
    public long addUser(String login, boolean apiSupport) throws ProtectimusApiException {
        UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseId(userServiceClient.addUser(login, null,
                null, null, null, null,
                String.valueOf(apiSupport)));
    }

    /**
     *
     * Adds a new user.
     *
     * @param login
     * @return id of a new user
     * @throws ProtectimusApiException
     */
    public long addUser(String login) throws ProtectimusApiException {
        UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
                username, this.apikey, ResponseFormat.XML, version);
        return XmlUtils.parseId(userServiceClient.addUser(login, null,
                null, null, null, null, String.valueOf(true)));    }



	/**
	 * 
	 * Edits an existing user with <code>userId</code>
	 * 
	 * @param userId
	 * @param login
	 * @param email
	 * @param phoneNumber
	 * @param password
	 * @param firstName
	 * @param secondName
	 * @param apiSupport
	 * @return edited user
	 * @throws ProtectimusApiException
	 */
	public User editUser(long userId, String login, String email,
			String phoneNumber, String password, String firstName,
			String secondName, boolean apiSupport)
			throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseUser(userServiceClient.editUser(
				String.valueOf(userId), login, email, phoneNumber, password,
				firstName, secondName, String.valueOf(apiSupport)));
	}

	/**
	 * 
	 * Change raw users password with <code>userId</code>
	 * 
	 * @param userId
	 * @param rawPassword
	 * @param rawSalt
	 * @param encodingType
	 *            - PLAIN, MD5, SHA, SHA256
	 * @param encodingFormat
	 * @return edited user
	 * @throws ProtectimusApiException
	 */
	public User editUsersPassword(long userId, String userLogin,
			String rawPassword, String rawSalt, String encodingType,
			String encodingFormat) throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseUser(userServiceClient.editUsersPassword(
				String.valueOf(userId), userLogin, rawPassword, rawSalt,
				encodingType, encodingFormat));
	}

	/**
	 * 
	 * Edits an existing user
	 * 
	 * @param user
	 * @return edited user
	 * @throws ProtectimusApiException
	 */
	public User editUser(User user, String password)
			throws ProtectimusApiException {
		return editUser(user.getId(), user.getLogin(), user.getEmail(),
				user.getPhoneNumber(), password, user.getFirstName(),
				user.getSecondName(), user.isApiSupport());
	}

	/**
	 * 
	 * Deletes an existing user with <code>userId</code>
	 * 
	 * @param userId
	 * @return id of deleted user
	 * @throws ProtectimusApiException
	 */
	public User deleteUser(long userId) throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseUser(userServiceClient.deleteUser(String
				.valueOf(userId)));
	}

	/**
	 * 
	 * Deletes an existing user
	 * 
	 * @param user
	 * @return id of deleted user
	 * @throws ProtectimusApiException
	 */
	public User deleteUser(User user) throws ProtectimusApiException {
		return deleteUser(user.getId());
	}

	/**
	 * 
	 * Gets a list of user tokens descending by <code>userId</code> (10 records
	 * starting from <code>offset</code>)
	 * 
	 * @param userId
	 * @param offset
     * @param limit
     * @return list of user tokens
	 * @throws ProtectimusApiException
	 */
	public List<Token> getUserTokens(long userId, int offset, int limit)
			throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseTokens(userServiceClient.getUserTokens(
				String.valueOf(userId), String.valueOf(offset), String.valueOf(limit)));
	}

	/**
	 * 
	 * Gets quantity of user tokens by <code>userId</code>
	 * 
	 * @param userId
	 * @return quantity of users
	 * @throws ProtectimusApiException
	 */
	public int getUserTokensQuantity(long userId)
			throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseQuantity(userServiceClient
				.getUserTokensQuantity(String.valueOf(userId)));
	}

	/**
	 * 
	 * Assigns token with <code>tokenId</code> to user with <code>userId</code>
	 * 
	 * @param userId
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void assignUserToken(long userId, long tokenId)
			throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(userServiceClient.assignUserToken(
				String.valueOf(userId), String.valueOf(tokenId)));
	}

	/**
	 * 
	 * Unassigns token with <code>tokenId</code> from user with
	 * <code>userId</code>
	 * 
	 * @param userId
	 * @param tokenId
	 * @throws ProtectimusApiException
	 */
	public void unassignUserToken(long userId, long tokenId)
			throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		XmlUtils.checkStatus(userServiceClient.unassignUserToken(
				String.valueOf(userId), String.valueOf(tokenId)));
	}

	public static void main(String[] args) {
        StringBuilder info = new StringBuilder();
        info.append("Hi friend! This is a main class of Protectimus Java SDK, all requests to our API can be done though it.");
        info.append("Javadoc for methods of this class is brief enough to stay relevant. You can find out detailed documentation and integration instructions at our site: www.protectimus.com or contact our support team");
        info.append("We hope you'll enjoy our solution!");
    }

    private void checkIfPinAndPinFormatIsCorrectlySpecified(String pin, PinOtpFormat pinFormat) throws ProtectimusApiException {
        if (pin == null ^ pinFormat == null) {
            throw new ProtectimusApiException("You have to specify both pin and pinOtpFormat to add PIN to this token", null, ErrorCode.MISSING_PARAMETER);
        }
    }

}