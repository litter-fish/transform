package com.fish.factory.fileparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.fish.factory.utils.FileHelper;
import com.fish.factory.utils.Pdf2ImageUtils;

/**
 * 
 * 类描述: pdf文件转换工厂类
 * 
 * @see 需要参见的其它类（可选）
 * @version 1.0
 * @date 2016年3月7日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class PdfFactory implements AbstractFactory {

	private final static Logger log = Logger.getLogger(PdfFactory.class);

	/**
	 * 将pdf文档转换为html文件
	 * @param fileName 
	 * @param outputFile 
	 */
	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("start convert pdf to Html,out file [" + outPutFile + ".html]......");
		String baseName = FilenameUtils.getBaseName(fileName);
		int size = Pdf2ImageUtils.convertPdf2Png(fileName, outPutFile);
		FileHelper.writeHtmlFile(size, outPutFile, baseName);
		log.info("convert success! Generate " + outPutFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) {
		// TODO Auto-generated method stub
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		PdfFactory.convertPdf2Text(fileName, outPutFile);
	}

	

	/**
	 * 将pdf文件转换为txt @param fileName @param outPutFile @see @since 1.7 @exception
	 */
	private static void convertPdf2Text(String fileName, String outPutFile) {
		File file = new File(fileName);
		FileInputStream in = null;
		try {
			/*in = new FileInputStream(fileName);
			// 新建一个PDF解析器对象
			PDFParser parser = new PDFParser(in);
			// 对PDF文件进行解析
			parser.parse();
			// 获取解析后得到的PDF文档对象
			PDDocument pdfdocument = parser.getPDDocument();
			// 新建一个PDF文本剥离器
			PDFTextStripper stripper = new PDFTextStripper();
			// 从PDF文档对象中剥离文本
			String result = stripper.getText(pdfdocument);
			System.out.println("PDF文件" + file.getAbsolutePath() + "的文本内容如下：");
			System.out.println(result);*/

		} catch (Exception e) {
			System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + e);
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	@Override
	public boolean covert2Pdf(String fileName, String outPutFile) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		PdfFactory pdfFactory = new PdfFactory();
		
		pdfFactory.convert2Html("D://home/RmadFile/588.pdf", "D://home/RmadFile/588");
		
	}

}