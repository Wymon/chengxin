package xx.tream.chengxin.ms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xx.tream.basepaltform.dao.DaoUtil;
import xx.tream.chengxin.ms.util.DateUtil;
import xx.tream.chengxin.ms.util.ExcelUtil;
import xx.tream.chengxin.ms.util.ParamUtil;
@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private DaoUtil dao;
	@Autowired
	private PayoutCommonService payoutCommonService;
	@Autowired
	private ReportParamService reportParamService;
	@Autowired
	private PayingService payingService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private PayoutService payoutService;
	@Autowired
	private IncomeCommonService incomeCommonService;
	
	public HSSFWorkbook financialStatements(HSSFWorkbook workbook,
			Map<String, Object> queryMap) {
		HSSFSheet sheet = workbook.createSheet("财务日报");
		HSSFRow headrow = sheet.createRow(0);
		HSSFCellStyle headcell = ExcelUtil.headCell(workbook);
		headrow.setRowStyle(headcell);
		String start = (String)queryMap.get("beginTime");
		String end = (String)queryMap.get("endTime");
		Date startDate = DateUtil.stringToDate(start, "yyyy-MM-dd");
		Date endDate = DateUtil.stringToDate(end, "yyyy-MM-dd");
		HSSFCellStyle rightcell = ExcelUtil.rightCell(workbook);
		
		//HSSFCellStyle leftcell = ExcelUtil.leftCell(workbook);
		HSSFCellStyle centercell = ExcelUtil.centerCell(workbook);
		//用于最后共计  收入和支出
		int icount = 0;
		if(endDate!=null){
			//时间加1天
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
			end = DateUtil.DateToString(endDate, "yyyy-MM-dd");
			queryMap.put("endTime", end);
		}
		
		//第一列 日期
		ExcelUtil.mergeRegion(sheet, 0, 1, 0, 0, "日期", headcell);
		sheet.setColumnWidth(0, 6500);
		icount++;
		//第二列 缴费
		ExcelUtil.mergeRegion(sheet, 0, 1, 1, 1, "缴费", headcell);
		sheet.setColumnWidth(1, 4500);
		HSSFRow row = sheet.createRow(1);	
		icount++;
		int col = 2 ;
		//收入  写入标题  公共标题加其它类型的
		String[] incomeItems = ParamUtil.incomeItems;
		for (int i = 0; i < incomeItems.length-1; i++) {
			//最后一个其它就不用写入来
			HSSFCell cell = row.createCell(col);
			cell.setCellValue(incomeItems[i]);
			cell.setCellStyle(headcell);
			sheet.setColumnWidth(col, 4500);
			col++;
			icount++;
		}
		String[] otherIncomeItems = reportParamService.queryIncomeOtherItem(startDate, endDate);
		int n = 0;
		if(otherIncomeItems!=null&&otherIncomeItems.length>0){
			for (int i = 0; i < otherIncomeItems.length; i++) {
				HSSFCell cell = row.createCell(col);
				cell.setCellValue("其它("+otherIncomeItems[i]+")");
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(col, 4500);
				icount++;
				col++;
			}
			n = otherIncomeItems.length;
		}
		int s = 2;
		int e = incomeItems.length+n ;
		ExcelUtil.mergeRegion(sheet, 0, 0, s, e, "收入", headcell);
		//公共收入
		String[] incomeCommonItems = ParamUtil.incomeCommonItems;
		for (int i = 0; i < incomeCommonItems.length-1; i++) {
			//最后一个其它就不用写入来
			HSSFCell cell = row.createCell(col);
			cell.setCellValue(incomeCommonItems[i]);
			cell.setCellStyle(headcell);
			sheet.setColumnWidth(col, 4500);
			col++;
			icount++;
		}
		String[] otherIncomeCommonItems = reportParamService.queryIncomeCommonOtherItem(startDate, endDate);
		n = 0;
		if(otherIncomeCommonItems!=null&&otherIncomeCommonItems.length>0){
			for (int i = 0; i < otherIncomeCommonItems.length; i++) {
				HSSFCell cell = row.createCell(col);
				cell.setCellValue("其它("+otherIncomeCommonItems[i]+")");
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(col, 4500);
				icount++;
				col++;
			}
			n = otherIncomeCommonItems.length;
		}
		s = e+1;
		e = e+incomeCommonItems.length-1+n ;
		ExcelUtil.mergeRegion(sheet, 0, 0, s, e, "公共收入", headcell);
		
		//支出
		n=0;
		String payoutItems[] = ParamUtil.payoutItems;
		for (int i = 0; i < payoutItems.length-1; i++) {
			//最后一个其它就不用写入来
			HSSFCell cell = row.createCell(col);
			cell.setCellValue(payoutItems[i]);
			cell.setCellStyle(headcell);
			sheet.setColumnWidth(col, 4500);
			col++;
		}
		String[] payoutOtherItems = reportParamService.queryPayoutOtherItem(startDate, endDate);
		if(payoutOtherItems!=null&&payoutOtherItems.length>0){
			for (int i = 0; i < payoutOtherItems.length; i++) {
				HSSFCell cell = row.createCell(col);
				cell.setCellValue("其它("+payoutOtherItems[i]+")");
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(col, 4500);
				col++;
			}
			n = payoutOtherItems.length;
		}
		s = e+1;
		e = e+payoutItems.length-1+n ;
		ExcelUtil.mergeRegion(sheet, 0, 0, s, e, "支出", headcell);
		//公共支出 写入标题  公共标题加其它类型的 
		String[] payoutCommonItems = ParamUtil.payoutCommonItems;
		for (int i = 0; i < payoutCommonItems.length-1; i++) {
			//最后一个其它就不用写入来
			HSSFCell cell = row.createCell(col);
			cell.setCellValue(payoutCommonItems[i]);
			cell.setCellStyle(headcell);
			sheet.setColumnWidth(col, 4500);
			col++;
			
		}
		String payoutCommonOtherTypes[] = reportParamService.queryPayoutCommonOtherItem(startDate, endDate);
		if(payoutCommonOtherTypes!=null&&payoutCommonOtherTypes.length>0){
			for (int i = 0; i < payoutCommonOtherTypes.length; i++) {
				HSSFCell cell = row.createCell(col);
				cell.setCellValue("其它("+payoutCommonOtherTypes[i]+")");
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(col, 4500);
				col++;
				
			}
			n = payoutCommonOtherTypes.length;
		}
		s = e+1;
		e = e+payoutCommonItems.length-1+n ;
		ExcelUtil.mergeRegion(sheet, 0, 0, s, e, "公共支出", headcell);
		HSSFCell cell = null;
		//第三行开始写入数据
		queryMap.put("", "");
		List<Map<String,Object>> list = this.reportParamService.queryIncomeAndPayout(queryMap, incomeItems, otherIncomeItems,incomeCommonItems,otherIncomeCommonItems, payoutItems, payoutOtherItems, payoutCommonItems, payoutCommonOtherTypes);
		if(list!=null&&list.size()>0){
			double[] count = new double[col];
			for(int i = 0 ; i < list.size();i++){
				col = 0;
				row = sheet.createRow(2+i);
				Map<String,Object> map = list.get(i);
				cell = row.createCell(col);
				cell.setCellValue((String)map.get("audittime"));
				col++;
				cell = row.createCell(col);
				if(map.get("paying")!=null){
					Double v = (Double)map.get("paying");
					count[col] =  count[col]+v;
					cell.setCellValue(v);
				}
				col++;
				//收入
				if(incomeItems!=null&&incomeItems.length>0){
					for(int k = 0 ; k < incomeItems.length-1;k++){
						cell = row.createCell(col);
						if(map.get("i"+k)!=null){
							Double v = (Double)map.get("i"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				if(otherIncomeItems!=null&&otherIncomeItems.length>0){
					for(int k = 0 ; k < otherIncomeItems.length;k++){
						cell = row.createCell(col);
						if(map.get("io"+k)!=null){
							Double v = (Double)map.get("io"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				//公共收入
				if(incomeCommonItems!=null&&incomeCommonItems.length>0){
					for(int k = 0 ; k < incomeCommonItems.length-1;k++){
						cell = row.createCell(col);
						if(map.get("ic"+k)!=null){
							Double v = (Double)map.get("ic"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				if(otherIncomeCommonItems!=null&&otherIncomeCommonItems.length>0){
					for(int k = 0 ; k < otherIncomeCommonItems.length;k++){
						cell = row.createCell(col);
						if(map.get("ico"+k)!=null){
							Double v = (Double)map.get("ico"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				//支出
				if(payoutItems!=null&&payoutItems.length>0){
					for(int k = 0 ; k < payoutItems.length-1;k++){
						cell = row.createCell(col);
						if(map.get("p"+k)!=null){
							Double v = (Double)map.get("p"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				if(payoutOtherItems!=null&&payoutOtherItems.length>0){
					for(int k = 0 ; k < payoutOtherItems.length;k++){
						cell = row.createCell(col);
						if(map.get("po"+k)!=null){
							Double v = (Double)map.get("po"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				//公共支出
				if(payoutCommonItems!=null&&payoutCommonItems.length>0){
					for(int k = 0 ; k < payoutCommonItems.length-1;k++){
						cell = row.createCell(col);
						if(map.get("pc"+k)!=null){
							Double v = (Double)map.get("pc"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				if(payoutCommonOtherTypes!=null&&payoutCommonOtherTypes.length>0){
					for(int k = 0 ; k < payoutCommonOtherTypes.length;k++){
						cell = row.createCell(col);
						if(map.get("pco"+k)!=null){
							Double v = (Double)map.get("pco"+k);
							count[col] =  count[col]+v;
							cell.setCellValue(v);
						}
						col++;
					}
				}
				
			}
			//合计
			row = sheet.createRow(2+list.size());
			cell =null;
			for(int i = 0 ; i < count.length;i++){
				if(i==0){
					cell = row.createCell(0);
					cell.setCellValue("合计");
					cell.setCellStyle(headcell);
				}else{
					cell = row.createCell(i);
					cell.setCellValue(count[i]);
				}
				
			}
			double ivalue = 0;
			double pvalue = 0;
			for(int i = 0 ; i < icount;i++){
				ivalue += count[i];
			}
			for(int i = icount ; i < count.length;i++){
				pvalue += count[i];
			}
			//共计
			row = sheet.createRow(3+list.size());
			cell = row.createCell(0);
			cell.setCellValue("共计收入:");
			cell.setCellStyle(headcell);
			cell = row.createCell(1);
			cell.setCellValue(ivalue);
			cell = row.createCell(2);
			cell.setCellValue("共计支出:");
			cell.setCellStyle(headcell);
			cell = row.createCell(3);
			cell.setCellValue(pvalue);
		}
		int rowNum = 0;
		//缴费
		List<Map<String,Object>> trainIPOList = this.queryTrainIncomePayout(queryMap);
		if(trainIPOList!=null&&trainIPOList.size()>0){
			
			sheet = workbook.createSheet("学员费用统计");
			String heads[] = {"学员","身份证","应交学费","收入","缴费","支出","应收总费用"};
			String values[] = {"name","idcard","payable","allincome","allpaying","allpayout"};
			row = sheet.createRow(rowNum);
			
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			double countAll[] = new double[heads.length];
			for(int i = 0 ; i < trainIPOList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = trainIPOList.get(i);
				//应收总费用
				double allpip = 0;
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k>1){
							double value = (Double)map.get(values[k]);
							if(k < 4){
								allpip += value;
							}
							cell.setCellValue(value);
							countAll[k] += value;
						}else{
							cell.setCellValue((String)map.get(values[k]));
						}
					}
				}
				//应收总费用
				cell = row.createCell(values.length);
				cell.setCellValue(allpip);
				countAll[values.length] += allpip;
				rowNum++;
			}
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			for(int i = 2 ; i<countAll.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(countAll[i]);
			}
			rowNum++;
		}
		rowNum = 0;
		//收支统计表
		List<Map<String,Object>> countList = this.queryCountByTime(queryMap);
		if(countList!=null&&countList.size()>0){
			
			sheet = workbook.createSheet("收支统计表");
			String heads[] = {"日期","应收学费合计","缴费（学费）合计","收入合计","公共收入合计","支出合计","公共支出合计","欠学费合计（备注：累计不真实，仅作参考）"};
			String values[] = {"audittime","payable","paying","income","incomeCommon","payout","payoutCommon","canPay"};
			row = sheet.createRow(rowNum);
			
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			double countAll[] = new double[heads.length];
			for(int i = 0 ; i < countList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = countList.get(i);
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k>0){
							double value = (Double)map.get(values[k]);
							cell.setCellValue(value);
							cell.setCellStyle(rightcell);
							countAll[k] += value;
						}else{
							cell.setCellValue((String)map.get(values[k]));
							cell.setCellStyle(centercell);
						}
					}
				}
				
				
				rowNum++;
			}
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			for(int i = 1 ; i<countAll.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(countAll[i]);
				if(countAll[i] >= 0){
					cell.setCellStyle(rightcell);
				}else{
					
				}
				
			}
			rowNum++;
		}
		
		
		return workbook;
	}

	public HSSFWorkbook financialStatementsDetail(HSSFWorkbook workbook,
			Map<String, Object> queryMap) {
		HSSFSheet sheet = null;
		HSSFCellStyle headcell = ExcelUtil.headCell(workbook);
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle leftStyle = ExcelUtil.leftCell(workbook);
		HSSFCellStyle rightStyle = ExcelUtil.rightCell(workbook);
		String end = (String)queryMap.get("endTime");
		Date endDate = DateUtil.stringToDate(end, "yyyy-MM-dd");
		if(endDate!=null){
			//时间加1天
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
			end = DateUtil.DateToString(endDate, "yyyy-MM-dd");
			queryMap.put("endTime", end);
		}
		int rowNum = 0;
		//缴费
		List<Map<String,Object>> trainIPOList = this.queryTrainIncomePayout(queryMap);
		if(trainIPOList!=null&&trainIPOList.size()>0){
			
			sheet = workbook.createSheet("注册学员应交学费");
			String heads[] = {"人数","学员","身份证","应交学费","缴费","收入","支出","应收总费用","新旧","挂靠","备注"};
			String values[] = {"","name","idcard","payable","allpaying","allincome","allpayout","count_all","newOrOld","type","licenseTag"};
			row = sheet.createRow(rowNum);
			
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			double countAll[] = new double[heads.length];
			for (int i = 0; i < trainIPOList.size(); i++) {
				row = sheet.createRow(rowNum);
				Map<String, Object> map = trainIPOList.get(i);
				for (int k = 0; k < values.length; k++) {
					cell = row.createCell(k);
					if (map.get(values[k]) != null) {
						if (k > 2 && k < 8) {
							double value = (Double) map.get(values[k]);
							cell.setCellValue(value);
							countAll[k] += value;
						} else {
							cell.setCellValue((String) map.get(values[k]));
						}
					} else if (k == 0) {
						cell.setCellValue(i + 1);
					}
					if (k > 2 && k < 8) {
						cell.setCellStyle(rightStyle);
					} else {
						cell.setCellStyle(leftStyle);
					}
					
				}
				
				rowNum++;
			}
			// 合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			for (int i = 3; i < 8 ; i++) {
				cell = row.createCell(i);
				cell.setCellValue(countAll[i]);
				cell.setCellStyle(rightStyle);
			}
			
		}
		
		//缴费
		List<Map<String,Object>> payList = this.payingService.queryByParam(queryMap);
		if(payList!=null&&payList.size()>0){
			rowNum = 0;
			sheet = workbook.createSheet("缴费");
			String heads[] = {"学员","身份证","缴费","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
			String values[] = {"name","idcard","paying","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
			row = sheet.createRow(rowNum);
			double p = 0 ;
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			for(int i = 0 ; i < payList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = payList.get(i);
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k==2){
							p += (Double)map.get(values[k]);
							cell.setCellValue((Double)map.get(values[k]));
						}else if(k==4||k==5||k==7||k==8){
							if(k==4||k==7)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "yyyy-MM-dd"));
							if(k==5||k==8)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "HH:mm"));
						}else{
							cell.setCellValue((String)map.get(values[k]));
						}
					}
					if (k ==2 ) {
						cell.setCellStyle(rightStyle);
					} else {
						cell.setCellStyle(leftStyle);
					}
				}
				rowNum++;
			}
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			cell = row.createCell(2);
			cell.setCellValue(p);
			cell.setCellStyle(rightStyle);
			rowNum++;
		}
		//收入
		List<Map<String,Object>> incomeList = this.incomeService.queryByParam(queryMap);
		if(incomeList!=null&&incomeList.size()>0){
			sheet = workbook.createSheet("收入");
			rowNum = 0;
			double iv = 0;
			String heads[] = {"学员","身份证","收入项目","金额","备注","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
			String values[] = {"name","idcard","type","income","note","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
			row = sheet.createRow(rowNum);
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			for(int i = 0 ; i < incomeList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = incomeList.get(i);
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k==3){
							iv+=(Double)map.get(values[k]);
							cell.setCellValue((Double)map.get(values[k]));
						}else if(k==6||k==7||k==9||k==10){
							if(k==6||k==9)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "yyyy-MM-dd"));
							if(k==7||k==10)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "HH:mm"));
						}else {
							String v = (String)map.get(values[k]);
							if(k==2&&v.equals("其它")){
								v = "其它("+map.get("otherType")+")";
							}
							cell.setCellValue(v);
						}
					}
					if (k ==3 ) {
						cell.setCellStyle(rightStyle);
					} else {
						cell.setCellStyle(leftStyle);
					}
				}
				rowNum++;
			}
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			cell = row.createCell(3);
			cell.setCellValue(iv);
			cell.setCellStyle(rightStyle);
			rowNum++;
		}
		
		//公共收入
		List<Map<String,Object>> incomeCommonList = this.incomeCommonService.queryByParam(queryMap);
		if(incomeCommonList!=null&&incomeCommonList.size()>0){
			sheet = workbook.createSheet("公共收入");
			rowNum = 0;
			double iv = 0;
			String heads[] = {"收入项目","金额","备注","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
			String values[] = {"type","income","note","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
			row = sheet.createRow(rowNum);
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			for(int i = 0 ; i < incomeCommonList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = incomeCommonList.get(i);
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k==1){
							iv+=(Double)map.get(values[k]);
							cell.setCellValue((Double)map.get(values[k]));
						}else if(k==4||k==5||k==7||k==8){
							
							if(k==4||k==7)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "yyyy-MM-dd"));
							if(k==5||k==8)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "HH:mm"));
						}else {
							String v = (String)map.get(values[k]);
							if(k==0&&v.equals("其它")){
								v = "其它("+map.get("otherType")+")";
							}
							cell.setCellValue(v);
						}
					}
					if (k ==1 ) {
						cell.setCellStyle(rightStyle);
					} else {
						cell.setCellStyle(leftStyle);
					}
				}
				rowNum++;
			}
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			cell = row.createCell(1);
			cell.setCellValue(iv);
			cell.setCellStyle(rightStyle);
		}
		//支出
		List<Map<String,Object>> payoutList = this.payoutService.queryByParam(queryMap);
		if(payoutList!=null&&payoutList.size()>0){
			sheet = workbook.createSheet("支出");
			rowNum = 0;
			String heads[] = {"学员","身份证","支出项目","金额","备注","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
			String values[] = {"name","idcard","type","payout","note","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
			row = sheet.createRow(rowNum);
			double pv = 0;
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			for(int i = 0 ; i < payoutList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = payoutList.get(i);
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k==3){
							pv += (Double)map.get(values[k]);
							cell.setCellValue((Double)map.get(values[k]));
						}else if(k==6||k==7||k==9||k==10){
							
							if(k==6||k==9)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "yyyy-MM-dd"));
							if(k==7||k==10)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "HH:mm"));
						}else {
							String v = (String)map.get(values[k]);
							if(k==2&&v.equals("其它")){
								v = "其它("+map.get("otherType")+")";
							}
							cell.setCellValue(v);
						}
					}
					if (k ==3 ) {
						cell.setCellStyle(rightStyle);
					} else {
						cell.setCellStyle(leftStyle);
					}
				}
				rowNum++;
			}
			
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			cell = row.createCell(3);
			cell.setCellValue(pv);
			cell.setCellStyle(rightStyle);
		}
		
		//公共支出
		List<Map<String,Object>> payoutCommonList = this.payoutCommonService.queryByParam(queryMap);
		if(payoutCommonList!=null&&payoutCommonList.size()>0){
			sheet = workbook.createSheet("公共支出");
			rowNum = 0;
			String heads[] = {"支出项目","金额","备注","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
			String values[] = {"type","payout","note","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
			row = sheet.createRow(rowNum);
			double pv = 0;
			for(int i = 0 ; i < heads.length;i++ ){
				cell = row.createCell(i);
				cell.setCellValue(heads[i]);
				cell.setCellStyle(headcell);
				sheet.setColumnWidth(i, 4500);
			}
			rowNum++;
			for(int i = 0 ; i < payoutCommonList.size();i++){
				row = sheet.createRow(rowNum);
				Map<String,Object> map = payoutCommonList.get(i);
				for(int k = 0 ; k < values.length;k++){
					cell = row.createCell(k);
					if(map.get(values[k])!=null){
						if(k==1){
							pv += (Double)map.get(values[k]);
							cell.setCellValue((Double)map.get(values[k]));
						}else if(k==4||k==5||k==7||k==8){
							
							if(k==4||k==7)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "yyyy-MM-dd"));
							if(k==5||k==8)
								cell.setCellValue(DateUtil.DateToString((Date)map.get(values[k]), "HH:mm"));
						}else {
							String v = (String)map.get(values[k]);
							if(k==0&&v.equals("其它")){
								v = "其它("+map.get("otherType")+")";
							}
							cell.setCellValue(v);
						}
					}
					if (k ==1 ) {
						cell.setCellStyle(rightStyle);
					} else {
						cell.setCellStyle(leftStyle);
					}
				}
				rowNum++;
			}
			//合计
			row = sheet.createRow(rowNum);
			cell = row.createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headcell);
			cell = row.createCell(1);
			cell.setCellValue(pv);
			cell.setCellStyle(rightStyle);
		}
		
		return workbook;
	}
	
	public HSSFWorkbook payoutCommonReport(HSSFWorkbook workbook,
			Map<String, Object> queryMap) {
		HSSFSheet sheet = workbook.createSheet("new sheet");
		HSSFCellStyle headcell = ExcelUtil.headCell(workbook);
		String heads[] = {"项目","金额","备注","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
		String values[] = {"type","payout","note","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < heads.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headcell);
			sheet.setColumnWidth(i, 4500);
		}
		String end = (String)queryMap.get("endTime");
		Date endDate = DateUtil.stringToDate(end, "yyyy-MM-dd");
		if(endDate!=null){
			//时间加1天
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
			end = DateUtil.DateToString(endDate, "yyyy-MM-dd");
			queryMap.put("endTime", end);
		}
		List<Map<String,Object>> list  = this.payoutCommonService.queryByParam(queryMap);
		if(list!=null&&list.size()>0){
			Double payouts = 0d;
			for(int i = 0 ; i < list.size();i++){
				row = sheet.createRow(i+1);
				Map<String,Object> map = list.get(i);
				for(int n = 0 ; n < values.length;n++){
					HSSFCell cell = row.createCell(n);
					String value = "";
					if(n==0){
						value =(String) map.get(values[n]);
						if(value!=null&&value.equals("其它")){
							value = value +"("+map.get("otherType")+")";
						}
						cell.setCellValue(value);
					}else if(n==4||n==5||n==7||n==8){//日期
						Date date = (Date)map.get(values[n]);
						//value = DateUtil.DateToString(date, "yyyy-MM-dd HH:mm");
						if(n==4||n==7){
							value = DateUtil.DateToString(date, "yyyy-MM-dd");
						}else if(n==5||n==8){
							value = DateUtil.DateToString(date, "HH:mm");
						}
						cell.setCellValue(value);
					}else if(n==1&&map.get(values[n])!=null){
						Double v =(Double) map.get(values[n]);
						payouts +=v;
						cell.setCellValue(v);
					}else{
						value =(String) map.get(values[n]);
						cell.setCellValue(value);
					}
				}
			}
			row = sheet.createRow(list.size()+1);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("合计:");
			cell.setCellStyle(headcell);
			cell = row.createCell(1);
			cell.setCellValue(payouts);
		}
		return workbook;
	}

	@Override
	public HSSFWorkbook incomeCommonReport(HSSFWorkbook workbook,
			Map<String, Object> queryMap) {
		HSSFSheet sheet = workbook.createSheet("new sheet");
		HSSFCellStyle headcell = ExcelUtil.headCell(workbook);
		String heads[] = {"项目","金额","备注","操作人","操作日期","操作时刻","审核人","审核日期","审核时刻"};
		String values[] = {"type","income","note","createUserName","createTime","createTime","auditUserName","auditTime","auditTime"};
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < heads.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headcell);
			sheet.setColumnWidth(i, 4500);
		}
		String end = (String)queryMap.get("endTime");
		Date endDate = DateUtil.stringToDate(end, "yyyy-MM-dd");
		if(endDate!=null){
			//时间加1天
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
			end = DateUtil.DateToString(endDate, "yyyy-MM-dd");
			queryMap.put("endTime", end);
		}
		List<Map<String,Object>> list  = this.incomeCommonService.queryByParam(queryMap);
		if(list!=null&&list.size()>0){
			Double payouts = 0d;
			for(int i = 0 ; i < list.size();i++){
				row = sheet.createRow(i+1);
				Map<String,Object> map = list.get(i);
				for(int n = 0 ; n < values.length;n++){
					HSSFCell cell = row.createCell(n);
					String value = "";
					if(n==0){
						value =(String) map.get(values[n]);
						if(value!=null&&value.equals("其它")){
							value = value +"("+map.get("otherType")+")";
						}
						cell.setCellValue(value);
					}else if(n==4||n==5||n==7||n==8){//日期
						Date date = (Date)map.get(values[n]);
						//value = DateUtil.DateToString(date, "yyyy-MM-dd HH:mm");
						if(n==4||n==7){
							value = DateUtil.DateToString(date, "yyyy-MM-dd");
						}else if(n==5||n==8){
							value = DateUtil.DateToString(date, "HH:mm");
						}
						cell.setCellValue(value);
					}else if(n==1&&map.get(values[n])!=null){
						Double v =(Double) map.get(values[n]);
						payouts +=v;
						cell.setCellValue(v);
					}else{
						value =(String) map.get(values[n]);
						cell.setCellValue(value);
					}
				}
			}
			row = sheet.createRow(list.size()+1);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("合计:");
			cell.setCellStyle(headcell);
			cell = row.createCell(1);
			cell.setCellValue(payouts);
		}
		return workbook;
	}

	@Override
	public List<Map<String, Object>> queryTrainIncomePayout(
			Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.*,i.allincome,p.allpaying,po.allpayout, case when i.allincome is null then t.payable else t.payable+i.allincome end count_all FROM tb_train t ")
		.append("LEFT JOIN trainincomeview i ON t.id = i.trainid ")
		.append("LEFT JOIN trainpayingview p ON t.id = p.trainid ")
		.append("LEFT JOIN trainpayoutview po ON t.id = po.trainid ")
		.append("where 1=1 ");
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and t.createTime >= '").append(queryMap.get("beginTime")).append("'");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and t.createTime < '").append(queryMap.get("endTime")).append("'");
		}
		return this.dao.queryForList(sql.toString());
	}

	@Override
	public List<Map<String, Object>> queryCountByTime(
			Map<String, Object> queryMap) {
		StringBuffer sql = new StringBuffer();
		//查询会出现所有的日期
		String dateType = "'%Y-%m-%d'";
		if(queryMap.get("queryType")!=null&&queryMap.get("queryType").equals("year")){
			dateType = "'%Y'";
		}else if(queryMap.get("queryType")!=null&&queryMap.get("queryType").equals("month")){
			dateType = "'%Y-%m'";
		}
		sql.append("select a.* FROM (SELECT DISTINCT DATE_FORMAT(t.createtime,"+dateType+") audittime FROM tb_train t where 1=1 ");
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and t.createTime >= '").append(queryMap.get("beginTime")).append("' ");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and t.createTime < '").append(queryMap.get("endTime")).append("' ");
		}
		sql.append("GROUP BY DATE_FORMAT(createtime,"+dateType+") ");
		sql.append(" UNION ");
		sql.append("SELECT DISTINCT DATE_FORMAT(a.audittime,"+dateType+") audittime FROM audittimeview a where 1=1 ");
		if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
			sql.append(" and a.audittime >= '").append(queryMap.get("beginTime")).append("' ");
		}
		if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
			sql.append(" and a.audittime < '").append(queryMap.get("endTime")).append("' ");
		}
		sql.append("GROUP BY DATE_FORMAT(audittime,"+dateType+") ) a order by audittime ");
		//
		List<Map<String,Object>> dateList = dao.queryForList(sql.toString());
		Map<String,Object> payableMap = null;
		Map<String,Object> payingMap = null;
		Map<String,Object> incomeMap = null;
		Map<String,Object> incomeCommonMap = null;
		Map<String,Object> payoutMap = null;
		Map<String,Object> payoutCommonMap = null;
		if(dateList!=null&&dateList.size()>0){
			//应交学费
			sql = new StringBuffer();
			sql.append(" SELECT DATE_FORMAT(createtime,"+dateType+") audittime ,SUM(payable) countValue FROM tb_train where 1=1 ");
			if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
				sql.append(" and createtime >= '").append(queryMap.get("beginTime")).append("' ");
			}
			if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
				sql.append(" and createtime < '").append(queryMap.get("endTime")).append("' ");
			}
			sql.append("GROUP BY DATE_FORMAT(createtime,"+dateType+") ");
			List<Map<String,Object>> payableList = dao.queryForList(sql.toString());
			payableMap = this.converMap(payableList);
			//缴费
			sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(audittime,"+dateType+") audittime ,SUM(paying) countValue FROM tb_paying ")
			.append("WHERE STATUS = 1 ");
			if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
				sql.append(" and audittime >= '").append(queryMap.get("beginTime")).append("' ");
			}
			if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
				sql.append(" and audittime < '").append(queryMap.get("endTime")).append("' ");
			}
			sql.append("GROUP BY DATE_FORMAT(audittime,"+dateType+") ");
			List<Map<String,Object>> payingList = dao.queryForList(sql.toString());
			payingMap = this.converMap(payingList);
			//收入
			sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(audittime,"+dateType+") audittime ,SUM(income) countValue FROM tb_income ")
			.append("WHERE STATUS = 1 ");
			if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
				sql.append(" and audittime >= '").append(queryMap.get("beginTime")).append("' ");
			}
			if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
				sql.append(" and audittime < '").append(queryMap.get("endTime")).append("' ");
			}
			sql.append("GROUP BY DATE_FORMAT(audittime,"+dateType+") ");
			List<Map<String,Object>> incomeList = dao.queryForList(sql.toString());
			incomeMap = this.converMap(incomeList);
			//公共收入
			sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(audittime,"+dateType+") audittime ,SUM(income) countValue FROM tb_incomeCommon ")
			.append("WHERE STATUS = 1 ");
			if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
				sql.append(" and audittime >= '").append(queryMap.get("beginTime")).append("' ");
			}
			if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
				sql.append(" and audittime < '").append(queryMap.get("endTime")).append("' ");
			}
			sql.append("GROUP BY DATE_FORMAT(audittime,"+dateType+") ");
			List<Map<String,Object>> incomeCommonList = dao.queryForList(sql.toString());
			incomeCommonMap = this.converMap(incomeCommonList);
			//支出
			sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(audittime,"+dateType+") audittime ,SUM(payout) countValue FROM tb_payout ")
			.append("WHERE STATUS = 1 ");
			if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
				sql.append(" and audittime >= '").append(queryMap.get("beginTime")).append("' ");
			}
			if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
				sql.append(" and audittime < '").append(queryMap.get("endTime")).append("' ");
			}
			sql.append("GROUP BY DATE_FORMAT(audittime,"+dateType+") ");
			List<Map<String,Object>> payoutList = dao.queryForList(sql.toString());
			payoutMap = this.converMap(payoutList);
			//公共支出
			sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(audittime,"+dateType+") audittime ,SUM(payout) countValue FROM tb_payoutCommon ")
			.append("WHERE STATUS = 1 ");
			if(queryMap.get("beginTime")!=null&&!queryMap.get("beginTime").toString().equals("")){
				sql.append(" and audittime >= '").append(queryMap.get("beginTime")).append("' ");
			}
			if(queryMap.get("endTime")!=null&&!queryMap.get("endTime").toString().equals("")){
				sql.append(" and audittime < '").append(queryMap.get("endTime")).append("' ");
			}
			sql.append("GROUP BY DATE_FORMAT(audittime,"+dateType+") ");
			List<Map<String,Object>> payoutCommonList = dao.queryForList(sql.toString());
			payoutCommonMap = this.converMap(payoutCommonList);
			//加入dateList 中
			for(Map<String,Object> d: dateList){
				String auditTime = (String)d.get("audittime");
				d.put("payable", payableMap.get(auditTime));
				d.put("paying", payingMap.get(auditTime));
				d.put("income", incomeMap.get(auditTime));
				d.put("incomeCommon", incomeCommonMap.get(auditTime));
				d.put("payout", payoutMap.get(auditTime));
				d.put("payoutCommon", payoutCommonMap.get(auditTime));
				Double payable = payableMap.get(auditTime) == null ? 0 : (Double)payableMap.get(auditTime);
				Double paying = payingMap.get(auditTime) == null ? 0 : (Double)payingMap.get(auditTime);
				double canPay = payable - paying;
				d.put("canPay",canPay);
			}
			return dateList;
		}
		return null;
	}
	
	private Map<String,Object> converMap(List<Map<String,Object>> list){
		Map<String,Object> map = new HashMap<String,Object>(); 
		if(list!=null&&list.size()>0){
			for(Map<String,Object> m : list){
				map.put(m.get("audittime").toString(), m.get("countValue"));
			}
		}
		return map;
	}
}
