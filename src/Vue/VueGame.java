package Vue;

import Controleur.GameControleur;
import Modele.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.*;

public class VueGame extends JPanel implements ChangementPositionListener, MonsterAttackListener{
    private GameControleur controleur;
    private GameUI window;
    private JPanel panneau, scoreBar;
    private JButton up, right, down, left, collect, useSword, useTreasor, quit;
    private int nb_lignes, nb_colonnes;
    private final int cell_taille;
    private EnsDeSalles ensdesalles;
    private JLabel score, life;
    private Clip clip;

    public VueGame(GameUI window, GameControleur controleur) {
        super();
        this.nb_lignes = controleur.getNbLignes();
        this.nb_colonnes = controleur.getNbColonnes();
        this.controleur = controleur;
        this.ensdesalles = controleur.getGrid();
        this.window = window;
        window.setTitle("Level " + controleur.getLevel());
        if (controleur.getLevel() == 0) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 25));
            cell_taille = 47;
        } else if (controleur.getLevel() == 1) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
            cell_taille = 35;
        } else {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
            cell_taille = 30;
        }
        int grid_height = cell_taille*nb_lignes;
        int grid_width = cell_taille*nb_colonnes;
        setBackground(Color.BLACK);

        panneau = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Jeton[][] grid = ensdesalles.getGrid();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, grid_width, grid_height);
                if (controleur.getLevel() == 0) {
                    g.setFont(new Font("Times New Roman", Font.BOLD, 30));
                } else {
                    g.setFont(new Font("Times New Roman", Font.BOLD, 23));
                }

                for (int i = 0; i < nb_lignes; i++) {
                    for (int j = 0; j < nb_colonnes; j++) {
                        if (grid[i][j] == Jeton.WALL) {
                            g.setColor(Color.PINK);
                            g.fillRect(j*cell_taille, i*cell_taille, cell_taille, cell_taille);
                        } else if (grid[i][j] == Jeton.PORTE) {
                            g.setColor(Color.PINK);
                            g.drawString(Jeton.PORTE.getSymbol(), j*cell_taille+(cell_taille/4), i*cell_taille+(cell_taille*4/5));
                        } else if (grid[i][j] == Jeton.VAMPIRE) {
                            g.setColor(Color.RED);
                            g.drawString(Jeton.VAMPIRE.getSymbol(), j*cell_taille+(cell_taille/4), i*cell_taille+(cell_taille*4/5));
                        } else if (grid[i][j] == Jeton.WEREWOLF) {
                            g.setColor(Color.magenta);
                            g.drawString(Jeton.WEREWOLF.getSymbol(),j*cell_taille+(cell_taille/4), i*cell_taille+(cell_taille*4/5));
                        } else if (grid[i][j] == Jeton.TRESOR) {
                            g.setColor(Color.ORANGE);
                            g.drawString(Jeton.TRESOR.getSymbol(), j*cell_taille+(cell_taille/4), i*cell_taille+(cell_taille*4/5));
                        } else if (grid[i][j] == Jeton.EPEE) {
                            g.setColor(Color.YELLOW);
                            g.drawString(Jeton.EPEE.getSymbol(), j*cell_taille+(cell_taille/4), i*cell_taille+(cell_taille*4/5));
                        } else if (grid[i][j] == Jeton.SORTIE) {
                            g.setColor(Color.GREEN);
                            g.drawString(Jeton.SORTIE.getSymbol(), j*cell_taille+(cell_taille/4), i*cell_taille+(cell_taille*4/5));
                        }
                    }
                }
                int x = ensdesalles.getJoueur().getPos().getX();
                int y = ensdesalles.getJoueur().getPos().getY();
                g.setColor(Color.BLUE);
                g.drawString(Jeton.PLAYER.getSymbol(), x*cell_taille+(cell_taille/4), y*cell_taille+(cell_taille*4/5));


                g.setColor(Color.BLACK);
                for (int i = 0; i < nb_lignes-1; i++) {
                    g.drawLine(0, (i+1)*cell_taille, window.getWindow_width(), (i+1)*cell_taille);
                }
                for (int j = 0; j < nb_colonnes-1; j++) {
                    g.drawLine((j+1)*cell_taille, 0, (j+1)*cell_taille, window.getWindow_height());
                }

            }
        };
        panneau.setPreferredSize(new Dimension(grid_width, grid_height));
        add(panneau);

        JPanel aside = new JPanel(new BorderLayout());
        aside.setBackground(Color.BLACK);
        aside.setPreferredSize(new Dimension(grid_width, window.getWindow_height()));

        up = new JButton("UP");
        right = new JButton("RIGHT");
        down = new JButton("DOWN");
        left = new JButton("LEFT");
        collect = new JButton("COLLECT");
        useSword = new JButton("USE SWORD");
        useTreasor = new JButton("USE TREASOR");

        decorateButton();
        addButtonListener();

        JPanel skillBar = new JPanel(new BorderLayout());
        skillBar.add(collect, BorderLayout.NORTH);
        skillBar.add(useSword, BorderLayout.CENTER);
        skillBar.add(useTreasor, BorderLayout.SOUTH);
        skillBar.setBackground(Color.BLACK);

        JPanel controlBar = new JPanel(new BorderLayout());
        controlBar.add(up, BorderLayout.NORTH);
        controlBar.add(right, BorderLayout.EAST);
        controlBar.add(down, BorderLayout.SOUTH);
        controlBar.add(left, BorderLayout.WEST);
        controlBar.setBackground(Color.BLACK);

        JPanel operationPanel = new JPanel();
        operationPanel.add(skillBar);
        operationPanel.add(controlBar);
        operationPanel.setBackground(Color.BLACK);

        scoreBar = new JPanel();
        score = new JLabel("Score: " + controleur.getScore());
        life = new JLabel("Life: " + controleur.getPlayerLife());
        score.setBackground(Color.BLACK);
        score.setForeground(Color.PINK);
        score.setFont(new Font("Times New Roman", Font.BOLD, 30));
        life.setBackground(Color.BLACK);
        life.setForeground(Color.PINK);
        life.setFont(new Font("Times New Roman", Font.BOLD, 30));
        scoreBar.add(score);
        scoreBar.add(life);
        scoreBar.setBackground(Color.BLACK);
        scoreBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));

        aside.add(operationPanel, BorderLayout.NORTH);
        aside.add(scoreBar, BorderLayout.CENTER);
        add(aside);

    }


    public void updateScore() {
        score.setText("Score: " + controleur.getScore());
        life.setText("Life: " + controleur.getPlayerLife());
    }


    @Override
    public void PositionChangee(ChangementPositionEvent event) {
        if (controleur.getTerminateStatus()) {
            playSoundEffect(MusicSamples.ENDGAME.getFilepath());
            window.showTerminate();
        }
        ensdesalles = event.getNewGrid();
        updateScore();
        panneau.repaint();
    }

    public void decorateButton() {
        ArrayList<JButton> buttonList = new ArrayList<>();
        buttonList.add(up);
        buttonList.add(down);
        buttonList.add(right);
        buttonList.add(left);
        buttonList.add(collect);
        buttonList.add(useSword);
        buttonList.add(useTreasor);
        for (JButton btn : buttonList) {
            btn.setFont(new Font("Times New Roman", Font.BOLD, 18));
            btn.setOpaque(false);
            btn.setForeground(Color.PINK);
            btn.setBorder(new MatteBorder(2, 2, 2, 2, Color.PINK));
            btn.setPreferredSize(new Dimension(100, 30));
        }

        collect.setPreferredSize(new Dimension(140, 30));
        useTreasor.setPreferredSize(new Dimension(140, 30));
        useSword.setPreferredSize(new Dimension(140, 30));

    }

    public void addButtonListener() {
        up.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.OperatedButton.getFilepath());
                controleur.notifyPositionChanged(Key.UP);
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
        right.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.OperatedButton.getFilepath());
                controleur.notifyPositionChanged(Key.RIGHT);
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
        down.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.OperatedButton.getFilepath());
                controleur.notifyPositionChanged(Key.DOWN);
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
        left.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.OperatedButton.getFilepath());
                controleur.notifyPositionChanged(Key.LEFT);
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
        collect.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.COLLECT_ITEM.getFilepath());
                controleur.notifyPositionChanged(Key.COLLECT);
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
        useSword.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.USE_SWORD.getFilepath());
                controleur.notifyPositionChanged(Key.USE_EPEE);
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
        useTreasor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSoundEffect(MusicSamples.USE_TREASURE.getFilepath());
                controleur.notifyPositionChanged(Key.USE_TREASOR);
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

    @Override
    public void MonsterAttackEffect(MonsterAttackEvent event) {
        if (event.getType() == Jeton.WEREWOLF) {
            playSoundEffect(MusicSamples.WOLF_BITES.getFilepath());
        } else {
            playSoundEffect(MusicSamples.VAMPIRE_ATTACK.getFilepath());
        }
    }
}
