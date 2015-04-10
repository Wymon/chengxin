package xx.tream.chengxin.ms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
@Service
public class ReportParamServiceImpl implements ReportParamService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private DaoUtil dao;
	/**
	 * 取得此段时间内的收入其它项目名称,以数组形式返回
	 * 参数可以为空表示返回全部
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryIncomeOtherItem(Date startDate ,Date endDate){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT othertype otherType FROM tb_income WHERE TYPE = '其它' and status = 1  ");
		if(startDate!=null){
			sql.append(" and auditTime >= '").append(sdf.format(startDate)).append("'");
		}
		if(endDate!=null){
			sql.append(" and auditTime <= '").append(sdf.format(endDate)).append("'");
		}
		List<Map<String,Object>> list = dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			String[] s = new String[list.size()];
			for(int i = 0 ; i < list.size();i++){
				s[i] = (String)list.get(i).get("otherType");
			}
			return s;
		}
		return null;
	}

	/**
	 * 取得其它支出项目
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryPayoutOtherItem(Date startDate ,Date endDate){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT othertype otherType FROM tb_payout WHERE TYPE = '其它' and status = 1  ");
		if(startDate!=null){
			sql.append(" and auditTime >= '").append(sdf.format(startDate)).append("'");
		}
		if(endDate!=null){
			sql.append(" and auditTime <= '").append(sdf.format(endDate)).append("'");
		}
		List<Map<String,Object>> list = dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			String[] s = new String[list.size()];
			for(int i = 0 ; i < list.size();i++){
				s[i] = (String)list.get(i).get("otherType");
			}
			return s;
		}
		return null;
	}
	/**
	 * 取得公共支出项目
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] queryPayoutCommonOtherItem(Date startDate ,Date endDate){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT othertype otherType FROM tb_payoutcommon WHERE TYPE = '其它' and status = 1  ");
		if(startDate!=null){
			sql.append(" and auditTime >= '").append(sdf.format(startDate)).append("'");
		}
		if(endDate!=null){
			sql.append(" and auditTime <= '").append(sdf.format(endDate)).append("'");
		}
		List<Map<String,Object>> list = dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			String[] s = new String[list.size()];
			for(int i = 0 ; i < list.size();i++){
				s[i] = (String)list.get(i).get("otherType");
			}
			return s;
		}
		return null;
	}

	@Override
	public String[] queryIncomeCommonOtherItem(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT othertype otherType FROM tb_incomecommon WHERE TYPE = '其它' and status = 1  ");
		if(startDate!=null){
			sql.append(" and auditTime >= '").append(sdf.format(startDate)).append("'");
		}
		if(endDate!=null){
			sql.append(" and auditTime <= '").append(sdf.format(endDate)).append("'");
		}
		List<Map<String,Object>> list = dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			String[] s = new String[list.size()];
			for(int i = 0 ; i < list.size();i++){
				s[i] = (String)list.get(i).get("otherType");
			}
			return s;
		}
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, Object>> queryIncomeAndPayout(
			Map<String, Object> queryMap, String[] incomeItems,
			String[] incomeOhterItems,String[] incomeCommonItems,
			String[] incomeCommonOtherItems, String[] payoutItems,
			String[] payoutOtherItems, String[] payoutCommonItems,
			String[] payoutCommonOtherItems) {
		//公共查询sql
		String queryType = (String)queryMap.get("queryType");
		String selectValue = "";
		if(queryType!=null&&queryType.equals("year")){
			selectValue = "DATE_FORMAT(a.audittime,'%Y')  ";
		}else if(queryType!=null&&queryType.equals("month")){
			selectValue = "DATE_FORMAT(a.audittime,'%Y-%m')  ";
		}else{
			selectValue = "a.audittime";
		}
		StringBuffer whereSql = new StringBuffer(100);
		whereSql.append(" where 1=1 " );
		if ((queryMap.get("beginTime") != null)
				&& (!queryMap.get("beginTime").toString().equals(""))) {
			whereSql.append(" and a.audittime >= '").append(queryMap.get("beginTime"))
					.append("' ");
		}
		if ((queryMap.get("endTime") != null)
				&& (!queryMap.get("endTime").toString().equals(""))) {
			whereSql.append(" and a.audittime <= '").append(queryMap.get("endTime"))
					.append("' ");
		}
		whereSql.append("GROUP BY "+selectValue+" order by a.audittime ");
		StringBuffer sql = new StringBuffer(100);
		sql.append("SELECT "+selectValue+" audittime ,SUM(b.paying) paying ");
		sql.append(" FROM AUDITTIMEVIEW a ")
		.append(" LEFT JOIN AUDITPAYINGVIEW b ON a.audittime = b.audittime ");
		sql.append(whereSql);
		List<Map<String,Object>> payingList = dao.queryForList(sql.toString());
		
		//收入
		sql = new StringBuffer(100);
		sql.append("SELECT "+selectValue+" audittime ");
		if(incomeItems!=null&&incomeItems.length>0){
			for(int i = 0 ; i < incomeItems.length-1;i++){
				sql.append(",SUM(CASE WHEN c.type ='"+incomeItems[i]+"' THEN c.income END ) i"+i);
			}
		}
		if(incomeOhterItems!=null&&incomeOhterItems.length>0){
			for(int i = 0 ; i < incomeOhterItems.length;i++){
				sql.append(",SUM(CASE WHEN c.type = '其它' and c.othertype ='"+incomeOhterItems[i]+"' THEN c.income END ) io"+i);
			}
		}
		sql.append(" FROM AUDITTIMEVIEW a ")
		.append(" LEFT JOIN AUDITINCOMEVIEW c ON a.audittime = c.audittime ");
		sql.append(whereSql);
		List<Map<String,Object>> incomeList = dao.queryForList(sql.toString());
		Map<String,Map<String,Object>> incomeMap = this.converAuditTimeKey(incomeList);
		
		//公共收入
		sql = new StringBuffer(100);
		sql.append("SELECT "+selectValue+" audittime ");
		if(incomeCommonItems!=null&&incomeCommonItems.length>0){
			for(int i = 0 ; i < incomeCommonItems.length-1;i++){
				sql.append(",SUM(CASE WHEN c.type ='"+incomeCommonItems[i]+"' THEN c.income END ) ic"+i);
			}
		}
		if(incomeCommonOtherItems!=null&&incomeCommonOtherItems.length>0){
			for(int i = 0 ; i < incomeCommonOtherItems.length;i++){
				sql.append(",SUM(CASE WHEN c.type = '其它' and c.othertype ='"+incomeCommonOtherItems[i]+"' THEN c.income END ) ico"+i);
			}
		}
		sql.append(" FROM AUDITTIMEVIEW a ")
		.append(" LEFT JOIN AUDITINCOMECOMMONVIEW c ON a.audittime = c.audittime ");
		sql.append(whereSql);
		List<Map<String,Object>> incomeCommonList = dao.queryForList(sql.toString());
		Map<String,Map<String,Object>> incomeCommonMap = this.converAuditTimeKey(incomeCommonList);
		//支出
		sql = new StringBuffer(100);
		sql.append("SELECT "+selectValue+" audittime ");
		if(payoutItems!=null&&payoutItems.length>0){
			for(int i = 0 ; i < payoutItems.length-1;i++){
				sql.append(",SUM(CASE WHEN d.type ='"+payoutItems[i]+"' THEN d.payout END ) p"+i);
			}
		}
		if(payoutOtherItems!=null&&payoutOtherItems.length>0){
			for(int i = 0 ; i < payoutOtherItems.length;i++){
				sql.append(",SUM(CASE WHEN d.type = '其它' and d.othertype ='"+payoutOtherItems[i]+"' THEN d.payout END ) po"+i);
			}
		}
		sql.append(" FROM AUDITTIMEVIEW a ")
		.append(" LEFT JOIN AUDITPAYOUTVIEW d ON a.audittime = d.audittime ")
		.append(whereSql);
		List<Map<String,Object>> payoutList = dao.queryForList(sql.toString());
		Map<String,Map<String,Object>> payoutMap = this.converAuditTimeKey(payoutList);
		//公共支出
		sql = new StringBuffer(100);
		sql.append("SELECT "+selectValue+" audittime ");
		if(payoutCommonItems!=null&&payoutCommonItems.length>0){
			for(int i = 0 ; i < payoutCommonItems.length-1;i++){
				sql.append(",SUM(CASE WHEN e.type ='"+payoutCommonItems[i]+"' THEN e.payout END ) pc"+i);
			}
		}
		if(payoutCommonOtherItems!=null&&payoutCommonOtherItems.length>0){
			for(int i = 0 ; i < payoutCommonOtherItems.length;i++){
				sql.append(",SUM(CASE WHEN e.type = '其它' and e.othertype ='"+payoutCommonOtherItems[i]+"' THEN e.payout END ) pco"+i);
			}
		}
		sql.append(" FROM AUDITTIMEVIEW a ")
		.append(" LEFT JOIN AUDITPAYOUTCOMMONVIEW e ON a.audittime = e.audittime ")
		.append(whereSql);
		List<Map<String,Object>> payoutCommonList = dao.queryForList(sql.toString());
		Map<String,Map<String,Object>> payoutCommonMap = this.converAuditTimeKey(payoutCommonList);
		Set<String> ikeys = null;
		Set<String> ickeys = null;
		Set<String> pkeys = null;
		Set<String> pckeys = null;
		for(Map<String,Object> m : payingList){
			Map<String,Object> in = incomeMap.get(m.get("audittime"));
			//收入
			if(ikeys==null){
				ikeys = in.keySet();
				ikeys.remove("audittime");
			}
			for(Iterator it = ikeys.iterator(); it.hasNext(); ){             
				String key = (String)it.next();
	            m.put(key, in.get(key)) ;
	        } 
			//公共收入
			Map<String,Object> ic = incomeCommonMap.get(m.get("audittime"));
			if(ickeys==null){
				ickeys = ic.keySet();
				ickeys.remove("audittime");
			}
			for(Iterator it = ickeys.iterator(); it.hasNext(); ){   
				String key = (String)it.next();
	            m.put(key, ic.get(key)) ; 
	        } 
			//支出
			Map<String,Object> p = payoutMap.get(m.get("audittime"));
			if(pkeys==null){
				pkeys = p.keySet();
				pkeys.remove("audittime");
			}
			for(Iterator it = pkeys.iterator(); it.hasNext(); ){   
				String key = (String)it.next();
	            m.put(key, p.get(key)) ; 
	        } 
			//公共支出
			Map<String,Object> pc = payoutCommonMap.get(m.get("audittime"));
			if(pckeys==null){
				pckeys = pc.keySet();
				pckeys.remove("audittime");
			}
			for(Iterator it = pckeys.iterator(); it.hasNext(); ){   
				String key = (String)it.next();
	            m.put(key, pc.get(key)) ; 
	        }
			
		}
		return payingList;
	}
	
	private Map<String,Map<String,Object>> converAuditTimeKey(List<Map<String,Object>> list){
		Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
		if(list!=null&&list.size()>0){
			for(Map<String,Object> m : list){
				String key = (String)m.get("audittime");
				map.put(key, m);
			}
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryPayable(Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM(payable) payable FROM tb_train t  where 1=1 ");
		if ((queryMap.get("beginTime") != null)
				&& (!queryMap.get("beginTime").toString().equals(""))) {
			sql.append(" and t.createtime >= '").append(queryMap.get("beginTime"))
					.append("' ");
		}
		if ((queryMap.get("endTime") != null)
				&& (!queryMap.get("endTime").toString().equals(""))) {
			sql.append(" and t.createtime <= '").append(queryMap.get("endTime"))
					.append("' ");
		}
		List<Map<String,Object>> list = dao.queryForList(sql.toString());
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return new HashMap<String,Object>();
	}

	

	
}
