package com.abhishek.sportsstar.data.repo;

import androidx.lifecycle.MutableLiveData;

import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.model.Events;
import com.abhishek.sportsstar.data.network.SportsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixtureRepository {

    private SportsAPI sportsAPIClient;
    private MutableLiveData<APIResponse> data = new MutableLiveData<>();

    public FixtureRepository(SportsAPI sportsAPIClient) {
        this.sportsAPIClient = sportsAPIClient;
    }

    public void fetchFutureEventsForLeague(String leagueCode) {

        Call<Events> call = sportsAPIClient.getFutureEvents(leagueCode);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                if (response.isSuccessful()) {
                    data.setValue(new APIResponse(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                data.setValue(new APIResponse(t));
            }
        });
    }

    public MutableLiveData<APIResponse> getData() {
        return data;
    }
}
