package xx.tream.chengxin.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.PayoutCommonBack;
@Service
public class PayoutCommonBackServiceImpl implements PayoutCommonBackService {
	@Autowired
	private DaoUtil dao;
	public Long save(PayoutCommonBack back) {
		return (Long)this.dao.save(back);
	}

	public void update(PayoutCommonBack back) {
		this.dao.update(back);
	}

	public List<Map<String, Object>> queryByParam(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select i.* from tb_payoutcommon_back i where 1=1 ");
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

	public List<Map<String, Object>> queryBack() {
		String sql = "select i.*,b.id backId ,b.updateStatus,b.createUser bcreateUser from tb_payoutCommon i left join tb_payoutCommon_back b on i.id = b.payoutId and b.updateStatus =0  where  i.status = 0 ";
		return this.dao.queryForList(sql.toString());
	}

	
	public Map<String, Object> findBackByPayoutId(Long payoutId) {
		String sql = "select * from tb_payoutCommon_back where payoutId = ? and updateStatus = 0 ";
		List<Map<String,Object>> list = this.dao.queryForList(sql.toString(),payoutId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageList queryPageList(Map<String, Object> queryMap, Integer index,
			Integer pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select i.*,b.id backId ,b.updateStatus,b.createUser bcreateUser from tb_payoutCommon i left join tb_payoutCommon_back b on i.id = b.payoutId and b.updateStatus =0  where  1=1 ");
		if(queryMap.get("status")!=null&&!queryMap.get("status").toString().equals("")){
			sql.append(" and i.status = ").append(queryMap.get("status"));
		}
		if(queryMap.get("type")!=null&&!queryMap.get("type").toString().equals("")){
			if(queryMap.get("type").toString().length()>4){
				String otherType = queryMap.get("type").toString().substring(0,2);
				if(otherType.equals("其它")){
					String t = queryMap.get("type").toString();
					if(t.length()>4){
						t = t.substring(4,t.length()-1);
					}
					sql.append(" and i.type ='其它' "
							+ "and i.otherType like '%").append(t).append("%' ");
				}else{
					sql.append(" and (i.type like '%").append(queryMap.get("type")).append("%' or i.otherType like '%").append(queryMap.get("type")).append("%' ) ");
				}
			}else{
				sql.append(" and (i.type like '%").append(queryMap.get("type")).append("%' or i.otherType like '%").append(queryMap.get("type")).append("%' ) ");
			}
		}
		if(queryMap.get("createUserName")!=null&&!queryMap.get("createUserName").toString().equals("")){
			sql.append(" and i.createUserName like '%").append(queryMap.get("createUserName")).append("%'");
		}
		if(queryMap.get("startDate")!=null&&!queryMap.get("startDate").toString().equals("")){
			sql.append(" and i.auditTime >= '").append(queryMap.get("startDate")).append("'");
		}
		if(queryMap.get("endDate")!=null&&!queryMap.get("endDate").toString().equals("")){
			sql.append(" and i.auditTime < '").append(queryMap.get("endDate")).append("'");
		}
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and i.createTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and i.createTime < '").append(queryMap.get("endTime")).append("'");
		}
		if(queryMap.get("note")!=null&&!queryMap.get("note").toString().equals("")){
			sql.append(" and i.note like '%").append(queryMap.get("note")).append("%'");
		}
		sql.append(" order by i.status ,i.createTime,i.id desc ");
		return dao.findObjectByPageList(sql.toString(), index, pageSize);
	}

}
