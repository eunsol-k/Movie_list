package com.example.mp_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mp_project.databinding.SearchMovieBinding;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SearchMovie extends AppCompatActivity {

    private SearchMovieBinding binding;
    private Context context;
    static RequestQueue requestQueue;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_movie);

        initBinding();
        initalize();
        sendMessage();

    }

    // 바인딩 설정
    private void initBinding() {
        binding = SearchMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    // 초기화
    private void initalize() {
        context = SearchMovie.this;
    }

    // 검색값 전달
    private void sendMessage() {
        binding.editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            View view = this.getCurrentFocus();
            if(i == EditorInfo.IME_ACTION_SEARCH) {
                makeRequest();
                binding.editText.clearFocus();
                if(view != null) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
            return true;
        });
    }


    public void makeRequest() {
        String search = binding.editText.getText().toString();

        binding.searchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        StringRequest request = new StringRequest(
                Request.Method.GET,
                "", // url
                response -> {
                    println("응답 > " + response);
                    processResponse(response);
                }, // 응답 시의 동작 || actions when response
                error -> {
                    println("에러 발생 > " + error);
                } // 적절한 에러처리(정답은 따로 없음, 로그출력용) || error handling, freely make it!
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄.");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        /*
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
         */
    }

    public void println(String data) {
        Log.d("MainActivity", data);
    }



    public void processResponse(String response) {
        /*
        Gson gson = new Gson(); // 변수 선언 || variable declaration
        MovieList movieList = gson.fromJson(response, MovieList.class); // 응답받은 JSON 문자열을 특정 타입의 클래스로 변환 || transforming specific class type from received JSON String

        println("영화정보의 수 : " + movieList.boxOfficeResult.dailyBoxOfficeList.size());

        for (int i = 0; i < movieList.boxOfficeResult.dailyBoxOfficeList.size(); i++) {
            Movie movie = movieList.boxOfficeResult.dailyBoxOfficeList.get(i);

            adapter.addItem(movie);
        }

        adapter.notifyDataSetChanged();
        */
    }
}