package com.example.myfilmsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfilmsapp.data.FilmDBHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ViewStaticFilmActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlaybackEventListener {

    LinearLayout linearLayoutViewStaticFilmMain;
    TextView textViewTitleF;
    TextView textViewYearF;
    TextView textViewCountryF;
    TextView textViewDirectorF;
    TextView textViewGenreF;
    TextView textViewDescriptionF;
    Button buttonOk;
    RadioButton radioButtonWatched;
    RadioButton radioButtonPending;
    RatingBar ratingBarDialog;

    String source;
    int id;
    int watched;
    int pending;

    String title;
    String country;
    String year;
    String director;
    String genre;
    String description;
    float rating = 0;
    String youtubeUrl="";

    YouTubePlayerView youTubePlayerViewTrailer;
    String youtubeKey = "AIzaSyDIurLKGc7sUKOVdylTNjJLBWYhBNI8qpY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_static_film);

        //obtenemos si estamos añadiendo a la lista de vistas o pendientes
        //nos traemos la pelicula clickada
        Bundle bundle = getIntent().getExtras();
        Film film = bundle.getParcelable("film");
        source = bundle.getString("source");

        Log.d("mipeliclickada", film.toString());

        id = film.getId();
        watched = film.getWatched();
        pending = film.getPending();
        title = film.getTitle();
        country = film.getCountry();
        director = film.getDirector();
        description = film.getDescription();
        year = Integer.toString(film.getYear());
        genre = film.getGenre();
        rating = film.getRating();
        youtubeUrl = film.getYoutubeUrl();

        linearLayoutViewStaticFilmMain = findViewById(R.id.linearLayoutViewStaticFilmMain);
        textViewTitleF = findViewById(R.id.textViewTitleF);
        textViewYearF = findViewById(R.id.textViewYearF);
        textViewCountryF = findViewById(R.id.textViewCountryF);
        textViewDirectorF = findViewById(R.id.textViewDirectorF);
        textViewGenreF = findViewById(R.id.textViewGenreF);
        textViewDescriptionF = findViewById(R.id.textViewDescriptionF);
        buttonOk = findViewById(R.id.buttonOk);
        radioButtonWatched = findViewById(R.id.radioButtonWatched);
        radioButtonPending = findViewById(R.id.radioButtonPending);
        youTubePlayerViewTrailer = (YouTubePlayerView) findViewById(R.id.youTubePlayerViewTrailer);
        youTubePlayerViewTrailer.initialize(youtubeKey, this);

        textViewTitleF.setText(title);
        textViewYearF.setText(year);
        textViewDirectorF.setText(director);
        textViewCountryF.setText(country);
        textViewDescriptionF.setText(description);
        textViewGenreF.setText(genre);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonOk:
                        updateFilm(id);
                        break;

                }
            }
        };

        buttonOk.setOnClickListener(onClickListener);

    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    //home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateFilm(int id) {

        FilmDBHelper filmDBHelper = new FilmDBHelper(this);

        final CharSequence FILMPENDINGTOAST = "La película ha sido enviada a su lista de películas pendientes";
        final CharSequence FILMEMPTY = "Seleccione una opción si quiere añadir la pelicula a una de tus listas";

        if (radioButtonWatched.isChecked()) {

            rankDialog(filmDBHelper);


        }
        if (radioButtonPending.isChecked()) {
            showToast(FILMPENDINGTOAST);

            long test = filmDBHelper.updateFilmToPending(id);
            Log.i("Id Actualizada", "" + test);

        }
        if (!radioButtonPending.isChecked() && !radioButtonWatched.isChecked()) {
            showToast(FILMEMPTY);
        }

    }


    public void goBack() {
        Intent i = new Intent(this, StaticFilmsActivity.class);
        i.putExtra("source", source);
        startActivity(i);
    }

    public void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void rankDialog(final FilmDBHelper filmDBHelper) {

        final Dialog rankDialog = new Dialog(ViewStaticFilmActivity.this);
        rankDialog.setContentView(R.layout.rank_dialog);
        rankDialog.setCancelable(true);
        final CharSequence FILMWATCHEDTOAST = "La película ha sido enviada a su lista de películas vistas";

        ratingBarDialog = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
        ratingBarDialog.setRating(rating);

        //Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
        TextView textViewDialogOk = (TextView) rankDialog.findViewById(R.id.textViewDialogOk);
        textViewDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = ratingBarDialog.getRating();
                Log.i("rating", Float.toString(rating));
                rankDialog.dismiss();
                long test = filmDBHelper.updateFilmToWatched(id);
                Log.i("Id Actualizada", "" + test);
                filmDBHelper.updateFilmRating(id, rating);
                showToast(FILMWATCHEDTOAST);

            }
        });
        //now that the dialog is set up, it's time to show it
        rankDialog.show();
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {

        if (!fueRestaurado) {
            youTubePlayer.cueVideo(youtubeUrl);//video reproducido obtenido del objeto Film

        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = "Error al inicializar Youtube" + youTubeInitializationResult.toString();
            Log.i("", error);
        }
    }

    protected void onActivityResult(int requestCode, int resultcode, Intent data) {

        if (resultcode == 1) {
            getYoutubePlayerProvider().initialize(youtubeKey, this);
        }

    }

    protected YouTubePlayer.Provider getYoutubePlayerProvider() {
        return youTubePlayerViewTrailer;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}