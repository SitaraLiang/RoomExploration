package Modele;

public class Tresor extends Objet{
    private final int ENERGY = (int) Configuration.TREASURE_ENERGY.getValue();
    public Tresor(Case pos) {
        super(pos);
    }

    public void useObjet(Vivant v) {
        ((Joueur)v).setVies(((Joueur)v).getVies()+ENERGY);
        System.out.println("Treasure is used! Life points increased: " + ENERGY);
    }
}
