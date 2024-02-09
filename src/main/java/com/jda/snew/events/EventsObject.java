package com.jda.snew.events;

import java.util.List;

public class EventsObject {

	private List<Event> data;
	private BarGraphEvents barGraph;
	
	
	public BarGraphEvents getBarGraph() {
		return barGraph;
	}

	public void setBarGraph(BarGraphEvents barGraph) {
		this.barGraph = barGraph;
	}

	public EventsObject(List<Event> events,BarGraphEvents barGraph) {
		this.data = events;
		this.barGraph = barGraph;
	}

	public List<Event> getData() {
		return data;
	}

	public void setData(List<Event> data) {
		this.data = data;
	}
}
