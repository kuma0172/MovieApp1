package com.example.finalprojects21;
/**
 * @author Preeti Kumari
 * @version1.0
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyMovieThread.MovieListener, TextWatcher {


    private EditText editSearchMovie;
    private Button searchButton;
    private RecyclerView movieRecycleView;
    private MovieRecycleViewAdapter myadapter;

    private List<Movie> movieList = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editSearchMovie = (EditText) findViewById(R.id.search_edit_text);
        editSearchMovie.addTextChangedListener(this);
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        /**
         * @author:Preeti Kumari
         * Creating a RecyclerView to present items in a list.
         * Selecting an item from the RecyclerView show detailed information about the item selected.
         */

        myadapter = new MovieRecycleViewAdapter(this, movieList, this);
        movieRecycleView = (RecyclerView) findViewById(R.id.movie_recycleview);
        movieRecycleView.setLayoutManager(new LinearLayoutManager(this));
        movieRecycleView.setAdapter(myadapter);

        DividerItemDecoration divider =
                new DividerItemDecoration(movieRecycleView.getContext(),
                        DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(),
                R.drawable.line_divide));

        movieRecycleView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

/**
 * @param
 * AlertDialog notification
 */

        if (id == searchButton.getId()) {
            String movieTitle = editSearchMovie.getText().toString();
            if (!TextUtils.isEmpty(movieTitle)) {

                MyMovieThread myMovieThread = new MyMovieThread(this, MainActivity.this);
                myMovieThread.executeAsyncTask(movieTitle, true);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Please enter movie name inside edittext.")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }

    }

    @Override
    public void listenMvoiecollection(List<Movie> list) {

        if (!movieList.isEmpty()) {
            movieList.clear();
        }

        if (!list.isEmpty()) {
            movieList.addAll(list);
            myadapter.notifyDataSetChanged();
        }

    }

    /**
     *
     * @param movie
     * Showing Toast bar notification
     */
    @Override
    public void openMovieDetail(Movie movie) {
        if (movie != null) {
            Toast.makeText(this, movie.getTile() + "clicked", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, MovieDetailActvity.class);
            myIntent.putExtra("Movie", movie);
            startActivity(myIntent);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        Log.e("Hello", "Hello: there: afterTextChanged" + s.toString());
        String title = s.toString();
        if (!TextUtils.isEmpty(title)) {
            MyMovieThread myMovieThread = new MyMovieThread(this, MainActivity.this);
            myMovieThread.executeAsyncTask(title, true);
        }

    }
}