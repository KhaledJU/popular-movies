package com.example.movies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movies.MovieAPI.Movie;
import com.example.movies.MovieAPI.MovieResponse;
import com.example.movies.MovieAPI.MovieService;
import com.example.movies.MovieAPI.Movie_Adapter;
import com.example.movies.MovieAPI.SingleMovieService;
import com.example.movies.favoritDB.Favorit;
import com.example.movies.favoritDB.FavoritDatabase;
import com.example.movies.favoritDB.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.movies.R.color.colorPrimary;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Movie_Adapter adapter;
    private List<Movie> popularList;
    private List<Movie> top_ratedList;
    private List<Movie> favMovieList;
    private Menu menu;

    public static String base_url = "https://api.themoviedb.org";
    public static String popular = "popular";
    public static String top_rated = "top_rated";
    public static String category = "popular";
    public static String api_key = BuildConfig.ApiKey;

    FavoritDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(colorPrimary)));
        setTitle("Pop Movies");

        progressBar = findViewById(R.id.Progress_circular);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mDB = FavoritDatabase.getInstance(this);

        popularList = new ArrayList<Movie>();
        top_ratedList = new ArrayList<Movie>();
        favMovieList = new ArrayList<Movie>();

        adapter = new Movie_Adapter( popularList,this);
        recyclerView.setAdapter(adapter);

        fetchFavorits();
        AppExecutors.getInstance().getNetworkIO().execute(new Runnable() {
            @Override
            public void run() {
                fetchDate(popular);
                fetchDate(top_rated);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(popularList);
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_by_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem current = menu.findItem(R.id.current_choice);
        switch (item.getItemId()){
            case R.id.most_popular:
                current.setTitle(R.string.most_popular);
                category = "popular";
                updateUI(popularList);
                break;
            case R.id.highest_rated:
                current.setTitle(R.string.highest_rated);
                category = "top_rated";
                updateUI(top_ratedList);
                break;

            case R.id.favorit:
                current.setTitle(R.string.favorits);
                category = "favorit";
                updateUI(favMovieList);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchDate(final String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService service  = retrofit.create(MovieService.class);
        service.get(category, api_key).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(!response.isSuccessful()){
                    ResponseBody errorBody = response.errorBody();
                    try{
                        Log.d("RESPONCE ERROR",errorBody.string());
                    }catch (Exception error){
                        Log.d("RESPONCEERR",error.getLocalizedMessage());
                    }
                    return;
                }
                if(category.equals(popular))
                    popularList = response.body().getMovieList();
                else
                    top_ratedList = response.body().getMovieList();

                updateUI(popularList);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("RESPONCEFAIL",t.getMessage());
            }
        });


    }

    public void fetchFavorits() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorits().observe(this, new Observer<List<Favorit>>() {
            @Override
            public void onChanged(@Nullable List<Favorit> favoritList) {
                favMovieList = new ArrayList<Movie>();
                for(final Favorit favorit : favoritList) {
                    favMovieList.add(fetchFavMovie(favorit.getId()));

                }
                if(category.equals("favorit"))
                    updateUI(favMovieList);
            }

        });

    }

    public Movie fetchFavMovie(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SingleMovieService service  = retrofit.create(SingleMovieService.class);

        final Movie finalMovie = new Movie();
        service.get(id, api_key).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(!response.isSuccessful()){
                    ResponseBody errorBody = response.errorBody();
                    try{
                        Log.d("RESPONCE ERROR",errorBody.string());
                    }catch (Exception error){
                        Log.d("RESPONCEERR",error.getLocalizedMessage());
                    }
                    return;
                }
                finalMovie.setId(response.body().getId());
                finalMovie.setTitle(response.body().getTitle());
                finalMovie.setOverview(response.body().getOverview());
                finalMovie.setPoster_path(response.body().getPoster_path());
                finalMovie.setRelease_date(response.body().getRelease_date());
                finalMovie.setVote_average(response.body().getVote_average());

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("RESPONCEFAIL",t.getMessage());
            }
        });

        return finalMovie;

    }

    private void updateUI(List<Movie> movies){
        adapter.setMovies(movies);
        recyclerView.setAdapter(adapter);
    }

}
