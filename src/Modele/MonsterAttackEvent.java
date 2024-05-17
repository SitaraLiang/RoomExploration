package Modele;

import java.util.EventObject;

public class MonsterAttackEvent extends EventObject {
    private Jeton type;
    public MonsterAttackEvent(Monstre monstre) {
        super(monstre);
        if (monstre.getClass() == Vampire.class) {
            this.type = Jeton.VAMPIRE;
        } else {
            this.type = Jeton.WEREWOLF;
        }
    }

    public Jeton getType() {
        return type;
    }
}
