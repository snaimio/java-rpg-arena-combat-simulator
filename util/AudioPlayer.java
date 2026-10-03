
package util;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;

public class AudioPlayer {
    private Clip music;
    private boolean playing = false;
    private ArrayList<Clip> sounds = new ArrayList<>();
    
    public AudioPlayer() {}
    
    public void playBackgroundMusic(String path) {
        try {
            if(music != null && music.isRunning()) {
                music.stop();
                music.close();
            }
            File f = new File(path);
            if(!f.exists()) return;
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            music = AudioSystem.getClip();
            music.open(ais);
            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();
            playing = true;
        } catch(Exception e) {}
    }
    
    public void playSoundEffect(String path) {
        new Thread(() -> {
            try {
                File f = new File(path);
                if(!f.exists()) return;
                AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                sounds.add(clip);
                clip.addLineListener(e -> {
                    if(e.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        sounds.remove(clip);
                    }
                });
                clip.start();
            } catch(Exception e) {}
        }).start();
    }
    
    public void stopBackgroundMusic() {
        if(music != null && music.isRunning()) {
            music.stop();
            music.close();
            playing = false;
        }
    }
    
    public boolean isPlaying() {
        return playing;
    }
}