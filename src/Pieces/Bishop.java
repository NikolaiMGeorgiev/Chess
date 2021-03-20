package Pieces;

public class Bishop extends PlayingPiece {
    Bishop(String color) {
        super(color);
        if (color.equals("white")) {
            setSymbol('\u2657');
            if (Table.getObject(0, 2) == null) {
                Table.setStartingPosition(0, 2, this);

            } else {
                Table.setStartingPosition(0, 5, this);
            }
        } else {
            setSymbol('\u265D');
            if (Table.getObject(7, 2) == null) {
                Table.setStartingPosition(7, 2, this);
            } else {
                Table.setStartingPosition(7, 5, this);
            }
        }
    }

    @Override
    public boolean moveIsLegal(int newX, int newY) {
        getPathX().clear();
        getPathY().clear();
        if (super.moveIsLegal(newX, newY) && Math.abs(getX() - newX) == Math.abs(getY() - newY)) {
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
        } else {
            return false;
        }
        return false;
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
}
