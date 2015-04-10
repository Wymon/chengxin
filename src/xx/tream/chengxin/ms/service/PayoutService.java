package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.chengxin.ms.model.Payout;

public interface PayoutService {
	
	public long save(Payout paramPayout);

	public void update(Payout paramPayout);

	public void delete(long paramLong);

	public Map<String, Object> find(long paramLong);

	public List<Map<String, Object>> findByTrain(Long paramLong);

	public List<Map<String, Object>> queryByParam(Long paramLong,
			String paramString);

	public List<Map<String, Object>> queryByParam(
			Map<String, Object> paramMap);
	
	public List<Map<String,Object>> queryByTrainIds(String ids);
}
