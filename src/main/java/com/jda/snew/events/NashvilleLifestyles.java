package com.jda.snew.events;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Website: http://www.nashville.com/calendarofevents.html
public class NashvilleLifestyles {

	private static final String REQUEST_URL = "http://www.nashvillelifestyles.com/events";

	@SuppressWarnings("deprecation")
	public static EventsObject getDataFromNashVilleLifestylesDotCom() {
		List<Event> events = new ArrayList<Event>();
		Map<Date, Integer> additionalDtls = new TreeMap<Date, Integer>();
		String request = REQUEST_URL;
		System.out.println("Sending request..." + request);
		SimpleDateFormat formatterForParsedFromTime = new SimpleDateFormat(
				"dd/MM/yy", Locale.ENGLISH);
		String event_date_start = "2016-04-07";
		String event_date_end = "2016-04-14";
		Date evtStartDateObj = null;
		Date evtEndDateObj = null;
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		try {
			evtStartDateObj = dtFormat.parse(event_date_start);
			evtEndDateObj = dtFormat.parse(event_date_end);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String defaultTime = " 00:00:00";
		try {
			// need http protocol, set this as a Google bot agent :)
			Document doc = Jsoup
					.connect(request)
					.data("event_date_start", event_date_start + defaultTime)
					.data("event_date_end", event_date_end + defaultTime)
					.data("date_search", "Go")
					.userAgent(
							"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(50000).get();
			// get all links
			Elements links = doc.select("section[id=more_events_NL] article");

			for (Element link : links) {
				Event evt = new Event();
				Elements eventDatesLink = link
						.select("section[class=event_date]");
				Elements eventNameLink = link.select("h1 a");
				Elements eventMoreLink = link.select("p a");
				String day = eventDatesLink.select("span[class=day]").text();
				String month = eventDatesLink.select("span[class=month]")
						.text();
				String dateStr = day + " " + month + " 2016";
				SimpleDateFormat formatter = new SimpleDateFormat(
						"dd MMMM yyyy", Locale.ENGLISH);
				Date dateObj = null;
				try {
					dateObj = formatter.parse(dateStr);
					if (!(dateObj.compareTo(evtStartDateObj) >= 0 && dateObj
							.compareTo(evtEndDateObj) <= 0)) {
						continue;
					}
					evt.setFromTime(dateObj);
					evt.setToTime(dateObj);
					evt.setParsedFromTime(formatterForParsedFromTime.format(dateObj));
					if (additionalDtls.get(dateObj) == null) {
						additionalDtls.put(dateObj, 1);
					} else {
						additionalDtls.put(dateObj,
								additionalDtls.get(dateObj) + 1);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}

				String eventName = eventNameLink.text();
				evt.setName(eventName);
				String moreDetailsLink = "http://www.nashvillelifestyles.com"
						+ link.select("p a[href]").attr("href");
				Document moreDetailsDoc = Jsoup
						.connect(moreDetailsLink)
						.userAgent(
								"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
						.timeout(5000).get();
				Elements eventAddress = moreDetailsDoc
						.select("section[id=event_address]");
				String address = eventAddress.text();
				if (address.indexOf("Get Direc") > -1) {
					address = address.substring(0,
							address.indexOf("Get Direc") - 1);
				}
				evt.setPlace(address);
				evt.setDescription(moreDetailsDoc.select(
						"section[class=event-description]").text());
				events.add(evt);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Date> dateKeys = additionalDtls.keySet();

		String labels[] = new String[dateKeys.size()];
		int values[] = new int[dateKeys.size()];
		int counter = 0;
		for (Date d1 : dateKeys) {
			labels[counter] = formatterForParsedFromTime.format(d1);
			values[counter] = additionalDtls.get(d1);
			counter++;
		}
		BarGraphEvents bge = new BarGraphEvents();
		bge.setDataSet(values, labels);
		EventsObject evtsObject = new EventsObject(events, bge);

		return evtsObject;
	}

}
