package com.fish.fileparser.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class ItextUtils {

  private final static String[] IMAGE_FORMAT =
      {"PNG", "png", "JPG", "jpg", "JPEG", "jpeg", "BMP", "bmp"};

  private final static String bg = "D:\\pic\\20150909140815.jpg";

  public static final Object[] getDocument(String file) {
    Document document = null;
    PdfWriter writer = null;
    Object[] objects = new Object[2];
    try {
      // Step 1—Create a Document.
      document = new Document(PageSize.A4.rotate());
      // Step 2—Get a PdfWriter instance.
      writer = PdfWriter.getInstance(document, new FileOutputStream(file + ".pdf"));
      // Step 3—Open the Document.
      document.open();
      objects[0] = document;
      objects[1] = writer;
    } catch (Exception e) {
      try {
        if (null != document) document.close();
        if (null != writer) writer.close();
      } finally {

      }
    }

    return objects;
  }

  public static void createSimplePdf(String content, String outputFile)
      throws DocumentException, IOException {
    Document document = null;
    try {
      Object[] objects = getDocument(outputFile);
      document = (Document) objects[0];
      // Step 4—Add content.
      document.add(new Paragraph(content, setChineseFont()));
    } finally {
      // Step 5—Close the Document.
      try {
        if (null != document) document.close();
      } catch (Exception e) {

      }
    }
  }

  public static void createImagePdf(String inputFile, String outputFile)
      throws MalformedURLException, IOException, DocumentException {
    Document document = null;
    try {
      Object[] objects = getDocument(outputFile);
      document = (Document) objects[0];

      addAllImage(inputFile, document);
    } finally {
      // Step 5—Close the Document.
      try {
        if (null != document) document.close();
      } catch (Exception e) {

      }
    }
  }


  public static void addAllImage(String inputFile, Document document)
      throws BadElementException, MalformedURLException, IOException, DocumentException {
    File file = new File(inputFile);
    if (!file.exists()) {
      return;
    }
    if (file.isFile()) {
      String extension = FilenameUtils.getExtension(file.getName());
      if (Arrays.asList(IMAGE_FORMAT).contains(extension)) {
        addImage(inputFile, document);
      }
    } else {
      File[] files = file.listFiles();
      for (File file2 : files) {
        addAllImage(file2.getPath(), document);
      }
    }
  }


  /**
   * @param inputFile
   * @param document
   * @throws BadElementException
   * @throws MalformedURLException
   * @throws IOException
   * @throws DocumentException
   */

  private static void addImage(String inputFile, Document document)
      throws BadElementException, MalformedURLException, IOException, DocumentException {
    Image img = Image.getInstance(inputFile);
    // document.setPageSize(new Rectangle(img.getWidth() + 20, img.getHeight() + 20));
    if (!document.isOpen()) {
      document.open();
    }
    float width = document.getPageSize().getWidth();
    float height = document.getPageSize().getHeight();
    float imgWidth = img.getWidth();
    float imgHeight = img.getHeight();
    if (imgHeight > height || imgWidth > width) {
      float diffWidth = imgWidth - width;
      float diffHeight = imgHeight - height;
      float result = 1;
      if (diffHeight <= 0) {
        result = diffWidth / imgWidth;
      } else if (diffWidth <= 0) {
        result = diffHeight / imgHeight;
      } else {
        float result1 = diffWidth / imgWidth;
        float result2 = diffHeight / imgHeight;
        int re = Float.compare(result1, result2);
        if (re >= 0) {
          result = result1;
        } else {
          result = result2;
        }
      }
      img.scaleToFit(imgWidth * (1 - result), imgHeight * (1 - result));
    }

    // Image对象
    document.newPage();
    //// 图片填充方式
    img.setAlignment(Image.ALIGN_MIDDLE);
    // img.setRotationDegrees(-30);//旋转
    document.add(img);
  }

  public static Font setFont() throws DocumentException, IOException {
    BaseFont baseFont =
        BaseFont.createFont("STSong-Light", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    Font font = new Font(baseFont);
    return font;
  }

  public static Font setChineseFont() {
    BaseFont bf = null;
    Font fontChinese = null;
    try {
      bf = BaseFont.createFont("SIMKAI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
      fontChinese = new Font(bf, 12, Font.NORMAL);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fontChinese;
  }

  static class Base64ImageProvider extends AbstractImageProvider {

    @Override
    public Image retrieve(String src) {
      int pos = src.indexOf("base64,");
      try {
        if (src.startsWith("data") && pos > 0) {
          byte[] img = Base64.decode(src.substring(pos + 7));
          return Image.getInstance(img);
        } else {
          return Image.getInstance(src);
        }
      } catch (BadElementException ex) {
        return null;
      } catch (IOException ex) {
        return null;
      }
    }

    @Override
    public String getImageRootPath() {
      return null;
    }
  }

  /**
   * Creates a PDF with the words "Hello World"
   * 
   * @param file
   * @throws IOException
   * @throws DocumentException
   */
  public static void createPdf(String HTML, String file) throws IOException, DocumentException {
    // step 1
    Document document = null;
    InputStream is = null;
    PdfWriter writer = null;
    try {
      Object[] objects = getDocument(file);
      document = (Document) objects[0];
      writer = (PdfWriter) objects[1];
      // step 4
      // CSS
      CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
      StringBuffer cssBuffer = FileHelper.getHtmlCss(HTML);
      CssFile cssFile =
          XMLWorkerHelper.getCSS(new ByteArrayInputStream(cssBuffer.toString().getBytes()));
      cssResolver.addCss(cssFile);

      // HTML
      CssAppliers cssAppliers = new CssAppliersImpl(new UnicodeFontFactory());
      HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
      htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
      htmlContext.setImageProvider(new Base64ImageProvider());

      // Pipelines
      PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
      HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
      CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

      // XML Worker
      XMLWorker worker = new XMLWorker(css, true);
      XMLParser p = new XMLParser(worker);
      StringBuffer buffer = FileHelper.readFile(HTML);
      p.parse(new ByteArrayInputStream(buffer.toString().getBytes()));
      p.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // step 5
        if (null != document) document.close();
        if (null != is) is.close();
        if (null != writer) writer.close();
      } catch (Exception e) {

      }
    }
  }

  /**
   * 
   * 【功能描述：添加图片和文字水印】 【功能详细描述：功能详细描述】
   * 
   * @param srcFile 待加水印文件
   * @param destFile 加水印后存放地址
   * @param text 加水印的文本内容
   * @param image 水印图片
   * @param textWidth 文字横坐标
   * @param textHeight 文字纵坐标
   * @throws Exception
   */
  public static void addWaterMark(String srcFile, String destFile, String text, String image,
      int textWidth, int textHeight) throws Exception {
    PdfReader reader = null;
    PdfStamper stamper = null;
    try {
      // 待加水印的文件
      reader = new PdfReader(srcFile);
      // 加完水印的文件
      stamper = new PdfStamper(reader, new FileOutputStream(destFile));
      // 设置字体
      Font font = new Font(FontFamily.HELVETICA, 30);
      // 循环对每页插入水印
      int total = reader.getNumberOfPages();
      // text watermark
      Phrase p = new Phrase(text, font);
      // image watermark
      Image img = Image.getInstance(image);
      float w = img.getScaledWidth();
      float h = img.getScaledHeight();
      // transparency
      PdfGState gs1 = new PdfGState();
      gs1.setFillOpacity(0.5f);
      // properties
      PdfContentByte over;
      Rectangle pagesize;
      float x, y;
      // loop over every page
      for (int i = 1; i <= total; i++) {
        pagesize = reader.getPageSizeWithRotation(i);
        x = (pagesize.getLeft() + pagesize.getRight()) / 2;
        y = (pagesize.getTop() + pagesize.getBottom()) / 2;
        over = stamper.getOverContent(i);
        over.saveState();
        over.setGState(gs1);
        if (i % 2 == 1)
          ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
        else
          over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
        over.restoreState();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != stamper) stamper.close();
        if (null != reader) reader.close();
      } catch (Exception e) {}
    }
  }

  /*
   * 给所有pdf文件添加背景图片
   */
  public static final void addBackgroundImage(String src, String dest, String bg)
      throws IOException, DocumentException {
    PdfReader reader = null;
    PdfStamper stamper = null;
    try {
      reader = new PdfReader(src);
      stamper = new PdfStamper(reader, new FileOutputStream(dest));
      int n = reader.getNumberOfPages();
      PdfContentByte canvas;
      for (int p = 1; p <= n; p++) {
        canvas = stamper.getOverContent(p);
        Image image = Image.getInstance(bg);
        image.scaleAbsolute(PageSize.A4.rotate());
        image.setAbsolutePosition(0, 0);
        canvas.saveState();
        PdfGState state = new PdfGState();
        state.setFillOpacity(0.6f);
        canvas.setGState(state);
        canvas.addImage(image);
        canvas.restoreState();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != reader) reader.close();
        if (null != stamper) stamper.close();
      } catch (Exception e) {}
    }
  }

  public static void main(String[] args)
      throws MalformedURLException, IOException, DocumentException {
    createPdf("D:\\home\\RmadFile\\html\\2016\\07\\28\\csv\\shujuxuqiujilu\\shujuxuqiujilu.html",
        "D:\\home\\RmadFile\\html\\2016\\07\\28\\csv\\shujuxuqiujilu\\shujuxuqiujilu");
    /*
     * try { //createPdf(
     * "D:\\home\\RmadFile\\html\\2016\\07\\27\\word\\1468896723716\\1468896723716.html",
     * "D:\\home\\RmadFile\\html\\2016\\07\\27\\word\\1468896723716\\1468896723716.pdf");
     * waterMark("D:\\home\\RmadFile\\html\\2016\\07\\28\\csv\\shujuxuqiujilu\\shujuxuqiujilu.pdf",
     * "D:\\pic\\20150909140815.jpg",
     * "D:\\home\\RmadFile\\html\\2016\\07\\28\\csv\\shujuxuqiujilu\\shujuxuqiujilu_2.pdf", "正版授权",
     * 16 ); addWaterMark(
     * "D:\\home\\RmadFile\\html\\2016\\07\\28\\csv\\shujuxuqiujilu\\shujuxuqiujilu.pdf",
     * "D:\\home\\RmadFile\\html\\2016\\07\\28\\csv\\shujuxuqiujilu\\shujuxuqiujilu_3.pdf", "正版授权",
     * "D:\\pic\\20150909140815.jpg", 200, 300 ); } catch (Exception e) { e.printStackTrace(); }
     */


  }

}

