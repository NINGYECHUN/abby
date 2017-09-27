package com.esm.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * 转换工具类
 * 
 * @author 宁业春
 * 
 */
public class TransforUtil {

	/**
	 * 将数据从map自动提取到bean
	 * 
	 * @param map
	 *            map数组，系统只会取第一个
	 * @param dest
	 *            目标对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public static Object transFromMapToBean(Map<String, String[]> map, Object dest)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		if (dest == null || map == null) {
			return null;
		}
		Set<String> keySet = map.keySet();
		// Field[] fields = dest.getClass().getDeclaredFields();
		// Field[] fields = dest.getClass().getFields();
		for (String key : keySet) {

			// for(Field field:fields){
			// if(key.equals(field.getName())){
			
			String value = null;
			if (map.get(key) != null && map.get(key)[0] != null && !map.get(key)[0].isEmpty()) {
				value = map.get(key)[0];
			}
			System.out.println("key="+key+",value="+value);
			if (value == null) {
				continue;
			}
			PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(key, dest.getClass());
			} catch (Exception e) {
				continue;
			}
			Method setMethod = pd.getWriteMethod();
			if (setMethod == null) {
				continue;
			}
			String paramType = setMethod.getParameterTypes()[0].toString();

			if (paramType.equals("class java.lang.String")) {

				setMethod.invoke(dest, value);

			} else if (paramType.equals("class java.util.Date")) {
				if (10 == value.length()) {
					setMethod.invoke(dest, TimeUtil.getDateFormatDay().parse(value));
				}
				if (19 == value.length()) {
					value = value.replace("T", " ");
					setMethod.invoke(dest, TimeUtil.getDateFormatTime().parse(value));
				}

			} else if (paramType.equals("class java.lang.Float") || paramType.equals("float")) {

				setMethod.invoke(dest, Float.parseFloat(value));

			} else if (paramType.equals("class java.lang.Long") || paramType.equals("long")) {

				setMethod.invoke(dest, Long.parseLong(value));

			} else if (paramType.equals("class java.lang.Double") || paramType.equals("double")) {

				setMethod.invoke(dest, Double.parseDouble(value));

			} else if (paramType.equals("class java.lang.Integer") || paramType.equals("int")) {

				setMethod.invoke(dest, new BigDecimal(value).intValue());

			} else if (paramType.equals("class java.math.BigDecimal")) {

				setMethod.invoke(dest, new BigDecimal(value));

			} else if (paramType.equals("class java.lang.Boolean") || paramType.equals("boolean")) {
				setMethod.invoke(dest, Boolean.parseBoolean(value));
			}
			// }
			// }
		}
		return dest;
	}

	// 从Map<String,String[]>转换成Map<String,Object>
	public static Map<String, Object> transRMapToMap(Map<String, String[]> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			rsMap.put(key, map.get(key) == null ? null : map.get(key)[0]);
		}
		return rsMap;
	}
	
	public static Map<String, Object> transRMapToMapObj(Map<String, String[]> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			rsMap.put(key, map.get(key) == null ? null : map.get(key)[0]);
		}
		return rsMap;
	}

	// 从Map<String,Object>转换成Map<String,String[]>
	public static Map<String, String[]> transMapToRMap(Map<String, Object> map) {
		Map<String, String[]> rsMap = new HashMap<String, String[]>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			rsMap.put(key, map.get(key) == null ? null : new String[] { map.get(key).toString() });
		}
		return rsMap;
	}

	/**
	 * 如果是空，那么就是N，一般用于checkbox
	 * 
	 * @param value
	 *            传入的要判断的值
	 * @return Y|N
	 */
	public static String transIsEmptyToN(String value) {
		return "Y".equals(value) ? "Y" : "N";
	}

	/**
	 * 从Y转换成on，一般用于checkbox
	 * 
	 * @param value
	 *            传入的要判断的值
	 * @return on|""
	 */
	public static String transFromYToOn(String value) {
		return "Y".equals(value) ? "on" : "";
	}

