package com.jda.snew.facebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jda.snew.util.HTTPClient;
import com.jda.snew.util.JSON;

@SuppressWarnings("unchecked")
public class FacebookPostsSearch {
	private static final String END_POINT = "https://graph.facebook.com/v2.5";
	List<Map<String,Object>> posts = new ArrayList<>();
	public List<Map<String, Object>> posts(List<String> pageId, String token) {
		//converting from lambda to normal loop to deploy it in appEng it should by java 7 compatible
		for(String page : pageId){
			String call = prepareFeedCall(page, token);
			String response = null;
			do{
				response = HTTPClient.getBody(call);
				Map<String, Object> level1 = JSON.object(response, Map.class);
				System.out.println(level1.keySet());
				posts.addAll((List<Map<String,Object>>)level1.get("data"));
			}while((call = nextPage(response))!= null);
		};
	return posts;	
	}

	private String nextPage(String response){
		Map<String, Object> level1 = JSON.object(response, Map.class);
		Map<String, String> paging = (Map<String, String>) level1.get("paging");
		if(paging!=null){
			String nextPage = paging.get("next");
			return nextPage;
		}
		return null;
	}
	
	private String prepareFeedCall(String page, String token) {
		String api = END_POINT;
		api+= "/";
		api+=page;
		api+="/";
		api+="feed";
		api+="?fields=id,message,created_time,comments,likes&";
		api+="access_token=";
		api+=token;
		api+="&pretty=0";
		return api;
	}
	
}
