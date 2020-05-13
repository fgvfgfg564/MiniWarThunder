package movable;

import java.io.File;

import Functions.AudioPlayer;
import engine.GameEngine;
import practical.Pair;
import settings.Settings;

import java.io.IOException;
import java.util.Random;
import java.awt.*;
import map.GameMap;

import javax.imageio.ImageIO;

import static settings.Settings.defaultBlockSize;

public class Tool extends MovableObject {

    int rad;
    public static AudioPlayer wudiSound = new AudioPlayer("sounds/wudi1.wav");
    public static AudioPlayer qhSound = new AudioPlayer("sounds/qh.wav");
    static Random rand = new Random(System.currentTimeMillis());

    public Tool(GameEngine engine, double x, double y) {
        super(engine, x, y);
        this.r = Settings.defaultToolRadius;
        rad = rand.nextInt(3);
        try {
            switch (this.rad) {
                case 0:
                    img = ImageIO.read(new File("./images/startool.png"));
                    break;
                case 1:
                    img = ImageIO.read(new File("./images/fastertool.png"));
                    break;
                case 2:
                    img = ImageIO.read(new File("./images/wuditool.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {
        if (CollideWith(myEngine.tank1)) {
            switch (this.rad) {
                case 0:
                    break;
                case 1:
                    myEngine.tank1.Speed *= 2;
                    qhSound.play();
                    break;
                case 2:
                    myEngine.tank1.wudi = 1;
                    wudiSound.play();
                    break;
            }
            isRubbish = true;
        }
        if (CollideWith(myEngine.tank2)) {
            switch (this.rad) {
                case 0:
                    break;
                case 1:
                    myEngine.tank1.Speed *= 2;
                    qhSound.play();
                    break;
                case 2:
                    myEngine.tank1.wudi = 1;
                    wudiSound.play();
                    break;
            }
            isRubbish = true;
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
