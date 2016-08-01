package com.fish.fileparser.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * 类描述: 文件操作类（包括：下载文件，上传文件（该方法无实现））
 * 
 * @see MessageUtil
 * @version 1.0
 * @date 2016年3月9日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public final class FileUtils {

  private final static Logger log = Logger.getLogger(FileUtils.class);

  public static final String DOT = ".";

  /**
   * 从远处服务器端下载文件，并将其保存为filePath文件
   * 
   * @param fileUrl 远程文件地址
   * @param filePath 保存本地文件
   * @return
   * @throws ConnectException
   */
  public final static String downloadFile(String fileUrl, String filePath) throws ConnectException {
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    HttpURLConnection httpUrl = null;
    URL url = null;
    try {
      byte[] buf = new byte[1024];
      int size = 0;

      // 建立链接
      url = new URL(fileUrl);
      httpUrl = (HttpURLConnection) url.openConnection();
      // 连接指定的资源
      httpUrl.connect();
      // 获取网络输入流
      bis = new BufferedInputStream(httpUrl.getInputStream());
      // 建立文件
      fos = new FileOutputStream(filePath);
      log.info("正在获取链接[" + fileUrl + "]的内容...\n将其保存为文件[" + filePath + "]");
      // 保存文件
      while ((size = bis.read(buf)) != -1) {
        fos.write(buf, 0, size);
      }

    } catch (ConnectException e) {
      throw new ConnectException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != fos) fos.close();
        if (null != bis) bis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (null != httpUrl) httpUrl.disconnect();
    }
    return null;
  }

  /**
   * 获取扩展名
   * 
   * @param fileName
   * @return
   */
  public static String getExtension(String fileName) {
    if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT)) return StringUtils.EMPTY;
    String ext = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, DOT));
    return StringUtils.trimToEmpty(ext);
  }

  public static boolean isExists(String filePath) {
    if (new File(filePath).exists()) {
      return true;
    }
    return false;
  }

  public static void mksFile(String filePath) {
    if (!isExists(filePath)) {
      new File(filePath).mkdirs();
    }
  }

  /**
   * 备份文件
   * 
   * @param file
   * @param destFile
   */
  public static void bakFile(File file, File destFile) {
    try {



      org.apache.commons.io.FileUtils.copyFile(file, destFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
