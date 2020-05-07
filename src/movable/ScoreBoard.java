package movable;

import static settings.Settings.defaultFPS;
import static settings.Settings.scoreFadeSpeed;
import static settings.Settings.scoreFont;

import engine.GameEngine;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ScoreBoard extends MovableObject {

    static final int width = 120, height = 80;

    int number;
    int counting = 0;
    FontMetrics metrics;
    Graphics2D g;

    public ScoreBoard(GameEngine engine, int x, int y) {
        super(engine, x, y);
        this.number = 0;
        this.img = new BufferedImage(width, height, 1);
        g = (Graphics2D) img.getGraphics();
        g.setFont(scoreFont);
        metrics = g.getFontMetrics();
        setNumber(0);
    }

    public void setNumber(Integer x) {
        number = x;
    }

    public void count() {
        setNumber(number + 1);
        counting = (int) (defaultFPS * scoreFadeSpeed);
    }

    @Override
    public void paintComponent(Graphics2D out) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, width, height);
        int stringWidth;
        if (counting > 0) {
            int temp = (int) Math.round((height - 10) * counting / scoreFadeSpeed / defaultFPS);
            g.setColor(new Color(188, 188, 188));
            stringWidth = metrics.stringWidth(Integer.toString(number - 1));
            g.drawString(Integer.toString(number - 1), (width - stringWidth) / 2, temp);
        }
        stringWidth = metrics.stringWidth(Integer.toString(number));
        g.setColor(new Color(0, 0, 0));
        g.drawString(Integer.toString(number), (width - stringWidth) / 2, height - 10);
        out.drawImage(img, (int) (x - width / 2), (int) (y - height / 2), null);
    }

    @Override
    public void loop() {
        if (counting > 0) {
            counting--;
        }
    }
}
