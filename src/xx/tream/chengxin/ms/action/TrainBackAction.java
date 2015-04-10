package xx.tream.chengxin.ms.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xx.tream.basepaltform.model.FormMap;
import xx.tream.basepaltform.model.PageList;
import xx.tream.basepaltform.util.BeanUtil;
import xx.tream.chengxin.ms.model.AutumnNumber;
import xx.tream.chengxin.ms.model.AutumnNumberBack;
import xx.tream.chengxin.ms.model.Income;
import xx.tream.chengxin.ms.model.IncomeBack;
import xx.tream.chengxin.ms.model.IncomeCommon;
import xx.tream.chengxin.ms.model.IncomeCommonBack;
import xx.tream.chengxin.ms.model.Paying;
import xx.tream.chengxin.ms.model.PayingBack;
import xx.tream.chengxin.ms.model.Payout;
import xx.tream.chengxin.ms.model.PayoutBack;
import xx.tream.chengxin.ms.model.PayoutCommon;
import xx.tream.chengxin.ms.model.PayoutCommonBack;
import xx.tream.chengxin.ms.model.Train;
import xx.tream.chengxin.ms.model.TrainBack;
import xx.tream.chengxin.ms.service.AutumnNumberBackService;
import xx.tream.chengxin.ms.service.AutumnNumberService;
import xx.tream.chengxin.ms.service.IncomeBackService;
import xx.tream.chengxin.ms.service.IncomeCommonBackService;
import xx.tream.chengxin.ms.service.IncomeCommonService;
import xx.tream.chengxin.ms.service.IncomeService;
import xx.tream.chengxin.ms.service.ParamService;
import xx.tream.chengxin.ms.service.PayingBackService;
import xx.tream.chengxin.ms.service.PayingService;
import xx.tream.chengxin.ms.service.PayoutBackService;
import xx.tream.chengxin.ms.service.PayoutCommonBackService;
import xx.tream.chengxin.ms.service.PayoutCommonService;
import xx.tream.chengxin.ms.service.PayoutService;
import xx.tream.chengxin.ms.service.TrainBackService;
import xx.tream.chengxin.ms.service.TrainService;
import xx.tream.chengxin.ms.util.DateUtil;
import xx.tream.chengxin.ms.util.ParamUtil;

@Controller
@RequestMapping({ "/trainBack" })
public class TrainBackAction {
	@Autowired
	private TrainService trainService;
	@Autowired
	private AutumnNumberService autumnNumberService;
	@Autowired
	private PayingService payingService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private PayoutService payoutService;
	@Autowired
	private TrainBackService trainBackService;
	@Autowired
	private PayingBackService payingBackService;
	@Autowired
	private IncomeBackService incomeBackService;
	@Autowired
	private PayoutBackService payoutBackService;
	@Autowired
	private PayoutCommonService payoutCommonService;
	@Autowired
	private PayoutCommonBackService payoutCommonBackService;
	@Autowired
	private IncomeCommonBackService incomeCommonBackService;
	@Autowired
	private IncomeCommonService incomeCommonService;
	@Autowired
	private ParamService paramService;
	private String[] trianBack = { "createTime","name", "idcard", "payable","note",
			"newOrOld", "type", "licenseTag" };

	private String[] payingBack = { "paying" };

	private String[] incomeBack = { "income", "type","otherType","note" };

	private String[] payoutBack = { "payout", "type","otherType","note" };
	
	private String[] payoutCommonBack = { "payout", "type","otherType", "note" };
	
