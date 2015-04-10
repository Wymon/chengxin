package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.PayingBack;

@Service
public class PayingBackServiceImpl implements PayingBackService {

	@Autowired
	private DaoUtil dao;

	public Long save(PayingBack payingBack) {
		return (Long) this.dao.save(payingBack);
	}

	public void update(PayingBack payingBack) {
		this.dao.update(payingBack);
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.* from tb_paying_back i  where 1=1 ");
		if ((queryMap.get("payingId") != null)
				&& (!queryMap.get("payingId").equals(""))) {
			sql.append(" and i.payingId = ").append(queryMap.get("payingId"));
		}
		if ((queryMap.get("createUser") != null)
				&& (!queryMap.get("createUser").equals(""))) {
			sql.append(" and i.createUser = ").append(
					queryMap.get("createUser"));
		}
		if ((queryMap.get("notCreatUser") != null)
				&& (!queryMap.get("notCreatUser").equals(""))) {
			sql.append(" and i.notCreatUser != ").append(
					queryMap.get("notCreatUser"));
		}
		if ((queryMap.get("updateStatus") != null)
				&& (!queryMap.get("updateStatus").equals(""))) {
			sql.append(" and i.updateStatus = ").append(
					queryMap.get("updateStatus"));
		}
		return this.dao.queryForList(sql.toString());
	}

	public void updateStatus(long payingId) {
		String sql = "update tb_paying_back set updateStatus = 1 where payingId = ? and updateStatus = 0 ";
		this.dao.update(sql, new Object[] { Long.valueOf(payingId) });
	}

	
	public List<Map<String, Object>> queryBackByTrainId(long trainId) {
		String sql = "select p.*,pk.id backId ,pk.updateStatus,pk.createUser bcreateUser from tb_paying p left join tb_paying_back pk on p.id = pk.payingid and pk.updatestatus!=1 and pk.updatestatus !=2 where p.trainId = ? ";
		return this.dao.queryForList(sql.toString(),trainId);
	}

	
	public Map<String, Object> findByPayingId(long payingId) {
		String sql = "select * from tb_paying_back where payingid = ? and updateStatus = 0 ";
		List<Map<String,Object>> list = this.dao.queryForList(sql.toString(),payingId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}