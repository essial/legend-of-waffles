package com.lunaticedit.legendofwaffles.implementations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.lunaticedit.legendofwaffles.enums.SoundEffect;

public class MusicPlayer {
    private static MusicPlayer _instance = null;
    final private Sound[] _soundEffects;
    private Music _music;

    private MusicPlayer() {
        _soundEffects = new Sound[7];
        _soundEffects[0] = Gdx.audio.newSound(Gdx.files.internal("boxhit.wav"));
        _soundEffects[1] = Gdx.audio.newSound(Gdx.files.internal("cannonshot.wav"));
        _soundEffects[2] = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        _soundEffects[3] = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        _soundEffects[4] = Gdx.audio.newSound(Gdx.files.internal("footstep.wav"));
        _soundEffects[5] = Gdx.audio.newSound(Gdx.files.internal("swordswing.wav"));
        _soundEffects[6] = Gdx.audio.newSound(Gdx.files.internal("crabdeath.wav"));
    }

    public static synchronized MusicPlayer getInstance() {
        if (_instance == null) {
            _instance = new MusicPlayer();
        }
        return _instance;
    }

    public static void playSound(final SoundEffect soundEffect) {

        switch (soundEffect) {
            case BoxHit:     getInstance()._soundEffects[0].play(); break;
            case CannonShot: getInstance()._soundEffects[1].play(); break;
            case Coin:       getInstance()._soundEffects[2].play(); break;
            case Jump:       getInstance()._soundEffects[3].play(); break;
            case Footstep:   getInstance()._soundEffects[4].play(); break;
            case SwordSwing: getInstance()._soundEffects[5].play(); break;
            case CrabDeath:  getInstance()._soundEffects[6].play(); break;

        }
    }

    public synchronized void playSong(final String ResourceName) {
        if (_music != null) {
            _music.stop();
            _music.dispose();
            _music = null;
        }
        _music = Gdx.audio.newMusic(Gdx.files.internal(ResourceName));
        _music.play();
    }



    public void stopSong() {
        if (_music == null) {
            return;
        }
        _music.stop();
    }
}
