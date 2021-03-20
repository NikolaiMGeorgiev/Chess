package Pieces;

public class Rook extends PlayingPiece {

    public Rook(String color) {
        super(color);
        if (color.equals("white")) {
            setSymbol('\u2656');
            if (Table.getObject(0, 0) == null) {
                Table.setStartingPosition(0, 0, this);
            } else {
                Table.setStartingPosition(0, 7, this);
            }
        } else {
            setSymbol('\u265C');
            if (Table.getObject(7, 0) == null) {
                Table.setStartingPosition(7, 0, this);
            } else {
                Table.setStartingPosition(7, 7, this);
            }
        }
    }

    @Override
    public boolean moveIsLegal(int newX, int newY) {
        getPathX().clear();
        getPathY().clear();
        if (super.moveIsLegal(newX, newY) && newX == getX() || newY == getY()) {
            if (newX > getX()) {
                return canGoVerticalDown(newX);
            } else if (newX < getX()) {
                return canGoVerticalUp(newX);
            } else if (newY > getY()) {
                return canGoHorizontalRight(newY);
            } else {
                return canGoHorizontalLeft(newY);
            }
        } else {
            return false;
        }
    }

    private boolean canGoVerticalDown(int newX) {
        for (int row = getX() + 1; row < newX; row++) {
            getPathX().add(row);
            getPathY().add(getY());
            if (Table.getObject(row, getY()) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
        }

        return Table.canTakePiece(newX, getY(), this);
    }

    private boolean canGoVerticalUp(int newX) {
        for (int row = getX() - 1; row > newX; row--) {
            getPathX().add(row);
            getPathY().add(getY());
            if (Table.getObject(row, getY()) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
        }

        return Table.canTakePiece(newX, getY(), this);
    }

    private boolean canGoHorizontalRight(int newY) {
        for (int col = getY() + 1; col < newY; col++) {
            getPathX().add(getX());
            getPathY().add(col);
            if (Table.getObject(getX(), col) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
        }

        return Table.canTakePiece(getX(), newY, this);
    }

    private boolean canGoHorizontalLeft(int newY) {
        for (int col = getY() - 1; col > newY; col--) {
            getPathX().add(getX());
            getPathY().add(col);
            if (Table.getObject(getX(), col) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
        }

        return Table.canTakePiece(getX(), newY, this);
    }
}
