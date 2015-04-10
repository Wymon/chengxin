package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.IncomeCommonBack;

public interface IncomeCommonBackService {
	
	public Long save(IncomeCommonBack back);

	public void update(IncomeCommonBack back);

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap);

	/**
	 * 查询状态为0的可以修改记录
	 * @return
	 */
	public List<Map<String,Object>> queryBack();
	/**
	 * 查询状态为0的可以修改记录
	 * @param IncomeId
	 * @return
	 */
	public Map<String,Object> findBackByIncomeId(Long IncomeId);
	
	public PageList queryPageList(Map<String, Object> queryMap,
			Integer index, Integer pageSize);
}
