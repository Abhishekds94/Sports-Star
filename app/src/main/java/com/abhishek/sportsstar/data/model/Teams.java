package com.abhishek.sportsstar.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Teams{

	@SerializedName("teams")
	private List<TeamsItem> teams;

	public void setTeams(List<TeamsItem> teams){
		this.teams = teams;
	}

	public List<TeamsItem> getTeams(){
		return teams;
	}
}