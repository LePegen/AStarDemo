import java.util.ArrayList;

public class Cell {
    private int x;
    private int y;
    private ArrayList<Cell> neighbours;
    private int g;
    private int f;
    private int h;
    private Cell previous;
    private boolean[] sides;
    private boolean visited;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        neighbours = new ArrayList<>();
        //up,down,left,right
        sides = new boolean[]{true, true, true, true};
    }

    //borders

    public void upBorder(boolean toggle) {
        sides[0] = toggle;
    }

    public void downBorder(boolean toggle) {
        sides[1] = toggle;
    }

    public void leftBorder(boolean toggle) {
        sides[2] = toggle;
    }

    public void rightBorder(boolean toggle) {
        sides[3] = toggle;
    }

    public void setPrevious(Cell previous) {
        this.previous = previous;
    }


    //getters and setters


    public void setNeighbours(ArrayList<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean[] getSides() {
        return sides;
    }

    public Cell getPrevious() {

        return previous;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addNeighbour(Cell neighbour) {
        neighbours.add(neighbour);
    }


    public ArrayList<Cell> getNeighbours() {
        return neighbours;
    }


    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean getBorderUp() {
        return sides[0];
    }

    public boolean getBorderDown() {
        return sides[1];
    }
    public boolean getBorderLeft() {
        return sides[2];
    }
    public boolean getBorderRight() {
        return sides[3];
    }
}
