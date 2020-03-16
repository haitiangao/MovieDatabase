package com.example.moviedatabase.network;

import com.example.moviedatabase.model.Movie;
import com.example.moviedatabase.model.Responses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    // https://api.themoviedb.org/3/search/movie?api_key=<<api_key>>&language=en-US&page=1&include_adult=false
    // public Call<List<GitResult>> getRepo(@Path("userName") String userName); //@Query("apiKey") String apiKey);


    // https://api.themoviedb.org/3/search/movie?&language=en-US&page=1&include_adult=false&api_key=<<api_key>>&query=<<query>>

    @GET("/3/search/movie?language=en-US&page=1&include_adult=false")
    Call<Responses> getMovie(@Query("api_key") String api_key ,
                             @Query("query") String query);



}
