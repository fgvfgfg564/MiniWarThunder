package movable;

import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

import engine.GameEngine;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import practical.Pair;
import settings.Settings;

public class Tank extends MovableObject {

    public int tankType;
    public double Speed;
    public double RotateSpeed;
    public boolean forward, back, rRotate, lRotate;

    public Tank(GameEngine engine, double x, double y, int tankType) {
        super(engine, x, y);
        r = Settings.defaultTankRadius;
        this.Speed = Settings.defaultTankSpeed;
        this.RotateSpeed = Settings.defaultTankRotateSpeed;
        this.forward = this.back = this.rRotate = this.lRotate = false;

        this.tankType = tankType;

        try {
            switch (this.tankType) {
                case 1:
                    img = ImageIO.read(new File("./images/tank1.gif"));
                    break;
                case 2:
                    img = ImageIO.read(new File("./images/tank2.gif"));
                    break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /* 响应键盘事件 */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (tankType == 1) {
            switch (key) {
                case KeyEvent.VK_W:
                    this.forward = true;
                    break;
                case KeyEvent.VK_S:
                    this.back = true;
                    break;
                case KeyEvent.VK_A:
                    this.lRotate = true;
                    break;
                case KeyEvent.VK_D:
                    this.rRotate = true;
                    break;
            }
        } else {
            switch (key) {
                case KeyEvent.VK_UP:
                    this.forward = true;
                    break;
                case KeyEvent.VK_DOWN:
                    this.back = true;
                    break;
                case KeyEvent.VK_LEFT:
                    this.lRotate = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    this.rRotate = true;
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (tankType == 1) {
            switch (key) {
                case KeyEvent.VK_W:
                    this.forward = false;
                    break;
                case KeyEvent.VK_S:
                    this.back = false;
                    break;
                case KeyEvent.VK_A:
                    this.lRotate = false;
                    break;
                case KeyEvent.VK_D:
                    this.rRotate = false;
                    break;
            }
        } else {
            switch (key) {
                case KeyEvent.VK_UP:
                    this.forward = false;
                    break;
                case KeyEvent.VK_DOWN:
                    this.back = false;
                    break;
                case KeyEvent.VK_LEFT:
                    this.lRotate = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    this.rRotate = false;
                    break;
            }
        }
    }

    @Override
    public void loop() {
        double dx = Speed * sin(theta);
        double dy = Speed * cos(theta);
        double oldx = x;
        double oldy = y;
        if (forward) {
            x -= dx;
            y -= dy;
        }
        if (back) {
            x += dx;
            y += dy;
        }
        Pair<Boolean, Boolean> test = myEngine.gameMap.collideWith(this);
        if (test.x || test.y) {
            x = oldx;
            y = oldy;
        }
        if (rRotate) {
            theta = (theta - RotateSpeed) % (2 * PI);
        }
        if (lRotate) {
            theta = (theta + RotateSpeed) % (2 * PI);
        }
    }
}
