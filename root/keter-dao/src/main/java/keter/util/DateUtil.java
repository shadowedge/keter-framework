package keter.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Some Date related utilities.
 * 
 * @author Maciej Hamiga, Dawid Fatyga
 */
public class DateUtil {

	private DateUtil() {}

	public static Calendar todayCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, 0);
		return calendar;
	}

	public static Date today() {
		return todayCalendar().getTime();
	}
	
	/**
	 * 获取当前日期的前一天日期
	 * @author src-dingchd
	 * @return
	 */
	public static Date yesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取当前日期的之前第n天的日期
	 * @param number
	 * @author src-dingchd
	 * @return
	 */
	public static Date beforeToday(int days){
		Calendar calendar = Calendar.getInstance();
		int n = 0 - days;
		
		calendar.add(Calendar.DATE,n );		
		return calendar.getTime();		
	}
	
	/**
	 * 返回今天日期是否为每个月的第一天
	 * @param today 日期参数
	 * @author src-dingchd
	 * @return
	 */
	public static boolean isFirstDayOfMonth(Date today){		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return (day==1);
	}
	
	/**
	 * 返回今天日期是否为1月1日
	 * @param today 日期参数
	 * @author src-dingchd
	 * @return
	 */
	
	public static boolean isFirstDayOfYear(Date today){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		return day==1 && month == 0;
	}
	public static Date daysAgo(int number) {
		Calendar calendar = todayCalendar();
		calendar.roll(Calendar.DAY_OF_MONTH, -number);
		return calendar.getTime();
	}

	public static Date getPeriodFrom(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getCurrentPeriod(){
		return getPeriodFrom(new Date());
	}

	public static Date getNextPeriodFrom(Date periodStart) {
		periodStart = getPeriodFrom(periodStart);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(periodStart);
		//calendar.roll(Calendar.MONTH, true);
        calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}
	
	public static boolean isJanuary(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) +1;
		return (month==1);
	}

}
