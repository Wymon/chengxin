package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.chengxin.ms.model.UserBack;

public interface UserBackService {

	public Long save(UserBack back);

	public void update(UserBack back);

	public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);

	/**
	 * 查询状态为0
	 * @param backId
	 * @return
	 */
	public Map<String,Object> findBackByBackId(long backId);
	
	public Map<String,Object> find(Long id);
	
	/**
	 * 取得所有用户
	 * @return
	 */
	public List<Map<String,Object>> queryList();
}
