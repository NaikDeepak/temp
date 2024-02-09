package com.jda.snew.twitter;

import java.util.List;

public class TweetsObject {

	public TweetsObject(List<Tweet> tweets) {
		this.data = tweets;
	}
	
	private List<Tweet> data;

	public List<Tweet> getData() {
		return data;
	}

	public void setData(List<Tweet> data) {
		this.data = data;
	}
}
