package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.PayoutBack;

@Service
public class PayoutBackServiceImpl implements PayoutBackService {

	@Autowired
	private DaoUtil dao;

	public Long save(PayoutBack payoutBack) {
		return (Long) this.dao.save(payoutBack);
	}

	public void update(PayoutBack payoutBack) {
		this.dao.update(payoutBack);
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.* from tb_payout_back i where 1=1 ");
		if ((queryMap.get("payoutId") != null)
				&& (!queryMap.get("payoutId").equals(""))) {
			sql.append(" and i.payoutId = ").append(queryMap.get("payoutId"));
		}
		if ((queryMap.get("createUser") != null)
				&& (!queryMap.get("createUser").equals(""))) {
			sql.append(" and i.createUser = ").append(
					queryMap.get("createUser"));
		}
		if ((queryMap.get("notCreateUser") != null)
				&& (!queryMap.get("notCreateUser").equals(""))) {
			sql.append(" and i.createUser != ").append(
					queryMap.get("notCreateUser"));
		}
		if ((queryMap.get("updateStatus") != null)
				&& (!queryMap.get("updateStatus").equals(""))) {
			sql.append(" and i.updateStatus = ").append(
					queryMap.get("updateStatus"));
		}
		return this.dao.queryForList(sql.toString());
	}

	public void updateStatus(long payoutId) {
		String sql = "update tb_payout_back set updateStatus = 1 where payoutId = ? and updateStatus = 0 ";
		this.dao.update(sql, new Object[] { Long.valueOf(payoutId) });
	}
	
	public List<Map<String, Object>> queryBackByTrainId(Long trainId) {
		String sql = "select i.*,b.id backId ,b.updateStatus,b.createUser bcreateUser from tb_payout i left join tb_payout_back b on i.id = b.payoutId and b.updateStatus =0  where i.trainId = ? ";
		return this.dao.queryForList(sql.toString(),trainId);
	}

	public Map<String, Object> findBackByPayoutId(Long payoutId) {
		String sql = "select * from tb_payout_back where payoutId = ? and updateStatus = 0 ";
		List<Map<String,Object>> list = this.dao.queryForList(sql.toString(),payoutId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}