package com.example.mp_project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<ResponseMovie> items = new ArrayList<ResponseMovie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ResponseMovie item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ResponseMovie item) {
        items.add(item);
    }

    public void setItems(ArrayList<ResponseMovie> items) {
        this.items = items;
    }

    public ResponseMovie getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);

            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(ResponseMovie item) {
            /*
            textView.setText(item.movieNm);
            Log.d("영화명 : ", textView.getText().toString());
            textView2.setText(item.audiCnt + " 명");
             */
        }

    }

}