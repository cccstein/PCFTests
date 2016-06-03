package com.ecs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@SpringBootApplication
@Controller
@RequestMapping("/")

public class Main 
{
	@Autowired
	private TwitterCloudConfig twitterCloudConfig;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String root() throws TwitterException {
		return getATweet();
	}

	private String getATweet() throws TwitterException {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = twitterCloudConfig.getTwitterInstance();
		Query query = new Query("#haiku");
		QueryResult result = twitter.search(query);
		Status status = result.getTweets().get(0);
		return "@" + status.getUser().getScreenName() + ":" + status.getText();

	}

}
