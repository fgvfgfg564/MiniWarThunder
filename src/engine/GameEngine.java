package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import map.GameMap;
import movable.MovableObject;
import settings.Settings;

public class GameEngine {

    Graphics g;     // 主窗口
    public ArrayList<MovableObject> objects = new ArrayList<>();    //保存所有会动的物体
    double scaling; // 这个变量代表地图的缩放比率。因为地图大小可变，导致坦克和奖励箱等的大小都必须相应变化。该值由地图大小确定
                    // 所有物品的碰撞箱大小及实际显示的大小都要乘上这一个常数
    GameMap gameMap;    // 地图

    public GameEngine(Graphics g) {
        this.g = g;
        gameMap = new GameMap();
        scaling = gameMap.getScale();
    }

    public void mainLoop() {
        while(true){
            gameMap.paintComponent(g);
        }
        //gameMap.paintComponent(g);
/*
        while (true) {
            for (MovableObject each : (ArrayList<MovableObject>) objects.clone()) {
                each.loop();
            }
            objects.removeIf(each -> each.isRubbish);

            gameMap.paintComponent(g);
            for (MovableObject each : objects) {
                each.paintComponent(g);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
