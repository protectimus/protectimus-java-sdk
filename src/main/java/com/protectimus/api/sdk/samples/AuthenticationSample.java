package com.protectimus.api.sdk.samples;

import java.math.BigDecimal;

import com.protectimus.api.sdk.ProtectimusApi;
import com.protectimus.api.sdk.enums.TokenType;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.pojo.Resource;
import com.protectimus.api.sdk.pojo.Token;
import com.protectimus.api.sdk.pojo.User;

public class AuthenticationSample {

	private static final String USERNAME = "";
	private static final String APIKEY = "";
	private static final String API_URL = "https://api.protectimus.com/";

	public static void main(String[] args) {
		try {
			ProtectimusApi api = new ProtectimusApi(API_URL, USERNAME, APIKEY,
					"v1");

			// IP-address of the end user. Must be specified to perform the
			// validation of geo-filter.
			String ip = "192.168.15.1";

			System.out.println("GETTING A BALANCE");
			BigDecimal balance = api.getBalance();
			System.out.println("BALANCE =>>> " + balance);

			System.out.println();
			System.out.println("CREATING A RESOURCE");
			long resourceId = api.addResource("resource", (short) 10);
			System.out.println("ID OF THE CREATED RESOURCE =>>> " + resourceId);

			String userPassword = "password";
			System.out.println();
			System.out.println("CREATING A USER");
			long userId = api.addUser("login", "login@gmail.com", null,
					userPassword, null, null, true);
			System.out.println("ID OF THE CREATED USER =>>> " + userId);

			System.out.println();
			System.out.println("CREATING A TOKEN");
			long tokenId = api.addSoftwareToken(null, null, TokenType.MAIL,
					"email@gmail.com", "Mail token", null, null, null, null,
					null, null);
			System.out.println("ID OF THE CREATED TOKEN =>>> " + tokenId);

			System.out.println();
			System.out.println("ASSIGNING TOKEN WITH ID = " + tokenId
					+ " TO RESOURCE WITH ID = " + resourceId);
			// assigns token to resource
			api.assignTokenToResource(resourceId, null, tokenId);

			System.out.println();
			System.out.println("PREPARING AUTHENTICATION");
			// In case of use tokens with type such
			// as SMS, MAIL or PROTECTIMUS_ULTRA this method must be called
			// before authentication to send sms for SMS-token or send e-mail
			// for
			// MAIL-token or get challenge string for PROTECTIMUS_ULTRA-token.
			api.prepareAuthentication(resourceId, tokenId, null, null);

			// one-time password
			String otp = "123456";
			// authenticates MAIL-token with one-time password
			System.out.println();
			System.out.println("AUTHENTICATION WITH ONE-TIME PASSWORD");
			boolean authenticationResult = api.authenticateToken(resourceId,
					tokenId, otp, ip);
			System.out.println("AUTHENTICATION RESULT =>>> "
					+ authenticationResult);

			System.out.println();
			System.out.println("ASSIGNING USER WITH ID = " + userId
					+ " TO RESOURCE WITH ID = " + resourceId);
			// assigns user to resource
			api.assignUserToResource(resourceId, null, userId, null);

			// authenticates user with static password
			System.out.println();
			System.out.println("AUTHENTICATION WITH STATIC USER PASSWORD");
			authenticationResult = api.authenticateUserPassword(resourceId,
					userId, null, userPassword, ip);
			System.out.println("AUTHENTICATION RESULT =>>> "
					+ authenticationResult);

			System.out.println();
			System.out.println("ASSIGNING TOKEN WITH ID = " + tokenId
					+ " TO USER WITH ID = " + userId);
			// assigns token to user
			api.assignUserToken(userId, tokenId);

			System.out.println();
			System.out.println("ASSIGNING USER WITH ID = " + userId
					+ " AND TOKEN WITH ID = " + tokenId
					+ " TOGETHER TO RESOURCE WITH ID = " + resourceId);
			// assigns user and token together to resource
			api.assignUserAndTokenToResource(resourceId, null, userId, null, tokenId);

			// authenticates token of user with one-time password
			System.out.println();
			System.out.println("PREPARING AUTHENTICATION");
			api.prepareAuthentication(resourceId, tokenId, null, null);

			System.out.println("AUTHENTICATION WITH ONE-TIME PASSWORD");
			authenticationResult = api.authenticateUserToken(resourceId,
					userId, null, otp, ip);
			System.out.println("AUTHENTICATION RESULT =>>> "
					+ authenticationResult);

			// authenticates token of user with one-time password and user with
			// static password
			System.out.println();
			System.out.println("PREPARING AUTHENTICATION");
			api.prepareAuthentication(resourceId, tokenId, null, null);
			System.out
					.println("AUTHENTICATION WITH ONE-TIME PASSWORD AND STATIC USER PASSWORD");
			authenticationResult = api.authenticateUserPasswordToken(
					resourceId, userId, null, otp, userPassword, ip);
			System.out.println("AUTHENTICATION RESULT =>>> "
					+ authenticationResult);

			System.out.println();
			System.out.println("DELETING A TOKEN");
			Token token = api.deleteToken(tokenId);
			System.out.println("ID OF THE DELETED TOKEN =>>> " + token.getId());

			System.out.println();
			System.out.println("DELETING A USER");
			User user = api.deleteUser(userId);
			System.out.println("ID OF THE DELETED USER =>>> " + user.getId());

			System.out.println();
			System.out.println("DELETING A RESOURCE");
			Resource resource = api.deleteResource(resourceId);
			System.out.println("ID OF THE DELETED RESOURCE =>>> "
					+ resource.getId());

		} catch (ProtectimusApiException e) {
			System.err.println(e.getErrorCode() + "\n" + e.getMessage() + "\n"
					+ e.getDeveloperMessage());
			e.printStackTrace();
		}
	}

}