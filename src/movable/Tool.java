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
    public static AudioPlayer bloodSound= new AudioPlayer("sounds/blood.wav");
    public static AudioPlayer hujiaSound=new AudioPlayer("sounds/hujia.wav");
    static Random rand = new Random(System.currentTimeMillis());

    public Tool(GameEngine engine, double x, double y) {
        super(engine, x, y);
        this.r = Settings.defaultToolRadius;
        this.rad = rand.nextInt(5);
        try {
            switch (this.rad) {
                case 0:
                    img = ImageIO.read(new File("./images/smallblood.bmp"));
                    break;
                case 1:
                    img = ImageIO.read(new File("./images/fastertool.jpg"));
                    break;
                case 2:
                    img = ImageIO.read(new File("./images/hujia.jpg"));
                    break;
                case 3:
                    img = ImageIO.read(new File("./images/bigblood.bmp"));
                case 4:
                    img=ImageIO.read(new File("./images/jiguang.png"));
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
                    if(myEngine.tank1.blood<Settings.defaultTankBlood) myEngine.tank1.blood++;
                    bloodSound.play();
                    break;

                case 1:
                    if(myEngine.tank1.Speed <10)myEngine.tank1.Speed+=2;
                    qhSound.play();
                    break;
                case 2:
                    myEngine.tank1.hujia++;
                    hujiaSound.play();
                    break;
                case 3:
                    myEngine.tank1.blood=Settings.defaultTankBlood;
                    bloodSound.play();
                    break;
                case 4:
                    myEngine.tank1.jiguang=1;
                    wudiSound.play();
            }
            isRubbish = true;
        }
        if (CollideWith(myEngine.tank2)) {
            switch (this.rad) {
                case 0:
                    if(myEngine.tank2.blood<Settings.defaultTankBlood) myEngine.tank2.blood++;
                    bloodSound.play();
                    break;
                case 1:
                    if(myEngine.tank2.Speed <10) myEngine.tank2.Speed += 2;
                    qhSound.play();
                    break;
                case 2:
                    myEngine.tank2.hujia++;
                    hujiaSound.play();
                    break;
                case 3:
                    myEngine.tank2.blood=Settings.defaultTankBlood;
                    bloodSound.play();
                    break;
                case 4:
                    myEngine.tank2.jiguang=1;
                    wudiSound.play();
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
