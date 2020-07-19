package com.abhishek.sportsstar.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abhishek.sportsstar.data.repo.SearchRepository;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private SearchRepository searchRepository;

    public SearchViewModelFactory(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchViewModel(searchRepository);
    }
}
