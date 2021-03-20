package Pieces;

public class Queen extends PlayingPiece {
    public Queen(String color) {
        super(color);
        if (color.equals("white")) {
            setSymbol('\u2655');
            Table.setStartingPosition(0, 4, this);
        } else {
            setSymbol('\u265B');
            Table.setStartingPosition(7, 4, this);
        }
    }

    @Override
    public boolean moveIsLegal(int newX, int newY) {
        getPathX().clear();
        getPathY().clear();
        if (super.moveIsLegal(newX, newY)) {
            if (Math.abs(getX() - newX) == Math.abs(getY() - newY)) {
                return canMoveLikeBishop(newX, newY);
            } else if (newX == getX() || newY == getY()) {
                return canMoveLikeRook(newX, newY);
            } else if (newX == getX() - 1 || newX == getX() || newX == getX() + 1) {
                return newY == getY() + 1 || newY == getY() || newY == getY() - 1;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean canMoveLikeBishop(int newX, int newY) {
        if (newX > getX()) {
            if (newY < getY()) {
                return canGoLeftAndDown(newX, newY);
            } else if (newY > getY()) {
                return canGoRightAndDown(newX, newY);
            }
        } else {
            if (newY < getY()) {
                return canGoLeftAndUp(newX, newY);
            } else if (newY > getY()) {
                return canGoRightAndUp(newX, newY);
            }
        }
        return false;
    }

    private boolean canMoveLikeRook(int newX, int newY) {
        if (newX > getX()) {
            return canGoVerticalDown(newX);
        } else if (newX < getX()) {
            return canGoVerticalUp(newX);
        } else if (newY > getY()) {
            return canGoHorizontalRight(newY);
        } else {
            return canGoHorizontalLeft(newY);
        }
    }

    private boolean canGoLeftAndDown(int newX, int newY) {
        int row = getX() + 1;
        int col = getY() - 1;
        while (row < newX && col > newY) {
            getPathX().add(row);
            getPathY().add(col);
            if (Table.getObject(row, col) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
            row++;
            col--;
        }
        return Table.canTakePiece(newX, newY, this);
    }

    private boolean canGoRightAndDown(int newX, int newY) {
        int row = getX() + 1;
        int col = getY() + 1;
        while (row < newX && col < newY) {
            getPathX().add(row);
            getPathY().add(col);
            if (Table.getObject(row, col) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
            row++;
            col++;
        }
        return Table.canTakePiece(newX, newY, this);
    }

    private boolean canGoLeftAndUp(int newX, int newY) {
        int row = getX() - 1;
        int col = getY() - 1;
        while (row > newX && col > newY) {
            getPathX().add(row);
            getPathY().add(col);
            if (Table.getObject(row, col) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
            row--;
            col--;
        }
        return Table.canTakePiece(newX, newY, this);
    }

    private boolean canGoRightAndUp(int newX, int newY) {
        int row = getX() - 1;
        int col = getY() + 1;
        while (row > newX && col < newY) {
            getPathX().add(row);
            getPathY().add(col);
            if (Table.getObject(row, col) != Table.getEmptyObject()) {
                getPathX().clear();
                getPathY().clear();
                return false;
            }
            row--;
            col++;
        }
        return Table.canTakePiece(newX, newY, this);
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
