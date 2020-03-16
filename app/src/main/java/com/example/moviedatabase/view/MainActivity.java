package com.example.moviedatabase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.moviedatabase.R;
import com.example.moviedatabase.adapter.MovieAdapter;
import com.example.moviedatabase.model.Movie;
import com.example.moviedatabase.model.Responses;
import com.example.moviedatabase.network.MovieRetrofitInstance;
import com.example.moviedatabase.receiver.InternetReciever;
import com.example.moviedatabase.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.UserClickListener {

    private MovieRetrofitInstance retrofitInstance = new MovieRetrofitInstance();
    private List<Movie> movieList = new ArrayList<Movie>();
    private InternetReciever internetReciever = new InternetReciever();


    @BindView(R.id.queryEditText)
    EditText queryEditText;
    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.movieRecycleView)
    RecyclerView movieRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        movieRecycleView.setLayoutManager(new LinearLayoutManager(this));
        movieRecycleView.setAdapter(new MovieAdapter(this, movieList, this));
        movieRecycleView.addItemDecoration(itemDecoration);

        registerReceiver(internetReciever,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void getQuery(View view) {
        String query = queryEditText.getText().toString().trim();
        getMovies(query);


    }

    private void getMovies(String query){
        Log.d("TAG_H", "getMovies called!");

        retrofitInstance.getMovie(Constants.API_KEY,query)
                .enqueue(new Callback<Responses>() {
                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {
                        Log.d("TAG_H", "onResponse called!");
                        setUpMovies(response.body().results);

                    }

                        @Override
                        public void onFailure(Call<Responses> call, Throwable t) {
                        Log.d("TAG_H", "Error on getMovie: " + t);

                    }
                    });

    }

    public void setUpMovies(List<Movie> movieList){
        MovieAdapter movieAdapter = new MovieAdapter(this, movieList,this);
        movieRecycleView.setAdapter(null);
        movieRecycleView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }



    @Override
    public void openMovie(Movie movie) {
        Intent openMovieIntent= new Intent(this, DisplayMovieActivity.class);
        openMovieIntent.putExtra(Constants.MOVIE_KEY, movie);
        startActivity(openMovieIntent);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(internetReciever);

    }

}
