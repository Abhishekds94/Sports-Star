package com.abhishek.sportsstar.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abhishek.sportsstar.data.repo.FixtureRepository;

public class FixtureViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private FixtureRepository fixtureRepository;

    public FixtureViewModelFactory(FixtureRepository fixtureRepository) {
        this.fixtureRepository = fixtureRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FixtureViewModel(fixtureRepository);
    }
}
