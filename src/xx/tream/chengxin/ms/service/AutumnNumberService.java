package xx.tream.chengxin.ms.service;

import java.util.Map;
import xx.tream.chengxin.ms.model.AutumnNumber;

public  interface AutumnNumberService {
	
	public long save(AutumnNumber paramAutumnNumber);

	public void update(AutumnNumber paramAutumnNumber);

	public void delete(long paramLong);

	public Map<String, Object> find(long id);

	public  Map<String, Object> findByTrain(long trainId);
}
