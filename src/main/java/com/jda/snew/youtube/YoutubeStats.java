package com.jda.snew.youtube;

import java.math.BigInteger;

public class YoutubeStats {
	private String videoId;
	private String videoUrl;
	private String title;
	private String thumbnailUrl;
	private BigInteger viewCount;
	private BigInteger likesCount;
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public BigInteger getViewCount() {
		return viewCount;
	}
	public void setViewCount(BigInteger viewCount) {
		this.viewCount = viewCount;
	}
	public BigInteger getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(BigInteger likesCount) {
		this.likesCount = likesCount;
	}	
}
