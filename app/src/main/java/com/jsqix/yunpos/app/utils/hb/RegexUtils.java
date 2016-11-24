package com.jsqix.yunpos.app.utils.hb;

import java.util.regex.Pattern;

public class RegexUtils {
  public static final String REG_NUMBER="^\\d+$";
  public static final String REG_MONEY="^\\d+(\\.\\d{1,2})?$";
  /**
   * 检查输入字符串是否为数字
   * @param str
   * @return
   */
  public static boolean isNum(String str){
	  return Pattern.matches(REG_NUMBER, str);
  }
  /**
   * 检查输入字符串是否为金钱
   * @param str
   * @return
   */
  public static boolean isMoney(String str){
	  return Pattern.matches(REG_MONEY, str);
  }
  /**
   * 检查输入字符串是否为手机号
   * @param str
   * @return
   * @author caopf
   * create on 2016-11-12  下午3:28:43
   */
  public static boolean isPhone(String str){
	  return Pattern.matches("^[1]([3][0-9]{1}|[4][0-9]{1}|[5][0-9]{1}|[7][0-9]{1}|[8][0-9]{1})[0-9]{8}$", str);
  }
  
  /**
   * 检查输入字符串是否是指定长度的数字
   * @param str
   * @param len
   * @return
   * @author caopf
   * create on 2016-11-12  下午3:22:41
   */
  public static boolean isNumBySize(String str, int len){
	  
	  if(!Pattern.matches(REG_NUMBER, str)){
		  return false;
	  }
	  
	  if(str.length() != len){
		  return false;
	  }
	  
	  return true;
  }
  
  public static void main(String[] args) {
	 System.out.println(isNum("123"));
	 System.out.println(isMoney("0.12"));
	 System.out.println(isMoney("100"));
	 System.out.println(isPhone("15720674156"));
  }
}
