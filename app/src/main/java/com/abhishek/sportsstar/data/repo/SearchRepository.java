package com.abhishek.sportsstar.data.repo;

import androidx.lifecycle.MutableLiveData;

import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.model.Events;
import com.abhishek.sportsstar.data.model.Teams;
import com.abhishek.sportsstar.data.network.SportsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private SportsAPI sportsAPIClient;
    private MutableLiveData<APIResponse> teamsData = new MutableLiveData<>();
    private MutableLiveData<APIResponse> lastEvents = new MutableLiveData<>();
    private MutableLiveData<APIResponse> nextEvents = new MutableLiveData<>();

    public SearchRepository(SportsAPI sportsAPIClient) {
        this.sportsAPIClient = sportsAPIClient;
    }

    public void fetchTeamInformation(String teamName) {

        Call<Teams> call = sportsAPIClient.getTeamList(teamName);
        call.enqueue(new Callback<Teams>() {
            @Override
            public void onResponse(Call<Teams> call, Response<Teams> response) {
                if (response.isSuccessful()) {
                    teamsData.setValue(new APIResponse(response.body()));
                } else {
                    // Do something else
                }
            }

            @Override
            public void onFailure(Call<Teams> call, Throwable t) {
                teamsData.setValue(new APIResponse(t));
            }
        });
    }

    public void fetchLastFiveEvents(String teamId) {

        Call<Events> call = sportsAPIClient.getLastEvents(teamId);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                if (response.isSuccessful()) {
                    lastEvents.setValue(new APIResponse(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                lastEvents.setValue(new APIResponse(t));
            }
        });
    }

    public void fetchNextFiveEvents(String teamId) {

        Call<Events> call = sportsAPIClient.getNextEvents(teamId);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                if (response.isSuccessful()) {
                    nextEvents.setValue(new APIResponse(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                nextEvents.setValue(new APIResponse(t));
            }
        });
    }


    public MutableLiveData<APIResponse> getTeamsData() {
        return teamsData;
    }

    public MutableLiveData<APIResponse> getLastEvents() {
        return lastEvents;
    }
    public MutableLiveData<APIResponse> getNextEvents() {
        return nextEvents;
    }
}
