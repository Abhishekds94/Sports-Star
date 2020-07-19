package com.abhishek.sportsstar.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.repo.FixtureRepository;

public class FixtureViewModel extends ViewModel {

    private FixtureRepository repository;
    private MutableLiveData<APIResponse> data = new MutableLiveData<>();

    public FixtureViewModel(FixtureRepository repository) {
        this.repository = repository;
    }

    public void fetchFutureEvents(String leagueCode) {
        repository.fetchFutureEventsForLeague(leagueCode);
        repository.getData().observeForever(new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                data.setValue(apiResponse);
            }
        });
    }

    public LiveData<APIResponse> getData() {
        return data;
    }
}
