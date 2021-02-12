package com.Basic_Flicks.Flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.Basic_Flicks.Flixster.Adapters.MovieAdapter;
import com.Basic_Flicks.Flixster.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        // creating a new adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        // tie the adapter on a recycler view
        rvMovies.setAdapter(movieAdapter);
        // setting a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        //tweak layout
        //rvMovies.setBackgroundColor(0xff000000);
        rvMovies.setBackgroundColor(0xff000000);
        //AsyncHttpClient control
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(null, "Movies count is: " + movies.size());
                } catch (JSONException e) {
                    Log.i(null, "check json", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(null, "Failure at JsonHttpResponseHandler");
            }
        });
    }
}
