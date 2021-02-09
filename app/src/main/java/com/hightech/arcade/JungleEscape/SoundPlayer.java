package com.hightech.arcade.JungleEscape;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import com.hightech.arcade.R;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 3;
    private static SoundPool soundPool;
    private static int pickSound;
    private static int gameOverSound;

    public SoundPlayer(Context context){

        // SoundPool is deprecated in API 21 (Lollipop)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }else{
            //SoundPool (int maxStreams, int streamType, int srcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        // CAMBIARE HITSOUND
        pickSound = soundPool.load(context, R.raw.jungleescape_banana_pick, 1);
        gameOverSound = soundPool.load(context, R.raw.jungleescape_gameover, 1);
    }

    public void playPickSound(){

        //doodle_play (int soundID,, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(pickSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playGameOverSound(){

        //doodle_play (int soundID,, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(gameOverSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
