package com.example.movies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.MovieAPI.Movie;
import com.example.movies.ReviewAPI.Review;
import com.example.movies.ReviewAPI.ReviewResponse;
import com.example.movies.ReviewAPI.ReviewService;
import com.example.movies.ReviewAPI.Review_Adapter;
import com.example.movies.VideoAPI.Video;
import com.example.movies.VideoAPI.VideoResponse;
import com.example.movies.VideoAPI.VideoService;
import com.example.movies.VideoAPI.Video_Adapter;
import com.example.movies.favoritDB.Favorit;
import com.example.movies.favoritDB.FavoritDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.movies.MainActivity.api_key;
import static com.example.movies.MainActivity.base_url;
import static com.example.movies.R.color.colorPrimary;

public class Details extends AppCompatActivity {

    @BindView(R.id.year_tv)  TextView yearTv;
    @BindView(R.id.rate_tv)  TextView rateTv;
    @BindView(R.id.overview_tv)  TextView overviewTv;
    @BindView(R.id.add_fav)  Button addFavBt;

    @BindView(R.id.image_Iv)  ImageView posterIv;

    public static String path_base = "http://image.tmdb.org/t/p/w185";

    private Movie movie;
    private List<Video> videoList;
    private List<Review> reviewList;
    private RecyclerView videoRecyclerView;
    private RecyclerView.Adapter videoAdapter;
    private RecyclerView reviewRecyclerView;
    private RecyclerView.Adapter reviewAdapter;

    private FavoritDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("Movie");
        mDB = FavoritDatabase.getInstance(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(colorPrimary)));
        setTitle(movie.getTitle());

        ButterKnife.bind(this);

        yearTv.setText(movie.getRelease_date().substring(0,7));
        rateTv.setText(movie.getVote_average()+" / 10");
        overviewTv.setText(movie.getOverview());


        final LiveData<Favorit> temp = mDB.daoAccess().getFav(movie.getId());
        temp.observe(this, new Observer<Favorit>() {
            @Override
            public void onChanged(Favorit favorit) {
                if(favorit == null)
                    addFavBt.setText(R.string.add_to_favorit);
                else
                    addFavBt.setText(R.string.remove_favorit);
            }
        });

        addFavBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addFavBt.getText().toString().equals("Add to Favorit")){
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDB.daoAccess().insertFav(new Favorit(movie.getId()));
                        }
                    });
                    addFavBt.setText(R.string.remove_favorit);
                }
                else{
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDB.daoAccess().deleteFav(new Favorit(movie.getId()));
                        }
                    });
                    addFavBt.setText(R.string.add_to_favorit);
                }
            }
        });

        videoRecyclerView = findViewById(R.id.vides_recycle);
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        videoList = new ArrayList<Video>();
        reviewList = new ArrayList<Review>();

        reviewRecyclerView = findViewById(R.id.review_recycle);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewList = new ArrayList<Review>();


        Picasso.get()
                .load( path_base + movie.getPoster_path())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(posterIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        //Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        fetchVideos();
        fetchReviews();

    }
    private void fetchVideos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VideoService service  = retrofit.create(VideoService.class);
        service.get(movie.getId(), api_key).enqueue(new retrofit2.Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if(!response.isSuccessful()){
                    ResponseBody errorBody = response.errorBody();
                    try{
                        Log.d("RESPONCE ERROR",errorBody.string());
                    }catch (Exception error){
                        Log.d("RESPONCEERR",error.getLocalizedMessage());
                    }
                    return;
                }
                videoList=response.body().getVideoList();
                updateUI();
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("RESPONCEFAIL",t.getMessage());
            }
        });

    }

    private void fetchReviews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ReviewService service  = retrofit.create(ReviewService.class);
        service.get(movie.getId(), api_key).enqueue(new retrofit2.Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(!response.isSuccessful()){
                    ResponseBody errorBody = response.errorBody();
                    try{
                        Log.d("RESPONCE ERROR",errorBody.string());
                    }catch (Exception error){
                        Log.d("RESPONCEERR",error.getLocalizedMessage());
                    }
                    return;
                }
                Log.d("size","in before : "+videoList.size());
                reviewList=response.body().getReviewList();
                Log.d("size","in after : "+videoList.size());
                updateUI();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("RESPONCEFAIL",t.getMessage());
            }
        });


    }



    private void updateUI() {
        videoAdapter = new Video_Adapter(videoList,this);
        videoRecyclerView.setAdapter(videoAdapter);
        reviewAdapter = new Review_Adapter(reviewList,this);
        reviewRecyclerView.setAdapter(reviewAdapter);
    }

}
