package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.IncomeCommon;

/**
 * 公共收入
 * 
 * @author huawen
 *
 */
public interface IncomeCommonService {

	public long save(IncomeCommon incomeCommon);

	public void update(IncomeCommon incomeCommon);

	public void delete(long id);

	public Map<String, Object> find(long id);

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryParam);

	public PageList queryByMap(Map<String, Object> queryMap, Integer index,
			Integer pageSize);

	public Double queryByMap(Map<String, Object> queryMap);
}
