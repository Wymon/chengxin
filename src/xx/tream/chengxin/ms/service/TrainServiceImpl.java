package xx.tream.chengxin.ms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.basepaltform.model.PageList;
import xx.tream.chengxin.ms.model.Train;

@Service
public class TrainServiceImpl implements TrainService {

	@Autowired
	private DaoUtil dao;

	public long save(Train train) {
		return ((Long) this.dao.save(train)).longValue();
	}

	public void update(Train train) {
		this.dao.update(train);
	}

	public void delete(long id) {
		this.dao.delete(Train.class, Long.valueOf(id));
	}

	public Map<String, Object> find(long id) {
		StringBuffer sql = new StringBuffer(100);
		sql.append(
				"select t.*,u.realName,a.autumnNumber, case when i.allincome is null then 0 else i.allincome end allincome ,")
				.append("case when p.allpaying is null then 0 else p.allpaying end allpaying ,a.id autumnNumberId ")
				.append("from tb_train t ")
				.append("left join tb_user u on t.createUser = u.id ")
				.append("left join tb_autumnNumber a on t.id = a.trainId ")
				.append("left join trainpayingview p on p.trainId = t.id ")
				.append("left join trainincomeview i on i.trainId = t.id ")
				.append("where t.id = ? ");
		return this.dao.queryForMap(sql.toString(),id);
	}

