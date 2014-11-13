package com.feihuwang.util;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataFormatUtil {
	private static final Log log = LogFactory.getLog(DataFormatUtil.class);
	private static final ObjectMapper m = new ObjectMapper();
	private static final ConcurrentHashMap<Class<?>, ObjectReader> objectReaderMap = new ConcurrentHashMap<Class<?>, ObjectReader>();
	private static final Object objectReaderMapLock = new Object();
	
	static {
		m.setSerializationInclusion(Include.NON_EMPTY);
		m.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static String toJsonString(final Object obj){
		if(obj == null){
			return "";
		}
		try {
			return m.writeValueAsString(obj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static JsonNode toJsonNode(final String jsonString){
		if(StringUtils.isBlank(jsonString)){
			return null;
		}
		try {
			return m.readTree(jsonString);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
		return null;
	}
	
	public static <T> T toBean(Class<T> clazz, String jsonStr){
		if(clazz == null || StringUtils.isBlank(jsonStr)){
			log.error("class and jsonStr params can't be empty;class=" + clazz + ";jsonStr=" + jsonStr);
			return null;
		}
		ObjectReader reader = objectReaderMap.get(clazz);
		if(reader == null){
			synchronized (objectReaderMapLock) {
				if((reader = objectReaderMap.get(clazz)) == null){
					reader = objectReaderMap.putIfAbsent(clazz, m.reader(clazz));
					if(reader == null){
						reader = objectReaderMap.get(clazz);
					}
				}
			}
		}
		try {
			return reader.readValue(jsonStr);
		} catch (Exception e) {
			log.error(e.getMessage() + "class=" + clazz + ";jsonStr=" + jsonStr, e);
		}
		return null;
	}

	public static ArrayNode createArrayNode(){
		try {
			return m.createArrayNode();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static ObjectNode createObjectNode(){
		try {
			return m.createObjectNode();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
