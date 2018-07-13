package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.Activities.DetailActivity;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.Activities.DetailActivity.EXTRA_MOVIE;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MoviesAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType)
    {
        View view = mInflater.inflate(R.layout.movie_image, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(EXTRA_MOVIE,  mMovieList.get(position));
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        Movie movie = mMovieList.get(position);

        // This is how we use Picasso to load images from the internet.
        Picasso.with(mContext)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList)
    {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}

