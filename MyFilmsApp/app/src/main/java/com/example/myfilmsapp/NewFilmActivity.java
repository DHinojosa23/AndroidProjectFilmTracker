package com.example.myfilmsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfilmsapp.data.FilmDBHelper;

public class NewFilmActivity extends AppCompatActivity {

    LinearLayout linearLayoutNewFilmMain;
    EditText editTextTitle;
    EditText editTextYear;
    EditText editTextCountry;
    EditText editTextDirector;
    EditText editTextDescription;
    Spinner spinnerGenre;
    RatingBar ratingBarFilm;
    TextView textViewRating;
    Button buttonAdd;
    ProgressBar progressBarFilmAdded;

    String source;
    String title;
    int year;
    String yearStr;
    String country;
    String director;
    String description;
    String genre;
    float rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_film);

        //obtenemos si estamos añadiendo a la lista de vistas o pendientes
        Intent intent = getIntent();
        source = intent.getStringExtra("source");

        linearLayoutNewFilmMain = findViewById(R.id.linearLayoutNewFilmMain);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextYear = findViewById(R.id.editTextYear);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        ratingBarFilm = findViewById(R.id.ratingBarFilm);
        textViewRating = findViewById(R.id.textViewRating);
        buttonAdd = findViewById(R.id.buttonAdd);
        progressBarFilmAdded = findViewById(R.id.progressBarFilmAdded);

        //si venimos de la lista de peliculas pendientes no nos interesa ver el rating al añadir pelicula
        if (source.equalsIgnoreCase("pending")) {
            ratingBarFilm.setVisibility(View.GONE);
            textViewRating.setVisibility(View.GONE);
        } else {
            ratingBarFilm.setVisibility(View.VISIBLE);
            textViewRating.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonAdd:
                        insertNewFilm();
                        break;

                }
            }
        };

        buttonAdd.setOnClickListener(onClickListener);


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


    public void insertNewFilm() {

        title = editTextTitle.getText().toString();
        country = editTextCountry.getText().toString();
        director = editTextDirector.getText().toString();
        genre = spinnerGenre.getSelectedItem().toString();
        description = editTextDescription.getText().toString();
        yearStr = editTextYear.getText().toString();
        rating = ratingBarFilm.getRating();

        final CharSequence EMPTYINSERTEDTOAST = "Debe rellenar todos los campos para añadir una pelicula a la base de datos";
        final CharSequence FILMINSERTEDTOAST = "La pelicula ha sido añadida";
        final CharSequence RATINGCERO = "Debe dar una valoración mñinima de 0.5 estrellas";
        final CharSequence WRONGYEAR = "El año introducido no es válido, por favor introduzca una fecha real (yyyy)";

        if (title.isEmpty() || country.isEmpty() || director.isEmpty() || genre.isEmpty() || description.isEmpty() || yearStr.isEmpty()) {

            showToast(EMPTYINSERTEDTOAST);

        } else if (rating == 0 && ratingBarFilm.getVisibility()==View.VISIBLE) {

            showToast(RATINGCERO);
        } else if (Integer.parseInt(yearStr) < 1800 || Integer.parseInt(yearStr) > 2100) {

            showToast(WRONGYEAR);

        } else {

            year = Integer.parseInt(yearStr);
            final Film film = new Film(title, year, country, director, genre, rating, description);
            FilmDBHelper filmDBHelper = new FilmDBHelper(this);

            if (source.equalsIgnoreCase("watched")) {
                double test = filmDBHelper.insertFilmWatched(film);
                Log.i("Id de la nueva entrada", "" + test);
            } else {
                double test = filmDBHelper.insertFilmPending(film);
                Log.i("Id de la nueva entrada", "" + test);
            }

            progressBarFilmAdded.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToast(FILMINSERTEDTOAST);
                    progressBarFilmAdded.setVisibility(View.GONE);
                }
            }, 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            }, 2000);

        }

    }

    public void goBack() {
        Intent i = new Intent(this, UserFilmsActivity.class);
        i.putExtra("source", source);
        startActivity(i);
    }

    public void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
