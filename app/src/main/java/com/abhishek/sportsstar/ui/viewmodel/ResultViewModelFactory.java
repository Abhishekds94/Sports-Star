package com.abhishek.sportsstar.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abhishek.sportsstar.data.repo.ResultRepository;

public class ResultViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ResultRepository repo;

    public ResultViewModelFactory(ResultRepository repo) {
        this.repo = repo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ResultViewModel(repo);
    }
}
