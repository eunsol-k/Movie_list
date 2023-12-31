package com.example.mp_project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_project.databinding.MovieItemBinding;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<Movie> items = new ArrayList<Movie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Movie item) {
        items.add(item);
    }

    public void setItems(ArrayList<Movie> items) {
        this.items = items;
    }

    public void removeAll() {
        items.clear();
    }

    public Movie getItem(int position) {
        return items.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView useTitle;
        TextView oriTitle;
        TextView aplcName;
        TextView prodYear;
        TextView direName;
        TextView gradeName;

        public ViewHolder(View itemView) {
            super(itemView);

            useTitle = itemView.findViewById(R.id.useTitle);
            oriTitle = itemView.findViewById(R.id.oriTitle);
            aplcName = itemView.findViewById(R.id.aplcName);
            prodYear = itemView.findViewById(R.id.prodYear);
            direName = itemView.findViewById(R.id.direName);
            gradeName = itemView.findViewById(R.id.gradeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Movie movie = items.get(getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), MovieInform.class);

                    intent.putExtra("Movie", movie);
                    context.startActivity(intent);

                    Log.d("RecyclerView", "position = " + getAdapterPosition());
                }
            });
        }

        public void setItem(Movie item) {
            String title = item.useTitle;
            String aplc = item.aplcName;
            int index = 6;
            String grade;

            if (title.length() > 5) {
                if (!title.substring(0, index).contains(",") & !title.substring(0, index).contains(" ")) {
                    index -= 1;
                }
                title = title.substring(0, index) + "…";
            }
            if(aplc.length() > 20)
                aplc = aplc.substring(0, 21) + "…";

            useTitle.setText(title);
            oriTitle.setText(item.oriTitle);
            aplcName.setText(aplc);
            prodYear.setText(item.prodYear);
            direName.setText(item.direName);

            if(item.gradeName.contains("청소년")) {
                grade = "19세";
            } else if(item.gradeName.contains("15")) {
                grade = "15세";
            } else if(item.gradeName.contains("12")) {
                grade = "12세";
            } else if(item.gradeName.contains("17")) {
                grade = "7세";
            } else {
                grade = "전체";
            }

            gradeName.setText(grade);
        }

    }

}