package main.sk.tuke.gamestudio.game.core.utils;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
    private Clip clip;

    private MusicPlayer music;

    public void startMusic(boolean selectMusic) {
        try {
            //  File audioFile = new File("/home/yurii/IdeaProjects/Helltaker/src/main.sk/tuke/gamestudio/music/Menu.wav");
            URL url;

            if(!selectMusic) {
                url = getClass().getClassLoader().getResource("music/Menu.wav");
            }
            else{
                url = getClass().getClassLoader().getResource("music/Game.wav");
            }

            assert url != null;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public MusicPlayer getMusic(){
        return music;
    }
    public void setMusic(MusicPlayer musicPlayer){
        this.music = musicPlayer;
    }

}