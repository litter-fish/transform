package com.fish.fileparser.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

public class Xlsx2Xls {
  private String outFn;
  private File inpFn;

  public Xlsx2Xls(File inpFn) {
    this.outFn = inpFn.getAbsolutePath().replace(".xlsx", ".xls");
    this.inpFn = inpFn;
  }

  /**
   * excel07转换03
   * @throws InvalidFormatException 格式非法
   * @throws IOException IO异常
   */
  public void xlsx2XlsProgress() throws InvalidFormatException, IOException {
    InputStream in = null;
    XSSFWorkbook xlsx = null;
    HSSFWorkbook xls = null;
    try {
      in = new FileInputStream(inpFn);
      xlsx = new XSSFWorkbook(in);
      xls = new HSSFWorkbook();
      int sheetCnt = xlsx.getNumberOfSheets();
      Map<Short, String> colorMap = ColorCo.getcMap();
      Map<String, Integer> hssfMap = ColorCo.getHssfMap();
      for (int i = 0; i < sheetCnt; i++) {
        Sheet xlsxSheet = xlsx.getSheetAt(i);
        Sheet xlsSheet = xls.createSheet(xlsxSheet.getSheetName());
        Iterator<Row> xlsxRows = xlsxSheet.rowIterator();
        while (xlsxRows.hasNext()) {
          Row xlsxRow = xlsxRows.next();
          Row xlsRow = xlsSheet.createRow(xlsxRow.getRowNum());
          // 设置单元格高度
          int height = xlsxRow.getHeight();
          xlsRow.setHeight((short) height);
          Iterator<Cell> xlsxCells = xlsxRow.cellIterator();
          int offset = 0;
          while (xlsxCells.hasNext()) {
            Cell xlsxCell = xlsxCells.next();
            Cell xlsCell = null;
            try {
              xlsCell = xlsRow.createCell(xlsxCell.getColumnIndex(), xlsxCell.getCellType());
            } catch (Exception exce) {
              System.err.println(exce.getLocalizedMessage());
            }
            if (null == xlsCell) {
              break;
            }
            switch (xlsxCell.getCellType()) {
              case Cell.CELL_TYPE_BLANK:
                break;

              case Cell.CELL_TYPE_BOOLEAN:
                xlsCell.setCellValue(xlsxCell.getBooleanCellValue());
                break;

              case Cell.CELL_TYPE_ERROR:
                xlsCell.setCellValue(xlsxCell.getErrorCellValue());
                break;

              case Cell.CELL_TYPE_FORMULA:
                xlsCell.setCellFormula(xlsxCell.getCellFormula());
                break;

              case Cell.CELL_TYPE_NUMERIC:
                xlsCell.setCellValue(xlsxCell.getNumericCellValue());
                break;

              case Cell.CELL_TYPE_STRING:
                xlsCell.setCellValue(xlsxCell.getStringCellValue());
                break;
              default:
                xlsCell.setCellValue(xlsxCell.getStringCellValue());
                break;
            }
            XSSFCellStyle xlsxStyle = (XSSFCellStyle) xlsxCell.getCellStyle();
            HSSFCellStyle xlsStyle = xls.createCellStyle();

            // start 设置单元格字体格式
            HSSFFont hssfFont = xls.createFont();
            short fontIndex = xlsxStyle.getFont().getColor();
            Map<Integer, HSSFColor> map = HSSFColor.getIndexHash();
            String value = colorMap.get(fontIndex);
            Integer key = hssfMap.get(value);
            HSSFColor hssfColor = null;
            try {
              hssfColor = map.get(Integer.valueOf(key));
            } catch (Exception exce) {
              System.err.println(exce.getMessage());
            }
            if (null == hssfColor) {
              if (25 == fontIndex) {
                hssfFont.setColor(HSSFColor.PLUM.index);
              } else {
                hssfFont.setColor(HSSFColor.BLACK.index);
              }
            } else {
              hssfFont.setColor(hssfColor.getIndex());
            }
            // hssfFont.setUnderline(xssfFont.getUnderline());
            xlsStyle.setFont(hssfFont);
            // end 设置单元格字体格式

            // start 设置边框
            short borderBottom = xlsxStyle.getBorderBottom();
            short borderLeft = xlsxStyle.getBorderLeft();
            short borderRight = xlsxStyle.getBorderRight();
            short borderTop = xlsxStyle.getBorderTop();
            xlsStyle.setBorderBottom(borderBottom);
            xlsStyle.setBorderLeft(borderLeft);
            xlsStyle.setBorderRight(borderRight);
            xlsStyle.setBorderTop(borderTop);
            // end 设置边框

            // start 设置水平和垂直对齐方式
            short alignment = xlsxStyle.getAlignment();
            short verticalAlignment = xlsxStyle.getVerticalAlignment();
            xlsStyle.setAlignment(alignment);
            xlsStyle.setVerticalAlignment(verticalAlignment);
            // end 设置水平和垂直对齐方式

            // start 设置单元框背景色
            try {
              XSSFColor xssfColor = xlsxStyle.getFillForegroundXSSFColor();
              short fillForegroundColor = xssfColor.getIndexed();
              short fillPattern = xlsxStyle.getFillPattern();
              if (64 != fillForegroundColor) {
                String fbc = ColorCo.getcMap().get(fillForegroundColor);
                Integer key2 = ColorCo.getHssfMap().get(fbc);
                hssfColor = map.get(Integer.valueOf(key2));
                xlsStyle.setFillForegroundColor(HSSFColor.PLUM.index);
                xlsStyle.setFillPattern(fillPattern);
              }
            } catch (Exception exce) {
              System.err.println(exce.getMessage());
            }
            // end 设置单元框背景色

            try {
              XSSFColor fb = xlsxStyle.getFillBackgroundXSSFColor();
              short fillBackgroundColor = fb.getIndex();
              xlsStyle.setFillBackgroundColor(HSSFColor.PLUM.index);
            } catch (Exception exce) {
              System.err.println(exce.getMessage());
            }
            xlsCell.setCellStyle(xlsStyle);
            // start 设置单元格列宽度
            int width = xlsxSheet.getColumnWidth(offset);
            xlsSheet.setColumnWidth((short) offset, (short) width);
            offset++;
            // end 设置单元格列宽度
          }
        }
      }
      OutputStream out = null;
      try {
        File outF = new File(outFn);
        if (outF.exists()) {
          outF.delete();
        }
        out = new BufferedOutputStream(new FileOutputStream(outF));
        xls.write(out);
      } finally {
        if (null != out) {
          out.close();
        }
      }
    } finally {
      if (null != in) {
        in.close();
      }
      if (null != xlsx) {
        xlsx.close();
      }
      if (null != xls) {
        xls.close();
      }
    }
  }

}
