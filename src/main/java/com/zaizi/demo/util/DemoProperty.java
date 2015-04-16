package com.zaizi.demo.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to retrieve configured property values
 * @author kvaratharaja
 *
 */
public class DemoProperty {
	private Properties prop = new Properties();
	private InputStream input = null;
	
	/**
	 * loading the property file
	 */
	public void loadProperty(){
	    String filename = "application.properties";
        try {
			input = ClassLoader.getSystemResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return;
			}
			prop.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * get the property value by key
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		return prop.getProperty(key);
	}
}
