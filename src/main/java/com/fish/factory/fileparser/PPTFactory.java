package com.fish.factory.fileparser;

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

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
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

import com.fish.factory.utils.FileHelper;
import com.fish.factory.utils.FileUtils;
import com.fish.factory.utils.Pdf2ImageUtils;

public class PPTFactory implements AbstractFactory {
	
	private final static Logger log = Logger.getLogger(PPTFactory.class);

	@Override
	public void convert2Html(String inputFile, String outputFile) throws Exception {
		log.info("将PPT或PPTX转换为html文件开始,输出文件 [" + outputFile + ".html]......");
		long startTime = System.currentTimeMillis();
		InputStream is = null;
		int size = 0;
		String baseName = FilenameUtils.getBaseName(inputFile);
		try {
			is = new FileInputStream(new File(inputFile));
			if( FileHelper.isPPT2003(new File(inputFile)) ) {
				size = convertPPT2Png(is, outputFile);
			} else {
				size = convertPPTX2Png(is, outputFile);
			}
		} finally {
			try { if(null != is) { is.close(); } } finally { }
		}
		FileHelper.writeHtmlFile(size, outputFile, baseName);
		FileHelper.parseCharset(outputFile + ".html");
		//convert2Png(inputFile, outputFile);
		log.info("将PPT或PPTX转换为html文件......ok");
		log.info("Generate " + outputFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
		System.out.println("Generate " + outputFile + ".html with " + (System.currentTimeMillis() - startTime) / 1000 + " s.");
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		boolean isSuccess = covert2Pdf(fileName, outPutFile);
		if(isSuccess) {
			long startTime = System.currentTimeMillis();
			log.info("将pdf转换为html文件开始,输出文件 [" + outPutFile + ".html]......");
			String baseName = FilenameUtils.getBaseName(fileName);
			int size = Pdf2ImageUtils.convertPdf2Png(outPutFile + ".pdf", outPutFile);
			FileHelper.writeHtmlFile(size, outPutFile, baseName);
			log.info("Generate " + outPutFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
			log.info("将pdf转换为html文件......ok");
		}
	}

	
	/**
	 * 将ppt文档转换为图片文件，一页文档将转为一张图片
	 * @param fileName
	 * @param outputFile
	 * @throws IOException
	 * @see
	 * @since 1.7
	 * @exception
	 */
	private static int convertPPT2Png(InputStream is, String outputFile) throws IOException {
		int size = 1;
		FileOutputStream out = null;
		HSLFSlideShow ppt = null;
		try {
			ppt = new HSLFSlideShow(is);

			Dimension pgsize = ppt.getPageSize();
	
			//List<HSLFSlide> slide = ppt.getSlides();
			
			for (HSLFSlide slide : ppt.getSlides()) {
		        //read hyperlinks from the text runs
				for(HSLFShape shape : slide.getShapes()) {
					if ( shape instanceof HSLFTextShape ) {
						setHSLFTxtFontFamily(shape);
					} else if(shape instanceof HSLFGroupShape) {
						HSLFGroupShape xslfGroupShape = (HSLFGroupShape) shape;
						List<HSLFShape> xslfShapes = xslfGroupShape.getShapes();
						for(HSLFShape xslfShape : xslfShapes) {
							if ( xslfShape instanceof HSLFTextShape ) {
								setHSLFTxtFontFamily(xslfShape);
							}
						}
					} else {
						//System.out.println(shape);
					}
				}
		        out = drawImage(outputFile, size, pgsize, null, slide);
				size++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != ppt) {
					ppt.close();
				}
				if(null != out) {
					out.close();
				}
			} catch (Exception e) {
				
			}
		}
		return size - 1;
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
	
	/**
	 * 将pptx文档转换为图片文件，一页文档将转为一张图片
	 * @param fileName
	 * @param outputFile
	 * @throws IOException
	 * @see
	 * @since 1.7
	 * @exception
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
				for(XSLFShape shape : slide.getShapes()) {
					if ( shape instanceof XSLFTextShape ) {
						XSLFTextShape txtshape = (XSLFTextShape) shape;
						for (XSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
							List<XSLFTextRun> textRunList = textPara.getTextRuns();
							for (XSLFTextRun textRun : textRunList) {
								textRun.setFontFamily("宋体");
							}
						}
					} else if(shape instanceof XSLFGroupShape) {
						XSLFGroupShape xslfGroupShape = (XSLFGroupShape) shape;
						List<XSLFShape> xslfShapes = xslfGroupShape.getShapes();
						for(XSLFShape xslfShape : xslfShapes) {
							if ( xslfShape instanceof XSLFTextShape ) {
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
						//System.out.println(shape);
					}
				}
				
				out = drawImage(outputFile, size, pgsize, slide, null);
				size++;
			}
		} finally {
			try {
				if(null != out) {
					out.close();
				}
				if(null != ppt) {
					ppt.close();
				}
			} catch (Exception e) {
			}
		}
		return size;
	}

	/**
	 * 保存图片
	 * @param outputFile
	 * @param size
	 * @param pgsize
	 * @param slide
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static <T> FileOutputStream drawImage(String outputFile, int size, Dimension pgsize, XSLFSlide xslfSlide, HSLFSlide hslfSlide)
			throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
		if(null != xslfSlide) {
			xslfSlide.getSlideShow();
			// render
			xslfSlide.draw(graphics);
		}
		if(null != hslfSlide) {
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
	 * 保存图片
	 * @param img
	 * @param outputFile
	 * @param i
	 * @param out
	 * @throws IOException
	 * @see
	 * @since 1.7
	 * @exception
	 */

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		// TODO Auto-generated method stub
		
			
	}

	@Override
	public boolean covert2Pdf(String inputFile, String outputFile) throws Exception {
		
		return true;	
	}
	
	public static void main(String[] args) throws Exception {
		PPTFactory pptFactory = new PPTFactory();
		//pptFactory.convert2Html("D://home/RmadFile//dps2.dps", "D://home/RmadFile//dps2");
		pptFactory.convert2Html("D://home/RmadFile//pptx.ppt", "D://home/RmadFile//pptx");
	}
	
}
	