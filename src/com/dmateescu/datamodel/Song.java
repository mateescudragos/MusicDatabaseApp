package com.dmateescu.datamodel;

public class Song {
    private int id;
    private int track;
    private String title;
    private int album;

    public Song(int id, int track, String title, int album) {
        this.id = id;
        this.track = track;
        this.title = title;
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public int getTrack() {
        return track;
    }

    public String getTitle() {
        return title;
    }

    public int getAlbum() {
        return album;
    }
}
