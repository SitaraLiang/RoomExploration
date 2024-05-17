package Modele;

import java.util.EventListener;

public interface MonsterAttackListener extends EventListener {
    public void MonsterAttackEffect(MonsterAttackEvent event);
}
