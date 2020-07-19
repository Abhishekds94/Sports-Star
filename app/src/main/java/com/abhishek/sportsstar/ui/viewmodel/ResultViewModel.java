package com.abhishek.sportsstar.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.repo.ResultRepository;

public class ResultViewModel extends ViewModel {

    private ResultRepository repo;
    private MutableLiveData<APIResponse> data = new MutableLiveData<>();

    public ResultViewModel(ResultRepository repo) {
        this.repo = repo;
    }

    public void fetchOldEventsFor(String leagueCode) {
        repo.fetchLastEventsForLeague(leagueCode);
        repo.getData().observeForever(new Observer<APIResponse>() {
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
