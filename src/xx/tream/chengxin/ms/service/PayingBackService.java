package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;
import xx.tream.chengxin.ms.model.PayingBack;

public interface PayingBackService {
	
	public Long save(PayingBack paramPayingBack);

	public void update(PayingBack paramPayingBack);

	public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);

	public void updateStatus(long paramLong);
	/**
	 * 与修改相关
	 * @param trainId
	 * @return
	 */
	public List<Map<String,Object>> queryBackByTrainId(long trainId);
	/**
	 * 查询状态为0的
	 * @param payingId
	 * @return
	 */
	public Map<String,Object> findByPayingId(long payingId);
}
