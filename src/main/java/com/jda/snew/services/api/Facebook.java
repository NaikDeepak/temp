package com.jda.snew.services.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.facebook.FacebookPostsSearch;

@Path("facebook")
public class Facebook {
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Map<String, Object>> getPosts() {
		SNEWCache instance = SNEWCache.getInstance();
		List<Map<String, Object>> posts = (List<Map<String, Object>>) instance.getObject("fbPostsObject");
		try {
			if (posts == null) {
				Properties props = (Properties) instance.getObject("properties");
				String pageIds = props.getProperty("fbPageIds");
				String[] pageIdsList = pageIds.split(",");
				FacebookPostsSearch postsSearch = new FacebookPostsSearch();
				posts = postsSearch.posts(Arrays.asList(pageIdsList), props.getProperty("fbAccessToken"));
				System.out.println("Found " + posts.size() + " Posts");
				System.out.println(posts.get(0).keySet());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return posts;
	}
}
