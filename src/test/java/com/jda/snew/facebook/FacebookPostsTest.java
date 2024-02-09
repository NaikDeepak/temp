package com.jda.snew.facebook;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.jda.snew.util.JSON;

@Ignore
public class FacebookPostsTest {

	private static final String PAGE_ID = "108036335928491";
	private static final String ACCESS_TOKEN = "CAACEdEose0cBACrKKda51I0ekg4NZCaMHHJgRaq1Km9N3CDbp0pFVIXShUZBSPjbQZAtjBRmJ1HglF4UATV7ZArvSDgBbAI6STDRWC6Oy6HVNZANilqg994yYHJSYnKybU3MZB6QaR7vAfb7yNDZC7An6boOMI8ZCFE74osTWDYepiWO3IxMSNJPccVh4ZBZCb0RnpUZAfBN0MCIifen8Kb0ZBDS";

	@Test
	public void test() {
		FacebookPostsSearch postsSearch = new FacebookPostsSearch();
		List<Map<String,Object>> posts = postsSearch.posts(Arrays.asList(PAGE_ID), ACCESS_TOKEN);
		System.out.println("Found "+posts.size()+" Posts");
		System.out.println(JSON.string(posts));
	}

}
