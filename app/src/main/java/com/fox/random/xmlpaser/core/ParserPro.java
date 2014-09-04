package com.fox.random.xmlpaser.core;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author w_q
 * 
 */
public interface ParserPro {
	public static HashMap<String, String> profile = new HashMap<String, String>();

    public static String TEXT = "TEXT";

	public String getTagName();

	List<Class> getChildren();

	void addChild(ParserPro obj);
}
