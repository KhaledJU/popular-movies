package com.example.movies.VideoAPI;
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
import com.example.movies.VideoAPI.Video;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.movies.Details.path_base;


public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {

    private List<Video> videos;
    private Context context;

    public Video_Adapter(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public Video_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Video_Adapter.ViewHolder holder, final int position) {
        final Video video = videos.get(position);

        holder.videoName.setText(video.getName());

        holder.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://"+video.getKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView videoName;
        LinearLayout videoLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.vid_name);
            videoLayout = itemView.findViewById(R.id.video_layout);
        }
    }
}
