package Vue;

import Controleur.GameControleur;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import Exception.LevelOutOfBoundException;

public class GameUI extends JFrame {
    private GameControleur controleur;
    private JPanel mainPanneau;
    private Clip clip;
    private final int window_width = 700;
    private final int window_height = 700;
    public GameUI(GameControleur controleur) {
        super();
        this.controleur = controleur;
        setTitle("Jeu d’exploration de salles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanneau = new VueWelcome(this, controleur);
        setContentPane(mainPanneau);
        pack();
        setSize(window_width, window_height);
        setLocationRelativeTo(null);
        playMusic(MusicSamples.BackgroundMusic.getFilepath());
    }

    public void showTutorial() {
        mainPanneau = new VueTutorial(this, controleur);
        setContentPane(mainPanneau);
        repaint();
        pack();
        setSize(window_width, window_height);
        setLocationRelativeTo(null);
    }

    public void showLevels() {
        mainPanneau = new VueLevel(this, controleur);
        setContentPane(mainPanneau);
        repaint();
        pack();
        setSize(window_width, window_height);
        setLocationRelativeTo(null);
    }

    public void showGame(int level) throws LevelOutOfBoundException, IOException {
        stopMusic();
        playMusic(MusicSamples.DuringTheGame.getFilepath());
        controleur.setLevel(level);
        mainPanneau = new VueGame(this, controleur);
        setContentPane(mainPanneau);
        repaint();
        pack();
        setSize(window_width, window_height);
        setLocationRelativeTo(null);
        Thread thread = new Thread(() -> {
            try {
                controleur.startGame((VueGame) mainPanneau);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public void showTerminate() {
        mainPanneau = new VueTerminate(this, controleur);
        setContentPane(mainPanneau);
        repaint();
        pack();
        setSize(window_width, window_height);
        setLocationRelativeTo(null);
    }

    public int getWindow_width() {
        return window_width;
    }

    public int getWindow_height() {
        return window_height;
    }


    public void playMusic(String filename) {
        try {
            // Create an audio input stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());

            // Get a Clip object
            clip = AudioSystem.getClip();

            // Open the audio input stream
            clip.open(audioInputStream);

            // Loop the music
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start playing the music
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


}
