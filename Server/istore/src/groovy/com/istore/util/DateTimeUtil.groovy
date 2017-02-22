package com.istore.util

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

import sun.util.resources.CalendarData;

class DateTimeUtil {
    def final static long oneSecond  = 1000
    def final static long oneMin = 60*oneSecond
    def final static long oneHour = 60*oneMin
    def final static long oneDay = 24*oneHour
    def final static long oneMonth = 30*oneDay
    def final static long oneYear  = 12*oneMonth
    public static Date current(){
        return new Date();
    }

    /**
     * Parse a datetime string.
     * @param param datetime string, pattern: "MM/dd/yyyy".
     * @return java.util.Date
     */
    public static Date parse(String format) {
        Date date = null
        SimpleDateFormat sdf
        if(format && format.indexOf('-') != -1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd")
        } else {
            sdf = new SimpleDateFormat("MM/dd/yyyy")
        }
        try {
            date = sdf.parse(format)
        } catch(Exception ex) {
        }
        return date
    }
	

	
    /**
     * Return short format datetime string.
     * @param date java.util.Date
     * @return short format datetime
     */
    public static String shortFmt(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")
        return sdf.format(date)
    }

    public static String shortFmt(Date date, String formatStr) {
        if (!date) return ""
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr)
        return sdf.format(date)
    }
    /**
     * Parse a datetime string.
     * @param param datetime string, pattern: "MM/dd/yyyyHH:mm".
     * @return java.util.Date
     */
    public static Date parse(String format, String format2) {
        Date date = null
        if(format2) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyyHH:mm")
            try {
                date = sdf.parse(format + format2)
            } catch(Exception ex) {
                date = parse(format)
            }
        } else {
            date = parse(format)
        }
        return date
    }
   
    /**
     * 根据时间字符串得到Date对象
     * @param time 时间字符串
     * @param format 时间格式
     * @return
     */
    public static Date parseHm(String time, String format) {
        Date date = null
        SimpleDateFormat sdf
        if(time && time.indexOf('-') != -1) {
            sdf = new SimpleDateFormat(format)
        } else {
            sdf = new SimpleDateFormat(format)
        }
        try {
            date = sdf.parse(time)
        } catch(Exception ex) {
        }
        return date
    }

	
	/**
	 * 计算年龄
	 */
	
	public static int yearsBetween(Date date1)
	{
		int days=timeBetween(date1,new Date(), "day")
		return days/365
	}
	
    /**
     * 计算两个日期之间相差的时间，单位依据unit或天或小时或分或秒
     * @param date1
     * @param date2
     * @param unit a String one of following 'day', 'hour', 'minute', 'second'
     * @return
     */
    public static long timeBetween(Object date1,Object date2, String unit) {
        try {
            Date d1
            Date d2
            if(date1 instanceof String && date2 instanceof String) {
                d1 = parse(date1)
                d2 = parse(date2)
            } else {
                d1 = date1
                d2 = date2
            }
            long time1 = d1.time
            long time2 = d2.time
            long calDivide = 1
            if(unit.equals("day")) {
                calDivide = 1000 * 60 * 60 * 24
            } else if(unit.equals("hour")) {
                calDivide = 1000 * 60 * 60
            } else if(unit.equals("minute")) {
                calDivide = 1000 * 60
            } else if(unit.equals("second")) {
                calDivide = 1000
            }
            long result = (time2 - time1) / (calDivide)
            return Integer.parseInt(String.valueOf(result))
        } catch(Exception e) {
            return 0
        }
    }

   
    /**
     * 计算两个日期之间相差的时间，返回相应的天、小时、分钟、秒
     * @param date1
     * @param date2
     * @return [result,unit] // unit a String one of following 'day', 'hour', 'minute', 'second'
     */
    static getInterval2(Object date1,Object date2) {
        if(date1==null || date2 == null){
            return [:]
        }
        long time1 = date1.time;
        long time2 = date2.time;
        long result = (time1 - time2);
        def interval  = []
        def unit
        if(result < 1000) {
            result = 0
            unit='second'
        } else if(result >= 1000 && result < oneMin) {
            result = result/(1000)
            unit=(result>1?'seconds':'second')
        } else if(result >= oneMin && result < oneHour) {
            result = result/oneMin
            unit=(result>1?'mins':'min')
        } else if(result >= oneHour && result < oneDay) {
            result = result/oneHour
            unit=(result>1?'hrs':'hr')
        } else if(result >= oneDay) {
            result = result/oneDay
            unit=(result>1?'days':'day')
        }

        return [result:result,unit:unit]
    }
	
	
    static getIntervalStr(Object date1,Object date2) {
        def intervalMap = getInterval(date1, date2)
        def intervalStr = ""
        if(intervalMap.result){
            intervalStr = intervalMap.result + new String(intervalMap.unit)
        }
        return intervalStr
    }

    /**
     * 得到星期字符串: Mon, Tue, Wed, Thu, Fri, Sat, Sun
     * @param date
     * @return
     */
    def static getWeek(def date) {
        def result = ''
        def c = Calendar.instance
        c.setTime(date)
        def i = c.get(Calendar.DAY_OF_WEEK)
        if(i == 1) {
            result = 'Sun'
        } else if(i == 2) {
            result = 'Mon'
        } else if(i == 3) {
            result = 'Tue'
        } else if(i == 4) {
            result = 'Wed'
        } else if(i == 5) {
            result = 'Thu'
        } else if(i == 6) {
            result = 'Fri'
        } else if(i == 7) {
            result = 'Sat'
        }
        return result
    }

    /**
     * 得到时间字符串: hh:mi
     * @param date
     * @return
     */
    def static getTime(def date) {
        def c = Calendar.instance
        c.setTime(date)
        return "${c.get(Calendar.HOUR_OF_DAY)}:${c.get(Calendar.MINUTE)}"
    }

    public static void main(String[] args){
        Date d = DateTimeUtil.current();
        System.out.println(d)
    }
        
    /*
     *get duration in days
     */
    def static getDurationForExpired(Date date1,Date date2,Long expireIntervalDays){
        if(date2!=null){
            long duration = date2.getTime() + expireIntervalDays*24*60*60*1000 - date1.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(duration);
            long hours = TimeUnit.MILLISECONDS.toHours(duration);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

            def returnStr = ""
            if (days == 0){
                if (hours > 0){
                    if(hours > 1){
                        returnStr = hours + " hrs"
                    }else{
                        returnStr = hours + " hr"
                    }                    
                }else if(hours == 0){
                    if(minutes > 0){
                        if(minutes > 1){
                            returnStr = minutes + " mins"
                        }else{
                            returnStr = minutes + " min"
                        }                        
                    }
                }
            }else if(days > 0){
                if(days > 1){
                    returnStr = days + " days"
                }else{
                    returnStr = days + " day"
                }
            }
            returnStr
        }
    }
    /*
     *getDateByYearAndMonth
     */
    def static getDateByYearAndMonth(def year,def month){
        def date = null
        if(year && month){
            def dateStr = ''+year + '-' + month
            date = parseHm(dateStr,'yyyy-MM')
        }
        return  date
    }
}
