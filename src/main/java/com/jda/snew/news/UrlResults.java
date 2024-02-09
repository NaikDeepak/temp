package com.jda.snew.news;

import java.util.Set;

public class UrlResults {
	private String totalCount;
	private Set<String> urlLinks;
	private String searchString;
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public Set<String> getUrlLinks() {
		return urlLinks;
	}
	public void setUrlLinks(Set<String> urlLinks) {
		this.urlLinks = urlLinks;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
}
