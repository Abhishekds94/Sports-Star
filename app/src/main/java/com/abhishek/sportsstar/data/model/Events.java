package com.abhishek.sportsstar.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Events{

	@SerializedName("events")
	private List<EventsItem> events;

	@SerializedName("results")
	private List<EventsItem> results;

	public void setEvents(List<EventsItem> events){
		this.events = events;
	}

	public List<EventsItem> getEvents(){
		return events;
	}

	public List<EventsItem> getResults() {
		return results;
	}

	public void setResults(List<EventsItem> results) {
		this.results = results;
	}
}