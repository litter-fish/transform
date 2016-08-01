package com.fish.fileparser.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TikaUtils {

  /**
   * 解析各种类型文件
   * 
   * @param 文件路径
   * @return 文件内容字符串
   * @throws TikaException
   * @throws IOException
   */
  public static String parse(String path) throws IOException, TikaException {
    Tika tika = new Tika();
    return tika.parseToString(new File(path));
  }

  public static String fileToTxt(File file) {
    org.apache.tika.parser.Parser parser = new AutoDetectParser();
    try {
      InputStream inputStream = new FileInputStream(file);
      DefaultHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
      ParseContext parseContext = new ParseContext();
      parseContext.set(Parser.class, parser);
      parser.parse(inputStream, handler, metadata, parseContext);
      /*
       * for (String string : metadata.names()) {
       * System.out.println(string+":"+metadata.get(string)); }
       */
      inputStream.close();
      return handler.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String parseToHTML(String fileName, String outPutFile)
      throws IOException, SAXException, TikaException {
    ContentHandler handler = new ToHTMLContentHandler();
    AutoDetectParser parser = new AutoDetectParser();
    Metadata metadata = new Metadata();
    InputStream stream = null;
    try {
      stream = new FileInputStream(new File(fileName));
      parser.parse(stream, handler, metadata);

      FileHelper.writeFile(handler.toString(), outPutFile + ".html");

      FileHelper.parse(outPutFile + ".html");
      return null;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {

    }

    return null;
  }


}

