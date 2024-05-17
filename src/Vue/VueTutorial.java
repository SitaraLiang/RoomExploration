package Vue;

import Controleur.GameControleur;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.*;

public class VueTutorial extends JPanel {
    private GameControleur controleur = null;
    private GameUI window = null;
    private JList<String> lst = null;
    private JButton button = null;
    private Clip clip;

    public VueTutorial(GameUI window, GameControleur controleur) {
        super();
        this.window = window;
        this.controleur = controleur;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Game Guide", JLabel.LEFT);
        title.setOpaque(false);
        title.setForeground(Color.PINK);
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("J: Joueur/Player");
        listModel.addElement("V: Vampire");
        listModel.addElement("W: Loup-garou/Werewolf");
        listModel.addElement("T: Tresor/Treasure");
        listModel.addElement("E: Epee/Sword");
        listModel.addElement("P: Porte/Door");
        listModel.addElement("S: Sortie/Exit");

        lst = new JList<>(listModel);
        lst.setOpaque(false);
        lst.setBackground(Color.BLACK);
        lst.setForeground(Color.PINK);
        lst.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lst.setBorder(new MatteBorder(1, 1, 1, 1, Color.PINK));

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setMinimumSize(new Dimension(1000, 50));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.PINK);
        String paragraph = "Players can collect the treasures and swords during the game, use them to defend themselves or kill the monstres. The final score is calculated based on the number of objects collected and the attack to monstres. Players travel through the rooms through different doors until they reach the exit." +
                "\n     Vampire - The player is forced to return to the starting position without objects refreshing." +
                "\n     Werewolf - Bites the player, reducing life points." +
                "\n     Treasure - Restore part of the player's life points." +
                "\n     Sword - Attack the monsters.";

        textArea.setText(paragraph);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));


        button = new JButton("Next");
        button.setFont(new Font("Times New Roman", Font.ITALIC, 23));
        button.setOpaque(false);
        button.setForeground(Color.PINK);
        button.setBorder(null);
        button.setPreferredSize(new Dimension(90, 40));
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.ClickButton.getFilepath());
                window.showLevels();
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
        gbcTitle.insets = new Insets(30, 0, 40, 0);
        add(title, gbcTitle);

        GridBagConstraints gbcLst = new GridBagConstraints();
        gbcLst.gridx = 0;
        gbcLst.gridy = 1;
        gbcLst.insets = new Insets(0, 0, 20, 0);
        add(lst, gbcLst);

        GridBagConstraints gbcText = new GridBagConstraints();
        gbcText.gridx = 0;
        gbcText.gridy = 2;
        gbcText.insets = new Insets(0, 0, 0, 0);
        add(textArea, gbcText);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 3;
        gbcButton.insets = new Insets(20, 0, 0, 0);
        add(button, gbcButton);

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
