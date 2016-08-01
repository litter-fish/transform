package com.fish.fileparser.factory;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.ExcelHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.ExcelPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.ExcelPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.ExcelText;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;


/**
 * 类描述: excel文件转换工厂
 * 
 * @see AbstractFactory
 * @version 1.0
 * @date 2016年3月7日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class ExcelFactory implements AbstractFactory {

  private static final Logger log = Logger.getLogger(ExcelFactory.class);

  /**
   * 将excel转换为html
   * 
   * @param fileName 需要转换的word文档
   * @param outPutFile 转换为html文件名称（全路径）
   * @throws FileNotFoundException 文件未发现
   * @see
   * @since 1.7
   */
  @Override
  public void convert2Html(String fileName, String outPutFile) throws Exception {
    log.info("将excel转换为html文件开始,输出文件 [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractHtml html = new ExcelHtml();
    html.createHtml(fileName, outPutFile);
    log.info("将excel转换为html文件......ok");
    log.info("Generate " + outPutFile + ".html with " + (System.currentTimeMillis() - startTime)
        + " ms.");
  }

  @Override
  public void convert2Png(String fileName, String outPutFile) throws Exception {
    log.info("将Excel转换为png文件开始,输出文件 [" + outPutFile + "]......");
    long startTime = System.currentTimeMillis();
    AbstractPng png = new ExcelPng();
    png.createPng(fileName, outPutFile);
    log.info("将Excel转换为png文件......ok");
    log.info(
        "Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
  }

  @Override
  public void convert2Text(String fileName, String outPutFile) throws Exception {
    log.info("将Excel转换为txt文件开始,输出文件 [" + outPutFile + ".txt]......");
    long startTime = System.currentTimeMillis();
    AbstractText text = new ExcelText();
    text.createTxt(fileName, outPutFile);
    log.info("将Excel转换为txt文件......ok");
    log.info("Generate " + outPutFile + ".txt with " + (System.currentTimeMillis() - startTime)
        + " ms.");
  }

  @Override
  public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
    log.info("将Excel转换为pdf文件开始,输出文件 [" + outPutFile + ".pdf]......");
    long startTime = System.currentTimeMillis();
    AbstractPdf html = new ExcelPdf();
    html.createPdf(fileName, outPutFile);
    log.info("将Excel转换为pdf文件......ok");
    log.info("Generate " + outPutFile + ".pdf with " + (System.currentTimeMillis() - startTime)
        + " ms.");
    return false;
  }

}

