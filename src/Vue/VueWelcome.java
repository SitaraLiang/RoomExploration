package Vue;
import Controleur.GameControleur;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.*;

public class VueWelcome extends JPanel{
    private GameControleur controleur = null;
    private Clip clip;
    private GameUI window = null;
    private JLabel title = null;
    private JButton button = null;
    private Color[] colors = {Color.RED, Color.BLUE, Color.PINK, Color.ORANGE};
    private int colorIndex = 0;
    public VueWelcome(GameUI window, GameControleur controleur) {
        super();
        this.controleur = controleur;
        this.window = window;
        setBackground(Color.BLACK);

        title = new JLabel("Room Exploration", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 55));
        title.setOpaque(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new GridBagLayout());
        button = new JButton("Enter");

        button.setFont(new Font("Times New Roman", Font.ITALIC, 23));
        button.setOpaque(false);
        button.setForeground(Color.PINK);
        button.setBorder(new MatteBorder(2, 2, 2, 2, Color.PINK));
        button.setPreferredSize(new Dimension(100, 50));
        button.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        button.addMouseListener(new MouseListener() {
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

        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(-100, 0, 30, 0);
        add(title, gbcTitle);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 1;
        gbcButton.insets = new Insets(30, 0, 0, 0);
        add(button, gbcButton);

        Timer timer = new Timer(700, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTitleLabel();
            }
        });
        timer.start();

    }

    private void updateTitleLabel() {
        title.setForeground(colors[colorIndex]);
        colorIndex = (colorIndex + 1) % colors.length;
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

