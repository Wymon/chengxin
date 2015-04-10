package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;
import xx.tream.chengxin.ms.model.TrainBack;

public interface TrainBackService {
	
	public Long save(TrainBack paramTrainBack);

	public void update(TrainBack paramTrainBack);

	public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);

	public void updateStatus(long paramLong);
	
	public Map<String,Object> findByTrainId(long trainId);
}
