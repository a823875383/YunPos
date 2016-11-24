package com.jsqix.yunpos.app.utils.hb;


import com.jsqix.yunpos.app.constant.Dictionary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


public class DataUtils {
	
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 2;
	//执行锁
	private static Object lock=new Object();
	
	/**
	 * 判断对象不为空
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		if (obj == null || obj.toString().length() == 0
				|| obj.toString().toUpperCase().equals("NULL")) {
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null || obj.toString().length() == 0
				|| obj.toString().toUpperCase().equals("NULL")) {
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 转换为BigDecimal
	 * @param obj
	 * @return
	 */
	public static BigDecimal $B(Object obj){
		return $B(obj,0);
	}
	/**
	 * 转换为BigDecimal
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static BigDecimal $B(Object obj, Object defaultVal){
		if(isNull(obj)){
			return new BigDecimal(String.valueOf(defaultVal));
		}
		return new BigDecimal(String.valueOf(obj));
	}
	
	/**
	 * 生成指定长度数字
	 */
	public static String makeNum(int len){
		String str="";
		while(str.length()<len){
			str+=(int)(Math.random()*10);
		}
		return str;
	}
	/**
	 * 转换为integer
	 */
	public static Integer I$(Object obj, int defaultValue){
		int tmp=0;
		try {
			tmp= Integer.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			tmp=defaultValue;
		}
		return tmp;
	}
	/**
	 * 转换为double
	 */
	public static Double D$(Object obj, double defaultValue){
		double tmp=0;
		try {
			tmp= Double.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			tmp=defaultValue;
		}
		return tmp;
	}
	/**
	* 转换为integer
	*/
	public static Integer I$(Object obj){
		return I$(obj, 0);
	}
	/**
	 * 转换为double
	 */
	public static Double D$(Object obj){
		return D$(obj, 0);
	}
	/**
	 * 将对象转换为字符串
	 * @param obj
	 * @return
	 */
	public static String S$(Object obj){
		return S$(obj,"");
	}
	
	/**
	 * 将对象转换为字符串
	 * @param obj
	 * @return
	 */
	public static String S$(Object obj, String defaultVal){
		if(obj==null ||obj.toString().trim().length()==0){
			return defaultVal;
		}else{
			return String.valueOf(obj);
		}
	}
	
	/**
	 * 产生len个不重复的数字
	 * 数字在0~len-1之间
	 * 数字以逗号隔开
	 * @param size
	 * @param len
	 * @return
	 */
	public static Object[] makeRdmArr(int size, int len){
		//默认：size > len
		Set<Integer> set=new HashSet<Integer>();
		while(set.toArray().length<len){
			set.add((int)(Math.random()*size));
		}
		return set.toArray();
	}
	/**
	 * 产生随机数0~num
	 * @param num
	 * @return
	 */
	public static int makeRdmNum(int num){
		return (int)(Math.random()*num);
	}
	
