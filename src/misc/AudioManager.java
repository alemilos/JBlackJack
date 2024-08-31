package misc;

import javax.sound.sampled.*;
import java.io.*;

public class AudioManager {
    private static AudioManager instance;
    private Clip clip;

    public static AudioManager getInstance() {
        if (instance == null){
            instance = new AudioManager();
        }
        return instance;
    }

    private AudioManager(){}

    public void play(String filename){
        if (clip != null){
            stop();
        }

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filename));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        }catch (FileNotFoundException fnfe){
           fnfe.printStackTrace();
        } catch(IOException ioe){
           ioe.printStackTrace();
        } catch (UnsupportedAudioFileException uafe){
           uafe.printStackTrace();
        } catch (LineUnavailableException lue){
           lue.printStackTrace();
        }
    }

    public void stop(){
        if (clip.isRunning()){
            clip.stop();
        }
    }
}
