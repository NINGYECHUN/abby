package com.esm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TimeUtil {

	/**
	 * 返回两个日期相差的毫秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		return (time2 - time1);
	}

	/**
	 * 将日期字符串转换为日期格式
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateStr, String format) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = dateFormat.parse(dateStr);
		return date;
	}
	
	/**
	 * 格式化日期.
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date,String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 将时间转换为String格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		
		if(Objects.isNull(date)) {
			return "";
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String date2Str(Date date) {

		if (Objects.isNull(date)) {
			return "";
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间:format
	 */
	public static String getCurTimeToFormat(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		String str = dateFormat.format(new Date());
		return str;
	}

	public static DateFormat getDateFormatDay() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	public static DateFormat getDateFormatTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public static DateFormat getDateFormatMonth() {
		return new SimpleDateFormat("yyyy-MM");
	}
	
	public static DateFormat getDateFormatYear() {
		return new SimpleDateFormat("yyyy");
	}

	/**
	 * 获取当前时间:format
	 */
	public static String getDefaultFormatCurTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dateFormat.format(new Date());
		return str;
	}

	/**
	 * 获取当前日期的前后i天的日期
	 * 
	 * @param i
	 * @return
	 */
	public static String getDate(int i) {

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);

		return dateString;
	}

	/**
	 * 获取某日期的前后i天的日期
	 * 
	 * @param i
	 * @return
	 */
	public static String getDate(String dateStr, int i) {
		String dateString = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			dateString = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}

	/**
	 * 时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */

	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 根据 interval 间隔 获取 时间段集合
	 * 
	 * @param startDate
	 * @param endDate
	 * @param interval
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> getDateIntervalList(String startDate, String endDate, int interval)
			throws Exception {
		List<Map<String, String>> resList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			resList = new ArrayList<Map<String, String>>();

			Date date1 = sdf.parse(startDate);
			Date date2 = sdf.parse(endDate);

			Calendar startCld = Calendar.getInstance();
			startCld.setTime(date1);

			Calendar endCld = Calendar.getInstance();
			endCld.setTime(date2);

			Calendar curMaxDate = Calendar.getInstance();

			Map<String, String> map = null;
			while (!startCld.getTime().after(date2)) {// 起始段小于结束日期
				map = new HashMap<String, String>();

				map.put("startDate", sdf.format(startCld.getTime()));

				Calendar endDay = Calendar.getInstance();
				endDay.setTime(startCld.getTime());
				endDay.add(Calendar.MONTH, interval - 1);
				endDay.set(Calendar.DAY_OF_MONTH, endDay.getActualMaximum(Calendar.DAY_OF_MONTH));
				if (!endDay.getTime().after(date2)) {
					map.put("endDate", sdf.format(endDay.getTime()));
					curMaxDate.setTime(endDay.getTime());
				} else {
					map.put("endDate", sdf.format(date2));
					curMaxDate.setTime(date2);
				}

				resList.add(map);
				startCld.setTime(curMaxDate.getTime());
				startCld.add(Calendar.DAY_OF_MONTH, 1);// 日+1
			}

		} catch (Exception e) {
			return null;
		}
		return resList;
	}

	/**
	 * 如果超过一天（86400000）则转换成（MM月dd日 HH小时mm分钟ss秒）
	 * @param timeLong
	 * @return
	 */
	public static String getTimeByLong(Long timeLong) {
		Date date = new Date(timeLong);
		if(timeLong > 86400000) {
			return new SimpleDateFormat("MM月dd日 HH小时mm分钟ss秒").format(date);
		}
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	/**
	 * 字符串转日期
	 * 
	 * @param object
	 * @return
	 * @throws ParseException
	 */
	public static Date str2Date(String obj) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(obj);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
	
	/**
	 * 把日期设置到这个日期的月份的第一天
	 * @param date
	 * @return
	 */
	public static Date setToFirstDayOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 判断输入的日期是否是本年.
	 * @param date 要判断的日期
	 * @return
	 */
	public static Boolean isCurrentYear(Date date){
		if(date == null){
			return false;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		String currentYear = dateFormat.format(new Date());
		String toBeCompareYear = dateFormat.format(date);
		return currentYear.equals(toBeCompareYear);
	}
	
	/**
	 * 判断输入的日期是否是本年.
	 * @param date 要判断的日期
	 * @return
	 */
	public static Boolean isCurrentYear(Object dateStr){
		if(StringUtil.isEmpty(dateStr)){
			return false;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		String currentYear = dateFormat.format(new Date());
		return dateStr.toString().contains(currentYear);
	}
	
	/**
	 * 上一个月的最后一天
	 * @return
	 */
	public static String getBeforeMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, cal_1.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置为1号,当前日期既为本月第一天
		return format.format(cal_1.getTime());
	}

	/**
	 * 上一个月的第一天
	 * @return
	 */
	public static String getBeforeMonthFristDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(cal_1.getTime());
	}
	
	/**
	 * 格式化天.
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date){
		if(date == null){
			return null;
		}else{
			return getDateFormatDay().format(date);
		}
	}
	
	/**
	 * 格式化年月(格式是yyyy-MM).
	 * @param date
	 * @return
	 */
	public static String formatMonth(Date date){
		if(date == null){
			return null;
		}else{
			return getDateFormatMonth().format(date);
		}
	}
	
	/**
	 * 获取指定日期的前几个月份的年月值yyyy-MM.
	 * @param date 指定日期
	 * @param number 前几个月
	 * @return
	 */
	public static String getLastYearMonthByNumber(Date date,int number){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -number);
		return getDateFormatMonth().format(cal.getTime());
	}
	
	/**
	 * 获取两个时间的月份差值.
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Integer getMonthsByTwoDate(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return  (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 +cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
	}
	
	/**
	 * 获取某日期的前后i月的日期,
	 * @param dateStr 格式必须是yyyy-MM
	 * @param i
	 * @return 是yyyy-MM格式的
	 */
	public static Date getMonth(Date date, int i) {
		try {
			DateFormat df = getDateFormatMonth();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, i);// 把日期往后增加一天.整数往后推,负数往前移动
			return calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据日两个时间，算出时间差
	 * @param endTime
	 * @param startTime
	 * @return
	 */
	public static Object getTimeByTwoDate(Long diff) {
		
		long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    
	    // 获得两个时间的毫秒时间差异
	    if(diff > 86400000) {
			return day + "天" + hour + "小时" + min + "分钟";
		}
	    
	    // 计算差多少秒//输出结果
	    // long sec = diff % nd % nh % nm / ns;
		return hour + "小时" + min + "分钟";
	}
}
