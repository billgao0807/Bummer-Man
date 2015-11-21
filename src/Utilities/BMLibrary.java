package Utilities;

import java.awt.Image;
<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
=======
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
>>>>>>> Ellen

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BMLibrary {

<<<<<<< HEAD

	
=======
	private HashMap<String, ImageIcon> imageMap;
	private File file;
>>>>>>> Ellen
	public final String SQLName = "SQLName";
	
	private static Map<String, Image> imageMap;
	static{
		imageMap = new HashMap<String,Image>();
	}
		
	private BMLibrary(){} //disable constructor
	
	//Gets the image if available already, otherwise the image is loaded and returned
	public static Image getImage(String directory) {
		
		URL url = BMLibrary.class.getResource(directory);
		System.out.println(url);
		Image img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		Image img = imageMap.get(directory);
		if(img == null) {
			try {
				if(directory.startsWith("http")) {
					img = ImageIO.read(new URL(directory));
				}
				else img = ImageIO.read(new File(directory));
			}
			catch (IOException e) { System.out.println("Error reading image: " + directory + e); return null; }
			imageMap.put(directory, img);
		}
		return img;
	}
	
<<<<<<< HEAD
	//Forced the image to be reloaded from file
	public static Image getImageReload(String directory) {
		Image img;
		try { img = ImageIO.read(new File(directory)); }
		catch (IOException e) { System.out.println("Error reading image: " + e); return null; }
		imageMap.put(directory, img);
		return img;
	}
	
	//Clears out all the images from the library
	public static void clearImages() {
		imageMap.clear();
=======
	public static Image readImages(String path){
		Image img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	public static int[ ][ ] getGameMap(){
		int [][] gamemap = new int[16][16];
		
		
		List<String[]> data = new ArrayList<>();		 
		try {
						Scanner in = new Scanner(new File("bin/map1"));
		 
		    while (in.hasNextLine()) {
		           String str = in.nextLine();
		 
		            data.add(str.split(" ,"));
		    }
		} catch (Exception e) {
		            e.printStackTrace();
		}
		
		 
		String[][] result = data.toArray(new String[][] {});
		
		for (int i=0 ; i<16 ; i++){
			for (int j =0; j<16 ; j++){
				gamemap[i][j] = Integer.parseInt(result[i][j]);
			}
		}
		
		return gamemap;
>>>>>>> Ellen
	}
}
