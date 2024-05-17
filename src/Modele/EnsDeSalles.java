package Modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnsDeSalles {
    private int nb_lignes;
    private int nb_colonnes;
    private List<Monstre> monstres;
    private List<Objet> objets;
    private Joueur joueur;
    private Case sortie;
    private Jeton[][] grid;

    public EnsDeSalles() {
        this.objets = new ArrayList<>();
        this.monstres = new ArrayList<>();
    }
    public void intialiseGrid(int current_level, String path) throws IOException {
        monstres = new ArrayList<>();
        objets = new ArrayList<>();
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(path));
        String line;
        int i = 0, j = 0;
        switch (current_level) {
            case 1:
                skipline(reader);
                break;
            case 2:
                skipline(reader);
                skipline(reader);
                break;
            default:
                break;
        }
        line = reader.readLine();
        String[] numbers = line.split(" ");
        nb_lignes = Integer.parseInt(numbers[0]);
        nb_colonnes = Integer.parseInt(numbers[1]);
        grid = new Jeton[nb_lignes][nb_colonnes];

        while (i < nb_lignes) {
            line = reader.readLine();
            j = 0;
            for (char ch : line.toCharArray()) {
                grid[i][j] = Jeton.getJeton(Character.toString(ch));
                if (ch == 'V') {
                    Monstre vampire = new Vampire(new Case(j, i));
                    monstres.add(vampire);
                } else if (ch == 'W') {
                    Monstre werewolf = new Werewolf(new Case(j, i));
                    monstres.add(werewolf);
                } else if (ch == 'J') {
                    Joueur joueur = new Joueur(new Case (j, i));
                    this.joueur = joueur;
                } else if (ch == 'T') {
                    Objet tresor = new Tresor(new Case(j, i));
                    objets.add(tresor);
                } else if (ch == 'E') {
                    Objet epee = new Epee(new Case(j, i));
                    objets.add(epee);
                } else if (ch == 'S') {
                    sortie = new Case(j, i);
                }
                j++;
            }
            i++;
        }
    }

    private void skipline(BufferedReader reader) throws IOException {
        int n = 0;
        String line = reader.readLine();
        String[] numbers = line.split(" ");
        n = Integer.parseInt(numbers[0]);
        for (int i = 0; i < n; i++) {
            reader.readLine();
        }
    }

    public void affiche() {
        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {
                System.out.print(grid[i][j].getSymbol());
            }
            System.out.println();
        }
    }

    public void setJeton(Case pos, Jeton jeton) {
        grid[pos.getY()][pos.getX()] = jeton;
    }
    public Jeton getJeton(int x, int y) {
        return grid[y][x];
    }
    public Jeton[][] getGrid() { return grid; }
    public Joueur getJoueur() { return joueur; }
    public Case getSortie() { return sortie; }
    public List<Monstre> getMonstres() { return monstres; }
    public List<Objet> getObjets() { return objets; }
    public int getNb_lignes() { return nb_lignes; }
    public int getNb_colonnes() { return nb_colonnes; }
    public void removeMonstre(Monstre m) { monstres.remove(m); }
    public void removeObjet(Objet o) { objets.remove(o); }
}
