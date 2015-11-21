package Client;

import java.awt.Dimension;
<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
=======
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
>>>>>>> Ellen

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.media.jfxmedia.Media;

import Client.BMLoginPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class BMGameFrame extends JFrame implements KeyListener{
private static final long serialVersionUID = 5147395078473323173L;
	
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);
	
	{
		setTitle("Bomberman");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
//		this.setFocusable(true);
//		this.requestFocusInWindow();
		add(new BMClientPanel());
		setLocationRelativeTo(null);
//		this.addKeyListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		BMGameFrame bmgf = new BMGameFrame();
		String bip = "bin/Utilities/bgm/bgm.mp3";
		Media hit = new Media(bip);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		
		try {
			InputStream bgm = new FileInputStream();
			try {
				BGM = new AudioStream(bgm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AudioPlayer.player.start(BGM);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bmgf.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("type");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("press");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Release");
	}
	
}
