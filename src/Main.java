import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import engine.GameEngine;
import settings.Settings;

public class Main {

    public static void main(String[] args) {
        new MainFrame();
    }
}

class MainFrame extends JFrame {

    public MainFrame() {
        int w = Settings.frameWidth;
        int h = Settings.frameHeight;
        setSize(w, h);
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        isDoubleBuffered();
        setVisible(true);
        GameEngine game = new GameEngine((Graphics2D) getGraphics());
        game.mainLoop();
    }
}

