public class Controller {

    AStar aStar;
    Main main;

    public Controller() {
        int cellX = 20;
        int cellY = 20;
        aStar = new AStar(cellX, cellY, 19, 19, this);
        main = new Main(this);
        System.out.println("Done generating borders");
    }

    public AStar getAStar() {
        return aStar;
    }

    public Main getMain() {
        return main;
    }

    public static void main(String[] args) {
        new Controller();
    }
}
