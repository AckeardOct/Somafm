package com.example.atg.somafm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atg on 04.01.18.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView artistSong;
        public TextView time;

        public HistoryViewHolder(View view) {
            super(view);
            artistSong = (TextView)view.findViewById(R.id.artistSongText);
            time  = (TextView)view.findViewById(R.id.timeText);
        }
    }

    private List<HistoryItem> data;

    public HistoryListAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(HistoryItem item) {
        data.add(item);
    }

    public void clearItems() {
        data.clear();
    }

    public HistoryItem getItem(int position) { return data.get(position); }

    @Override
    public HistoryListAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list_item, viewGroup, false);
        HistoryListAdapter.HistoryViewHolder vh = new HistoryListAdapter.HistoryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryListAdapter.HistoryViewHolder viewHolder, int i) {
        HistoryItem item = data.get(i);
        viewHolder.artistSong.setText(item.getArtist() + " - " + item.getSong());
        viewHolder.time.setText(item.getTime());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
