package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapters.FilmListAdapter;
import com.example.movieapp.Domain.OMDBResponse;
import com.example.movieapp.R;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.net.URL;

public class ListFilmsActivity extends AppCompatActivity {
    RecyclerView.Adapter adapterBestMovies;
    private RecyclerView recyclerViewBestMovies;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar loading;
    protected  String rech;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        rech=intent.getStringExtra("search");
        setContentView(R.layout.activity_list_films);
        initView();
        sendRequest();



    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://www.omdbapi.com/?apikey=b43975eb&s="+rech, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading.setVisibility(View.GONE);
                OMDBResponse omdbResponse = gson.fromJson(response, OMDBResponse.class);
                adapterBestMovies = new FilmListAdapter(omdbResponse);
                recyclerViewBestMovies.setAdapter(adapterBestMovies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                Log.d("Uilover", "OneErrorResopnse:" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        recyclerViewBestMovies = findViewById(R.id.recyclerfilmlist);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this));
        loading = findViewById(R.id.progressBar5);
        back=findViewById(R.id.backbtn);
    }
}
