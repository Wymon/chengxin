package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;
import xx.tream.chengxin.ms.model.IncomeBack;

public interface IncomeBackService {
	
	public Long save(IncomeBack paramIncomeBack);

	public void update(IncomeBack paramIncomeBack);

	public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);

	public void updateStatus(long paramLong);
	/**
	 * 查询，同修改相关（可以修改的）
	 * @param trainId
	 * @return
	 */
	public List<Map<String,Object>> queryBackByTrainId(long trainId);
	/**
	 * 查询状态为0
	 * @param incomeId
	 * @return
	 */
	public Map<String,Object> findBackByIncomeId(long incomeId);
}
