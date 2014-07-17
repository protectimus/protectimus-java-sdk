package com.protectimus.api.sdk.samples;

import java.util.List;

import com.protectimus.api.sdk.ProtectimusApi;
import com.protectimus.api.sdk.enums.TokenType;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.pojo.Token;

public class TokensManagementSample {

	private static final String USERNAME = "";
	private static final String APIKEY = "";
	private static final String API_URL = "https://api.protectimus.com/";

	public static void main(String[] args) {
		try {
			ProtectimusApi api = new ProtectimusApi(API_URL, USERNAME, APIKEY,
					"v1");

			System.out
					.println("GETTING SECRET KEY FOR GOOGLE AUTHENTICATOR TOKEN");
			String secret = api.getGoogleAuthenticatorSecretKey();
			System.out
					.println("SECRET KEY FOR GOOGLE AUTHENTICATOR TOKEN =>>> "
							+ secret);

			System.out.println();
			System.out
					.println("GETTING SECRET KEY FOR PROTECTIMUS SMART TOKEN");
			secret = api.getProtectimusSmartSecretKey();
			System.out.println("SECRET KEY FOR PROTECTIMUS SMART TOKEN =>>> "
					+ secret);

			System.out.println();
			System.out.println("CREATING A TOKEN");
			long hwTokenId = api.addHardwareToken(null, null,
					TokenType.PROTECTIMUS_ULTRA, "ocratoken", "token name",
					"token secret", null, false, null, null);
			System.out.println("ID OF THE CREATED TOKEN =>>> " + hwTokenId);

			System.out.println();
			System.out.println("CREATING A TOKEN");
			long tokenId = api.addSoftwareToken(null, null, TokenType.MAIL,
					"email@gmail.com", "Mail token", null, null, null, null,
					null, null);
			System.out.println("ID OF THE CREATED TOKEN =>>> " + tokenId);

			// to add hardware token you must own it, because you need to
			// provide one-time password from this token
			/*
			 * tokenId = api.addHardwareToken(null, null, TokenType.PROTECTIMUS,
			 * "797150452", "797150452", null, "220225", true, null, null);
			 * System.out.println(tokenId);
			 */

			System.out.println();
			System.out.println("GETTING A TOKEN BY ID");
			Token token = api.getToken(tokenId);
			System.out.println("TOKEN =>>> " + token);

			token.setName("E-mail token");
			token.setEnabled(false);
			token.setApiSupport(false);

			System.out.println();
			System.out.println("UPDATING A TOKEN");
			token = api.editToken(token);
			System.out.println("UPDATED TOKEN =>>> " + token);

			System.out.println();
			System.out.println("UPDATING A TOKEN");
			token = api.editToken(token.getId(), "Mail token", true, true);
			System.out.println("UPDATED TOKEN =>>> " + token);

			System.out.println();
			System.out.println("GETTING A LIST OF TOKENS");
			// gets a list of tokens descending (10 records starting from
			// 'offset'
			// parameter)
			List<Token> tokens = api.getTokens(0);
			System.out.println("TOKENS =>>> " + tokens);

			System.out.println();
			System.out.println("GETTING A QUANTITY OF TOKENS");
			int quantity = api.getTokensQuantity();
			System.out.println("TOKENS QUANTITY =>>> " + quantity);

			System.out.println();
			System.out.println("UNASSIGNING TOKEN WITH ID = " + tokenId
					+ " FROM USER, WHICH HAS THIS TOKEN");
			// unassigns token from user
			api.unassignToken(tokenId);

			System.out.println();
			System.out.println("DELETING A TOKEN");
			token = api.deleteToken(tokenId);
			System.out.println("ID OF THE DELETED TOKEN =>>> " + token.getId());

		} catch (ProtectimusApiException e) {
			System.err.println(e.getErrorCode() + "\n" + e.getMessage() + "\n"
					+ e.getDeveloperMessage());
			e.printStackTrace();
		}
	}

}