	public PageList queryByMap(Map<String, Object> map, Integer index,
			Integer side) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("select t.*,a.autumnNumber,ip.allincome ")
				.append(",ip.allpaying,h.hasAudit,ab.createUser abcreateUser,ab.id abId,case when u.id is not null then 1 else 0 end canUpdateAudit from tb_train t  ")
				.append("left join tb_autumnNumber a on t.id = a.trainId ")
				.append("left join trainincomepayingview ip on ip.trainId = t.id ")
				.append("left join hasAuditView h on t.id = h.trainId ")
				.append(" left join tb_autumnNumber_back ab on ab.backId = a.id and ab.updatestatus = 0 ")
				.append("left join updateview u on t.id = u.id ")
				.append("where 1=1 ");
		if ((map.get("name") != null)
				&& (!map.get("name").toString().equals(""))) {
			sql.append(" and t.name like '%").append(map.get("name"))
					.append("%' ");
		}
		if ((map.get("id") != null)
				&& (!map.get("id").toString().equals(""))) {
			sql.append(" and t.id = ").append(map.get("id"));
		}
		if ((map.get("note") != null)
				&& (!map.get("note").toString().equals(""))) {
			sql.append(" and t.note like '%").append(map.get("note"))
					.append("%' ");
		}
		if ((map.get("autumnNumber") != null)
				&& (!map.get("autumnNumber").toString().equals(""))) {
			sql.append(" and a.autumnNumber like '%")
					.append(map.get("autumnNumber")).append("%' ");
		}
		if ((map.get("beginTime") != null)
				&& (!map.get("beginTime").toString().equals(""))) {
			sql.append(" and t.createTime >= '").append(map.get("beginTime"))
					.append("' ");
		}
		if ((map.get("endTime") != null)
				&& (!map.get("endTime").toString().equals(""))) {
			sql.append(" and t.createTime <= '").append(map.get("endTime"))
					.append("' ");
		}
		if ((map.get("canPayingTo") != null)
				&& (!map.get("canPayingTo").toString().equals(""))) {
			sql.append(" and (t.payable-ip.allpaying) >=  ")
					.append(map.get("canPayingTo")).append(" ");
		}
		if ((map.get("canPayingForm") != null)
				&& (!map.get("canPayingForm").toString().equals(""))) {
			sql.append(" and (t.payable-ip.allpaying) <=  ")
					.append(map.get("canPayingForm")).append(" ");
		}
		if ((map.get("newOrOld") != null)
				&& (!map.get("newOrOld").toString().equals(""))) {
			sql.append(" and t.newOrOld = '").append(map.get("newOrOld"))
					.append("' ");
		}
		if ((map.get("type") != null)
				&& (!map.get("type").toString().equals(""))) {
			sql.append(" and t.type = '").append(map.get("type"))
					.append("' ");
		}
		if ((map.get("licenseTag") != null)
				&& (!map.get("licenseTag").toString().equals(""))) {
			sql.append(" and t.licenseTag = '").append(map.get("licenseTag"))
					.append("' ");
		}
		if ((map.get("createUserName") != null)
				&& (!map.get("createUserName").toString().equals(""))) {
			sql.append(" and t.createUserName like '%").append(map.get("createUserName")+"%")
					.append("' ");
		}
		sql.append(" order by h.hasAudit desc ,t.id desc ");
		return this.dao.findObjectByPageList(sql.toString(), index, side);
	}

	public Map<String, Object> findByIdcard(String idcard) {
		String sql = "select * from tb_train where idcard = ?";
		return this.dao.queryForMap(sql, new Object[] { idcard });
	}

	public boolean checkByIdcard(String idcard) {
		String sql = "select count(id) num from tb_train where idcard = ?";

		List<Map<String, Object>> list = this.dao.queryForList(sql,
				new Object[] { idcard });
		if ((list != null) && (list.size() > 0)) {
			Map<String, Object> numMap = (Map<String, Object>) list.get(0);
			Long num = (Long) numMap.get("num");
			if (num.longValue() > 0L) {
				return true;
			}
		}
		return false;
	}

	public List<Map<String, Object>> queryForParam(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(1000);
		sql.append(
				"SELECT t.payable+ip.allincome count_all,ip.allincome+ip.allpaying allip,ip.allincome,ip.allpaying allpaying,t.id,a.autumnNumber,t.name,t.idcard,t.payable pay,t.payable-ip.allpaying canpay,t.newOrOld,t.type,t.licenseTag,t.createTime,t.note,t.createUserName FROM tb_train t ")
				.append("LEFT JOIN tb_autumnNumber a ON t.id = a.trainId ")
				.append("LEFT JOIN trainincomepayingview ip ON ip.trainId = t.id where 1=1");
		
		if ((map.get("name") != null)
				&& (!map.get("name").toString().equals(""))) {
			sql.append(" and t.name like '%").append(map.get("name"))
					.append("%' ");
		}
		if ((map.get("autumnNumber") != null)
				&& (!map.get("autumnNumber").toString().equals(""))) {
			sql.append(" and a.autumnNumber like '%")
					.append(map.get("autumnNumber")).append("%' ");
		}
		if ((map.get("beginTime") != null)
				&& (!map.get("beginTime").toString().equals(""))) {
			sql.append(" and t.createTime >= '").append(map.get("beginTime"))
					.append("' ");
		}
		if ((map.get("endTime") != null)
				&& (!map.get("endTime").toString().equals(""))) {
			sql.append(" and t.createTime <= '").append(map.get("endTime"))
					.append("' ");
		}
		if ((map.get("canPayingTo") != null)
				&& (!map.get("canPayingTo").toString().equals(""))) {
			sql.append(" and (ip.allincome+t.payable-ip.allpaying) >=  ")
					.append(map.get("canPayingTo")).append(" ");
		}
		if ((map.get("canPayingForm") != null)
				&& (!map.get("canPayingForm").toString().equals(""))) {
			sql.append(" and (ip.allincome+t.payable-ip.allpaying) <=  ")
					.append(map.get("canPayingForm")).append(" ");
		}
		if ((map.get("newOrOld") != null)
				&& (!map.get("newOrOld").toString().equals(""))) {
			sql.append(" and t.newOrOld = '").append(map.get("newOrOld"))
					.append("' ");
		}
		if ((map.get("type") != null)
				&& (!map.get("type").toString().equals(""))) {
			sql.append(" and t.type = '").append(map.get("type"))
					.append("' ");
		}
		if ((map.get("licenseTag") != null)
				&& (!map.get("licenseTag").toString().equals(""))) {
			sql.append(" and t.licenseTag = '").append(map.get("licenseTag"))
					.append("' ");
		}
		if ((map.get("createUserName") != null)
				&& (!map.get("createUserName").toString().equals(""))) {
			sql.append(" and t.createUserName like '%").append(map.get("createUserName")+"%")
					.append("' ");
		}
		sql.append(" order by t.id  ");
		return this.dao.queryForList(sql.toString());
	}

	public List<Map<String, Object>> queryPaying(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(100);
		sql.append("SELECT p.* FROM tb_paying p ")
				.append("WHERE  p.status = 1 AND p.trainId IN ( ")
				.append("SELECT t.id  FROM tb_train t ")
				.append("LEFT JOIN tb_autumnNumber a ON t.id = a.trainId ")
				.append("LEFT JOIN trainincomepayingview ip ON ip.trainId = t.id where 1=1  ");
		if ((map.get("name") != null)
				&& (!map.get("name").toString().equals(""))) {
			sql.append(" and t.name like '%").append(map.get("name"))
					.append("%' ");
		}
		if ((map.get("autumnNumber") != null)
				&& (!map.get("autumnNumber").toString().equals(""))) {
			sql.append(" and a.autumnNumber like '%")
					.append(map.get("autumnNumber")).append("%' ");
		}
		if ((map.get("beginTime") != null)
				&& (!map.get("beginTime").toString().equals(""))) {
			sql.append(" and t.createTime >= '").append(map.get("beginTime"))
					.append("' ");
		}
		if ((map.get("endTime") != null)
				&& (!map.get("endTime").toString().equals(""))) {
			sql.append(" and t.createTime <= '").append(map.get("endTime"))
					.append("' ");
		}
		if ((map.get("canPayingTo") != null)
				&& (!map.get("canPayingTo").toString().equals(""))) {
			sql.append(" and (ip.allincome+t.payable-ip.allpaying) >=  ")
					.append(map.get("canPayingTo")).append(" ");
		}
		if ((map.get("canPayingForm") != null)
				&& (!map.get("canPayingForm").toString().equals(""))) {
			sql.append(" and (ip.allincome+t.payable-ip.allpaying) <=  ")
					.append(map.get("canPayingForm")).append(" ");
		}
		if ((map.get("newOrOld") != null)
				&& (!map.get("newOrOld").toString().equals(""))) {
			sql.append(" and t.newOrOld = '").append(map.get("newOrOld"))
					.append("' ");
		}
		if ((map.get("type") != null)
				&& (!map.get("type").toString().equals(""))) {
			sql.append(" and t.type = '").append(map.get("type"))
					.append("' ");
		}
		if ((map.get("licenseTag") != null)
				&& (!map.get("licenseTag").toString().equals(""))) {
			sql.append(" and t.licenseTag = '").append(map.get("licenseTag"))
					.append("' ");
		}
		if ((map.get("createUserName") != null)
				&& (!map.get("createUserName").toString().equals(""))) {
			sql.append(" and t.createUserName like '%").append(map.get("createUserName")+"%")
					.append("' ");
		}
		sql.append(")  ");
		sql.append(" order by p.trainId desc ");
		return this.dao.queryForList(sql.toString());
	}

	public Map<String, Object> statistics(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(1000);
		sql.append(
				"SELECT sum(t.payable) payable,sum(v.allpaying) paying,SUM(t.payable+v.allincome) allpay ,sum(v.allincome) allincome,SUM(v.allpaying+v.allincome) allpaying,sum(p.allpayout) allpayout FROM tb_train t  ")
				.append("LEFT JOIN tb_autumnNumber a ON t.id = a.trainId ")
				.append("LEFT JOIN trainpayoutview p ON t.id = p.trainId ")
				.append("LEFT JOIN trainincomepayingview v ON t.id = v.trainId  where 1=1 ");
		if ((map.get("name") != null)
				&& (!map.get("name").toString().equals(""))) {
			sql.append(" and t.name like '%").append(map.get("name"))
					.append("%' ");
		}
		if ((map.get("id") != null)
				&& (!map.get("id").toString().equals(""))) {
			sql.append(" and t.id = ").append(map.get("id"));
		}
		if ((map.get("note") != null)
				&& (!map.get("note").toString().equals(""))) {
			sql.append(" and t.note like '%").append(map.get("note"))
					.append("%' ");
		}
		if ((map.get("autumnNumber") != null)
				&& (!map.get("autumnNumber").toString().equals(""))) {
			sql.append(" and a.autumnNumber like '%")
					.append(map.get("autumnNumber")).append("%' ");
		}
		if ((map.get("beginTime") != null)
				&& (!map.get("beginTime").toString().equals(""))) {
			sql.append(" and t.createTime >= '").append(map.get("beginTime"))
					.append("' ");
		}
		if ((map.get("endTime") != null)
				&& (!map.get("endTime").toString().equals(""))) {
			sql.append(" and t.createTime <= '").append(map.get("endTime"))
					.append("' ");
		}
		if ((map.get("canPayingTo") != null)
				&& (!map.get("canPayingTo").toString().equals(""))) {
			sql.append(" and (v.allincome+t.payable-v.allpaying) >=  ")
					.append(map.get("canPayingTo")).append(" ");
		}
		if ((map.get("canPayingForm") != null)
				&& (!map.get("canPayingForm").toString().equals(""))) {
			sql.append(" and (v.allincome+t.payable-v.allpaying) <=  ")
					.append(map.get("canPayingForm")).append(" ");
		}
		if ((map.get("newOrOld") != null)
				&& (!map.get("newOrOld").toString().equals(""))) {
			sql.append(" and t.newOrOld = '").append(map.get("newOrOld"))
					.append("' ");
		}
		if ((map.get("type") != null)
				&& (!map.get("type").toString().equals(""))) {
			sql.append(" and t.type = '").append(map.get("type"))
					.append("' ");
		}
		if ((map.get("licenseTag") != null)
				&& (!map.get("licenseTag").toString().equals(""))) {
			sql.append(" and t.licenseTag = '").append(map.get("licenseTag"))
					.append("' ");
		}
		if ((map.get("createUserName") != null)
				&& (!map.get("createUserName").toString().equals(""))) {
			sql.append(" and t.createUserName like '%").append(map.get("createUserName")+"%")
					.append("' ");
		}
		List<Map<String, Object>> list = this.dao.queryForList(sql.toString());
		if ((list != null) && (list.size() > 0)) {
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

	public List<Map<String, Object>> dayStatistics(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(5, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date);
		String endDate = sdf.format(cal.getTime());
		StringBuffer sql = new StringBuffer(100);
		sql.append(
				"SELECT t.name,t.idcard,pay.allpaying,payout.allpayout,income.allincome  FROM tb_train t ")
				.append("LEFT JOIN (")
				.append("SELECT p.trainId,SUM(p.paying) allpaying FROM tb_paying p WHERE p.createTime > '"
						+ startDate
						+ "'  AND p.createTime <'"
						+ endDate
						+ "'  GROUP BY p.status = 1 ")
				.append(") pay ON t.id = pay.trainId ")
				.append("LEFT JOIN (")
				.append("SELECT p.trainId,SUM(p.payout) allpayout FROM tb_payout p WHERE p.createTime > '"
						+ startDate
						+ "'  AND p.createTime <'"
						+ endDate
						+ "'  GROUP BY p.status = 1 ")
				.append(") payout ON t.id = payout.trainId ")
				.append("LEFT JOIN (")
				.append("SELECT i.trainId,SUM(i.income) allincome FROM tb_income i WHERE i.createTime > '"
						+ startDate
						+ "'  AND i.createTime <'"
						+ endDate
						+ "'  GROUP BY i.status = 1 ")
				.append(") income ON income.trainId = t.id  ")
				.append("WHERE pay.allpaying IS NOT NULL OR payout.allpayout IS NOT NULL AND income.allincome IS NOT NULL");
		List<Map<String, Object>> list = this.dao.queryForList(sql.toString());
		return list;
	}

	public boolean hasPayoutCommonAudti() {
		String sql = "select count(*) num from tb_payoutcommon where status = 0 ";
		List<Map<String, Object>> list = this.dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			Long num = (Long)list.get(0).get("num");
			if(num>0){
				return true;
			}
		}
		sql = "select count(*) num from tb_incomecommon where status = 0 ";
		list = this.dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			Long num = (Long)list.get(0).get("num");
			if(num>0){
				return true;
			}
		}
		return false;
	}

	public Map<String, Object> findBackByBTrainId(long trainId) {
		String sql = "select t.*,n.autumnNumber,b.id backId,b.updateStatus,b.createUser bcreateUser from tb_train t left join tb_autumnNumber n on t.id = n.trainId left join tb_train_back b on t.id = b.trainId and b.updateStatus = 0 where t.id = ? ";
		List<Map<String,Object>> list = dao.queryForList(sql,trainId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}