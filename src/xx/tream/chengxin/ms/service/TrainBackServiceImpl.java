package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.TrainBack;

@Service
public class TrainBackServiceImpl implements TrainBackService {

	@Autowired
	private DaoUtil dao;

	public Long save(TrainBack trainBack) {
		return (Long) this.dao.save(trainBack);
	}

	public void update(TrainBack trainBack) {
		this.dao.update(trainBack);
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.*,u.realName from tb_train_back i left join tb_user u on i.updateUser = u.id where 1=1 ");
		if ((queryMap.get("trainId") != null)
				&& (!queryMap.get("trainId").equals(""))) {
			sql.append(" and i.trainId = ").append(queryMap.get("trainId"));
		}
		if ((queryMap.get("createUser") != null)
				&& (!queryMap.get("createUser").equals(""))) {
			sql.append(" and i.createUser = ").append(
					queryMap.get("createUser"));
		}
		if ((queryMap.get("notCreateUser") != null)
				&& (!queryMap.get("notCreateUser").equals(""))) {
			sql.append(" and i.createUser != ").append(
					queryMap.get("notCreateUserr"));
		}
		if ((queryMap.get("updateStatus") != null)
				&& (!queryMap.get("updateStatus").equals(""))) {
			sql.append(" and i.updateStatus = ").append(
					queryMap.get("updateStatus"));
		}
		return this.dao.queryForList(sql.toString());
	}

	public void updateStatus(long trainId) {
		String sql = "update tb_train_back set updateStatus = 1 where trainId = ? and updateStatus = 0 ";
		this.dao.update(sql, new Object[] { Long.valueOf(trainId) });
	}

	@Override
	public Map<String, Object> findByTrainId(long trainId) {
		String sql = "select * from tb_train_back where trainId = ? and updateStatus = 0";
		List<Map<String,Object>> list = this.dao.queryForList(sql.toString(),trainId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}