package com.fish.fileparser.factory;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.RtfHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.RtfPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.RtfPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.RtfText;

import org.apache.log4j.Logger;

public class RtfFactory implements AbstractFactory {

  private static final Logger log = Logger.getLogger(RtfFactory.class);

  @Override
  public void convert2Html(String fileName, String outPutFile) throws Exception {
    log.info("start convert RTF to html,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractHtml html = new RtfHtml();
    html.createHtml(fileName, outPutFile);
    log.info("将RTF转换为html文件......ok");
    log.info("convert success! Generate " + outPutFile + " with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }


  @Override
  public void convert2Png(String fileName, String outPutFile) throws Exception {
    log.info("start convert RTF to png,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractPng png = new RtfPng();
    png.createPng(fileName, outPutFile);
    log.info("将RTF转换为png文件......ok");
    log.info("convert success! Generate " + outPutFile + " with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }

  @Override
  public void convert2Text(String fileName, String outPutFile) throws Exception {
    log.info("start convert RTF to txt,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractText text = new RtfText();
    text.createTxt(fileName, outPutFile);
    log.info("将RTF转换为txt文件......ok");
    log.info("convert success! Generate " + outPutFile + ".txt with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }

  @Override
  public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
    log.info("start convert RTF to pdf,out file [" + outPutFile + ".html]......");
    long startTime = System.currentTimeMillis();
    AbstractPdf html = new RtfPdf();
    html.createPdf(fileName, outPutFile);
    log.info("将RTF转换为pdf文件......ok");
    log.info("convert success! Generate " + outPutFile + ".pdf with "
        + (System.currentTimeMillis() - startTime) + " ms.");
    return false;
  }

}

