package com.fox.random.xmlpaser.core;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Hanshuang 2014年8月22日16:37:2
 * 
 */
public interface ParserPro {
	public static HashMap<String, String> profile = new HashMap<String, String>();

    public static String TEXT = "TEXT";

	public String getTagName();

	List<Class> getChildren();

	void addChild(ParserPro obj);
}
