package Pieces;

public class King extends PlayingPiece {
    public King(String color) {
        super(color);
        if (color.equals("white")) {
            setSymbol('\u2654');

            Table.setKingWhite(0, 3, this);
        } else {
            setSymbol('\u265A');
            Table.setKingBlack(7, 3, this);
        }
    }

    @Override
    public boolean moveIsLegal(int newX, int newY) {
        if (super.moveIsLegal(newX, newY)) {
            if ((newX == getX() - 1 || newX == getX() || newX == getX() + 1)
                    && (newY == getY() + 1 || newY == getY() || newY == getY() - 1)) {
                return Table.canTakePiece(newX, newY, this);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
