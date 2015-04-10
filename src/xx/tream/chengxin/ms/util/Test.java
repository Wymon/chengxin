package xx.tream.chengxin.ms.util;

import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		Calendar startMonth = Calendar.getInstance();
		startMonth.set(startMonth.get(Calendar.YEAR), startMonth.get(Calendar.MONTH), 1);
		Calendar endMonth = Calendar.getInstance();
		endMonth.set(endMonth.get(Calendar.YEAR), endMonth.get(Calendar.MONTH), endMonth.get(Calendar.DATE)+1);
		String startTimeMonth = DateUtil.DateToString(startMonth.getTime(), "yyyy-MM-dd");
		String endTimeMonth = DateUtil.DateToString(endMonth.getTime(), "yyyy-MM-dd");
		System.out.println(startTimeMonth+"\n"+endTimeMonth);
	}

}
