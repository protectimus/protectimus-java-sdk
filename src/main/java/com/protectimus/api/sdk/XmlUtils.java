/**
 * Copyright (C) 2013-2014 INSART <vsolo@insart.com>
 *
 * This file is part of Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of INSART
 */
package com.protectimus.api.sdk;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import com.protectimus.api.sdk.pojo.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.protectimus.api.sdk.enums.TokenType;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException.ErrorCode;
import org.xml.sax.SAXException;

class XmlUtils {

	public static final String OK = "OK";
	public static final String FAILURE = "FAILURE";

	private static class XmlObject {

		public XmlObject(Element root, XPath xPath) {
			this.root = root;
			this.xPath = xPath;
		}

		private Element root;
		private XPath xPath;

		public Element getRoot() {
			return root;
		}

		public XPath getXPath() {
			return xPath;
		}
	}

	private static ErrorCode getErrorCode(int code) {
		for (ErrorCode errorCode : ErrorCode.values()) {
			if (errorCode.getCode() == code) {
				return errorCode;
			}
		}
		return null;
	}

	public static XmlObject checkStatus(String input)
			throws ProtectimusApiException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId)
                        throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });
            Document document = builder.parse(new InputSource(new StringReader(
					input)));
			Element root = document.getDocumentElement();

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();

			String status = xPath.evaluate("/responseHolder/status", root);
			if (status.equals(FAILURE)) {
				String message = xPath.evaluate(
						"/responseHolder/error/message", root);
				String developerMessage = xPath.evaluate(
						"/responseHolder/error/developerMessage", root);
				ErrorCode errorCode = getErrorCode(Integer.parseInt(xPath
						.evaluate("/responseHolder/error/code", root)));
				throw new ProtectimusApiException(message, developerMessage,
						errorCode);
			}
			return new XmlObject(root, xPath);
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static BigDecimal parseBalance(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			String balance = xmlObject.getXPath().evaluate(
					"/responseHolder/response/balance", xmlObject.getRoot());
			return new BigDecimal(balance);
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static String parseChallengeString(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			return xmlObject.getXPath().evaluate(
					"/responseHolder/response/challenge", xmlObject.getRoot());
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

    public static String parseAdditionalString(String input)
            throws ProtectimusApiException {
        try {
            XmlObject xmlObject = checkStatus(input);
            return xmlObject.getXPath().evaluate(
                    "/responseHolder/response/additional", xmlObject.getRoot());
        } catch (Exception e) {
            if (e instanceof ProtectimusApiException) {
                throw (ProtectimusApiException) e;
            }
            throw new ProtectimusApiException("Failed to parse response",
                    e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
        }
    }

    public static String parseSecretKey(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			return xmlObject.getXPath().evaluate(
					"/responseHolder/response/key", xmlObject.getRoot());
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

    public static Prepare parsePrepareResult(String input)
            throws ProtectimusApiException {
        try {
            Prepare prepare = new Prepare();
            XmlObject xmlObject = checkStatus(input);
            prepare.setChallenge(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/challenge", xmlObject.getRoot()));
            prepare.setTokenName(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/tokenName", xmlObject.getRoot()));
            prepare.setTokenType(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/tokenType", xmlObject.getRoot()));
            return prepare;
        } catch (Exception e) {
            if (e instanceof ProtectimusApiException) {
                throw (ProtectimusApiException) e;
            }
            throw new ProtectimusApiException("Failed to parse response",
                    e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
        }
    }

    public static SignTransaction parseSignTransaction(String input)
            throws ProtectimusApiException {

        System.out.println("\n\n\n" + input + "\n\n\n");
        try {
            SignTransaction transaction = new SignTransaction();
            XmlObject xmlObject = checkStatus(input);
            transaction.setChallenge(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/challenge", xmlObject.getRoot()));
            transaction.setTokenName(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/tokenName", xmlObject.getRoot()));
            transaction.setTokenType(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/tokenType", xmlObject.getRoot()));
            transaction.setTransactionData(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/transactionData", xmlObject.getRoot()));
            transaction.setTokenId(Long.parseLong(xmlObject.getXPath().evaluate(
                    "/responseHolder/response/id", xmlObject.getRoot())));
            return transaction;
        } catch (Exception e) {
            if (e instanceof ProtectimusApiException) {
                throw (ProtectimusApiException) e;
            }
            throw new ProtectimusApiException("Failed to parse response",
                    e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
        }
    }
    public static boolean parseAuthenticationResult(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			String result = xmlObject.getXPath().evaluate(
					"/responseHolder/response/result", xmlObject.getRoot());
			return Boolean.parseBoolean(result);
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static List<Resource> parseResources(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			Element root = xmlObject.getRoot();
			XPath xPath = xmlObject.getXPath();

			XPathExpression resourcesExp = xPath
					.compile("/responseHolder/response//resource");
			NodeList resources = (NodeList) resourcesExp.evaluate(root,
					XPathConstants.NODESET);

			int resourcesCount = resources.getLength();

			List<Resource> list = new ArrayList<Resource>(resourcesCount);
			for (int i = 0; i < resourcesCount; i++) {
				Resource resource = new Resource();
				resource.setId(Long.parseLong(xPath.evaluate(
						"/responseHolder/response//resource[" + (i + 1)
								+ "]/id", root)));
				resource.setName(xPath.evaluate(
						"/responseHolder/response//resource[" + (i + 1)
								+ "]/name", root));
				resource.setFailedAttemptsBeforeLock(Short.parseShort(xPath
						.evaluate("/responseHolder/response//resource["
								+ (i + 1) + "]/failedAttemptsBeforeLock", root)));
				String geoFilterId = xPath.evaluate(
						"/responseHolder/response//resource[" + (i + 1)
								+ "]/geoFilterId", root);
				if (geoFilterId != null && geoFilterId.length() > 0) {
					resource.setGeoFilterId(Long.valueOf(geoFilterId));
					resource.setGeoFilterName(xPath.evaluate(
							"/responseHolder/response//resource[" + (i + 1)
									+ "]/geoFilterName", root));
					resource.setGeoFilterEnabled(Boolean.valueOf(xPath
							.evaluate("/responseHolder/response//resource["
									+ (i + 1) + "]/geoFilterEnabled", root)));
				}
				String timeFilterId = xPath.evaluate(
						"/responseHolder/response//resource[" + (i + 1)
								+ "]/timeFilterId", root);
				if (timeFilterId != null && timeFilterId.length() > 0) {
					resource.setTimeFilterId(Long.valueOf(timeFilterId));
					resource.setTimeFilterName(xPath.evaluate(
							"/responseHolder/response//resource[" + (i + 1)
									+ "]/timeFilterName", root));
					resource.setTimeFilterEnabled(Boolean.valueOf(xPath
							.evaluate("/responseHolder/response//resource["
									+ (i + 1) + "]/timeFilterEnabled", root)));
				}
				resource.setCreatorId(Long.valueOf(xPath.evaluate(
						"/responseHolder/response//resource[" + (i + 1)
								+ "]/creatorId", root)));
				resource.setCreatorUsername(xPath.evaluate(
						"/responseHolder/response//resource[" + (i + 1)
								+ "]/creatorUsername", root));
				list.add(resource);
			}

			return list;

		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static Resource parseResource(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			Element root = xmlObject.getRoot();
			XPath xPath = xmlObject.getXPath();

			Resource resource = new Resource();

			resource.setId(Long.parseLong(xPath.evaluate(
					"/responseHolder/response/resource/id", root)));
			resource.setName(xPath.evaluate(
					"/responseHolder/response/resource/name", root));
			resource.setFailedAttemptsBeforeLock(Short.parseShort(xPath
					.evaluate(
							"/responseHolder/response/resource/failedAttemptsBeforeLock",
							root)));
			String geoFilterId = xPath.evaluate(
					"/responseHolder/response/resource/geoFilterId", root);
			if (geoFilterId != null && geoFilterId.length() > 0) {
				resource.setGeoFilterId(Long.valueOf(geoFilterId));
				resource.setGeoFilterName(xPath
						.evaluate(
								"/responseHolder/response/resource/geoFilterName",
								root));
				resource.setGeoFilterEnabled(Boolean.valueOf(xPath.evaluate(
						"/responseHolder/response/resource/geoFilterEnabled",
						root)));
			}
			String timeFilterId = xPath.evaluate(
					"/responseHolder/response/resource/timeFilterId", root);
			if (timeFilterId != null && timeFilterId.length() > 0) {
				resource.setTimeFilterId(Long.valueOf(timeFilterId));
				resource.setTimeFilterName(xPath.evaluate(
						"/responseHolder/response/resource/timeFilterName",
						root));
				resource.setTimeFilterEnabled(Boolean.valueOf(xPath.evaluate(
						"/responseHolder/response/resource/timeFilterEnabled",
						root)));
			}
			resource.setCreatorId(Long.valueOf(xPath.evaluate(
					"/responseHolder/response/resource/creatorId", root)));
			resource.setCreatorUsername(xPath.evaluate(
					"/responseHolder/response/resource/creatorUsername", root));

			return resource;

		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static int parseQuantity(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			String quantity = xmlObject.getXPath().evaluate(
					"/responseHolder/response/quantity", xmlObject.getRoot());
			return Integer.parseInt(quantity);
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static long parseId(String input) throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			String id = xmlObject.getXPath().evaluate(
					"/responseHolder/response/id", xmlObject.getRoot());
			return Long.parseLong(id);
		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static List<Token> parseTokens(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			Element root = xmlObject.getRoot();
			XPath xPath = xmlObject.getXPath();

			XPathExpression tokensExp = xPath
					.compile("/responseHolder/response//token");
			NodeList tokens = (NodeList) tokensExp.evaluate(root,
					XPathConstants.NODESET);

			int tokensCount = tokens.getLength();

			List<Token> list = new ArrayList<Token>(tokensCount);
			for (int i = 0; i < tokensCount; i++) {
				Token token = new Token();
				token.setId(Long.parseLong(xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1) + "]/id",
						root)));
				token.setName(xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/name", root));
				token.setType(TokenType.valueOf(xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/type", root)));
				token.setSerialNumber(xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/serialNumber", root));
				token.setEnabled(Boolean.parseBoolean(xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/enabled", root)));
				token.setApiSupport(Boolean.parseBoolean(xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/apiSupport", root)));
				String userId = xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/userId", root);
				if (userId != null && userId.length() > 0) {
					token.setUserId(Long.valueOf(userId));
					token.setUsername(xPath.evaluate(
							"/responseHolder/response//token[" + (i + 1)
									+ "]/username", root));
				}
				String clientStaffId = xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/clientStaffId", root);
				if (clientStaffId != null && clientStaffId.length() > 0) {
					token.setClientStaffId(Long.valueOf(clientStaffId));
					token.setClientStaffUsername(xPath.evaluate(
							"/responseHolder/response//token[" + (i + 1)
									+ "]/clientStaffUsername", root));
				}
				String creatorId = xPath.evaluate(
						"/responseHolder/response//token[" + (i + 1)
								+ "]/creatorId", root);
				if (creatorId != null && creatorId.length() > 0) {
					token.setCreatorId(Long.valueOf(creatorId));
					token.setCreatorUsername(xPath.evaluate(
							"/responseHolder/response//token[" + (i + 1)
									+ "]/creatorUsername", root));
				}
				list.add(token);
			}

			return list;

		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static Token parseToken(String input) throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			Element root = xmlObject.getRoot();
			XPath xPath = xmlObject.getXPath();

			Token token = new Token();
			token.setId(Long.parseLong(xPath.evaluate(
					"/responseHolder/response/token/id", root)));
			token.setName(xPath.evaluate("/responseHolder/response/token/name",
					root));
			token.setType(TokenType.valueOf(xPath.evaluate(
					"/responseHolder/response/token/type", root)));
			token.setSerialNumber(xPath.evaluate(
					"/responseHolder/response/token/serialNumber", root));
			token.setEnabled(Boolean.parseBoolean(xPath.evaluate(
					"/responseHolder/response/token/enabled", root)));
			token.setApiSupport(Boolean.parseBoolean(xPath.evaluate(
					"/responseHolder/response/token/apiSupport", root)));
			String userId = xPath.evaluate(
					"/responseHolder/response/token/userId", root);
			if (userId != null && userId.length() > 0) {
				token.setUserId(Long.valueOf(userId));
				token.setUsername(xPath.evaluate(
						"/responseHolder/response/token/username", root));
			}
			String clientStaffId = xPath.evaluate(
					"/responseHolder/response/token/clientStaffId", root);
			if (clientStaffId != null && clientStaffId.length() > 0) {
				token.setClientStaffId(Long.valueOf(clientStaffId));
				token.setClientStaffUsername(xPath.evaluate(
						"/responseHolder/response/token/clientStaffUsername",
						root));
			}
			String creatorId = xPath.evaluate(
					"/responseHolder/response/token/creatorId", root);
			if (creatorId != null && creatorId.length() > 0) {
				token.setCreatorId(Long.valueOf(creatorId));
				token.setCreatorUsername(xPath.evaluate(
						"/responseHolder/response/token/creatorUsername", root));
			}

			return token;

		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static List<User> parseUsers(String input)
			throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			Element root = xmlObject.getRoot();
			XPath xPath = xmlObject.getXPath();

			XPathExpression usersExp = xPath
					.compile("/responseHolder/response//user");
			NodeList users = (NodeList) usersExp.evaluate(root,
					XPathConstants.NODESET);

			int usersCount = users.getLength();

			List<User> list = new ArrayList<User>(usersCount);
			for (int i = 0; i < usersCount; i++) {
				User user = new User();
				user.setId(Long.parseLong(xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1) + "]/id",
						root)));
				user.setLogin(xPath.evaluate("/responseHolder/response//user["
						+ (i + 1) + "]/login", root));
				user.setEmail(xPath.evaluate("/responseHolder/response//user["
						+ (i + 1) + "]/email", root));
				user.setPhoneNumber(xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1)
								+ "]/phoneNumber", root));
				user.setFirstName(xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1)
								+ "]/firstName", root));
				user.setSecondName(xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1)
								+ "]/secondName", root));
				user.setApiSupport(Boolean.parseBoolean(xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1)
								+ "]/apiSupport", root)));
				user.setHasTokens(Boolean.parseBoolean(xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1)
								+ "]/hasTokens", root)));
				String creatorId = xPath.evaluate(
						"/responseHolder/response//user[" + (i + 1)
								+ "]/creatorId", root);
				if (creatorId != null && creatorId.length() > 0) {
					user.setCreatorId(Long.valueOf(creatorId));
					user.setCreatorUsername(xPath.evaluate(
							"/responseHolder/response//user[" + (i + 1)
									+ "]/creatorUsername", root));
				}
				list.add(user);
			}

			return list;

		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

	public static User parseUser(String input) throws ProtectimusApiException {
		try {
			XmlObject xmlObject = checkStatus(input);
			Element root = xmlObject.getRoot();
			XPath xPath = xmlObject.getXPath();

			User user = new User();
			user.setId(Long.parseLong(xPath.evaluate(
					"/responseHolder/response/user/id", root)));
			user.setLogin(xPath.evaluate("/responseHolder/response/user/login",
					root));
			user.setEmail(xPath.evaluate("/responseHolder/response/user/email",
					root));
			user.setPhoneNumber(xPath.evaluate(
					"/responseHolder/response/user/phoneNumber", root));
			user.setFirstName(xPath.evaluate(
					"/responseHolder/response/user/firstName", root));
			user.setSecondName(xPath.evaluate(
					"/responseHolder/response/user/secondName", root));
			user.setApiSupport(Boolean.parseBoolean(xPath.evaluate(
					"/responseHolder/response/user/apiSupport", root)));
			user.setHasTokens(Boolean.parseBoolean(xPath.evaluate(
					"/responseHolder/response/user/hasTokens", root)));
			String creatorId = xPath.evaluate(
					"/responseHolder/response/user/creatorId", root);
			if (creatorId != null && creatorId.length() > 0) {
				user.setCreatorId(Long.valueOf(creatorId));
				user.setCreatorUsername(xPath.evaluate(
						"/responseHolder/response/user/creatorUsername", root));
			}

			return user;

		} catch (Exception e) {
			if (e instanceof ProtectimusApiException) {
				throw (ProtectimusApiException) e;
			}
			throw new ProtectimusApiException("Failed to parse response",
					e.getMessage(), e, ErrorCode.UNKNOWN_ERROR);
		}
	}

}