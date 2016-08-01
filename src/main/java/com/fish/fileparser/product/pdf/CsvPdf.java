package com.fish.fileparser.product.pdf;

import com.fish.fileparser.utils.ItextUtils;
import com.fish.fileparser.utils.TikaUtils;

public class CsvPdf implements AbstractPdf {

  @Override
  public void createPdf(String inputFile, String outputFile) throws Exception {
    String message = TikaUtils.parse(inputFile);
    ItextUtils.createSimplePdf(message, outputFile);
  }
}

