package com.fish.fileparser.product.png;

import com.fish.fileparser.product.html.CsvHtml;
import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.ItextUtils;

public class CsvPng implements AbstractPng {

  @Override
  public void createPng(String inputFile, String outputFile) throws Exception {
    CsvHtml csvHtml = new CsvHtml();
    csvHtml.createHtml(inputFile, outputFile);

    FileHelper.checkHtmlEndTag(outputFile + ".html");
    ItextUtils.createPdf(outputFile + ".html", outputFile + ".pdf");

    PdfPng pdfPng = new PdfPng();
    pdfPng.createPng(outputFile + ".pdf", outputFile);
  }
}

