package unsw.loopmania;

import javafx.scene.media.AudioClip;
import java.io.File;

public class Sound {
    public void playBackground(){
        AudioClip background = new AudioClip(new File("sounds/bgm.mp3").toURI().toString());
        background.setCycleCount(AudioClip.INDEFINITE);
        background.play();
    }

    public void playLost(){
        AudioClip lostAudioClip = new AudioClip(new File("sounds/lost.mp3").toURI().toString());
        lostAudioClip.setCycleCount(AudioClip.INDEFINITE);
        lostAudioClip.play();
    }

    public void playWin(){
        AudioClip WinAudioClip = new AudioClip(new File("sounds/win.mp3").toURI().toString());
        WinAudioClip.setCycleCount(AudioClip.INDEFINITE);
        WinAudioClip.play();
    }

    public void stopBackground(){
        AudioClip background = new AudioClip(new File("sounds/bgm.mp3").toURI().toString());
        background.stop();
    }

    public void stopLost(){
        AudioClip lostAudioClip = new AudioClip(new File("sounds/lost.mp3").toURI().toString());
        lostAudioClip.stop();
    }

    public void stopWin(){
        AudioClip WinAudioClip = new AudioClip(new File("sounds/win.mp3").toURI().toString());
        WinAudioClip.stop();
    }
    
}
