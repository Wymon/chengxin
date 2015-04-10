package xx.tream.chengxin.ms.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xx.tream.basepaltform.model.FormMap;
import xx.tream.chengxin.ms.service.AutumnNumberService;
import xx.tream.chengxin.ms.service.IncomeCommonService;
import xx.tream.chengxin.ms.service.IncomeService;
import xx.tream.chengxin.ms.service.PayingService;
import xx.tream.chengxin.ms.service.PayoutService;
import xx.tream.chengxin.ms.service.ReportService;
import xx.tream.chengxin.ms.service.TrainService;
import xx.tream.chengxin.ms.util.ExcelUtil;

@Controller
@RequestMapping({ "/trainReport" })
public class TrainReportAction {

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
	private ReportService reportService;
	@Autowired
	private IncomeCommonService incomeCommonService;
	/**
	 * 进入导出公共收入项目
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping({ "/toExportIncomeCommonReport" })
	public String toExportIncomeCommonReport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		return "train/exportIncomeCommonReport";
	}
	private String getDateFile(){
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String name = sdf.format(date);
		return name;
	}
	@RequestMapping({ "/exportIncomeCommonReport" })
	public String exportIncomeCommonReport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		Map<String, Object> queryMap = formMap.getFormMap();
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String title = "公共收入报表"+getDateFile()+".xls";
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode(title, "UTF-8"));
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook workbook = new HSSFWorkbook();
		this.reportService.incomeCommonReport(workbook, queryMap);
		workbook.write(os);
		os.flush();
		os.close();
		return "train/exportIncomeCommonReport";
	}
	
	/**
	 * 进入导出公共支出
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping({ "/toExportPayoutCommonReport" })
	public String toExportPayoutCommonReport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		return "train/exportPayoutCommonReport";
	}
	/**
	 * 导出公共支出
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping({ "/exportPayoutCommonReport" })
	public String exportPayoutCommonReport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		Map<String, Object> queryMap = formMap.getFormMap();
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String title = "公共支出报表"+getDateFile()+".xls";
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode(title, "UTF-8"));
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook workbook = new HSSFWorkbook();
		this.reportService.payoutCommonReport(workbook, queryMap);
		workbook.write(os);
		os.flush();
		os.close();
		return "train/exportPayoutCommonReport";
	}
	
	@RequestMapping({ "/toExportReport" })
	public String toExportReport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		return "train/exportReport";
	}
	
	@RequestMapping({ "/toExportReportDetail" })
	public String toExportReportDetail(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		return "train/exportReportDetail";
	}
	@RequestMapping({ "/exportReportDetail" })
	public String exportReportDetail(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		Map<String, Object> queryMap = formMap.getFormMap();
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String title = "财务日报明细表"+getDateFile()+".xls";
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode(title, "UTF-8"));
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook workbook = new HSSFWorkbook();
		this.reportService.financialStatementsDetail(workbook, queryMap);
		workbook.write(os);
		os.flush();
		os.close();
		return "train/exportReportDetail";
	}
	@RequestMapping({ "/exportReport" })
	public String exportReport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) throws IOException {
		Map<String, Object> queryMap = formMap.getFormMap();
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String title = "财务日报"+getDateFile()+".xls";
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode(title, "UTF-8"));
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook workbook = new HSSFWorkbook();
		this.reportService.financialStatements(workbook, queryMap);
		workbook.write(os);
		os.flush();
		os.close();
		return "train/exportReport";
	}

	private String getTrainIds(List<Map<String, Object>> trainList){
		if(trainList!=null&&trainList.size()>0){
			String ids = "";
			for(Map<String,Object> map : trainList){
				ids += map.get("id")+",";
			}
			ids = ids.substring(0,ids.length()-1);
			return ids;
		}
		return "-1";
	}
	@RequestMapping({ "/toExport" })
	public String toExport(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap, Integer currentPage,
			Integer pageSize) throws IOException {
		Map<String, Object> qm = formMap.getFormMap();
		List<Map<String, Object>> list = this.trainService.queryForParam(qm);
		//Map<String, Object> statisticsMap = this.trainService.statistics(qm);
		String trainIds = this.getTrainIds(list);
		List<Map<String, Object>> payingList = this.payingService.queryByTrainIds(trainIds);
		List<Map<String,Object>> incomeList = this.incomeService.queryByTrainIds(trainIds);
		List<Map<String,Object>> payoutList = this.payoutService.queryByTrainIds(trainIds);
		Map<Long, List<Map<String, Object>>> payingMap = converList(payingList);
		Map<Long, List<Map<String, Object>>> incomeMap = converList(incomeList);
		Map<Long, List<Map<String, Object>>> payoutMap = converList(payoutList);
		

		OutputStream os = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String title = "学员费用信息表"+getDateFile()+".xls";
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode(title, "UTF-8"));
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("学员信息");
		HSSFRow headrow = sheet.createRow(0);
		HSSFCellStyle headcell = workbook.createCellStyle();

		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		headcell.setFont(font);
		headrow.setRowStyle(headcell);
		String payingValue[] = {"","paying","createUserName","createTime","createTime","","auditUserName","auditTime","auditTime"};
		String incomeValue[] = {"type","income","createUserName","createTime","createTime","note","auditUserName","auditTime","auditTime"};
		String payoutValue[] = {"type","payout","createUserName","createTime","createTime","","auditUserName","auditTime","auditTime"};
		String[] heads = { "人数","编号","立秋编号","姓名", "身份证号", "应交学费","已交学费","应交总学费","总已交费用", "欠费","新/旧",
				"本校/挂靠","C1/C2","注册人","注册日期","注册时刻","备注","项目","金额","操作人","操作日期",
				"操作时刻","备注","审核人","审核日期","审核时刻" };
		String[] values = {"","id","autumnNumber","name","idcard","pay","allpaying","count_all","allip","canpay","newOrOld","type","licenseTag"
				,"createUserName","createTime","createTime","note"};
		HSSFCellStyle headStyle = ExcelUtil.headCell(workbook);
		HSSFCell cell = null;
		//列
		int cr = 0;
		for (int i = 0; i < heads.length ; i++) {
			cell = headrow.createCell(cr);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headStyle);
			sheet.setColumnWidth(cr, 5000);
			cr++;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		HSSFCellStyle rowStyle = ExcelUtil.leftCell(workbook);
		HSSFCellStyle paleBlueStyle = ExcelUtil.paleBlueForgroundCell(workbook);
		//HSSFCellStyle redFontStyle = ExcelUtil.leftFontRedCell(workbook);
		//HSSFCellStyle redFontForegroudStyle = ExcelUtil.leftFontRedForegroudCell(workbook);
		HSSFCellStyle rightStyle = ExcelUtil.rightCell(workbook);
		HSSFCellStyle rightForegroudStyle =ExcelUtil.rightPaleBlueForgroundCell(workbook);
		HSSFCellStyle rightredFontStyle = ExcelUtil.rightFontRedCell(workbook);
		HSSFCellStyle rightredFontForegroudStyle = ExcelUtil.rightFontRedForegroudCell(workbook);
		double ac[] =new double[5];
		
		//行
		int rn = 1;
		HSSFRow row= null;
		//表示靠右
		for (int i = 0; i < list.size(); i++) {
			int a = 0;
			//写入背景色
			int pb = 0;
			//列
			cr = 0;
			row = sheet.createRow(rn++);
			Map<String, Object> trainMap = (Map<String, Object>) list.get(i);
			List<Map<String, Object>> payL = (List<Map<String, Object>>) payingMap.get((Long) trainMap.get("id"));
			List<Map<String, Object>> incomeL = (List<Map<String, Object>>) incomeMap.get((Long) trainMap.get("id"));
			List<Map<String, Object>> payoutL = (List<Map<String, Object>>) payoutMap.get((Long) trainMap.get("id"));
			for(int v = 0 ; v < values.length;v++){
				cell = row.createCell(cr++);
				if(trainMap.get(values[v])!=null){
					if(v==14){
						cell.setCellValue(sdf.format((Date)trainMap.get(values[v])));
					}else if(v==15){
						cell.setCellValue(sdf2.format((Date)trainMap.get(values[v])));
					}else if(v==5||v==6||v==7||v==8||v==9){
						Double d = trainMap.get(values[v]) == null ? 0 :(Double)trainMap.get(values[v]) ;
						ac[a] += d ;
						a++;
						cell.setCellValue((Double)trainMap.get(values[v]));
					}else if(v==1){
						cell.setCellValue((Long)trainMap.get(values[v]));
					}else{
						cell.setCellValue((String)trainMap.get(values[v]));
					}
				}else{
					if(v ==0){
						cell.setCellValue(i+1);
					}else{
						cell.setCellValue("");
					}
				}
				if(v==5||v==6||v==7||v==8||v==9){//右边
					cell.setCellStyle(rightForegroudStyle);
				}else{
					cell.setCellStyle(paleBlueStyle);
				}
				
			}
			
			//写入缴费部分
			if (payL!= null && payL.size() > 0) {
				for(int p = 0 ;p<payL.size();p++){
					Map<String,Object> pMap = payL.get(p);
					cr = values.length ;
					for(int v = 0 ; v < payingValue.length;v++){
						cell = row.createCell(cr++);
						if(v==0){
							cell.setCellValue("已交学费"+(p+1));
						}else{
							if(pMap.get(payingValue[v])!=null){
								if(v==3||v==7){
									cell.setCellValue(sdf.format((Date)pMap.get(payingValue[v])));
								}else if(v==4||v==8){
									cell.setCellValue(sdf2.format((Date)pMap.get(payingValue[v])));
								}else if(v==1){
									Double nv = (Double)pMap.get(payingValue[v]);
									cell.setCellValue(nv);
								}else{
									cell.setCellValue((String)pMap.get(payingValue[v]));
								}
							}else{
								cell.setCellValue("");
							}
						}
						if(v==1){//右边
							if(pb==0){
								cell.setCellStyle(rightForegroudStyle);
							}else{
								cell.setCellStyle(rightStyle);
							}
						}else{
							if(pb==0){
								cell.setCellStyle(paleBlueStyle);
								
							}else{
								cell.setCellStyle(rowStyle);
							}
						}
					}
					pb++;
					row = sheet.createRow(rn++);
					
				}
				
			}
			//写入收入部分
			if (incomeL!= null && incomeL.size() > 0) {
				for(int p = 0 ;p<incomeL.size();p++){
					Map<String,Object> iMap = incomeL.get(p);
					cr = values.length ;
					for(int v = 0 ; v < incomeValue.length;v++){
						cell = row.createCell(cr++);
						if(v==0){
							cell.setCellValue(iMap.get(incomeValue[v])+"(收入项目)");
						}else{
							if(iMap.get(incomeValue[v])!=null){
								if(v==3||v==7){
									cell.setCellValue(sdf.format((Date)iMap.get(incomeValue[v])));
								}else if(v==4||v==8){
									cell.setCellValue(sdf2.format((Date)iMap.get(incomeValue[v])));
								}else if(v==1){
									cell.setCellValue((Double)iMap.get(incomeValue[v]));
								}else{
									cell.setCellValue((String)iMap.get(incomeValue[v]));
								}
							}else{
								cell.setCellValue("");
							}
						}
						if(v==1){//右边
							if(pb==0){
								cell.setCellStyle(rightForegroudStyle);
							}else{
								cell.setCellStyle(rightStyle);
							}
						}else{
							if(pb==0){
								cell.setCellStyle(paleBlueStyle);
								
							}else{
								cell.setCellStyle(rowStyle);
							}
						}
					}
					pb++;
					row = sheet.createRow(rn++);
				}
			}
			boolean flag = false;
			//写入支出部分
			if (payoutL!= null && payoutL.size() > 0) {
				for(int p = 0 ;p<payoutL.size();p++){
					Map<String,Object> pMap = payoutL.get(p);
					cr = values.length ;
					for(int v = 0 ; v < payoutValue.length;v++){
						cell = row.createCell(cr++);
						if(v==0){
							cell.setCellValue(pMap.get(payoutValue[v])+"(支出项目)");
						}else{
							if(pMap.get(payoutValue[v])!=null){
								if(v==3||v==7){
									cell.setCellValue(sdf.format((Date)pMap.get(payoutValue[v])));
								}else if(v==4||v==8){
									cell.setCellValue(sdf2.format((Date)pMap.get(payoutValue[v])));
								}else if(v==1){
									flag = true;
									cell.setCellValue(0-(Double)pMap.get(payoutValue[v]));
								}else{
									cell.setCellValue((String)pMap.get(payoutValue[v]));
								}
							}else{
								cell.setCellValue("");
							}
						}
						if(pb==0&&flag){
							flag = false;
							cell.setCellStyle(rightredFontForegroudStyle);
						}else if(flag){
							flag = false;
							cell.setCellStyle(rightredFontStyle);
						}else if(pb==0){
							cell.setCellStyle(paleBlueStyle);
						}else{
							cell.setCellStyle(rowStyle);
						}
					}
					pb++;
					if(p!=payoutL.size()-1){
						row = sheet.createRow(rn++);
					}
				}
			}
		}
		if(list!=null&&list.size()>0){
			row = sheet.createRow(rn++);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headStyle);
			for(int i = 0 ; i < ac.length;i++){
				cell = row.createCell(5+i);
				cell.setCellValue(ac[i]);
				cell.setCellStyle(rightStyle);
			}
		}
		
		workbook.write(os);
		os.flush();
		os.close();
		return null;
	}

	private Map<Long, List<Map<String, Object>>> converList(
			List<Map<String, Object>> list) {
		Map<Long, List<Map<String, Object>>> map = new HashMap<Long, List<Map<String, Object>>>();
		if(list!=null&&list.size()>0){
			for (Map<String, Object> m : list) {
				Long trainId = (Long) m.get("trainId");
				if (map.containsKey(trainId)) {
					List<Map<String, Object>> l = (List<Map<String, Object>>) map
							.get(trainId);
					l.add(m);
				} else {
					List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
					l.add(m);
					map.put(trainId, l);
				}
			}
		}
		
		return map;
	}
}