package com.protectimus.api.sdk;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Util {
	
	

	public static String toHex(String arg) {
		return String.format("%x",
				new BigInteger(1, arg.getBytes()));
	}
		

	/**
	 * This method uses the JCE to provide the crypto algorithm. HMAC computes a
	 * Hashed Message Authentication Code with the crypto hash algorithm as a
	 * parameter.
	 * 
	 * @param crypto
	 *            the crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
	 * @param keyBytes
	 *            the bytes to use for the HMAC key
	 * @param text
	 *            the message or text to be authenticated.
	 */
	public static byte[] hmac_sha1(String crypto, byte[] keyBytes, byte[] text) {
		try {
			Mac hmac;
			hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}
	
	/**
	 * This method uses the JCE to provide the HMAC-SHA-1 algorithm. HMAC
	 * computes a Hashed Message Authentication Code and in this case SHA1 is
	 * the hash algorithm used.
	 * 
	 * @param keyBytes
	 *            the bytes to use for the HMAC-SHA-1 key
	 * @param text
	 *            the message or text to be authenticated.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             if no provider makes either HmacSHA1 or HMAC-SHA-1 digest
	 *             algorithms available.
	 * @throws InvalidKeyException
	 *             The secret provided was not a valid HMAC-SHA-1 key.
	 * 
	 */

	public static byte[] hmac_sha1(byte[] keyBytes, byte[] text)
			throws NoSuchAlgorithmException, InvalidKeyException {
		try {
			Mac hmacSha1;
			try {
				hmacSha1 = Mac.getInstance("HmacSHA1");
			} catch (NoSuchAlgorithmException nsae) {
				hmacSha1 = Mac.getInstance("HMAC-SHA-1");
			}
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmacSha1.init(macKey);
			return hmacSha1.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}

	
	
	public static byte[] hmac(String crypto, byte[] keyBytes, byte[] text, String algorithm) {
		try {
			Mac hmac;
			hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, algorithm);
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}
	

}
