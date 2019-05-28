package com.example.myfilmsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    final String[] QUESTIONS = {"", "El primer James Bond del cine fue:",
            "¿Cuáles de los siguientes actores NO tienen un oscar a mejor actor?",
            "¿Cuál de los siguientes escritores fue el creador de las novelas en las que se basó la trilogía del señor de los anillos?",
            "¿A qué película pertenece la banda sonora que estás escuchando?",
            "Fue el último director en dirigir al personaje de Batman:",
            "de las siguientes películas selecciona las que ha dirigido Guy Ritchie",
            "¿De qué película es conocida la frase 'say hello to my little friend'?",
            "¿Cuáles de las siguientes actrices tienen un oscar a mejor actor?",
            "¿Qué actor interpretó al famoso personaje 'Señor Lobo' en Pulp Fiction?"};

    final String[] ANSWERSRADIO = {"David Niven", "Sean Pen", "Sean Connery", "Barry Nelson",
            "J.R.R. Tolkien", "George R.R. Martin", "Andrezj Shapkowski", "Juan Gómez Jurado",
            "Batman Begins", "Titanic", "Los chicos del coro", "Amelie",
            "Zack Snyder", "Christopher Nolan", "Tim Burton", "Santiago Segura",
            "Lock, Stock and Two Smoking Barrels", "Rock 'n' Rolla", "Snatch: Cerdos y Diamantes", "Sherlock Holmes",
            "Aladdin", "Malditos Bastardos", "Scarface", "El padrino II",
            "Penelope Cruz", " Emma Stone", "Julianne Moore", "Renée Zellweger",
            "Tom Hanks", "Harvey Keitel", "Tim Roth", "Christoph Waltz"};

    final String SENDEMAIL = "Envíar Email";
    final String[] CHEERS = {"A ver si sabes tanto como crees", "Hitchcock estaría orgulloso", "Ya estas terminando, ve preparándote para nuestro próximo quiz", "¡Has acabado! Eres todo un somelier del cine"};
    String quizFinished = "";
    String body = "";
    int count = 0, score = 0;
    String[] adresses = {""};

    TextView textViewBait;
    TextView textViewQuestions;
    Button buttonNext;
    RadioGroup radioGroupAnswers;
    RadioButton radioButtonOp1;
    RadioButton radioButtonOp2;
    RadioButton radioButtonOp3;
    RadioButton radioButtonOp4;
    CheckBox checkBoxOp1;
    CheckBox checkBoxOp2;
    CheckBox checkBoxOp3;
    CheckBox checkBoxOp4;

    MediaPlayer mediaPlayerAmelie;
    MediaPlayer mediaPlayerSayHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroupAnswers = (RadioGroup) findViewById(R.id.radioGroupAnswers);
        radioButtonOp1 = (RadioButton) findViewById(R.id.radioButtonOp1);
        radioButtonOp2 = (RadioButton) findViewById(R.id.radioButtonOp2);
        radioButtonOp3 = (RadioButton) findViewById(R.id.radioButtonOp3);
        radioButtonOp4 = (RadioButton) findViewById(R.id.radioButtonOp4);
        checkBoxOp1 = (CheckBox) findViewById(R.id.checkBoxOp1);
        checkBoxOp2 = (CheckBox) findViewById(R.id.checkBoxOp2);
        checkBoxOp3 = (CheckBox) findViewById(R.id.checkBoxOp3);
        checkBoxOp4 = (CheckBox) findViewById(R.id.checkBoxOp4);
        textViewQuestions = (TextView) findViewById(R.id.textViewQuestions);
        textViewBait = (TextView) findViewById(R.id.textViewBait);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        mediaPlayerAmelie = MediaPlayer.create(getApplicationContext(), R.raw.amelie);
        mediaPlayerSayHello = MediaPlayer.create(getApplicationContext(), R.raw.sayhello);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/bebasreg.ttf");
        radioButtonOp1.setTypeface(face);
        radioButtonOp2.setTypeface(face);
        radioButtonOp3.setTypeface(face);
        radioButtonOp4.setTypeface(face);
        checkBoxOp1.setTypeface(face);
        checkBoxOp2.setTypeface(face);
        checkBoxOp3.setTypeface(face);
        checkBoxOp4.setTypeface(face);
        textViewQuestions.setTypeface(face);
        textViewBait.setTypeface(face);
        buttonNext.setTypeface(face);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonNext:
                        next();
                        break;


                }
            }
        };

        buttonNext.setOnClickListener(onClickListener);


    }


    public void next() {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toastCheers1 = Toast.makeText(context, CHEERS[0], duration), toastCheers2 = Toast.makeText(context, CHEERS[1], duration), toastCheers3 = Toast.makeText(context, CHEERS[2], duration), toastCheers4 = Toast.makeText(context, CHEERS[3], duration);
        Toast toastAgain = Toast.makeText(context, "Debes elegir al menos una respuesta", duration);

        if (count == 10) {
            count++;
        } else if (count != 11 && (radioButtonOp1.isChecked() || radioButtonOp2.isChecked() || radioButtonOp3.isChecked() || radioButtonOp4.isChecked() || checkBoxOp1.isChecked() || checkBoxOp2.isChecked() || checkBoxOp3.isChecked() || checkBoxOp4.isChecked())) {
            count++;
        } else {
            if(count<10) {
                toastAgain.show();
            }
        }


        switch (count) {

            case 1:

                if (radioButtonOp2.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));

                }

                textViewQuestions.setText(QUESTIONS[count]);

                radioGroupAnswers.clearCheck();

                radioButtonOp1.setText(ANSWERSRADIO[0]);
                radioButtonOp2.setText(ANSWERSRADIO[1]);
                radioButtonOp3.setText(ANSWERSRADIO[2]);
                radioButtonOp4.setText(ANSWERSRADIO[3]);
                break;

            case 2:

                if (radioButtonOp3.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));

                }

                textViewQuestions.setText(QUESTIONS[count]);

                radioGroupAnswers.clearCheck();

                radioButtonOp1.setVisibility(View.GONE);
                radioButtonOp2.setVisibility(View.GONE);
                radioButtonOp3.setVisibility(View.GONE);
                radioButtonOp4.setVisibility(View.GONE);

                checkBoxOp1.setVisibility(View.VISIBLE);
                checkBoxOp2.setVisibility(View.VISIBLE);
                checkBoxOp3.setVisibility(View.VISIBLE);
                checkBoxOp4.setVisibility(View.VISIBLE);

                break;

            case 3:

                if (!checkBoxOp1.isChecked() || !checkBoxOp3.isChecked()) {

                    if (checkBoxOp2.isChecked() && checkBoxOp4.isChecked()){
                        score++;
                    Log.i("puntuación",Integer.toString(score));
                    }
                }

                textViewQuestions.setText(QUESTIONS[count]);

                if (checkBoxOp1.isChecked()) {
                    checkBoxOp1.setChecked(false);
                }
                if (checkBoxOp2.isChecked()) {
                    checkBoxOp2.setChecked(false);
                }
                if (checkBoxOp3.isChecked()) {
                    checkBoxOp3.setChecked(false);
                }
                if (checkBoxOp4.isChecked()) {
                    checkBoxOp4.setChecked(false);
                }

                toastCheers1.show();

                radioGroupAnswers.clearCheck();

                checkBoxOp1.setVisibility(View.GONE);
                checkBoxOp2.setVisibility(View.GONE);
                checkBoxOp3.setVisibility(View.GONE);
                checkBoxOp4.setVisibility(View.GONE);

                radioButtonOp1.setText(ANSWERSRADIO[4]);
                radioButtonOp2.setText(ANSWERSRADIO[5]);
                radioButtonOp3.setText(ANSWERSRADIO[6]);
                radioButtonOp4.setText(ANSWERSRADIO[7]);

                radioButtonOp1.setVisibility(View.VISIBLE);
                radioButtonOp2.setVisibility(View.VISIBLE);
                radioButtonOp3.setVisibility(View.VISIBLE);
                radioButtonOp4.setVisibility(View.VISIBLE);

                break;


            case 4:

                if (radioButtonOp1.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));
                }

                mediaPlayerAmelie.start();

                textViewQuestions.setText(QUESTIONS[count]);

                radioGroupAnswers.clearCheck();

                radioButtonOp1.setText(ANSWERSRADIO[8]);
                radioButtonOp2.setText(ANSWERSRADIO[9]);
                radioButtonOp3.setText(ANSWERSRADIO[10]);
                radioButtonOp4.setText(ANSWERSRADIO[11]);
                break;

            case 5:

                if (radioButtonOp4.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));
                }

                mediaPlayerAmelie.stop();

                textViewQuestions.setText(QUESTIONS[count]);

                radioGroupAnswers.clearCheck();

                radioButtonOp1.setText(ANSWERSRADIO[12]);
                radioButtonOp2.setText(ANSWERSRADIO[13]);
                radioButtonOp3.setText(ANSWERSRADIO[14]);
                radioButtonOp4.setText(ANSWERSRADIO[15]);

                break;

            case 6:

                if (radioButtonOp1.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));
                }

                textViewQuestions.setText(QUESTIONS[count]);

                radioGroupAnswers.clearCheck();

                toastCheers2.show();

                checkBoxOp1.setText(ANSWERSRADIO[16]);
                checkBoxOp2.setText(ANSWERSRADIO[17]);
                checkBoxOp3.setText(ANSWERSRADIO[18]);
                checkBoxOp4.setText(ANSWERSRADIO[19]);

                radioButtonOp1.setVisibility(View.GONE);
                radioButtonOp2.setVisibility(View.GONE);
                radioButtonOp3.setVisibility(View.GONE);
                radioButtonOp4.setVisibility(View.GONE);

                checkBoxOp1.setVisibility(View.VISIBLE);
                checkBoxOp2.setVisibility(View.VISIBLE);
                checkBoxOp3.setVisibility(View.VISIBLE);
                checkBoxOp4.setVisibility(View.VISIBLE);
                break;

            case 7:

                if (checkBoxOp1.isChecked() && checkBoxOp2.isChecked() && checkBoxOp3.isChecked() && checkBoxOp4.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));
                }

                textViewQuestions.setText(QUESTIONS[count]);

                if (checkBoxOp1.isChecked()) {
                    checkBoxOp1.setChecked(false);
                }
                if (checkBoxOp2.isChecked()) {
                    checkBoxOp2.setChecked(false);
                }
                if (checkBoxOp3.isChecked()) {
                    checkBoxOp3.setChecked(false);
                }
                if (checkBoxOp4.isChecked()) {
                    checkBoxOp4.setChecked(false);
                }

                mediaPlayerSayHello.start();

                checkBoxOp1.setVisibility(View.GONE);
                checkBoxOp2.setVisibility(View.GONE);
                checkBoxOp3.setVisibility(View.GONE);
                checkBoxOp4.setVisibility(View.GONE);

                radioButtonOp1.setText(ANSWERSRADIO[20]);
                radioButtonOp2.setText(ANSWERSRADIO[21]);
                radioButtonOp3.setText(ANSWERSRADIO[22]);
                radioButtonOp4.setText(ANSWERSRADIO[23]);

                radioButtonOp1.setVisibility(View.VISIBLE);
                radioButtonOp2.setVisibility(View.VISIBLE);
                radioButtonOp3.setVisibility(View.VISIBLE);
                radioButtonOp4.setVisibility(View.VISIBLE);
                break;

            case 8:

                if (radioButtonOp3.isChecked())  {
                    score++;
                    Log.i("puntuación",Integer.toString(score));
                }

                textViewQuestions.setText(QUESTIONS[count]);

                radioGroupAnswers.clearCheck();

                radioButtonOp1.setVisibility(View.GONE);
                radioButtonOp2.setVisibility(View.GONE);
                radioButtonOp3.setVisibility(View.GONE);
                radioButtonOp4.setVisibility(View.GONE);

                checkBoxOp1.setText(ANSWERSRADIO[24]);
                checkBoxOp2.setText(ANSWERSRADIO[25]);
                checkBoxOp3.setText(ANSWERSRADIO[26]);
                checkBoxOp4.setText(ANSWERSRADIO[27]);

                checkBoxOp1.setVisibility(View.VISIBLE);
                checkBoxOp2.setVisibility(View.VISIBLE);
                checkBoxOp3.setVisibility(View.VISIBLE);
                checkBoxOp4.setVisibility(View.VISIBLE);
                break;

            case 9:

                if (!checkBoxOp1.isChecked() || !checkBoxOp4.isChecked()) {

                    if (checkBoxOp2.isChecked() && checkBoxOp3.isChecked()) {
                        score++;
                        Log.i("puntuación",Integer.toString(score));
                    }

                }

                textViewQuestions.setText(QUESTIONS[count]);

                toastCheers3.show();

                if (checkBoxOp1.isChecked()) {
                    checkBoxOp1.setChecked(false);
                }
                if (checkBoxOp2.isChecked()) {
                    checkBoxOp2.setChecked(false);
                }
                if (checkBoxOp3.isChecked()) {
                    checkBoxOp3.setChecked(false);
                }
                if (checkBoxOp4.isChecked()) {
                    checkBoxOp4.setChecked(false);
                }

                checkBoxOp1.setVisibility(View.GONE);
                checkBoxOp2.setVisibility(View.GONE);
                checkBoxOp3.setVisibility(View.GONE);
                checkBoxOp4.setVisibility(View.GONE);

                radioButtonOp1.setText(ANSWERSRADIO[28]);
                radioButtonOp2.setText(ANSWERSRADIO[29]);
                radioButtonOp3.setText(ANSWERSRADIO[30]);
                radioButtonOp4.setText(ANSWERSRADIO[31]);

                radioButtonOp1.setVisibility(View.VISIBLE);
                radioButtonOp2.setVisibility(View.VISIBLE);
                radioButtonOp3.setVisibility(View.VISIBLE);
                radioButtonOp4.setVisibility(View.VISIBLE);

                break;

            case 10:

                if (radioButtonOp2.isChecked()) {
                    score++;
                    Log.i("puntuación",Integer.toString(score));
                }

                radioGroupAnswers.clearCheck();

                radioButtonOp1.setVisibility(View.GONE);
                radioButtonOp2.setVisibility(View.GONE);
                radioButtonOp3.setVisibility(View.GONE);
                radioButtonOp4.setVisibility(View.GONE);

                toastCheers4.show();

                buttonNext.setText(SENDEMAIL);

                if (score > 6) {
                    quizFinished = "¡¡ENHORABUENA!! Tu puntuación ha sido de " + score + "/10. Nos gusta tener usuarios tan entendidos. Esperamos que hayas aprendido y disfrutado.";
                } else {
                    quizFinished = " ¡¡OTRA VEZ SERÁ!! Tu puntuación ha sido de " + score + "/10 respuestas acertadas, aun te queda mucho cine por disfrutar. Gracias por elegirnos para hacerte de guía";
                }

                textViewQuestions.setText(quizFinished);
                textViewBait.setVisibility(View.VISIBLE);

                body = "Mi puntuación en la app FilmDiary Quiz ha sido de " + score + "/10. Para mantener un seguimient ode las películas que veis y de las que no quereis olvidar ver" +
                        " Podeis descargaros FilmDiary app en la Store de Android";
                break;

            case 11:

                composeEmail(adresses, body);

                break;


        }

    }

    public void composeEmail(String[] adresses, String body) {
        final String SUBJECT = "FilmDiary App Quiz Score";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_EMAIL, adresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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


}