	private String[] incomeCommonBack = { "income", "type","otherType", "note" };
	@Autowired
	private AutumnNumberBackService autumnNumberBackService;
	/**
	 * 保存立秋编号到back
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/updateAutumnNumber" })
	public String updateAutumnNumber(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> backMap = formMap.getFormMap();
		Long backId = Long.parseLong(backMap.get("backId")+"");
		Map<String,Object> map = this.autumnNumberBackService.findBackByBackId(backId);
		if(map==null){
			AutumnNumberBack back = new AutumnNumberBack();
			BeanUtil.copyBeanProperties(back, backMap);
			back.setCreateTime(new Date());
			back.setCreateUserName(ParamUtil.getRealName(request));
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setUpdateStatus("0");
			this.autumnNumberBackService.save(back);
			modelMap.put("msg","succeed");
		}else{
			modelMap.put("msg","已存在修改记录,请进行审核操作!");
		}
		return "train/updateAutumnNumber";
	}
	/**
	 * 进入审核立秋编号页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping({ "/toUpdateAudtiAutumnNumber" })
	public String toUpdateAudtiAutumnNumber(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize,Long id ) {
		Map<String,Object> backMap = this.autumnNumberBackService.find(id);
		if(backMap!=null&&backMap.get("updateStatus").equals("0")){
			Long backId = Long.parseLong(backMap.get("backId")+"");
			Map<String,Object> oldMap = this.autumnNumberService.find(backId);
			modelMap.put("backMap", backMap);
			modelMap.put("oldMap", oldMap);
		}else{
			modelMap.put("msg", "状态已变更了,不能进行审核操作");
		}
		return "train/updateAuditAutumnNumber";
	}
	/**
	 * 更新立秋编号(审核时)
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/updateAudtiAutumnNumber" })
	public String updateAudtiAutumnNumber(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize,Long id ) {
		Map<String,Object> auditMap = formMap.getFormMap();
		Map<String,Object> backMap = this.autumnNumberBackService.find(id);
		if(backMap!=null&&backMap.get("updateStatus").equals("0")){
			Long backId = Long.parseLong(backMap.get("backId")+"");
			Map<String,Object> oldMap = this.autumnNumberService.find(backId);
			AutumnNumberBack back = new AutumnNumberBack();
			BeanUtil.copyBeanProperties(back, backMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setAuditNote((String)auditMap.get("auditNote"));
			back.setUpdateTime(new Date());
			back.setUpdateUserName(ParamUtil.getRealName(request));
			if(auditMap.get("updateStatus").equals("1")){//审核通过
				back.setUpdateStatus("1");
				AutumnNumber a = new AutumnNumber();
				BeanUtil.copyBeanProperties(a, oldMap);
				a.setAutumnNumber((String)backMap.get("autumnNumber"));
				this.autumnNumberService.update(a);
			}else{//不通过
				back.setUpdateStatus("2");
			}
			this.autumnNumberBackService.update(back);
			modelMap.put("msg","succeed");
		}else{
			modelMap.put("msg", "状态已变更了,不能进行审核操作");
		}
		return "train/updateAuditAutumnNumber";
	}
	@RequestMapping({ "/toUpdate" })
	public String toUpdate(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		modelMap.put("id", id);
		Map<String, Object> trainMap = this.trainService.findBackByBTrainId(id);
		modelMap.put("trainMap", trainMap);

		List<Map<String, Object>> payingMapList = this.payingBackService.queryBackByTrainId(id);
		modelMap.put("payingMapList", payingMapList);

		List<Map<String, Object>> incomeMapList = this.incomeBackService.queryBackByTrainId(id);
		modelMap.put("incomeMapList", incomeMapList);

		List<Map<String, Object>> payoutMapList = this.payoutBackService.queryBackByTrainId(id);
		modelMap.put("payoutMapList", payoutMapList);
		
		
		return "train/update";
	}

	@RequestMapping({ "/toUpdateTrain" })
	public String toUpdateTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String,Object> oldTrainInfoMap = this.paramService.findByCode("canAddOldTrain");
		modelMap.put("oldTrainInfoMap", oldTrainInfoMap);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("trainId", id);
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("updateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.trainBackService
				.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "对不起，你已修改过此记录，请等待其他人员修改确认！");
		} else {
			Map<String, Object> trainMap = this.trainService.find(id
					.longValue());
			modelMap.put("trainMap", trainMap);
		}
		return "train/updateTrain";
	}

	@RequestMapping({ "/updateTrain" })
	public String updateTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> trainMap = formMap.getFormMap();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("trainId", trainMap.get("trainId"));
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("notCreateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.trainBackService
				.queryByParam(queryMap);
		
		if ((list != null) && (list.size() > 0)) {//
			modelMap.put("msg", "表示存在别人修改了，不能再进行修改，请进行审核操作");
		}else{
			TrainBack back = new TrainBack();
			if(trainMap.get("createTime")!=null&&!trainMap.get("createTime").equals("")){
				back.setCreateTime(DateUtil.stringToDate(trainMap.get("createTime").toString(), "yyyy-MM-dd HH:mm:ss"));
				trainMap.remove("createTime");
			}
			BeanUtil.copyBeanProperties(back, trainMap);
			back.setUpdateStatus("0");
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setCreateUserName(ParamUtil.getRealName(request));
			//back.setCreateTime(new Date());
			this.trainBackService.save(back);
			modelMap.put("msg", "succeed");
		}
		return "train/updateTrain";
	}
	/**
	 * 进入修改审核页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/toUpdateAuditTrain" })
	public String toUpdateAuditTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		//修改前信息
		Map<String,Object> trainMap = this.trainService.find(id);
		modelMap.put("trainMap", trainMap);
		//修改后信息
		Map<String,Object> trainBackMap = this.trainBackService.findByTrainId(id);
		if(trainBackMap==null){
			modelMap.put("msg", "其它负责人修改记录状态已变更不能进行审核操作.");
		}else{
			modelMap.put("trainBackMap", trainBackMap);
		}
		return "train/updateAuditTrain";
	}
	
	@RequestMapping({ "/updateAuditTrain" })
	public String updateAuditTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String,Object> fm = formMap.getFormMap();
		//修改前信息
		Map<String,Object> trainMap = this.trainService.find(id);
		modelMap.put("trainMap", trainMap);
		//修改后信息
		Map<String,Object> trainBackMap = this.trainBackService.findByTrainId(id);
		if(trainBackMap==null){
			modelMap.put("msg", "其它负责人修改记录状态已变更不能进行审核操作.");
		}else{
			if(fm.get("updateStatus")!=null&&fm.get("updateStatus").equals("1")){//审核通过
				//把back中的信息更新到
				for(int i = 0 ; i < trianBack.length;i++){
					trainMap.put(trianBack[i], trainBackMap.get(trianBack[i]));
				}
				Train train = new Train();
				
				BeanUtil.copyBeanProperties(train, trainMap);
				this.trainService.update(train);
			}
			
			//更新修改记录
			TrainBack back = new TrainBack();
			BeanUtil.copyBeanProperties(back, trainBackMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setUpdateUserName(ParamUtil.getRealName(request));
			back.setUpdateTime(new Date());
			back.setUpdateStatus((String)fm.get("updateStatus"));
			back.setAuditNote((String)fm.get("auditNote"));
			this.trainBackService.update(back);
			modelMap.put("msg", "succeed");
		}
		return "train/updateAuditTrain";
	}

	@RequestMapping({ "/toUpdatePaying" })
	public String toUpdatePaying(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("payingId", id);
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("updateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.payingBackService
				.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "对不起，你已修改过此记录，请等待其他人员修改确认！");
		} else {
			Map<String, Object> payingMap = this.payingService.find(id
					.longValue());
			//if (!payingMap.get("status").equals("0"))
			//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
			//else {
				modelMap.put("payingMap", payingMap);
			//}
		}
		return "train/updatePaying";
	}

	@RequestMapping({ "/updatePaying" })
	public String updatePaying(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> payingMap = formMap.getFormMap();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("payingId", payingMap.get("payingId"));
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("notCreateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.payingBackService.queryByParam(queryMap);
		
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "其他负责人已进行修改了，请进行审核操作！");
		} else {
			PayingBack back = new PayingBack();
			BeanUtil.copyBeanProperties(back, payingMap);
			back.setUpdateStatus("0");
			back.setCreateTime(new Date());
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setCreateUserName(ParamUtil.getRealName(request));
			this.payingBackService.save(back);
			modelMap.put("msg", "succeed");
		}
		return "train/updatePaying";
	}
	/**
	 * 进入审核页面缴费
	 */
	@RequestMapping({ "/toUpdateAuditPaying" })
	public String toUpdateAuditPaying(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		//查询修改记录
		Map<String,Object> payingBackMap = this.payingBackService.findByPayingId(id);
		if(payingBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditPaying";
		}else{
			modelMap.put("payingBackMap", payingBackMap);
		}
		//查询修改前记录
		Map<String,Object> payingMap = this.payingService.find(id);
		//if (!payingMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			modelMap.put("payingMap", payingMap);
		//}
		return "train/updateAuditPaying";
	}
	/**
	 * 保存审核结果
	 */
	@RequestMapping({ "/updateAuditPaying" })
	public String updateAuditPaying(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		//查询修改记录
		Map<String,Object> payingBackMap = this.payingBackService.findByPayingId(id);
		if(payingBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditPaying";
		}
		//查询修改前记录
		Map<String,Object> payingMap = this.payingService.find(id);
		//if (!payingMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			//更新原记录
			Map<String,Object> fm = formMap.getFormMap();
			for(int i = 0 ; i < payingBack.length;i++){
				payingMap.put(payingBack[i], payingBackMap.get(payingBack[i]));
			}
			if(fm.get("updateStatus")!=null&&fm.get("updateStatus").equals("1")){
				Paying paying = new Paying();
				BeanUtil.copyBeanProperties(paying, payingMap);
				this.payingService.update(paying);
			}
			
			//修改back
			PayingBack back = new PayingBack();
			BeanUtil.copyBeanProperties(back, payingBackMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setUpdateUserName(ParamUtil.getRealName(request));
			back.setUpdateTime(new Date());
			back.setUpdateStatus((String)fm.get("updateStatus"));
			back.setAuditNote((String)fm.get("auditNote"));
			this.payingBackService.update(back);
			modelMap.put("msg", "succeed");
		//}
		return "train/updateAuditPaying";
	}
	
	@RequestMapping({ "/toUpdateIncome" })
	public String toUpdateIncome(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		List<Map<String,Object>> incomeItems = ParamUtil.getIncomeItemList();
		modelMap.put("incomeItems", incomeItems);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("incomeId", id);
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("createUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.incomeBackService.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "对不起，你已修改过此记录，请等待其他人员修改确认！");
		} else {
			Map<String, Object> incomeMap = this.incomeService.find(id);
			//if (!incomeMap.get("status").equals("0"))
			//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
			//else {
				modelMap.put("incomeMap", incomeMap);
			//}
		}
		return "train/updateIncome";
	}

	@RequestMapping({ "/updateIncome" })
	public String updateIncome(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String, Object> incomeMap = formMap.getFormMap();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("incomeId", incomeMap.get("incomeId"));
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("notCreateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.incomeBackService.queryByParam(queryMap);
		
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "其他负责人已进行修改了，请进行审核操作！");
		} else {
			//Map<String,Object> income = this.incomeService.find(Long.parseLong((String)incomeMap.get("incomeId")));
			//if(income.get("status").equals("0")){
				IncomeBack back = new IncomeBack();
				BeanUtil.copyBeanProperties(back, incomeMap);
				back.setUpdateStatus("0");
				back.setCreateTime(new Date());
				back.setCreateUser(ParamUtil.getUserId(request));
				back.setCreateUserName(ParamUtil.getRealName(request));
				this.incomeBackService.save(back);
				modelMap.put("msg", "succeed");
			//}else{
			//	modelMap.put("msg", "状态已变更，不能进行修改操作！");
			//}
		}
		return "train/updateIncome";
	}
	/**
	 * 进入审核收入页面
	 */
	@RequestMapping({ "/toUpdateAuditIncome" })
	public String toUpdateAuditIncome(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> incomeBackMap = this.incomeBackService.findBackByIncomeId(id);
		if(incomeBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditIncome";
		}else{
			modelMap.put("incomeBackMap", incomeBackMap);
		}
		//查询修改前记录
		Map<String,Object> incomeMap = this.incomeService.find(id);
		//if (!incomeMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			modelMap.put("incomeMap", incomeMap);
		//}
		return "train/updateAuditIncome";
	}
	
