package com.fish.fileparser.product.txt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PdfText implements AbstractText {

  @Override
  public void createTxt(String inputFile, String outputFile) throws Exception {
    convertPdf2Text(inputFile, outputFile);
  }

  /**
   * 将pdf文件转换为txt @param fileName @param outPutFile @see @since 1.7 @exception
   */
  private static void convertPdf2Text(String fileName, String outPutFile) {
    File file = new File(fileName);
    FileInputStream in = null;
    try {
      /*
       * in = new FileInputStream(fileName); // 新建一个PDF解析器对象 PDFParser parser = new PDFParser(in);
       * // 对PDF文件进行解析 parser.parse(); // 获取解析后得到的PDF文档对象 PDDocument pdfdocument =
       * parser.getPDDocument(); // 新建一个PDF文本剥离器 PDFTextStripper stripper = new PDFTextStripper();
       * // 从PDF文档对象中剥离文本 String result = stripper.getText(pdfdocument); System.out.println("PDF文件"
       * + file.getAbsolutePath() + "的文本内容如下："); System.out.println(result);
       */

    } catch (Exception e) {
      System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + e);
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e1) {}
      }
    }
  }

}

