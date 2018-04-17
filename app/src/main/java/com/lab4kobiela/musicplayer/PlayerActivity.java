package com.lab4kobiela.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    static final String SONG_INDEX = "SongIndex";
    static final int REW = 5000;
    MediaPlayer mediaPlayer;

    ImageView ivPhoto;
    TextView tvTitle, tvTime;
    ImageButton bPlayPause, bRewBack, bRewForw, bPrevious, bNext;
    SeekBar sbProgress;
    Handler mHandler;

    int songIndex = 0;
    int songLength;
    String lengthTextBase;


    private void handling() {
        PlayerActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaPlayer != null) {
                    sbProgress.setProgress(Math.round((0f + mediaPlayer.getCurrentPosition()) / songLength * 100));
                }
                mHandler.postDelayed(this, 500);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (savedInstanceState == null) getArgs();

        ivPhoto = (ImageView) findViewById(R.id.imageView);
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvTime = (TextView) findViewById(R.id.textViewTime);
        bPlayPause = (ImageButton) findViewById(R.id.imageButtonPlayPause);
        bRewBack = (ImageButton) findViewById(R.id.imageButtonRewBack);
        bRewForw = (ImageButton) findViewById(R.id.imageButtonRewForw);
        bPrevious = (ImageButton) findViewById(R.id.imageButtonPrevious);
        bNext = (ImageButton) findViewById(R.id.imageButtonNext);
        sbProgress = (SeekBar) findViewById(R.id.seekBar);

        setDetails();

        mHandler = new Handler();

        handling();

        bPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    bPlayPause.setImageResource(R.drawable.button_play);
                } else {
                    mediaPlayer.start();
                    bPlayPause.setImageResource(R.drawable.button_pause);
                }
            }
        });

        bRewForw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + REW);
            }
        });

        bRewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - REW);
            }
        });

        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mediaPlayer.seekTo(Math.round(progress / 100f * mediaPlayer.getDuration()));
                int curTime = mediaPlayer.getCurrentPosition();
                int min = curTime / 60000;
                int sec = (curTime / 1000) % 60;
                tvTime.setText(min + ":" + (sec < 10 ? ("0" + sec) : sec) + lengthTextBase);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                String result = lengthTextBase.substring(3, lengthTextBase.length()) + lengthTextBase;
                tvTime.setText(result);
                bPlayPause.setImageResource(R.drawable.button_play);
            }
        });

        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songIndex = (songIndex - 1) < 0 ? (MainActivity.songList.size() - 1) : (songIndex - 1);
                startNewSong();
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songIndex = (songIndex + 1) % (MainActivity.songList.size());
                startNewSong();
            }
        });
    }

    private void startNewSong() {
        mediaPlayer.stop();
        setDetails();
        handling();
    }

    private void setDetails() {
        ivPhoto.setImageResource(MainActivity.songList.get(songIndex).posterId);
        tvTitle.setText(MainActivity.songList.get(songIndex).author + " - " + MainActivity.songList.get(songIndex).name);
        createStartMP(songIndex);

        songLength = mediaPlayer.getDuration();
        int min = songLength / 60000;
        int sec = (songLength / 1000) % 60;
        lengthTextBase = " / " + min + ":" + (sec < 10 ? ("0" + sec) : sec);
        tvTime.setText(lengthTextBase);
    }

    private void getArgs() {
        songIndex = getIntent().getIntExtra(SONG_INDEX, 0);
    }

    static public Intent getStartIntent(Context context, int index) {
        Intent intent = new Intent(context, PlayerActivity.class);
        return intent.putExtra(SONG_INDEX, index);
    }


    public void createStartMP(int position) {
        int resID = MainActivity.songList.get(position).songId;
        mediaPlayer = MediaPlayer.create(PlayerActivity.this, resID);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.stop();
    }

}
