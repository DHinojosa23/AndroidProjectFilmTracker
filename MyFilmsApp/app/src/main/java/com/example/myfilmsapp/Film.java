package com.example.myfilmsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    private int id;
    private String title;
    private int year;
    private String country;
    private String director;
    private String genre;
    private float rating;
    private String description;
    //los siguientes atributos son invisibles para el usuario, necesarios para controlar las consultas de datos de modo óptimo.
    private String dateWatched;
    private String type;
    private int watched;
    private int pending;
    private int countryImg;
    private String youtubeUrl;

    public Film() {

    }

    public Film(String title, int year, String country, String director, String genre, float rating, String description) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.description = description;

    }

    public Film(int id, String title, int year, String country, String director, String genre, float rating, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.description = description;

    }


    public Film(int id, String title, int year, String country, String director, String genre, float rating, String description, String dateWatched, String type, int watched, int pending) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.dateWatched = dateWatched;
        this.type = type;
        this.watched = watched;
        this.pending = pending;
    }

    public Film(int id, String title, int year, String country, String director, String genre, float rating, String description, String dateWatched, String type, int watched, int pending, int countryImg) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.dateWatched = dateWatched;
        this.type = type;
        this.watched = watched;
        this.pending = pending;
        this.countryImg = countryImg;
    }

    public Film(int id, String title, int year, String country, String director, String genre, float rating, String description, String dateWatched, String type, int watched, int pending, int countryImg, String youtubeUrl) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.dateWatched = dateWatched;
        this.type = type;
        this.watched = watched;
        this.pending = pending;
        this.countryImg = countryImg;
        this.youtubeUrl = youtubeUrl;
    }

    public Film(int id, String title, int year, String country, String director, String genre, float rating, String description, String dateWatched, String type, int watched, int pending, String youtubeUrl) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.dateWatched = dateWatched;
        this.type = type;
        this.watched = watched;
        this.pending = pending;
        this.youtubeUrl = youtubeUrl;
    }

    public Film(Parcel in) {
        id = in.readInt();
        title = in.readString();
        year = in.readInt();
        country = in.readString();
        director = in.readString();
        genre = in.readString();
        rating = in.readFloat();

        description = in.readString();
        dateWatched = in.readString();
        type = in.readString();

        watched = in.readInt();
        pending = in.readInt();

        youtubeUrl = in.readString();



    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public String getDateWatched() {
        return dateWatched;
    }

    public void setDateWatched(String dateWatched) {
        this.dateWatched = dateWatched;
    }

    public int getCountryImg() {
        return countryImg;
    }

    public void setCountryImg(int countryImg) {
        this.countryImg = countryImg;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {


        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(year);
        dest.writeString(country);
        dest.writeString(director);
        dest.writeString(genre);
        dest.writeFloat(rating);
        dest.writeString(description);
        dest.writeString(dateWatched);
        dest.writeString(type);
        dest.writeInt(watched);
        dest.writeInt(pending);
        dest.writeString(youtubeUrl);

    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", dateWatched='" + dateWatched + '\'' +
                ", type='" + type + '\'' +
                ", watched=" + watched +
                ", pending=" + pending +
                ", countryImg=" + countryImg +
                '}';
    }
}
