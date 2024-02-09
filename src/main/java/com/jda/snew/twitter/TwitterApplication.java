package com.jda.snew.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.web.client.RestTemplate;

public class TwitterApplication {

    public static List<Tweet> searchTwitter(String query, String appToken) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        // Using application token for search so that we don't have to go through hassle of getting a user token.
        // This is not (yet) supported by Spring Social, so we must construct the request ourselves.
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + appToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        String lastTweetId = null;
        boolean tweetsExist = true;
        List<Tweet> tweets = new ArrayList<Tweet>();
        while(tweetsExist) {
        	String url ="https://api.twitter.com/1.1/search/tweets.json?q={query}&count=100";
        	if(lastTweetId != null && lastTweetId != "") {
        		url = url +"&max_id="+lastTweetId;
        		//lastTweetId = null;
        	}        	
        	Map<String, ?> result = rest.exchange(url, HttpMethod.GET, requestEntity, Map.class, query).getBody();
            List<Map<String, ?>> statuses = (List<Map<String, ?>>) result.get("statuses");
            
            int counter = 0;
            int noOfStatuses = statuses.size();
            if(noOfStatuses <2) {
            	tweetsExist = false;
            }
            
            for (Map<String, ?> status : statuses) {
            	if(!status.get("id").toString().equals(lastTweetId)) {
            		String statusText = status.get("text").toString();
            		Map<String, ?> userDetails = (Map<String, ?>)status.get("user");
            		String likedBy = status.get("favorite_count").toString();
            		String createdTime = status.get("created_at").toString();
            		String userName = userDetails.get("name").toString();
            		String retweetCount = status.get("retweet_count").toString();
            		
            		//String tweet = statusText.substring(statusText.indexOf(":")+1 , statusText.length());
            		//System.out.println(statusText);
                	tweets.add(new Tweet(Long.valueOf(status.get("id").toString()), userName, statusText, likedBy, createdTime, retweetCount));	
            	} 
                counter++;
                if(noOfStatuses == counter) {                	
                	lastTweetId = status.get("id").toString();
                }
                               
            }	
        }
        return tweets;
    }

       
    public static String fetchApplicationAccessToken(String appId, String appSecret) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        OAuth2Operations oauth = new OAuth2Template(appId, appSecret, "", "https://api.twitter.com/oauth2/token");
        return oauth.authenticateClient().getAccessToken();
    }
    

}
