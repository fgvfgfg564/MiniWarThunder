package engine;

import java.awt.Graphics;
import java.util.ArrayList;
import map.GameMap;
import movable.MovableObject;

public class GameEngine {

    Graphics g;     // 主窗口
    public ArrayList<MovableObject> objects = new ArrayList<>();    //保存所有会动的物体

    public GameEngine(Graphics g) {
        this.g = g;
    }

    public void mainLoop() {
        GameMap gameMap = new GameMap();
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
        }
    }
}
