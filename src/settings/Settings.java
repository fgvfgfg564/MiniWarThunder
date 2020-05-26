package settings;

import java.awt.Color;
import java.awt.Font;

public class Settings {

    public static final int frameWidth = 1280;
    public static final int frameHeight = 720;
    public static final double defaultFPS = 60;


    public static final int minWidth = 5;
    public static final int maxWidth = 12;
    public static final int minHeight = 5;
    public static final int maxHeight = 12;   // 地图大小限制
    public static final double scaleConstant = 0.8; // 表示地图最大占据屏幕长和宽的多少百分比
    public static final Color defaultMapColor = Color.black;

    public static final int defaultBlockSize = 80;
    public static final int defaultTankRadius = 15; // 指在scaling=1的前提下，地图和坦克的默认大小
    public static final int defaultBulletRadius = 5;
    public static final int defaultToolRadius = 15;
    public static final int defaultTankBlood=5;
    public static final int rebound_time=40;

    public static final double defaultTankSpeed = 3;
    public static final double defaultTankRotateSpeed = 0.08;

    public static final double waitTime = 4.0;

    public static final double updateAppearTime = 10.0;

    public static final int scoreSize = 64;
    public static final Font scoreFont = new Font("微软雅黑", Font.BOLD, scoreSize);
    public static double scoreFadeSpeed = 0.5;

}
