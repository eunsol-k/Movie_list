package com.example.mp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mp_project.databinding.MovieInformBinding;
import com.example.mp_project.databinding.SearchMovieBinding;

import java.io.Serializable;

public class MovieInform extends AppCompatActivity {
    Movie movie;
    private MovieInformBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_inform);
        context = MovieInform.this;

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("Movie");

        initBinding();
        setView();
    }

    private void initBinding() {
        binding = MovieInformBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setView() {
        binding.useTitle.setText(movie.useTitle);
        binding.oriTitle.setText(movie.oriTitle);
        binding.direName.setText(movie.direName);
        binding.aplcName.setText(movie.aplcName);
        binding.gradeName.setText(movie.gradeName);
        binding.screTime.setText(movie.screTime);
        binding.workCont.setText(movie.workCont);
    }

}