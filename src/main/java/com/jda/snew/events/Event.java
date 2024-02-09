package com.jda.snew.events;

import java.util.Date;

public class Event {

	private String name;
    private String place;
    private Date fromTime;
    private Date toTime;
    private String category;
    private String description;
    private String parsedFromTime;
           
    public String getParsedFromTime() {
		return parsedFromTime;
	}

	public void setParsedFromTime(String parsedFromTime) {
		this.parsedFromTime = parsedFromTime;
	}

	public Event() {
    	
    }
    
    public Event(String name, String place, Date time, String category, String description) {
        this.name = name;
        this.place = place;
        this.fromTime = time;
        this.toTime = time;
        this.category = category;
        this.description = description;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public void setFromTime(Date fromTime) {
    	this.fromTime = fromTime;
    }
    
    
    public void setToTime(Date toTime) {
    	this.toTime = toTime;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPlace() {
        return place;
    }
    
    public Date getFromTime() {
    	//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	//String dateTime = df.format(fromTime);
        return this.fromTime;
    }        
    
    public Date getToTime() {
    	//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	//String dateTime = df.format(toTime);
        return this.toTime;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getDescription() {
        return description;
    }

	@Override
	public String toString() {
		return "Event [name=" + name + ", place=" + place + ", fromTime="
				+ fromTime + ", toTime=" + toTime + ", category="
				+ category + ", description=" + description + "]";
	}
}
