import java.awt.Dimension;
import javax.swing.JFrame;
import engine.GameEngine;

public class Main {

    public static void main(String[] args) {
        new MainFrame();
    }
}

class MainFrame extends JFrame {

    public MainFrame() {
        setSize(1280, 591);
        setPreferredSize(new Dimension(1280, 591));
        setMaximumSize(new Dimension(1280, 591));
        setMinimumSize(new Dimension(1280, 591));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        isDoubleBuffered();
        setVisible(true);
        GameEngine game = new GameEngine(getGraphics());
        game.mainLoop();
    }
}

