package com.hightech.arcade.JungleRun;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import com.hightech.arcade.R;

public class Sounds {
    private SoundPool soundPool;
    private MediaPlayer backgroundSound;
    private int deathSound, jumpSound, coinPickSound;
    Sounds(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }
        backgroundSound = MediaPlayer.create(context, R.raw.junglerun_backgroundsound);
        deathSound = soundPool.load(context, R.raw.junglerun_deathsound, 1);
        jumpSound = soundPool.load(context, R.raw.junglerun_jumpsound, 1);
        coinPickSound = soundPool.load(context, R.raw.junglerun_coinpicksound, 1);
    }

    public void playBackgroundSound() {
        backgroundSound.start();
    }

    public void pauseBackgroundSound() {
        backgroundSound.pause();
    }

    public void stopBackgroundSound() {
        backgroundSound.stop();
    }

    public void playJumpSound() {
        soundPool.play(jumpSound, 1, 1, 0, 0, 1);
    }

    public void playCoinPickSound() {
        soundPool.play(coinPickSound, 1, 1, 0, 0, 1);
    }

    public void playDeathSound() {
        soundPool.play(deathSound, 1, 1, 0, 0, 1);
    }

}
