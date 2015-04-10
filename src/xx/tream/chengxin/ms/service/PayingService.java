package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;
import xx.tream.chengxin.ms.model.Paying;

public interface PayingService {
	
	public long save(Paying paramPaying);

	public void update(Paying paramPaying);

	public void delete(long paramLong);

	public Map<String, Object> find(long paramLong);

	public double sumPayByTrainId(Long paramLong);

	public List<Map<String, Object>> queryByParam(
			Map<String, Object> paramMap);

	public Map<String, Object> findPaying(long paramLong);
	
	public List<Map<String,Object>> queryByTrainIds(String ids);
}