   /**
    * 根据权重获取随机索引
    * @param key
    * @param list
    * @return
    */
    public static int getRdmIndex(String key, List<Map<String,Object>> list){
		  List<Integer> rdmList=new ArrayList<Integer>();
		  Map<String,Object> map=null;
		  for(int i=0;i<list.size();i++){
			  map=list.get(i);
			  int sum= Integer.valueOf(String.valueOf(map.get(key)));
			  for(int j=0;j<sum;j++){
				  rdmList.add(i);
			  }
			 // Collections.shuffle(rdmList,new Random(UUID.randomUUID().getMostSignificantBits()));
		  }
		  Collections.shuffle(rdmList,new Random(UUID.randomUUID().getMostSignificantBits()));
		  return rdmList.get((int)(Math.random()*rdmList.size()));
    }
	/**
	 * 将字符编码
	 * @param str
	 * @param charset
	 * @return
	 */
    public static byte[] encode(String str, String charset){
    	byte[] arr=null;
    	try {
			arr=str.getBytes(charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
    }
    /**
     * 将list map中值为null的替换为空字符串
     * @param list
     * @return
     */
    public static List<Map<String,Object>> replaceListMapNullToString(List<Map<String,Object>> list){
    	if(list==null){
    		return list;
    	}
    	List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
    	for(Map<String,Object> map:list){
    		rtnList.add(replaceMapNullToString(map));
    	}
    	return rtnList;
    }
    /**
     * 将 map中值为null的替换为空字符串
     * @param list
     * @return
     */
    public static Map<String,Object> replaceMapNullToString(Map<String,Object> map){
    	if(map==null){
    		return map;
    	}
    	Map<String,Object> temp=new HashMap<String, Object>();
    	for(String key:map.keySet()){
    		temp.put(key, map.get(key)==null?"":map.get(key));
    	}
    	return temp;
    }
    
    /**
     * 检查输入字符串是否为金额
     * @param str
     * @return
     */
    public static boolean isMoney(String str){
    	return RegexUtils.isMoney(str);
    }
    
    /**
     * 将字符编码 utf-8
     * @param str
     * @param charset
     * @return
     */
    public static byte[] encode(String str){
    	return encode(str, Dictionary.SYSTEM_ENCODING_UTF_8);
    }
    /**
     * 判断map是否存在list中
     * @param list
     * @param key
     * @param val
     * @return
     */
    public static boolean exists(List<Map<String,Object>> list, String key, Object val){
    	boolean exists=false;
    	for(Map<String,Object> map:list){
    		if(String.valueOf(map.get(key)).equals(String.valueOf(val))){
    			exists=true;
    			break;
    		}
    	}
        return exists;
    }
    /**
     * 从list中找到map
     * @param list
     * @param key
     * @param val
     * @return
     */
    public static Map<String,Object> findMapFromList(List<Map<String,Object>> list, String key, Object val){
    	Map<String,Object> rMap=null;
    	for(Map<String,Object> map:list){
    		if(val.equals(map.get(key))){
    			rMap=map;
    			break;
    		}
    	}
    	return rMap;
    }
    
    /**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
    
	/**
	 * 制定字符串长度不足len在字符串前端 补0
	 * @param old
	 * @param len
	 * @return
	 */
	public static String foceStr(String old, int len){
		if(old.length()<len){
			int cl=len-old.length();
			StringBuffer sbf=new StringBuffer();
			for(int i=0;i<cl;i++){
				sbf.append("0");
			}
			return sbf.append(old).toString();
		}
		return old;
	}
	/**
	 * 生产指定长度的数字串
	 * @param length
	 * @return
	 */
	public synchronized static String makeRdmCode(int length){
		StringBuffer sbf=new StringBuffer();
		while(sbf.length()<length){
			sbf.append(new Random().nextInt(10));
		}
		return sbf.toString();
	}
	/**
	 * 扫码订单号
	 * @return
	 * @author caopengfei
	 * create on 2016-5-19  上午11:39:35
	 */
	public static String makeOrder(){
		synchronized (System.class) {
			StringBuffer sbf=new StringBuffer();
			sbf.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			while(sbf.length()<16){
				sbf.append(new Random().nextInt(10));
			}
			return sbf.toString();
		}
	}

	/**
	 * 生成随机数
	 * @return
	 */
	public synchronized static String makePhone(){
		int [] arr={139,138,137,136,135,134,159,150,151,158,157,188,187,152,182,147};
		return arr[new Random().nextInt(arr.length)]+makeRdmCode(8);
	}
	
	/**
	 * 从指定idx截取字符串
	 * @param str
	 * @param idx
	 * @return
	 * @author caopengfei
	 * create on 2016-6-16  下午06:57:35
	 */
	public synchronized static String subStart(String str, String idx){
		return str.substring(str.indexOf(idx)+idx.length());
	}
	
	/**
	 * 从开始位置截取到指定idx
	 * @param str
	 * @param idx
	 * @return
	 * @author caopengfei
	 * create on 2016-6-16  下午06:59:18
	 */
	public synchronized static String subEnd(String str, String idx){
		return str.substring(0,str.indexOf(idx));
	}
	/**
	 * 从指定位置开始截取到指定位置内容
	 * @param rtn
	 * @param idx
	 * @param edx
	 * @return
	 */
	public synchronized static String getValue(String rtn, String idx, String edx){
		String tmpStr=rtn.substring(rtn.indexOf(idx)+idx.length());
		return tmpStr.substring(0,tmpStr.indexOf(edx));
	}
	/**
	 * 从指定位置开始截取到指定位置内容
	 * 分两次细化位置
	 * @param rtn
	 * @param idxa
	 * @param idxb
	 * @param edx
	 * @return
	 */
	public synchronized static String getValue(String rtn, String idxa, String idxb, String edx){
		String tmpStr=rtn.substring(rtn.indexOf(idxa)+idxa.length());
		tmpStr=tmpStr.substring(tmpStr.indexOf(idxb)+idxb.length());
		return tmpStr.substring(0,tmpStr.indexOf(edx));
	}
	
	
	/**
	 * 随机生成ipv4地址
	 * @return
	 * @author caopengfei
	 * create on 2016-6-28  下午06:56:22
	 */
	public static String randomAddr(){
		StringBuffer sbf=new StringBuffer();
		sbf.append((int)(Math.random()*255));
		sbf.append(".");
		sbf.append((int)(Math.random()*255));
		sbf.append(".");
		sbf.append((int)(Math.random()*255));
		sbf.append(".");
		sbf.append((int)(Math.random()*255));
		return sbf.toString();
	}
	/**
	 * 获取批次号
	 * @return
	 */
	public static String getBatchNo(){
		synchronized (lock) {
			return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		}
	}
	/**
	 * 获取uuid
	 * 格式d04e9571a5d940f0b1a2367d2b5a44b1
	 * @return
	 */
	public static String getRdmUUID(){
		synchronized (lock) {
			return UUID.randomUUID().toString().replace("-", "");
		}
	}
	
	public static void main(String[] args) {
		String rtn="<input type=\"hidden\" name=\"USR_NOPWD_FLG\" id=\"USR_NOPWD_FLG\" value=\"111\" /><!--1 代表 用户勾选了 使用免密支付-->";
		System.out.println(getValue(rtn, "value=\"", "\""));
		System.out.println(getValue(rtn, "name=\"USR_NOPWD_FLG\"", "value=\"","\""));
	}
}
