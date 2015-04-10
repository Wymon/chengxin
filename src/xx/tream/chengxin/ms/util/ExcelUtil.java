package xx.tream.chengxin.ms.util;

import java.util.Calendar;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {
	public static int mergeRegion(HSSFSheet sheet, int firstRow, int lastRow,
			int firstCol, int lastCol, Object value, HSSFCellStyle cellStyle) {
		int index = sheet.addMergedRegion(new CellRangeAddress(firstRow,
				lastRow, firstCol, lastCol));
		for(int i =firstRow;i <= lastRow;i++ ){
			for(int j = firstCol;j <=lastCol ;j++){
				HSSFCell cell = cell(sheet, i, j);
				cell.setCellStyle(cellStyle);
			}
		}
		setValue(sheet, firstRow, firstCol, value, cellStyle);
		return index;
	}

	public static void setValue(HSSFSheet sheet, int rowIndex, int colIndex,
			Object value, HSSFCellStyle cellStyle) {
		HSSFCell cell = cell(sheet, rowIndex, colIndex);
		setValue(cell, value);
		cell.setCellStyle(cellStyle);
	}

	public static HSSFCell cell(HSSFSheet sheet, int rowIndex, int index) {
		HSSFRow row = row(sheet, rowIndex);
		return cell(row, index);
	}

	private static HSSFRow row(HSSFSheet sheet, int index) {
		HSSFRow row = sheet.getRow(index);
		if (row == null)
			row = sheet.createRow(index);
		return row;
	}

	private static HSSFCell cell(HSSFRow row, int index) {
		HSSFCell cell = row.getCell(index);
		if (cell == null) {
			cell = row.createCell(index);
		}

		return cell;
	}

	public static void setValue(HSSFCell cell, Object value) {
		if (value == null)
			return;
		if ((value instanceof Integer)) {
			if (!value.toString().trim().equals("0"))
				cell.setCellValue(Integer.valueOf(value.toString()).intValue());
		} else if ((value instanceof Long)) {
			if (!value.toString().trim().equals("0"))
				cell.setCellValue(Long.valueOf(value.toString()).longValue());
		} else if ((value instanceof Float))
			cell.setCellValue(Float.valueOf(value.toString()).floatValue());
		else if ((value instanceof Double))
			cell.setCellValue(Double.valueOf(value.toString()).doubleValue());
		else if ((value instanceof Short)) {
			if (!value.toString().trim().equals("0"))
				cell.setCellValue(Short.valueOf(value.toString()).shortValue());
		} else if ((value instanceof Byte))
			cell.setCellValue(Byte.valueOf(value.toString()).byteValue());
		else if ((value instanceof Boolean))
			cell.setCellValue(Boolean.valueOf(value.toString()).booleanValue());
		else if ((value instanceof Date)) {
			cell.setCellValue((Date) value);
		} else if ((value instanceof Calendar))
			cell.setCellValue((Calendar) value);
		else
			cell.setCellValue(value.toString());
	}

	public static HSSFCellStyle headCell(HSSFWorkbook workbook) {
		HSSFCellStyle headcell = workbook.createCellStyle();
		headcell.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		headcell.setFillPattern((short) 1);
		headcell.setBorderBottom((short) 1);
		headcell.setBorderLeft((short) 1);
		headcell.setBorderTop((short) 1);
		headcell.setBorderRight((short) 1);
		headcell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		headcell.setFont(cellFont);
		
		return headcell;
	}
	/**
	 * 左边加背景
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle paleBlueForgroundCell(HSSFWorkbook workbook) {
		HSSFCellStyle headcell = workbook.createCellStyle();
		headcell.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headcell.setFillPattern((short) 1);
		headcell.setBorderBottom((short) 1);
		headcell.setBorderLeft((short) 1);
		headcell.setBorderTop((short) 1);
		headcell.setBorderRight((short) 1);
		headcell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		headcell.setFont(cellFont);
		return headcell;
	}
	
	/**
	 * 右边加背景
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle rightPaleBlueForgroundCell(HSSFWorkbook workbook) {
		HSSFCellStyle headcell = workbook.createCellStyle();
		headcell.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headcell.setFillPattern((short) 1);
		headcell.setBorderBottom((short) 1);
		headcell.setBorderLeft((short) 1);
		headcell.setBorderTop((short) 1);
		headcell.setBorderRight((short) 1);
		headcell.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		headcell.setFont(cellFont);
		return headcell;
	}
	/**
	 * 左边
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle rowCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		rowCell.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 居中
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle centerCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 左边
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle leftCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 右边
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle rightCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 左边 红色字体
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle leftFontRedCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setColor(IndexedColors.RED.index);
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 左边红色字体加背景色
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle leftFontRedForegroudCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setColor(IndexedColors.RED.index);
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 右边 红色字体
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle rightFontRedCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setColor(IndexedColors.RED.index);
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
	/**
	 * 右边红色字体加背景色
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle rightFontRedForegroudCell(HSSFWorkbook workbook) {
		HSSFCellStyle rowCell = workbook.createCellStyle();
		rowCell.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		rowCell.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		rowCell.setFillPattern((short) 1);
		rowCell.setBorderBottom((short) 1);
		rowCell.setBorderLeft((short) 1);
		rowCell.setBorderTop((short) 1);
		rowCell.setBorderRight((short) 1);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setColor(IndexedColors.RED.index);
		cellFont.setFontHeightInPoints((short) 10);
		rowCell.setFont(cellFont);
		return rowCell;
	}
}
