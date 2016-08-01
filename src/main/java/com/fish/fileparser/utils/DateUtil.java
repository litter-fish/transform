package com.fish.fileparser.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类
 *
 */
public class DateUtil {



  /**
   * 获取yyyyMMdd格式日期
   * 
   * @return
   */
  public static String getDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获取yyyy格式日期
   * 
   * @return
   */
  public static String getYYYY() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获取MM格式日期
   * 
   * @return
   */
  public static String getMM() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM");
    Date date = new Date();
    return sdf.format(date);
  }


  /**
   * 获取dd格式日期
   * 
   * @return
   */
  public static String getDD() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd");
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获取yyyyMMddHHmmss格式日期
   * 
   * @return
   */
  public static String getFullDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获取yyyy-MM-dd格式日期
   * 
   * @return
   */
  public static String getDate2() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获取日期格式mmhhss
   * 
   * @return
   */
  public static String getTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("mmhhssSSS");
    Date date = new Date();
    return sdf.format(date);
  }

  public static String getMonthAndDay(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date d = null;
    try {
      d = sdf.parse(date);
      String[] dates = sdf.format(d).split("-");
      return dates[1] + "-" + dates[2];
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 根据给定的日期 + 期限(月) 推算出新的日期值
   * 
   * @param dateStr
   * @param month
   * @return
   */
  public static String dateCalculation(String dateStr, int month) {
    String returnstr = "";
    try {
      SimpleDateFormat adf = new SimpleDateFormat("yyyyMMdd");
      Date date = adf.parse(dateStr);
      Calendar dateNow = Calendar.getInstance();

      dateNow.setTime(date);
      System.out.println(dateNow);
      // 对月进行加减
      dateNow.add(Calendar.MONTH, month);

      Date returnDate = dateNow.getTime();
      returnstr = adf.format(returnDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnstr;
  }

  public static String getStartTime() {
    Calendar todayStart = Calendar.getInstance();
    todayStart.set(Calendar.HOUR, 0);
    todayStart.set(Calendar.MINUTE, 0);
    todayStart.set(Calendar.SECOND, 0);
    todayStart.set(Calendar.MILLISECOND, 0);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date(todayStart.getTime().getTime()));
  }

  public static String getEndTime() {
    Calendar todayEnd = Calendar.getInstance();
    todayEnd.set(Calendar.HOUR, 23);
    todayEnd.set(Calendar.MINUTE, 59);
    todayEnd.set(Calendar.SECOND, 59);
    todayEnd.set(Calendar.MILLISECOND, 999);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date(todayEnd.getTime().getTime()));
  }

}
