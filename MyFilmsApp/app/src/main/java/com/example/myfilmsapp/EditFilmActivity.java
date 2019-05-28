package com.example.myfilmsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myfilmsapp.data.FilmDBHelper;

public class EditFilmActivity extends AppCompatActivity {

    LinearLayout linearLayoutNewFilmMain;
    EditText editTextTitle;
    EditText editTextYear;
    EditText editTextCountry;
    EditText editTextDirector;
    EditText editTextDescription;
    Spinner spinnerGenre;
    RatingBar ratingBarFilm;
    Button buttonUpdate;
    ProgressBar progressBarFilmLoading;
    ImageButton imageButtonDelete;
    CheckBox checkBoxWatched;

    String source;
    int id;
    String title;
    String newTitle;
    int year;
    String newYearStr;
    int newYear;
    String country;
    String newCountry;
    String director;
    String newDirector;
    String description;
    String newDescription;
    String genre;
    String newGenre;
    String type;
    float rating;
    float newRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_film);

        //obtenemos si estamos añadiendo a la lista de vistas o pendientes
        //nos traemos la pelicula clickada
        Bundle bundle = getIntent().getExtras();
        Film film = bundle.getParcelable("film");
        source = bundle.getString("source");

        Log.d("mipeliclickada", film.toString());

        id = film.getId();
        title = film.getTitle();
        year = film.getYear();
        country = film.getCountry();
        director = film.getDirector();
        rating = film.getRating();
        genre = film.getGenre();
        description = film.getDescription();
        type = film.getType();

        linearLayoutNewFilmMain = findViewById(R.id.linearLayoutNewFilmMain);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextYear = findViewById(R.id.editTextYear);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        ratingBarFilm = findViewById(R.id.ratingBarFilm);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        progressBarFilmLoading = findViewById(R.id.progressBarFilmLoading);
        imageButtonDelete = findViewById(R.id.imageButtonDelete);
        checkBoxWatched = findViewById(R.id.checkBoxWatched);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        editTextTitle.setText(title);
        editTextYear.setText(Integer.toString(year));
        editTextDirector.setText(director);
        editTextCountry.setText(country);
        editTextDescription.setText(description);
        ratingBarFilm.setRating(rating);
        int spinnerPosition = adapter.getPosition(genre);
        spinnerGenre.setSelection(spinnerPosition);

        //cambiamos el aspecto de la nueva actividad dependiendo si estamos viendo las peliculas pendientes o las vistas
        if (source.equalsIgnoreCase("pending")) {
            checkBoxWatched.setVisibility(View.VISIBLE);
        } else {
            checkBoxWatched.setVisibility(View.GONE);
        }
        //Nos aseguramos que la carga no es visible
        progressBarFilmLoading.setVisibility(View.GONE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonUpdate:
                        updateFilm(id);
                        break;

                    case R.id.imageButtonDelete:
                        if(type.equalsIgnoreCase("")) {
                            deleteDialog();
                        }else{
                            deleteDialogStaticFilm();
                        }

                        break;

                }
            }
        };

        buttonUpdate.setOnClickListener(onClickListener);
        imageButtonDelete.setOnClickListener(onClickListener);

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


    public void deleteFilm(int id) {

        final CharSequence FILMDELETEDTOAST = "La película ha sido eliminada de la base de datos";
        FilmDBHelper filmDBHelper = new FilmDBHelper(this);
        Integer deleteRow = filmDBHelper.deleteData(id);
        if (deleteRow > 0) {
            Log.i("Data deleted row id", "");
        } else {
            Log.i("Data deleted row id", "");
        }


        progressBarFilmLoading.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast(FILMDELETEDTOAST);
                progressBarFilmLoading.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goBack();
            }
        }, 2500);

    }

    public void updateFilm(int id) {

        newGenre = spinnerGenre.getSelectedItem().toString();
        newTitle = editTextTitle.getText().toString();
        newCountry = editTextCountry.getText().toString();
        newDescription = editTextDescription.getText().toString();
        newYearStr = editTextYear.getText().toString();
        newRating = ratingBarFilm.getRating();
        newDirector = editTextDirector.getText().toString();
        FilmDBHelper filmDBHelper = new FilmDBHelper(this);

        final CharSequence EMPTYINSERTEDTOAST = "Debe rellenar todos los campos para añadir una pelicula a la base de datos";
        final CharSequence STATICFILM = "No puede modificar las peliculas creadas por la propia aplicación";
        final CharSequence FILMUPDATEDTOAST = "La pelicula ha sido actualizada";
        final CharSequence FILMRATINGUPDATETOAST = "La puntuación de la película ha sido actualizada";
        final CharSequence WATCHEDFILM = "La pelicula ha sido movida a su lista de peliculas vistas";
        final CharSequence RATINGCERO = "Debe dar una valoración mínima de 0.5 estrellas";
        final CharSequence WRONGYEAR = "El año introducido no es válido, por favor introduzca una fecha real (yyyy)";
        final CharSequence WATCHTORATE = "Para puntuar una película marcala como vista";

        if (!type.equalsIgnoreCase("")) {

            if (checkBoxWatched.getVisibility() == View.GONE) {

                if (newRating == 0) {

                    showToast(RATINGCERO);
                }else if(newRating==rating){
                    showToast(STATICFILM);
                } else {
                    filmDBHelper.updateFilmRating(id, newRating);
                    filmDBHelper.updateFilmToWatched(id);
                    showToast(FILMRATINGUPDATETOAST);
                }

            } else if (checkBoxWatched.isChecked()) {
                if (newRating == 0) {

                    showToast(RATINGCERO);
                } else {
                    filmDBHelper.updateFilmRating(id, newRating);
                    filmDBHelper.updateFilmToWatched(id);
                    showToast(WATCHEDFILM);
                }
            } else {
                showToast(STATICFILM);
            }
        } else if (newTitle.isEmpty() || newCountry.isEmpty() || newDirector.isEmpty() || newGenre.isEmpty() || newDescription.isEmpty() || newYearStr.isEmpty()) {

            showToast(EMPTYINSERTEDTOAST);

        } else if (newRating == 0 && checkBoxWatched.isChecked()) {

            showToast(RATINGCERO);
        } else if (Integer.parseInt(newYearStr) < 1800 || Integer.parseInt(newYearStr) > 2100) {

            showToast(WRONGYEAR);
        }else if(!checkBoxWatched.isChecked() && newRating>0 && source.equalsIgnoreCase("pending")){

            showToast(WATCHTORATE);
        } else {

            newYear = Integer.parseInt(newYearStr);
            final Film film = new Film(newTitle, newYear, newCountry, newDirector, newGenre, newRating, newDescription);


            if (checkBoxWatched.isChecked()) {
                filmDBHelper.updateFilmToWatched(id);
            }

            long test = filmDBHelper.updateFilm(film, id);
            Log.i("Id Actualizada", "" + test);

            showToast(FILMUPDATEDTOAST);

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


    public AlertDialog deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("IMPORTANTE")
                .setMessage("¿Está seguro que desea ELIMINAR PERMANENTEMENTE esta película?")
                .setPositiveButton("ELIMINAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFilm(id);
                            }
                        })

                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si el usuario cancela, no hacemos nada, desaparece el alertdialog
                            }
                        });

        return builder.show();
    }


    public AlertDialog deleteDialogStaticFilm() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("IMPORTANTE")
                .setMessage("Las películas por defecto no pueden eliminarse ¿Desea quitarla de esta lista?")
                .setPositiveButton("QUITAR DE LISTA",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateFilmNotWatchedNotEmpty(id);
                            }
                        })

                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si el usuario cancela, no hacemos nada, desaparece el alertdialog
                            }
                        });

        return builder.show();
    }

    public void updateFilmNotWatchedNotEmpty(int id){
        FilmDBHelper filmDBHelper = new FilmDBHelper(this);
        filmDBHelper.updateFilmToNotPendingNotWatched(id);
        final CharSequence FILMDELETEDTOAST = "La película ha sido eliminada de esta lista";

        progressBarFilmLoading.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast(FILMDELETEDTOAST);
                progressBarFilmLoading.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goBack();
            }
        }, 2500);

    }



}
