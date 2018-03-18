package com.ahmaddudayef.movieudacity.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmaddudayef.movieudacity.R;
import com.ahmaddudayef.movieudacity.adapter.MovieAdapter;
import com.ahmaddudayef.movieudacity.model.MovieURL;
import com.ahmaddudayef.movieudacity.pojo.MoviePopular;
import com.ahmaddudayef.movieudacity.singleton.GsonSingleton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private ActionBar actionBar;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Popular Movies");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this,
//                LinearLayoutManager.VERTICAL,false);

        GridLayoutManager glm = new GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setLayoutManager(glm);
        movieAdapter = new MovieAdapter(null);
        mRecyclerView.setAdapter(movieAdapter);

        if (internet_connection()){
            // Execute DownloadJSON AsyncTask
            new AmbilDataMoviePopular().execute();
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
                    new AmbilDataMoviePopular().execute();
                }
            }).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_settings, menu);
        menuInflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.movie_popular:
                if (internet_connection()){
                    // Execute DownloadJSON AsyncTask
                    new AmbilDataMoviePopular().execute();
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
                            new AmbilDataMoviePopular().execute();
                        }
                    }).show();
                }
                break;
            case R.id.movie_top_rated:
                if (internet_connection()){
                    // Execute DownloadJSON AsyncTask
                    new MovieTopRated().execute();
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
                            new MovieTopRated().execute();
                        }
                    }).show();
                }
                break;
            case R.id.movie_up_coming:
                if (internet_connection()){
                    // Execute DownloadJSON AsyncTask
                    new MovieUpComing().execute();
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
                            new MovieUpComing().execute();
                        }
                    }).show();
                }
                break;
            case R.id.movie_now_playing:
                if (internet_connection()){
                    // Execute DownloadJSON AsyncTask
                    new MovieNowPlaying().execute();
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
                            new MovieNowPlaying().execute();
                        }
                    }).show();
                }
                break;
            case R.id.action_search :
                handleMenuSearch();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void doSearch(){
        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = true;
        }
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

    private class AmbilDataMoviePopular extends AsyncTask<Void, Void, MoviePopular>{
        @Override
        protected MoviePopular doInBackground(Void... voids) {
            MoviePopular moviePopular = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MovieURL.getMovie("popular"))
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                moviePopular =
                        GsonSingleton.getGson().fromJson(response.body().string(),
                                MoviePopular.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviePopular;
        }

        @Override
        protected void onPostExecute(MoviePopular moviePopular) {
            super.onPostExecute(moviePopular);
            actionBar.setTitle("Popular Movies");
//            Toast.makeText(MainActivity.this, moviePopular.toString(), Toast.LENGTH_SHORT).show();
            movieAdapter.updateList(moviePopular.getResults());
            movieAdapter.notifyDataSetChanged();
        }
    }

    private class MovieTopRated extends AsyncTask<Void, Void, MoviePopular> {

        @Override
        protected MoviePopular doInBackground(Void... voids) {
            MoviePopular moviePopular = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MovieURL.getMovie("top_rated"))
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                moviePopular =
                        GsonSingleton.getGson().fromJson(response.body().string(),
                                MoviePopular.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviePopular;
        }

        @Override
        protected void onPostExecute(MoviePopular moviePopular) {
            super.onPostExecute(moviePopular);
            actionBar.setTitle("Top Rated Movies");
//            Toast.makeText(MainActivity.this, moviePopular.toString(), Toast.LENGTH_SHORT).show();
            movieAdapter.updateList(moviePopular.getResults());
            movieAdapter.notifyDataSetChanged();
        }
    }

    private class MovieUpComing extends AsyncTask<Void, Void, MoviePopular> {

        @Override
        protected MoviePopular doInBackground(Void... voids) {
            MoviePopular moviePopular = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MovieURL.getMovie("upcoming"))
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                moviePopular =
                        GsonSingleton.getGson().fromJson(response.body().string(),
                                MoviePopular.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviePopular;
        }

        @Override
        protected void onPostExecute(MoviePopular moviePopular) {
            super.onPostExecute(moviePopular);
            actionBar.setTitle("Up Coming Movies");
//            Toast.makeText(MainActivity.this, moviePopular.toString(), Toast.LENGTH_SHORT).show();
            movieAdapter.updateList(moviePopular.getResults());
            movieAdapter.notifyDataSetChanged();
        }
    }

    private class MovieNowPlaying extends AsyncTask<Void, Void, MoviePopular> {

        @Override
        protected MoviePopular doInBackground(Void... voids) {
            MoviePopular moviePopular = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MovieURL.getMovie("now_playing"))
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                moviePopular =
                        GsonSingleton.getGson().fromJson(response.body().string(),
                                MoviePopular.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviePopular;
        }

        @Override
        protected void onPostExecute(MoviePopular moviePopular) {
            super.onPostExecute(moviePopular);
            actionBar.setTitle("Now Playing Movies");
//            Toast.makeText(MainActivity.this, moviePopular.toString(), Toast.LENGTH_SHORT).show();
            movieAdapter.updateList(moviePopular.getResults());
            movieAdapter.notifyDataSetChanged();
        }
    }

    //test
}
