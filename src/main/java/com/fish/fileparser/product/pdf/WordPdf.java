package com.fish.fileparser.product.pdf;

import org.apache.log4j.Logger;

import com.fish.fileparser.utils.ItextUtils;
import com.fish.fileparser.utils.TikaUtils;

public class WordPdf implements AbstractPdf {

  private static final Logger log = Logger.getLogger(WordPdf.class);

  @Override
  public void createPdf(String inputFile, String outputFile) throws Exception {
    String message = TikaUtils.parse(inputFile);
    ItextUtils.createSimplePdf(message, outputFile);
  }

}

