package xx.tream.chengxin.ms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.Payout;

@Service
public class PayoutServiceImpl implements PayoutService {

	@Autowired
	private DaoUtil dao;

	public long save(Payout payout) {
		return ((Long) this.dao.save(payout)).longValue();
	}

	public void update(Payout payout) {
		this.dao.update(payout);
	}

	public void delete(long id) {
		this.dao.delete(Payout.class, Long.valueOf(id));
	}

	public Map<String, Object> find(long id) {
		String sql = "select e.*,t.name,t.idcard,a.autumnNumber from tb_payout e left join tb_train t on e.trainId = t.id  left join tb_autumnnumber a on e.trainId = a.trainId where e.Id = ? ";
		List<Map<String, Object>> list = this.dao.queryForList(sql,
				new Object[] { Long.valueOf(id) });
		if ((list != null) && (list.size() > 0)) {
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

	public List<Map<String, Object>> findByTrain(Long trainId) {
		String sql = "select e.* from tb_payout e  where e.trainId = ? order by e.id desc ";
		return this.dao.queryForList(sql, new Object[] { trainId });
	}

	public List<Map<String, Object>> queryByParam(Long trainId, String status) {
		String sql = "select e.* from tb_payout e where e.trainId = ? and e.status = ? order by e.id desc ";
		return this.dao.queryForList(sql, new Object[] { trainId, status });
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select p.*,t.name,t.idcard from tb_payout p left join tb_train t on p.trainId = t.id where 1=1 ");
		if ((queryMap.get("trainId") != null)
				&& (!queryMap.get("trainId").equals(""))) {
			sql.append(" and p.trainId = ").append(queryMap.get("trainId"));
		}
		if ((queryMap.get("status") != null)
				&& (!queryMap.get("status").equals(""))) {
			sql.append(" and p.status = ").append(queryMap.get("status"));
		}
		if ((queryMap.get("beginTime") != null)
				&& (!queryMap.get("beginTime").equals(""))) {
			sql.append(" and p.auditTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if ((queryMap.get("endTime") != null)
				&& (!queryMap.get("endTime").equals(""))) {
			sql.append(" and p.auditTime <= '").append(queryMap.get("endTime")).append("'");
		}
		return this.dao.queryForList(sql.toString());
	}

	@Override
	public List<Map<String, Object>> queryByTrainIds(String ids) {
		String sql = "select * from tb_payout where trainId in ("+ids+")";
		return dao.queryForList(sql);
	}
}