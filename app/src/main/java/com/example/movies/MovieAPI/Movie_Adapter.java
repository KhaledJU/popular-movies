package com.example.movies.MovieAPI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Details;
import com.example.movies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.movies.Details.path_base;

public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;

    public Movie_Adapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public Movie_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Movie_Adapter.ViewHolder holder, final int position) {
        final Movie movie = movies.get(position);

        holder.movieTitle.setVisibility(View.GONE);
        holder.posterImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(path_base + movie.getPoster_path())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.posterImg, new Callback() {
                            @Override
                            public void onSuccess() {
                                //Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                holder.movieTitle.setText(movie.getTitle());
                                holder.movieTitle.setVisibility(View.VISIBLE);
                                //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }, 1200);


        holder.posterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("Movie",movie);
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImg;
        TextView movieTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImg = itemView.findViewById(R.id.poster_img);
            movieTitle = itemView.findViewById(R.id.movie_title);
        }
    }
}
