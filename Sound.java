import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    
    public Sound(){
        soundURL[0]=getClass().getResource("som/266163__plasterbrain__pacman-is-dead.wav");
        soundURL[1]=getClass().getResource("som/543386__thedragonsspark__nom-noise.wav");
        soundURL[2]=getClass().getResource("som/609640__chungus43a__yoshi-super-mario-voice.wav");
    }
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip=AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){}

    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}
