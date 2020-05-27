package map;

import Functions.Mathematics;
import java.awt.Graphics;
import java.util.Random;
import practical.Pair;
import movable.MovableObject;
import settings.Settings;
import java.lang.Math;

import static settings.Settings.defaultBlockSize;
import static settings.Settings.defaultVertexRadius;
import static settings.Settings.removeWallRate;


public class GameMap {

    public int h, w;
    double scaling; // scaling = 1时地图每一格的边长为80像素
    boolean[][] right, down, ignore;
    public Pair<Integer, Integer> startPoint;   // 地图左上角的坐标
    Random random;

    int[] fa;

    public GameMap() {
        random = new Random(System.currentTimeMillis());
        h = random.nextInt(Settings.maxHeight - Settings.minHeight) + Settings.minHeight;
        w = random.nextInt(Settings.maxWidth - Settings.minWidth) + Settings.minWidth;
        scaling = Math
            .min(Settings.frameWidth * Settings.scaleConstant / (Settings.defaultBlockSize * w),
                Settings.frameHeight * Settings.scaleConstant / (Settings.defaultBlockSize * h));

        int stw = (int) Math
            .round((Settings.frameWidth - w * Settings.defaultBlockSize * scaling) / 2);
        int sth = (int) Math
            .round((Settings.frameHeight - h * Settings.defaultBlockSize * scaling) / 2);
        startPoint = new Pair<>(stw, sth);

        right = new boolean[w - 1][h];
        down = new boolean[w][h - 1];
        ignore = new boolean[w + 1][h + 1];

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

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (i < w - 1 && random.nextDouble() <= removeWallRate) {
                    right[i][j] = true;
                }
                if (j < h - 1 && random.nextDouble() <= removeWallRate) {
                    down[i][j] = true;
                }
            }
        }

        for (int i = 1; i < w; i++) {
            for (int j = 1; j < h; j++) {
                if (right[i - 1][j - 1] && right[i - 1][j] && down[i - 1][j - 1] && down[i][j
                    - 1]) {
                    ignore[i][j] = true;
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

    public Pair<Boolean, Boolean> collideWith(MovableObject obj) {
        // 判断某个物体是否与地图的边界碰撞
        // 返回一对boolean，res.getKey() 指代该物体是否撞上竖直墙（此时反转x轴速度）
        // res.getValue() 指代该物体是否撞上竖水平墙（此时反转y轴速度）
        // 若物体撞在了迷宫墙的一个角上，则两个都为true. 暂时不考虑碰撞的角度
        double x = obj.x, y = obj.y, r = obj.r;
        if (x < 0 || y < 0 || x > w * defaultBlockSize || y > h * defaultBlockSize) {
            return new Pair<>(true, true);
        }
        double blockSize = Settings.defaultBlockSize;
        int xb = (int) Math.floor(x / blockSize);
        int yb = (int) Math.floor(y / blockSize);

        boolean colX = false, colY = false;

        if ((xb == w - 1 || !right[xb][yb]) && (xb + 1) * blockSize - x <= r) {
            colX = true;
        }
        if ((yb == h - 1 || !down[xb][yb]) && (yb + 1) * blockSize - y <= r) {
            colY = true;
        }
        if ((xb == 0 || !right[xb - 1][yb]) && x - xb * blockSize <= r) {
            colX = true;
        }
        if ((yb == 0 || !down[xb][yb - 1]) && y - yb * blockSize <= r) {
            colY = true;
        }

        if (!ignore[xb][yb]
            && Mathematics.dist(x, y, xb * blockSize, yb * blockSize) <= r + defaultVertexRadius) {
            colX = colY = true;
        }
        if (!ignore[xb + 1][yb]
            && Mathematics.dist(x, y, (xb + 1) * blockSize, yb * blockSize)
            <= r + defaultVertexRadius) {
            colX = colY = true;
        }
        if (!ignore[xb + 1][yb + 1]
            && Mathematics.dist(x, y, (xb + 1) * blockSize, (yb + 1) * blockSize)
            <= r + defaultVertexRadius) {
            colX = colY = true;
        }
        if (!ignore[xb][yb + 1]
            && Mathematics.dist(x, y, xb * blockSize, (yb + 1) * blockSize)
            <= r + defaultVertexRadius) {
            colX = colY = true;
        }
        return new Pair<>(colX, colY);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Settings.defaultMapColor);
        double blockSize = Settings.defaultBlockSize * scaling;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (i == 0) {
                    drawVertical(g, startPoint.x, startPoint.y + j * blockSize,
                        blockSize);
                }
                if (j == 0) {
                    drawHorizontal(g, startPoint.x + i * blockSize, startPoint.y,
                        blockSize);
                }
                if (i == w - 1 || !right[i][j]) {
                    drawVertical(g, startPoint.x + (i + 1) * blockSize,
                        startPoint.y + j * blockSize, blockSize);
                }
                if (j == h - 1 || !down[i][j]) {
                    drawHorizontal(g, startPoint.x + i * blockSize,
                        startPoint.y + (j + 1) * blockSize, blockSize);
                }
            }
        }
        double vertexSize = defaultVertexRadius * 2 * scaling;
        for (int i = 0; i <= w; i++) {
            for (int j = 0; j <= h; j++) {
                if (!ignore[i][j]) {
                    g.fillOval(newRound(startPoint.x + i * blockSize - vertexSize / 2.0),
                        newRound(startPoint.y + j * blockSize - vertexSize / 2.0),
                        newRound(vertexSize),
                        newRound(vertexSize));
                }
            }
        }
    }

    static int newRound(double x) {
        return (int) (Math.round(x));
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

    public Pair<Integer, Integer> getSpawnPoint() {
        Integer x = random.nextInt(w) * defaultBlockSize + defaultBlockSize / 2;
        Integer y = random.nextInt(h) * defaultBlockSize + defaultBlockSize / 2;
        return new Pair<>(x, y);
    }
}
