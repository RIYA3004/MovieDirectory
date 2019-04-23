package com.example.moviedirectory.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.example.moviedirectory.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {
    private Movie movie;
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieYear;

    private RequestQueue queue;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        queue= Volley.newRequestQueue(this);
        movie=(Movie) getIntent().getSerializableExtra("movie");
        movieId=movie.getImdbId();
        setUpUI();
        getMovieDetails(movieId);

    }

    private void getMovieDetails(String id) {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                Constants.URL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.has("Ratings")){
                        JSONArray ratings=response.getJSONArray("Ratings");
                        String source=null;
                        String value=null;
                        if(ratings.length()>0){
                            JSONObject mRatings=ratings.getJSONObject(ratings.length()-1);
                            source=mRatings.getString("Source");
                            value=mRatings.getString("Value");
                        }
                    }
                    movieTitle.setText(response.getString("Title"));
                    movieYear.setText("Released: "+response.getString("Released"));
                    Picasso.get().load(response.getString("Poster")).into(movieImage);

                }
                catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:",error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void setUpUI() {
        movieTitle=(TextView) findViewById(R.id.movieTitleIDDets);
        movieImage=(ImageView) findViewById(R.id.movieImageIDDets);
        movieYear=(TextView) findViewById(R.id.movieReleaseIDDets);

    }
}
