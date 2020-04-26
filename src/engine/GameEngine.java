package engine;

import static settings.Settings.frameHeight;
import static settings.Settings.frameWidth;

import frame.MainFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import map.GameMap;
import movable.MovableObject;
import movable.Tank;
import practical.Pair;
import settings.Settings;

public class GameEngine {

    Graphics2D g;     // 主窗口
    MainFrame myframe;
    public Tank tank1, tank2;
    public ArrayList<MovableObject> objects = new ArrayList<>();    //保存所有会动的物体
    public double scaling; // 这个变量代表地图的缩放比率。因为地图大小可变，导致坦克和奖励箱等的大小都必须相应变化。该值由地图大小确定
    // 所有物品的碰撞箱大小及实际显示的大小都要乘上这一个常数
    public Pair<Integer, Integer> startPoint; // 地图左上角的像素坐标
    public GameMap gameMap;    // 地图

    public GameEngine(Graphics2D g, MainFrame frame) {
        this.g = g;
        gameMap = new GameMap();
        scaling = gameMap.getScale();
        startPoint = gameMap.startPoint;
        tank1 = new Tank(this, 40, 40, 1);
        tank2 = new Tank(this, 40, 120, 2);
        myframe = frame;
        myframe.addKeyListener(new KeyMonitor());
    }

    public void mainLoop() {
        while (true) {
            g.setColor((Color.WHITE));
            g.fillRect(0, 0, frameWidth, frameHeight);

            for (MovableObject each : (ArrayList<MovableObject>) objects.clone()) {
                each.loop();
            }
            tank1.loop();
            tank2.loop();

            objects.removeIf(each -> each.isRubbish);

            gameMap.paintComponent(g);
            for (MovableObject each : objects) {
                each.paintComponent(g);
            }
            tank1.paintComponent(g);
            tank2.paintComponent(g);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* 事件监听器 */
    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            tank1.keyReleased(e);
            tank2.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            tank1.keyPressed(e);
            tank2.keyPressed(e);
        }
    }


}