package com.fish.fileparser.product.png;

import com.fish.fileparser.product.html.WordHtml;
import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.ItextUtils;

import org.apache.log4j.Logger;

public class WordPng implements AbstractPng {

  private static final Logger log = Logger.getLogger(WordPng.class);
  private static final String bg = "D:\\pic\\20150909140815.jpg";

  @Override
  public void createPng(String inputFile, String outputFile) throws Exception {
    WordHtml wordHtml = new WordHtml();
    wordHtml.createHtml(inputFile, outputFile);

    FileHelper.changeImageType(outputFile + ".html");
    FileHelper.checkHtmlEndTag(outputFile + ".html");
    ItextUtils.createPdf(outputFile + ".html", outputFile);

    ItextUtils.addWaterMark(outputFile + ".pdf", outputFile + "AddWaterMake.pdf", "正版授权", bg, 100,
        200);

    PdfPng pdfPng = new PdfPng();
    pdfPng.createPng(outputFile + ".pdf", outputFile);
  }

}

