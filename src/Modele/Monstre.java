package Modele;

public abstract class Monstre extends Vivant {
    private boolean downward = false; // change from up to down or from down to up
    private boolean fromLeftToRight = false; // change from left to right or from right to left

    public Monstre(Case pos) {
        super(pos);
    }

    @Override
    public void move(Direction d, EnsDeSalles grid) {
        d = getDirection(grid);
        move_aux(d, grid);
        Joueur j = detectPlayer(grid);
        if (j != null) {
            attack(j, grid);
        }
    }

    private void move_aux(Direction d, EnsDeSalles grid) {
        int new_x = pos.getX(), new_y = pos.getY();
        switch(d) {
            case TOP:
                new_y--;
                break;
            case RIGHT:
                new_x++;
                break;
            case DOWN:
                new_y++;
                break;
            case LEFT:
                new_x--;
                break;
        }
        Case new_pos = new Case(new_x, new_y);
        Jeton jeton = grid.getJeton(pos.getX(), pos.getY());
        grid.setJeton(pos, Jeton.CHEMIN);
        grid.setJeton(new_pos, jeton);
        setPos(new_pos);
    }

    private Direction getDirection(EnsDeSalles grid) {
        if (downward) {
            // monster goes down
            if (isValidMove(Direction.DOWN, grid)){
                return Direction.DOWN;
            } else {
                if (fromLeftToRight) {
                    if (isValidMove(Direction.RIGHT, grid)) {
                        downward = false;
                        return Direction.RIGHT;
                    } else {
                        // This means the monster is at the bottom right corner of the grid
                        downward = false;
                        fromLeftToRight = false;
                        return Direction.LEFT;
                    }
                } else {
                    // The monster is moving from right to left
                    if (isValidMove(Direction.LEFT, grid)) {
                        downward = false;
                        return Direction.LEFT;
                    } else {
                        // This means the monster is at the bottom left corner of the grid
                        downward = false;
                        fromLeftToRight = true;
                        return Direction.RIGHT;
                    }
                }
            }
        } else {
            // monster goes up
            if (isValidMove(Direction.TOP, grid)){
                return Direction.TOP;
            } else {
                if (fromLeftToRight) {
                    if (isValidMove(Direction.RIGHT, grid)) {
                        downward = true;
                       return Direction.RIGHT;
                    } else {
                        // This means the monster is at the top right corner of the grid
                        downward = true;
                        fromLeftToRight = false;
                        return Direction.LEFT;
                    }
                } else {
                    // The monster is moving from right to left
                    if (isValidMove(Direction.LEFT, grid)) {
                        downward = true;
                        return Direction.LEFT;
                    } else {
                        // This means the monster is at the top left corner of the grid
                        downward = true;
                        fromLeftToRight = true;
                        return Direction.RIGHT;
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidMove(Direction d, EnsDeSalles grid) {
        int new_x = getPos().getX(), new_y = getPos().getY();
        switch(d) {
            case TOP:
                new_y--;
                break;
            case RIGHT:
                new_x++;
                break;
            case DOWN:
                new_y++;
                break;
            case LEFT:
                new_x--;
                break;
        }
        if (grid.getJeton(new_x, new_y) != Jeton.CHEMIN) {
            return false;
        }
        return true;
    }

    public Joueur detectPlayer(EnsDeSalles grid) {
        int x = pos.getX(), y = pos.getY();
        Case j_pos = grid.getJoueur().getPos();
        if (j_pos.compare(new Case(x-1, y-1)) || j_pos.compare(new Case(x, y-1)) || j_pos.compare(new Case(x+1, y-1))
                || j_pos.compare(new Case(x-1, y)) || j_pos.compare(new Case(x, y)) || j_pos.compare(new Case(x+1, y))
                || j_pos.compare(new Case(x-1, y+1)) || j_pos.compare(new Case(x, y+1)) || j_pos.compare(new Case(x+1, y+1))) {
            return grid.getJoueur();
        }
        return null;
    }


    public abstract void attack(Joueur j, EnsDeSalles grid);
}