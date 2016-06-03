package com.ecs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterCloudConfig extends AbstractCloudConfig {

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;

	@Bean
	public Twitter getTwitterInstance() {
		//if (consumerKey == null) {
			getCredentials();
		//}
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

	private void getCredentials() {
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		if (VCAP_SERVICES != null) {
			JSONObject obj = new JSONObject(VCAP_SERVICES);
			JSONArray arr = obj.getJSONArray("user-provided");
			JSONObject userProvided = arr.getJSONObject(0);
			JSONObject creds = userProvided.getJSONObject("credentials");
			consumerKey = creds.getString("consumerKey");
			consumerSecret = creds.getString("consumerSecret");
			accessToken = creds.getString("accessToken");
			accessTokenSecret = creds.getString("accessTokenSecret");

		} else {
			throw new CloudException("Twitter service not found");
		}
		
	/*	System.out.println(consumerKey);
		System.out.println(consumerSecret);
		System.out.println(accessToken);
		System.out.println(accessTokenSecret);*/
	}

}
