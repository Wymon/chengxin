package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.chengxin.ms.model.AutumnNumberBack;

public interface AutumnNumberBackService {
	
	public Long save(AutumnNumberBack back);

	public void update(AutumnNumberBack back);

	public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);

	/**
	 * 查询状态为0
	 * @param backId
	 * @return
	 */
	public Map<String,Object> findBackByBackId(long backId);
	
	public Map<String,Object> find(Long id);
	
}
