package com.example.movies.ReviewAPI;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movies.R;
import com.example.movies.ReviewAPI.Review;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.movies.Details.path_base;


public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ViewHolder> {

    private List<Review> reviews;
    private Context context;
    private int hight;

    public Review_Adapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public Review_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Review_Adapter.ViewHolder holder, final int position) {
        final Review review = reviews.get(position);

        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams textParam = (LinearLayout.LayoutParams) holder.content.getLayoutParams();
                if(textParam.height != ViewGroup.LayoutParams.WRAP_CONTENT){
                    hight = textParam.height;
                    textParam.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                else
                    textParam.height=hight;
                holder.content.setLayoutParams(textParam);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author_tv);
            content = itemView.findViewById(R.id.content_tv);
        }
    }
}
