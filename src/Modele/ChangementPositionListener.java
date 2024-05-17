package Modele;

import java.util.EventListener;

public interface ChangementPositionListener extends EventListener {
    public void PositionChangee(ChangementPositionEvent event);
}
