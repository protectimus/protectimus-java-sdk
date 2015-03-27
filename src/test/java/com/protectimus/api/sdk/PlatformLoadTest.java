package com.protectimus.api.sdk;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.protectimus.api.sdk.enums.OtpKeyType;
import com.protectimus.api.sdk.enums.TokenType;

public class PlatformLoadTest {

	public static final String NL = System.getProperty("line.separator");

	private static class UserTokenHolder {

		private long userId;
		private long tokenId;
		private String secret;
		private long counter;

		public UserTokenHolder(long userId, long tokenId, String secret,
				long counter) {
			super();
			this.userId = userId;
			this.tokenId = tokenId;
			this.secret = secret;
			this.counter = counter;
		}

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public long getTokenId() {
			return tokenId;
		}

		public void setTokenId(long tokenId) {
			this.tokenId = tokenId;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public long getCounter() {
			return counter;
		}

		public void setCounter(long counter) {
			this.counter = counter;
		}

	}

	private static UserTokenHolder[] userTokenArray;
	private static final AtomicInteger GLOBAL = new AtomicInteger(0);
	private static final AtomicInteger COUNT = new AtomicInteger(0);
	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	private static final String API_URL = "http://192.168.15.90:8080/multipass-platform/";
	private static final String FILE_PATH = "/home/yarv/Config/multipass/load_test/platform/users_tokens_insart_test_server.txt";
	private static final Long RESOURCE_ID = 1l;
	private static final int LIMIT = 10;
	private static Integer INDEX = 0;

	public static void main(String[] args) {
		// generateUsersAndTokens();
		System.out.println();
		System.out.println("START =>>> " + new Date());
		System.out.println("START =>>> " + System.currentTimeMillis());
		System.out.println();
		if (GLOBAL.getAndIncrement() != 20) {
			authenticate();
		} else {
			System.out.println();
			System.out.println("END =>>> " + new Date());
			System.out.println("END =>>> " + System.currentTimeMillis());
			System.out.println();
		}
	}

	private static void authenticate() {
		// System.out.println();
		// System.out.println("<<<= MAIN =>>>");
		// System.out.println();
		try {
			fillUserTokenArray();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		COUNT.set(0);
		COUNTER.set(-1);
		final double start = System.currentTimeMillis();
		for (int i = 1; i <= LIMIT; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// Thread.sleep(randInt(0, 1000));
						UserTokenHolder userTokenHolder = userTokenArray[COUNTER
								.incrementAndGet()];

						ProtectimusApi api = new ProtectimusApi(API_URL,
								"veanim@rambler.ru",
								"AqfBCwbavqsCCEdiEn2fYCos17LVKztP", "v1");

						final String otp = getOneTimePasswordHOTP(
								userTokenHolder.getSecret()
										.substring(
												0,
												userTokenHolder.getSecret()
														.length() - 2),
								userTokenHolder.getCounter());

						// System.out.println();
						// System.out.println("OTP =>>> " + otp);
						// System.out.println();

						boolean result = api.authenticateUserToken(RESOURCE_ID,
								userTokenHolder.getUserId(), null, otp, null);
						// System.out.println();
						// System.out.println("RESULT =>>> " + result);
						// System.out.println();
						if (result) {
							userTokenHolder.setCounter(userTokenHolder
									.getCounter() + 1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (COUNT.incrementAndGet() == LIMIT) {
						double end = System.currentTimeMillis();
						System.out.println("TIME MILLISECONDS =>> "
								+ (end - start));
						double seconds = (end - start) / 1000;
						System.out.println("TIME SECONDS =>> " + seconds);
						writeArray();
						main(new String[] {});
					}
				}
			}).start();
		}
	}

