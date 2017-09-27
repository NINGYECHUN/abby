package com.esm.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * @category 字符串处理
 * @author 宁业春
 *
 */
public class StringUtil {

	private volatile static StringUtil stringUtil;

	private StringUtil() {
	}

	public static StringUtil getStringUtil() {
		if (stringUtil == null) {
			synchronized (StringUtil.class) {
				if (stringUtil == null) {
					stringUtil = new StringUtil();
				}
			}
		}
		return stringUtil;
	}

	/**
	 * @category null转空字符串
	 * @param str
	 *            空字符串 or 原非空字符串
	 * @return
	 */
	public static String nullTOEmpty(String str) {
		return str == null ? "" : str;
	}
	
	/**
	 * @category null转空字符串
	 * @param str
	 *            空字符串 or 原非空字符串
	 * @return
	 */
	public static String nullToEmpty(Object str) {
		return str == null ? "" : str.toString();
	}
	
	//异常转为字符串输出
	public static  String exceptionToString(Exception e1){
		StringWriter sw = null;
		PrintWriter pw = null;
		String rs = null;
		try{
			if(e1 != null){
				sw = new StringWriter();
		        pw = new PrintWriter(sw);
		        e1.printStackTrace(pw);
				rs = "\r\n" + sw.toString() + "\r\n";
			}
		}catch(Exception e){
			
		}finally{
			try{
				if(sw !=null){
					sw.close();
				}
				if(pw != null){
					pw.close();
				}
			}catch(Exception e){
			}
		}
		return rs;
	}
	
	public static Boolean isEmpty(String str){
		if(str == null || str.trim().isEmpty() || "null".equalsIgnoreCase(str)){
			return true;
		}
		return false;
	}
	
	public static Boolean isEmpty(Object str){
		if(str == null || str.toString().trim().isEmpty() || "null".equalsIgnoreCase(str.toString())){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验list是否为空
	 * @param list 传入的校验对象
	 * @return 校验结果 true表示传入的对象为空或空字符或只带空格的字符串，false表示字符串不为空
	 */
	public static Boolean isEmpty(List list){
		return (list==null||list.size() == 0);
	}
	
	/**
	 * 将null 转换为""
	 * @param obj
	 * @return
	 */
	public static String null2Empty(Object obj) {
		return StringUtil.isEmpty(obj) ? "" : obj.toString();
	}
	
	//去掉字符串中的比如 ,A,B,C,,D,中的逗号和空值，结果应该为A,B,C,D
	public static String deleteCommaAndEmptyValue(String str){
		if(str == null){
			return null;
		}
		String[] arr = str.split(",");
		StringBuffer rs = new StringBuffer("");
		for(String a :arr){
			if(!isEmpty(a)){
				if(rs.length() > 0){
					rs.append(","); 
				}
				rs.append(a);
			}
		}
		return rs.toString();
	}
	
		//将字符串加上单引号，比如 ,A,B,C,,D, 这个字符串 ，结果应该为'A','B','C','','D',''
		public static String addSingleQuote(String str){
			if(str == null){
				return null;
			}
			String[] arr = str.split(",");
			StringBuffer rs = new StringBuffer("");
			for(String a :arr){
				if(rs.length() > 0){
					rs.append(","); 
				}
				rs.append("'"+a+"'");
			}
			return rs.toString();
		}
		
		//是否小数
		public static Boolean isDecimal(String qty){
			Boolean isDecimal = null;
			try{
				BigDecimal d = new BigDecimal(qty);
				if(d.longValue() != d.doubleValue()){
					isDecimal = true;
				}else{
					isDecimal = false;
				}
			}catch(Exception e){
				isDecimal =false;
			}
			return isDecimal;
		} 
		
		public static String objectToString(Object obj){
		if (obj == null) {
			return "NULL";
		}
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(obj);
		return jsonElement.toString();}
		
		/**
		 *字符串是否全是整数
		 * @param str
		 * @return
		 */
		public static Boolean isAllNumber(String str){
			Pattern p = Pattern.compile("^-?[1-9]d*$");
			Matcher m = p.matcher(str);
			if (!m.matches()) {
				return false;
			}else{
				return true;
			}
		}
		
		/**
		 * 对象转成字段串
		 * @param o 对象
		 * @param dateFormat 日期格式,比如"yyyy-MM-dd HH:mm:ss"
		 * @return
		 */
		public static String objToString(Object o,String dateFormat){
			Gson gson = new GsonBuilder().setDateFormat(dateFormat)
					.create();
			JsonElement jsonElement = gson.toJsonTree(o);
			return jsonElement.toString();
		}
		
		/**
		 * 对象转成字段串
		 * @param o 对象
		 */
		public static String objToString(Object o){
			return objToString(o,"yyyy-MM-dd HH:mm:ss");
		}
		
		/**
		 * true -> Y , false - > N
		 * @param selfCreate
		 * @return
		 */
		public static String bool2Str(Boolean selfCreate) {
			return selfCreate ? "Y" : "N";
		}
		
		/**
		 * 1 - > Y, else -> N
		 * @param i
		 * @return
		 */
		public static String intger2Str(Integer i) {
			return new Integer(1).equals(i) ? "Y" : "N";
		}
		
		
		
		/**
		 * 删除异常中的java.lang.Exception信息.
		 * @param msg
		 * @return
		 */
		public static String deleteExceptionString(String msg) {
			String deleteString = "java.lang.Exception:";
			if (msg != null) {
				return msg.replace(deleteString, "");
			} else {
				return msg;
			}
		}
		
		/**
		 * 空的值转换成0，非空则将值转换成integer.
		 * @param o
		 * @return
		 */
		public static Integer emptyTo0Int(Object o){
			if(o == null){
				return 0;
			}else{
				return Double.valueOf(o.toString()).intValue();
			}
		}
		
		/**
		 * 空的值转换成0，非空则将值转换成integer.
		 * @param o
		 * @return
		 */
		public static BigDecimal emptyTo0str(Object o){
			String oStr = null;
			if(o == null){
				oStr =  "0";
			}else{
				oStr = o.toString();
			}
			return new BigDecimal(oStr);
		}

}
