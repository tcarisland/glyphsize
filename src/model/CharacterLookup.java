package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CharacterLookup {

	public static HashMap<String, HashSet<String>> table;
	public static final int ISO8859_LATIN1_NUMCHARS = 256;
	
	public CharacterLookup(String type) {
		table = new HashMap<String, HashSet<String>>();
		if(type.equals("ISO 8859-1")) {
			try {
				Scanner in = new Scanner(new File("tables/ISO 8859-1.csv"));
				while(in.hasNextLine()) {
					String input[] = in.nextLine().split("\\s+");
					HashSet<String> alternate_names = new HashSet<String>();
					for(String s : input) {
						alternate_names.add(s);
					}
					table.put(input[0], alternate_names);
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int unicodeStringToNum(String s) {
		if(s == null || s.equals("UNDEFINED"))
			return -1;
		return Integer.parseInt(s.substring(2), 16);
	}
	
	public static String numToUnicodeString(int number) {
		return "0u" + Integer.toHexString(number);
	}
		
	public String getUnicodeString(String name) {
		name = name.substring(0, name.length() - 4);
		if(name.length() == 1) {
			int c = (int) name.charAt(0);
			return "0u" + Integer.toHexString(c);
		}
		Set<String> keys = table.keySet();
		for(String key : keys) {
			if(table.get(key).contains(name))
				return key;
		}
		return null;
	}

}
