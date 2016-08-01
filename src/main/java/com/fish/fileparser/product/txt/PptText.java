package com.fish.fileparser.product.txt;

import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.TikaUtils;

public class PptText implements AbstractText {

  @Override
  public void createTxt(String inputFile, String outputFile) throws Exception {
    String content = TikaUtils.parse(inputFile);
    FileHelper.writeFile(content, outputFile + ".txt");
  }


}

