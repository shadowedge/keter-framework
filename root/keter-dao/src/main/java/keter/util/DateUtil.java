package keter.util;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * Some Date related utilities.
 * 
 * @author Maciej Hamiga, Dawid Fatyga
 */
public class DateUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DateUtil.class);

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
		return new DateTime().minusDays(1).toDate();
	}
	
	/**
	 * 获取当前日期的之前第n天的日期
	 * @param number
	 * @author src-dingchd
	 * @return
	 */
	public static Date beforeToday(int days){	
		return new DateTime().minusDays(days).toDate();
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
		return (new DateTime(date).monthOfYear().get()==1);
	}
	
	public static void main(String[] args) {
		logger.info(DateUtil.today());
		logger.info(new DateTime().monthOfYear().get());
		//yesterday
		logger.info(DateUtil.yesterday());
		logger.info(new DateTime().minusDays(1).toDate());
		
		//isJan
		Date d = new Date();
		logger.info(DateUtil.isJanuary(d));
		logger.info(new DateTime(d).monthOfYear().get()==1);
		
		//before
		logger.info(DateUtil.beforeToday(10)); 
		logger.info(new DateTime().minusDays(10).toDate());
	}

}
