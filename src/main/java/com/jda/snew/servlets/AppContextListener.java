package com.jda.snew.servlets;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.services.api.EventNashvilleDotCom;
import com.jda.snew.services.api.EventNashvilleLifestyleDotCom;
import com.jda.snew.services.api.News;
import com.jda.snew.services.api.Twitter;
import com.jda.snew.services.api.Weather;
import com.jda.snew.services.api.Youtube;

public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("SNEW AppListener has been shutdown");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("SNEW AppListener initialized.");
		TimerTask vodTimer = new VodTimerTask();
		Timer timer = new Timer();
								//minutes * seconds * milliseconds = 5 minutes
		timer.schedule(vodTimer, 1000, (5 * 60 * 1000));
	}

	class VodTimerTask extends TimerTask{
		@Override
		public void run(){
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("TimerTask " + new Date().toString());
			SNEWCache instance = SNEWCache.getInstance();
			
			instance.setProperty("latitude", "17.3850");
			System.out.println(instance.getProperty("latitude"));
			
			Weather weather = new Weather();
			instance.setObject("weatherForecastObject", null);
			instance.setObject("weatherForecastObject", weather.getForecast());
			
			News news= new News();
			instance.setObject("newsListObject", null);
			instance.setObject("newsListObject", news.getNewsResults());
			
			Twitter twitter= new Twitter();
			instance.setObject("tweetsListObject", null);
			instance.setObject("tweetsListObject", twitter.getTweets());
			
			Youtube youtube= new Youtube();
			instance.setObject("youtubeVidoesObject", null );
			instance.setObject("youtubeVidoesObject", youtube.getyoutubeVideos());
			
			EventNashvilleDotCom nashvilledotcom = new EventNashvilleDotCom();
			instance.setObject("nashvilledotcom", null);
			instance.setObject("nashvilledotcom", nashvilledotcom.getEvents());
			
			EventNashvilleLifestyleDotCom nashvilleEventfulDotcom = new EventNashvilleLifestyleDotCom();
			instance.setObject("nashvillelifestyledotcom", null);
			instance.setObject("nashvillelifestyledotcom", nashvilleEventfulDotcom.getEvents());
		}
	}
}
