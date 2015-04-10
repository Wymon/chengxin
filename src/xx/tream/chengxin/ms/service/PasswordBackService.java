package xx.tream.chengxin.ms.service;

import java.util.Map;

import xx.tream.chengxin.ms.model.PasswordBack;

public interface PasswordBackService {
	
	public Long save(PasswordBack back);

	public void update(PasswordBack back);

	/**
	 * 查询状态为0的
	 * @param payingId
	 * @return
	 */
	public Map<String,Object> findByUserId(long userId);
	
	public Map<String,Object> find(Long id);
}
