package com.jda.snew.youtube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatistics;

public class YoutubeSearch {

	/**
	 * Define a global variable that identifies the name of a file that contains
	 * the developer's API key.
	 */
	public static final long NUMBER_OF_VIDEOS_RETURNED = 25;

	/**
	 * Define a global instance of a Youtube object, which will be used to make
	 * YouTube Data API requests.
	 */
	public static YouTube youtube;

	/*
	 * Prints out all results in the Iterator. For each result, print the title,
	 * video ID, and thumbnail.	 * 
	 * @param iteratorSearchResults Iterator of SearchResults to print	 * 
	 * @param query Search query (String)
	 */

	public static VideoStatistics getVideoStatistics(String videoId, String apiKey) {
		VideoStatistics statistics = null;
		try {
			YouTube.Videos.List list = youtube.videos().list("statistics");
			list.setId(videoId);
			list.setKey(apiKey);
			Video video = list.execute().getItems().get(0);
			statistics = video.getStatistics();
		} catch (Exception e) {
			System.out.println("error");
		}
		return statistics;
	}

	public static List<YoutubeStats> prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query, String apiKey) {
		List<YoutubeStats> youtubeVideosObject = new ArrayList<YoutubeStats>();
		System.out.println("\n=============================================================");
		System.out.println("   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
		System.out.println("=============================================================\n");

		if (!iteratorSearchResults.hasNext()) {
			System.out.println(" There aren't any results for your query.");
		}

		// boolean flag = true;

		while (iteratorSearchResults.hasNext()) {
			YoutubeStats yVideo = new YoutubeStats();
			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();
			VideoStatistics currentVideoStatistics = getVideoStatistics(rId.getVideoId(), apiKey);
			if (currentVideoStatistics != null) {
				// Confirm that the result represents a video. Otherwise, the
				// item will not contain a video ID.
				if (rId.getKind().equals("youtube#video")) {
					Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
					String videoId = rId.getVideoId();
					yVideo.setVideoId(videoId);
					String videoUrl = "https://www.youtube.com/watch?v="+videoId;
					yVideo.setVideoUrl(videoUrl);
					yVideo.setTitle(singleVideo.getSnippet().getTitle());
					yVideo.setThumbnailUrl(thumbnail.getUrl());
					yVideo.setLikesCount(currentVideoStatistics.getLikeCount());
					yVideo.setViewCount(currentVideoStatistics.getViewCount());
					/*System.out.println(" Video Id: " + rId.getVideoId());
					System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
					System.out.println(" Thumbnail: " + thumbnail.getUrl());
					System.out.println("The view count is: " + currentVideoStatistics.getViewCount());
					System.out.println("The like count is: " + currentVideoStatistics.getLikeCount());
					System.out.println("\n-------------------------------------------------------------\n");*/
				
				}//inner if loop
			}//outer if
			youtubeVideosObject.add(yVideo);
		}//while loop
		return youtubeVideosObject;
	}
}
