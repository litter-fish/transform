package com.fish.fileparser.factory;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.PdfHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.PdfPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.PdfPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.PdfText;

/**
 * 
 * 类描述: pdf文件转换工厂类
 * 
 * @see 需要参见的其它类（可选）
 * @version 1.0
 * @date 2016年3月7日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class PdfFactory implements AbstractFactory {

  private static final Logger log = Logger.getLogger(PdfFactory.class);

  /**
   * 将pdf文档转换为html文件
   * 
   * @param fileName 输入文件名
   * @param outPutFile 输出文件路径
   */
  @Override
  public void convert2Html(String fileName, String outPutFile) throws Exception {
    long startTime = System.currentTimeMillis();
    log.info("start convert pdf to Html,out file [" + outPutFile + ".html]......");
    AbstractHtml html = new PdfHtml();
    html.createHtml(fileName, outPutFile);
    log.info("convert success! Generate " + outPutFile + ".html with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }

  @Override
  public void convert2Png(String fileName, String outPutFile) throws Exception {
    log.info("start convert pdf to png,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractPng png = new PdfPng();
    png.createPng(fileName, outPutFile);
    log.info("将Pdf转换为png文件......ok");
    log.info("convert success! Generate " + outPutFile + " with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }

  @Override
  public void convert2Text(String fileName, String outPutFile) throws Exception {
    log.info("start convert pdf to txt,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractText text = new PdfText();
    text.createTxt(fileName, outPutFile);
    log.info("将Pdf转换为txt文件......ok");
    log.info("convert success! Generate " + outPutFile + ".txt with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }

  @Override
  public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
    log.info("start convert pdf to pdf,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractPdf html = new PdfPdf();
    html.createPdf(fileName, outPutFile);
    log.info("将Pdf转换为pdf文件......ok");
    log.info("convert success! Generate " + outPutFile + ".pdf with "
        + (System.currentTimeMillis() - startTime) + " ms.");
    return false;
  }

}
