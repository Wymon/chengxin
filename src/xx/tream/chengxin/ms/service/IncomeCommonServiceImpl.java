package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.IncomeCommon;
@Service
public class IncomeCommonServiceImpl implements IncomeCommonService {

	@Autowired
	private DaoUtil dao;
	@Override
	public long save(IncomeCommon incomeCommon) {
		return (Long)dao.save(incomeCommon);
	}

	@Override
	public void update(IncomeCommon incomeCommon) {
		this.dao.update(incomeCommon);
	}

	@Override
	public void delete(long id) {
		this.dao.delete(IncomeCommon.class, id);
	}

	@Override
	public Map<String, Object> find(long id) {
		String sql = "select * from tb_incomeCommon where id = ?";
		List<Map<String,Object>> list = dao.queryForList(sql,id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select * from tb_incomecommon where 1=1 ");
		if(queryMap.get("status")!=null&&!queryMap.get("status").toString().equals("")){
			sql.append(" and status = ").append(queryMap.get("status"));
		}
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and auditTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and auditTime < '").append(queryMap.get("endTime")).append("'");
		}
		return this.dao.queryForList(sql.toString());
	}
	public PageList queryByMap(Map<String, Object> queryMap,
			Integer index, Integer pageSize) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select * from tb_incomecommon where 1=1 ");
		if(queryMap.get("status")!=null&&!queryMap.get("status").toString().equals("")){
			sql.append(" and status = ").append(queryMap.get("status"));
		}
		if(queryMap.get("type")!=null&&!queryMap.get("type").toString().equals("")){
			if(queryMap.get("type").toString().length()>4){
				String otherType = queryMap.get("type").toString().substring(0,2);
				if(otherType.equals("其它")){
					String t = queryMap.get("type").toString();
					if(t.length()>4){
						t = t.substring(4,t.length()-1);
					}
					sql.append(" and type ='其它' "
							+ "and otherType like '%").append(t).append("%' ");
				}else{
					sql.append(" and (type like '%").append(queryMap.get("type")).append("%' or otherType like '%").append(queryMap.get("type")).append("%' ) ");
				}
			}else{
				sql.append(" and (type like '%").append(queryMap.get("type")).append("%' or otherType like '%").append(queryMap.get("type")).append("%' ) ");
			}
			
		}
		if(queryMap.get("createUserName")!=null&&!queryMap.get("createUserName").toString().equals("")){
			sql.append(" and createUserName like '%").append(queryMap.get("createUserName")).append("%'");
		}
		
		if(queryMap.get("startDate")!=null&&!queryMap.get("startDate").toString().equals("")){
			sql.append(" and auditTime >= '").append(queryMap.get("startDate")).append("'");
		}
		if(queryMap.get("endDate")!=null&&!queryMap.get("endDate").toString().equals("")){
			sql.append(" and auditTime < '").append(queryMap.get("endDate")).append("'");
		}
		if(queryMap.get("note")!=null&&!queryMap.get("note").toString().equals("")){
			sql.append(" and note like '%").append(queryMap.get("note")).append("%'");
		}
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and createTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and createTime < '").append(queryMap.get("endTime")).append("'");
		}
		sql.append(" order by status ,createTime,id desc ");
		return this.dao.findObjectByPageList(sql.toString(), index, pageSize);
	}
	public Double queryByMap(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select sum(income) from tb_incomecommon where 1=1 ");
		if(queryMap.get("status")!=null&&!queryMap.get("status").toString().equals("")){
			sql.append(" and status = ").append(queryMap.get("status"));
		}
		if(queryMap.get("type")!=null&&!queryMap.get("type").toString().equals("")){
			if(queryMap.get("type").toString().length()>4){
				String otherType = queryMap.get("type").toString().substring(0,2);
				if(otherType.equals("其它")){
					String t = queryMap.get("type").toString();
					if(t.length()>4){
						t = t.substring(4,t.length()-1);
					}
					sql.append(" and type ='其它' "
							+ "and otherType like '%").append(t).append("%' ");
				}else{
					sql.append(" and (type like '%").append(queryMap.get("type")).append("%' or otherType like '%").append(queryMap.get("type")).append("%' ) ");
				}
			}else{
				sql.append(" and (type like '%").append(queryMap.get("type")).append("%' or otherType like '%").append(queryMap.get("type")).append("%' ) ");
			}
		}
		if(queryMap.get("createUserName")!=null&&!queryMap.get("createUserName").toString().equals("")){
			sql.append(" and createUserName like '%").append(queryMap.get("createUserName")).append("%'");
		}
		
		if(queryMap.get("startDate")!=null&&!queryMap.get("startDate").toString().equals("")){
			sql.append(" and auditTime >= '").append(queryMap.get("startDate")).append("'");
		}
		if(queryMap.get("endDate")!=null&&!queryMap.get("endDate").toString().equals("")){
			sql.append(" and auditTime < '").append(queryMap.get("endDate")).append("'");
		}
		if(queryMap.get("note")!=null&&!queryMap.get("note").toString().equals("")){
			sql.append(" and note like '%").append(queryMap.get("note")).append("%'");
		}
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and createTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and createTime < '").append(queryMap.get("endTime")).append("'");
		}
		sql.append(" order by status ,createTime,id desc ");
		return this.dao.queryForObject(sql.toString(),Double.class);
	}

}
