package com.Basic_Flicks.Flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Basic_Flicks.Flixster.Models.Movie;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvOverview;
    ImageView ivPoster;
    RelativeLayout container;
    RatingBar tvRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview =  findViewById(R.id.overview);
        ivPoster =  findViewById(R.id.ivPoster);
        container = findViewById(R.id.container);
        tvRate = findViewById(R.id.ratingBar);


        Movie movie= Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float temp = (float) movie.getRating();
       temp=((temp)/0.1f)*.05f;
        tvRate.setRating(temp) ;



    }
}