package com.ravindra.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/*
 * Load all the input files from the class path
 */
/**
 * 
 * @author KVSRR
 * NN Palli
 * Donakonda
 * Prakastam district
 * Andhrapradesh, India
 *
 */
public class PropertiesLoader {
	
	public static Properties loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("inputentries.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
}
