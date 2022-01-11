package com.example.wytwornia;

import javafx.beans.property.SimpleStringProperty;

public class Film {
    private String title;
    private String director;
    private String genre;

    public Film(String title, String director, String genre) {
        this.title = title;
        this.director = director;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}