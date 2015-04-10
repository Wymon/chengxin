package xx.tream.chengxin.ms.task;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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

import xx.tream.basepaltform.service.UserService;
import xx.tream.chengxin.ms.service.ReportParamService;
import xx.tream.chengxin.ms.service.ReportService;
import xx.tream.chengxin.ms.service.TrainService;
import xx.tream.chengxin.ms.util.DateUtil;
import xx.tream.chengxin.ms.util.EmailUtil2;
import xx.tream.chengxin.ms.util.ExcelUtil;
import xx.tream.chengxin.ms.util.ParamUtil;

public class StatisticsTask {

	@Autowired
	private TrainService trainService;
	@Autowired
	private ReportParamService reportParamService;
	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;
	private String root = System.getProperty("webRoot");

	private String path = this.root +File.separator+ "temp";

	public void service() {
		sendEmail();
	}

	public void sendEmail() {
		Long start = Long.valueOf(System.currentTimeMillis());
		System.out.println("发邮件执行开始......");

		String filePath = writeExcel();
		if (filePath != null) {
			List<Map<String, Object>> list = this.userService.queryBySendMail();
			if ((list != null) && (list.size() > 0)) {
				EmailUtil2 email = new EmailUtil2();
				List<String> fileList = new ArrayList<String>();
				List<String> fileNameList = new ArrayList<String>();
				fileList.add(filePath);
				fileNameList.add("诚信驾校收入支出统计表.xls");
				email.setFileList(fileList);
				email.setFileNameList(fileNameList);
				//收支明细
				String file = this.writeExcelDetail();
				fileList.add(file);
				fileNameList.add("诚信驾校收入支出明细表.xls");
				for (Map<String, Object> m : list) {
					try {
						email.simpleSend((String) m.get("email"), "诚信驾校收入支出报表",
								"您好！诚信驾校的每天收入支出报表已统计好，详见附件！");
						Thread.sleep(5000L);
					} catch (Exception e) {
						System.out.println(m.get("name") + "的邮箱:"
								+ m.get("email") + "没有发送成功.....");
						e.printStackTrace();
					}
				}
			}
		}
		Long end = Long.valueOf(System.currentTimeMillis());
		System.out.println("发邮件执行结束......");
		System.out.println("用时：" + (end.longValue() - start.longValue()));
	}

