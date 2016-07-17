package com.fish.factory.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Pdf2ImageUtils {

	/**
	 * 将pdf文件转换为图片，一页文档将转为一张图片
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
			for (PDPage page : pdPageTree)
			{ 
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
	 * 获取PDF文档解析器 @param fileName @return @throws IOException @see @since
	 * 1.7 @exception
	 */
	public static PDDocument getPDDocument(String fileName) throws IOException {
		
		PDDocument document = PDDocument.load(new File(fileName));
		
		return document;
	}
}