package Pieces;

public class Pawn extends PlayingPiece {
    private int startX;

    public Pawn(String color) {
        super(color);
        if (color.equals("white")) {
            setSymbol('\u2659');
            for (int col = 0; col < 8; col++) {
                if (Table.getObject(1, col) == null) {
                    startX = 1;
                    Table.setStartingPosition(1, col, this);
                    break;
                }
            }
        } else {
            setSymbol('\u265F');
            for (int col = 0; col < 8; col++) {
                if (Table.getObject(6, col) == null) {
                    startX = 6;
                    Table.setStartingPosition(6, col, this);
                    break;
                }
            }
        }
    }

    @Override
    public boolean moveIsLegal(int newX, int newY) {
        getPathX().clear();
        getPathY().clear();
        if (super.moveIsLegal(newX, newY)) {
            if (this.getColor().equals("white")) {
                return moveIsLegalWhites(newX, newY);
            } else {
                return moveIsLegalBlacks(newX, newY);
            }
        } else {
            return false;
        }
    }

    private boolean moveIsLegalWhites(int newX, int newY) {
        if (newY == getY() && Table.getObject(newX, newY) == Table.getEmptyObject()) {
            if (startX != this.getX()) {
                if (newX == getX() + 1) {
                    getPathX().add(newX);
                    getPathY().add(newY);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (newX == getX() + 1 ||
                        (newX == getX() + 2 && Table.getObject(getX() + 1, newY) == Table.getEmptyObject())) {
                    getPathX().add(newX);
                    getPathY().add(newY);
                    return true;
                } else {
                    return false;
                }
            }
        } else if ((newY == this.getY() + 1 || newY == this.getY() - 1)
                && newX == this.getX() + 1) {
            return canTakePiece(newX, newY);
        }
        return false;
    }

    private boolean moveIsLegalBlacks(int newX, int newY) {
        if (newY == getY() && Table.getObject(newX, newY) == Table.getEmptyObject()) {
            if (startX != this.getX()) {
                if (newX == getX() - 1) {
                    getPathX().add(newX);
                    getPathY().add(newY);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (newX == getX() - 1 ||
                        (newX == getX() - 2 && Table.getObject(getX() - 1, newY) == Table.getEmptyObject())) {
                    getPathX().add(newX);
                    getPathY().add(newY);
                    return true;
                } else {
                    return false;
                }
            }
        } else if ((newY == this.getY() + 1 || newY == this.getY() - 1)
                && newX == this.getX() - 1) {
            return canTakePiece(newX, newY);
        }
        return false;
    }

    private boolean canTakePiece(int newX, int newY) {
        if (!Table.getObject(newX, newY).getColor().equals(this.getColor())
                && Table.getObject(newX, newY) != Table.getEmptyObject()) {
            if (!isIsOnlyTesting()) {
                Table.takePiece(newX, newY);
            }
            getPathX().add(newX);
            getPathY().add(newY);
            return true;
        }
        return false;
    }
}
