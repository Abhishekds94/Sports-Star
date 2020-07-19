package com.abhishek.sportsstar.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.sportsstar.R;
import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.model.EventsItem;
import com.abhishek.sportsstar.data.model.TeamsItem;
import com.abhishek.sportsstar.data.repo.SearchRepository;
import com.abhishek.sportsstar.databinding.FragmentSearchBinding;
import com.abhishek.sportsstar.ui.adapter.EventAdapter;
import com.abhishek.sportsstar.ui.adapter.NextEventAdapter;
import com.abhishek.sportsstar.ui.base.BaseFragment;
import com.abhishek.sportsstar.ui.viewmodel.SearchViewModel;
import com.abhishek.sportsstar.ui.viewmodel.SearchViewModelFactory;

import java.util.List;

public class SearchFragment extends BaseFragment {

    private SearchViewModel searchViewModel = null;
    private FragmentSearchBinding binding;

    private View zeroLoaderState;
    private View teamInfoContainer;
    private TextView teamName;
    private TextView teamShortName;
    private TextView teamYear;
    private TextView teamAlias;
    private ImageView teamLogo;
    private ImageView teamStadium;

    private TextView noEventFoundRS;
    private TextView noEventFoundFS;

    private RecyclerView resultsRC;
    private RecyclerView fixtureRC;
    private EventAdapter lastEventAdapter;
    private NextEventAdapter nextEventAdapter;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        rootView = binding.getRoot(); //inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SearchViewModelFactory factory = new SearchViewModelFactory(new SearchRepository(client));
        searchViewModel = new ViewModelProvider(this, factory).get(SearchViewModel.class);
        binding.setViewModel(searchViewModel);
        initUI();
        initRecyclerView();
        addObserver();
    }

    private void addObserver() {
        if (null != searchViewModel) {

            searchViewModel.getTeamsData().observe(this,
                    new Observer<APIResponse>() {
                        @Override
                        public void onChanged(APIResponse apiResponse) {
                            if (apiResponse.getError() == null) {
                                List<TeamsItem> temp = apiResponse.getTeams().getTeams();
                                if (null != temp && !temp.isEmpty()) {
                                    hideStateZeroLoader();
                                    TeamsItem item = temp.get(0);
                                    updateTeamCardUI(item);
                                    searchViewModel.fetchEvents(item.getIdTeam());
                                } else {
                                    // No Data found
                                    showToast("No Result found");
                                }
                            } else {
                                throwError(apiResponse.getError());
                            }
                        }
                    });

            searchViewModel.getLastEvents().observe(this, new Observer<APIResponse>() {
                @Override
                public void onChanged(APIResponse apiResponse) {
                    if (apiResponse.getError() == null) {
                        List<EventsItem> temp = apiResponse.getEvents().getResults();
                        if (null != temp
                                && !temp.isEmpty()) {
                            noEventFoundRS.setVisibility(View.GONE);
                            resultsRC.setVisibility(View.VISIBLE);
                            lastEventAdapter.updateData(temp);
                        } else {
                            noEventFoundRS.setVisibility(View.VISIBLE);
                            resultsRC.setVisibility(View.GONE);
                        }
                    } else {
                        throwError(apiResponse.getError());
                    }
                }
            });

            searchViewModel.getNextEvents().observe(this, new Observer<APIResponse>() {
                @Override
                public void onChanged(APIResponse apiResponse) {
                    if (apiResponse.getError() == null) {
                        List<EventsItem> temp = apiResponse.getEvents().getEvents();
                        if (null != temp
                                && !temp.isEmpty()) {
                            noEventFoundFS.setVisibility(View.GONE);
                            fixtureRC.setVisibility(View.VISIBLE);
                            nextEventAdapter.updateData(temp);
                        } else {
                            noEventFoundFS.setVisibility(View.VISIBLE);
                            fixtureRC.setVisibility(View.GONE);
                        }
                    } else {
                        throwError(apiResponse.getError());
                    }
                }
            });
        }
    }

    private void updateTeamCardUI(TeamsItem teamsItem) {
        teamName.setText(teamsItem.getStrTeam());
        teamYear.setText(teamsItem.getIntFormedYear());
        teamAlias.setText(teamsItem.getStrKeywords());
        teamShortName.setText(teamsItem.getStrTeamShort());
        String url = teamsItem.getStrTeamBadge();
        String url1 = teamsItem.getStrStadiumThumb();
        if (null != url && !url.isEmpty()) {
            teamLogo.setBackgroundResource(android.R.color.transparent);
            loadImage(teamLogo, teamsItem.getStrTeamBadge());
        }
        if (null != url1 && !url1.isEmpty()) {
            teamStadium.setBackgroundResource(android.R.color.transparent);
            loadImage1(teamStadium, teamsItem.getStrStadiumThumb());
        }
    }

    private void initUI() {
        final SearchView searchView = rootView.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchViewModel.fetchTeamInformation(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        // --- Component ---
        zeroLoaderState = rootView.findViewById(R.id.zeroLoaderState);
        teamInfoContainer = rootView.findViewById(R.id.teamInfoContainer);

        teamName = rootView.findViewById(R.id.tv_teamName);
        teamShortName = rootView.findViewById(R.id.tv_teamShortName);
        teamAlias = rootView.findViewById(R.id.tv_teamAlias);
        teamYear = rootView.findViewById(R.id.tv_teamYear);
        teamLogo = rootView.findViewById(R.id.iv_Team);
        teamStadium = rootView.findViewById(R.id.iv_teamStadium);

        noEventFoundRS = rootView.findViewById(R.id.noEventFoundRS);
        noEventFoundFS = rootView.findViewById(R.id.noEventFoundFC);

    }

    private void initRecyclerView() {
        // --- Recycler View ---
        resultsRC = rootView.findViewById(R.id.resultsRC);
        fixtureRC = rootView.findViewById(R.id.fixtureRC);

        nextEventAdapter = new NextEventAdapter();
        lastEventAdapter = new EventAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,
                RecyclerView.HORIZONTAL,
                false);

        LinearLayoutManager linearLayoutManagerFixture = new LinearLayoutManager(activity,
                RecyclerView.HORIZONTAL,
                false);

        resultsRC.setLayoutManager(linearLayoutManager);
        fixtureRC.setLayoutManager(linearLayoutManagerFixture);

        resultsRC.setAdapter(lastEventAdapter);
        fixtureRC.setAdapter(nextEventAdapter);
    }

    private void hideStateZeroLoader() {
        zeroLoaderState.setVisibility(View.GONE);
        teamInfoContainer.setVisibility(View.VISIBLE);
    }
}
