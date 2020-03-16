package com.example.moviedatabase.network;

import com.example.moviedatabase.model.Responses;
import com.example.moviedatabase.util.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRetrofitInstance {

    private MovieService movieService;

    public MovieRetrofitInstance(){
        movieService =createService(createRetrofit());
    }

    private MovieService createService(Retrofit retrofit) {
        return retrofit.create(MovieService.class);
    }

    private Retrofit createRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<Responses> getMovie(String api_key, String query) {

        return movieService.getMovie(api_key,query);
    }

}
