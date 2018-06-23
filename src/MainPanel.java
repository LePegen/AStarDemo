import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class MainPanel extends JPanel {

    AStar aStar;
    Cell[][] cells;
    int cellX;
    int cellY;
    int cellSide;

    public MainPanel(Controller controller) {
        cellX = 20;
        cellY = 20;
        cellSide = 25;
        this.aStar = controller.getAStar();
        cells = aStar.getCells();
    }

    public void update() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g = (Graphics2D) g;

        /*int curX=aStar.getMazeGenerator().getCurrentCell().getX();
        int curY=aStar.getMazeGenerator().getCurrentCell().getY();

        Rectangle curRect = new Rectangle(curX * cellSide, curY * cellSide, cellSide, cellSide);
        g.setColor(new Color(75,0,0,50));
        ((Graphics2D) g).fill(curRect);*/


        for (int i = 0; i < aStar.getOpenList().size(); i++) {
            int tempX = aStar.getOpenList().get(i).getX();
            int tempY = aStar.getOpenList().get(i).getY();
            Rectangle rectangle = new Rectangle(tempX * cellSide, tempY * cellSide, cellSide, cellSide);
            ((Graphics2D) g).fill(rectangle);
            g.setColor(Color.BLUE);
        }


        for (int i = 0; i < aStar.getClosedList().size(); i++) {
            int tempX = aStar.getClosedList().get(i).getX();
            int tempY = aStar.getClosedList().get(i).getY();
            Rectangle rectangle = new Rectangle(tempX * cellSide, tempY * cellSide, cellSide, cellSide);
            ((Graphics2D) g).fill(rectangle);
            g.setColor(Color.RED);
        }


        for (int i = 0; i < aStar.getBackTrack().size(); i++) {
            int tempX = aStar.getBackTrack().get(i).getX();
            int tempY = aStar.getBackTrack().get(i).getY();
            Rectangle rectangle = new Rectangle(tempX * cellSide, tempY * cellSide, cellSide, cellSide);
            ((Graphics2D) g).fill(rectangle);
            g.setColor(Color.green);

        }
g.setColor(Color.black);
        for (int i = 0; i < cellX; i++) {
            for (int j = 0; j < cellY; j++) {
                Cell tempCell = aStar.getCells()[i][j];
                for (int k = 0; k < tempCell.getSides().length; k++) {
                    int cornerPointX=cellSide*i;
                    int cornerPointY=cellSide*j;
                    if (tempCell.getSides()[0]) {
                        Line2D.Double line = new Line2D.Double(cornerPointX, cornerPointY, cornerPointX+cellSide, cornerPointY);
                        ((Graphics2D) g).draw(line);
                    }
                    if (tempCell.getSides()[1]) {
                        Line2D.Double line = new Line2D.Double(cornerPointX, cornerPointY+cellSide, cornerPointX+cellSide, cornerPointY+cellSide);
                        ((Graphics2D) g).draw(line);

                    }
                    if (tempCell.getSides()[2]) {
                        Line2D.Double line = new Line2D.Double(cornerPointX, cornerPointY, cornerPointX, cornerPointY+cellSide);
                        ((Graphics2D) g).draw(line);
                    }
                    if (tempCell.getSides()[3]) {
                        Line2D.Double line = new Line2D.Double(cornerPointX+cellSide, cornerPointY, cornerPointX+cellSide, cornerPointY+cellSide);
                        ((Graphics2D) g).draw(line);

                    }

                }
            }
        }
    }
}
