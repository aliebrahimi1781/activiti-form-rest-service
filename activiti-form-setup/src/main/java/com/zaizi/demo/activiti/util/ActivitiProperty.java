package com.zaizi.demo.activiti.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to retrieve configured property values
 * @author kvaratharaja
 *
 */
public class ActivitiProperty {
	private Properties prop = new Properties();
	private InputStream input = null;

	/**
	 * load the property file
	 */
	public void loadProperty() {
		String tomcatPath = System.getProperty("catalina.base");
		String filename = tomcatPath
				+ "/webapps/activiti-app/WEB-INF/classes/META-INF/activiti-app/activiti-app.properties";
		try {
			input = new FileInputStream(filename);
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
	public String getValue(String key) {
		return prop.getProperty(key);
	}
}
