package com.fish.fileparser.product.txt;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageText implements AbstractText {

  @Override
  public void createTxt(String inputFile, String outputFile) throws Exception {
    File file = new File(inputFile);
    String baseName = FilenameUtils.getBaseName(inputFile);
    InputStream is = null;
    OutputStream os = null;
    try {
      is = new FileInputStream(file);
      os = new FileOutputStream(
          outputFile.substring(0, outputFile.length() - baseName.length() - 1) + ".txt");
      byte[] buffer = new byte[1024];
      int result = -1;
      while (-1 != (result = is.read(buffer))) {
        os.write(buffer, 0, result);
      }
    } finally {
      try {
        if (null != is) {
          is.close();
        }
        if (null != os) {
          os.close();
        }
      } catch (Exception exce) {
        System.err.println(exce.getMessage());
      }

    }

  }


}

