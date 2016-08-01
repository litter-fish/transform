package com.fish.fileparser.factory;

public interface AbstractFactory {

  public void convert2Html(String fileName, String outPutFile) throws Exception;

  public void convert2Png(String fileName, String outPutFile) throws Exception;

  public void convert2Text(String fileName, String outPutFile) throws Exception;

  public boolean convert2Pdf(String fileName, String outPutFile) throws Exception;

}
