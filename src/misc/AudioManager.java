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

    public void play(Sounds sound){
        if (clip != null){
            stop();
        }

        String filename = getSoundFilename(sound);

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

    private String getSoundFilename(Sounds sound){
        String path = "./assets/sounds/";

        switch (sound){
            case HOME -> {return path + "homesound.wav";}
            case BUSTED -> {return path + "busted.wav";}
            case LOSE -> { return path + "lose.wav" ;}
            case KNOCK -> { return path + "knock.wav";}
            case MONEY -> { return path + "money.wav";}
            case BLACKJACK -> { return path +"blackjack.wav";}
            case CARD_DEAL -> { return path + "carddeal.wav";}
            case CHIPS_BET -> { return path + "betchip.wav";}
            default -> {return "";}
        }
    }

    public void stop(){
        clip.flush();
        clip.stop();
    }
}
