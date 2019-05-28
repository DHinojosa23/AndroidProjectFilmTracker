package com.example.myfilmsapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfilmsapp.data.FilmDBHelper;

import java.util.ArrayList;

public class UserFilmsActivity extends AppCompatActivity {

    String source = "";
    ListView listViewUserFilms;
    ArrayList<Film> films = new ArrayList<Film>();
    ImageView imageViewEmptyList;
    ImageButton imageButtonNewFilm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_films);
        Intent intent = getIntent();
        source = intent.getStringExtra("source");

        listViewUserFilms = findViewById(R.id.listViewUserFilms);
        imageViewEmptyList = findViewById(R.id.imageViewEmptyList);
        imageButtonNewFilm = findViewById(R.id.imageButtonNewFilm);

        //Creación de la base de datos si aun no ha sido creada
        FilmDBHelper myFilmDBHelper = new FilmDBHelper(this);
        SQLiteDatabase db = myFilmDBHelper.getReadableDatabase();

        //dependiendo el botón pulsado por el usuario la lista que mostraremos es distina

        if(source.equalsIgnoreCase("watched")) {
            films = (ArrayList<Film>) myFilmDBHelper.getFilmsWatched();
        }

        if(source.equalsIgnoreCase("pending")) {
            films = (ArrayList<Film>) myFilmDBHelper.getFilmsPending();
        }

        //si el array de peliculas está lleno, teniendo al menos una pelicula la base de datos, eliminamos el splash de (introduce tu primera peli)
        if(films.size()>0){
            imageViewEmptyList.setVisibility(View.GONE);
        }

        for (int i = 0; i < films.size(); i++) {

            //insertamos imagenes a las mascotas segun sea su raza
            switch (films.get(i).getCountry().toLowerCase()) {

                case ("españa"):
                    films.get(i).setCountryImg(R.drawable.espana);
                    break;

                case ("eeuu"):
                    films.get(i).setCountryImg(R.drawable.us);
                    break;

                case ("usa"):
                    films.get(i).setCountryImg(R.drawable.us);
                    break;

                case ("japón"):
                    films.get(i).setCountryImg(R.drawable.japon);
                    break;

                case ("japon"):
                    films.get(i).setCountryImg(R.drawable.japon);
                    break;

                case ("italia"):
                    films.get(i).setCountryImg(R.drawable.italia);
                    break;

                case ("francia"):
                    films.get(i).setCountryImg(R.drawable.francia);
                    break;

                case ("nueva zelanda"):
                    films.get(i).setCountryImg(R.drawable.nuevazelanda);
                    break;

                case ("reino unido"):
                    films.get(i).setCountryImg(R.drawable.reinounido);
                    break;

                case ("inglatera"):
                    films.get(i).setCountryImg(R.drawable.reinounido);
                    break;

                case ("argentina"):
                    films.get(i).setCountryImg(R.drawable.argentina);
                    break;

                default:
                    films.get(i).setCountryImg(R.drawable.unknown);
                    break;

            }

        }


        FilmAdapter filmAdapter = new FilmAdapter(this, 0, films);
        listViewUserFilms.setAdapter(filmAdapter);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.imageButtonNewFilm:
                        openActivity_NewFilm();
                        break;

                }
            }
        };

        imageButtonNewFilm.setOnClickListener(onClickListener);


        //añadir un onclickitems a nuestra lista
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity_EditFilm(position);

            }
        };

        listViewUserFilms.setOnItemClickListener(onItemClickListener);



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    //home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openActivity_EditFilm(int position) {

        Film filmSelected = (Film) listViewUserFilms.getItemAtPosition(position);

        Intent i = new Intent(this, EditFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("film", filmSelected);
        i.putExtras(bundle);
        i.putExtra("source", source);
        startActivity(i);
    }

    public void openActivity_NewFilm() {
        Intent i = new Intent(this, NewFilmActivity.class);
        i.putExtra("source", source);
        startActivity(i);
    }

}
