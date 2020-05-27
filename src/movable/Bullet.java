package movable;

import Functions.AudioPlayer;
import engine.GameEngine;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import practical.Pair;
import settings.Settings;

import java.awt.*;

import static settings.Settings.defaultBlockSize;

public class Bullet extends MovableObject {

    double vx, vy;
    int cnt_rebound;
    int tp;
    public static AudioPlayer gedangSound = new AudioPlayer("sounds/gedang.wav");

    public Bullet(GameEngine engine, double x, double y, double vx, double vy, int type) {
        super(engine, x, y);
        this.r = Settings.defaultBulletRadius;
        this.vx = vx;
        this.vy = vy;
        this.cnt_rebound = 0;
        this.tp = type;
        try {
            img = ImageIO.read(new File("./images/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {
        if (CollideWith(myEngine.tank1)) {
            if (myEngine.tank1.hujia == 0) {
                myEngine.tank1.blood -= 1;
            } else {
                myEngine.tank1.hujia--;
                gedangSound.play();
            }
            if (!myEngine.tank1.isRubbish && myEngine.tank1.blood == 0) {
                myEngine.objects.add(new tankbomb(myEngine, myEngine.tank1.x, myEngine.tank1.y));
                myEngine.tank1.die();
            }
            isRubbish = true;
            if (tp == 1) {
                myEngine.tank1.bulletnumber--;
            }
            if (tp == 2) {
                myEngine.tank2.bulletnumber--;
            }
        }
        if (CollideWith(myEngine.tank2)) {
            if (myEngine.tank2.hujia == 0) {
                myEngine.tank2.blood -= 1;
            } else {
                myEngine.tank2.hujia--;
                gedangSound.play();
            }
            if (!myEngine.tank2.isRubbish && myEngine.tank2.blood == 0) {
                myEngine.objects.add(new tankbomb(myEngine, myEngine.tank2.x, myEngine.tank2.y));
                myEngine.tank2.die();
            }
            isRubbish = true;
            if (tp == 1) {
                myEngine.tank1.bulletnumber--;
            }
            if (tp == 2) {
                myEngine.tank2.bulletnumber--;
            }
        }
        double ox = x;
        double oy = y;
        x += vx;
        y += vy;
        Pair<Boolean, Boolean> test = myEngine.gameMap.collideWith(this);
        if (test.x) {
            x = ox;
            y = oy;
            vx = -vx;
            this.cnt_rebound++;
        }
        if (test.y) {
            x = ox;
            y = oy;
            vy = -vy;
            this.cnt_rebound++;
        }
        if (this.cnt_rebound >= Settings.rebound_time) {
            this.isRubbish = true;
            if (tp == 1) {
                myEngine.tank1.bulletnumber--;
            }
            if (tp == 2) {
                myEngine.tank2.bulletnumber--;
            }
        }
    }

    public boolean CollideWith(Tank tank) {
        double a = (this.r + tank.r) * (this.r + tank.r);
        if (a <= (this.x - tank.x) * (this.x - tank.x) + (this.y - tank.y) * (this.y - tank.y)) {
            return false;
        }
        return true;
    }
}

class Bigbullet extends Bullet {

    public static AudioPlayer jiguangsound = new AudioPlayer("sounds/jiguang.wav");

    public Bigbullet(GameEngine engine, double x, double y, double vx, double vy, int type,
        double thetaa) {
        super(engine, x, y, vx, vy, type);
        this.tp = type + 2;
        this.r = Settings.defaultBulletRadius * 3;
        this.cnt_rebound = 9;
        this.theta = thetaa;
        try {
            img = ImageIO.read(new File("./images/jig.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {
        if (CollideWith(myEngine.tank1)) {
            myEngine.tank1.blood = 0;
            if (!myEngine.tank1.isRubbish && myEngine.tank1.blood == 0) {
                myEngine.objects.add(new tankbomb(myEngine, myEngine.tank1.x, myEngine.tank1.y));
                myEngine.tank1.die();
            }
        }
        if (CollideWith(myEngine.tank2)) {
            myEngine.tank2.blood = 0;
            if (!myEngine.tank2.isRubbish && myEngine.tank2.blood == 0) {
                myEngine.objects.add(new tankbomb(myEngine, myEngine.tank2.x, myEngine.tank2.y));
                myEngine.tank2.die();
            }
        }
        double ox = x;
        double oy = y;
        this.cnt_rebound++;
        if (this.cnt_rebound >= Settings.rebound_time) {
            this.isRubbish = true;
        }
    }

    public boolean CollideWith(Tank tank) {
        if (tank.tankType == (this.tp - 2)) {
            return false;
        }
        double a = (this.r + tank.r) * (this.r + tank.r);
        double px = this.x, py = this.y;
        do {
            if (a > (px - tank.x) * (px - tank.x) + (py - tank.y) * (py - tank.y)) {
                return true;
            }
            px += vx;
            py += vy;
        } while (px >= 0 && py >= 0 && px <= myEngine.gameMap.w * defaultBlockSize
            && py <= myEngine.gameMap.h * defaultBlockSize);
        return false;
    }

    @Override
    public void paintComponent(Graphics2D g) {
        double scaling = myEngine.scaling;
        double newX = x * scaling + myEngine.startPoint.x;
        double newY = y * scaling + myEngine.startPoint.y;

        int width = img.getWidth(null);
        int height = img.getHeight(null);

        double dx = width / 2.0, dy = height; // 对齐点的坐标

        double a = scaling * Math.cos(theta), b = scaling * -Math.sin(theta), c = scaling *
            Math.sin(theta), d = scaling * Math.cos(theta);

        AffineTransform transform = new AffineTransform(a, b, c, d, -dx * a - dy * c,
            -dx * b - dy * d);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        g.drawImage(img, op, (int) Math.round(newX), (int) Math.round(newY));
    }
}