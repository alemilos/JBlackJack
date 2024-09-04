package misc;

import javax.sound.sampled.*;
import java.io.*;

public class AudioManager {
    private static AudioManager instance;
    private Clip clip;
    private boolean homeSongPlaying;

    public static AudioManager getInstance() {
        if (instance == null){
            instance = new AudioManager();
        }
        return instance;
    }

    private AudioManager(){}

    /**
     * Loop the home song so that it never ends until another clip is created.
     */
    public void playHomeSongOnRepeat(){
        if(!homeSongPlaying) {
            try {
                InputStream in = new BufferedInputStream(new FileInputStream("./assets/sounds/homesound.wav"));
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                homeSongPlaying = true;

            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (UnsupportedAudioFileException uafe) {
                uafe.printStackTrace();
            } catch (LineUnavailableException lue) {
                lue.printStackTrace();
            }
        }
    }

    /**
     * Play a sound and stop the previous one if exists.
     * @param sound
     */
    public void play(Sounds sound){
        homeSongPlaying = false;
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

    /**
     * Map the sounds to their filepath
     * @param sound
     * @return
     */
    private String getSoundFilename(Sounds sound){
        String path = "./assets/sounds/";

        switch (sound){
            case SHUFFLE -> {return path + "shuffle.wav";}
            case BUSTED -> {return path + "busted.wav";}
            case DEALER_BUSTED -> {return path + "dealerbusted.wav";}
            case LOSE -> { return path + "lose.wav" ;}
            case KNOCK -> { return path + "knock.wav";}
            case MONEY -> { return path + "money.wav";}
            case BLACKJACK -> { return path +"blackjack.wav";}
            case CARD_DEAL -> { return path + "carddeal.wav";}
            case CHIPS_BET -> { return path + "betchip.wav";}
            default -> {return "";}
        }
    }

    /**
     * Stop the sound
     */
    public void stop(){
        clip.flush();
        clip.stop();
    }
}
