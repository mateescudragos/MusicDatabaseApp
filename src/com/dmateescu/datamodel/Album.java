package com.dmateescu.datamodel;

public class Album {
    private int id;
    private String name;
    private int artist;

    public Album(int id, int artist, String name) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getArtist() {
        return artist;
    }
}
