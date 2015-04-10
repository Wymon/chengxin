package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.PayoutCommon;

public interface PayoutCommonService {
	
	public long save(PayoutCommon payoutCommon);

	public void update(PayoutCommon payoutCommon);

	public void delete(long id);

	public Map<String, Object> find(long id);

	public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);
	
	public PageList queryByMap(Map<String, Object> queryMap, Integer currentPage,
			Integer pageSize);
	
	public Double queryByMap(Map<String, Object> queryMap);
	
}
