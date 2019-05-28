package com.example.myfilmsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {

    public FilmAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Film> words) {
        super(context, resource, words);
    }

    //método para devolver nuestro list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View list_item = convertView;

        //si la lista está vacia la inflamos
        if (list_item == null) {
            list_item = LayoutInflater.from(getContext()).inflate(R.layout.item_list_films, parent, false);
        }
        //Traemos cada uno de nuestras words de nuestro Array
        Film currentFilm = getItem(position);

        TextView textViewTitle = (TextView) list_item.findViewById(R.id.textViewTitle);
        textViewTitle.setText(currentFilm.getTitle());

        TextView textViewYear = (TextView) list_item.findViewById(R.id.textViewYear);
        textViewYear.setText(Integer.toString(currentFilm.getYear()));

        TextView textViewCountry = (TextView) list_item.findViewById(R.id.textViewCountry);
        textViewCountry.setText(currentFilm.getCountry());

        ImageView imageViewImage = (ImageView) list_item.findViewById(R.id.imageViewCountry);

        TextView textViewDateWatched = (TextView) list_item.findViewById(R.id.textViewDateWatched);
        textViewDateWatched.setText(currentFilm.getDateWatched());

        RatingBar ratingBarList = (RatingBar) list_item.findViewById(R.id.ratingBarList);
        ratingBarList.setRating(currentFilm.getRating());

        //sí la puntuación es cero, no mostramos el ratingBar
        if(currentFilm.getRating()==0){
            ratingBarList.setVisibility(View.GONE);
        }else{
            ratingBarList.setVisibility(View.VISIBLE);
        }

        //check if we have any image to implement
        if(currentFilm.getCountryImg()!=0) {
            imageViewImage.setImageResource(currentFilm.getCountryImg());
        }



        return list_item;

    }

}

