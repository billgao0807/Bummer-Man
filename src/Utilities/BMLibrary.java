package Utilities;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class BMLibrary {

	private HashMap<String, ImageIcon> imageMap;
	private File file;
	public final String SQLName = "SQLName";
	
	//read Images
	public BMLibrary(String path){
		
	}
	
	public static Image getImages(String name){
		return null;
	}
	public static int[ ][ ] getGameMap(){
		int [][] gamemap = new int[16][16];
		
		
		List<String[]> data = new ArrayList<>();		 
		try {
??			Scanner in = new Scanner(new File("   ??"));
		 
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
	}
}
