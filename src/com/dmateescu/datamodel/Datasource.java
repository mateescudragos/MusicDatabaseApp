package com.dmateescu.datamodel;

import com.dmateescu.exceptions.DatabaseException;
import com.dmateescu.exceptions.QueryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Datasource {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;


    private static Datasource datasource;

    private List<Song> songs;
    private List<Artist> artists;
    private List<Album> albums;


     private Connection connection;

     private Datasource(){
     }

     public static Datasource getInstance(){
         if (datasource == null){
             datasource = new Datasource();
         }
         return datasource;
     }

     public void open() throws DatabaseException {
         try{
             connection = DriverManager.getConnection(CONNECTION_STRING);
             System.out.println("Database is opened");
         } catch (SQLException e){
             System.out.println("Error while opening Music Database!");
             e.printStackTrace();
             throw new DatabaseException();
         }
     }

     public void close() throws DatabaseException{
         try{
             if (connection!=null) {
                 connection.close();
             }
             System.out.println("Database is closed");
         } catch (SQLException e){
             System.out.println("Error while closing Music Database!");
             e.printStackTrace();
             throw new DatabaseException();
         }
     }

     public List<Artist> queryArtists(int sortOrder) throws QueryException {
         if (artists != null){
             return Collections.unmodifiableList(artists);
         }

         try(Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(buildQueryTableSyntax(TABLE_ARTISTS, COLUMN_ARTIST_NAME, sortOrder))){
             artists = new ArrayList<>();
             while (results.next()){
                 artists.add(new Artist(results.getInt(COLUMN_ARTIST_ID), results.getString(COLUMN_ARTIST_NAME)));
             }
             return artists;
         } catch (SQLException e){
             throw new QueryException(TABLE_ARTISTS);
         }
     }

    public List<Album> queryAlbums(int sortOrder) throws QueryException {
        if (albums != null){
            return Collections.unmodifiableList(albums);
        }

        try(Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(buildQueryTableSyntax(TABLE_ALBUMS, COLUMN_ALBUM_NAME, sortOrder))){
            albums = new ArrayList<>();
            while (results.next()){
                albums.add(new Album(results.getInt(COLUMN_ALBUM_ID), results.getInt(COLUMN_ALBUM_ARTIST), results.getString(COLUMN_ALBUM_NAME)));
            }
            return albums;
        } catch (SQLException e){
            throw new QueryException(TABLE_ALBUMS);
        }
    }

    public List<Song> querySongs(int sortOrder) throws QueryException {
        if (songs != null){
            return Collections.unmodifiableList(songs);
        }
        try(Statement statement = connection.createStatement();
         ResultSet results = statement.executeQuery(buildQueryTableSyntax(TABLE_SONGS, COLUMN_SONG_TITLE, sortOrder))){
            songs = new ArrayList<>();
            while (results.next()){
                songs.add(new Song(results.getInt(COLUMN_SONG_ID), results.getInt(COLUMN_SONG_TRACK),
                        results.getString(COLUMN_SONG_TITLE), results.getInt(COLUMN_SONG_ALBUM)));
            }
            return songs;
        } catch (SQLException e){
            throw new QueryException(TABLE_SONGS);
        }
    }

    private String buildQueryTableSyntax(String tableName, String columnToBeSorted, int sortOrder) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        sb.append(buildSortOrderSyntax(columnToBeSorted, sortOrder));
        return sb.toString();
    }

    private String buildSortOrderSyntax(String column, int sortOrder) {
        if (sortOrder != ORDER_BY_NONE) {
            StringBuilder sb = new StringBuilder();
            sb.append(" ORDER BY " + column);
            if (sortOrder == ORDER_BY_ASC) {
                sb.append(" ASC ");
            } else if (sortOrder == ORDER_BY_DESC) {
                sb.append(" DESC ");
            }
            return sb.toString();
        }
        return "";
    }


}
