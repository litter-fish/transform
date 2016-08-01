package com.fish.fileparser.product.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.w3c.dom.Document;

import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.Xlsx2Xls;

public class ExcelHtml implements AbstractHtml {

  private static final Logger log = Logger.getLogger(ExcelHtml.class);

  @Override
  public void createHtml(String inputFile, String outputFile) throws Exception {
    InputStream is = null;
    /*
     * File sourcefile = new File(fileName); is = new FileInputStream(sourcefile);
     */

    try {
      File file = new File(inputFile);
      if (!file.exists()) {
        log.error("file not found:" + inputFile);
      }
      if (!FileHelper.isExcel2003(file)) {
        try {
          Xlsx2Xls xlsx2Xls = new Xlsx2Xls(file);
          xlsx2Xls.xlsx2XlsProgress();

          /*
           * XLS2CSV xls2csv = new XLS2CSV("C:\\Time.xls","c:\\out.csv"); xls2csv.process();
           */

        } catch (InvalidFormatException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      file = new File(inputFile.replace(".xlsx", ".xls"));
      is = new FileInputStream(file);
      convertExcel2Html(is, outputFile);
    } finally {
      try {
        if (null != is) {
          is.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 将xls(03版本)文件转换为html文件 @param fileName 需要转换的xls文件 @param outPutFile 转换为html文件的名称 @see @since
   * 1.7 @exception
   */
  private static void convertExcel2Html(InputStream is, String outputFile) {
    FileWriter out = null;
    try {
      // Document doc = ExcelToHtmlConverter.process( new File(fileName) );
      HSSFWorkbook workBook = new HSSFWorkbook(is);
      ExcelToHtmlConverter converter = new ExcelToHtmlConverter(
          DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
      converter.setOutputColumnHeaders(false);
      converter.setOutputRowNumbers(false);
      converter.setOutputLeadingSpacesAsNonBreaking(false);
      converter.setOutputRowNumbers(false);
      converter.processWorkbook(workBook);
      Document doc = converter.getDocument();

      out = new FileWriter(outputFile + ".html");
      DOMSource domSource = new DOMSource(doc);
      StreamResult streamResult = new StreamResult(out);

      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer serializer = tf.newTransformer();
      // TODO set encoding from a command argument
      serializer.setOutputProperty(OutputKeys.ENCODING, "GB2312"); // 文件编码方式
      serializer.setOutputProperty(OutputKeys.INDENT, "no"); // indent 指定了当输出结果树时，Transformer
                                                             // 是否可以添加额外的空白；其值必须为 yes 或 no
      serializer.setOutputProperty(OutputKeys.METHOD, "html"); // 指定输出文件的后缀名
      serializer.transform(domSource, streamResult);

      // FileHelper.parseCharset(outPutFile + ".html");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != out) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}

