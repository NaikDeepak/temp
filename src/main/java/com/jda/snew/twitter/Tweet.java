package com.jda.snew.twitter;

public class Tweet {
    private long id;

    private String tweet;
    private String userName;
    private String likedBy;
    private String createdTime;
    private String retweetCount;
    
    public String getRetweetCount() {
		return retweetCount;
	}

	public String getLikedBy() {
		return likedBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public String getUserName() {
		return userName;
	}

	public Tweet(long id, String userName, String tweet, String likedBy, String createdTime, String retweetCount) {
        this.id = id;
        this.tweet = tweet;
        this.userName = userName;
        this.likedBy = likedBy;
        this.createdTime = createdTime;
        this.retweetCount = retweetCount;
    }
    
    public long getId() {
        return id;
    }
    
    @Override
	public String toString() {
		return "Tweet [id=" + id + ", tweet=" + tweet + ", userName="
				+ userName + ", likedBy=" + likedBy + ", createdTime="
				+ createdTime + "]";
	}

	public String getTweet() {
        return tweet;
    }
}
