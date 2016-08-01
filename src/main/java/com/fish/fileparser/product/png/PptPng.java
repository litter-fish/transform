package com.fish.fileparser.product.png;

import com.fish.fileparser.utils.ImageUtils;

public class PptPng implements AbstractPng {

  @Override
  public void createPng(String inputFile, String outputFile) throws Exception {
    ImageUtils.convertPpt2Png(inputFile, outputFile);
  }


}

