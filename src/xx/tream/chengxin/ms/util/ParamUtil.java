package xx.tream.chengxin.ms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ParamUtil {
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (Map<String, Object>) session.getAttribute("user");
	}

	public static long getUserId(HttpServletRequest request) {
		Map<String, Object> map = getSessionUser(request);
		return (Long) map.get("id");
	}

	public static String getRealName(HttpServletRequest request) {
		Map<String, Object> map = getSessionUser(request);
		return (String) map.get("realName");
	}
	/**
	 * 收入项目 
	 */
	public static String[] incomeItems = { "挂靠费", "科一补（1）","科一补（2）","科一补（3）","科一补（4）","科一补（5）",
		"科二补（1）","科二补（2）","科二补（3）","科二补（4）","科二补（5）",
		"科三补（1）","科三补（2）","科三补（3）","科三补（4）","科三补（5）",
		"科四车费（1）","科四车费（2）","科四车费（3）","科四车费（4）","科四车费（5）", "打卡费",
		"租车费","阳山培训费",
			"一次性收入", "其它" };
	/**
	 * 收入项目 
	 */
	public static List<Map<String, Object>> getIncomeItemList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < incomeItems.length; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", i);
			m.put("name", incomeItems[i]);
			list.add(m);
		}
		return list;
	}
	/**
	 * 详细支出项目（精确到学员的）
	 */
	public static String[] payoutItems = {"科目补考费","科一补（1）","科一补（2）","科一补（3）","科一补（4）","科一补（5）",
		"科二补（1）","科二补（2）","科二补（3）","科二补（4）","科二补（5）",
		"科三补（1）","科三补（2）","科三补（3）","科三补（4）","科三补（5）",
		"阳山刷卡费","学员体检费","科四车费（1）","科四车费（2）","科四车费（3）","科四车费（4）","科四车费（5）"
		,"一次性支出","其它"};
	/**
	 * 详细支出项目 （精确到学员的）
	 */
	public static List<Map<String, Object>> getPayoutItemList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < payoutItems.length; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", i);
			m.put("name", payoutItems[i]);
			list.add(m);
		}
		return list;
	}
	/**
	 * 公用支出项目 
	 */
	public static String[] payoutCommonItems = {"汽油费","机油费","车年审年票","车季审",
		"车保险","IC卡费（含改卡）","教材费","办公费","考试车费","煤气费","米、油、副食品","菜金","业务费、接待费",
		"土地、复印机租金","退学费","工资","提成奖金","基本户支出","汽车维修","一次性支出","其它"};
	/**
	 * 公用支出项目 
	 * @return
	 */
	public static List<Map<String, Object>> getPayoutCommonItemList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < payoutCommonItems.length; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", i);
			m.put("name", payoutCommonItems[i]);
			list.add(m);
		}
		return list;
	}
	/**
	 * 公用收入项目
	 */
	public static String[] incomeCommonItems = {"一次性收入","其它"};
	/**
	 * 公用收入项目 
	 * @return
	 */
	public static List<Map<String, Object>> getIncomeCommonItemList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < incomeCommonItems.length; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", i);
			m.put("name", incomeCommonItems[i]);
			list.add(m);
		}
		return list;
	}
}