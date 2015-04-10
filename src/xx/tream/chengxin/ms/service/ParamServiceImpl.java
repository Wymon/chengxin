package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.Param;
@Service
public class ParamServiceImpl implements ParamService {
	@Autowired
	private DaoUtil dao;
	@Override
	public long save(Param param) {
		return (long)dao.save(param);
	}

	@Override
	public void update(Param param) {
		this.dao.update(param);
	}

	@Override
	public void delete(long id) {
		this.dao.delete(Param.class, id);
	}

	@Override
	public Map<String, Object> find(long id) {
		String sql = "select * from tb_param where id = ? ";
		List<Map<String,Object>> list = dao.queryForList(sql,id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> findByCode(String code) {
		String sql = "select * from tb_param where code = ? ";
		List<Map<String,Object>> list = dao.queryForList(sql,code);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
}
