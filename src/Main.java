import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    Controller controller;
    MainPanel mainPanel;
    JButton start;
    JButton generateMaze;
    JPanel actionContainer;

    public Main(Controller controller) {
        this.controller=controller;
        mainPanel = new MainPanel(this.controller);
        this.setSize(600, 600);
        start = new JButton("Start");
        generateMaze = new JButton("Generate Maze");
        actionContainer=new JPanel();
        actionContainer.add(start);
        actionContainer.add(generateMaze);
        actionContainer.setLayout(new BoxLayout(actionContainer,BoxLayout.LINE_AXIS));
        this.setLayout(new BorderLayout());
        this.add(mainPanel,BorderLayout.CENTER);
        this.add(actionContainer,BorderLayout.SOUTH);
        this.setVisible(true);
        setActionListeners();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setActionListeners(){
        start.addActionListener(e -> {
        controller.getAStar().start();
        });

        generateMaze.addActionListener(e -> {
            controller.getAStar().generateMaze();
        });
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
