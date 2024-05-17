package Modele;

import java.util.EventObject;

public class ChangementPositionEvent extends EventObject {
    private EnsDeSalles grid;

    public ChangementPositionEvent(Object source, EnsDeSalles grid) {
        super(source);
        this.grid = grid;
    }

    public EnsDeSalles getNewGrid() {
        return grid;
    }


}