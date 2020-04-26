package movable;

import engine.GameEngine;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.lang.Math;

abstract public class MovableObject {

    public Image img;
    public double x, y, r;
    protected GameEngine myEngine;
    public boolean isRubbish = false;
    public double theta;

    public MovableObject(GameEngine engine, double x, double y) {
        this.x = x;
        this.y = y;
        myEngine = engine;
        this.theta = 0;
    }

    public void paintComponent(Graphics2D g) {
        double scaling = myEngine.scaling;
        double newX = x * scaling + myEngine.startPoint.x;
        double newY = y * scaling + myEngine.startPoint.y;

        int width = img.getWidth(null);
        int height = img.getHeight(null);

        double dx = width / 2.0, dy = height / 2.0;

        double a = scaling * Math.cos(theta), b = scaling * -Math.sin(theta), c = scaling *
            Math.sin(theta), d = scaling * Math.cos(theta);

        g.drawImage(img,
            new AffineTransform(a, b, c, d, newX - dx * a - dy * c, newY - dx * b - dy * d), null);
    }

    abstract public void loop();    // 每一帧的迭代
}