package frame;
import java.applet.AudioClip;
import java.io.*;
import java.awt.Graphics;
import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel; 
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import engine.GameEngine;
import settings.Settings;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Functions.AudioPlayer;
public class MainFrame extends JFrame{
	AudioPlayer Music=new AudioPlayer("sounds/苏维埃进行曲.wav");
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
		setVisible(true);
        addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				Music.stop();
				GameEngine game = new GameEngine((Graphics2D) getGraphics(),MainFrame.this);
				game.mainLoop();
			}
		});
    }
	public void paint(Graphics g){
			super.paint(g);
			int w = Settings.frameWidth;
			int h = Settings.frameHeight;
			g.drawImage(new ImageIcon("images/login2.png").getImage(),0,0,w,h,null);
			g.drawImage(new ImageIcon("images/start2.png").getImage(),w/2-115,550,220,45,null);
		}
}
