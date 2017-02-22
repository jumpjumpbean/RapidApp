package com.istore.util

import org.apache.commons.lang.StringEscapeUtils
import org.springframework.web.util.JavaScriptUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

class StringUtil {
	  
	static String regEx = "[`~!@#\$%^&*()+\\-=|{}':;'\",//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]" 
	static Pattern p = Pattern.compile(regEx);

    //生成随机数字
    public static String getRandomNumber(int length) {
        String serialNumber = "";
        Random random = new Random();
        for(int i = 0;i < length; i++) {
            serialNumber += String.valueOf(random.nextInt(10));
        }

        return serialNumber
    }
	/**
	 * 得到字符窜列表
	 * @param str
	 * @return
	 */
	public static String[] getStringList(Object str) {
		String[] result = {
		};
		if(str instanceof String) {
			result = new String[1];
			result[0] = (String)str;
		} else {
			result = (String[])str;
		}
		return result;
	}

	/**
	 * 字符串转map
	 * @param source
	 * @return
	 */
	public static HashMap parseToMap(String source) {
		def result = [:]
		String[] mapList = source.split("#,")
		mapList.each {
			def content = it.split("#:")
			String key = ""
			String value = ""
			if(content.size()>1){
				key = it.split("#:")[0]
				value = it.split("#:")[1]
			}else if(content.size()==1){
				key = it.split("#:")[0]
			}
			result[key] = "${value}"
		}
		return result
	}

	/**
	 * Filter the contact info.
	 * @param content
	 * @return
	 */
	public static String filter(String content) {

		if(!content) return ''

		// filter email
		def pattern = ~/[\w-\.]+@(.|\s+|[\w-]+\.com)/
		def matcher = pattern.matcher(content)
		def count = matcher.getCount()
		(0..<count).each { i ->
			content = content.replaceAll(matcher[i][0],'*')
		}

		// filter phone number
		pattern = ~/(\d|\s|-){8,15}/
		matcher = pattern.matcher(content)
		count = matcher.getCount()
		(0..<count).each { i ->
			content = content.replaceAll(matcher[i][0],' **** ')
		}
		return content
	}

	/**
	 * 判断中英文姓名
	 */
	public static boolean ifGbkUserName(String nickName) {
		boolean isGbk = false;
		for (int i = 0; i < nickName.length(); i++) {
			String str = nickName.substring(i, i+1);
			//生成一个Pattern,同时编译一个正则表达式
			isGbk = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", str);
			if(isGbk){
				break;
			}
		}
		return isGbk;
	}

	/**
	 * 对页面中的特殊字符进行转义
	 */
	public static String getEscapeString(String content) {
		if(content == null) {
			return null;
		}

		return JavaScriptUtils.javaScriptEscape(content);
	}

	public static String getSqlEscapeString(String content) {
		if(content == null) {
			return null;
		}
		return StringEscapeUtils.escapeSql(content);
	}

	public static String getHtmlEscapeString(String content) {
		if(content == null) {
			return null;
		}
		return StringEscapeUtils.unescapeHtml(content);
	}
	
	public static String getHtmlAttrString(String content) {
		if(content == null) {
			return null;
		}
		content=getEscapeString(content);
		content=content.replaceAll("\'", "&#39;")
//		println content
		return content.replaceAll("\"", "&#34;");
	}

