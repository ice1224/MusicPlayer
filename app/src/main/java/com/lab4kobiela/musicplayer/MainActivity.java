package com.lab4kobiela.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();


        for (int i = 0; i < songList.size(); i++) {
            list.add(songList.get(i).author + " - " + songList.get(i).name);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = PlayerActivity.getStartIntent(MainActivity.this, position);
                startActivity(intent);
            }
        });
    }


    public static List<Song> songList = new ArrayList<>(Arrays.asList(new Song("Sirena", "Dirty Three", R.raw.dirtythree_sirena, R.drawable.sirena),
            new Song("Time Jesum Transeuntum Et Non Riverentum", "Nick Cave", R.raw.nickcave_timejesum, R.drawable.timejesum),
            new Song("The Restless Waves", "Dirty Three", R.raw.dirtythree_therestlesswaves, R.drawable.therestlesswaves),
            new Song("Bohemian Rhapsody", "Quuen", R.raw.queen_bohemianrhapsody, R.drawable.bohemianrhapsody),
            new Song("The Czar", "Mastodon", R.raw.mastodon_thaczar, R.drawable.czar)));
}