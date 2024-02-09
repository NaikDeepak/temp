package com.jda.snew.weather.api;

import java.util.List;

public class ForecastObject {
	String place;
	List<DataPoint> data;

	public List<DataPoint> getData() {
		return data;
	}

	public void setData(List<DataPoint> data) {
		this.data = data;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	
}
