package com.fish.fileparser.product.pdf;

import com.fish.fileparser.utils.ItextUtils;

public class ImagePdf implements AbstractPdf {

  @Override
  public void createPdf(String inputFile, String outputFile) throws Exception {
    ItextUtils.createImagePdf(inputFile, outputFile + ".pdf");
  }


}

