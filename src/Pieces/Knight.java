package Pieces;

public class Knight extends PlayingPiece {
    public Knight(String color) {
        super(color);
        if (color.equals("white")) {
            setSymbol('\u2658');
            if (Table.getObject(0, 1) == null) {
                Table.setStartingPosition(0, 1, this);
            } else {
                Table.setStartingPosition(0, 6, this);
            }
        } else {
            setSymbol('\u265E');
            if (Table.getObject(7, 1) == null) {
                Table.setStartingPosition(7, 1, this);
            } else {
                Table.setStartingPosition(7, 6, this);
            }
        }
    }

    @Override
    public boolean moveIsLegal(int newX, int newY) {
        getPathX().clear();
        getPathY().clear();
        if (super.moveIsLegal(newX, newY)) {
            if (newX == getX() + 1 && newY == getY() + 2 ||
                    newX == getX() + 2 && newY == getY() + 1 ||
                    newX == getX() + 2 && newY == getY() - 1 ||
                    newX == getX() + 1 && newY == getY() - 2 ||
                    newX == getX() - 1 && newY == getY() - 2 ||
                    newX == getX() - 2 && newY == getY() - 1 ||
                    newX == getX() - 2 && newY == getY() + 1 ||
                    newX == getX() - 1 && newY == getY() + 2) {
                if (Table.canTakePiece(newX, newY, this)) {
                    getPathX().add(newX);
                    getPathY().add(newY);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}

