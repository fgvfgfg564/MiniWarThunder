package frame;
import java.applet.AudioClip;
import java.io.*;
import java.awt.Graphics;
import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import Functions.AudioPlayer;
public class MainFrame extends JFrame{
	MyPanel mp;
	JButton jb;
    public MainFrame() {
		AudioPlayer Music=new AudioPlayer("sounds/苏维埃进行曲.wav");
		Music.play();
        int w = Settings.frameWidth;
        int h = Settings.frameHeight;
		mp=new MyPanel();
		jb=new JButton(new ImageIcon("images/start.png"));
        setSize(w, h);
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        isDoubleBuffered();
		this.setLayout(null);
		this.add(mp);
		this.add(jb);
		jb.setBounds(w/2-120,550,240,45);
		jb.setBorderPainted(false);
		mp.setBounds(0,0,w,h);
		setLocationRelativeTo(null);
		setVisible(true);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{	 
				Music.stop();
				GameEngine game = new GameEngine((Graphics2D) getGraphics(),MainFrame.this);
				game.mainLoop();
			}
		});
       
    }
}
class MyPanel extends JPanel{
		public void paint(Graphics g){
			super.paint(g);
			int w = Settings.frameWidth;
			int h = Settings.frameHeight;
			g.drawImage(new ImageIcon("images/login2.png").getImage(),0,0,w,h,null);
		}
}