package com.jda.snew.events;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Website: http://www.nashville.com/calendarofevents.html
public class NashvilleEventfullDotCom {

	public static void main(String args[]) {
		List<Event> evts = getDataFromNashVilleDotCom();
		for (Event e : evts) {
			System.out.println(e.toString());
		}
	}

	private static List<Event> getDataFromNashVilleDotCom() {
		List<Event> events = new ArrayList<Event>();
		int pageCount = 15;
		String fromDate = "20160408", toDate = "20160409";

		int pageNumber = 1;
		int noOfPages = 1;
		do {
			// http://nashville.eventful.com/events?q=*&ga_search=*&ga_type=events&t=2016040800-2016040900&sort_order=Popularity&page_number=1
			String request = "http://nashville.eventful.com/events?q=*&ga_search=*&ga_type=events&t=" + fromDate + "00-"
					+ toDate + "00&sort_order=Popularity&page_number=" + pageNumber;
			System.out.println("Sending request..." + request);

			try {
				// need http protocol, set this as a Google bot agent :)
				Document doc = Jsoup.connect(request)
						.userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
						.timeout(5000).get();
				// get all links
				Elements totalNumberOfResults = doc.select("span[class=results-count]");
				int totalCount = 0;
				if (totalNumberOfResults != null) {
					totalCount = Integer
							.parseInt(totalNumberOfResults.text().replace("events", "").replace(",", "").trim());
					noOfPages = totalCount / pageCount;
					if(noOfPages >= 10) noOfPages=10;
				}
				// Elements eventsLinks = doc.select("td[class=event-info]");
				Elements eventsLinks = doc.select("table[class=event-results] tr");
				for (Element link : eventsLinks) {
					Event evt = new Event();
					Elements eventInfo = link.select("td[class=event-info]");
					Elements eventTitleLink = eventInfo.select("a[data-ga-label=Event Title Link]");
					if (eventTitleLink != null) {
						String titleStr = eventTitleLink.text();
						evt.setName(titleStr);
					}
					Elements eventVenueLink = eventInfo.select("h4");
					if (eventVenueLink != null) {
						String venueStr = eventVenueLink.text();
						evt.setName(venueStr);
					}
					Elements eventDescLink = eventInfo.select("p[class=description]");
					if (eventDescLink != null) {
						String descStr = eventDescLink.text();
						evt.setDescription(descStr);
					}

					Elements eventDate = link.select("td[class=event-date]");
					if (eventDate != null) {
						String dateTime = eventDate.text();
						String additionalInfo = eventDate.select("span").text();
						dateTime = dateTime.replace(additionalInfo, "").replace("\u00a0", " ").replaceAll("\\s+", " ")
								.trim();
						SimpleDateFormat formatter = new SimpleDateFormat("EEEE MMMM yyyy", Locale.ENGLISH);
						String test = "";
					}

				}

				pageNumber++;
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (pageNumber <= noOfPages);

		return events;
	}

}