	/**
	 * 从true 或者 on 转换为Y，一般用于checkbox
	 * 
	 * @param value
	 *            传入的要判断的值
	 * @return Y|N
	 */
	public static String transFromTrueToY(String value) {
		return ("true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value) || "Y".equalsIgnoreCase(value)) ? "Y"
				: "N";
	}

	/**
	 * 从Y 或者 on 转换为true，一般用于checkbox
	 * 
	 * @param value
	 *            传入的要判断的值
	 * @return Y|N
	 */
	public static String transFromYToTrue(String value) {
		return ("true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value) || "Y".equalsIgnoreCase(value)) ? "true"
				: "false";
	}
	
	/**
	 * 如果传入的字符串是true，那么就返回1，否则返回0
	 * @param o
	 * @return
	 */
	public static Integer transTrueTo1(String o){
		if("true".equals(o)){
			return 1;
		}else{
			return 0;
		}
	} 
	
	/**
	 * 如果传入的字符串是1，那么就返回是，否则返回否
	 * @param o
	 * @return
	 */
	public static String trans1ToShi(Number num){
		if(num == null){
			return "否";
		}
		if(new Long(1).equals(num.longValue())){
			return "是";
		}else{
			return "否";
		}
	} 
	
	public static String deleteExceptionString(String msg) {
		String deleteString = "java.lang.Exception:";
		if (msg != null) {
			return msg.replace(deleteString, "");
		} else {
			return msg;
		}
	}
	
	public  static JsonElement obj2GsonDefault(Object obj,  String timeFormat) {
		Gson gson = new GsonBuilder().setDateFormat(timeFormat).create();
		return gson.toJsonTree(obj);
	}
	
	public  static JsonElement obj2GsonDefault(Object obj ) {
		return obj2GsonDefault(obj, "yyyy-MM-dd");
	}
	
	/**
	 * bean转换成Map.
	 * @param bean 需要转换的bean
	 * @param timeFormat 时间格式，如yyyy-MM-dd
	 * @return
	 */
	public static Map<String,Object> transBeanToMap(Object bean, String timeFormat){
		if(bean == null){
			return new HashMap<String,Object>();
		}
		Gson gson = new GsonBuilder().setDateFormat(timeFormat).create();
		JsonElement je =  gson.toJsonTree(bean);
		System.out.println(je.toString());
		Map<String,Object> rsMap = gson.fromJson(je, Map.class);
		return rsMap;
	}
	
	/**
	 * bean转换成Map.
	 * @param bean 需要转换的bean
	 * @param timeFormat 时间格式，如yyyy-MM-dd
	 * @return
	 */
	public static Map<String,Object> transBeanToMap(Object bean){
		return transBeanToMap(bean, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 从数据库标注字段转换成驼峰命名，比如USER_NAME转换成userName
	 */
	public static String transToHump(String attrName) {
		if (attrName == null) {
			return null;
		}
		StringBuffer result = new StringBuffer("");
		String[] arr = attrName.split("_");
		/*if(arr.length == 1){
			return attrName;
		}*/
		String word = null;
		for (int i = 0; i < arr.length; i++) {
			// 第一个全部小写
			if (i == 0) {
				result.append(arr[0].toLowerCase());
			} else {// 第二个开始第一个字母大写
				word = arr[i];
				word = word.toLowerCase();
				word = word.replaceFirst("[" + word.substring(0, 1) + "]", word
						.substring(0, 1).toUpperCase());
				result.append(word);
			}
		}

		return result.toString();
	}
	
	/**
	 * 获取优先级
	 * @param priority
	 * @return
	 */
	public static Object getPriorityName(int priority) {
		
		if(priority == 50) {
			return "一般";
		}
		
		if(priority > 50) {
			return "高";
		}
		
		return "底";
	}
}
