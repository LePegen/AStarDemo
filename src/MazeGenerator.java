import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {
    Cell[][] cells;
    Cell currentCell;
    Controller controller;
    public MazeGenerator(Cell[][] cells,Controller controller) {
        this.cells = cells;
        this.controller=controller;
    }

    public Cell[][] getMaze(){
        createMaze();
        return cells;
    }


    public void createMaze() {
        int cellSize=cells[0].length*cells.length;
        currentCell = cells[0][0];
        LinkedList<Cell> cellsList = new LinkedList<>();
        currentCell.setVisited(true);
        while (cellSize>1) {
            if (getUnvisitedNeighbors(currentCell).size() > 0) {
                Cell neighbour = getRandomUnvisitedNeighbor(currentCell);
                cellsList.add(currentCell);
                removeBorders(currentCell, neighbour);
                currentCell = neighbour;
                currentCell.setVisited(true);
                cellSize--;
            } else {
                currentCell = cellsList.pop();
            }

            try {
                Thread.sleep(10);
                controller.getMain().getMainPanel().update();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void removeBorders(Cell cell1, Cell cell2) {

        int horz=cell1.getX()-cell2.getX();
        int vert=cell1.getY()-cell2.getY();

        if (horz == 1) {
            cell1.leftBorder(false);
            cell2.rightBorder(false);
        } else if(horz==-1){
            cell1.rightBorder(false);
            cell2.leftBorder(false);
        }
        if (vert == 1) {
            cell1.upBorder(false);
            cell2.downBorder(false);
        } else if(vert==-1){
            cell1.downBorder(false);
            cell2.upBorder(false);
        }


    }

    public int getDistanceNumber(int x, int y) {
        return cells.length * x + y;

    }

    public ArrayList<Cell> getUnvisitedNeighbors(Cell cell) {
        ArrayList<Cell> unvisitedNeighbours = new ArrayList<>();
        for (int i = 0; i < cell.getNeighbours().size(); i++) {
            Cell tempNeighbour = cell.getNeighbours().get(i);
            if (!tempNeighbour.isVisited()) {
                unvisitedNeighbours.add(tempNeighbour);
            }
        }
        return unvisitedNeighbours;
    }

    public Cell getRandomUnvisitedNeighbor(Cell cell) {
        Random rand = new Random();
        ArrayList<Cell> unvisitedNeighbours = getUnvisitedNeighbors(cell);
        int randomNeighborIndex = rand.nextInt(unvisitedNeighbours.size());
        Cell neighbour = unvisitedNeighbours.get(randomNeighborIndex);
        return neighbour;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }
}
