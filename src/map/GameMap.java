package map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.util.Set;
import movable.MovableObject;
import settings.Settings;
import java.lang.Math;


public class GameMap {

    int h, w;
    double scaling; // scaling = 1时地图每一格的边长为80像素
    double block_size;
    boolean[][] right, down;
    Dimension startPoint;   // 地图左上角的坐标

    int[] fa;

    public GameMap() {
        Random random = new Random(System.currentTimeMillis());
        h = random.nextInt(Settings.maxHeight - Settings.minHeight) + Settings.minHeight;
        w = random.nextInt(Settings.maxWidth - Settings.minWidth) + Settings.minWidth;
        scaling = Math
            .min(Settings.frameWidth * Settings.scaleConstant / (Settings.defaultBlockSize * w),
                Settings.frameHeight * Settings.scaleConstant / (Settings.defaultBlockSize * h));

        int stw = (int) Math
            .round((Settings.frameWidth - w * Settings.defaultBlockSize * scaling) / 2);
        int sth = (int) Math
            .round((Settings.frameHeight - h * Settings.defaultBlockSize * scaling) / 2);
        startPoint = new Dimension(stw, sth);

        right = new boolean[w - 1][h];
        down = new boolean[w][h - 1];

        int n = w * h;
        fa = new int[n];
        for (int i = 0; i < n; i++) {
            fa[i] = i;
        }
        int cnt = w * h;
        while (cnt > 1) {
            int u = random.nextInt(2 * w * h - w - h);
            if (u < (w - 1) * h) {
                int x = u % (w - 1), y = u / (w - 1);
                int a = y * w + x, b = y * w + x + 1;
                if (right[x][y]) {
                    continue;
                }
                if (combine(a, b)) {
                    right[x][y] = true;
                    cnt--;
                }
            } else {
                u -= (w - 1) * h;
                int x = u % w, y = u / w;
                int a = y * w + x, b = y * w + x + w;
                if (down[x][y]) {
                    continue;
                }
                if (combine(a, b)) {
                    down[x][y] = true;
                    cnt--;
                }
            }
        }
    }

    int find(int x) {
        if (x == fa[x]) {
            return x;
        } else {
            fa[x] = find(fa[x]);
            return fa[x];
        }
    }

    boolean combine(int x, int y) {
        x = find(x);
        y = find(y);
        if (x != y) {
            fa[y] = x;
            return true;
        }
        return false;
    }

    public boolean collideWith(MovableObject obj) {
        // 检查某个物体是否与地图的墙或者边界碰撞
        return false;
    }

    public void paintComponent(Graphics g) {
        double blockSize = Settings.defaultBlockSize * scaling;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (i == 0) {
                    drawVertical(g, startPoint.width, startPoint.height + j * blockSize,
                        blockSize);
                }
                if (j == 0) {
                    drawHorizontal(g, startPoint.width + i * blockSize, startPoint.height,
                        blockSize);
                }
                if (i == w - 1 || !right[i][j]) {
                    drawVertical(g, startPoint.width + (i + 1) * blockSize,
                        startPoint.height + j * blockSize, blockSize);
                }
                if (j == h - 1 || !down[i][j]) {
                    drawHorizontal(g, startPoint.width + i * blockSize,
                        startPoint.height + (j + 1) * blockSize, blockSize);
                }
            }
        }
    }

    void drawVertical(Graphics g, double x, double y, double len) {
        int ix = (int) Math.round(x);
        int iy = (int) Math.round(y);
        int iy2 = (int) Math.round(y + len);
        g.drawLine(ix, iy, ix, iy2);
    }

    void drawHorizontal(Graphics g, double x, double y, double len) {
        int ix = (int) Math.round(x);
        int iy = (int) Math.round(y);
        int ix2 = (int) Math.round(x + len);
        g.drawLine(ix, iy, ix2, iy);
    }

    public double getScale() {
        return scaling;
    }

    public Dimension getStartPoint() {
        return startPoint;
    }
}
