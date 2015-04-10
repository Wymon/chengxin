package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;
import xx.tream.chengxin.ms.model.PayoutBack;

public interface PayoutBackService {
	public Long save(PayoutBack paramPayoutBack);

	public void update(PayoutBack paramPayoutBack);

	public List<Map<String, Object>> queryByParam(
			Map<String, Object> paramMap);

	public void updateStatus(long paramLong);
	/**
	 * 查询状态为0的可以修改记录
	 * @param trainId
	 * @return
	 */
	public List<Map<String,Object>> queryBackByTrainId(Long trainId);
	/**
	 * 查询状态为0的可以修改记录
	 * @param payoutId
	 * @return
	 */
	public Map<String,Object> findBackByPayoutId(Long payoutId);
}
