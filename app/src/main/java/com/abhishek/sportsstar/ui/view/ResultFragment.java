package com.abhishek.sportsstar.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.sportsstar.R;
import com.abhishek.sportsstar.data.model.APIResponse;
import com.abhishek.sportsstar.data.model.EventsItem;
import com.abhishek.sportsstar.data.repo.ResultRepository;
import com.abhishek.sportsstar.ui.adapter.EventAdapter;
import com.abhishek.sportsstar.ui.base.BaseFragment;
import com.abhishek.sportsstar.ui.viewmodel.ResultViewModel;
import com.abhishek.sportsstar.ui.viewmodel.ResultViewModelFactory;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultFragment extends BaseFragment implements ChipGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private Spinner resultSpinner;
    private View zeroLoaderState;
    private RecyclerView teamRecyclerView;
    private List<String> teamData = new ArrayList<>();
    private List<String> teamCodeList = new ArrayList<>();
    private ResultViewModel resultViewModel = null;
    private EventAdapter adapter = null;

    // Obtain the FirebaseAnalytics instance.
    private FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.activity);


    public static ResultFragment getInstance() {
        return new ResultFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_results, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initUI();
        addObserver();
    }

    private void addObserver() {

        // Observe Data change

        resultViewModel.getData().observe(
                this,
                new Observer<APIResponse>() {
                    @Override
                    public void onChanged(APIResponse apiResponse) {

                        if (null == apiResponse.getError()) {
                            if (null != adapter) {
                                List<EventsItem> _temp = apiResponse.getEvents().getEvents();
                                if (_temp.size() >= 1) {
                                    teamRecyclerView.setVisibility(View.VISIBLE);
                                    adapter.updateData(_temp);
                                } else {
                                    teamRecyclerView.setVisibility(View.GONE);
                                    Log.e("####", "No Events found");
                                }
                            }
                        } else {
                            throwError(apiResponse.getError());
                        }
                    }
                }
        );
    }

    private void initViewModel() {
        ResultRepository resultRepository = new ResultRepository(client);
        ResultViewModelFactory factory = new ResultViewModelFactory(resultRepository);
        resultViewModel = new ViewModelProvider(this, factory).get(ResultViewModel.class);
    }

    private void initUI() {
        ChipGroup group = rootView.findViewById(R.id.chipGroup);
        resultSpinner = rootView.findViewById(R.id.resultSpinner);
        zeroLoaderState = rootView.findViewById(R.id.zeroLoaderState);
        teamRecyclerView = rootView.findViewById(R.id.teamRecyclerView);
        group.setOnCheckedChangeListener(this);


        adapter = new EventAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,
                RecyclerView.VERTICAL,
                false);
        teamRecyclerView.setLayoutManager(linearLayoutManager);
        teamRecyclerView.setAdapter(adapter);
    }

    private void configureSpinnerFor(int id) {

        teamData.clear();
        teamCodeList.clear();
        adapter.clearData();
        switch (id) {
            case R.id.chip_Soccer:
                String[] data = activity.getResources().getStringArray(R.array.SoccerLeagueFixtures_value);
                String[] codes = activity.getResources().getStringArray(R.array.SoccerLeagueFixtures_code);
                teamData.addAll(Arrays.asList(data));
                teamCodeList.addAll(Arrays.asList(codes));
                break;
            case R.id.chip_BaseBall:
                String[] baseBallData = activity.getResources().getStringArray(R.array.BaseBallLeagueFixtures_value);
                teamData.addAll(Arrays.asList(baseBallData));
                String[] baseBallcodes = activity.getResources().getStringArray(R.array.BaseBallLeagueFixtures_code);
                teamCodeList.addAll(Arrays.asList(baseBallcodes));
                break;

            case R.id.chip_BasketBall:
                String[] basketBallData = activity.getResources().getStringArray(R.array.BasketBallLeagueFixtures_value);
                teamData.addAll(Arrays.asList(basketBallData));
                String[] basketBallcodes = activity.getResources().getStringArray(R.array.BasketBallLeagueFixtures_code);
                teamCodeList.addAll(Arrays.asList(basketBallcodes));
                break;

            case R.id.chip_Cricket:
                String[] cricketData = activity.getResources().getStringArray(R.array.CricketLeagueFixtures_value);
                teamData.addAll(Arrays.asList(cricketData));
                String[] cricketCodes = activity.getResources().getStringArray(R.array.CricketLeagueFixtures_code);
                teamCodeList.addAll(Arrays.asList(cricketCodes));
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                teamData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(adapter);
        resultSpinner.setOnItemSelectedListener(this);
    }

    private void showLoaderState() {
        zeroLoaderState.setVisibility(View.VISIBLE);
        resultSpinner.setVisibility(View.GONE);
        teamRecyclerView.setVisibility(View.GONE);
    }

    private void hideLoaderState() {
        zeroLoaderState.setVisibility(View.GONE);
        resultSpinner.setVisibility(View.VISIBLE);
        teamRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(ChipGroup group, int checkedId) {
        Log.e("####", "" + checkedId);
        adapter.clearData();
        if (checkedId == -1) {
            showLoaderState();
        } else {
            hideLoaderState();
            configureSpinnerFor(checkedId);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long id) {
        Log.e("####", "Item Selected: " + teamData.get(index));
        Log.e("####", "Item Code: " + teamCodeList.get(index));

        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, teamData.get(index));
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);

        if (null != resultViewModel && index > 0) {
            adapter.clearData();
            resultViewModel.fetchOldEventsFor(teamCodeList.get(index));
        } else {
            Log.e("####", "resultViewModel is null or index is 0");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing with it
    }
}
