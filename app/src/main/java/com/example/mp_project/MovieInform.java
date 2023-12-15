package com.example.mp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;

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
        int useTitleSize = 16;
        int oriTitleSize = 14;
        if(movie.useTitle.length() > 6 || movie.direName.length() > 9 || movie.direName.length() > 9) {
            if(movie.useTitle.length() > 14) {
                useTitleSize -= 2;
                oriTitleSize -= 2;
            }

            binding.useTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, useTitleSize);
            binding.oriTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, oriTitleSize);
            binding.direName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            binding.aplcName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        }

        binding.useTitle.setText(movie.useTitle);
        binding.oriTitle.setText(movie.oriTitle);
        binding.direName.setText(movie.direName);
        binding.aplcName.setText(movie.aplcName);
        binding.gradeName.setText(movie.gradeName);
        binding.screTime.setText(movie.screTime);
        binding.workCont.setText(movie.workCont);
    }

}