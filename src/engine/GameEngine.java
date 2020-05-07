package engine;

import static settings.Settings.defaultFPS;
import static settings.Settings.frameHeight;
import static settings.Settings.frameWidth;
import static settings.Settings.waitTime;

import frame.MainFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import map.GameMap;
import movable.MovableObject;
import movable.ScoreBoard;
import movable.Tank;
import practical.Pair;
import settings.Settings;

public class GameEngine {

    Graphics2D g, out;     // 主窗口
    MainFrame myFrame;
    BufferedImage buffer;
    public Tank tank1, tank2;
    public ScoreBoard score1, score2;
    public ArrayList<MovableObject> objects;    //保存所有会动的物体
    public double scaling; // 这个变量代表地图的缩放比率。因为地图大小可变，导致坦克和奖励箱等的大小都必须相应变化。该值由地图大小确定
    // 所有物品的碰撞箱大小及实际显示的大小都要乘上这一个常数
    public Pair<Integer, Integer> startPoint; // 地图左上角的像素坐标
    public GameMap gameMap;    // 地图

    public GameEngine(Graphics2D g, MainFrame frame) {
        this.buffer = new BufferedImage(frameWidth, frameHeight, 1);
        this.g = (Graphics2D) buffer.getGraphics();
        this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.out = g;

        score1 = new ScoreBoard(this, 100, frameHeight - 100);
        score2 = new ScoreBoard(this, frameWidth - 100, frameHeight - 100);

        myFrame = frame;
        myFrame.addKeyListener(new KeyMonitor());
    }

    public void mainLoop() {
        while (true) {
            int res = gameLoop();
            if (res == 1) {
                score1.count();
            } else if (res == 2) {
                score2.count();
            }
        }
    }

    int gameLoop() {
        gameMap = new GameMap();
        scaling = gameMap.getScale();
        startPoint = gameMap.startPoint;
        objects = new ArrayList<>();

        Pair<Integer, Integer> st1 = gameMap.getSpawnPoint();
        Pair<Integer, Integer> st2 = gameMap.getSpawnPoint();
        tank1 = new Tank(this, st1.x, st1.y, 1);
        tank2 = new Tank(this, st2.x, st2.y, 2);
        objects.add(score1);
        objects.add(score2);
        long fpsTime = (long) (1000.0 / Settings.defaultFPS * 1000000);
        long total;

        int countdown = (int) (waitTime * defaultFPS);

        while (true) {
            long now = System.nanoTime();

            if (tank1.isRubbish || tank2.isRubbish) {
                countdown--;
                if (countdown == 0) {
                    if (tank1.isRubbish && tank2.isRubbish) {
                        return 0;
                    }
                    if (tank1.isRubbish) {
                        return 2;
                    }
                    return 1;
                }
            }

            for (MovableObject each : new ArrayList<>(objects)) {
                each.loop();
            }
            tank1.loop();
            tank2.loop();

            objects.removeIf(each -> each.isRubbish);

            // 渲染
            g.setColor((Color.WHITE));
            g.fillRect(0, 0, frameWidth, frameHeight);
            gameMap.paintComponent(g);
            for (MovableObject each : objects) {
                each.paintComponent(g);
            }
            if(!tank1.isRubbish) tank1.paintComponent(g);
            if(!tank2.isRubbish) tank2.paintComponent(g);
            score1.paintComponent(g);

            out.drawImage(buffer, 0, 0, null);

            // fps控制
            try {
                total = System.nanoTime() - now;
                if (total > fpsTime) {
                    continue;
                }
                Thread.sleep((fpsTime - (System.nanoTime() - now)) / 1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while ((System.nanoTime() - now) < fpsTime) {
                System.nanoTime();
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