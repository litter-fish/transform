package com.fish.fileparser.product.html;

import com.fish.fileparser.utils.TikaUtils;

public class RtfHtml implements AbstractHtml {

  @Override
  public void createHtml(String inputFile, String outputFile) throws Exception {
    TikaUtils.parseToHTML(inputFile, outputFile);
  }


}

