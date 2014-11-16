package com.feihuwang.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Properties;

public class FileUtil {
	
	/**
	 * 读取配置文件中的某一项
	 * @param name
	 * @return
	 */
	public static synchronized String getProperties(String filename,String urlName){
		try {
            Properties properties = new Properties();
            InputStream file = FileUtil.class.getClassLoader().getResourceAsStream(filename);
            properties.load(file);
            return properties.getProperty(urlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;

	}
	
	/**
	 * 读取文件内容
	 * @param name
	 * @return
	 */
	public static String getFileContent(String filename){
		InputStream file = FileUtil.class.getClassLoader().getResourceAsStream(filename);
		return inputStream2String(file, "UTF-8");
	}
	
	public static String inputStream2String(InputStream is, String charset) {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int i = -1;
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
			return baos.toString(charset);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != baos) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				baos = null;
			}
		}
		return null;
	}
	
	/**
	 * 读取
	 * @param name
	 * @return
	 */
	public static synchronized String readFile(String filename){
		String result = "";
		try {
			FileInputStream stream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream,  "utf-8"));
			String lineTxt = null;
			StringBuffer sb = new StringBuffer();
			while((lineTxt = bufferedReader.readLine()) != null){
	        	sb.append(lineTxt);
	        }
			result = sb.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;

	}
	
	/**
	 * 替换
	 * @param name
	 * @return
	 */
	public static synchronized boolean replaceRegex(String filename, String regex, String replacement){
		try {
			String content = readFile(filename);
			content = content.replaceAll(regex, replacement);
			saveFile(filename, content);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 替换
	 * @param name
	 * @return
	 */
	public static synchronized boolean replaceRegex(String filename, List<String> regexs, List<String> replacements){
		try {
			String content = readFile(filename);
			for (int i = 0; i < regexs.size(); i++) {
				content = content.replaceAll(regexs.get(i), replacements.get(i));
			}
			saveFile(filename, content);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 保存
	 * @param name
	 * @return
	 */
	public static synchronized boolean saveFile(String filename, String content){
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			} 
			OutputStream fis = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fis, "utf-8");
			osw.write(content);
			osw.flush();
			osw.close();
		} catch (Exception e1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 读取配置文件
	 * @return
	 */
	public static synchronized Properties getProperties(String propFile){
		try {
            Properties properties = new Properties();
            InputStream file = FileUtil.class.getClassLoader().getResourceAsStream(propFile);
            properties.load(file);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
}
