package com.fish.fileparser.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.tool.xml.html.HTML;

/**
 * 
 * 类描述: 包括功能、用途、现存BUG，以及其它别人可能感兴趣的介绍。
 * 
 * @see 需要参见的其它类（可选）
 * @version 1.0
 * @date 2016年7月27日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class FileHelper {

  private static final Logger log = Logger.getLogger(FileHelper.class);

  /**
   * 
   * @param file
   * @return
   */
  public static boolean isExcel2003(File file) {
    InputStream is = null;
    Workbook wb = null;
    try {
      is = new FileInputStream(file);
      wb = WorkbookFactory.create(is);
      if (wb instanceof XSSFWorkbook) {
        return false;
      } else if (wb instanceof HSSFWorkbook) {
        return true;
      }
    } catch (Exception e) {
      return false;
    } finally {
      try {
        if (null != is) {
          is.close();
        }
        if (null != wb) {
          wb.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  /**
   * 
   * @param file
   * @return
   */
  public static boolean isWord2003(File file) {
    InputStream is = null;
    try {
      is = new FileInputStream(file);
      new HWPFDocument(is);
    } catch (Exception e) {
      return false;
    } finally {
      try {
        if (null != is) {
          is.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public static boolean isWord2007(File file) {
    InputStream is = null;
    try {
      is = new FileInputStream(file);
      new XWPFDocument(is).close();
    } catch (Exception e) {
      return false;
    } finally {
      try {
        if (null != is) {
          is.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  /**
   * 
   * @param file
   * @return
   */
  public static boolean isPPT2003(File file) {
    InputStream is = null;
    HSLFSlideShow ppt = null;
    try {
      is = new FileInputStream(file);
      ppt = new HSLFSlideShow(is);
    } catch (Exception e) {
      return false;
    } finally {
      try {
        if (null != is) {
          is.close();
        }
        if (null != ppt) {
          ppt.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  /**
   * 
   * @param path
   * @return
   */
  public static StringBuffer readFile(String path) {
    StringBuffer buffer = new StringBuffer();
    InputStream is = null;
    BufferedReader br = null;
    try {
      File file = new File(path);
      if (file.exists()) {
        is = new FileInputStream(file);
        br = new BufferedReader(new InputStreamReader(is));
        String content = br.readLine();
        while (null != content) {
          buffer.append(content);
          content = br.readLine();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != is) {
          is.close();
        }
        if (null != br) {
          br.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return buffer;
  }

  /**
   * 读取文件，并按照指定的分割符保存文件
   * @param path 文件的路径
   * @param split 分割内容的标识
   * @return 按照传入的分割符，标识的字符串
   */
  public static StringBuffer readFile(String path, String split) {
    StringBuffer buffer = new StringBuffer();
    InputStream is = null;
    BufferedReader br = null;
    try {
      File file = new File(path);
      if (file.exists()) {
        is = new FileInputStream(file);
        br = new BufferedReader(new InputStreamReader(is));
        String content = br.readLine();
        while (null != content) {
          buffer.append(content).append(split);
          content = br.readLine();
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      try {
        if (null != is) {
          is.close();
        }
        if (null != br) {
          br.close();
        }
      } catch (Exception exception2) {
        exception2.printStackTrace();
      }
    }

    return buffer;
  }

  /**
   * 将传入的字符串写入到指定的路径的文件下
   * @param content 将要写入文件的内容
   * @param path 写入内容的文件路径
   */
  public static void writeFile(String content, String path) {
    OutputStream fos = null;
    BufferedWriter bw = null;
    try {
      File file = new File(path);
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
      bw.write(content);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (bw != null) {
          bw.close();
        }
      } catch (IOException ioException) {
        System.err.println(ioException.getMessage());
      }
    }
  }

  /**
   * 将图片写成html文件
   * @param size 图片数量
   * @param path 保存html文件全路径
   * @param fileName 图片路径
   */
  public static void writeHtmlFile(int size, String path, String fileName) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<!DOCTYPE html><html><head>");
    buffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
    buffer.append("<meta name=\"viewport\" ");
    buffer.append("content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no,minimal-ui\"/>");
    buffer.append("<meta name=\"format-detection\" content=\"telephone=no\"/>");
    buffer.append("<meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\"/>");
    buffer.append("<title>touch</title>");
    buffer.append("<meta name=\"keywords\" content=\"\"/>");
    buffer.append("<meta name=\"description\" content=\"\"/>");
    buffer.append(
        "<style type=\"text/css\">body{width:100%;height:auto;position:relative;}img{max-width:100%;height:auto;margin:0 auto;}</style>");

    buffer.append("</head>");
    buffer.append("<body>");

    for (int offset = 0; offset < size; offset++) {
      buffer.append("<img src=\"" + fileName + "/" + (offset + 1) + ".png\" />");
      buffer.append("<br />");
    }
    buffer.append("</body></html>");
    // System.out.println(buffer.toString());
    writeFile(buffer.toString(), path + ".html");
  }

  public static void writeHtmlFile(String path, String fileName) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<!DOCTYPE html><html><head>");
    buffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
    buffer.append(
        "<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no,minimal-ui\"/>");
    buffer.append("<meta name=\"format-detection\" content=\"telephone=no\"/>");
    buffer.append("<meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\"/>");
    buffer.append("<title>touch</title>");
    buffer.append("<meta name=\"keywords\" content=\"\"/>");
    buffer.append("<meta name=\"description\" content=\"\"/>");
    buffer.append(
        "<style type=\"text/css\">body{width:100%;height:auto;position:relative;}img{max-width:100%;height:auto;margin:0 auto;}</style>");

    buffer.append("</head>");
    buffer.append("<body>");

    buffer.append("<img src=\"" + fileName + "/" + fileName + ".png\" />");
    buffer.append("<br />");

    buffer.append("</body></html>");
    // System.out.println(buffer.toString());
    writeFile(buffer.toString(), path + ".html");
  }

  public static void write2Html(StringBuffer content, String path) {

    StringBuffer buffer = new StringBuffer();
    buffer.append(
        "<html><head><meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\"></head><body><div align=\"left\">");
    buffer.append("<p>" + content + "</p>");
    buffer.append("</div></body></html>");
    // System.out.println(buffer.toString());
    writeFile(buffer.toString(), path + ".html");
  }

  public static void mkdirFiles(String filePath, String fileType) {
    File file = new File(filePath + "/" + fileType);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  /**
   * 删除文件空行
   * 
   * @param content
   * @param outPutFile
   * @throws IOException
   */
  public static void rmrBlankLines(String inputFile, String outPutFile) throws IOException {
    File htmFile = new File(inputFile);
    // 以GB2312读取文件
    BufferedReader br = null;
    BufferedWriter bw = null;
    try {
      br = new BufferedReader(new FileReader(htmFile));
      bw = new BufferedWriter(new FileWriter(new File(outPutFile)));
      String result = null;
      while (null != (result = br.readLine())) {
        if (!"".equals(result.trim())) {
          bw.write(result + "\r\n");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != br) {
          br.close();
        }
        if (null != bw) {
          bw.close();
        }
      } catch (Exception e) {

      }
    }


  }

  /**
   * @param htmFilePath
   * @throws IOException
   */
  public static void parseH2(String htmFilePath) throws IOException {
    File htmFile = new File(htmFilePath);
    Document doc = Jsoup.parse(htmFile, "UTF-8");
    doc.getElementsByAttribute("h2");
    Elements content = doc.getElementsByTag("h2");
    for (Element meta : content) {
      meta.attr("style", "text-align:center");
    }
    FileUtils.writeStringToFile(htmFile, doc.html(), "UTF-8");
  }

  /**
   * @param htmFilePath
   * @throws IOException
   */
  public static void parseCharset(String htmFilePath) throws IOException {
    File htmFile = new File(htmFilePath);
    // 以GB2312读取文件
    Document doc = Jsoup.parse(htmFile, "utf-8");
    // 获取html节点
    Elements content = doc.getElementsByAttributeValueStarting("content", "text/html;");
    Elements brs = doc.getElementsByTag("<br>");

    for (Element br : brs) {
      br.before("<br />");
      br.remove();
    }


    for (Element meta : content) {
      // 获取content节点，修改charset属性
      meta.attr("content", "text/html; charset=utf-8");
      break;
    }
    // 转换成utf-8编码的文件写入
    System.out.println(doc.html());
    FileUtils.writeStringToFile(htmFile, doc.html(), "utf-8");
  }

  /**
   * @param htmFilePath
   * @throws IOException
   */
  public static void parse(String htmFilePath) throws IOException {
    File htmFile = new File(htmFilePath);
    // 以GB2312读取文件
    Document doc = Jsoup.parse(htmFile, "utf-8");
    String xmlns = doc.getElementsByTag("html").attr("xmlns");
    if (null == xmlns || "".equals(xmlns)) {
      return;
    }
    doc.getElementsByTag("html").removeAttr("xmlns");
    Element head = doc.head();
    /*
     * Elements headChildren = head.children(); for(Element children : headChildren) { Elements
     * metas = children.getElementsByTag("meta"); for(Element meta : metas) { meta.remove(); } }
     */
    head.appendElement("meta").attr("name", "viewport").attr("content",
        "width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no");

    // 获取html节点
    Element element = doc.body();
    Elements content = head.getElementsByAttributeValueStarting("name", "meta:page-count");
    for (Element meta : content) {
      String value = meta.attr("content");
      try {
        Integer count = Integer.valueOf(value);
        Elements ps = element.getElementsByTag("p");
        Iterator<Element> iterator = ps.iterator();
        while (iterator.hasNext()) {
          Element p = iterator.next();
          String text = p.text();
          if (text.equals("- " + count + " -")) {
            for (int offset = count; offset > 0; offset--) {
              p.remove();
              p = iterator.next();
              text = p.text();
            }
          }
          if (text.equals("")) {
            p.remove();
            p = iterator.next();
          }
          p.attr("align", "center");
          p.attr("style", "font-size:1.5rem;");
          break;
        }
      } catch (Exception e) {

      }
      // 获取content节点，修改charset属性
      // meta.attr("content", "text/html; charset=utf-8");
      break;
    }


    // 转换成utf-8编码的文件写入
    FileUtils.writeStringToFile(htmFile, "<!DOCTYPE html>" + doc.html(), "utf-8");
  }

  public static void checkHtmlEndTag(String htmFilePath) throws IOException {
    File htmFile = new File(htmFilePath);
    // 以GB2312读取文件
    Document doc = Jsoup.parse(htmFile, "utf-8");
    Elements all = doc.getElementsByTag("html");
    for (Element element : all) {
      parseElements(all, element);
    }
    FileUtils.writeStringToFile(htmFile, doc.html(), "utf-8");
  }

  public static void parseElements(Elements elements, Element element) {
    int childNodeSize = elements.size();
    if (0 < childNodeSize) {
      for (int offset = 0; offset < childNodeSize; offset++) {
        parseElements(elements.get(offset).children(), elements.get(offset));
      }
    } else {
      String tagName = element.tagName();
      String content = element.toString();
      if (tagName.length() + 3 > content.length()) {
        element.text("");
      } else {
        try {
          String endTag =
              content.substring(content.length() - tagName.length() - 3, content.length());
          if (!("</" + tagName + ">").equals(endTag)) {
            element.text("");
          }
        } catch (Exception w) {}
      }
    }
  }


  public static void changeImageType(String htmFilePath) throws IOException {
    File htmFile = new File(htmFilePath);
    // 以GB2312读取文件
    Document doc = Jsoup.parse(htmFile, "utf-8");

    Elements elements = doc.getElementsByTag("img");
    String imgPath = "";
    for (Element element : elements) {
      String src = element.attr("src");
      String[] sp = src.split("\\.");
      String newSrc = htmFile.getParent() + File.separator + sp[0] + ".jpg";
      imgPath = src;
      element.attr("src", newSrc);
    }
    FileUtils.writeStringToFile(htmFile, doc.html(), "utf-8");

    String name = htmFile.getName();

    htmFilePath = htmFilePath.substring(0, htmFilePath.length() - name.length()) + imgPath;


    File file = new File(htmFilePath);

    File[] files = file.getParentFile().listFiles();
    for (File file2 : files) {
      String filePath = file2.getPath();
      String[] sp = filePath.split("\\.");
      String newSrc = sp[0] + ".jpg";
      FileHelper.copyFile(filePath, newSrc, true);
    }

  }

  public static void nioTransferCopy(File source, File target) {
    FileChannel in = null;
    FileChannel out = null;
    FileInputStream inStream = null;
    FileOutputStream outStream = null;
    try {
      inStream = new FileInputStream(source);
      outStream = new FileOutputStream(target);
      in = inStream.getChannel();
      out = outStream.getChannel();
      in.transferTo(0, in.size(), out);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      close(inStream);
      close(in);
      close(outStream);
      close(out);
    }
  }


  private static boolean nioBufferCopy(File source, File target) {
    FileChannel in = null;
    FileChannel out = null;
    FileInputStream inStream = null;
    FileOutputStream outStream = null;
    try {
      inStream = new FileInputStream(source);
      outStream = new FileOutputStream(target);
      in = inStream.getChannel();
      out = outStream.getChannel();
      ByteBuffer buffer = ByteBuffer.allocate(4096);
      while (in.read(buffer) != -1) {
        buffer.flip();
        out.write(buffer);
        buffer.clear();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      close(inStream);
      close(in);
      close(outStream);
      close(out);
    }
    return true;
  }


  public static void customBufferStreamCopy(File source, File target) {
    InputStream fis = null;
    OutputStream fos = null;
    try {
      fis = new FileInputStream(source);
      fos = new FileOutputStream(target);
      byte[] buf = new byte[4096];
      int i;
      while ((i = fis.read(buf)) != -1) {
        fos.write(buf, 0, i);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close(fis);
      close(fos);
    }
  }



  /**
   * 复制单个文件
   * 
   * @param srcFileName 待复制的文件名
   * @param destFileName 目标文件名
   * @param overlay 如果目标文件存在，是否覆盖
   * @return 如果复制成功返回true，否则返回false
   */
  public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
    File srcFile = new File(srcFileName);
    // 判断源文件是否存在
    if (!srcFile.exists()) {
      log.info("input file not null");
      return false;
    } else if (!srcFile.isFile()) {
      log.info("input file is not file");
      return false;
    }

    // 判断目标文件是否存在
    File destFile = new File(destFileName);
    if (destFile.exists()) {
      // 如果目标文件存在并允许覆盖
      if (overlay) {
        // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
        new File(destFileName).delete();
      }
    } else {
      // 如果目标文件所在目录不存在，则创建目录
      if (!destFile.getParentFile().exists()) {
        // 目标文件所在目录不存在
        if (!destFile.getParentFile().mkdirs()) {
          // 复制文件失败：创建目标文件所在目录失败
          return false;
        }
      }
    }

    boolean result = nioBufferCopy(srcFile, destFile);

    return result;
  }

  /**
   * 复制整个目录的内容
   * 
   * @param srcDirName 待复制目录的目录名
   * @param destDirName 目标目录名
   * @param overlay 如果目标目录存在，是否覆盖
   * @return 如果复制成功返回true，否则返回false
   */
  public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
    // 判断源目录是否存在
    File srcDir = new File(srcDirName);
    if (!srcDir.exists()) {
      log.info("srcDir not found");
      return false;
    } else if (!srcDir.isDirectory()) {
      log.info("srcDir not Directory");
      return false;
    }

    // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
    if (!destDirName.endsWith(File.separator)) {
      destDirName = destDirName + File.separator;
    }
    File destDir = new File(destDirName);
    // 如果目标文件夹存在
    if (destDir.exists()) {
      // 如果允许覆盖则删除已存在的目标目录
      if (overlay) {
        new File(destDirName).delete();
      } else {
        log.info("");
        return false;
      }
    } else {
      // 创建目的目录
      System.out.println("目的目录不存在，准备创建。。。");
      if (!destDir.mkdirs()) {
        System.out.println("复制目录失败：创建目的目录失败！");
        return false;
      }
    }

    boolean flag = true;
    File[] files = srcDir.listFiles();
    for (int i = 0; i < files.length; i++) {
      // 复制文件
      if (files[i].isFile()) {
        flag = FileHelper.copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(),
            overlay);
        if (!flag) {
          break;
        }
      } else if (files[i].isDirectory()) {
        flag = FileHelper.copyDirectory(files[i].getAbsolutePath(),
            destDirName + files[i].getName(), overlay);
        if (!flag) {
          break;
        }
      }
    }
    if (!flag) {
      log.info("copy Directory fail");
      return false;
    } else {
      return true;
    }
  }

  /**
   * 关闭资源
   * @param object 需要关闭的对象
   */
  public static void close(Object object) {
    if (null == object) {
      return;
    }
    try {
      if (object instanceof InputStream) {
        ((InputStream) object).close();
      } else if (object instanceof OutputStream) {
        ((OutputStream) object).close();
      } else if (object instanceof Channel) {
        ((Channel) object).close();
      }
    } catch (Exception exce) {
      System.err.println(exce.getMessage());
    }
  }



  /**
   * 合并excel表格的sheet
   * 
   * @param htmFilePath html文件路径 
   * @throws IOException 打开文件异常
   */
  public static void mergeTable(String htmFilePath) throws IOException {
    File htmFile = new File(htmFilePath);
    // 以GB2312读取文件
    Document doc = Jsoup.parse(htmFile, "utf-8");
    Integer tableMaxSize = getMaxTableSize(doc);
    Elements allTable = doc.getElementsByTag("tbody");
    Element max = null;
    for (Element table : allTable) {
      Elements elements = table.children();
      if (0 >= elements.size()) {
        table.parent().remove();
        continue;
      }
      int size = elements.first().children().size();
      if (size >= tableMaxSize) {
        max = table;
        continue;
      }
      for (Element tr : elements) {
        Elements td = tr.children();

        for (int offset = tableMaxSize; offset > td.size(); offset--) {
          Element tdd = doc.createElement("td");
          tr.appendChild(tdd);
        }

        max.appendChild(tr);
      }
      table.parent().remove();
    }
    FileUtils.writeStringToFile(htmFile, doc.html(), "utf-8");
  }

  private static Integer getMaxTableSize(Document doc) {
    Elements allTable = doc.getElementsByTag("tbody");
    TreeSet<Integer> tableSize = new TreeSet<Integer>();
    for (Element table : allTable) {
      Elements elements = table.children();
      int size = 0;
      try {
        size = elements.first().children().size();
      } catch (Exception e) {
        size = -1;
      }
      if (tableSize.contains(size)) {
        size--;
      }
      tableSize.add(size);
    }
    return tableSize.last();
  }

  /**
   * 获取文件css样式
   * @param src 文件
   * @return 文件css样式
   * @throws IOException 打开文件异常
   */
  public static final StringBuffer getHtmlCss(String src) throws IOException {
    File htmFile = new File(src);
    // 以GB2312读取文件
    Document doc = Jsoup.parse(htmFile, "utf-8");
    Elements styles = doc.getElementsByTag("style");
    StringBuffer csStringBuffer = new StringBuffer();
    for (Element style : styles) {
      csStringBuffer.append(style.toString().replace("<style>", "").replace("</style>", ""));
    }
    Elements links = doc.getElementsByTag("link");
    for (Element style : links) {
      String href = style.attr("href");
      String realPath = src + File.separator + href;
      StringBuffer link = FileHelper.readFile(realPath);
      csStringBuffer.append(link);
    }

    return csStringBuffer;

  }

}

