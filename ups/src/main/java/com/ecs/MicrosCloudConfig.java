package com.ecs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Configuration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class MicrosCloudConfig extends AbstractCloudConfig {

	private String consumerKey;
	private String consumerSecret;
	private String webRequestUrl;

	public String getURL() {
		if (consumerKey == null) {
			getCredentials();
		}
		return webRequestUrl+"?consumerKey="+consumerKey+"&consumerSecret="+consumerSecret;
		
	}

	private void getCredentials() {
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		if (VCAP_SERVICES != null) {
			try {
				JSONObject obj = new JSONObject(VCAP_SERVICES);
				JSONArray arr = obj.getJSONArray("user-provided");
				JSONObject userProvided = arr.getJSONObject(0);
				JSONObject creds = userProvided.getJSONObject("credentials");
				webRequestUrl = creds.getString("uri");
				consumerKey = creds.getString("consumerKey");
				consumerSecret = creds.getString("consumerSecret");
			} catch (Exception e) {
				throw new CloudException("Micros service credentials not found");
			}

		} else {
			throw new CloudException("Micros service credentials not found");
		}

		/*	System.out.println(consumerKey);
			System.out.println(consumerSecret);
			System.out.println(accessToken);
			System.out.println(accessTokenSecret);*/
	}

}
