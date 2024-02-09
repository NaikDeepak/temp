package com.jda.snew.services.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.twitter.Tweet;
import com.jda.snew.twitter.TweetsObject;
import com.jda.snew.twitter.TwitterApplication;

@Path("twitter")
public class Twitter {

	@Path("/tweets")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public TweetsObject getTweets() {
		SNEWCache instance = SNEWCache.getInstance();
		TweetsObject tweetsObj = (TweetsObject) instance.getObject("tweetsListObject");
		try {
			if (tweetsObj == null) {
				List<Tweet> tweetsList = new ArrayList<Tweet>();
				Properties props = (Properties) instance.getObject("properties");
				String appId = props.getProperty("twitterAppId");
				String appSecret = props.getProperty("twitterAppSecretId");
				String appToken = TwitterApplication.fetchApplicationAccessToken(appId, appSecret);
				String hashTag = "#" + props.getProperty("twitterHashTag");
				tweetsList = TwitterApplication.searchTwitter(hashTag, appToken);
				System.out.println("no of tweets:" + tweetsList.size());
				tweetsObj = new TweetsObject(tweetsList);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return tweetsObj;
	}
}
