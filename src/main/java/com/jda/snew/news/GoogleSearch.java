package com.jda.snew.news;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jda.snew.config.SNEWCache;

public class GoogleSearch {

	private static Pattern patternDomainName;
	private Matcher matcher;
	private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	}

	public String getDomainName(String url) {

		String domainName = "";
		matcher = patternDomainName.matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim();
		}
		return domainName;

	}

	public Set<String> getDataFromGoogle(String query) {

		Set<String> result = new LinkedHashSet<String>();
		String request = "https://www.google.com/search?q=" + query + "&num=100&filter=1";
		System.out.println("Sending request..." + request);

		try {

			// need http protocol, set this as a Google bot agent :)
			Document doc = Jsoup
					.connect(request)
					.userAgent(
							"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(50000).get();
			
			// get all links
			Elements links = doc.select("a[href]");
			Elements noOfRecordsElement = doc.select("div[id*=resultStats]");
			String resultCount = noOfRecordsElement.get(0).text().replace("About", "").replace("results", "").trim();
			SNEWCache cacheInstance = SNEWCache.getInstance();
			cacheInstance.setProperty("googleResultCount", resultCount);
			System.out.println("Result count:"+resultCount);
			for (Element link : links) {

				String temp = link.attr("href");
				if (temp.startsWith("/url?q=")) {
					// use regex to get domain name
					result.add(getDomainName(temp));
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}