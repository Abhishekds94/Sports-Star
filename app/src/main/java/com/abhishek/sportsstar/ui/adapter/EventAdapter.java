package com.abhishek.sportsstar.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.sportsstar.R;
import com.abhishek.sportsstar.data.model.EventsItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<EventsItem> listOfItems = new ArrayList<>();
    Context mContext;

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.card_results, parent, false);
        return new EventViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bindData(listOfItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public void updateData(List<EventsItem> data) {
        clearData();
        listOfItems.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        int size = listOfItems.size();
        listOfItems.clear();
        notifyItemRangeRemoved(0, size);
    }


    protected static class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView resultsEventDate;
        private ImageView resultsHomeTeam;
        private TextView resultsHomeScore;
        private TextView resultsAwayScore;
        private TextView resultsHomeName;
        private TextView resultsAwayName;
        private Context mContext;

        public EventViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.mContext = context;
            resultsEventDate = itemView.findViewById(R.id.tv_resultsEventDate);
            resultsHomeTeam = itemView.findViewById(R.id.iv_resultsHomeTeam);
            resultsHomeScore = itemView.findViewById(R.id.tv_resultsHomeScore);
            resultsAwayScore = itemView.findViewById(R.id.tv_resultsAwayScore);
            resultsHomeName = itemView.findViewById(R.id.tv_resultsHomeName);
            resultsAwayName = itemView.findViewById(R.id.tv_resultsAwayName);
        }

        private void bindData(EventsItem item) {

            resultsEventDate.setText(item.getDateEvent());
            resultsHomeScore.setText(item.getIntHomeScore());
            resultsAwayScore.setText(item.getIntAwayScore());
            resultsHomeName.setText(item.getStrHomeTeam());
            resultsAwayName.setText(item.getStrAwayTeam());

            String strThumb = item.getStrThumb();
            if (strThumb != null && !strThumb.isEmpty()) {
                resultsHomeTeam.setBackgroundResource(android.R.color.transparent);
                loadImage(resultsHomeTeam, strThumb);
            } else {
                resultsHomeTeam.setBackground(ContextCompat.getDrawable(mContext, R.drawable.card_bg));
                resultsHomeTeam.setImageResource(android.R.color.transparent);
            }

        }

        private void loadImage(ImageView targetImageView, String url) {
            Glide.with(mContext)
                    .load(url)
                    .optionalFitCenter()
                    .into(targetImageView);
        }
    }
}

