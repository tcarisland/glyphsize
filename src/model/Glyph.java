package model;

import java.io.File;

import controller.SVGFunctions;

public class Glyph {
	
	String name;
	String filename;
	String unicode;
	int viewbox[];
	int unicodeNumber;
	boolean unicodeDefined;
	
	public Glyph(File f, CharacterLookup table) {
		viewbox = SVGFunctions.getViewboxDimensions(f);
		this.name = f.getName();
		this.filename = f.getAbsolutePath();
		this.unicode = table.getUnicodeString(name);
		if(unicode == null) {
			unicodeDefined = false;			
			unicode = "UNDEFINED";
		} else {
			unicodeDefined = true;
			unicodeNumber = Integer.parseInt(unicode.substring(2), 16);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the unicode
	 */
	public String getUnicode() {
		return unicode;
	}

	/**
	 * @param unicode the unicode to set
	 */
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}

	/**
	 * @return the unicodeDefined
	 */
	public boolean isUnicodeDefined() {
		return unicodeDefined;
	}

	/**
	 * @param unicodeDefined the unicodeDefined to set
	 */
	public void setUnicodeDefined(boolean unicodeDefined) {
		this.unicodeDefined = unicodeDefined;
	}

	/**
	 * @return the unicodeNumber
	 */
	public int getUnicodeNumber() {
		return unicodeNumber;
	}

	/**
	 * @param unicodeNumber the unicodeNumber to set
	 */
	public void setUnicodeNumber(int unicodeNumber) {
		this.unicodeNumber = unicodeNumber;
	}

	/**
	 * @return the viewbox
	 */
	public int[] getViewbox() {
		return viewbox;
	}

	/**
	 * @param viewbox the viewbox to set
	 */
	public void setViewbox(int[] viewbox) {
		this.viewbox = viewbox;
	}

}
