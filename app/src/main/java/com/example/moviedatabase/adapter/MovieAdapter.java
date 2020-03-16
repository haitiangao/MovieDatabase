package com.example.moviedatabase.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviedatabase.R;
import com.example.moviedatabase.model.Movie;
import com.example.moviedatabase.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private UserClickListener userClickListener;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movieList, UserClickListener userClickListener){
        this.context = context;
        this.movieList = movieList;
        this.userClickListener = userClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        holder.movieTitleView.setText(movieList.get(position).getTitle());
        holder.overView.setText(movieList.get(position).getOverview());
        holder.ratingView.setText(movieList.get(position).getVoteAverage().toString());


        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.PREFERENCE_KEY,Context.MODE_PRIVATE);
        String connection = sharedPreferences.getString(Constants.PREFERENCE_KEY,"Disconnected");

        if (connection== "Connected"){
            RequestOptions myOptions = new RequestOptions()
                    .override(400, 400)
                    .fitCenter();


            Glide.with(context)
                    .asBitmap()
                    .apply(myOptions)
                    .load(Constants.BASE_POSTER+movieList.get(position).getPosterPath())
                    .into(holder.movieImage);
        }
        else
        {
            holder.movieImage.setImageResource(R.drawable.ic_movie_red_40dp);
        }


        holder.itemView.setOnClickListener(view ->
                userClickListener.openMovie(movieList.get(position)));

    }

    @Override
    public int getItemCount()
    {
        return movieList.size();
    }

    public interface UserClickListener {
        void openMovie(Movie movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movieTitleView)
        TextView movieTitleView;
        @BindView(R.id.overView)
        TextView overView;
        @BindView(R.id.ratingView)
        TextView ratingView;
        @BindView(R.id.movieImage)
        ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
