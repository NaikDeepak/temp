package com.jda.snew.services.api;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.jda.snew.config.SNEWCache;
import com.jda.snew.youtube.YoutubeSearch;
import com.jda.snew.youtube.YoutubeStats;

@Path("youtube")
public class Youtube {
	/**
	 * Initialize a YouTube object to search for videos on YouTube. Then display
	 * title,thumbnail, stats of video of each video in the result.
	 *
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<YoutubeStats> getyoutubeVideos() {
		SNEWCache cacheInstance = SNEWCache.getInstance();
		Properties properties = (Properties) cacheInstance.getObject("properties");
		List<YoutubeStats> youtubeVideosList = (List<YoutubeStats>) cacheInstance.getObject("youtubeVidoesObject");
		try {
			if (youtubeVideosList == null) {
				try {
					// This object is used to make YouTube Data API requests.
					// The
					// last
					// argument is required, but since we don't need anything
					// initialized when the HttpRequest is initialized, we
					// override
					// the interface and provide a no-op function.
					YoutubeSearch.youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
							new HttpRequestInitializer() {
								public void initialize(HttpRequest request) throws IOException {
								}
							}).setApplicationName("youtube-cmdline-search-sample").build();

					// Prompt the user to enter a query term.
					String queryString = properties.getProperty("youtubeQueryString");

					// Define the API request for retrieving search results.
					YouTube.Search.List search = YoutubeSearch.youtube.search().list("id,snippet");

					// Set your developer key from the Google Developers Console
					// for
					// non-authenticated requests. See:
					// https://console.developers.google.com/
					String apiKey = properties.getProperty("youtubeApikey");
					search.setKey(apiKey);
					search.setQ(queryString);
					// Restrict the search results to only include videos. See:
					// https://developers.google.com/youtube/v3/docs/search/list#type
					search.setType("video");
					search.setOrder("viewCount");

					// To increase efficiency, only retrieve the fields that the
					// application uses.
					search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
					search.setMaxResults(YoutubeSearch.NUMBER_OF_VIDEOS_RETURNED);

					// Call the API and print results.
					SearchListResponse searchResponse = search.execute();
					List<SearchResult> searchResultList = searchResponse.getItems();
					if (searchResultList != null) {
						youtubeVideosList = YoutubeSearch.prettyPrint(searchResultList.iterator(), queryString, apiKey);
					}
				} catch (GoogleJsonResponseException e) {
					System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
							+ e.getDetails().getMessage());
				} catch (IOException e) {
					System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return youtubeVideosList;
	}
}
