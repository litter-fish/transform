package com.fish.fileparser.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

public class WordExcelToHtml {

  /**
   * 回车符ASCII码
   */
  private static final short ENTER_ASCII = 13;

  /**
   * 空格符ASCII码
   */
  private static final short SPACE_ASCII = 32;

  /**
   * 水平制表符ASCII码
   */
  private static final short TABULATION_ASCII = 9;

  public static String htmlText = "";
  public static String htmlTextTbl = "";
  public static int counter = 0;
  public static int beginPosi = 0;
  public static int endPosi = 0;
  public static int beginArray[];
  public static int endArray[];
  public static String htmlTextArray[];
  public static boolean tblExist = false;

  public static final String inputFile = "D://home//RmadFile//tran//doc.doc";

  public static void main(String argv[]) {
    try {
      getWordAndStyle(inputFile);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * 读取每个文字样式
   * 
   * @param fileName
   * @throws Exception
   */

  public static void getWordAndStyle(String fileName) throws Exception {
    FileInputStream in = new FileInputStream(new File(fileName));
    HWPFDocument doc = new HWPFDocument(in);

    Range rangetbl = doc.getRange();// 得到文档的读取范围
    TableIterator it = new TableIterator(rangetbl);
    int num = 100;

    beginArray = new int[num];
    endArray = new int[num];
    htmlTextArray = new String[num];

    // 取得文档中字符的总数
    int length = doc.characterLength();
    // 创建图片容器
    PicturesTable pTable = doc.getPicturesTable();

    htmlText = "<html><head><title>" + doc.getSummaryInformation().getTitle() + "</title>";

    htmlText += "<style type=\"text/css\">";
    htmlText += "table.gridtable {font-family: verdana,arial,sans-serif;";
    htmlText +=
        "font-size:11px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;}";
    htmlText +=
        "table.gridtable th {border-width: 1px;	padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;}";
    htmlText +=
        "table.gridtable td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}";
    htmlText += "</style></head><body>";

    // 创建临时字符串,好加以判断一串字符是否存在相同格式

    if (it.hasNext()) {
      readTable(it, rangetbl);
    }

    int cur = 0;

    String tempString = "";
    for (int i = 0; i < length - 1; i++) {
      // 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
      Range range = new Range(i, i + 1, doc);

      CharacterRun cr = range.getCharacterRun(0);
      // beginArray=new int[num];
      // endArray=new int[num];
      // htmlTextArray=new String[num];
      if (tblExist) {
        if (i == beginArray[cur]) {
          htmlText += tempString + htmlTextArray[cur];
          tempString = "";
          i = endArray[cur] - 1;
          cur++;
          continue;
        }
      }
      if (pTable.hasPicture(cr)) {
        htmlText += tempString;
        // 读写图片
        readPicture(pTable, cr);
        tempString = "";
      } else {

        Range range2 = new Range(i + 1, i + 2, doc);
        // 第二个字符
        CharacterRun cr2 = range2.getCharacterRun(0);
        char c = cr.text().charAt(0);

        System.out
            .println(i + "::" + range.getEndOffset() + "::" + range.getStartOffset() + "::" + c);

        // 判断是否为回车符
        if (c == ENTER_ASCII) {
          tempString += "<br />";

        }
        // 判断是否为空格符
        else if (c == SPACE_ASCII)
          tempString += " ";
        // 判断是否为水平制表符
        else if (c == TABULATION_ASCII) tempString += "    ";
        // 比较前后2个字符是否具有相同的格式
        boolean flag = compareCharStyle(cr, cr2);
        if (flag)
          tempString += cr.text();
        else {
          String fontStyle = "<span style=\"font-family:" + cr.getFontName() + ";font-size:"
              + cr.getFontSize() / 2 + "pt;";

          if (cr.isBold()) fontStyle += "font-weight:bold;";
          if (cr.isItalic()) fontStyle += "font-style:italic;";

          htmlText += fontStyle + "\">" + tempString + cr.text() + "</span>";
          tempString = "";
        }
      }
    }

    htmlText += tempString + "</body></html>";
    writeFile(htmlText);
    // FileHelper.parseCharset("D://home//RmadFile//tran//doc//doc.html");
  }

  /**
   * 读写文档中的表格
   * 
   * @param pTable
   * @param cr
   * @throws Exception
   */
  public static void readTable(TableIterator it, Range rangetbl) throws Exception {

    htmlTextTbl = "";
    // 迭代文档中的表格

    counter = -1;
    while (it.hasNext()) {
      tblExist = true;
      htmlTextTbl = "";
      Table tb = (Table) it.next();
      beginPosi = tb.getStartOffset();
      endPosi = tb.getEndOffset();

      System.out.println("............" + beginPosi + "...." + endPosi);
      counter = counter + 1;
      // 迭代行，默认从0开始
      beginArray[counter] = beginPosi;
      endArray[counter] = endPosi;

      htmlTextTbl += "<table class=\"gridtable\">";
      for (int i = 0; i < tb.numRows(); i++) {
        TableRow tr = tb.getRow(i);

        htmlTextTbl += "<tr>";
        // 迭代列，默认从0开始
        for (int j = 0; j < tr.numCells(); j++) {
          TableCell td = tr.getCell(j);// 取得单元格
          int cellWidth = td.getWidth();

          // 取得单元格的内容
          for (int k = 0; k < td.numParagraphs(); k++) {
            Paragraph para = td.getParagraph(k);
            String s = para.text().toString().trim();
            if (s == "") {
              s = " ";
            }
            System.out.println(s);
            htmlTextTbl += "<td width=" + cellWidth + ">" + s + "</td>";
            System.out.println(i + ":" + j + ":" + cellWidth + ":" + s);
          } // end for
        } // end for
        htmlTextTbl += "</tr>";
      } // end for
      htmlTextTbl += "</table>";
      htmlTextArray[counter] = htmlTextTbl;

    } // end while
  }

  /**
   * 读写文档中的图片
   * 
   * @param pTable
   * @param cr
   * @throws Exception
   */
  public static void readPicture(PicturesTable pTable, CharacterRun cr) throws Exception {
    // 提取图片
    Picture pic = pTable.extractPicture(cr, false);
    // 返回POI建议的图片文件名
    String afileName = pic.suggestFullFileName();
    OutputStream out = new FileOutputStream(
        new File("D://home//RmadFile//tran//doc" + File.separator + afileName));
    pic.writeImageContent(out);
    htmlText += "<img src=\"" + afileName + "\"></img>";
  }

  public static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {
    boolean flag = false;
    if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic()
        && cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) {
      flag = true;
    }
    return flag;
  }

  /**
   * 写文件
   * 
   * @param s
   */
  public static void writeFile(String s) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    try {
      File file = new File("D://home//RmadFile//tran//doc//doc.html");
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos));
      bw.write(s);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (bw != null) bw.close();
        if (fos != null) fos.close();
      } catch (IOException ie) {}
    }
  }

}
