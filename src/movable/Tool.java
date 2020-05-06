package movable;

import engine.GameEngine;
import practical.Pair;
import settings.Settings;

import java.awt.*;

public class Tool extends MovableObject {

    int rad;
    
    public Tool(GameEngine engine, double x, double y,int rad) {
        super(engine, x, y);
        this.r = Settings.defaultToolRadius;
        Pair<Integer,Integer> pos=getSpawnPoint();
        this.x = pos.getKey();
        this.y =  pos.getValue();
        rad=random.nextInt(3);
        switch (this.rad) {
              case 0:
                  img = Toolkit.getDefaultToolkit().getImage("./images/startool.gif");
                  break;
              case 1:
                  img = Toolkit.getDefaultToolkit().getImage("./images/fastertool.gif");
                  break;
              case 2:
                  img = Toolkit.getDefaultToolkit().getImage("./images/wuditool.gif");
                   break;
        }
    }

    @Override
    public void loop() {
        if (CollideWith(myEngine.tank1)) {
        switch (this.rad) {
            case 0:
                //虚空操作
                break;
            case 1:
                myEngine.tank1.Speed*=2;//阴间操作，zym说叫我直接改，大力出奇迹
                break;
            case 2:
                myEngine.tank1.wudi=1;//阴间操作，zym说叫我直接改，大力出奇迹
                break;
        }
            isRubbish = true;
        }
        if (CollideWith(myEngine.tank2)) {      
        switch (this.rad) {
            case 0:
                //虚空操作
                break;
            case 1:
                myEngine.tank1.Speed*=2;//阴间操作，zym说叫我直接改，大力出奇迹
                break;
            case 2:
                myEngine.tank1.wudi=1;//阴间操作，zym说叫我直接改，大力出奇迹
                break;
        }
            isRubbish = true;
        }
    }

    public boolean CollideWith(Tank tank) {
        double a = (this.r + tank.r) * (this.r + tank.r);
        if (a <= (this.x - tank.x) * (this.x - tank.x) + (this.y - tank.y) * (this.y - tank.y))
            return true;
        return false;
    }
}
