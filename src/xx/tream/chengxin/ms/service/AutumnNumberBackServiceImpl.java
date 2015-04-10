package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.AutumnNumberBack;
@Service
public class AutumnNumberBackServiceImpl implements AutumnNumberBackService {
	@Autowired
	private DaoUtil dao;
	@Override
	public Long save(AutumnNumberBack back) {
		
		return (Long)dao.save(back);
	}

	@Override
	public void update(AutumnNumberBack back) {
		this.dao.update(back);
	}

	@Override
	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.* from tb_autumnNumber_back i where 1=1 ");
		if ((queryMap.get("backId") != null)
				&& (!queryMap.get("backId").equals(""))) {
			sql.append(" and i.backId = ").append(queryMap.get("backId"));
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

	@Override
	public Map<String, Object> findBackByBackId(long backId) {
		String sql = "select * from tb_autumnNumber_back where backId = ? and updateStatus = 0 ";
		List<Map<String,Object>> list = dao.queryForList(sql,backId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> find(Long id) {
		String sql = "select b.* from tb_autumnNumber_back b where b.id = ? ";
		List<Map<String,Object>> list = dao.queryForList(sql,id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
