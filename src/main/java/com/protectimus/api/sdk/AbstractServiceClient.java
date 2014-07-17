package com.protectimus.api.sdk;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.protectimus.api.sdk.enums.ResponseFormat;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException;
import com.protectimus.api.sdk.exceptions.ProtectimusApiException.ErrorCode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

abstract class AbstractServiceClient {

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	private URL apiUrl;
	private String baseUrl;
	private String version;
	private String username;
	private String apiKey;
	private ResponseFormat responseFormat;

	public AbstractServiceClient(String apiUrl, String username, String apiKey,
			ResponseFormat responseFormat, String version)
			throws ProtectimusApiException {
		try {
			this.apiUrl = new URL(apiUrl);
		} catch (MalformedURLException e) {
			throw new ProtectimusApiException("Invalid API URL", "API URL = ["
					+ apiUrl + "] has invalid format", e,
					ErrorCode.INVALID_URL_PATTERN);
		}
		if (username == null) {
			throw new ProtectimusApiException(
					"Authentication is required. Please, specify username.",
					null, ErrorCode.MISSING_PARAMETER);
		}
		this.username = username;
		if (apiKey == null) {
			throw new ProtectimusApiException(
					"Authentication is required. Please, specify apiKey.",
					null, ErrorCode.MISSING_PARAMETER);
		}
		this.apiKey = apiKey;
		this.responseFormat = responseFormat == null ? ResponseFormat.XML
				: responseFormat;
		this.baseUrl = this.apiUrl.getProtocol()
				+ "://"
				+ this.apiUrl.getHost()
				+ (this.apiUrl.getPort() != 80 && this.apiUrl.getPort() > 0 ? ":"
						+ this.apiUrl.getPort()
						: "")
				+ (this.apiUrl.getPath().endsWith("/") ? this.apiUrl.getPath()
						: this.apiUrl.getPath() + "/");
		this.version = version != null && version.length() == 0 ? null
				: version;
	}

	private String getServiceUri() {
		return baseUrl
				+ "api"
				+ (version != null ? "/" + version + "/" + getServiceName()
						: "/" + getServiceName()) + "/";
	}

	private String hmacSHA256(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data.getBytes());
		byte[] bytes = md.digest();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	protected WebResource getWebResource() throws ProtectimusApiException {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd:HH");
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		String apiKeyHash = null;
		try {
			apiKeyHash = hmacSHA256(String.format("%s:%s", apiKey,
					f.format(new Date())));
		} catch (NoSuchAlgorithmException e) {
			throw new ProtectimusApiException(e.getMessage(), e.toString(),
					ErrorCode.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new ProtectimusApiException(e.getMessage(), e.toString(),
					ErrorCode.INTERNAL_SERVER_ERROR);
		}
		Client client = getClient();
		client.addFilter(new HTTPBasicAuthFilter(username, apiKeyHash));
		return client.resource(getServiceUri());
	}

	protected String checkResponse(ClientResponse response)
			throws ProtectimusApiException {
		if (response.getStatus() == 200) {
			return response.getEntity(String.class);
		} else {
			throw new ProtectimusApiException("HTTP Response Status Code: "
					+ response.getStatus(), response.getEntity(String.class),
					ErrorCode.UNKNOWN_ERROR, response.getStatus());
		}
	}

	private Client getClient() throws ProtectimusApiException {
		if (apiUrl.getProtocol().equals("https")) {
			try {
				ClientConfig config = new DefaultClientConfig();
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, new TrustManager[] { new X509TrustManager() {
					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					@Override
					public void checkClientTrusted(X509Certificate[] certs,
							String authType) {
						// do nothing
					}

					@Override
					public void checkServerTrusted(X509Certificate[] certs,
							String authType) {
						// do nothing
					}
				} }, null);
				config.getProperties().put(
						HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
						new HTTPSProperties(new HostnameVerifier() {
							@Override
							public boolean verify(String arg0, SSLSession arg1) {
								return true;
							}
						}, ctx));
				return Client.create(config);
			} catch (Exception e) {
				throw new ProtectimusApiException(
						"Failed to create API client", null, e,
						ErrorCode.UNKNOWN_ERROR);
			}
		} else {
			return Client.create();
		}
	}

	protected String getExtension() {
		return responseFormat.getExtension();
	}

	protected abstract String getServiceName();

}