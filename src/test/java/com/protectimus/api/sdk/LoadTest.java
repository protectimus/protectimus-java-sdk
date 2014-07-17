package com.protectimus.api.sdk;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.RandomStringUtils;

public class LoadTest {

	private static class ClientHolder {

		private String email;
		private String apiKey;

		public ClientHolder(String email, String apiKey) {
			super();
			this.email = email;
			this.apiKey = apiKey;
		}

		public String getEmail() {
			return email;
		}

		public String getApiKey() {
			return apiKey;
		}

	}

	private static final ClientHolder[] array = new ClientHolder[] {
			// new ClientHolder("protektimus@gmail.com",
			// "SFJ6zn37sxqOQnn4XhOIMggdiuycHtjw"),
			new ClientHolder("protektimus1@gmail.com",
					"Scs6ftY2BrqCJjilCvaBOQEtVQuUY7g1"),
			new ClientHolder("protektimus2@gmail.com",
					"mkIukiixBo14TTQefwLrN8R08ahRD7gt"),
			new ClientHolder("protektimus3@gmail.com",
					"2EJW8T0cIUYuq8zcDxvF0mtHlj7XelNb"),
			new ClientHolder("protektimus4@gmail.com",
					"MujUQqR5dJm2yAIiPVe2sVCTImM2voOl"),
			new ClientHolder("protektimus5@gmail.com",
					"b9TGKdoV9rP1dYHkyC7Dk0ma8SQlmOUi"),
			new ClientHolder("protektimus6@gmail.com",
					"jvA4i9VQ3OR8gJUkjiZb0QxuNFWpkt77"),
			new ClientHolder("protektimus7@gmail.com",
					"B0vSpit1TQ4yH7JyettvbMYAl1i25tWY"),
			new ClientHolder("protektimus8@gmail.com",
					"TbFfSVVgp1zdKO9kWZQTJ77PvZedty94"),
			new ClientHolder("protektimus9@gmail.com",
					"qpnLuh3jJP9zsqbOqoMCRHq5FTxykluv"),
			new ClientHolder("protektimus10@gmail.com",
					"yKRDyhFJrQu7lq32Zx4SJTauKqdbpIwF") };
	private static final AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) {
		final int limit = 10;
		count.set(0);
		final double start = System.currentTimeMillis();
		for (int i = 0; i <= limit; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//Thread.sleep(randInt(0, 1000));
						ClientHolder holder = array[randInt(0, 9)];
						ProtectimusApi api = new ProtectimusApi(
								"https://api.protectimus.com/",
								holder.getEmail(), holder.getApiKey(), "v1");
						long userId = api.addUser(
								RandomStringUtils.randomAlphanumeric(14),
								RandomStringUtils.randomAlphanumeric(14)
										+ "@gmail.com", null, null, null, null,
								true);
						System.out.println(userId);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (count.incrementAndGet() == limit) {
						double end = System.currentTimeMillis();
						System.out.println("TIME MILLISECONDS =>> "
								+ (end - start));
						double seconds = (end - start) / 1000;
						System.out.println("TIME SECONDS =>> " + seconds);
						// main(new String[] {});
					}
				}
			}).start();
		}
	}

	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}