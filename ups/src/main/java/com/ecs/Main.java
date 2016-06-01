package com.ecs;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@SpringBootApplication
@Controller
@RequestMapping("/")
@Configuration
public class Main extends AbstractCloudConfig
{

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String root() {
		String value = "";
		for (Object key : cloudProperties().keySet()) {
			value += key.toString() + ":" + cloudProperties().getProperty(key.toString()) + "<br>";
		}

	/*	try {
			return "First #haiku found:<br>"+getATweet();
		} catch (TwitterException e) {
			return "Cannot communicate with twitter";
		}
*/
		return value;
	}

	@Bean
	public Properties cloudProperties() {
		return properties();
	}

	@Bean
	public Twitter getInstance() {
		String consumerKey = cloudProperties().getProperty("cloud.services.twitter.consumerKey");
		String consumerSecret = cloudProperties().getProperty("cloud.services.twitter.consumerSecret");
		String accessToken = cloudProperties().getProperty("cloud.services.twitter.accessToken");
		String accessTokenSecret = cloudProperties().getProperty("cloud.services.twitter.accessTokenSecret");

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

	private String getATweet() throws TwitterException  {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = getInstance();
		Query query = new Query("#haiku");
		QueryResult result = twitter.search(query);
		Status status = result.getTweets().get(0);
		return "@" + status.getUser().getScreenName() + ":" + status.getText();

	}

}
