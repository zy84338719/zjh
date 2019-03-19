package com.zttx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

	// 將一般格式的日期转换成特定格式的日期
	public static Date format1(Date old) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String strDate = simpleDateFormat.format(old);
		try {
			return simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 将日期转成特定格式的字符串
	public static String format2(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String strDate = simpleDateFormat.format(date);
		strDate = strDate.replace(':', '-').replace(' ', '-');
		return strDate;
	}

	// 将日期转成特定格式的字符串
	public static String format3(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpleDateFormat.format(date);
		strDate = strDate.replace(':', '-').replace(' ', '-');
		return strDate;
	}

	// 将字符串转成特定格式的日期
	public static Date format4(String str) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.parse(str);
	}
}
