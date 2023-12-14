package com.example.mp_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_project.databinding.MovieItemBinding;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<Movie> item_list = new ArrayList<Movie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie item = item_list.get(position);
        viewHolder.setItem(item);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public void addItem(Movie item) {
        item_list.add(item);
    }

    public void setItems(ArrayList<Movie> items) {
        this.item_list = items;
    }

    public Movie getItem(int position) {
        return item_list.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView useTitle;
        TextView aplcName;
        TextView prodYear;
        TextView direName;
        TextView gradeName;

        public ViewHolder(View itemView) {
            super(itemView);

            useTitle = itemView.findViewById(R.id.useTitle);
            aplcName = itemView.findViewById(R.id.aplcName);
            prodYear = itemView.findViewById(R.id.prodYear);
            direName = itemView.findViewById(R.id.direName);
            gradeName = itemView.findViewById(R.id.gradeName);
        }

        public void setItem(Movie item) {
            useTitle.setText(item.useTitle);
            // Log.d("영화명 : ", useTitle.getText().toString());
            aplcName.setText(item.aplcName);
            prodYear.setText(item.prodYear);
            direName.setText(item.direName);
            gradeName.setText(item.gradeName);
        }

    }

}