package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import xx.tream.chengxin.ms.model.Income;

public interface IncomeService{
	
  public long save(Income paramIncome);

  public void update(Income paramIncome);

  public void delete(long paramLong);

  public Map<String, Object> find(long paramLong);

  public List<Map<String, Object>> findByTrain(Long paramLong);

  public List<Map<String, Object>> queryByParam(Map<String, Object> paramMap);
  
  public Map<String,Object> findIncome(Long id);
  
  public List<Map<String,Object>> queryByTrainIds(String ids);
}

