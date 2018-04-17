package com.lab4kobiela.musicplayer;

/**
 * Created by Mariusz on 2017-06-17.
 */

class Song {
    String name;
    String author;
    int songId;
    int posterId;

    Song(String name, String author, int songId, int posterId) {
        this.name = name;
        this.author = author;
        this.songId = songId;
        this.posterId = posterId;
    }

}
