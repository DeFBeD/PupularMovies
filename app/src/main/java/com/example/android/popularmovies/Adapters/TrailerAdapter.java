package com.example.android.popularmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Trailers;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<Trailers> tData;
    private LayoutInflater tLayoutInflater;
    private Context tContext;

    public TrailerAdapter(Context context) {
        this.tContext = context;
        this.tLayoutInflater = LayoutInflater.from(context);
        this.tData = new ArrayList<>();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = tLayoutInflater.inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerAdapter.TrailerViewHolder holder, int position) {

        final Trailers trailers = tData.get(position);

        String trailerName = trailers.getTrailerName();

        final String id = trailers.getTrailerKey();

        holder.trailerName.setText(trailerName);


// This is how we use Picasso to load images from the internet.
        Picasso.with(tContext)
                .load("https://img.youtube.com/vi/" + id +"/hqdefault.jpg")
                .placeholder(R.color.colorAccent)
                .into(holder.playButton);

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //holder.id.setText(trailers.getTrailerKey());

                String url = "https://www.youtube.com/watch?v=" + id;

                Uri webPage = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

                tContext.startActivity(intent);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idForTrailer = id;
                shareText(trailers, idForTrailer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (tData == null) ? 0 : tData.size();
    }

    public void setTrailersList(List<Trailers> trailerList) {
        this.tData.clear();
        this.tData.addAll(trailerList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        TextView trailerName;
        ImageView playButton;
        ImageButton shareButton;


        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_title);
            playButton = itemView.findViewById(R.id.play_button);
            shareButton = itemView.findViewById(R.id.share_button);
        }
    }

    private void shareText(Trailers trailers, String idTrailer) {


        String url = "https://www.youtube.com/watch?v=" + idTrailer;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, trailers.getTrailerName());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, trailers.getTrailerName() + ": "
                + url);
        tContext.startActivity(sharingIntent);
    }
}