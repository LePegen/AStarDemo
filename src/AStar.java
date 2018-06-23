import java.util.*;

public class AStar {


    private Cell[][] cells;
    private ArrayList<Cell> openList;
    private ArrayList<Cell> closedList;
    private ArrayList<Cell> backTrack;
    private Cell currentCell;
    private Cell startCell;
    private Cell endCell;
    private int toFindX;
    private int toFindY;
    private int gScore;
    private int fScore;
    private Controller controller;
    private ArrayList<Cell> obstacles;
    MazeGenerator mazeGenerator;


    /**
     * @param width
     * @param height
     * @param toFindX
     * @param toFindY
     */
    public AStar(int width, int height, int toFindX, int toFindY, Controller controller) {
        this.controller = controller;
        cells = new Cell[width][height];
        this.toFindX = toFindX;
        this.toFindY = toFindY;
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        backTrack = new ArrayList<>();
        obstacles = new ArrayList<>();
        initializeCells();
    }

    /**
     * @param x
     * @param y
     * @return
     */

    /**
     * @param cell1
     * @param cell2
     * @return
     */
    public int getDistance(Cell cell1, Cell cell2) {

        int distance;
        distance = (int) Math.sqrt(Math.pow(cell1.getX() - cell2.getX(), 2) + Math.pow(cell1.getY() - cell2.getY(), 2));

        return distance;
    }

    /**
     * starts the search
     */
    public void start() {
        setBorderedNeighbours();
        System.out.println("Starting find");
        this.currentCell = cells[0][0];
        openList.add(currentCell);
        Cell endCell = new Cell(toFindX, toFindY);
        while (openList.size() != 0) {
            currentCell = getLowestF();
            removeCell(openList, currentCell);
            closedList.add(currentCell);

            if (currentCell.getX() == toFindX && currentCell.getY() == toFindY) {
                reconstructPath();
                System.out.println("Search done");
                return;
            }

            for (int i = 0; i < currentCell.getNeighbours().size(); i++) {
                Cell tempNeighbour = currentCell.getNeighbours().get(i);
                if (isInSet(closedList, tempNeighbour)) {
                    continue;
                } else if (!isInSet(openList, tempNeighbour)) {

                    int tempG = getDistance(tempNeighbour, endCell) + 1;
                    if (isInSet(openList, tempNeighbour)) {
                        if (tempG < tempNeighbour.getG()) {
                            tempNeighbour.setG(tempG);
                        }
                    } else {
                        tempNeighbour.setG(tempG);
                        openList.add(tempNeighbour);
                    }

                    tempNeighbour.setPrevious(currentCell);
                    tempNeighbour.setH(getDistance(tempNeighbour, endCell));
                    tempNeighbour.setF(tempNeighbour.getF() + tempNeighbour.getG());

                }


            }


            try {
                Thread.sleep(50);
                controller.getMain().getMainPanel().update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void reconstructPath() {
        ArrayList<Cell> tempPath = new ArrayList<>();

        while (currentCell.getPrevious() != null) {
            tempPath.add(currentCell);
            currentCell = currentCell.getPrevious();

        }
        this.backTrack = tempPath;
    }

    public void generateObstacles(int obsCount) {
        ArrayList<Integer> tempX = new ArrayList<>();
        ArrayList<Integer> tempY = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if ((i == 0 && j == 0) || (i == cells.length - 1 && j == cells.length - 1)) {
                    continue;
                } else {
                    tempX.add(j);
                    tempY.add(i);
                }
            }
        }

        Collections.shuffle(tempX);
        Collections.shuffle(tempY);

        for (int i = 0; i < obsCount; i++) {
            closedList.add(cells[tempX.get(i)][tempY.get(i)]);
            obstacles.add(cells[tempX.get(i)][tempY.get(i)]);

        }
    }

    public ArrayList<Cell> getObstacles() {
        return obstacles;
    }

    public Cell getLowestF() {
        Cell lowest = openList.get(0);
        for (int i = 0; i < openList.size(); i++) {
            if (lowest.getF() > openList.get(i).getF()) {
                lowest = openList.get(i);
            }
        }
        return lowest;
    }

    /**
     * @param cell Checks whether inputted cell is in the set
     * @return returns true if is in cell and false if not
     */

    public boolean isInSet(ArrayList<Cell> cellList, Cell cell) {
        for (int i = 0; i < cellList.size(); i++) {
            if (cell.getX() == cellList.get(i).getX() && cell.getY() == cellList.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public void removeCell(ArrayList<Cell> cellArray, Cell cell) {
        cellArray.remove(cell);
    }


    public void setPrimitiveNeighbours(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (x + 1 < cells.length) {
            cell.addNeighbour(cells[x + 1][y]);
        }
        if (x - 1 > 0) {
            cell.addNeighbour(cells[x - 1][y]);

        }
        if (y + 1 < cells[0].length) {
            cell.addNeighbour(cells[x][y + 1]);

        }
        if (y - 1 > 0) {
            cell.addNeighbour(cells[x][y - 1]);
        }
    }

    public void setBorderedNeighbours() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                setMazedNeighbours(cells[i][j]);
            }
        }
    }

    public void setMazedNeighbours(Cell cell) {
        ArrayList<Cell> cellNeighbours = cell.getNeighbours();
        ArrayList<Cell> toSetNeigbours = new ArrayList<>();
        for (int i = 0; i < cellNeighbours.size(); i++) {
            Cell tempNeighbour = cellNeighbours.get(i);
            if(isBorderOpen(tempNeighbour,cell)){
                toSetNeigbours.add(tempNeighbour);
            }
        }
        cell.setNeighbours(toSetNeigbours);

    }

    public boolean isBorderOpen(Cell cell1, Cell cell2) {
        int horz=cell1.getX()-cell2.getX();
        int vert=cell1.getY()-cell2.getY();
        if (!cell1.getBorderLeft() && !cell2.getBorderRight()&&horz==1) {
            return true;
        }else if (!cell1.getBorderRight()&& !cell2.getBorderLeft()&&horz==-1) {
            return true;
        }

        if (!cell1.getBorderUp() && !cell2.getBorderDown()&&vert==1) {
            return true;
        } else if (!cell1.getBorderDown() && !cell2.getBorderUp()&&vert==-1) {
            return true;
        }
        return false;
    }


    public void initializeCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                setPrimitiveNeighbours(cells[i][j]);
            }
        }

    }

    public void generateMaze() {
        mazeGenerator = new MazeGenerator(cells, controller);
        this.cells = mazeGenerator.getMaze();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList<Cell> getOpenList() {
        return openList;
    }

    public ArrayList<Cell> getClosedList() {
        return closedList;
    }

    public ArrayList<Cell> getBackTrack() {
        return backTrack;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public MazeGenerator getMazeGenerator() {
        return mazeGenerator;
    }
}
