package com.example.atg.somafm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by atg on 26.12.17.
 */

public class HistoryItemAdapter extends ArrayAdapter<HistoryItem> {
    private int layout;
    private LayoutInflater inflater;

    public HistoryItemAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.layout = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = inflater.inflate(layout, null);

        final HistoryItem item = getItem(position);

        final TextView artistSongText = (TextView)view.findViewById(R.id.artistSongText);
        final TextView timeText = (TextView)view.findViewById(R.id.timeText);

        String artistSong = item.getArtist() + " - " + item.getSong();
        artistSongText.setText(artistSong);
        timeText.setText(item.getTime());

        return view;
    }
}
