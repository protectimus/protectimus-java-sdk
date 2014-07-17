package com.protectimus.api.sdk.samples;

import java.util.List;

import com.protectimus.api.sdk.ProtectimusApi;
import com.protectimus.api.sdk.enums.TokenType;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.pojo.Resource;
import com.protectimus.api.sdk.pojo.Token;
import com.protectimus.api.sdk.pojo.User;

public class ResourcesManagementSample {

	private static final String USERNAME = "";
	private static final String APIKEY = "";
	private static final String API_URL = "https://api.protectimus.com/";

	public static void main(String[] args) {
		try {
			ProtectimusApi api = new ProtectimusApi(API_URL, USERNAME, APIKEY,
					"v1");

			System.out.println("CREATING A RESOURCE");
			long resourceId = api.addResource("resource", (short) 5);
			System.out.println("ID OF THE CREATED RESOURCE =>>> " + resourceId);

			System.out.println();
			System.out.println("GETTING A RESOURCE BY ID");
			Resource resource = api.getResource(resourceId);
			System.out.println("RESOURCE =>>> " + resource);

			resource.setName("resourceNew");
			resource.setFailedAttemptsBeforeLock((short) 8);

			System.out.println();
			System.out.println("UPDATING A RESOURCE");
			resource = api.editResource(resource);
			System.out.println("UPDATED RESOURCE =>>> " + resource);

			System.out.println();
			System.out.println("UPDATING A RESOURCE");
			resource = api
					.editResource(resource.getId(), "resource", (short) 5);
			System.out.println("UPDATED RESOURCE =>>> " + resource);

			System.out.println();
			System.out.println("GETTING A LIST OF RESOURCES");
			// gets a list of resources descending (10 records starting from
			// 'offset'
			// parameter)
			List<Resource> resources = api.getResources(0);
			System.out.println("RESOURCES =>>> " + resources);

			System.out.println();
			System.out.println("GETTING A QUANTITY OF RESOURCES");
			int quantity = api.getResourcesQuantity();
			System.out.println("RESOURCES QUANTITY =>>> " + quantity);

			System.out.println();
			System.out.println("CREATING A USER");
			long userId = api.addUser("login", null, null, null, null, null,
					true);
			System.out.println("ID OF THE CREATED USER =>>> " + userId);

			System.out.println();
			System.out.println("ASSIGNING USER WITH ID = " + userId
					+ " TO RESOURCE WITH ID = " + resourceId);
			// assigns user to resource
			api.assignUserToResource(resourceId, null, userId, null);

			System.out.println();
			System.out.println("UNASSIGNING USER WITH ID = " + userId
					+ " FROM RESOURCE WITH ID = " + resourceId);
			// unassigns user from resource
			api.unassignUserFromResource(resourceId, null, userId, null);

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
			System.out.println("UNASSIGNING TOKEN WITH ID = " + tokenId
					+ " FROM RESOURCE WITH ID = " + resourceId);
			// unassigns token from resource
			api.unassignTokenFromResource(resourceId, null, tokenId);

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

			System.out.println();
			System.out.println("UNASSIGNING USER WITH ID = " + userId
					+ " AND TOKEN WITH ID = " + tokenId
					+ " TOGETHER FROM RESOURCE WITH ID = " + resourceId);
			// unassigns user and token together from resource
			api.unassignUserAndTokenFromResource(resourceId, null, userId, null, tokenId);

			System.out.println();
			System.out
					.println("ASSIGNING TOKEN WITH ID = "
							+ tokenId
							+ " AND USER, WHICH HAS THIS TOKEN, TOGETHER TO RESOURCE WITH ID = "
							+ resourceId);
			// assigns user and token together to resource (userId does not
			// specified, because token is already assigned to user)
			api.assignTokenWithUserToResource(resourceId, null, tokenId);
			System.out.println();
			System.out
					.println("UNASSIGNING TOKEN WITH ID = "
							+ tokenId
							+ " AND USER, WHICH HAS THIS TOKEN, TOGETHER FROM RESOURCE WITH ID = "
							+ resourceId);
			// unassigns user and token together from resource (userId does not
			// specified, because token is already assigned to user)
			api.unassignTokenWithUserFromResource(resourceId, null, tokenId);

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
			resource = api.deleteResource(resourceId);
			System.out.println("ID OF THE DELETED RESOURCE =>>> "
					+ resource.getId());

		} catch (ProtectimusApiException e) {
			System.err.println(e.getErrorCode() + "\n" + e.getMessage() + "\n"
					+ e.getDeveloperMessage());
			e.printStackTrace();
		}
	}

}