package com.esm.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 通用方法.
 * @author NYC
 *
 */
public class MethodUtil {
	
	protected static ObjectMapper mapper = new ObjectMapper();

	protected static JsonFactory factory = mapper.getJsonFactory();

	/**
	 * MD5加密.
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String toMD5(String str) throws NoSuchAlgorithmException {
		if(StringUtil.isEmpty(str)) {
			return null;
		}
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return byteToHex(md5.digest(str.getBytes()));
	}
	
	/**
	 * 字节转成16进制. 
	 * @param bytes
	 * @return
	 */
	public static String byteToHex(byte[] bytes) {
		String rs = "";
		for(int i = 0; i < bytes.length;i++) {
			int temp = bytes[i] & 0xff;
			String tempHex = Integer.toHexString(temp);
			if(tempHex.length() < 2) {
				rs += "0";
			}
			rs += tempHex;
		}
		return rs;
	}
	
	/**
	 * 对象转成字段串
	 * @param o 对象
	 * @param dateFormat 日期格式,比如"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static JsonObject objToJson(Object o,String dateFormat){
		Gson gson = new GsonBuilder().setDateFormat(dateFormat)
				.create();
		JsonElement jsonElement = gson.toJsonTree(o);
		return jsonElement.getAsJsonObject();
	}
	
	public static JsonObject objToJson(Object o){
		return objToJson(o, "yyyy-MM-dd");
	}
	
	/**
	 * 对象转成字段串
	 * @param o 对象
	 * @param dateFormat 日期格式,比如"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static JsonArray objToArr(Object o,String dateFormat){
		Gson gson = new GsonBuilder().setDateFormat(dateFormat)
				.create();
		JsonElement jsonElement = gson.toJsonTree(o);
		return jsonElement.getAsJsonArray();
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static JsonArray objToArr(Object o){
		return objToArr(o, "yyyy-MM-dd");
	}
	
	/**
	 * 将对象转化成JSONObject,支持指定时间格式.
	 * @param o
	 * @param dateFomat
	 * @return
	 */
	public static JSONObject fromObject(Object o,String dateFomat){
		return JSONObject.parseObject(JSONObject.toJSONStringWithDateFormat(o, dateFomat, SerializerFeature.WriteDateUseDateFormat)); 
	}
	
	/**
	 * 将对象转化成JSONObject,时间类型使用默认格式yyyy-MM-dd HH:mm:ss.
	 * @param o
	 * @return
	 */
	public static JSONObject fromObject(Object o){
		return fromObject(o, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static JSONArray fromArr(Object o){
		return fromArr(o, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static JSONArray fromArr(Object o, String dataFormat) {
		return JSONObject.parseArray(JSONObject.toJSONStringWithDateFormat(o, dataFormat, SerializerFeature.WriteDateUseDateFormat));
	}
	
	public static  void writeJSON(HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

	public static  void writeJSON(HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JsonGenerator responseJsonGenerator = factory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		responseJsonGenerator.writeObject(obj);
	}
}
