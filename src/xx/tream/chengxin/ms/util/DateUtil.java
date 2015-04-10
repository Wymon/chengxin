package xx.tream.chengxin.ms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间帮助类
 * @author huawen
 *
 */
public class DateUtil {
	/**
	 * String 转日期  
	 * @param date
	 *     时间
	 * @param type
	 * 		格式 如:yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception 
	 */
	public static Date stringToDate(String date,String type){
		if(date==null||date.toString().equals("")){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 日期转string
	 * @param date
	 * 		时间
	 * @param type
	 * 		格式 如:yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static String DateToString(Date date,String type){
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(date);
	}

}