	@RequestMapping({ "/updateAuditIncome" })
	public String updateAuditIncome(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> incomeBackMap = this.incomeBackService.findBackByIncomeId(id);
		if(incomeBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditIncome";
		}
		//查询修改前记录
		Map<String,Object> incomeMap = this.incomeService.find(id);
		//if (!incomeMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			//更新原记录
			Map<String,Object> fm = formMap.getFormMap();
			for(int i = 0 ; i < incomeBack.length;i++){
				incomeMap.put(incomeBack[i], incomeBackMap.get(incomeBack[i]));
			}
			if(fm.get("updateStatus")!=null&&fm.get("updateStatus").equals("1")){
				Income income = new Income();
				BeanUtil.copyBeanProperties(income, incomeMap);
				this.incomeService.update(income);
			}
			//修改back
			IncomeBack back = new IncomeBack();
			BeanUtil.copyBeanProperties(back, incomeBackMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setUpdateUserName(ParamUtil.getRealName(request));
			back.setUpdateTime(new Date());
			back.setUpdateStatus((String)fm.get("updateStatus"));
			back.setAuditNote((String)fm.get("auditNote"));
			this.incomeBackService.update(back);
			modelMap.put("msg", "succeed");
		//}
		return "train/updateAuditIncome";
	}
	@RequestMapping({ "/toUpdatePayout" })
	public String toUpdatePayout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("payoutId", id);
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("createUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.payoutBackService.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "对不起，你已修改过此记录，请等待其他人员修改确认！");
		} else {
			Map<String, Object> payoutMap = this.payoutService.find(id
					.longValue());
			//if (!payoutMap.get("status").equals("0"))
			//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
			//else {
				modelMap.put("payoutMap", payoutMap);
				List<Map<String,Object>> payoutItemList = ParamUtil.getPayoutItemList();
				modelMap.put("payoutItemList", payoutItemList);
			//}
		}
		return "train/updatePayout";
	}

