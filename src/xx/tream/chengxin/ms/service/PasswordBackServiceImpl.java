package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.model.PasswordBack;
@Service
public class PasswordBackServiceImpl implements PasswordBackService {
	@Autowired
	private DaoUtil dao;
	
	public Long save(PasswordBack back) {
		return (Long)dao.save(back);
	}

	public void update(PasswordBack back) {
		dao.update(back);
	}
	
	public Map<String, Object> findByUserId(long userId) {
		String sql = "select * from tb_password_back where userId = ? and updatestatus = 0 ";
		List<Map<String,Object>> list = dao.queryForList(sql, userId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	public Map<String, Object> find(Long id) {
		String sql = "select * from tb_password_back where id = ?";
		return dao.queryForMap(sql,id);
	}

}
