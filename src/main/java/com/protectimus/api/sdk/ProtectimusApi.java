package com.protectimus.api.sdk;

import java.math.BigDecimal;
import java.util.List;

import com.protectimus.api.sdk.enums.OtpKeyType;
import com.protectimus.api.sdk.enums.PinOtpFormat;
import com.protectimus.api.sdk.enums.ResponseFormat;
import com.protectimus.api.sdk.enums.TokenType;
import com.protectimus.api.sdk.enums.UnifyKeyAlgo;
import com.protectimus.api.sdk.enums.UnifyKeyFormat;
import com.protectimus.api.sdk.enums.UnifyTokenType;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException.ErrorCode;
import com.protectimus.api.sdk.pojo.Prepare;
import com.protectimus.api.sdk.pojo.Resource;
import com.protectimus.api.sdk.pojo.Token;
import com.protectimus.api.sdk.pojo.User;

/**
 * 
 * The main class for Protectimus API calls
 */
public class ProtectimusApi {

	private String apiUrl;
	private String username;
	private String apikey;
	private String version;

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
	 * @return list of resources
	 * @throws ProtectimusApiException
	 */
	public List<Resource> getResources(int offset)
			throws ProtectimusApiException {
		ResourceServiceClient resourceServiceClient = new ResourceServiceClient(
				apiUrl, username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseResources(resourceServiceClient
				.getResources(String.valueOf(offset)));
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
	 * @return list of tokens
	 * @throws ProtectimusApiException
	 */
	public List<Token> getTokens(int offset) throws ProtectimusApiException {
		TokenServiceClient tokenServiceClient = new TokenServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseTokens(tokenServiceClient.getTokens(String
				.valueOf(offset)));
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
	public long addHardwareToken(String userId, String userLogin,
			TokenType type, String serialNumber, String name, String secret,
			String otp, boolean isExistedToken, String pin, String pinOtpFormat)
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
		return XmlUtils.parseId(tokenServiceClient.addHardwareToken(
				userId != null ? String.valueOf(userId) : "", userLogin, String
						.valueOf(type), serialNumber, name, secret, otp, String
						.valueOf(isExistedToken), pin,
				pinOtpFormat != null ? String.valueOf(pinOtpFormat) : null));
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
	 * @param token
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
	 * Gets a list of users descending (10 records starting from
	 * <code>offset</code>)
	 * 
	 * @param offset
	 * @return list of users
	 * @throws ProtectimusApiException
	 */
	public List<User> getUsers(int offset) throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseUsers(userServiceClient.getUsers(String
				.valueOf(offset)));
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
	 * @return list of user tokens
	 * @throws ProtectimusApiException
	 */
	public List<Token> getUserTokens(long userId, int offset)
			throws ProtectimusApiException {
		UserServiceClient userServiceClient = new UserServiceClient(apiUrl,
				username, this.apikey, ResponseFormat.XML, version);
		return XmlUtils.parseTokens(userServiceClient.getUserTokens(
				String.valueOf(userId), String.valueOf(offset)));
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
	}

}