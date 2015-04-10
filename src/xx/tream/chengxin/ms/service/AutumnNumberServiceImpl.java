package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.AutumnNumber;

@Service
public class AutumnNumberServiceImpl
  implements AutumnNumberService
{

  @Autowired
  private DaoUtil dao;

  public long save(AutumnNumber autumnNumber)
  {
    return ((Long)this.dao.save(autumnNumber)).longValue();
  }

  public void update(AutumnNumber autumnNumber)
  {
    this.dao.update(autumnNumber);
  }

  public void delete(long id)
  {
    this.dao.delete(AutumnNumber.class, Long.valueOf(id));
  }

  public Map<String, Object> find(long id)
  {
    String sql = "select a.*,t.name from tb_autumnnumber a left join tb_train t on a.trainId = t.id where a.id = ? ";
    List<Map<String,Object>> list = this.dao.queryForList(sql,id);
    if ((list != null) && (list.size() > 0)) {
      return (Map<String,Object>)list.get(0);
    }
    return null;
  }

  public Map<String, Object> findByTrain(long trainId){
    String sql = "select a.*,t.name from tb_autumnnumber a left join tb_train t on a.trainId = t.id where a.trainId = ? ";
    List<Map<String,Object>> list = this.dao.queryForList(sql, trainId);
    if ((list != null) && (list.size() > 0)) {
      return (Map<String,Object>)list.get(0);
    }
    return null;
  }
}