package com.jda.snew.events;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//Website: http://www.nashville.com/calendarofevents.html

public class NashvilleDotCom {	
	public static final String REQUESTURL = "http://www.nashville.com/calendarofevents.html";
	
	@SuppressWarnings("deprecation")
	public static List<Event> getDataFromNashVilleDotCom(String startd,String endd) {		
		List<Event> events = new ArrayList<Event>();
		String request = REQUESTURL;
		System.out.println("Sending request..." + request);
		Date startDate = new Date(startd);
		Date endDate = new Date(endd);
		try {
			// need http protocol, set this as a Google bot agent :)
			Document doc = Jsoup.connect(request).data("eventstartdate", startd)
					.data("eventenddate", endd).data("date_search", "Go")
					.userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").timeout(50000)
					.get();
			// get all links
			Elements links = doc.select("div[class=events_main_holder]");
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			SimpleDateFormat formatter1 = new SimpleDateFormat("MMMM dd yyyy", Locale.ENGLISH);
			SimpleDateFormat formatterForParsedFromTime = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
			for (Element link : links) {
				Elements eventDatesLink = link.select("div[class=event_date]");
				Elements eventTitleLink = link.select("a[class=event_title]");
				Elements eventVenueLink = link.select("div[class=titel_venue] span");
				Elements eventDescLink = link.select("div[class=desc]");
				Event evt = new Event();
				if (eventDatesLink != null) {
					String datesStr = eventDatesLink.text().replace("\u00a0", " ");
					if (datesStr.contains("-")) {
						String datesArr[] = datesStr.trim().split("-"); // Gives
																		// two
																		// date
																		// strings-
																		// from/to
						if (datesArr.length == 2) {							
							try {
								Date parsedFromTime = formatter.parse(datesArr[0]);
								if (parsedFromTime.compareTo(startDate) < 0) {
									evt.setFromTime(startDate);
									evt.setParsedFromTime(formatterForParsedFromTime.format(startDate));
								} else if (parsedFromTime.after(endDate)) {
									continue;
									// evt.setFromTime(parsedFromTime);
								} else {
									evt.setFromTime(parsedFromTime);
									evt.setParsedFromTime(formatterForParsedFromTime.format(parsedFromTime));
								}
								Date parsedToTime = formatter.parse(datesArr[1]);
								if (parsedToTime.compareTo(endDate) > 0) {
									evt.setToTime(endDate);
								} else {
									evt.setToTime(parsedToTime);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					} else {
						int year = Calendar.getInstance().get(Calendar.YEAR);
						String[] strTemp = datesStr.split("\\s+");
						for (int i = 1; i < strTemp.length; i++) {
							if (i == 1) {
								datesStr = strTemp[i];
							} else {
								datesStr = datesStr + " " + strTemp[i];
							}
						}
						datesStr = datesStr + " " + year;						
						Date date = null;
						try {
							date = formatter1.parse(datesStr);
							if (date.compareTo(startDate) < 0) {
								evt.setFromTime(startDate);
								evt.setParsedFromTime(formatterForParsedFromTime.format(startDate));
								evt.setToTime(startDate);
							} else if (date.compareTo(endDate) > 0) {
								evt.setFromTime(endDate);
								evt.setParsedFromTime(formatterForParsedFromTime.format(endDate));
								evt.setToTime(endDate);
							} else {
								evt.setFromTime(date);
								evt.setParsedFromTime(formatterForParsedFromTime.format(date));
								evt.setToTime(date);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				if (eventTitleLink != null) {
					String titleStr = eventTitleLink.text();
					evt.setName(titleStr);
				}
				if (eventVenueLink != null) {
					String venueStr = eventVenueLink.text().replace("/", "").trim();
					evt.setPlace(venueStr);
				}
				if (eventDescLink != null) {
					String descStr = eventDescLink.text();
					evt.setDescription(descStr);
				}
				events.add(evt);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return events;
	}
	
}
