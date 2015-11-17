package Client;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

import com.sun.media.jfxmedia.Media;

import Client.BMLoginPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class BMGameFrame extends JFrame{
private static final long serialVersionUID = 5147395078473323173L;
	
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);
	
	{
		setTitle("Bomberman");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		add(new BMClientPanel());
		setLocationRelativeTo(null);
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
}
