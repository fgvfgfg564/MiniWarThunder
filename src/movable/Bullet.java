package movable;

import engine.GameEngine;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import practical.Pair;
import settings.Settings;

import java.awt.*;

public class Bullet extends MovableObject {

    double vx, vy;
    int cnt_rebound;
    public Bullet(GameEngine engine, double x, double y, double vx, double vy) {
        super(engine, x, y);
        this.r = Settings.defaultBulletRadius;
        this.vx = vx;
        this.vy = vy;
        this.cnt_rebound=0;
        try {
            img = ImageIO.read(new File("./images/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {
        if (CollideWith(myEngine.tank1)) {
            myEngine.tank1.blood -= 1;
            if(!myEngine.tank1.isRubbish&&myEngine.tank1.blood==0){
                myEngine.objects.add(new tankbomb(myEngine,myEngine.tank1.x,myEngine.tank1.y));
                myEngine.tank1.isRubbish=true;
            }
            isRubbish = true;
        }
        if (CollideWith(myEngine.tank2)) {
            myEngine.tank2.blood -= 1;
            if(!myEngine.tank2.isRubbish&&myEngine.tank2.blood==0) {
                myEngine.objects.add(new tankbomb(myEngine,myEngine.tank2.x, myEngine.tank2.y));
                myEngine.tank2.isRubbish = true;
            }
            isRubbish = true;
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
        if(this.cnt_rebound>=Settings.rebound_time)
            this.isRubbish=true;
    }

    public boolean CollideWith(Tank tank) {
        double a = (this.r + tank.r) * (this.r + tank.r);
        if (a <= (this.x - tank.x) * (this.x - tank.x) + (this.y - tank.y) * (this.y - tank.y))
            return false;
        return true;
    }
}
