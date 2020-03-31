package tools;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tools {

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	public static String readKeyboard() {
		String s = null;
		try {
			BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
			s = entree.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	// https://stackoverflow.com/questions/35692823/getting-a-color-by-name-in-java
	public static Color getColorByName(String name) {
		try {
			return (Color) Color.class.getField(name.toUpperCase()).get(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String [] tokenize(String s) {
		StringTokenizer nizer = new StringTokenizer(s);
		ArrayList<String> result = new ArrayList<String>();
        while (nizer.hasMoreElements()) {
        	result.add((String) nizer.nextElement());
        }
        String[] arr = new String[result.size()];
		return result.toArray(arr);
	}
	
	public static String[] readCmdAndArgs() {
		String input = readKeyboard();
		String[] cmdAndargs = tokenize(input);
		return cmdAndargs;
	}
}
