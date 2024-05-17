package Modele;

public class Werewolf extends Monstre{
    private final int POWER = (int)Configuration.WOLF_POWER.getValue();
    public Werewolf(Case pos) {
        super(pos);
    }

    @Override
    public void attack(Joueur j, EnsDeSalles grid) {
        Jeu.MonsterAttack(this);
        if (j.getVies() < POWER) {
            j.setVies(0);
        } else {
            j.setVies(j.getVies() - POWER);
        }
        if (j.isDead()) {
            Jeu.termine = true;
        }
        System.out.println("Ouch! You were bitten by the Werewolf! Player life points remaining: " + j.getVies());

    }
}
