package xx.tream.chengxin.ms.action;


import java.text.SimpleDateFormat;
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
import xx.tream.chengxin.ms.model.Income;
import xx.tream.chengxin.ms.model.IncomeCommon;
import xx.tream.chengxin.ms.model.Paying;
import xx.tream.chengxin.ms.model.Payout;
import xx.tream.chengxin.ms.model.PayoutCommon;
import xx.tream.chengxin.ms.model.Train;
import xx.tream.chengxin.ms.service.AutumnNumberService;
import xx.tream.chengxin.ms.service.IncomeCommonBackService;
import xx.tream.chengxin.ms.service.IncomeCommonService;
import xx.tream.chengxin.ms.service.IncomeService;
import xx.tream.chengxin.ms.service.ParamService;
import xx.tream.chengxin.ms.service.PayingService;
import xx.tream.chengxin.ms.service.PayoutCommonBackService;
import xx.tream.chengxin.ms.service.PayoutCommonService;
import xx.tream.chengxin.ms.service.PayoutService;
import xx.tream.chengxin.ms.service.TrainService;
import xx.tream.chengxin.ms.util.ParamUtil;

@Controller
@RequestMapping({ "/train" })
public class TrainAction {

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
	private PayoutCommonService payoutCommonService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private IncomeCommonService incomeCommonService;
	@Autowired
	private IncomeCommonBackService incomeCommonBackService;
	@Autowired
	private PayoutCommonBackService payoutCommonBackService;
	
	@RequestMapping({ "/toIncomeCommonPrint" })
	public String toIncomeCommonPrint(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> printMap = this.incomeCommonService.find(id);
		printMap.put("printDate", new Date());
		modelMap.put("printMap", printMap);
		return "train/incomeCommonPrint";
	}
	@RequestMapping({ "/toPayoutCommonPrint" })
	public String toPayoutCommonPrint(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> printMap = this.payoutCommonService.find(id);
		printMap.put("printDate", new Date());
		modelMap.put("printMap", printMap);
		return "train/payoutCommonPrint";
	}
	@RequestMapping({ "/toPayoutPrint" })
	public String toPayoutPrint(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> printMap = this.payoutService.find(id);
		printMap.put("printDate", new Date());
		modelMap.put("printMap", printMap);
		return "train/payoutPrint";
	}
	@RequestMapping({ "/toIncomeCommonManager" })
	public String toIncomeCommonManager(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> queryMap = formMap.getFormMap();
		PageList pageList = this.incomeCommonService.queryByMap(queryMap, currentPage,pageSize);
		modelMap.put("pageList", pageList);
		modelMap.put("queryMap", queryMap);
		Double allCount = this.incomeCommonService.queryByMap(queryMap);
		modelMap.put("allCount", allCount);
		return "train/incomeCommonManager";
	}
	
