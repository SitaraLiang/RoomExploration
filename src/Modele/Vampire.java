package Modele;

public class Vampire extends Monstre{
    public Vampire(Case pos) {
        super(pos);
    }

    @Override
    public void attack(Joueur j, EnsDeSalles grid) {
        if (!pos.compare(Jeu.START) && !(j.getPos().compare(Jeu.START))) {
            Jeu.MonsterAttack(this);
            grid.setJeton(j.getPos(), Jeton.CHEMIN);
            grid.setJeton(Jeu.START, Jeton.PLAYER);
            j.setPos(Jeu.START);
        }
    }
}
