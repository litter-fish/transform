package com.fish.fileparser.product.pdf;

import com.fish.fileparser.utils.FileHelper;

public class PdfPdf implements AbstractPdf {

  @Override
  public void createPdf(String inputFile, String outputFile) throws Exception {
    FileHelper.copyFile(inputFile, outputFile, true);
  }

}

