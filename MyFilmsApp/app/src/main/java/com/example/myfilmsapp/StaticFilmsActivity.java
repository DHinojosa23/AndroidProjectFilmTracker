package com.example.myfilmsapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myfilmsapp.data.FilmDBHelper;

import java.util.ArrayList;

public class StaticFilmsActivity extends AppCompatActivity {

    String source;
    ListView listViewStaticFilms;
    ArrayList<Film> films = new ArrayList<Film>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_films);

        //obtenemos que botón ha pulsado el usuario
        Intent intent = getIntent();
        source = intent.getStringExtra("source");

        listViewStaticFilms = findViewById(R.id.listViewStaticFilms);

        //Creación de la base de datos si aun no ha sido creada
        FilmDBHelper myFilmDBHelper = new FilmDBHelper(this);
        SQLiteDatabase db = myFilmDBHelper.getReadableDatabase();

        //dependiendo el botón pulsado por el usuario la lista que mostraremos es distina

        if(source.equalsIgnoreCase("recommendation")) {
            films = (ArrayList<Film>) myFilmDBHelper.getFilmsRecommended();
        }

        if(source.equalsIgnoreCase("oscar")) {
            films = (ArrayList<Film>) myFilmDBHelper.getFilmsOscar();
        }

        if(source.equalsIgnoreCase("cult")) {
            films = (ArrayList<Film>) myFilmDBHelper.getFilmsCult();
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
        listViewStaticFilms.setAdapter(filmAdapter);

        //añadir un onclickitems a nuestra lista
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity_ViewStaticFilm(position);

            }
        };

        listViewStaticFilms.setOnItemClickListener(onItemClickListener);




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

    public void openActivity_ViewStaticFilm(int position) {

        Film filmSelected = (Film) listViewStaticFilms.getItemAtPosition(position);

        Intent i = new Intent(this, ViewStaticFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("film", filmSelected);
        i.putExtras(bundle);
        i.putExtra("source", source);
        startActivity(i);
    }



}
