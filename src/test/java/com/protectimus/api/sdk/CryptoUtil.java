package com.protectimus.api.sdk;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {

	public static String randomHexString() {
		return UUID.randomUUID().toString().replaceAll("-", "")
				.substring(0, 20);
	}

	public static String base32StringToHexString(String base32String) {
		byte[] byteArray = Base32.decode(base32String);
		return String.format("%x", new BigInteger(1, byteArray)).toLowerCase();
	}

	public static String hexStringToBase32String(String hexString) {
		byte[] byteArray = hexStringToByteArray(hexString);
		return Base32.encode(byteArray).toLowerCase();
	}

	public static String byteArrayToHexString(byte[] byteArray) {
		return String.format("%x", new BigInteger(1, byteArray));
	}

	public static byte[] hexStringToByteArray(String hexString) {
		// adding one byte to get the right conversion values starting with "0"
		// can be converted
		byte[] bArray = new BigInteger("10" + hexString, 16).toByteArray();

		// copy all the REAL bytes, not the "first"
		byte[] ret = new byte[bArray.length - 1];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = bArray[i + 1];
		}
		return ret;
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
	public static byte[] hmacSha1(String crypto, byte[] keyBytes, byte[] text) {
		try {
			Mac hmac = Mac.getInstance(crypto);
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
	public static byte[] hmacSha1(byte[] keyBytes, byte[] text)
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

	public static byte[] hmac(String crypto, byte[] keyBytes, byte[] text,
			String algorithm) {
		try {
			Mac hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, algorithm);
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}

	public static String md5(String string) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());
		byte byteData[] = md.digest();
		// convert the byte to hex format
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

}