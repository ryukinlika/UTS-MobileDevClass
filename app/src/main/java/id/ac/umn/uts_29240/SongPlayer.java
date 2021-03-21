package id.ac.umn.uts_29240;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class SongPlayer extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl{
    private TextView tvTitle;
    private Button fwButton, backButton, ppButton; //pause-play
    private MediaPlayer mediaplayer;
    private MediaController mediacontroller;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_player_activity);

        this.setTitle("Now Playing");

        tvTitle = findViewById(R.id.playerTitle);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        SongModel song = (SongModel) bundle.getSerializable("Detail");

        tvTitle.setText(song.getTitle());

        mediaplayer = new MediaPlayer();
        mediaplayer.setOnPreparedListener(this);
        mediacontroller = new MediaController(this){
            @Override
            public void show (int timeout) {
                if (timeout == 3000) timeout = 0; //Set to desired number
                super.show(timeout);
            }
            public boolean dispatchKeyEvent(KeyEvent event)
            {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    ((Activity) getContext()).finish();

                return super.dispatchKeyEvent(event);
            }
        };

        try {
            mediaplayer.setDataSource(song.getSongURI());
            mediaplayer.prepare();
            mediaplayer.start();
        } catch (IOException e) {
            Log.e("SongPlayer", "Could not open file " + song.getSongURI() + " for playback.", e);
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        mediacontroller.hide();
        mediaplayer.stop();
        mediaplayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("SongPlayer", "onPrepared");
        mediacontroller.setMediaPlayer(this);
        mediacontroller.setAnchorView(findViewById(R.id.controlLayout));

        handler.post(new Runnable() {
            public void run() {
                mediacontroller.setEnabled(true);
                mediacontroller.show(0);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return true;
    }

    @Override
    public void start() {
        mediaplayer.start();
    }

    @Override
    public void pause() {
        mediaplayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaplayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaplayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaplayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaplayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
