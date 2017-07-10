package com.ahmaddudayef.movieudacity.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmaddudayef.movieudacity.R;
import com.ahmaddudayef.movieudacity.adapter.ReviewAdapter;
import com.ahmaddudayef.movieudacity.adapter.TrailerAdapter;
import com.ahmaddudayef.movieudacity.model.MovieURL;
import com.ahmaddudayef.movieudacity.pojo.MovieTrailer;
import com.ahmaddudayef.movieudacity.pojo.ReviewMovie;
import com.ahmaddudayef.movieudacity.singleton.GsonSingleton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView img_backdrop, img_poster;
    private TextView title, rating, release_date, description;

    private static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w185";
    private static final String POSTER_IMAGE_SIZE2 = "w500";

    Context context;

    String intent_title, intent_rating, intent_release_date,
            intent_description, intent_img_backdrop,
            intent_img_movie, intent_movie_id;

    private RecyclerView mRecyclerViewTrailer;
    private RecyclerView mRecyclerViewReview;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_tollbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initView();

        mRecyclerViewTrailer = (RecyclerView) findViewById(R.id.recyclerview_trailer);
        mRecyclerViewReview = (RecyclerView) findViewById(R.id.recyclerview_review) ;
//        LinearLayoutManager llm = new LinearLayoutManager(DetailActivity.this,
//                LinearLayoutManager.VERTICAL,false);

        mRecyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewReview.setLayoutManager(new LinearLayoutManager(this));
        trailerAdapter = new TrailerAdapter(null);
        reviewAdapter = new ReviewAdapter(null);
        mRecyclerViewTrailer.setAdapter(trailerAdapter);
        mRecyclerViewReview.setAdapter(reviewAdapter);

        if (internet_connection()){
            // Execute DownloadJSON AsyncTask
            new GetTrailerMovie().execute();
            new GetReviewMovie().execute();
        }else{
            //create a snackbar telling the user there is no internet connection and issuing a chance to reconnect
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection.",
                    Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.colorAccent));
            snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //recheck internet connection and call DownloadJson if there is internet
                    new GetTrailerMovie().execute();
                    new GetReviewMovie().execute();
                }
            }).show();
        }
    }

    private void initView() {

        img_backdrop = (ImageView) findViewById(R.id.image_movie_backdrop);
        img_poster = (ImageView) findViewById(R.id.poster_movie);

        title = (TextView) findViewById(R.id.title_movie);
        rating = (TextView) findViewById(R.id.rating_movie);
        release_date = (TextView) findViewById(R.id.release_date_movie);
        description = (TextView) findViewById(R.id.description_movie);

        intent_title = getIntent().getStringExtra("title");
        intent_rating = getIntent().getStringExtra("rating");
        intent_release_date = getIntent().getStringExtra("release_date");
        intent_description = getIntent().getStringExtra("description");
        intent_img_backdrop = getIntent().getStringExtra("img_backdrop");
        intent_img_movie = getIntent().getStringExtra("img_movie");
        intent_movie_id = getIntent().getStringExtra("id_movie");
        Log.d("id_movie", intent_movie_id);

        collapsingToolbar.setTitle(intent_title);
        toolbar.setTitle(intent_title);

        title.setText(intent_title);
        rating.setText(String.valueOf(intent_rating));
        release_date.setText(intent_release_date);
        description.setText(intent_description);

        Picasso.with(context)
                .load(POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE2 + intent_img_backdrop)
                .into(img_backdrop);

        Picasso.with(context)
                .load(POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + intent_img_movie)
                .into(img_poster);
    }

    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private class GetTrailerMovie extends AsyncTask<Void, Void, MovieTrailer> {

        @Override
        protected MovieTrailer doInBackground(Void... voids) {
            MovieTrailer movieTrailer = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MovieURL.getTrailer(intent_movie_id))
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                movieTrailer =
                        GsonSingleton.getGson().fromJson(response.body().string(),
                                MovieTrailer.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieTrailer;
        }

        @Override
        protected void onPostExecute(MovieTrailer movieTrailer) {
            super.onPostExecute(movieTrailer);
            trailerAdapter.updateListTrailer(movieTrailer.getTrailer());
            trailerAdapter.notifyDataSetChanged();
        }
    }

    private class GetReviewMovie extends AsyncTask<Void, Void, ReviewMovie> {

        @Override
        protected ReviewMovie doInBackground(Void... params) {
            ReviewMovie reviewMovie = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MovieURL.getReview(intent_movie_id))
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reviewMovie =
                        GsonSingleton.getGson().fromJson(response.body().string(),
                                ReviewMovie.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return reviewMovie;
        }

        @Override
        protected void onPostExecute(ReviewMovie reviewMovie) {
            super.onPostExecute(reviewMovie);
            reviewAdapter.updateListReview(reviewMovie.getReviews());
            reviewAdapter.notifyDataSetChanged();
        }
    }
}
