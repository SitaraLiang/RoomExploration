package Vue;

import Controleur.GameControleur;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.*;

public class VueTerminate extends JPanel {
    private GameControleur controleur;
    private GameUI window;
    private JLabel message, score;
    private JButton again, quit;
    private Clip clip;

    public VueTerminate(GameUI window, GameControleur controleur) {
        super();
        this.window = window;
        this.controleur = controleur;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());


        System.out.println(controleur.getMessage());
        message = new JLabel(controleur.getMessage());
        message.setForeground(Color.PINK);
        message.setOpaque(false);
        message.setFont(new Font("Times New Roman", Font.BOLD, 32));
        System.out.println("Your final score is " + controleur.getScore());
        score = new JLabel("Your final score is " + controleur.getScore());
        score.setForeground(Color.PINK);
        score.setOpaque(false);
        score.setFont(new Font("Times New Roman", Font.BOLD, 32));

        again = new JButton("Play Again");
        quit = new JButton("Quit");
        again.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        again.setOpaque(false);
        again.setForeground(Color.PINK);
        again.setBorder(new MatteBorder(2, 2, 2, 2, Color.PINK));
        again.setPreferredSize(new Dimension(140, 60));
        quit.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        quit.setOpaque(false);
        quit.setForeground(Color.PINK);
        quit.setBorder(new MatteBorder(2, 2, 2, 2, Color.PINK));
        quit.setPreferredSize(new Dimension(100, 50));

        GridBagConstraints gbcMsg = new GridBagConstraints();
        gbcMsg.gridx = 0;
        gbcMsg.gridy = 0;
        gbcMsg.insets = new Insets(30, 0, 0, 0);
        add(message, gbcMsg);

        GridBagConstraints gbcScore = new GridBagConstraints();
        gbcScore.gridx = 0;
        gbcScore.gridy = 1;
        gbcScore.insets = new Insets(10, 0, 100, 0);
        add(score, gbcScore);

        GridBagConstraints gbcAgain = new GridBagConstraints();
        gbcAgain.gridx = 0;
        gbcAgain.gridy = 2;
        gbcAgain.insets = new Insets(0, 0, 30, 0);
        add(again, gbcAgain);

        GridBagConstraints gbcQuit = new GridBagConstraints();
        gbcQuit.gridx = 0;
        gbcQuit.gridy = 3;
        gbcQuit.insets = new Insets(0, 0, 30, 0);
        add(quit, gbcQuit);

        quit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.ClickButton.getFilepath());
                System.out.println("Quit the game.");
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        again.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.ClickButton.getFilepath());
                System.out.println("Play again.");
                window.showLevels();
                window.stopMusic();
                window.playMusic(MusicSamples.BackgroundMusic.getFilepath());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }


    private void playSoundEffect(String filename) {
        try {
            // Create an audio input stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());

            // Get a Clip object
            clip = AudioSystem.getClip();

            // Open the audio input stream
            clip.open(audioInputStream);

            // Start playing the sound effect
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

