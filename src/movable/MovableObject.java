package movable;

import engine.GameEngine;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.Math;

abstract public class MovableObject {

    public BufferedImage img;
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

        AffineTransform transform = new AffineTransform(a, b, c, d, -dx * a - dy * c,
            -dx * b - dy * d);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        g.drawImage(img, op, (int) Math.round(newX), (int) Math.round(newY));
    }

    abstract public void loop();    // 每一帧的迭代
}