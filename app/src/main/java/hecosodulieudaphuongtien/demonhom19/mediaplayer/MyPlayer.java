package hecosodulieudaphuongtien.demonhom19.mediaplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.entity.Audio;
import hecosodulieudaphuongtien.demonhom19.ui.MainActivity;

/**
 * Created by admin on 3/29/2016.
 */
public class MyPlayer implements MediaPlayer.OnPreparedListener, View.OnClickListener {
    private MainActivity activity;
    private MediaPlayer mediaPlayer;
    int SEEK_TIME = 5000;

    private TextView tv_Title;
    private LinearLayout btnPlay, btnForward, btnBackward;
    private ImageView iconPlay;
    private RelativeLayout viewPlayer, layoutTrick;

    public MyPlayer(MainActivity activity) {
        this.activity = activity;
        init();
        mediaPlayer = new MediaPlayer();
    }

    public void init() {
        tv_Title = (TextView) activity.findViewById(R.id.tv_title);
        iconPlay = (ImageView) activity.findViewById(R.id.iv_play);
        btnBackward = (LinearLayout) activity.findViewById(R.id.btn_backward);
        btnForward = (LinearLayout) activity.findViewById(R.id.btn_forward);
        btnPlay = (LinearLayout) activity.findViewById(R.id.btn_play);
        viewPlayer = (RelativeLayout) activity.findViewById(R.id.viewPlayer);
        layoutTrick = (RelativeLayout) activity.findViewById(R.id.layout_trick);
        viewPlayer.setVisibility(View.GONE);
        btnPlay.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnForward.setOnClickListener(this);
    }

    public void playOnline(Audio audio) {
        if (viewPlayer.getVisibility() != View.VISIBLE) {
            viewPlayer.setVisibility(View.VISIBLE);
            layoutTrick.setVisibility(View.VISIBLE);
        }
        tv_Title.setText(audio.title);
        iconPlay.setBackgroundResource(R.drawable.pause_circle);
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audio.getURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(MyPlayer.this);
        mediaPlayer.prepareAsync();
    }

    public void playOffline(Audio audio) {
        if (viewPlayer.getVisibility() != View.VISIBLE) {
            viewPlayer.setVisibility(View.VISIBLE);
            layoutTrick.setVisibility(View.VISIBLE);
        }
        tv_Title.setText(audio.title);
        iconPlay.setBackgroundResource(R.drawable.pause_circle);
        mediaPlayer.reset();
        String mediaPath = "sdcard/Music/" + audio.title + ".mp3";
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri uri = Uri.parse(mediaPath);
        try {
            mediaPlayer.setDataSource(activity.getApplicationContext(), uri);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seekForward() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        if (currentPosition + SEEK_TIME <= mediaPlayer.getDuration()) {
            mediaPlayer.seekTo(currentPosition + SEEK_TIME);
        } else mediaPlayer.seekTo(mediaPlayer.getDuration());
    }

    public void seekBackward() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        if (currentPosition - SEEK_TIME >= 0) {
            mediaPlayer.seekTo(currentPosition - SEEK_TIME);
        } else mediaPlayer.seekTo(0);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    iconPlay.setBackgroundResource(R.drawable.play_circle);
                } else {
                    mediaPlayer.start();
                    iconPlay.setBackgroundResource(R.drawable.pause_circle);
                }
                break;
            case R.id.btn_backward:
                seekBackward();
                break;
            case R.id.btn_forward:
                seekForward();
                break;
        }
    }
}
