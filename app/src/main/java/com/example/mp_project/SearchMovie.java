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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mp_project.databinding.SearchMovieBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchMovie extends AppCompatActivity {
    private final String URL = BuildConfig.MOVIE_SERVER +
            "?ServiceKey=" +
            BuildConfig.API_KEY +
            "&pageNo=1&numOfRows=10";
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
                makeRequest(binding.editText.toString());
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


    public void makeRequest(String search_value) {
        String[] spin_list = {"제목명","배급사명"};

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spin_list);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.searchSp.setAdapter(spinAdapter);

        binding.searchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (search_value != null) {
                    sendRequest(position, search_value);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 선택값 없음
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        recycleView();
    }

    public void sendRequest(int position, String search_value) {
        String request_url = URL;
        if (position == 0) {
            request_url += "&title=" + search_value;
        } else {
            request_url += "&aplcName=" + search_value;
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                response -> {
                    println("응답 > " + response);
                    try {
                        processResponse(response);
                    } catch (JSONException e) {
                        println("XML-JSON 변환 에러 > " + e);
                    }
                },
                error -> {
                    println("응답 시 에러 > " + error);
                }
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
    }

    public void recycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    public void processResponse(String response) throws JSONException {
        JSONObject json = XML.toJSONObject(response);
        String jsonStr = json.toString();

        println(jsonStr);

        Gson gson = new Gson();
        Response responseValue = gson.fromJson(response, Response.class);

        for (int i = 0; i < Integer.parseInt(responseValue.body.numOfRows); i++) {
            Movie movie = responseValue.body.items.item.get(i);
            adapter.addItem(movie);
        }

        adapter.notifyDataSetChanged();
    }

    public void println(String data) {
        Log.d("SerachMovie: ", data);
    }
}