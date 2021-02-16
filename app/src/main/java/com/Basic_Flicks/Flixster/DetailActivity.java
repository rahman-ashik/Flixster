package com.Basic_Flicks.Flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Basic_Flicks.Flixster.Models.Movie;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    Context context2;
    private static final String YOUTUBE_API= "AIzaSyCxbyCuDTNME_6O4l_NBse-cpfors4eyOc";
    private static final String VID_LINK= "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    TextView tvTitle;
    TextView tvOverview;
    ImageView ivPoster;
    RelativeLayout container;
    RatingBar tvRate;
    YouTubePlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview =  findViewById(R.id.overview);
        ivPoster =  findViewById(R.id.ivPoster);
        container = findViewById(R.id.container);
        tvRate = findViewById(R.id.ratingBar);
        player= findViewById(R.id.player);

        Movie movie= Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float temp = (float) movie.getRating();
       temp=((temp)/0.1f)*.05f;
        tvRate.setRating(temp) ;




        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VID_LINK, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");

                    if(results.length()==0) { player.setVisibility(View.GONE); return; }

                    String vidKey = results.getJSONObject(0).getString("key");

                    Log.e("flag", vidKey);

                    startPlayer(vidKey);


                } catch (JSONException e) {
                    Log.e(null, "failed to parse JSon");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
            }
        });




    }

    private void startPlayer(final String vidKey) {
        player.initialize(YOUTUBE_API, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(vidKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

}