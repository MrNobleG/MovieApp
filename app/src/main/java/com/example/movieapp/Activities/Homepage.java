package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.movieapp.Adapters.SliderAdapters;
import com.example.movieapp.Domain.SliderItems;
import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
protected ViewPager2 viewPager2;
protected Handler slideHandler =new Handler();
private RecyclerView.Adapter adapterBestMovie,AdapterUpComing,adapterCategory;
private RecyclerView recyclerViewBestMovies,recyclerviewUpcoming,recyclerviewCategory;
private RequestQueue mRequestQueue;
private StringRequest mStringRequest,mStringRequest2,mStringRequest3;
private ProgressBar loading1,loading2,loading3;

private LinearLayout favoritebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
        banners();
        ImageView searchbt=findViewById(R.id.searchbutton);
        EditText textv= findViewById(R.id.textView11);



        searchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, ListFilmsActivity.class);
                String forSearch = textv.getText().toString();
                if (!forSearch.isEmpty()) {
                    intent.putExtra("search", forSearch);
                    startActivity(intent);


                }
                else{
                    Toast.makeText(Homepage.this, "Please enter a search term", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void banners() {
        List<SliderItems> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.damselposter2));
        sliderItems.add(new SliderItems(R.drawable.duneparttwodolbyposter));
        sliderItems.add(new SliderItems(R.drawable.oppenheimer));

        viewPager2.setAdapter(new SliderAdapters(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);


        CompositePageTransformer compositePageTransformer =new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
            }
        });
    }
    private Runnable slideRunnable= new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable,100);
    }

    @Override
        protected void onPause() {
            super.onPause();
            slideHandler.removeCallbacks(slideRunnable);
        }

    private void  initView(){
        viewPager2=findViewById(R.id.viewpagerSlider);

        recyclerViewBestMovies=findViewById(R.id.view1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        recyclerviewCategory=findViewById(R.id.view2);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        recyclerviewUpcoming=findViewById(R.id.view3);
        recyclerviewUpcoming.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        loading1=findViewById(R.id.progressBar);
        loading2=findViewById(R.id.progressBar2);
        loading3=findViewById(R.id.progressBar3);

        favoritebtn=findViewById(R.id.favoriteicon);

        favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfav =new Intent( Homepage.this, ListFavoriteFilms.class);
                startActivity(intentfav);

            }
        });


    }
}