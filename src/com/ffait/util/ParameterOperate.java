package com.ffait.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ParameterOperate {
	public static String extract(String key) {
		String str = null;
		try {
			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(
					new FileInputStream("C:\\parameter\\setting.properties"));
			prop.load(in);
			str = prop.getProperty(key);

		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
