package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.PayoutCommonBack;

public interface PayoutCommonBackService {
	
	public Long save(PayoutCommonBack back);

	public void update(PayoutCommonBack back);

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap);

	/**
	 * 查询状态为0的可以修改记录
	 * @return
	 */
	public List<Map<String,Object>> queryBack();
	/**
	 * 查询状态为0的可以修改记录
	 * @param payoutId
	 * @return
	 */
	public Map<String,Object> findBackByPayoutId(Long payoutId);
	
	public PageList queryPageList(Map<String, Object> queryMap,
			Integer index, Integer pageSize);
}
