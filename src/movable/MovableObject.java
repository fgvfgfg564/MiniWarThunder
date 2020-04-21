package movable;

import engine.GameEngine;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import java.lang.Math;

abstract public class MovableObject extends JPanel {

    protected static Image img;
    public double x, y, r;
    protected GameEngine myEngine;
    public boolean isRubbish = false;

    public MovableObject(GameEngine engine, double x, double y) {
        this.x = x;
        this.y = y;
        myEngine = engine;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, (int) Math.round(x), (int) Math.round(y), this);
    }

    abstract public void loop();    // 每一帧的迭代
}