package com.example.giboon_ver3;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class MainAdapter  extends RecyclerView.Adapter<MainAdapter.GalleryViewHolder> {
    private ArrayList<String> mDataset;
    private FragmentCampaign activity;

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public MainAdapter(FragmentCampaign activity, ArrayList<String> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
        System.out.println("myDataset" + myDataset);
    }

    @NonNull
    @Override
    public MainAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textView = cardView.findViewById(R.id.textView);
        System.out.println(mDataset.get(position));
        System.out.println(mDataset.size());
        textView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}