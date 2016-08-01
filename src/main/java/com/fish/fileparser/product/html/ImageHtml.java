package com.fish.fileparser.product.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fish.fileparser.utils.FileHelper;

public class ImageHtml implements AbstractHtml {

  @Override
  public void createHtml(String inputFile, String outputFile) throws Exception {
    OutputStream output = null;

    try {
      output = new FileOutputStream(outputFile + ".png");
      String baseName = FilenameUtils.getBaseName(inputFile);
      FileUtils.copyFile(new File(inputFile), output);
      FileHelper.writeHtmlFile(outputFile.substring(0, outputFile.length() - baseName.length() - 1),
          baseName);
    } finally {
      try {
        if (null != output) {
          output.close();
        }
      } catch (Exception e) {

      }
    }
  }

}

