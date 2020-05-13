package frame;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import engine.GameEngine;
import settings.Settings;
import java.awt.event.MouseEvent;
import Functions.AudioPlayer;

public class MainFrame extends JFrame {

    AudioPlayer Music = new AudioPlayer("sounds/苏维埃进行曲.wav");
    GameEngine game;
    volatile boolean flag = false;
    static BufferedImage button, background;

    static {
        try {
            button = ImageIO.read(new File("images/start.png"));
            background = ImageIO.read(new File("images/login2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainFrame() {
        Music.play();
        int w = Settings.frameWidth;
        int h = Settings.frameHeight;
        setSize(w, h);
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        isDoubleBuffered();
        this.setLayout(null);
        setVisible(true);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                flag = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        game = new GameEngine((Graphics2D) getGraphics(), this);
        while (true) {
            paintMe(getGraphics());
            if (flag) {
                Music.stop();

                game.mainLoop();
                flag = false;
                Music.play();
            }
        }
    }

    public void paintMe(Graphics g) {
        int w = Settings.frameWidth;
        int h = Settings.frameHeight;
        g.drawImage(background, 0, 0, w, h, null);
        g.drawImage(button, w / 2 - 120, 550, 240, 45, null);
    }
}
