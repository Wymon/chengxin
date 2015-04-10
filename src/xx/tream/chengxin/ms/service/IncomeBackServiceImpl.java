package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.IncomeBack;

@Service
public class IncomeBackServiceImpl implements IncomeBackService {

	@Autowired
	private DaoUtil dao;

	public Long save(IncomeBack incomeBack) {
		return (Long) this.dao.save(incomeBack);
	}

	public void update(IncomeBack incomeBack) {
		this.dao.update(incomeBack);
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.* from tb_income_back i where 1=1 ");
		if ((queryMap.get("incomeId") != null)
				&& (!queryMap.get("incomeId").equals(""))) {
			sql.append(" and i.incomeId = ").append(queryMap.get("incomeId"));
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

	public void updateStatus(long incomeId) {
		String sql = "update tb_income_back set updateStatus = 1 where incomeId = ? and updateStatus = 0 ";
		this.dao.update(sql, new Object[] { Long.valueOf(incomeId) });
	}


	public List<Map<String,Object>> queryBackByTrainId(long trainId){
		String sql = "select i.*,b.id backId ,b.updateStatus,b.createUser bcreateUser from tb_income i left join tb_income_back b on i.id = b.incomeId and b.updateStatus =0  where i.trainId = ?  ";
		return this.dao.queryForList(sql.toString(),trainId);
	}

	public Map<String, Object> findBackByIncomeId(long incomeId) {
		String sql = "select * from tb_income_back where incomeId = ? and updateStatus = 0 ";
		List<Map<String,Object>> list = this.dao.queryForList(sql.toString(),incomeId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}