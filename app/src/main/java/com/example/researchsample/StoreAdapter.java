package com.example.researchsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.signature.ObjectKey;
import com.example.researchsample.R;


/**
 * Created by ravi on 16/11/17.
 */


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
    private Context context;
    private List<SampleList> movieList;
    private MovieAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onMovieSelected(movieList.get(getAdapterPosition()));
                }
            });
        }
    }


    public StoreAdapter(Context context, List<SampleList> movieList, MovieAdapterListener listener) {
        this.context = context;
        this.movieList = movieList;
        //this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SampleList movie = movieList.get(position);
        holder.name.setText(movie.getName());
        holder.price.setText(movie.getDevelopedBy());
           /* holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mIntent = new Intent(context, ContactSearchActivity.class);
                    Toast.makeText(context,movieList.get(position).getPrice().toString(),Toast.LENGTH_SHORT).show();
                    mIntent.putExtra("menu",movieList.get(position).getPrice());
                    context.startActivity(mIntent);

                }
            });*/
        Glide.with(context)
                .load("http://minagazi.com/shamimsproject/app/upload/"+movie.getImageUrl())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface MovieAdapterListener {
        void onMovieSelected(SampleList movie);
    }


}
