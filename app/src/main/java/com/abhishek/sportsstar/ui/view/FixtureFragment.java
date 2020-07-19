package com.abhishek.sportsstar.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.sportsstar.R;
import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.model.EventsItem;
import com.abhishek.sportsstar.data.repo.FixtureRepository;
import com.abhishek.sportsstar.ui.adapter.NextEventAdapter;
import com.abhishek.sportsstar.ui.base.BaseFragment;
import com.abhishek.sportsstar.ui.viewmodel.FixtureViewModel;
import com.abhishek.sportsstar.ui.viewmodel.FixtureViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureFragment extends BaseFragment {

    private View zeroLoaderState;
    private RecyclerView fixtureRC;
    private View containerList;
    private TextView noEventFound;

    private NextEventAdapter adapter;
    private List<String> listOfLeagueCode = new ArrayList<>();
    private FixtureViewModel fixtureViewModel;

    public static FixtureFragment getInstance() {
        return new FixtureFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fixtures, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FixtureViewModelFactory fixtureViewModelFactory = new FixtureViewModelFactory(
                new FixtureRepository(client));
        fixtureViewModel = new ViewModelProvider(this, fixtureViewModelFactory).get(FixtureViewModel.class);

        initUI();
        getData();
        addObserver();
    }

    private void addObserver() {
        fixtureViewModel.getData().observe(this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {

                if (apiResponse.getError() == null) {
                    List<EventsItem> temp = apiResponse.getEvents().getEvents();
                    if (null != temp && !temp.isEmpty()) {
                        hideZeroState();
                        adapter.updateData(temp);
                    } else {
                        hideZeroState();
                        fixtureRC.setVisibility(View.GONE);
                        noEventFound.setVisibility(View.VISIBLE);
                    }
                } else {
                    throwError(apiResponse.getError());
                }
            }
        });
    }

    private void getData() {
        String[] data = activity.getResources().getStringArray(R.array.league_code);
        listOfLeagueCode.addAll(Arrays.asList(data));
    }

    private void initUI() {

        zeroLoaderState = rootView.findViewById(R.id.zeroLoaderState);
        containerList = rootView.findViewById(R.id.containerList);
        noEventFound = rootView.findViewById(R.id.noEventFound);

        Spinner leagueSpinner = rootView.findViewById(R.id.sp_fixtures);
        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long id) {
                Log.e("#####", "League Code Selected: " + listOfLeagueCode.get(index));
                if (null != fixtureViewModel && index > 0) {
                    fixtureViewModel.fetchFutureEvents(listOfLeagueCode.get(index));
                } else {
                    showZeroState();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });


        // --- RecyclerView ---

        fixtureRC = rootView.findViewById(R.id.fixtureRC);
        adapter = new NextEventAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,
                RecyclerView.VERTICAL,
                false);
        fixtureRC.setLayoutManager(linearLayoutManager);
        fixtureRC.setAdapter(adapter);
    }

    private void hideZeroState() {
        zeroLoaderState.setVisibility(View.GONE);
        containerList.setVisibility(View.VISIBLE);
        fixtureRC.setVisibility(View.VISIBLE);
        noEventFound.setVisibility(View.GONE);

    }

    private void showZeroState() {
        zeroLoaderState.setVisibility(View.VISIBLE);
        adapter.clearData();
        containerList.setVisibility(View.GONE);
        fixtureRC.setVisibility(View.GONE);
        noEventFound.setVisibility(View.GONE);
    }
}
