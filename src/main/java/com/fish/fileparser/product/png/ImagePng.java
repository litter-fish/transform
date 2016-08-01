package com.fish.fileparser.product.png;

import com.fish.fileparser.utils.FileHelper;

public class ImagePng implements AbstractPng {

  @Override
  public void createPng(String inputFile, String outputFile) throws Exception {
    String[] inputFiles = inputFile.split("\\.");
    FileHelper.copyFile(inputFile, outputFile + inputFiles[inputFiles.length - 1], true);
  }


}

