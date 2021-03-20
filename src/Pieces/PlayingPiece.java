package Pieces;

import java.util.ArrayList;

public class PlayingPiece {
    private static boolean isOnlyTesting = false;
    private int x;
    private int y;
    private String color;
    private char symbol;
    private ArrayList<Integer> pathX = new ArrayList<>();
    private ArrayList<Integer> pathY = new ArrayList<>();

    public PlayingPiece(String color) {
        this.color = color;
    }

    public void move(int newX, int newY) {
        setX(newX);
        setY(newY);
    }

    public boolean moveIsLegal(int newX, int newY) {
        return newX >= 0 && newX < 8 && newY >= 0 && newY < 8
                && (newX != this.getX() || newY != this.getY())
                && !Table.getObject(newX, newY).getColor().equals(this.getColor());
    }

    public ArrayList<Integer> getPathX() {
        return pathX;
    }

    public ArrayList<Integer> getPathY() {
        return pathY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public String getColor() {
        return color;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public static boolean isIsOnlyTesting() {
        return isOnlyTesting;
    }

    public static void setIsOnlyTesting(boolean isOnlyTesting) {
        PlayingPiece.isOnlyTesting = isOnlyTesting;
    }
}
