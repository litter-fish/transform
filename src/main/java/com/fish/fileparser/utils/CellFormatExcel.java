package com.fish.fileparser.utils;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;


public class CellFormatExcel {

  public static void main(String[] args) {
    try {
      // 创建Excel表格工作簿
      Workbook wb = new HSSFWorkbook();
      Sheet sheet = wb.createSheet("表格单元格格式化");

      // ============================
      // 设置单元格的字体
      // ============================
      Row ztRow = sheet.createRow((short) 0);
      Cell ztCell = ztRow.createCell(0);
      ztCell.setCellValue("中国");
      // 创建单元格样式对象
      XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
      // 创建字体对象
      Font ztFont = wb.createFont();
      ztFont.setItalic(true); // 设置字体为斜体字
      ztFont.setColor(Font.COLOR_RED); // 将字体设置为“红色”
      ztFont.setFontHeightInPoints((short) 22); // 将字体大小设置为18px
      ztFont.setFontName("华文行楷"); // 将“华文行楷”字体应用到当前单元格上
      ztFont.setUnderline(Font.U_DOUBLE); // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）
      // ztFont.setStrikeout(true); // 是否添加删除线
      ztStyle.setFont(ztFont); // 将字体应用到样式上面
      ztCell.setCellStyle(ztStyle); // 样式应用到该单元格上

      // ============================
      // 设置单元格边框
      // ============================
      Row borderRow = sheet.createRow(2);
      Cell borderCell = borderRow.createCell(1);
      borderCell.setCellValue("中国");
      // 创建单元格样式对象
      XSSFCellStyle borderStyle = (XSSFCellStyle) wb.createCellStyle();
      // 设置单元格边框样式
      // CellStyle.BORDER_DOUBLE 双边线
      // CellStyle.BORDER_THIN 细边线
      // CellStyle.BORDER_MEDIUM 中等边线
      // CellStyle.BORDER_DASHED 虚线边线
      // CellStyle.BORDER_HAIR 小圆点虚线边线
      // CellStyle.BORDER_THICK 粗边线
      borderStyle.setBorderBottom(CellStyle.BORDER_THICK);
      borderStyle.setBorderTop(CellStyle.BORDER_DASHED);
      borderStyle.setBorderLeft(CellStyle.BORDER_DOUBLE);
      borderStyle.setBorderRight(CellStyle.BORDER_THIN);

      // 设置单元格边框颜色
      borderStyle.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));
      borderStyle.setTopBorderColor(new XSSFColor(java.awt.Color.GREEN));
      borderStyle.setLeftBorderColor(new XSSFColor(java.awt.Color.BLUE));

      borderCell.setCellStyle(borderStyle);

      // ============================
      // 设置单元内容的对齐方式
      // ============================
      Row alignRow = sheet.createRow(4);
      Cell alignCell = alignRow.createCell(1);
      alignCell.setCellValue("中国");

      // 创建单元格样式对象
      XSSFCellStyle alignStyle = (XSSFCellStyle) wb.createCellStyle();

      // 设置单元格内容水平对其方式
      // XSSFCellStyle.ALIGN_CENTER 居中对齐
      // XSSFCellStyle.ALIGN_LEFT 左对齐
      // XSSFCellStyle.ALIGN_RIGHT 右对齐
      alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      short s = HSSFCellStyle.ALIGN_CENTER;

      // 设置单元格内容垂直对其方式
      // XSSFCellStyle.VERTICAL_TOP 上对齐
      // XSSFCellStyle.VERTICAL_CENTER 中对齐
      // XSSFCellStyle.VERTICAL_BOTTOM 下对齐
      alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

      alignCell.setCellStyle(alignStyle);

      // ============================
      // 设置单元格的高度和宽度
      // ============================
      Row sizeRow = sheet.createRow(6);
      sizeRow.setHeightInPoints(30); // 设置行的高度

      Cell sizeCell = sizeRow.createCell(1);
      String sizeCellValue = "《Java编程思想》"; // 字符串的长度为10，表示该字符串中有10个字符，忽略中英文
      sizeCell.setCellValue(sizeCellValue);
      // 设置单元格的长度为sizeCellVlue的长度。而sheet.setColumnWidth使用sizeCellVlue的字节数
      // sizeCellValue.getBytes().length == 16
      sheet.setColumnWidth(1, (sizeCellValue.getBytes().length) * 256);

      // ============================
      // 设置单元格自动换行
      // ============================
      Row wrapRow = sheet.createRow(8);
      Cell wrapCell = wrapRow.createCell(2);
      wrapCell.setCellValue("宝剑锋从磨砺出,梅花香自苦寒来");

      // 创建单元格样式对象
      XSSFCellStyle wrapStyle = (XSSFCellStyle) wb.createCellStyle();
      wrapStyle.setWrapText(true); // 设置单元格内容是否自动换行
      wrapCell.setCellStyle(wrapStyle);

      // ============================
      // 合并单元格列
      // ============================
      Row regionRow = sheet.createRow(12);
      Cell regionCell = regionRow.createCell(0);
      regionCell.setCellValue("宝剑锋从磨砺出,梅花香自苦寒来");

      // 合并第十三行中的A、B、C三列
      CellRangeAddress region = new CellRangeAddress(12, 12, 0, 2); // 参数都是从O开始
      sheet.addMergedRegion(region);

      // ============================
      // 合并单元格行和列
      // ============================
      Row regionRow2 = sheet.createRow(13);
      Cell regionCell2 = regionRow2.createCell(3);
      String region2Value =
          "宝剑锋从磨砺出,梅花香自苦寒来。" + "采得百花成蜜后,为谁辛苦为谁甜。" + "操千曲而后晓声,观千剑而后识器。" + "察己则可以知人,察今则可以知古。";
      regionCell2.setCellValue(region2Value);

      // 合并第十三行中的A、B、C三列
      CellRangeAddress region2 = new CellRangeAddress(13, 17, 3, 7); // 参数都是从O开始
      sheet.addMergedRegion(region2);

      XSSFCellStyle region2Style = (XSSFCellStyle) wb.createCellStyle();
      region2Style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
      region2Style.setWrapText(true); // 设置单元格内容是否自动换行
      regionCell2.setCellStyle(region2Style);

      // ============================
      // 将Excel文件写入到磁盘上
      // ============================
      FileOutputStream is = new FileOutputStream("D:/home/RmadFile/CellFormatExcel.xls");
      wb.write(is);
      is.close();

      System.out.println("写入成功，运行结束！");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
