package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.tmbd_safwane.models.Serie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 21/11/2017.
 */

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.CustomViewHolder> {

    private List<Serie> mListSerie = new ArrayList<>();
    private Context mContext;

    public SerieAdapter(List<Serie> listSerie, Context context) {
        mListSerie = listSerie;
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
        Serie serie = mListSerie.get(position);
        String title = serie.getName();
        String overview = serie.getOverview();
        String poster = serie.getPosterPath();
        holder.bind(serie, title, overview, poster);
    }

    @Override
    public int getItemCount() {
        return mListSerie.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView listSerieTitle;
        TextView listSerieOverview;
        ImageView listSeriePoster;

        public CustomViewHolder(View itemView) {
            super(itemView);
            listSerieTitle =  itemView.findViewById(R.id.movie_title);
            listSerieOverview = itemView.findViewById(R.id.movie_overview);
            listSeriePoster = itemView.findViewById(R.id.movie_poster);

        }

        void bind(final Serie serie, final String title, final String overview, final String poster) {

            listSerieTitle.setText(title);
            listSerieOverview.setText(overview);
            Picasso
                    .with(mContext)
                    .load("http://image.tmdb.org/t/p/w500"+poster)
                    .into(listSeriePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "You clicked on : " + title, Toast.LENGTH_SHORT).show();
                    Intent detailsIntent = new Intent(mContext, SerieActivity.class);
                    detailsIntent.putExtra("serie", serie);
                    mContext.startActivity(detailsIntent);
                }
            });
        }
    }
}
