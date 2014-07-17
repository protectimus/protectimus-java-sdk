package com.protectimus.api.sdk.samples;

import java.util.List;

import com.protectimus.api.sdk.ProtectimusApi;
import com.protectimus.api.sdk.enums.TokenType;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.pojo.Token;
import com.protectimus.api.sdk.pojo.User;

public class UsersManagementSample {

	private static final String USERNAME = "";
	private static final String APIKEY = "";
	private static final String API_URL = "https://api.protectimus.com/";
	
	public static void main(String[] args) {
		try {
			ProtectimusApi api = new ProtectimusApi(API_URL, USERNAME, APIKEY,
					"v1");

			System.out.println("CREATING A USER");
			long userId = api.addUser("login", "login@gmail.com", null, null,
					null, null, true);
			System.out.println("ID OF THE CREATED USER =>>> " + userId);

			System.out.println();
			System.out.println("GETTING A USER BY ID");
			User user = api.getUser(userId);
			System.out.println("USER =>>> " + user);

			user.setLogin("loginNew");
			user.setEmail("loginNew@gmail.com");
			user.setPhoneNumber("123456789101");
			user.setApiSupport(false);

			System.out.println();
			System.out.println("UPDATING A USER");
			user = api.editUser(user, null);
			System.out.println("UPDATED USER =>>> " + user);

			System.out.println();
			System.out.println("UPDATING A USER");
			user = api.editUser(user.getId(), "login", "login@gmail.com", null,
					null, null, null, true);
			System.out.println("UPDATED USER =>>> " + user);

			System.out.println();
			System.out.println("UPDATING USERS PASSWORD");
			user = api
					.editUsersPassword(user.getId(), user.getLogin(),
							"a7d48dbb7165641112ad1da9c59a6140",
							"5f4dcc3b5aa765d61d8327deb882cf99", "MD5",
							"PASS{HEX_SALT}");

			System.out.println();
			System.out.println("GETTING A LIST OF USERS");
			// gets a list of users descending (10 records starting from
			// 'offset'
			// parameter)
			List<User> users = api.getUsers(0);
			System.out.println("USERS =>>> " + users);

			System.out.println();
			System.out.println("GETTING A QUANTITY OF USERS");
			int quantity = api.getUsersQuantity();
			System.out.println("USERS QUANTITY =>>> " + quantity);

			System.out.println();
			System.out.println("GETTING A LIST OF TOKENS OF THE USER");
			// gets a list of tokens of the user descending (10 records
			// starting from
			// 'offset'
			// parameter)
			List<Token> tokens = api.getUserTokens(userId, 0);
			System.out.println("TOKENS OF THE USER =>>> " + tokens);

			System.out.println();
			System.out.println("GETTING A QUANTITY OF TOKENS OF THE USER");
			quantity = api.getUserTokensQuantity(userId);
			System.out.println("QUANTITY OF TOKENS OF THE USER =>>> "
					+ quantity);

			System.out.println();
			System.out.println("CREATING A TOKEN");
			long tokenId = api.addSoftwareToken(null, null, TokenType.MAIL,
					"email@gmail.com", "Mail token", null, null, null, null,
					null, null);
			System.out.println("ID OF THE CREATED TOKEN =>>> " + tokenId);

			System.out.println();
			System.out.println("ASSIGNING TOKEN WITH ID = " + tokenId
					+ " TO USER WITH ID = " + userId);
			// assigns token to user
			api.assignUserToken(userId, tokenId);

			System.out.println();
			System.out.println("GETTING A LIST OF TOKENS OF THE USER");
			// gets a list of tokens of the user descending (10 records
			// starting from
			// 'offset'
			// parameter)
			tokens = api.getUserTokens(userId, 0);
			System.out.println("TOKENS OF THE USER =>>> " + tokens);

			System.out.println();
			System.out.println("GETTING A QUANTITY OF TOKENS OF THE USER");
			quantity = api.getUserTokensQuantity(userId);
			System.out.println("QUANTITY OF TOKENS OF THE USER =>>> "
					+ quantity);

			System.out.println();
			System.out.println("UNASSIGNING TOKEN WITH ID = " + tokenId
					+ " FROM USER WITH ID = " + userId);
			// unassigns token to user
			api.unassignUserToken(userId, tokenId);

			System.out.println();
			System.out.println("GETTING A LIST OF TOKENS OF THE USER");
			// gets a list of tokens of the user descending (10 records
			// starting from
			// 'offset'
			// parameter)
			tokens = api.getUserTokens(userId, 0);
			System.out.println("TOKENS OF THE USER =>>> " + tokens);

			System.out.println();
			System.out.println("GETTING A QUANTITY OF TOKENS OF THE USER");
			quantity = api.getUserTokensQuantity(userId);
			System.out.println("QUANTITY OF TOKENS OF THE USER =>>> "
					+ quantity);

			System.out.println();
			System.out.println("DELETING A TOKEN");
			Token token = api.deleteToken(tokenId);
			System.out.println("ID OF THE DELETED TOKEN =>>> " + token.getId());

			System.out.println();
			System.out.println("DELETING A USER");
			user = api.deleteUser(userId);
			System.out.println("ID OF THE DELETED USER =>>> " + user.getId());

		} catch (ProtectimusApiException e) {
			System.err.println(e.getErrorCode() + "\n" + e.getMessage() + "\n"
					+ e.getDeveloperMessage());
			e.printStackTrace();
		}
	}

}