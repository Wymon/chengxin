package xx.tream.chengxin.ms.service;

import java.util.Map;

import xx.tream.chengxin.ms.model.Param;

public interface ParamService {
	
	public long save(Param param);

	public void update(Param param);

	public void delete(long id);

	public Map<String, Object> find(long id);
	
	public Map<String, Object> findByCode(String code);
}
