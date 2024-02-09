package com.jda.snew.services.api;

import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.news.GoogleSearch;
import com.jda.snew.news.UrlResults;

@Path("news")
public class News {
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public UrlResults getNewsResults() {
		SNEWCache cacheInstance = SNEWCache.getInstance();
		Properties properties = (Properties) cacheInstance.getObject("properties");
		UrlResults urlResults = (UrlResults) cacheInstance.getObject("newsListObject");
		try {
			if (urlResults == null) {
				GoogleSearch obj = new GoogleSearch();
				String searchString = properties.getProperty("newsSearchQuery");
				Set<String> result = obj.getDataFromGoogle(searchString);
				urlResults = new UrlResults();
				urlResults.setSearchString(searchString);
				urlResults.setTotalCount(cacheInstance.getProperty("googleResultCount"));
				urlResults.setUrlLinks(result);
				System.out.println("Total News Results:" + result.size());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return urlResults;
	}
}