	@RequestMapping({ "/updatePayout" })
	public String updatePayout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> payoutMap = formMap.getFormMap();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("payoutId", payoutMap.get("payoutId"));
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("notCreateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.payoutBackService.queryByParam(queryMap);
		
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "其他负责人已进行修改了，请进行审核操作！");
		} else {
			PayoutBack back = new PayoutBack();
			BeanUtil.copyBeanProperties(back, payoutMap);
			back.setUpdateStatus("0");
			back.setCreateTime(new Date());
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setCreateUserName(ParamUtil.getRealName(request));
			this.payoutBackService.save(back);
			modelMap.put("msg", "succeed");
		}
		return "train/updatePayout";
	}
	/**
	 * 进入审核支出页面
	 */
	@RequestMapping({ "/toUpdateAuditPayout" })
	public String toUpdateAuditPayout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> payoutBackMap = this.payoutBackService.findBackByPayoutId(id);
		if(payoutBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditPayout";
		}else{
			modelMap.put("payoutBackMap", payoutBackMap);
		}
		//查询修改前记录
		Map<String,Object> payoutMap = this.payoutService.find(id);
		//if (!payoutMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			modelMap.put("payoutMap", payoutMap);
		//}
		return "train/updateAuditPayout";
	}
	
	@RequestMapping({ "/updateAuditPayout" })
	public String updateAuditPayout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> payoutBackMap = this.payoutBackService.findBackByPayoutId(id);
		if(payoutBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditPayout";
		}
		//查询修改前记录
		Map<String,Object> payoutMap = this.payoutService.find(id);
		//if (!payoutMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			//更新原记录
			Map<String,Object> fm = formMap.getFormMap();
			for(int i = 0 ; i < payoutBack.length;i++){
				payoutMap.put(payoutBack[i], payoutBackMap.get(payoutBack[i]));
			}
			if(fm.get("updateStatus")!=null&&fm.get("updateStatus").equals("1")){
				Payout payout = new Payout();
				BeanUtil.copyBeanProperties(payout, payoutMap);
				this.payoutService.update(payout);
			}
			//修改back
			PayoutBack back = new PayoutBack();
			BeanUtil.copyBeanProperties(back, payoutBackMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setUpdateUserName(ParamUtil.getRealName(request));
			back.setUpdateTime(new Date());
			back.setUpdateStatus((String)fm.get("updateStatus"));
			back.setAuditNote((String)fm.get("auditNote"));
			this.payoutBackService.update(back);
			modelMap.put("msg", "succeed");
		//}
		return "train/updateAuditPayout";
	}
	/**
	 * 公共收入页面修改列表
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping({ "/toUpdateIncomeCommonManager" })
	public String toUpdateIncomeCommonManager(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> queryMap = formMap.getFormMap();
		PageList pageList = this.incomeCommonBackService.queryPageList(queryMap,currentPage,pageSize);
		modelMap.put("pageList", pageList);
		modelMap.put("queryMap", queryMap);
		return "train/updateIncomeCommonManager";
	}
	/**
	 * 进入公共收入修改页面
	 */
	@RequestMapping({ "/toUpdateIncomeCommon" })
	public String toUpdateIncomeCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("incomeId", id);
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("createUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.incomeCommonBackService.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "对不起，你已修改过此记录，请等待其他人员修改确认！");
		} else {
			Map<String, Object> incomeCommonMap = this.incomeCommonService.find(id);
			//if (!incomeCommonMap.get("status").equals("0"))
			//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
			//else {
				modelMap.put("incomeCommonMap", incomeCommonMap);
				List<Map<String,Object>> incomeCommonItemList = ParamUtil.getIncomeCommonItemList();
				modelMap.put("incomeCommonItemList", incomeCommonItemList);
			//}
		}
		return "train/updateIncomeCommon";
	}
	/**
	 * 保存公共收入修改记录
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/updateIncomeCommon" })
	public String updateIncomeCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> incomeCommonMap = formMap.getFormMap();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("incomeId", incomeCommonMap.get("incomeId"));
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("notCreateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.incomeCommonBackService.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "其他负责人已进行修改了，请进行审核操作！");
		} else {
			IncomeCommonBack back = new IncomeCommonBack();
			BeanUtil.copyBeanProperties(back, incomeCommonMap);
			back.setUpdateStatus("0");
			back.setCreateTime(new Date());
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setCreateUserName(ParamUtil.getRealName(request));
			this.incomeCommonBackService.save(back);
			modelMap.put("msg", "succeed");
		}
		return "train/updateIncomeCommon";
	}
	/**
	 * 进入审核公共支出页面
	 */
	@RequestMapping({ "/toUpdateAuditIncomeCommon" })
	public String toUpdateAuditIncomeCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> incomeCommonBackMap = this.incomeCommonBackService.findBackByIncomeId(id);
		if(incomeCommonBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditIncomeCommon";
		}else{
			modelMap.put("incomeCommonBackMap", incomeCommonBackMap);
		}
		//查询修改前记录
		Map<String,Object> incomeCommonMap = this.incomeCommonService.find(id);
		//if (!incomeCommonMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			modelMap.put("incomeCommonMap", incomeCommonMap);
		//}
		return "train/updateAuditIncomeCommon";
	}
	
	@RequestMapping({ "/updateAuditIncomeCommon" })
	public String updateAuditIncomeCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> incomeCommonBackMap = this.incomeCommonBackService.findBackByIncomeId(id);
		if(incomeCommonBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditincomeCommon";
		}
		//查询修改前记录
		Map<String,Object> incomeCommonMap = this.incomeCommonService.find(id);
		//if (!incomeCommonMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			//更新原记录
			Map<String,Object> fm = formMap.getFormMap();
			for(int i = 0 ; i < incomeCommonBack.length;i++){
				incomeCommonMap.put(incomeCommonBack[i], incomeCommonBackMap.get(incomeCommonBack[i]));
			}
			if(fm.get("updateStatus")!=null&&fm.get("updateStatus").equals("1")){
				IncomeCommon incomeCommon = new IncomeCommon();
				BeanUtil.copyBeanProperties(incomeCommon, incomeCommonMap);
				this.incomeCommonService.update(incomeCommon);
			}
			//修改back
			IncomeCommonBack back = new IncomeCommonBack();
			BeanUtil.copyBeanProperties(back, incomeCommonBackMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setUpdateUserName(ParamUtil.getRealName(request));
			back.setUpdateTime(new Date());
			back.setUpdateStatus((String)fm.get("updateStatus"));
			back.setAuditNote((String)fm.get("auditNote"));
			this.incomeCommonBackService.update(back);
			modelMap.put("msg", "succeed");
		//}
		return "train/updateAuditIncome";
	}
	/**
	 * 公共支出修改页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/toUpdatePayoutCommonManager" })
	public String toUpdatePayoutCommonManager(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> queryMap = formMap.getFormMap();
		PageList pageList = this.payoutCommonBackService.queryPageList(queryMap, currentPage, pageSize);
		modelMap.put("pageList", pageList);
		modelMap.put("queryMap", queryMap);
		return "train/updatePayoutCommonManager";
	}
	
	@RequestMapping({ "/toUpdatePayoutCommon" })
	public String toUpdatePayoutCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("payoutId", id);
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("createUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.payoutCommonBackService.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "对不起，你已修改过此记录，请等待其他人员修改确认！");
		} else {
			Map<String, Object> payoutCommonMap = this.payoutCommonService.find(id);
			//if (!payoutCommonMap.get("status").equals("0"))
			//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
			//else {
				modelMap.put("payoutCommonMap", payoutCommonMap);
				List<Map<String,Object>> payoutCommonItemList = ParamUtil.getPayoutCommonItemList();
				modelMap.put("payoutCommonItemList", payoutCommonItemList);
			//}
		}
		return "train/updatePayoutCommon";
	}

	@RequestMapping({ "/updatePayoutCommon" })
	public String updatePayoutCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> payoutCommonMap = formMap.getFormMap();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("payoutId", payoutCommonMap.get("payoutId"));
		Map<String, Object> user = ParamUtil.getSessionUser(request);
		queryMap.put("notCreateUser", user.get("id"));
		queryMap.put("updateStatus", "0");
		List<Map<String, Object>> list = this.payoutCommonBackService.queryByParam(queryMap);
		if ((list != null) && (list.size() > 0)) {
			modelMap.put("msg", "其他负责人已进行修改了，请进行审核操作！");
		} else {
			PayoutCommonBack back = new PayoutCommonBack();
			BeanUtil.copyBeanProperties(back, payoutCommonMap);
			back.setUpdateStatus("0");
			back.setCreateTime(new Date());
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setCreateUserName(ParamUtil.getRealName(request));
			this.payoutCommonBackService.save(back);
			modelMap.put("msg", "succeed");
		}
		return "train/updatePayout";
	}
	/**
	 * 进入审核公共支出页面
	 */
	@RequestMapping({ "/toUpdateAuditPayoutCommon" })
	public String toUpdateAuditPayoutCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> payoutCommonBackMap = this.payoutCommonBackService.findBackByPayoutId(id);
		if(payoutCommonBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditPayoutCommon";
		}else{
			modelMap.put("payoutCommonBackMap", payoutCommonBackMap);
		}
		//查询修改前记录
		Map<String,Object> payoutCommonMap = this.payoutCommonService.find(id);
		//if (!payoutCommonMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			modelMap.put("payoutCommonMap", payoutCommonMap);
		//}
		return "train/updateAuditPayoutCommon";
	}
	
	@RequestMapping({ "/updateAuditPayoutCommon" })
	public String updateAuditPayoutCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Long id) {
		//修改后记录
		Map<String,Object> payoutCommonBackMap = this.payoutCommonBackService.findBackByPayoutId(id);
		if(payoutCommonBackMap==null){
			modelMap.put("msg", "对不起，修改记录状态已变更,不能进行审核操作！");
			return "train/updateAuditPayoutCommon";
		}
		//查询修改前记录
		Map<String,Object> payoutCommonMap = this.payoutCommonService.find(id);
		//if (!payoutCommonMap.get("status").equals("0")){
		//	modelMap.put("msg", "对不起，此记录的状态已变更，不能再修改了！");
		//}else {
			//更新原记录
			Map<String,Object> fm = formMap.getFormMap();
			for(int i = 0 ; i < payoutCommonBack.length;i++){
				payoutCommonMap.put(payoutCommonBack[i], payoutCommonBackMap.get(payoutCommonBack[i]));
			}
			if(fm.get("updateStatus")!=null&&fm.get("updateStatus").equals("1")){
				PayoutCommon payoutCommon = new PayoutCommon();
				BeanUtil.copyBeanProperties(payoutCommon, payoutCommonMap);
				this.payoutCommonService.update(payoutCommon);
			}
			//修改back
			PayoutCommonBack back = new PayoutCommonBack();
			BeanUtil.copyBeanProperties(back, payoutCommonBackMap);
			back.setUpdateUser(ParamUtil.getUserId(request));
			back.setUpdateUserName(ParamUtil.getRealName(request));
			back.setUpdateTime(new Date());
			back.setUpdateStatus((String)fm.get("updateStatus"));
			back.setAuditNote((String)fm.get("auditNote"));
			this.payoutCommonBackService.update(back);
			modelMap.put("msg", "succeed");
		//}
		return "train/updateAuditPayout";
	}
}