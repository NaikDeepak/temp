package com.jda.snew.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.services.api.EventNashvilleDotCom;
import com.jda.snew.services.api.EventNashvilleLifestyleDotCom;
import com.jda.snew.services.api.Twitter;
import com.jda.snew.services.api.Weather;
import com.jda.snew.services.api.Youtube;

@SuppressWarnings("serial")
public class StartupHook extends HttpServlet {
	public void init() throws ServletException {
		System.out.println("----------");
		System.out.println("---------- StartupHook Servlet Initialized successfully ----------");
		System.out.println("----------");
		loadSNEWPropertiesIntoCache();
	}

	public void loadSNEWPropertiesIntoCache(){
		SNEWCache instance = SNEWCache.getInstance();
		InputStream inputStream = null;
		try {
			Properties props = new Properties();
			String propFileName = "/WEB-INF/Properties/application.properties";

			inputStream = getServletContext().getResourceAsStream(propFileName);
			if (inputStream != null) {
				props.load(inputStream);
				instance.setObject("properties", props);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*instance.setProperty("latitude", "17.3850");
		System.out.println(instance.getProperty("latitude"));
		
		Weather weather = new Weather();
		instance.setObject("weatherForecastObject", weather.getForecast());
		
		Twitter twitter= new Twitter();
		instance.setObject("tweetsListObject", twitter.getTweets());
		
		Youtube youtube= new Youtube();
		instance.setObject("youtubeVidoesObject", youtube.getyoutubeVideos());
		
		Facebook facebook= new Facebook();
		instance.setObject("fbPostsObject", facebook.getPosts());
		
		EventNashvilleDotCom nashvilledotcom = new EventNashvilleDotCom();
		instance.setObject("nashvilledotcom", nashvilledotcom.getEvents());
		
		EventNashvilleLifestyleDotCom nashvilleEventfulDotcom = new EventNashvilleLifestyleDotCom();
		instance.setObject("nashvillelifestyledotcom", nashvilleEventfulDotcom.getEvents());*/
	}
}
