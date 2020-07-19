package com.abhishek.sportsstar.data.model;

public class APIResponse {

    private Events events;
    private Teams teams;
    private Throwable error;

    public APIResponse(Events events) {
        this.events = events;
    }

    public APIResponse(Teams teams) {
        this.teams = teams;
    }

    public APIResponse(Throwable error) {
        this.error = error;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