	public String writeExcel() {
		String fileName = "";
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		Calendar startYear = Calendar.getInstance();
		startYear.set(startYear.get(Calendar.YEAR), 0, 1);
		Calendar endYear = Calendar.getInstance();
		endYear.set(endYear.get(Calendar.YEAR)+1, 0, 1);
		String startTimeYear = DateUtil.DateToString(startYear.getTime(), "yyyy-MM-dd");
		String endTimeYear = DateUtil.DateToString(endYear.getTime(), "yyyy-MM-dd");
		queryMap.put("beginTime", startTimeYear);
		queryMap.put("endTime", endTimeYear);
		queryMap.put("status", "1");
		
		Date startDate = DateUtil.stringToDate(startTimeYear, "yyyy-MM-dd");
		Date endDate = DateUtil.stringToDate(endTimeYear, "yyyy-MM-dd");;
		//收入  写入标题  公共标题加其它类型的
		String[] incomeItems = ParamUtil.incomeItems;
		String[] otherIncomeItems = reportParamService.queryIncomeOtherItem(startDate, endDate);
		String[] payoutItems = ParamUtil.payoutItems;
		String[] payoutOtherItems = reportParamService.queryPayoutOtherItem(startDate, endDate);
		//公共支出 写入标题  公共标题加其它类型的 
		String[] payoutCommonItems = ParamUtil.payoutCommonItems;
		String[] payoutCommonOtherTypes = reportParamService.queryPayoutCommonOtherItem(startDate, endDate);
		//公共收入
		String[] incomeCommonItems = ParamUtil.incomeCommonItems;
		String[] incomeCommonOtherTypes = reportParamService.queryIncomeCommonOtherItem(startDate, endDate);
		//写入数据
		//写入数据(年份)
		queryMap.put("queryType", "year");
		List<Map<String,Object>> yearList = this.reportParamService.queryIncomeAndPayout(queryMap, incomeItems, otherIncomeItems,incomeCommonItems,incomeCommonOtherTypes, payoutItems, payoutOtherItems, payoutCommonItems, payoutCommonOtherTypes);
		
		//应交学费
		Map<String,Object> payableYearMap = this.reportParamService.queryPayable(queryMap);
		//写入数据(月份)
		Calendar startMonth = Calendar.getInstance();
		startMonth.set(startMonth.get(Calendar.YEAR), startMonth.get(Calendar.MONTH), 1);
		Calendar endMonth = Calendar.getInstance();
		endMonth.set(endMonth.get(Calendar.YEAR), endMonth.get(Calendar.MONTH), endMonth.get(Calendar.DATE)+1);
		String startTimeMonth = DateUtil.DateToString(startMonth.getTime(), "yyyy-MM-dd");
		String endTimeMonth = DateUtil.DateToString(endMonth.getTime(), "yyyy-MM-dd");
		queryMap.put("beginTime", startTimeMonth);
		queryMap.put("endTime", endTimeMonth);
		queryMap.put("queryType", "month");
		List<Map<String,Object>> monthList = this.reportParamService.queryIncomeAndPayout(queryMap, incomeItems, otherIncomeItems,incomeCommonItems,incomeCommonOtherTypes, payoutItems, payoutOtherItems, payoutCommonItems, payoutCommonOtherTypes);
		//应交学费
		Map<String,Object> payableMonthMap = this.reportParamService.queryPayable(queryMap);
		//当天
		Calendar cal = Calendar.getInstance();
		startDate = cal.getTime();
		String startTime = DateUtil.DateToString(cal.getTime(), "yyyy-MM-dd");
		cal.add(Calendar.DATE, 1);
		String endTime = DateUtil.DateToString(cal.getTime(), "yyyy-MM-dd");
		
		queryMap.put("beginTime", startTime);
		queryMap.put("endTime", endTime);
		queryMap.put("queryType", "day");
		List<Map<String,Object>> list = this.reportParamService.queryIncomeAndPayout(queryMap, incomeItems, otherIncomeItems,incomeCommonItems,incomeCommonOtherTypes, payoutItems, payoutOtherItems, payoutCommonItems, payoutCommonOtherTypes);
		//应交学费
		Map<String,Object> payableMap = this.reportParamService.queryPayable(queryMap);
		if ((list != null) && (list.size() > 0)) {
			try {
				fileName = this.path + File.separator
						+ System.currentTimeMillis() + ".xls";
				System.out.println(fileName);
				File f = new File(this.path);
				if (!f.exists()) {
					f.mkdir();
				}
				File file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				Map<String,Object> yearMap = yearList.get(0);
				Map<String,Object> monthMap = monthList.get(0);
				Map<String,Object> map = list.get(0);
				FileOutputStream fos = new FileOutputStream(fileName);
				HSSFWorkbook workBook = new HSSFWorkbook();
				HSSFSheet sheet = workBook.createSheet("财务日报");
				HSSFCellStyle headcell = ExcelUtil.headCell(workBook);
				HSSFCellStyle rowcell = ExcelUtil.rowCell(workBook);
				HSSFCellStyle leftcell = ExcelUtil.leftCell(workBook);
				HSSFCellStyle rightcell = ExcelUtil.rightCell(workBook);
				ExcelUtil.mergeRegion(sheet, 0, 0, 0, 19, "诚信驾校收入和支出统计表", rowcell);
				//第二行开始 第一列和第二列是收入项目和收入值   
				int rowNum = 1 ;
				ExcelUtil.mergeRegion(sheet, 1, 1, 0,3, "应收学费", rowcell);
				ExcelUtil.mergeRegion(sheet, 1, 1, 4,7, "收入", rowcell);
				ExcelUtil.mergeRegion(sheet, 1, 1, 8,11, "公共收入", rowcell);
				ExcelUtil.mergeRegion(sheet, 1, 1, 12,15, "支出", rowcell);
				ExcelUtil.mergeRegion(sheet, 1, 1, 16,19, "公共支出", rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 0,0, "时间", rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 1,1, DateUtil.DateToString(startDate, "yyyy年MM月dd日"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 2,2, DateUtil.DateToString(startDate, "yyyy年MM月"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 3,3, DateUtil.DateToString(startDate, "yyyy年"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 4,4, "时间", rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 5,5, DateUtil.DateToString(startDate, "yyyy年MM月dd日"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 6,6, DateUtil.DateToString(startDate, "yyyy年MM月"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 7,7, DateUtil.DateToString(startDate, "yyyy年"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 8,8, "时间", rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 9,9, DateUtil.DateToString(startDate, "yyyy年MM月dd日"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 10,10, DateUtil.DateToString(startDate, "yyyy年MM月"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 11,11, DateUtil.DateToString(startDate, "yyyy年"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 12,12, "时间", rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 13,13, DateUtil.DateToString(startDate, "yyyy年MM月dd日"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 14,14, DateUtil.DateToString(startDate, "yyyy年MM月"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 15,15, DateUtil.DateToString(startDate, "yyyy年"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 16,16, "时间", rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 17,17, DateUtil.DateToString(startDate, "yyyy年MM月dd日"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 18,18, DateUtil.DateToString(startDate, "yyyy年MM月"), rowcell);
				ExcelUtil.mergeRegion(sheet, 2, 2, 19,19, DateUtil.DateToString(startDate, "yyyy年"), rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 0,0, "项目", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 1,3, "金额", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 4,4, "项目", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 5,7, "金额", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 8,8, "项目", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 9,11, "金额", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 12,12, "项目", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 13,15, "金额", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 16,16, "项目", rowcell);
				ExcelUtil.mergeRegion(sheet, 3, 3, 17,19, "金额", rowcell);
				//设置列宽
				for(int i = 0 ; i < 20;i++){
					sheet.setColumnWidth(i, 5000);
				}
				
				rowNum = 4;
				HSSFRow row = sheet.createRow(rowNum);	
				HSSFCell cell = row.createCell(0);
				cell.setCellValue("应收学费合计：");
				cell.setCellStyle(leftcell);
				cell = row.createCell(1);
				if(payableMap.get("payable")!=null){
					cell.setCellValue((Double)payableMap.get("payable"));
				}else{
					cell.setCellValue("");
				}
				cell.setCellStyle(rightcell);
				cell = row.createCell(2);
				if(payableMonthMap.get("payable")!=null){
					cell.setCellValue((Double)payableMonthMap.get("payable"));
				}else{
					cell.setCellValue("");
				}
				cell.setCellStyle(rightcell);
				cell = row.createCell(3);
				if(payableYearMap.get("payable")!=null){
					cell.setCellValue((Double)payableYearMap.get("payable"));
				}else{
					cell.setCellValue("");
				}
				cell.setCellStyle(rightcell);
				rowNum++;
				//缴费
				row = sheet.createRow(rowNum);	
				cell = row.createCell(0);
				cell.setCellValue("缴费（学费）合计：");
				cell.setCellStyle(leftcell);
				cell = row.createCell(1);
				if(map.get("paying")!=null){
					cell.setCellValue((Double)map.get("paying"));
				}else{
					cell.setCellValue("");
				}
				cell.setCellStyle(rightcell);
				//月份
				cell = row.createCell(2);
				if(monthMap.get("paying")!=null){
					cell.setCellValue((Double)monthMap.get("paying"));
				}else{
					cell.setCellValue("");
				}
				cell.setCellStyle(rightcell);
				//年份
				cell = row.createCell(3);
				if(yearMap.get("paying")!=null){
					cell.setCellValue((Double)yearMap.get("paying"));
				}else{
					cell.setCellValue("");
				}
				cell.setCellStyle(rightcell);
				rowNum++;
				//欠费
				row = sheet.createRow(rowNum);	
				cell = row.createCell(0);
				cell.setCellValue("欠学费合计：");
				cell.setCellStyle(leftcell);
				
				double payable = (Double)payableMap.get("payable") == null ? 0 : (Double)payableMap.get("payable");
				double payableMonth = (Double)payableMonthMap.get("payable") == null ? 0 : (Double)payableMonthMap.get("payable");
				double payableYear = (Double)payableYearMap.get("payable") == null ? 0 : (Double)payableYearMap.get("payable");
				double paying = map.get("paying")==null ? 0 : (Double)map.get("paying");
				double payingMonth = monthMap.get("paying")==null ? 0 : (Double)monthMap.get("paying");
				double payingYear = yearMap.get("paying")==null ? 0 : (Double)yearMap.get("paying");
				double calPay = payable - paying;
				double calPayMonth = payableMonth - payingMonth;
				double calPayYear = payableYear - payingYear;
				//当天
				cell = row.createCell(1);
				cell.setCellValue(calPay);
				cell.setCellStyle(rightcell);
				//月份
				cell = row.createCell(2);
				cell.setCellValue(calPayMonth);
				cell.setCellStyle(rightcell);
				//年份
				cell = row.createCell(3);
				cell.setCellValue(calPayYear);
				cell.setCellStyle(rightcell);
				rowNum++;
//				
				rowNum = 4;
				//收入
				double icome = 0;
				double icomeM = 0;
				double icomeY = 0;
				for (int i = 0; i < incomeItems.length-1; i++) {
					row = sheet.getRow(rowNum);
					if(row==null){
						row = sheet.createRow(rowNum);
					}
					//最后一个其它就不用写入来
					cell = row.createCell(4);
					cell.setCellValue(incomeItems[i]);
					cell.setCellStyle(leftcell);
					cell = row.createCell(5);
					if(map.get("i"+i)!=null){
						cell.setCellValue((Double)map.get("i"+i));
						icome += (Double)map.get("i"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//月份
					cell = row.createCell(6);
					if(monthMap.get("i"+i)!=null){
						cell.setCellValue((Double)monthMap.get("i"+i));
						icomeM += (Double)monthMap.get("i"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//年份
					cell = row.createCell(7);
					if(yearMap.get("i"+i)!=null){
						cell.setCellValue((Double)yearMap.get("i"+i));
						icomeY += (Double)yearMap.get("i"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					rowNum++;
				}
				if(otherIncomeItems!=null&&otherIncomeItems.length>0){
					for (int i = 0; i < otherIncomeItems.length; i++) {
						row = sheet.createRow(rowNum);
						//最后一个其它就不用写入来
						cell = row.createCell(4);
						cell.setCellValue("其它("+otherIncomeItems[i]+")");
						cell.setCellStyle(leftcell);
						cell = row.createCell(5);
						if(map.get("io"+i)!=null){
							cell.setCellValue((Double)map.get("io"+i));
							icome += (Double)map.get("io"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//月份
						cell = row.createCell(6);
						if(monthMap.get("io"+i)!=null){
							cell.setCellValue((Double)monthMap.get("io"+i));
							icomeM += (Double)monthMap.get("io"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//年份
						cell = row.createCell(7);
						if(yearMap.get("io"+i)!=null){
							cell.setCellValue((Double)yearMap.get("io"+i));
							icomeY += (Double)yearMap.get("io"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						rowNum++;
					}
				}
				
				row = sheet.createRow(rowNum);
				cell = row.createCell(4);
				cell.setCellValue("收入合计:");
				cell.setCellStyle(headcell);
				cell = row.createCell(5);
				cell.setCellStyle(rightcell);
				cell.setCellValue(icome);
				cell = row.createCell(6);
				cell.setCellStyle(rightcell);
				cell.setCellValue(icomeM);
				cell = row.createCell(7);
				cell.setCellStyle(rightcell);
				cell.setCellValue(icomeY);
				rowNum++;
				rowNum = 4;
				//公共收入
				double incomeCommon = 0,incomeCommonM = 0,incomeCommonY = 0;
				
				for (int i = 0; i < incomeCommonItems.length-1; i++) {
					row = sheet.getRow(rowNum);
					if(row==null){
						row = sheet.createRow(rowNum);
					}
					cell = row.createCell(8);
					cell.setCellValue(incomeCommonItems[i]);
					cell.setCellStyle(leftcell);
					cell = row.createCell(9);
					if(map.get("ic"+i)!=null){
						cell.setCellValue((Double)map.get("ic"+i));
						incomeCommon += (Double)map.get("ic"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//月份
					cell = row.createCell(10);
					if(monthMap.get("ic"+i)!=null){
						cell.setCellValue((Double)monthMap.get("ic"+i));
						incomeCommonM += (Double)monthMap.get("ic"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//年份
					cell = row.createCell(11);
					if(yearMap.get("ic"+i)!=null){
						cell.setCellValue((Double)yearMap.get("ic"+i));
						incomeCommonY += (Double)yearMap.get("ic"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					rowNum++;
				}
				if(incomeCommonOtherTypes!=null&&incomeCommonOtherTypes.length>0){
					for (int i = 0; i < incomeCommonOtherTypes.length; i++) {
						row = sheet.getRow(rowNum);
						if(row==null){
							row = sheet.createRow(rowNum);
						}
						cell = row.createCell(8);
						cell.setCellValue("其它("+incomeCommonOtherTypes[i]+")");
						cell.setCellStyle(leftcell);
						cell = row.createCell(9);
						if(map.get("ico"+i)!=null){
							cell.setCellValue((Double)map.get("ico"+i));
							incomeCommon += (Double)map.get("ico"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//月份
						cell = row.createCell(10);
						if(monthMap.get("ico"+i)!=null){
							cell.setCellValue((Double)monthMap.get("ico"+i));
							incomeCommonM += (Double)monthMap.get("ico"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//年份
						cell = row.createCell(11);
						if(yearMap.get("ico"+i)!=null){
							cell.setCellValue((Double)yearMap.get("ico"+i));
							incomeCommonY += (Double)yearMap.get("ico"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						rowNum++;
					}
				}
				
				row = sheet.getRow(rowNum);
				if(row==null){
					row = sheet.createRow(rowNum);
				}
				cell = row.createCell(8);
				cell.setCellValue("公共收入合计:");
				cell.setCellStyle(headcell);
				cell = row.createCell(9);
				cell.setCellValue(incomeCommon);
				cell.setCellStyle(rightcell);
				cell = row.createCell(10);
				cell.setCellValue(incomeCommonM);
				cell.setCellStyle(rightcell);
				cell = row.createCell(11);
				cell.setCellValue(incomeCommonY);
				cell.setCellStyle(rightcell);
				rowNum++;
				row = sheet.getRow(rowNum);
				if(row==null){
					row = sheet.createRow(rowNum);
				}
				
				//支出
				rowNum = 4;
				double payout = 0,payoutM = 0 ,payoutY = 0;
				for (int i = 0; i < payoutItems.length-1; i++) {
					row = sheet.getRow(rowNum);
					if(row==null){
						row = sheet.createRow(rowNum);
					}
					//最后一个其它就不用写入来
					cell = row.createCell(12);
					cell.setCellValue(payoutItems[i]);
					cell.setCellStyle(leftcell);
					cell = row.createCell(13);
					if(map.get("p"+i)!=null){
						cell.setCellValue((Double)map.get("p"+i));
						payout += (Double)map.get("p"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//月份
					cell = row.createCell(14);
					if(monthMap.get("p"+i)!=null){
						cell.setCellValue((Double)monthMap.get("p"+i));
						payoutM += (Double)monthMap.get("p"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//年份
					cell = row.createCell(15);
					if(yearMap.get("p"+i)!=null){
						cell.setCellValue((Double)yearMap.get("p"+i));
						payoutY += (Double)yearMap.get("p"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					rowNum++;
				}
				if(payoutOtherItems!=null&&payoutOtherItems.length>0){
					for (int i = 0; i < payoutOtherItems.length; i++) {
						row = sheet.getRow(rowNum);
						if(row==null){
							row = sheet.createRow(rowNum);
						}
						cell = row.createCell(12);
						cell.setCellValue("其它("+payoutOtherItems[i]+")");
						cell.setCellStyle(rowcell);
						cell = row.createCell(13);
						if(map.get("po"+i)!=null){
							cell.setCellValue((Double)map.get("po"+i));
							payout += (Double)map.get("po"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//月份
						cell = row.createCell(14);
						if(monthMap.get("po"+i)!=null){
							cell.setCellValue((Double)monthMap.get("po"+i));
							payoutM += (Double)monthMap.get("po"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//年份
						cell = row.createCell(15);
						if(yearMap.get("po"+i)!=null){
							cell.setCellValue((Double)yearMap.get("po"+i));
							payoutY += (Double)yearMap.get("po"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						rowNum++;
					}
				}
				
				row = sheet.getRow(rowNum);
				if(row==null){
					row = sheet.createRow(rowNum);
				}
				cell = row.createCell(12);
				cell.setCellValue("支出合计:");
				cell.setCellStyle(headcell);
				cell = row.createCell(13);
				cell.setCellValue(payout);
				cell.setCellStyle(rightcell);
				cell = row.createCell(14);
				cell.setCellValue(payoutM);
				cell.setCellStyle(rightcell);
				cell = row.createCell(15);
				cell.setCellValue(payoutY);
				cell.setCellStyle(rightcell);
				rowNum++;
				rowNum = 4;
				//公共支出
				double payoutCommon = 0,payoutCommonM = 0,payoutCommonY = 0;
				
				for (int i = 0; i < payoutCommonItems.length-1; i++) {
					row = sheet.getRow(rowNum);
					if(row==null){
						row = sheet.createRow(rowNum);
					}
					cell = row.createCell(16);
					cell.setCellValue(payoutCommonItems[i]);
					cell.setCellStyle(leftcell);
					cell = row.createCell(17);
					if(map.get("pc"+i)!=null){
						cell.setCellValue((Double)map.get("pc"+i));
						payoutCommon += (Double)map.get("pc"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//月份
					cell = row.createCell(18);
					if(monthMap.get("pc"+i)!=null){
						cell.setCellValue((Double)monthMap.get("pc"+i));
						payoutCommonM += (Double)monthMap.get("pc"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					//年份
					cell = row.createCell(19);
					if(yearMap.get("pc"+i)!=null){
						cell.setCellValue((Double)yearMap.get("pc"+i));
						payoutCommonY += (Double)yearMap.get("pc"+i);
					}else{
						cell.setCellValue("");
					}
					cell.setCellStyle(rightcell);
					
					rowNum++;
				}
				if(payoutCommonOtherTypes!=null&&payoutCommonOtherTypes.length>0){
					for (int i = 0; i < payoutCommonOtherTypes.length; i++) {
						row = sheet.getRow(rowNum);
						if(row==null){
							row = sheet.createRow(rowNum);
						}
						cell = row.createCell(16);
						cell.setCellValue("其它("+payoutCommonOtherTypes[i]+")");
						cell.setCellStyle(leftcell);
						cell = row.createCell(17);
						if(map.get("pco"+i)!=null){
							cell.setCellValue((Double)map.get("pco"+i));
							payoutCommon += (Double)map.get("pco"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//月份
						cell = row.createCell(18);
						if(monthMap.get("pco"+i)!=null){
							cell.setCellValue((Double)monthMap.get("pco"+i));
							payoutCommonM += (Double)monthMap.get("pco"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						//年份
						cell = row.createCell(19);
						if(yearMap.get("pco"+i)!=null){
							cell.setCellValue((Double)yearMap.get("pco"+i));
							payoutCommonY += (Double)yearMap.get("pco"+i);
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(rightcell);
						rowNum++;
					}
				}
				
				row = sheet.getRow(rowNum);
				if(row==null){
					row = sheet.createRow(rowNum);
				}
				cell = row.createCell(16);
				cell.setCellValue("公共支出合计:");
				cell.setCellStyle(headcell);
				cell = row.createCell(17);
				cell.setCellValue(payoutCommon);
				cell.setCellStyle(rightcell);
				cell = row.createCell(18);
				cell.setCellValue(payoutCommonM);
				cell.setCellStyle(rightcell);
				cell = row.createCell(19);
				cell.setCellValue(payoutCommonY);
				cell.setCellStyle(rightcell);
				rowNum++;
				//个人公共收支合计
				double ipd = paying+icome+incomeCommon-payout-payoutCommon;
				double ipm = payingMonth+icomeM+incomeCommonM-payoutM-payoutCommonM;
				double ipy = payingYear+icomeY+incomeCommonY-payoutY-payoutCommonY;
				rowNum = 7;
				HSSFCellStyle rightRed = ExcelUtil.rightFontRedCell(workBook);
				row = sheet.getRow(rowNum);
				if(row==null){
					row = sheet.createRow(rowNum);
				}	
				cell = row.createCell(0);
				cell.setCellValue("个人公共收支合计：");
				cell.setCellStyle(leftcell);
				//当天
				cell = row.createCell(1);
				cell.setCellValue(ipd);
				if(ipd>0){
					cell.setCellStyle(rightcell);
				}else{
					cell.setCellStyle(rightRed);
				}
				
				//月份
				cell = row.createCell(2);
				cell.setCellValue(ipm);
				if(ipm>0){
					cell.setCellStyle(rightcell);
				}else{
					cell.setCellStyle(rightRed);
				}
				//年份
				cell = row.createCell(3);
				cell.setCellValue(ipy);
				if(ipy>0){
					cell.setCellStyle(rightcell);
				}else{
					cell.setCellStyle(rightRed);
				}
				//收支统计表 当年累计
				queryMap.put("beginTime", startTimeYear);
				queryMap.put("endTime", endTimeYear);
				List<Map<String,Object>> countList = this.reportService.queryCountByTime(queryMap);
				if(countList!=null&&countList.size()>0){
					rowNum = 0;
					sheet = workBook.createSheet("收支统计表");
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
						Map<String,Object> mapC = countList.get(i);
						for(int k = 0 ; k < values.length;k++){
							cell = row.createCell(k);
							if(mapC.get(values[k])!=null){
								if(k>0){
									double value = (Double)mapC.get(values[k]);
									cell.setCellValue(value);
									cell.setCellStyle(rightcell);
									countAll[k] += value;
								}else{
									cell.setCellValue((String)mapC.get(values[k]));
									cell.setCellStyle(rowcell);
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
						cell.setCellStyle(rightcell);
					}
					rowNum++;
				}
				workBook.write(fos);
				fos.close();
				return fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String writeExcelDetail(){
		String fileName = "";
		Calendar cal = Calendar.getInstance();
		String startTime = DateUtil.DateToString(cal.getTime(), "yyyy-MM-dd");
		cal.add(Calendar.DATE, 1);
		String endTime = DateUtil.DateToString(cal.getTime(), "yyyy-MM-dd");
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("beginTime", startTime);
		queryMap.put("endTime", endTime);
		queryMap.put("status", "1");
		try {
			fileName = this.path + File.separator
					+ System.currentTimeMillis() + ".xls";
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdir();
			}
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println(fileName);
			FileOutputStream fos = new FileOutputStream(fileName);
			HSSFWorkbook workBook = new HSSFWorkbook();
			this.reportService.financialStatementsDetail(workBook, queryMap);
			workBook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileName;
	} 
	
}