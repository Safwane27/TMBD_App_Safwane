package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.tmbd_safwane.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 21/11/2017.
 */

public class MovieAdapterGrid extends RecyclerView.Adapter<MovieAdapterGrid.CustomViewHolder> {

    private List<Movie> mListMovie = new ArrayList<>();
    private Context mContext;

    public MovieAdapterGrid(List<Movie> listMovie, Context context) {
        mListMovie = listMovie;
        mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_grid_item, viewGroup, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Movie movie = mListMovie.get(position);
        String poster = movie.getPosterPath();
        holder.bind(movie, poster);
    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView listMoviePoster;

        public CustomViewHolder(View itemView) {
            super(itemView);
            listMoviePoster = itemView.findViewById(R.id.movie_poster);

        }

        void bind(final Movie movie, final String poster) {
            Picasso
                    .with(mContext)
                    .load("http://image.tmdb.org/t/p/w500"+poster)
                    .into(listMoviePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "You clicked on : " + title, Toast.LENGTH_SHORT).show();
                    Intent detailsIntent = new Intent(mContext, MovieActivity.class);
                    detailsIntent.putExtra("movie", movie);
                    mContext.startActivity(detailsIntent);
                }
            });
        }
    }
}
