package xx.tream.chengxin.ms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报表参数
 * @author huawen
 *
 */
public interface ReportParamService {
	
	/**
	 * 取得此段时间内的收入其它项目名称,以数组形式返回
	 * 参数可以为空表示返回全部
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryIncomeOtherItem(Date startDate ,Date endDate);

	/**
	 * 取得其它支出项目
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryPayoutOtherItem(Date startDate ,Date endDate);
	/**
	 * 取得公共支出项目
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryPayoutCommonOtherItem(Date startDate ,Date endDate);
	/**
	 * 取得公共收入其它项目 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryIncomeCommonOtherItem(Date startDate ,Date endDate);
	/**
	 * 财务日报查询结果 ,
	 * @param queryMap
	 * @param incomeItems
	 * 		收入项目 
	 * @param incomeOhterItems
	 * 		其它收入
	 * @param payoutItems
	 * 		支出
	 * @param payoutOtherItems
	 * 		其它支出
	 * @param payoutCommonItems
	 * 		公共支出
	 * @param payoutCommonOtherItems
	 * 		公共其它支出
	 * @return
	 */
	public List<Map<String,Object>> queryIncomeAndPayout(Map<String,Object> queryMap
			,String[] incomeItems,String[] incomeOhterItems,String[] incomeCommonItems,
			String[] incomeCommonOtherItems,
			String[] payoutItems,String[] payoutOtherItems,
			String[] payoutCommonItems,String[] payoutCommonOtherItems);
	/**
	 * 统计应收学费
	 * @param queryMap
	 * @return
	 */
	public Map<String,Object> queryPayable(Map<String,Object> queryMap);
}
