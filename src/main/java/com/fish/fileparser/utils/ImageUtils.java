package com.fish.fileparser.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.hslf.model.HeadersFooters;
import org.apache.poi.hslf.usermodel.HSLFGroupShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFGroupShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

public class ImageUtils {

  /**
   * 将pdf文件转换为图片，一页文档将转为一张图片
   * 
   * @param fileName
   * @param outputFile
   * @return
   */

  public static int convertPdf2Png(String fileName, String outputFile) {
    PDDocument document = null;
    int pageCounter = 0;
    try {
      document = getPDDocument(fileName);
      String outputPrefix = outputFile;// 保存图片的位置
      File file = new File(outputPrefix);
      if (!file.exists()) {
        file.mkdirs();
      }
      String imageFormat = "png";// 格式

      PDFRenderer pdfRenderer = new PDFRenderer(document);

      PDPageTree pdPageTree = document.getPages();
      for (PDPage page : pdPageTree) {
        BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 100, ImageType.RGB);
        // 生成图片保存路径 /TLCB_FILEHANDLER/rmad/20160129/pdf/test
        fileName = outputPrefix + "/" + (++pageCounter) + "." + imageFormat;
        ImageIOUtil.writeImage(bim, fileName, 100);
      }
      document.close();



    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != document) {
        try {
          document.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return pageCounter;
  }

  /**
   * 获取PDF文档解析器 @param fileName @return @throws IOException @see @since 1.7 @exception
   */
  public static PDDocument getPDDocument(String fileName) throws IOException {

    PDDocument document = PDDocument.load(new File(fileName));

    return document;
  }

  public static int convertPpt2Png(String inputFile, String outputFile) throws IOException {
    InputStream is = null;
    int size = 0;
    try {
      is = new FileInputStream(new File(inputFile));
      if (FileHelper.isPPT2003(new File(inputFile))) {
        size = ImageUtils.convertPPT2Png(is, outputFile);
      } else {
        size = ImageUtils.convertPPTX2Png(is, outputFile);
      }
    } finally {
      try {
        if (null != is) {
          is.close();
        }
      } finally {}
    }
    return size;
  }


  /**
   * 将ppt文档转换为图片文件，一页文档将转为一张图片 @param fileName @param outputFile @throws IOException @see @since
   * 1.7 @exception
   */
  private static int convertPPT2Png(InputStream is, String outputFile) throws IOException {
    int size = 1;
    FileOutputStream out = null;
    HSLFSlideShow ppt = null;
    try {
      ppt = new HSLFSlideShow(is);

      Dimension pgsize = ppt.getPageSize();

      // List<HSLFSlide> slide = ppt.getSlides();

      for (HSLFSlide slide : ppt.getSlides()) {
        // read hyperlinks from the text runs
        for (HSLFShape shape : slide.getShapes()) {
          if (shape instanceof HSLFTextShape) {
            setHSLFTxtFontFamily(shape);
          } else if (shape instanceof HSLFGroupShape) {
            HSLFGroupShape xslfGroupShape = (HSLFGroupShape) shape;
            List<HSLFShape> xslfShapes = xslfGroupShape.getShapes();
            for (HSLFShape xslfShape : xslfShapes) {
              if (xslfShape instanceof HSLFTextShape) {
                setHSLFTxtFontFamily(xslfShape);
              }
            }
          } else {
            // System.out.println(shape);
          }
        }
        out = drawImage(outputFile, size, pgsize, null, slide);
        size++;

        HeadersFooters header = slide.getHeadersFooters();
        if (header.isFooterVisible()) {
          String footerText = header.getFooterText();
          System.out.println(footerText);
        }
        if (header.isUserDateVisible()) {
          String customDate = header.getDateTimeText();
          System.out.println(customDate);
        }
        if (header.isSlideNumberVisible()) {
          int slideNUm = slide.getSlideNumber();
          System.out.println(slideNUm);
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != ppt) {
          ppt.close();
        }
        if (null != out) {
          out.close();
        }
      } catch (Exception e) {

      }
    }
    return size - 1;
  }

  /**
   * 将pptx文档转换为图片文件，一页文档将转为一张图片 @param fileName @param outputFile @throws IOException @see @since
   * 1.7 @exception
   */
  private static int convertPPTX2Png(InputStream is, String outputFile) throws IOException {
    FileOutputStream out = null;
    int size = 0;
    XMLSlideShow ppt = null;
    try {
      ppt = new XMLSlideShow(is);
      Dimension pgsize = ppt.getPageSize();
      // append a new slide to the end
      for (XSLFSlide slide : ppt.getSlides()) {
        for (XSLFShape shape : slide.getShapes()) {
          if (shape instanceof XSLFTextShape) {
            XSLFTextShape txtshape = (XSLFTextShape) shape;
            for (XSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
              List<XSLFTextRun> textRunList = textPara.getTextRuns();
              for (XSLFTextRun textRun : textRunList) {
                textRun.setFontFamily("宋体");
              }
            }
          } else if (shape instanceof XSLFGroupShape) {
            XSLFGroupShape xslfGroupShape = (XSLFGroupShape) shape;
            List<XSLFShape> xslfShapes = xslfGroupShape.getShapes();
            for (XSLFShape xslfShape : xslfShapes) {
              if (xslfShape instanceof XSLFTextShape) {
                XSLFTextShape txtshape = (XSLFTextShape) xslfShape;
                for (XSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
                  List<XSLFTextRun> textRunList = textPara.getTextRuns();
                  for (XSLFTextRun textRun : textRunList) {
                    textRun.setFontFamily("宋体");
                  }
                }
              }
            }
          } else {
            // System.out.println(shape);
          }
        }

        out = drawImage(outputFile, size, pgsize, slide, null);
        size++;
      }
    } finally {
      try {
        if (null != out) {
          out.close();
        }
        if (null != ppt) {
          ppt.close();
        }
      } catch (Exception e) {}
    }
    return size;
  }

  /**
   * 保存图片
   * 
   * @param outputFile
   * @param size
   * @param pgsize
   * @param slide
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private static <T> FileOutputStream drawImage(String outputFile, int size, Dimension pgsize,
      XSLFSlide xslfSlide, HSLFSlide hslfSlide) throws FileNotFoundException, IOException {
    FileOutputStream out = null;
    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = img.createGraphics();
    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
    if (null != xslfSlide) {
      xslfSlide.getSlideShow();
      // render
      xslfSlide.draw(graphics);
    }
    if (null != hslfSlide) {
      hslfSlide.getSlideShow();
      // render
      hslfSlide.draw(graphics);
    }
    // save the output
    FileUtils.mksFile(outputFile);
    out = new FileOutputStream(outputFile + File.separator + size + ".png");
    javax.imageio.ImageIO.write(img, "png", out);
    return out;
  }

  /**
   * @param shape
   */
  private static void setHSLFTxtFontFamily(HSLFShape shape) {
    HSLFTextShape txtshape = (HSLFTextShape) shape;
    for (HSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
      List<HSLFTextRun> textRunList = textPara.getTextRuns();
      for (HSLFTextRun textRun : textRunList) {
        textRun.setFontFamily("宋体");
      }
    }
  }



}