	@RequestMapping({ "/toAddIncomeCommon" })
	public String toAddIncomeCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		List<Map<String,Object>> incomeCommonItemList = ParamUtil.getIncomeCommonItemList();
		modelMap.put("incomeCommonItemList", incomeCommonItemList);
		return "train/addIncomeCommon";
	}

	@RequestMapping({ "/addIncomeCommon" })
	public String addIncomeCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, String flag) {
		Map<String, Object> incomeCommonMap = formMap.getFormMap();
		IncomeCommon income = new IncomeCommon();
		BeanUtil.copyBeanProperties(income, incomeCommonMap);
		income.setStatus("0");
		income.setCreateTime(new Date());
		income.setCreateUserName(ParamUtil.getRealName(request));
		this.incomeCommonService.save(income);
		
		modelMap.put("flag", flag);
		
		List<Map<String,Object>> incomeCommonItemList = ParamUtil.getIncomeCommonItemList();
		modelMap.put("incomeCommonItemList", incomeCommonItemList);
		modelMap.put("msg", "succeed");
		return "train/addIncomeCommon";
	}
	
	@RequestMapping({ "/toList" })
	public String toList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String, Object> qm = formMap.getFormMap();
		PageList pageList = this.trainService.queryByMap(qm, currentPage,
				pageSize);
		Map<String, Object> statisticsMap = this.trainService.statistics(qm);
		boolean hasPayoutCommonAudit = this.trainService.hasPayoutCommonAudti();
		modelMap.put("hasCommonAudit", hasPayoutCommonAudit);
		modelMap.put("pageList", pageList);
		modelMap.put("queryMap", qm);
		modelMap.put("statisticsMap", statisticsMap);
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("updateStatus", "0");
		List<Map<String,Object>> icList = this.incomeCommonBackService.queryByParam(queryMap);
		if(icList!=null&&icList.size()>0){
			modelMap.put("hasIncomeCommonAudit", 1);
		}else{
			modelMap.put("hasIncomeCommonAudit", 0);
		}
		List<Map<String,Object>> pcList = this.payoutCommonBackService.queryByParam(queryMap);
		if(pcList!=null&&pcList.size()>0){
			modelMap.put("hasPayoutCommonAudit", 1);
		}else{
			modelMap.put("hasPayoutCommonAudit", 0);
		}
		return "index";
	}

	@RequestMapping({ "/toAddTrain" })
	public String toAddTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> oldTrainInfoMap = this.paramService.findByCode("canAddOldTrain");
		modelMap.put("oldTrainInfoMap", oldTrainInfoMap);
		return "train/addTrain";
	}

	@RequestMapping({ "/addTrain" })
	public String addTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String, Object> trainMap = formMap.getFormMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Train train = new Train();
		if (trainMap.get("newOrOld").toString().equals("新")) {
			trainMap.put("createTime", new Date());
		} else {
			try {
				train.setCreateTime(sdf.parse((String) trainMap
						.get("createTime")));
				trainMap.remove("createTime");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		BeanUtil.copyBeanProperties(train, trainMap);
		train.setCreateUserName(ParamUtil.getRealName(request));
		if (this.trainService.checkByIdcard(train.getIdcard())) {
			modelMap.put("trainMap", trainMap);
			modelMap.put("msg", "学员身份证号已存在！");
			return "train/add";
		}
		this.trainService.save(train);
		modelMap.put("msg", "succeed");
		return "train/addTrain";
	}

	@RequestMapping({ "/toAddAutumnNumber" })
	public String toAddAutumnNumber(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			FormMap formMap, Integer currentPage, Integer pageSize, Long id) {
		Map<String, Object> trainMap = this.autumnNumberService.findByTrain(id);
		Map<String,Object> train = this.trainService.find(id);
		modelMap.put("trainMap", trainMap);
		modelMap.put("train", train);
		return "train/autumnNumber";
	}

	@RequestMapping({ "/addAutumnNumber" })
	public String addAutumnNumber(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			FormMap formMap, Integer currentPage, Integer pageSize) {
		Map<String, Object> autumnNumberMap = formMap.getFormMap();
		autumnNumberMap.put("createTime", new Date());
		AutumnNumber autumnNumber = new AutumnNumber();
		BeanUtil.copyBeanProperties(autumnNumber, autumnNumberMap);
		Map<String, Object> m = this.autumnNumberService
				.findByTrain(autumnNumber.getTrainId());
		if (m == null) {
			autumnNumber.setCreateUserName(ParamUtil.getRealName(request));
			this.autumnNumberService.save(autumnNumber);
			modelMap.put("msg", "succeed");
		} else {
			modelMap.put("trainMap", m);
			modelMap.put("msg", "立秋编号已添加了，不能再添加！");
		}
		return "train/autumnNumber";
	}
	@RequestMapping({ "/toUpdateAutumnNumber" })
	public String toUpdateAutumnNumber(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			FormMap formMap, Integer currentPage, Integer pageSize, Long id) {
		Map<String, Object> trainMap = this.autumnNumberService.findByTrain(id);
		Map<String,Object> train = this.trainService.find(id);
		modelMap.put("trainMap", trainMap);
		modelMap.put("train", train);
		return "train/updateAutumnNumber";
	}
	@RequestMapping({ "/updateAutumnNumber" })
	public String updateAutumnNumber(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			FormMap formMap, Integer currentPage, Integer pageSize) {
		Map<String, Object> autumnNumberMap = formMap.getFormMap();
		autumnNumberMap.put("createTime", new Date());
		AutumnNumber autumnNumber = new AutumnNumber();
		BeanUtil.copyBeanProperties(autumnNumber, autumnNumberMap);
		Map<String, Object> m = this.autumnNumberService
				.findByTrain(autumnNumber.getTrainId());
		if (m == null) {
			modelMap.put("msg", "立秋编号找不到了");
		} else {
			autumnNumber.setId(Long.parseLong(m.get("id")+""));
			autumnNumber.setAutumnNumber((String)autumnNumberMap.get("autumnNumber"));
			autumnNumber.setCreateTime(new Date());
			autumnNumber.setCreateUser(ParamUtil.getUserId(request));
			autumnNumber.setCreateUserName(ParamUtil.getRealName(request));
			this.autumnNumberService.update(autumnNumber);
			modelMap.put("msg", "succeed");
		}
		return "train/updateAutumnNumber";
	}
	@RequestMapping({ "/toAddPaying" })
	public String toAddPaying(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> trainMap = this.trainService.find(id);
		modelMap.put("trainMap", trainMap);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("trainId", id);
		List<Map<String, Object>> payingMapList = this.payingService
				.queryByParam(queryMap);
		modelMap.put("payingMapList", payingMapList);
		return "train/addPaying";
	}

	@RequestMapping({ "/addPaying" })
	public String addPaying(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> payingMap = formMap.getFormMap();
		Paying pay = new Paying();
		BeanUtil.copyBeanProperties(pay, payingMap);
		pay.setCreateTime(new Date());
		pay.setStatus("0");
		pay.setCreateUserName(ParamUtil.getRealName(request));
		this.payingService.save(pay);
		modelMap.put("msg", "succeed");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("trainId", pay.getTrainId());
		List<Map<String, Object>> payingMapList = this.payingService
				.queryByParam(queryMap);
		modelMap.put("payingMapList", payingMapList);
		Map<String, Object> trainMap = this.trainService.find(pay.getTrainId());
		modelMap.put("trainMap", trainMap);

		return "train/addPaying";
	}

	@RequestMapping({ "/toPayingPrint" })
	public String toPayingPrint(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> printMap = this.payingService.findPaying(id);
		printMap.put("printDate", new Date());
		modelMap.put("printMap", printMap);
		return "train/printPaying";
	}
	@RequestMapping({ "/toIncomePrint" })
	public String toIncomePrint(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> printMap = this.incomeService.findIncome(id);
		printMap.put("printDate", new Date());
		modelMap.put("printMap", printMap);
		return "train/printIncome";
	}
	@RequestMapping({ "/toUpdateAudit" })
	public String toUpdateAudit(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> trainMap = this.trainService.find(id);
		modelMap.put("trainMap", trainMap);
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("status","0");
		queryMap.put("trainId", id);
		List<Map<String, Object>> payingMapList = this.payingService
				.queryByParam(queryMap);
		modelMap.put("payingMapList", payingMapList);

		List<Map<String, Object>> incomeMapList = this.incomeService
				.queryByParam(queryMap);
		modelMap.put("incomeMapList", incomeMapList);

		List<Map<String, Object>> payoutMapList = this.payoutService
				.queryByParam(queryMap);
		modelMap.put("payoutMapList", payoutMapList);
		return "train/updateAudit";
	}

	@RequestMapping({ "/updateAudit" })
	public String updateAudit(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String, Object> auditMap = formMap.getFormMap();
		Long auditUser = Long.valueOf(Long.parseLong((String) auditMap
				.get("auditUser")));
		String paying = (String) auditMap.get("paying");
		String auditUserName = ParamUtil.getRealName(request);
		if ((paying != null) && (!paying.equals(""))) {
			String[] data = paying.split(";");
			for (int i = 0; i < data.length; i++) {
				String[] p = data[i].split("_");
				Long id = Long.valueOf(Long.parseLong(p[0]));
				Map<String, Object> payMap = this.payingService.find(id
						.longValue());
				Paying pay = new Paying();
				BeanUtil.copyBeanProperties(pay, payMap);
				pay.setAuditTime(new Date());
				pay.setAuditUser(auditUser);
				pay.setStatus(p[1]);
				pay.setAuditUserName(auditUserName);
				this.payingService.update(pay);
			}
		}

		String income = (String) auditMap.get("income");
		if ((income != null) && (!income.equals(""))) {
			String[] data = income.split(";");
			for (int i = 0; i < data.length; i++) {
				String[] p = data[i].split("_");
				Long id = Long.valueOf(Long.parseLong(p[0]));
				Map<String, Object> inMap = this.incomeService.find(id
						.longValue());
				Income in = new Income();
				BeanUtil.copyBeanProperties(in, inMap);
				in.setAuditTime(new Date());
				in.setAuditUser(auditUser);
				in.setStatus(p[1]);
				in.setAuditUserName(auditUserName);
				this.incomeService.update(in);
			}
		}
		String payout = (String) auditMap.get("payout");
		if ((payout != null) && (!payout.equals(""))) {
			String[] data = payout.split(";");
			for (int i = 0; i < data.length; i++) {
				String[] p = data[i].split("_");
				Long id = Long.valueOf(Long.parseLong(p[0]));
				Map<String, Object> poMap = this.payoutService.find(id
						.longValue());
				Payout po = new Payout();
				BeanUtil.copyBeanProperties(po, poMap);
				po.setAuditTime(new Date());
				po.setAuditUser(auditUser);
				po.setStatus(p[1]);
				po.setAuditUserName(auditUserName);
				this.payoutService.update(po);
			}
		}
		modelMap.put("msg", "succeed");
		return "train/updateAudit";
	}

	@RequestMapping({ "/toAddIncome" })
	public String toAddIncome(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		List<Map<String,Object>> incomeItems = ParamUtil.getIncomeItemList();
		modelMap.put("incomeItems", incomeItems);
		Map<String, Object> trainMap = this.trainService.find(id.longValue());
		modelMap.put("trainMap", trainMap);
		List<Map<String, Object>> incomeMapList = this.incomeService
				.findByTrain(id);
		modelMap.put("incomeMapList", incomeMapList);
		return "train/addIncome";
	}

	@RequestMapping({ "/addIncome" })
	public String addIncome(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, String flag) {
		Map<String, Object> incomeMap = formMap.getFormMap();
		Income income = new Income();
		BeanUtil.copyBeanProperties(income, incomeMap);
		income.setStatus("0");
		income.setCreateTime(new Date());
		income.setCreateUserName(ParamUtil.getRealName(request));
		this.incomeService.save(income);
		List<Map<String, Object>> incomeMapList = this.incomeService
				.findByTrain(income.getTrainId());
		modelMap.put("incomeMapList", incomeMapList);
		modelMap.put("flag", flag);
		Map<String, Object> trainMap = this.trainService.find(income
				.getTrainId().longValue());
		modelMap.put("trainMap", trainMap);
		modelMap.put("msg", "succeed");
		List<Map<String,Object>> incomeItems = ParamUtil.getIncomeItemList();
		modelMap.put("incomeItems", incomeItems);
		return "train/addIncome";
	}

	@RequestMapping({ "/toAddPayout" })
	public String toAddPayout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> trainMap = this.trainService.find(id.longValue());
		modelMap.put("trainMap", trainMap);
		List<Map<String, Object>> payoutMapList = this.payoutService
				.findByTrain(id);
		modelMap.put("payoutMapList", payoutMapList);
		List<Map<String,Object>> payoutItemList = ParamUtil.getPayoutItemList();
		modelMap.put("payoutItemList", payoutItemList);
		return "train/addPayout";
	}

	@RequestMapping({ "/addPayout" })
	public String addPayout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, String flag) {
		Map<String, Object> payoutMap = formMap.getFormMap();
		Payout payout = new Payout();
		BeanUtil.copyBeanProperties(payout, payoutMap);
		payout.setStatus("0");
		payout.setCreateTime(new Date());
		payout.setCreateUserName(ParamUtil.getRealName(request));
		this.payoutService.save(payout);
		List<Map<String, Object>> payoutMapList = this.payoutService
				.findByTrain(payout.getTrainId());
		modelMap.put("payoutMapList", payoutMapList);
		modelMap.put("flag", flag);
		Map<String, Object> trainMap = this.trainService.find(payout
				.getTrainId());
		modelMap.put("trainMap", trainMap);
		List<Map<String,Object>> payoutItemList = ParamUtil.getPayoutItemList();
		modelMap.put("payoutItemList", payoutItemList);
		modelMap.put("msg", "succeed");
		return "train/addPayout";
	}
	/**
	 * 进入公共支出财务审核
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping({ "/toUpdatePayoutCommonAudit" })
	public String toUpdatePayoutCommonAudit(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("status", "0");
		List<Map<String,Object>> auditList = this.payoutCommonService.queryByParam(queryMap);
		modelMap.put("auditList", auditList);
		List<Map<String,Object>> incomeList = this.incomeCommonService.queryByParam(queryMap);
		modelMap.put("incomeList", incomeList);
		return "train/updatePayoutAudit";
	}
	
	@RequestMapping({ "/updatePayoutCommonAudit" })
	public String updatePayoutCommonAudit(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String, Object> auditMap = formMap.getFormMap();
		Long auditUser = Long.valueOf(Long.parseLong((String) auditMap
				.get("auditUser")));
		String paying = (String) auditMap.get("payoutCommon");
		if ((paying != null) && (!paying.equals(""))) {
			String[] data = paying.split(";");
			for (int i = 0; i < data.length; i++) {
				String[] p = data[i].split("_");
				Long id = Long.valueOf(Long.parseLong(p[0]));
				Map<String, Object> payMap = this.payoutCommonService.find(id);
				PayoutCommon pay = new PayoutCommon();
				BeanUtil.copyBeanProperties(pay, payMap);
				pay.setAuditTime(new Date());
				pay.setAuditUser(auditUser);
				pay.setAuditUserName(ParamUtil.getRealName(request));
				pay.setStatus(p[1]);
				this.payoutCommonService.update(pay);
			}
		}
		String incomeCommon = (String) auditMap.get("incomeCommon");
		if ((incomeCommon != null) && (!incomeCommon.equals(""))) {
			String[] data = incomeCommon.split(";");
			for (int i = 0; i < data.length; i++) {
				String[] p = data[i].split("_");
				Long id = Long.parseLong(p[0]);
				Map<String, Object> incomeMap = this.incomeCommonService.find(id);
				IncomeCommon income = new IncomeCommon();
				BeanUtil.copyBeanProperties(income, incomeMap);
				income.setAuditTime(new Date());
				income.setAuditUser(auditUser);
				income.setAuditUserName(ParamUtil.getRealName(request));
				income.setStatus(p[1]);
				this.incomeCommonService.update(income);
			}
		}
		modelMap.put("msg", "succeed");
		return "train/updatePayoutAudit";
	}
	
	@RequestMapping({ "/toPayoutCommonManager" })
	public String toPayoutCommonManager(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> queryMap = formMap.getFormMap();
		PageList pageList = this.payoutCommonService.queryByMap(queryMap, currentPage,pageSize);
		modelMap.put("pageList", pageList);
		modelMap.put("queryMap", queryMap);
		Double allCount = this.payoutCommonService.queryByMap(queryMap);
		modelMap.put("allCount", allCount);
		return "train/payoutCommonManager";
	}
	
	@RequestMapping({ "/toAddPayoutCommon" })
	public String toAddPayoutCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		List<Map<String,Object>> payoutCommonItemList = ParamUtil.getPayoutCommonItemList();
		modelMap.put("payoutCommonItemList", payoutCommonItemList);
		return "train/addPayoutCommon";
	}

	@RequestMapping({ "/addPayoutCommon" })
	public String addPayoutCommon(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, String flag) {
		Map<String, Object> payoutCommonMap = formMap.getFormMap();
		PayoutCommon payout = new PayoutCommon();
		BeanUtil.copyBeanProperties(payout, payoutCommonMap);
		payout.setStatus("0");
		payout.setCreateTime(new Date());
		payout.setCreateUserName(ParamUtil.getRealName(request));
		this.payoutCommonService.save(payout);
		
		modelMap.put("flag", flag);
		
		List<Map<String,Object>> payoutCommonItemList = ParamUtil.getPayoutCommonItemList();
		modelMap.put("payoutCommonItemList", payoutCommonItemList);
		modelMap.put("msg", "succeed");
		return "train/addPayoutCommon";
	}
	
	@RequestMapping({ "/toView" })
	public String toView(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize, Long id) {
		Map<String, Object> trainMap = this.trainService.find(id.longValue());
		modelMap.put("trainMap", trainMap);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("trainId", id);

		List<Map<String, Object>> payingMapList = this.payingService
				.queryByParam(queryMap);
		modelMap.put("payingMapList", payingMapList);

		List<Map<String, Object>> incomeMapList = this.incomeService
				.queryByParam(queryMap);
		modelMap.put("incomeMapList", incomeMapList);

		List<Map<String, Object>> payoutMapList = this.payoutService
				.queryByParam(queryMap);
		modelMap.put("payoutMapList", payoutMapList);
		return "train/view";
	}
}