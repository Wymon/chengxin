package xx.tream.chengxin.ms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.Income;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private DaoUtil dao;

	public long save(Income income) {
		return ((Long) this.dao.save(income)).longValue();
	}

	public void update(Income income) {
		this.dao.update(income);
	}

	public void delete(long id) {
		this.dao.delete(Income.class, Long.valueOf(id));
	}

	public Map<String, Object> find(long id) {
		String sql = "select e.* from tb_income e where e.Id = ?  ";
		List<Map<String, Object>> list = this.dao.queryForList(sql,id);
		if ((list != null) && (list.size() > 0)) {
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

	public List<Map<String, Object>> findByTrain(Long trainId) {
		String sql = "select e.* from tb_income e  where e.trainId = ? order by e.id desc ";
		return this.dao.queryForList(sql, new Object[] { trainId });
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.* ,t.name,t.idcard from tb_income i left join tb_train t on i.trainId = t.id  where 1=1 ");
		if ((queryMap.get("trainId") != null)
				&& (!queryMap.get("trainId").equals(""))) {
			sql.append(" and i.trainId = ").append(queryMap.get("trainId"));
		}
		if ((queryMap.get("status") != null)
				&& (!queryMap.get("status").equals(""))) {
			sql.append(" and i.status = ").append(queryMap.get("status"));
		}
		if ((queryMap.get("beginTime") != null)
				&& (!queryMap.get("beginTime").equals(""))) {
			sql.append(" and i.auditTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if ((queryMap.get("endTime") != null)
				&& (!queryMap.get("endTime").equals(""))) {
			sql.append(" and i.auditTime <= '").append(queryMap.get("endTime")).append("'");
		}
		return this.dao.queryForList(sql.toString());
	}

	@Override
	public Map<String, Object> findIncome(Long id) {
		String sql = "select i.*,t.name,t.idcard,a.autumnNumber from tb_income i left join tb_train t on i.trainId = t.id left join tb_autumnnumber a on i.trainId = a.trainId where i.id = ? ";
		List<Map<String,Object>> list = dao.queryForList(sql,id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> queryByTrainIds(String ids) {
		String sql = "select * from tb_income where trainId in ("+ids+")";
		return dao.queryForList(sql);
	}
}