	private static void generateUsersAndTokens() {
		userTokenArray = new UserTokenHolder[LIMIT];
		COUNT.set(0);
		final double start = System.currentTimeMillis();
		for (int i = 1; i <= LIMIT; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// Thread.sleep(randInt(0, 1000));
						ProtectimusApi api = new ProtectimusApi(API_URL,
								"veanim@rambler.ru",
								"AqfBCwbavqsCCEdiEn2fYCos17LVKztP", "v1");

						final long userId = api.addUser(
                                SecureRandomStringUtils.randomAlphanumeric(14),
                                SecureRandomStringUtils.randomAlphanumeric(14)
										+ "@gmail.com", null, null, null, null,
								true);
						final String secret = api
								.getProtectimusSmartSecretKey();
						final String otp = getOneTimePasswordHOTP(
								secret.substring(0, secret.length() - 2), 0l);

						final long tokenId = api.addSoftwareToken(userId, null,
								TokenType.PROTECTIMUS_SMART, null, null,
								secret, otp, (short) 6, OtpKeyType.HOTP, null,
								null);
						api.assignTokenWithUserToResource(RESOURCE_ID, null,
								tokenId);

						synchronized (INDEX) {
							userTokenArray[INDEX++] = new UserTokenHolder(
									userId, tokenId, secret, 1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (COUNT.incrementAndGet() == LIMIT) {
						double end = System.currentTimeMillis();
						System.out.println("TIME MILLISECONDS =>> "
								+ (end - start));
						double seconds = (end - start) / 1000;
						System.out.println("TIME SECONDS =>> " + seconds);
						writeArray();
					}
				}

			}).start();
		}
	}

	private static void writeArray() {
		try {
			FileWriter fwstream = new FileWriter(FILE_PATH);
			BufferedWriter out = new BufferedWriter(fwstream);

			for (UserTokenHolder userToken : userTokenArray) {
				if (userToken != null) {
					out.write(userToken.getUserId() + "|"
							+ userToken.getTokenId() + "|"
							+ userToken.getSecret() + "|"
							+ userToken.getCounter() + NL);
				}
			}

			out.close();
			fwstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void fillUserTokenArray() throws IOException {
		FileInputStream fstream = new FileInputStream(FILE_PATH);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;
		ArrayList<UserTokenHolder> list = new ArrayList<UserTokenHolder>();
		while ((strLine = br.readLine()) != null) {
			if (strLine != null && strLine.length() != 0) {
				String array[] = strLine.split("\\|");
				list.add(new UserTokenHolder(Long.parseLong(array[0]), Long
						.parseLong(array[1]), array[2], Long
						.parseLong(array[3])));
			}
		}

		br.close();
		in.close();
		fstream.close();

		userTokenArray = list.toArray(new UserTokenHolder[list.size()]);
	}

	private static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private static void write(String string) throws IOException {
		if (string != null && string.length() != 0) {
            Path file = new File(FILE_PATH).toPath();

            Set<OpenOption> options = new HashSet<OpenOption>();
            options.add(StandardOpenOption.CREATE);
            options.add(StandardOpenOption.WRITE);

            SeekableByteChannel sbc = null;

            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
            sbc = Files.newByteChannel(file, options, attr);

            ByteBuffer bfSrc = ByteBuffer.wrap(string.getBytes());
            sbc.write(bfSrc);
            sbc.close();
		}
	}

	private static String getOneTimePassword(String secret) {
		int x = 30;
		String returnDigits = "6";

		long thisMoment = System.currentTimeMillis() / 1000;
		String currOtp;
		Long currTime;

		String key = CryptoUtil.base32StringToHexString(secret.replaceAll(" ",
				""));

		thisMoment = System.currentTimeMillis() / 1000;
		currTime = thisMoment / x;
		currOtp = TOTP.generateTOTP(key, Long.toHexString(currTime),
				returnDigits);

		return currOtp;
	}

	private static String getOneTimePasswordHOTP(String secret, long counter) {
		String returnDigits = "6";

		String currOtp;

		String key = CryptoUtil.base32StringToHexString(secret.replaceAll(" ",
				""));

		currOtp = TOTP.generateTOTP(key, Long.toHexString(counter),
				returnDigits);

		return currOtp;
	}

}