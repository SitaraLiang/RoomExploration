package Vue;

import Controleur.GameControleur;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;

import Exception.LevelOutOfBoundException;

public class VueLevel extends JPanel {
    private GameControleur controleur = null;
    private GameUI window = null;
    private Clip clip;
    public VueLevel(GameUI window, GameControleur controleur) {
        super();
        this.window = window;
        this.controleur = controleur;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Choose your Level");
        title.setForeground(Color.PINK);
        title.setOpaque(false);
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));

        JButton btn1 = new JButton("Rookie");
        JButton btn2 = new JButton("Experienced");
        JButton btn3 = new JButton("Master");
        JButton back = new JButton("Back");
        ArrayList<JButton> buttonList = new ArrayList<>();
        buttonList.add(btn1);
        buttonList.add(btn2);
        buttonList.add(btn3);
        buttonList.add(back);
        for (JButton button : buttonList) {
            button.setFont(new Font("Times New Roman", Font.ITALIC, 23));
            button.setOpaque(false);
            button.setForeground(Color.PINK);
            button.setBorder(new MatteBorder(2, 2, 2, 2, Color.PINK));
            button.setPreferredSize(new Dimension(100, 50));
        }

        btn2.setPreferredSize(new Dimension(150, 50));
        back.setBorder(null);

        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(30, 0, 100, 0);
        add(title, gbcTitle);

        GridBagConstraints gbcBtn1 = new GridBagConstraints();
        gbcBtn1.gridx = 0;
        gbcBtn1.gridy = 1;
        gbcBtn1.insets = new Insets(0, 0, 30, 0);
        add(btn1, gbcBtn1);

        GridBagConstraints gbcBtn2 = new GridBagConstraints();
        gbcBtn2.gridx = 0;
        gbcBtn2.gridy = 2;
        gbcBtn2.insets = new Insets(0, 0, 30, 0);
        add(btn2, gbcBtn2);

        GridBagConstraints gbcBtn3 = new GridBagConstraints();
        gbcBtn3.gridx = 0;
        gbcBtn3.gridy = 3;
        gbcBtn3.insets = new Insets(0, 0, 0, 0);
        add(btn3, gbcBtn3);

        GridBagConstraints gbcBack = new GridBagConstraints();
        gbcBack.gridx = 0;
        gbcBack.gridy = 4;
        gbcBack.insets = new Insets(50, 0, 0, 0);
        add(back, gbcBack);

        btn1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSoundEffect(MusicSamples.ClickButton.getFilepath());
                    window.showGame(0);
                } catch (LevelOutOfBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
        btn2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSoundEffect(MusicSamples.ClickButton.getFilepath());
                    window.showGame(1);
                } catch (LevelOutOfBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
        btn3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSoundEffect(MusicSamples.ClickButton.getFilepath());
                    window.showGame(2);
                } catch (LevelOutOfBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
        back.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.ClickButton.getFilepath());
                window.showTutorial();
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
