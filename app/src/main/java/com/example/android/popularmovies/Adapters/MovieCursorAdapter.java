package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Data.MovieDataBaseContract.MovieDatabase;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

public class MovieCursorAdapter extends RecyclerView.Adapter<MovieCursorAdapter.MovieViewHolder> {

    private Cursor moviesCursor;
    private Context mContext;

    public MovieCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fav_layout_item, parent, false);

        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        int idIndex = moviesCursor.getColumnIndex(MovieDatabase._ID);
        int movieTitleIndex = moviesCursor.getColumnIndex(MovieDatabase.COLUMN_MOVIE_TITLE);
        int releaseIndex = moviesCursor.getColumnIndex(MovieDatabase.COLUMN_MOVIE_RELEASE);
        int ratingIndex = moviesCursor.getColumnIndex(MovieDatabase.COLUMN_MOVIE_RATING);
        int plotIndex = moviesCursor.getColumnIndex(MovieDatabase.COLUMN_MOVIE_PLOT);
        int moviePosterIndex = moviesCursor.getColumnIndex(MovieDatabase.COLUMN_MOVIE_IMAGE);

        moviesCursor.moveToPosition(position);


        final int id = moviesCursor.getInt(idIndex);
        String title = moviesCursor.getString(movieTitleIndex);
        String releaseDate = moviesCursor.getString(releaseIndex);
        String rating = moviesCursor.getString(ratingIndex);
        String plot = moviesCursor.getString(plotIndex);
        String image = moviesCursor.getString(moviePosterIndex);

        holder.itemView.setTag(id);
        holder.tittleThumbnail.setText(title);
        holder.releaseThumbnail.setText(releaseDate);
        holder.ratingThumbnail.setText(rating);
        holder.plotThumbnail.setText(plot);
        holder.moviePoster.setTag(holder);

        Picasso.with(mContext)
                .load(image)
                .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        if (moviesCursor == null) {
            return 0;
        }
        return moviesCursor.getCount();
    }

    public Cursor swapMovieCursor(Cursor c) {

        if (moviesCursor == c) {
            return null;
        }
        Cursor temp = moviesCursor;
        this.moviesCursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView tittleThumbnail;
        TextView releaseThumbnail;
        TextView ratingThumbnail;
        TextView plotThumbnail;
        ImageView moviePoster;


        public MovieViewHolder(View itemView) {
            super(itemView);

            tittleThumbnail = (TextView) itemView.findViewById(R.id.titleViewThumbnail);
            releaseThumbnail = (TextView) itemView.findViewById(R.id.releaseViewThumbnail);
            ratingThumbnail = (TextView) itemView.findViewById(R.id.ratingViewThumbnail);
            plotThumbnail = (TextView) itemView.findViewById(R.id.plotThumbnail);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_thumbnail);
        }
    }
}