	public static String changeCharset(String str, String newCharset)
	throws UnsupportedEncodingException {
		if (str != null) {
			//用默认字符编码解码字符串。
			byte[] bs = str.getBytes();
			//用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * 获取用户IP
	 * @param request
	 * @return
	 */
	def static String getIpAddr(def request) {
		String ip = request.getHeader("x-forwarded-for")
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP")
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP")
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr()
		}
		return ip
	}

	/**
	 * 短信内容简介
	 * @param content
	 * @return
	 */
	def static String getSmsBrief(def content) {
		def result = content
		if(content && content.size() > 30) {
			result = content.substring(0, 30)
		}
		return result
	}
	
	/**
	* 获取字符串中的字节个数（汉字占两个，ASCII占一个）
	* @param str
	* @return
	*/
	def static int charLength(String str) throws Exception
	{
	    str=str.decodeHTML();
		byte[] bytes = str.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length; i++)
		{
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1)
			{
				n++; // 在UCS2第二个字节时n加1
			}
			else
			{
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0)
				{
					n++;
				}
			}
		}
		return n;
	}
	
	/**
	* 截取前n个字符
	* @param str
	* @param length
	* @return 
	*/
	def static String subString(String str, int length) throws Exception
	{
		str=str.decodeHTML();
		if(charLength(str) <= length){
			return str;
		}else{
			byte[] bytes = str.getBytes("Unicode");
			int n = 0; // 表示当前的字节数
			int i = 2; // 要截取的字节数，从第3个字节开始
			for (; i < bytes.length && n < length; i++)
			{
				// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
				if (i % 2 == 1)
				{
					n++; // 在UCS2第二个字节时n加1
				}
				else
				{
					// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
					if (bytes[i] != 0)
					{
						n++;
					}
				}
			}
			// 如果i为奇数时，处理成偶数
			if (i % 2 == 1)
			{
				// 该UCS2字符是汉字时，去掉这个截一半的汉字
				if (bytes[i - 1] != 0)
					i = i - 1;
				// 该UCS2字符是字母或数字，则保留该字符
				else
					i = i + 1;
			}
			String returnStr = new String(bytes, 0, i, "Unicode");
			return returnStr+"...";
		}
	}
	/**
	 * 统计字符串中的大写字母个数
	 * @param str
	 * @param length
	 * @return
	 */
	def static int uppercaseNum(String str,int length){
		if(str.length()>15){
			if(str.length()>length){
						char[] chars = str.toCharArray()  ;//将字符串转化成一个字符数组
						int UppercaseNum=0;
						for(int i=0;i<length;i++) {
						 if(chars[i]>64&&chars[i]<91) {
						  UppercaseNum++;
						 }
						}
					return UppercaseNum/3;
			}else{
					char[] chars = str.toCharArray()  ;//将字符串转化成一个字符数组
					int UppercaseNum=0;
					for(int i=0;i<str.length();i++) {
					 if(chars[i]>64&&chars[i]<91) {
					  UppercaseNum++;
					 }
					}
					return UppercaseNum/3;
			}
		}else{
		return 0
		}
	}
	
	def static String escapeURL(String str){
		return str.replace('%','%25').replace('+','%2B').replace(' ','%20').replace('/','%2F').replace('?','%3F').replace('#','%23').replace('&','%26').replace('=','%3D');
	}
	def static String unescapeURL(String str){
		return str.replace('%2B','+').replace('%20',' ').replace('%2F','/').replace('%3F','?').replace('%23','#').replace('%26','&').replace('%3D','=').replace('%25','%');
	}
	//过滤json特殊字符 
	def static String getJsonEncodeString(String str){
		str = str.replace("\\", "\\\\");
		str = str.replace("\b", "\\\b");
		str = str.replace("\t", "\\\t");
		str = str.replace("\n", "\\\n");
		str = str.replace("\f", "\\\f");
		str = str.replace("\r", "\\\r");
		str = str.replace("\"", "\\\"");
		return str
	}
	
	//将boardname 转换成linkname 去掉特殊是字符 并截取
	def static String boardname2linkname(String boardname)
	{
	  
		if(boardname == null) return "";
		boardname = boardname.trim();
		Matcher m = p.matcher(boardname)
		String s = m.replaceAll(" ").trim()
		s = s.replaceAll("\\s+", "-")
		return  subString(s,50)
	}
	
	def static String getFirstLetterUppercase(String strname)
	{
		if(strname == null || "".equals(strname)){
			return "";
		}else {
		    char firstletter = strname.charAt(0)
			if(firstletter>96&&firstletter<123) {
				strname = firstletter.toUpperCase().toString() + strname.substring(1)
			}
			return strname
		}
	}
	
	// escape single quotes and double quotes
	def static String escapeQuote( String string ) {
		return string.replaceAll( /'/, "\\\\'" ).replace( /"/, "\\\\\"" )
	}
}
