package com.example.myfilmsapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myfilmsapp.data.FilmDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textViewWatched;
    TextView textViewPending;
    TextView textViewRecommendation;
    TextView textViewOscar;
    TextView textViewCult;
    TextView textViewQuiz;

    RelativeLayout relativeLayoutWatched;
    RelativeLayout relativeLayoutPending;
    RelativeLayout relativeLayoutRecommendation;
    RelativeLayout relativeLayoutOscar;
    RelativeLayout relativeLayoutCult;
    RelativeLayout relativeLayoutQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWatched = findViewById(R.id.textViewWatched);
        textViewPending = findViewById(R.id.textViewPending);
        textViewRecommendation = findViewById(R.id.textViewRecommendation);
        textViewOscar = findViewById(R.id.textViewOscar);
        textViewCult = findViewById(R.id.textViewCult);
        textViewQuiz = findViewById(R.id.textViewQuiz);

        relativeLayoutWatched = findViewById(R.id.relativeLayoutWatched);
        relativeLayoutPending = findViewById(R.id.relativeLayoutPending);
        relativeLayoutRecommendation = findViewById(R.id.relativeLayoutRecommendation);
        relativeLayoutOscar = findViewById(R.id.relativeLayoutOscar);
        relativeLayoutCult = findViewById(R.id.relativeLayoutCult);
        relativeLayoutQuiz = findViewById(R.id.relativeLayoutQuiz);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/goodbrush.ttf");
        textViewWatched.setTypeface(face);
        textViewPending.setTypeface(face);
        textViewRecommendation.setTypeface(face);
        textViewOscar.setTypeface(face);
        textViewCult.setTypeface(face);
        textViewQuiz.setTypeface(face);


        //Creación de la base de datos si aun no ha sido creada
        FilmDBHelper myFilmDBHelper = new FilmDBHelper(this);
        SQLiteDatabase db = myFilmDBHelper.getReadableDatabase();

        //si la tabla pets está vacía, le metemos 10 mascotas
        if (myFilmDBHelper.TableRows() == 0) {
            insertDefaultFilms();
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.relativeLayoutWatched:
                        openActivity_UserFilms("watched");
                        break;

                    case R.id.relativeLayoutPending:
                        openActivity_UserFilms("pending");
                        break;

                    case R.id.relativeLayoutRecommendation:
                        openActivity_StaticFilms("recommendation");
                        break;

                    case R.id.relativeLayoutOscar:
                        openActivity_StaticFilms("oscar");
                        break;

                    case R.id.relativeLayoutCult:
                        openActivity_StaticFilms("cult");
                        break;

                    case R.id.relativeLayoutQuiz:
                        openActivity_Quiz();
                        break;


                }
            }
        };

        relativeLayoutWatched.setOnClickListener(onClickListener);
        relativeLayoutPending.setOnClickListener(onClickListener);
        relativeLayoutRecommendation.setOnClickListener(onClickListener);
        relativeLayoutOscar.setOnClickListener(onClickListener);
        relativeLayoutCult.setOnClickListener(onClickListener);
        relativeLayoutQuiz.setOnClickListener(onClickListener);

    }

    public void openActivity_UserFilms(String source) {

        Intent i = new Intent(this, UserFilmsActivity.class);
        i.putExtra("source", source);
        startActivity(i);
    }

    public void openActivity_StaticFilms(String source) {
        Intent i = new Intent(this, StaticFilmsActivity.class);
        i.putExtra("source", source);
        startActivity(i);
    }

    public void openActivity_Quiz() {
        Intent i = new Intent(this, QuizActivity.class);
        startActivity(i);
    }

    public void insertDefaultFilms() {

        final ArrayList<Film> myDefaultFilmsList = new ArrayList<Film>();

        //peliculas recomendadas
        Film film1 = new Film(1, "El Caso Slevin", 2006, "EEUU","Paul McGuigan", "Thriller",0,"La pelicula favorita en las oficinas de FilmDiary, caras muy conocidas en una pelicula no tan popular pero que merece cada segundo de metraje","","recommended",0,0,"3skO4UOtUDs" );
        myDefaultFilmsList.add(film1);

        Film film2 = new Film(2, "Malditos Bastardos", 2009, "EEUU","Quentin Tarantino", "Acción",0,"cuando se trata de Quentin es difícil elegir, pero sin duda esta película es una obra maestra","","recommended",0,0 ,"2jcTRi8D80k");
        myDefaultFilmsList.add(film2);

        Film film3 = new Film(3, "Snatch. Cerdos y diamantes", 2000, "Reino Unido","Guy Ritchie", "Comedia",0,"Guy Ritchie nunca deja indiferente, esta pelicula nos muestra la esencia de este director, siendo sin duda su ópera prima","","recommended",0,0, "ni4tEtuTccc" );
        myDefaultFilmsList.add(film3);

        Film film4 = new Film(4, "Vengadores: Endgame", 2019, "EEUU","Anthony Russo & Joe Russo", "Acción",0,"Si no has seguido la saga ,te la recomendamos, y sobre todo Endgame cómo cierre final, un must-see","","recommended",0,0 ,"TcMBFSGVi1c");
        myDefaultFilmsList.add(film4);

        Film film5 = new Film(5, "The Battery", 2012, "EEUU","Jeremy Gardner", "Terror",0,"Pelicuna indie de muy bajo presupuesto y que hace todo bien, si te gustan las buenas peliculas postapocalípticas dale una oportunidad","","recommended",0,0 ,"6RAC0uwuIr8");
        myDefaultFilmsList.add(film5);

        Film film6 = new Film(6, "Un ciudadano ejemplar", 2009, "EEUU","Kurt Wimmer", "Thriller",0,"Thriller brutal sobre como todo puede cambiar de un día a otro, no queremos hacerte ni un pequeño spoiler, simplemente pontela","","recommended",0,0,"hL9A1iqu5ck" );
        myDefaultFilmsList.add(film6);

        Film film7 = new Film(7, "Intocable", 2011, "Francia","Olivier Nakache & Eric Toledano", "Drama",0,"Una comedñia dramática sobre la amistad y los prejuicios","","recommended",0,0,"drQRl0-nZw4" );
        myDefaultFilmsList.add(film7);

        Film film8 = new Film(8, "El secreto de sus ojos", 2009, "Argentina","Juan José Campanella", "Thriller",0,"Un oficial de juzgado obsesionado por un brutal asesinato ocurrido veinticinco años antes, decide escribir una novela sobre el caso, del cual fue testigo y protagonista.","","recommended",0,0,"GcHkTSqeGoU" );
        myDefaultFilmsList.add(film8);

        Film film9 = new Film(9, "Fogueo", 2017, "España","David Sainz", "Comedia",0,"Pelicula muy desconocida con el humorista David Sainz como director, puedes encontrarla ne youtube, muy recomendable.","","recommended",0,0,"F9QIxp860MI" );
        myDefaultFilmsList.add(film9);

        Film film10 = new Film(10, "La princesa Mononoke", 1997, "Japón","Hayao Miyazaki", "Animación",0,"Ópera prima del Estudio Ghibli, para nosotros la mejor de Miyazaki","","recommended",0,0, "WVjVkpk7wKg" );
        myDefaultFilmsList.add(film10);

        Film film11= new Film(11, "El gran showman", 2017, "EEUU","Michael Gracey", "Musical",0,"Nuestro musical favorito, Hugh Jackman deja las garras para tocarnos el corazón junto a un casting que lo hace todo bien","","recommended",0,0,"iX6RElPxtf0" );
        myDefaultFilmsList.add(film11);

    //peliculas ganadoras del oscar
        Film film12 = new Film(12, "El señor de los anillos: El retorno del rey", 2003, "Nueva Zelanda","Peter Jackson", "Fantasía",0,"La mejor trilogía de la historia, mayor galardonada de la academia, suerte que se hizo cuando los premios se les daban a las peliculas por ser buenas y no por su diversidad étnica y sexual","","oscar",0,0,"h-9RYiqyqjk" );
        myDefaultFilmsList.add(film12);

        Film film13 = new Film(13, "El silencio de los corderos", 1991, "EEUU","Jonathan Demme", "Thriller",0,"Película mítica donde las haya. Thriller puro con una interpretación escalofriante de Anthony Hopkins.","","oscar",0,0,"mDKn4VL8o10" );
        myDefaultFilmsList.add(film13);

        Film film14 = new Film(14, "No es país para viejos", 2007, "EEUU","Joel Coen & Ethan Coen", "Thriller",0,"los hermanos Coen son increibles haciendo historias en las que estas en tensión desde el primer minuto, esta no es menos","","oscar",0,0,"FvjkojaH1GQ" );
        myDefaultFilmsList.add(film14);

        Film film15 = new Film(15, "Infiltrados", 2006, "EEUU","Martin Scorsese", "Thriller",0,"Thriller policiaco/criminal estilo Scorsese en el que Leonardo DiCaprio y Matt Damon se comen la pantalla","","oscar",0,0,"VSKapBl__oU" );
        myDefaultFilmsList.add(film15);

        Film film16 = new Film(16, "Crash", 2004, "EEUU","Paul Haggis", "Drama",0,"Esta pelicula nos cuenta varias historias dramñaticas de desconocidos, todas tienen un punto de union. Brutal","","oscar",0,0,"LFVZ0_xTk6U" );
        myDefaultFilmsList.add(film16);

        Film film17 = new Film(17, "La Vida es Bella", 1997, "Italia","Roberto Benigni", "Drama",0,"Largometraje sobre el holocausto em el que un padre hace todo lo posible por que su hijo no entienda tanta maldad","","oscar",0,0,"Tw33Xs4Q2r4" );
        myDefaultFilmsList.add(film17);

        Film film18 = new Film(18, "La Milla Verde", 1999, "EEUU","Frank Darabont", "Drama",0,"Sobre la pena de muerte, todo bien con esta película, prepárate para llorar","","oscar",0,0 ,"hBtSF4-cnzk");
        myDefaultFilmsList.add(film18);

        Film film19 = new Film(19, "Mystic River", 2003, "EEUU","Clint Eastwood", "Thriller",0,"Clint Eastwood muestra sus dotes como director y se marca este peliculón, suspense puro","","oscar",0,0,"jF1e3g6jvlU" );
        myDefaultFilmsList.add(film19);

        Film film20 = new Film(20, "Slumdog Millionaire", 2008, "Reino unido","Danny Boyle & Loveleen Tandan", "Drama",0,"n adolescente pobre de los suburbios de Bombay que participa en la versión hindú del popular programa \"¿Quién quiere ser millonario?\". A punto de conseguir 20 millones de rupias, que es el premio máximo del concurso, el joven es interrogado por la policía, que sospecha que está haciendo trampas. Pero para cada una de las preguntas, Jamal tiene una respuesta","","oscar",0,0,"aCvNg15wSME" );
        myDefaultFilmsList.add(film20);

        Film film21 = new Film(21, "Bohemian Rhapsody", 2018, "Reino Unido","Bryan Singer", "Musical",0,"No ganó el oscar a mejor película pero se llevó unos cuantos, pelos de punta con este biopic sobre Freddy Mercury","","oscar",0,0,"LTAIlPsMOs4" );
        myDefaultFilmsList.add(film21);

        // peliculas de culto
        Film film22 = new Film(22, "Star Wars. Ep.V: El imperio contraataca", 1980, "EEUU","Irvin Kershner", "Fantasía",0,"Saga conocida en todo el mundo, todo un éxito, consideramos esta la mejor de la saga, pero sin duda es una saga que tiene que ser vista, esperemos que la dejen tranquila pronto","","cult",0,0,"LTAIlPsMOs4" );
        myDefaultFilmsList.add(film22);

        Film film23 = new Film(23, "Uno de los Nuestros", 1990, "EEUU","Martin Scorsese", "Mafia",0,"Pelicula top sobre mafia, muy disfrutable.","","cult",0,0,"mDipxg3H37o" );
        myDefaultFilmsList.add(film23);

        Film film24 = new Film(24, "Reservoir Dogs", 1992, "EEUU","Quentin Tarantino", "Thriller",0,"Otro peliculón de Quentin Tarantino que parece que solo sabe hacer peliculones, hasta las peores son el sueño de muchos directores","","cult",0,0,"RJBFHPwH1Xw" );
        myDefaultFilmsList.add(film24);

        Film film25 = new Film(25, "Pulp Fiction", 1994, "EEUU","Quentin Tarantino", "Thriller",0,"Nos cuesta no poner toda la filmografía de Tarantino, Pulp Fiction es sin duda una de sus más conocidas y de culto","","cult",0,0,"ZFYCXAG6fdo" );
        myDefaultFilmsList.add(film25);

        Film film26 = new Film(26, "American History X", 1998, "EEUU","Tony Kaye", "Drama",0,"Pelicula sobre el nazismo que te hace pensar y toca el corazón, muy recomendable","","cult",0,0,"oi7bhRmJzec" );
        myDefaultFilmsList.add(film26);

        Film film27 = new Film(27, "Seven", 1995, "EEUU","David Fincher", "Thriller",0,"Thriller sobre los 7 pecados capitales, policía y un asesino en serie, hay que verla","","cult",0,0,"xhzBmjdehPA" );
        myDefaultFilmsList.add(film27);

        Film film28 = new Film(28, "El Padrino", 1972, "EEUU","Francis Ford Coppola", "Thriller",0,"No creo que se pueda decir nada de esta pelicula que no este dicho, si no la has visto desinstala esta aplicación por favor","","cult",0,0,"COQvkUmN6H8" );
        myDefaultFilmsList.add(film28);

        Film film29 = new Film(29, "12 hombres sin piedad", 1957, "EEUU","Sidney Lumet", "Thriller",0,"Hoy en día a veces cuesta ver películas de culto o recomendaciones posteriores a los 70-80, el cine era distinto y todo se nos hace artifical y lento a veces, nada de eso pasa con esta obra maestra","","cult",0,0,"I0OPus5jM2w" );
        myDefaultFilmsList.add(film29);

        Film film30 = new Film(30, "La lista de Schindler", 2003, "EEUU","Steven Spielberg", "Drama",0,"Otra película sobre el Holocausto y la segunda guerra mundial que toca la fibra","","cult",0,0 ,"7q-ETFeMxwI");
        myDefaultFilmsList.add(film30);

        Film film31 = new Film(31, "El milagro de P. Tinto", 1998, "España","Javier Fesser", "Comedia",0,"Queríamos añadir algo de producto nacional, esta película pese a no ser comparable al resto de la lista hace las cosas bastante bien y es bastante divertida y reconocida por la crítica en España como una película de culto","","cult",0,0,"8CG6CSD-ylk" );
        myDefaultFilmsList.add(film31);



        for (int i = 0; i < myDefaultFilmsList.size(); i++) {
            FilmDBHelper filmDBHelper = new FilmDBHelper(this);
            double test = filmDBHelper.insertFilmStatic(myDefaultFilmsList.get(i));
            Log.i("Id de la nueva entrada", "" + test);
        }

    }


}
