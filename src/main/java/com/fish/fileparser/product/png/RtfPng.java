package com.fish.fileparser.product.png;

import com.fish.fileparser.product.html.RtfHtml;
import com.fish.fileparser.utils.ItextUtils;

public class RtfPng implements AbstractPng {

  @Override
  public void createPng(String inputFile, String outputFile) throws Exception {
    RtfHtml rtfHtml = new RtfHtml();
    rtfHtml.createHtml(inputFile, outputFile + ".html");
    ItextUtils.createPdf(outputFile + ".html", outputFile + ".pdf");

    PdfPng pdfPng = new PdfPng();
    pdfPng.createPng(outputFile + ".pdf", outputFile);
  }


}

