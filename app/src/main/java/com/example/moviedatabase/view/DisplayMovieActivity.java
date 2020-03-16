package com.example.moviedatabase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviedatabase.R;
import com.example.moviedatabase.model.Movie;
import com.example.moviedatabase.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayMovieActivity extends AppCompatActivity {

    private Movie movie;
    @BindView(R.id.languageViewInner)
    TextView languageViewInner;
    @BindView(R.id.moviePosterInner)
    ImageView moviePosterInner;
    @BindView(R.id.overViewInner)
    TextView overViewInner;
    @BindView(R.id.ratingViewInner)
    TextView ratingViewInner;
    @BindView(R.id.titleViewInner)
    TextView titleViewInner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
        ButterKnife.bind(this);

        Bundle data= getIntent().getExtras();
        movie =data.getParcelable(Constants.MOVIE_KEY);

        languageViewInner.setText("Language: "+movie.getOriginalLanguage());
        ratingViewInner.setText(movie.getVoteAverage().toString()+"/10");
        overViewInner.setText(movie.getOverview());
        titleViewInner.setText(movie.getTitle());


        SharedPreferences sharedPreferences =
                this.getSharedPreferences(Constants.PREFERENCE_KEY, MODE_PRIVATE);
        String connection = sharedPreferences.getString(Constants.PREFERENCE_KEY,"Disconnected");
        Log.d("TAG_H", "Connectivity: "+ connection);

        if (connection.equals("Connected")) {
            Log.d("TAG_H", "Connectivity: "+ connection);

            RequestOptions myOptions = new RequestOptions()
                    .fitCenter();

            Glide.with(this)
                    .asBitmap()
                    .apply(myOptions)
                    .load(Constants.BASE_POSTER + movie.getPosterPath())
                    .into(moviePosterInner);
        }
        else
        {
            moviePosterInner.setImageResource(R.drawable.ic_movie_red_40dp);
        }



    }

    public void backToPrevious(View view){
        finish();
    }



}
