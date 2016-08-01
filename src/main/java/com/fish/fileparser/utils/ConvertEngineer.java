package com.fish.fileparser.utils;

import com.fish.fileparser.factory.AbstractFactory;

public class ConvertEngineer {

  private String fileName;
  private String outPutFile;

  public ConvertEngineer(String fileName, String outPutFile) {
    this.fileName = fileName;
    this.outPutFile = outPutFile;
  }

  /**
   * 文件转换处理类
   * @param abstractFactory 文件转换工厂
   * @throws Exception exception
   */
  public void convert(AbstractFactory abstractFactory) throws Exception {
    abstractFactory.convert2Html(fileName, outPutFile);
    abstractFactory.convert2Text(fileName, outPutFile);
    abstractFactory.convert2Pdf(fileName, outPutFile);
    abstractFactory.convert2Png(fileName, outPutFile);
  }
}
