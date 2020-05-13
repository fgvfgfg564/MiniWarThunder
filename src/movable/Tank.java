package movable;

import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

import Functions.AudioPlayer;
import engine.GameEngine;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.text.Segment;
import practical.Pair;
import settings.Settings;

public class Tank extends MovableObject {

    public static AudioPlayer shootSound = new AudioPlayer("sounds/shoot.wav");
    public static AudioPlayer dieSound = new AudioPlayer("sounds/explode.wav");

    public int tankType;
    public double Speed;
    public double RotateSpeed;
    public boolean forward, back, rRotate, lRotate;
    public int blood;
    public int wudi;
    public int fires;

    public Tank(GameEngine engine, double x, double y, int tankType) {
        super(engine, x, y);
        r = Settings.defaultTankRadius;
        this.Speed = Settings.defaultTankSpeed;
        this.RotateSpeed = Settings.defaultTankRotateSpeed;
        this.forward = this.back = this.rRotate = this.lRotate = false;
        this.blood=Settings.defaultTankBlood;
        this.wudi=0;
        this.fires=0;
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
                case KeyEvent.VK_F:
                    this.fires++;
                    break;
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
                case KeyEvent.VK_M:
                    this.fires++;
                    break;
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

    public void fire()
    {
        shootSound.play();
        double vx=-Speed*sin(theta);
        double vy=-Speed*cos(theta);
        myEngine.objects.add(new Bullet(myEngine,x+5*vx,y+5*vy,vx,vy));
    }

    public void die()
    {
        isRubbish = true;
        dieSound.play();
    }

    @Override
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

        g.setColor(new Color(255,0,0));
        g.fillRect((int)(newX-r*scaling),(int)(newY),(int)(2*r*blood*scaling/ Settings.defaultTankBlood),(int)(5*scaling));
    }


    @Override
    public void loop() {
        for(int i=0;i<fires;i++)
        {
            this.fire();
        }
        fires=0;
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
