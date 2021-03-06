package movable;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.text.Segment;
import practical.Pair;
import settings.Settings;

import static java.lang.StrictMath.*;

public class Tank extends MovableObject {

    public static AudioPlayer shootSound = new AudioPlayer("sounds/shoot.wav");
    public static AudioPlayer dieSound = new AudioPlayer("sounds/explode.wav");
    public int tankType;
    public double Speed;
    public double RotateSpeed;
    public boolean forward, back, rRotate, lRotate;
    public int blood;
    public int hujia;
    public int fires;
    public int jiguang;
    public int bulletnumber;

    public Tank(GameEngine engine, double x, double y, int tankType) {
        super(engine, x, y);
        r = Settings.defaultTankRadius;
        this.Speed = Settings.defaultTankSpeed;
        this.RotateSpeed = Settings.defaultTankRotateSpeed;
        this.forward = this.back = this.rRotate = this.lRotate = false;
        this.blood=Settings.defaultTankBlood;
        this.hujia=0;
        this.fires=0;
        this.bulletnumber=0;
        this.jiguang=0;
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
        System.out.println("###"+this.jiguang);

        if(this.jiguang>0){
            int t=this.jiguang;
            this.jiguang=0;
           // System.out.println("what the fuck"+t);
            if(t==1)myEngine.objects.add(new Bigbullet(myEngine,x,y,vx,vy,tankType,theta));
            if(t==2||t==3){
                double sum=sqrt(vx*vx+vy*vy),ta=vy/vx;
                double degree=Math.toDegrees(Math.atan (ta));
                double d1=degree+10.0,d2=degree-10.0;
                int re1=0,re2=0;
                if(d1>90.0){d1-=180;re1=1;}
                if(d2<-90.0){d2+=180;re2=1;}
                double ta1=Math.tan(Math.toRadians(d1)),ta2=Math.tan(Math.toRadians(d2));
              //  System.out.println(degree+","+d1+","+d2+"|||"+ta1+","+ta2);
                double vx1=sum/sqrt(1+ta1*ta1),vy1=sum*ta1/sqrt(1+ta1*ta1);
                double vx2=sum/sqrt(1+ta2*ta2),vy2=sum*ta2/sqrt(1+ta2*ta2);
                if(vx<0){vx1=-vx1;vy1=-vy1;vx2=-vx2;vy2=-vy2;}
                if(re1>0){vx1=-vx1;vy1=-vy1;}
                if(re2>0){vx2=-vx2;vy2=-vy2;}
               // System.out.println(vx+"|"+vy+","+vx1+"|"+vy1+","+vx2+"|"+vy2);
                myEngine.objects.add(new Bullet(myEngine,x+8*vx,y+8*vy,vx,vy,0));
                myEngine.objects.add(new Bullet(myEngine,x+9*vx,y+9*vy,vx,vy,0));
                myEngine.objects.add(new Bullet(myEngine,x+10*vx,y+10*vy,vx,vy,0));
                myEngine.objects.add(new Bullet(myEngine,x+8*vx,y+8*vy,vx1,vy1,0));
                myEngine.objects.add(new Bullet(myEngine,x+8*vx,y+8*vy,vx2,vy2,0));
            }
            if(t==4)myEngine.objects.add(new Bomb(myEngine,x,y,0,0,tankType));
            return;
        }
        this.bulletnumber++;
        if(this.bulletnumber<=5)myEngine.objects.add(new Bullet(myEngine,x+8*vx,y+8*vy,vx,vy,tankType));
        else this.bulletnumber--;
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
        if(!this.isRubbish){
        for(int i=0;i<fires;i++)
        {
            this.fire();
        }
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
        if (test.x)x=oldx;
        if (test.y)y=oldy;
        if (rRotate) {
            theta = (theta - RotateSpeed) % (2 * PI);
        }
        if (lRotate) {
            theta = (theta + RotateSpeed) % (2 * PI);
        }
    }
}
