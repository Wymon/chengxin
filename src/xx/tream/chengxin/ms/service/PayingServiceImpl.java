package xx.tream.chengxin.ms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.Paying;

@Service
public class PayingServiceImpl implements PayingService {

	@Autowired
	private DaoUtil dao;

	public long save(Paying paying) {
		return ((Long) this.dao.save(paying)).longValue();
	}

	public void update(Paying paying) {
		this.dao.update(paying);
	}

	public void delete(long id) {
		this.dao.delete(Paying.class, Long.valueOf(id));
	}

	public Map<String, Object> find(long id) {
		String sql = "select * from tb_paying where id = ? ";
		return this.dao.queryForMap(sql, new Object[] { Long.valueOf(id) });
	}

	public double sumPayByTrainId(Long trainId) {
		String sql = "select sum(paying) payings from tb_paying where trainId = ? and status = 1 ";
		List<Map<String, Object>> list = this.dao.queryForList(sql,
				new Object[] { trainId });
		if ((list != null) && (list.size() > 0)) {
			Map<String, Object> m = (Map<String, Object>) list.get(0);
			double pay = (double) m.get("payings");
			return pay;
		}
		return 0.0D;
	}



	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("SELECT p.*,t.name,t.idcard FROM tb_paying p left join tb_train t on p.trainId = t.id where 1=1 ");
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

	public Map<String, Object> findPaying(long payingId) {
		StringBuffer sql = new StringBuffer(100);
		sql.append(
				"SELECT a.autumnnumber,t.name,t.idcard,t.payable,p.paying,(t.payable+ (case when tv.allincome is null then 0 else tv.allincome end )) allincome ")
				.append(",ti.allpaying +(case when tv.allincome is null then 0 else tv.allincome end) allpaying  ,payable ,p.createUserName,p.auditUserName ")
				.append(",p.createTime,p.auditTime,p.status pstatus FROM tb_paying p ")
				.append("LEFT JOIN tb_train t ON p.trainId = t.id ")
				.append("LEFT JOIN trainincomeview tv ON tv.trainId = p.trainId ")
				.append("LEFT JOIN trainpayingview ti ON ti.trainId = p.trainId ")
				.append("LEFT JOIN tb_autumnnumber a ON a.trainid = p.trainId ")
				.append("where p.id = ? ");
		List<Map<String, Object>> list = this.dao.queryForList(sql.toString(),
				new Object[] { Long.valueOf(payingId) });
		if ((list != null) && (list.size() > 0)) {
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

	@Override
	public List<Map<String, Object>> queryByTrainIds(String ids) {
		String sql = "select * from tb_paying where trainId in ("+ids+")";
		return dao.queryForList(sql);
	}
}