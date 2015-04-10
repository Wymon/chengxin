package xx.tream.chengxin.ms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.Train;

public interface TrainService
{
  public long save(Train paramTrain);

  public void update(Train paramTrain);

  public void delete(long paramLong);

  public Map<String, Object> find(long paramLong);

  public PageList queryByMap(Map<String, Object> paramMap, Integer paramInteger1, Integer paramInteger2);

  public Map<String, Object> findByIdcard(String paramString);

  public boolean checkByIdcard(String paramString);

  public List<Map<String, Object>> queryForParam(Map<String, Object> paramMap);

  public List<Map<String, Object>> queryPaying(Map<String, Object> paramMap);

  public Map<String, Object> statistics(Map<String, Object> paramMap);

  public List<Map<String, Object>> dayStatistics(Date paramDate);
  /**
   * 是否有公共支出审核 
   * @return
   */
  public boolean hasPayoutCommonAudti();
  /**
   * 同修改记录相关
   * @param trainId
   * @return
   */
  public Map<String,Object> findBackByBTrainId(long trainId);
}

