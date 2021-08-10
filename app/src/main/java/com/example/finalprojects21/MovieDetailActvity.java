package com.example.finalprojects21;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActvity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MovieDetailActvity";
    TextView movieDetailTitle;
    TextView movieDetailYear;
    TextView ratedDetail;
    TextView releasedDetail;
    ImageView posterImageDetail;

    private FrameLayout buyframe;
    private Movie movie;
    private Button buyButton;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.movie_detail);

        movie = (Movie) getIntent().getSerializableExtra("Movie");
        Log.d(TAG, "hello: ther: tilte = " + movie.getTile());


        movieDetailTitle = (TextView) findViewById(R.id.movie_detail_title);
        movieDetailYear = (TextView) findViewById(R.id.movie_detail_year);
        ratedDetail = (TextView) findViewById(R.id.movie_detail_rated);
        releasedDetail = (TextView) findViewById(R.id.movie_detail_released);
        posterImageDetail = (ImageView) findViewById(R.id.movie_detail_poster);
        buyButton = (Button) findViewById(R.id.buy_movie);
        buyButton.setOnClickListener(this);

        buyframe = (FrameLayout) findViewById(R.id.buy_frame);



    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    /**
     * Fetching movie information and data from website through this url
     */
    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(movie != null){
                    movieDetailTitle.setText(movie.getTile());
                    movieDetailYear.setText(movie.getYear());
                    ratedDetail.setText(movie.getRated());
                    releasedDetail.setText(movie.getReleaseDate());
                    String imageUrl = movie.getPosterUrl();
                    if(!TextUtils.isEmpty(imageUrl)){
                        Picasso.with(getApplicationContext())
                                .load(imageUrl)
                                .fit()
                                // To prevent fade animation
                                .noFade()
                                .into(posterImageDetail);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == buyButton.getId()){


            new AlertDialog.Builder(this)
                    .setTitle("Info")
                    .setMessage("You really want to buy this movie.")

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MovieDetailActvity.this, "This movie will cost yout 10$.", Toast.LENGTH_LONG).show();

                            loadFramgnet();
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void loadFramgnet(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(buyframe.getId(), new BuyFragment());
        fragmentTransaction.commit();
    }
}