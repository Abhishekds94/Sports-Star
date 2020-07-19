package com.abhishek.sportsstar.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.repo.SearchRepository;

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository;
    private MutableLiveData<APIResponse> teamsData = new MutableLiveData<>();
    private MutableLiveData<APIResponse> lastEvents = new MutableLiveData<>();
    private MutableLiveData<APIResponse> nextEvents = new MutableLiveData<>();

    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void fetchTeamInformation(String query) {
        searchRepository.fetchTeamInformation(query);
        searchRepository.getTeamsData().observeForever(new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                teamsData.setValue(apiResponse);
            }
        });

        searchRepository.getLastEvents().observeForever(new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                lastEvents.setValue(apiResponse);
            }
        });

        searchRepository.getNextEvents().observeForever(new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                nextEvents.setValue(apiResponse);
            }
        });
    }


    public void fetchEvents(String teamId) {
        searchRepository.fetchLastFiveEvents(teamId);
        searchRepository.fetchNextFiveEvents(teamId);
    }


    public LiveData<APIResponse> getTeamsData() {
        return teamsData;
    }

    public LiveData<APIResponse> getLastEvents() {
        return lastEvents;
    }

    public LiveData<APIResponse> getNextEvents() {
        return nextEvents;
    }
}
