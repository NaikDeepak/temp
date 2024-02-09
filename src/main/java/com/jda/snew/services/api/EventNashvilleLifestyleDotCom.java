package com.jda.snew.services.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.events.BarGraphEvents;
import com.jda.snew.events.Event;
import com.jda.snew.events.EventsObject;
import com.jda.snew.events.NashvilleDotCom;
import com.jda.snew.events.NashvilleLifestyles;

@Path("events")
public class EventNashvilleLifestyleDotCom {
	@GET
	@Path("nashvillelifestyledotcom")
	@Produces({ MediaType.APPLICATION_JSON })
	public EventsObject getEvents() {
		SNEWCache cacheInstance = SNEWCache.getInstance();
		Properties props = (Properties) cacheInstance.getObject("properties");
		EventsObject eventsObject = (EventsObject) cacheInstance.getObject("nashvillelifestyledotcom");
		try {
			if (eventsObject == null) {
				eventsObject = NashvilleLifestyles.getDataFromNashVilleLifestylesDotCom();
				System.out.println("Total Events" + eventsObject.getData().size());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return eventsObject;
	}
}
