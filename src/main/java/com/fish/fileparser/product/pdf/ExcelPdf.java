package com.fish.fileparser.product.pdf;

import com.fish.fileparser.utils.ItextUtils;
import com.fish.fileparser.utils.TikaUtils;

import org.apache.log4j.Logger;

public class ExcelPdf implements AbstractPdf {

  private static final Logger log = Logger.getLogger(ExcelPdf.class);

  @Override
  public void createPdf(String inputFile, String outputFile) throws Exception {
    String message = TikaUtils.parse(inputFile);
    ItextUtils.createSimplePdf(message, outputFile);
  }
}

