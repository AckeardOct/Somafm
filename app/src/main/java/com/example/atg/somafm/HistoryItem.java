package com.example.atg.somafm;

/**
 * Created by atg on 26.12.17.
 */

public class HistoryItem {
    private String artist;
    private String song;
    private String time;

    public HistoryItem(String artist, String song, String time) {
        this.artist = artist;
        this.song = song;
        this.time = time;
    }

    public String toString() {
        return artist + " - " + song;
    }

    public String getArtist() { return artist; }
    public String getSong() { return song; }
    public String getTime() { return time; }
}
