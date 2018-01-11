package com.example.atg.somafm;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by atg on 04.01.18.
 */

public class RadioListAdapter extends RecyclerView.Adapter<RadioListAdapter.RadioViewHolder> {

    private OnItemClickListener clickListener = null;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public static class RadioViewHolder extends RecyclerView.ViewHolder {
        public ImageView radioImage;
        public TextView radioName;
        public ImageView favoriteImage;

        public RadioViewHolder(View view) {
            super(view);
            radioImage = (ImageView)view.findViewById(R.id.radioImage);
            radioName  = (TextView)view.findViewById(R.id.radioName);
            favoriteImage = (ImageView)view.findViewById(R.id.favoriteImage);
        }
    }

    private List<RadioInfo> data;

    public RadioListAdapter(List<RadioInfo> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public RadioInfo getItem(int position) { return data.get(position); }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radio_grid_item, viewGroup, false);
        RadioViewHolder vh = new RadioViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RadioViewHolder viewHolder, int i) {
        final int position = i;
        RadioInfo info = data.get(i);
        viewHolder.radioImage.setImageResource(info.getIcon());
        viewHolder.radioName.setText(info.getName());
        if(!info.isFavorite())
            viewHolder.favoriteImage.setVisibility(View.INVISIBLE);
        else
            viewHolder.favoriteImage.setVisibility(View.VISIBLE);

        viewHolder.radioImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener != null)
                    clickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
