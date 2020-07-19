package com.abhishek.sportsstar.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.sportsstar.R;
import com.abhishek.sportsstar.data.model.EventsItem;

import java.util.ArrayList;
import java.util.List;

public class NextEventAdapter extends RecyclerView.Adapter<NextEventAdapter.NextEventViewHolder> {

    private List<EventsItem> listOfItems = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public NextEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.card_fixtures, parent, false);
        return new NextEventViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NextEventViewHolder holder, int position) {
        holder.bindData(listOfItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public void updateData(List<EventsItem> data) {
        if (null != data){
            clearData();
            listOfItems.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        int size = listOfItems.size();
        listOfItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    protected class NextEventViewHolder extends RecyclerView.ViewHolder {

        private TextView resultsEventDate;
        private TextView resultsHomeName;
        private TextView resultsAwayName;

        public NextEventViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsEventDate = itemView.findViewById(R.id.tv_fixturesEventDate);
            resultsHomeName = itemView.findViewById(R.id.tv_fixturesHomeName);
            resultsAwayName = itemView.findViewById(R.id.tv_fixturesAwayName);
        }

        private void bindData(EventsItem item) {
            resultsEventDate.setText(item.getDateEvent());
            resultsHomeName.setText(item.getStrHomeTeam());
            resultsAwayName.setText(item.getStrAwayTeam());
        }


    }
}
