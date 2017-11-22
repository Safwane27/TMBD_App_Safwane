package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.tmbd_safwane.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 21/11/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder> {

    private List<Movie> mListMovie = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(List<Movie> listMovie, Context context) {
        mListMovie = listMovie;
        mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String title = mListMovie.get(position).getTitle();
        String overview = mListMovie.get(position).getOverview();
        String poster = mListMovie.get(position).getPosterPath();
        holder.bind(title, overview, poster);
    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView listMovieTitle;
        TextView listMovieOverview;
        ImageView listMoviePoster;

        public CustomViewHolder(View itemView) {
            super(itemView);
            listMovieTitle =  itemView.findViewById(R.id.movie_title);
            listMovieOverview = itemView.findViewById(R.id.movie_overview);
            listMoviePoster = itemView.findViewById(R.id.movie_poster);

        }

        void bind(final String title, final String overview, final String poster) {
            listMovieTitle.setText(title);
            listMovieOverview.setText(overview);
            //listMovieOverview.setText(poster);
            Picasso
                    .with(mContext)
                    .load("http://image.tmdb.org/t/p/w500"+poster)
                    .into(listMoviePoster);

            listMovieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "You clicked on : " + title, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
