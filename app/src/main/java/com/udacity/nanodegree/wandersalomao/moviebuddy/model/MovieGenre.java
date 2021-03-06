package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Class that represents a Movie Genre.
 * @author Wander Salomao
 */
@Parcel
public class MovieGenre {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
