package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 报表
 * @author huawen
 *
 */
public interface ReportService {
	/**
	 * 财务日报
	 * @param workbook
	 * @param queryMap
	 * @return
	 */
	public HSSFWorkbook financialStatements(HSSFWorkbook workbook,Map<String,Object> queryMap);
	/**
	 * 财务日报明细
	 * @param workbook
	 * @param queryMap
	 * @return
	 */
	public HSSFWorkbook financialStatementsDetail(HSSFWorkbook workbook,Map<String,Object> queryMap);
	
	/**
	 * 导出公共支出
	 * @param workbook
	 * @param queryMap
	 * @return
	 */
	public HSSFWorkbook payoutCommonReport(HSSFWorkbook workbook,Map<String,Object> queryMap);
	
	/**
	 * 导出公共收入
	 * @param workbook
	 * @param queryMap
	 * @return
	 */
	public HSSFWorkbook incomeCommonReport(HSSFWorkbook workbook,Map<String,Object> queryMap);
	/**
	 * 统计学员收入支出
	 * @param queryMap
	 * @return
	 */
	public List<Map<String,Object>> queryTrainIncomePayout(Map<String,Object> queryMap);
	/**
	 * 统计合计
	 * @param queryMap
	 * @return
	 */
	public List<Map<String,Object>> queryCountByTime(Map<String,Object> queryMap);
}
