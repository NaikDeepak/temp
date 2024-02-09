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

@Path("events")
public class EventNashvilleDotCom {
	@GET
	@Path("nashvilledotcom")
	@Produces({ MediaType.APPLICATION_JSON })
	public EventsObject getEvents() {
		SNEWCache cacheInstance = SNEWCache.getInstance();
		Properties props = (Properties) cacheInstance.getObject("properties");
		EventsObject eventsObject = (EventsObject) cacheInstance.getObject("nashvilledotcom");
		try {
			if (eventsObject == null) {
				List<Event> evts = NashvilleDotCom.getDataFromNashVilleDotCom(props.getProperty("eventStartDate"),
						props.getProperty("eventEndDate"));
				int totalEvents = 0;
				Map<Date, Integer> eventsCountMap = new TreeMap<Date, Integer>();
				for (Event e : evts) {
					Date date = e.getFromTime();
					Integer count = eventsCountMap.get(date);
					if (count == null) {
						eventsCountMap.put(date, 1);
					} else {
						eventsCountMap.put(date, eventsCountMap.get(date) + 1);
					}
					totalEvents++;
					// System.out.println(e.toString());
				}
				Set<Date> datesSet = eventsCountMap.keySet();
				Iterator<Date> datesIterator = datesSet.iterator();
				int[] counts = new int[eventsCountMap.size()];
				String[] datesParsed = new String[eventsCountMap.size()];
				int index = 0;
				while (datesIterator.hasNext()) {
					Date dcurr = datesIterator.next();
					counts[index] = eventsCountMap.get(dcurr);
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					datesParsed[index] = df.format(dcurr);
					index++;
				}
				index = 0;
				BarGraphEvents barGraph = new BarGraphEvents();
				barGraph.setDataSet(counts, datesParsed);

				System.out.println("Total Events" + totalEvents);

				eventsObject = new EventsObject(evts, barGraph);
				/*
				 * String[] datList =
				 * (eventsObject.getBarGraph().getDataSet().getLabels()); int[]
				 * evtCounts =
				 * (eventsObject.getBarGraph().getDataSet().getValues());
				 * for(int i=0; i< datList.length;i++){
				 * System.out.println(datList[i]+"-->"+evtCounts[i]); }
				 */
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return eventsObject;
	}
}
