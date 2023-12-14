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
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mp_project.databinding.SearchMovieBinding;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SearchMovie extends AppCompatActivity {
    private final String URL = BuildConfig.MOVIE_SERVER +
            "?ServiceKey=" +
            BuildConfig.API_KEY +
            "&pageNo=1&numOfRows=10";
    private SearchMovieBinding binding;
    private Context context;
    private int selected = 0;
    static RequestQueue requestQueue;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_movie);

        initBinding();
        initialize();
        sendMessage();

    }

    // 바인딩 설정
    private void initBinding() {
        binding = SearchMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    // 초기화
    private void initialize() {
        context = SearchMovie.this;

        // 드롭다운 동작 정의
        String[] spin_list = {"제목명","배급사명"};
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spin_list
        );

        spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.searchSp.setAdapter(spin_adapter);

        binding.searchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 선택값 없음
            }
        });

    }

    // 검색값 전달
    private void sendMessage() {
        binding.editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            View view = this.getCurrentFocus();
            if(i == EditorInfo.IME_ACTION_DONE) {
                sendRequest(selected, binding.editText.getText().toString());
                binding.editText.clearFocus();
                if(view != null) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
            return true;
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        recycleView();
    }

    public void sendRequest(int position, String search_value) {
        println("search_value : " + search_value);
        String request_url = URL;
        if (position == 0) {
            request_url += "&title=" + search_value;
        } else {
            request_url += "&aplcName=" + search_value;
        }

        println("request_url : " + request_url);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                request_url,
                response -> {
                    println("응답 > " + response);
                    processResponse(response);
                },
                error -> {
                    println("응답 시 에러 > " + error);
                }
        ) {
            // response data UTF-8 변환
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void recycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    public void processResponse(String response) {
        adapter.removeAll();

        ArrayList<Movie> movies = new ArrayList<>();
        movies = xmlParser(response);

        for (int i = 0; i < movies.size(); i++) {
            adapter.addItem(movies.get(i));
        }

        adapter.notifyDataSetChanged();
    }

    public ArrayList<Movie> xmlParser(String xmlStr) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource xml = new InputSource(new StringReader(xmlStr));
            Document xmlDoc = builder.parse(xml);
            xmlDoc.getDocumentElement().normalize();

            NodeList itemTagList = xmlDoc.getElementsByTagName("item");
            for (int i = 0; i < itemTagList.getLength(); i++) {
                Movie movie = new Movie();
                NodeList childNodes = itemTagList.item(i).getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if ("aplcName".equals(childNodes.item(j).getNodeName())) {
                        movie.aplcName = childNodes.item(j).getTextContent();
                    }
                    if ("coreHarmRsn".equals(childNodes.item(j).getNodeName())) {
                        movie.coreHarmRsn = childNodes.item(j).getTextContent();
                    }
                    if ("direName".equals(childNodes.item(j).getNodeName())) {
                        movie.direName = childNodes.item(j).getTextContent();
                    }
                    if ("gradeName".equals(childNodes.item(j).getNodeName())) {
                        movie.gradeName = childNodes.item(j).getTextContent();
                    }
                    if ("leadaName".equals(childNodes.item(j).getNodeName())) {
                        movie.leadaName = childNodes.item(j).getTextContent();
                    }
                    if ("mvAssoName".equals(childNodes.item(j).getNodeName())) {
                        movie.mvAssoName = childNodes.item(j).getTextContent();
                    }
                    if ("oriTitle".equals(childNodes.item(j).getNodeName())) {
                        movie.oriTitle = childNodes.item(j).getTextContent();
                    }
                    if ("prodYear".equals(childNodes.item(j).getNodeName())) {
                        movie.prodYear = childNodes.item(j).getTextContent();
                    }
                    if ("rtDate".equals(childNodes.item(j).getNodeName())) {
                        movie.rtDate = childNodes.item(j).getTextContent();
                    }
                    if ("rtNo".equals(childNodes.item(j).getNodeName())) {
                        movie.rtNo = childNodes.item(j).getTextContent();
                    }
                    if ("screTime".equals(childNodes.item(j).getNodeName())) {
                        movie.screTime = childNodes.item(j).getTextContent();
                    }
                    if ("useTitle".equals(childNodes.item(j).getNodeName())) {
                        movie.useTitle = childNodes.item(j).getTextContent();
                    }
                    if ("workCont".equals(childNodes.item(j).getNodeName())) {
                        movie.workCont = childNodes.item(j).getTextContent();
                    }
                }
                movies.add(movie);
            }

        } catch (ParserConfigurationException e) {
            println("XML 파싱 에러 > " + e);
        } catch (IOException e) {
            println("XML 파싱 에러 > " + e);
        } catch (SAXException e) {
            println("XML 파싱 에러 > " + e);
        }

        return movies;
    }

    public void println(String data) {
        Log.d("SerachMovie: ", data);
    }
}