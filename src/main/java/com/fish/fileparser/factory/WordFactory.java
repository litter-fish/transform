package com.fish.fileparser.factory;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.WordHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.WordPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.WordPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.WordText;

public class WordFactory implements AbstractFactory {
	
	private final static Logger log = Logger.getLogger(WordFactory.class);

	/**
	 * 将word转换为html
	 * @param inputFile 需要转换的word文档
	 * @param outputFile 转换为html文件名称（全路径）
	 * @throws Exception 
	 * @see
	 * @since 1.7
	 */
	@Override
	public void convert2Html(String inputFile, String outputFile) throws Exception {
		log.info("将word转换为html文件开始,输出文件 ["+ outputFile +".html]......");
		long startTime = System.currentTimeMillis();
		AbstractHtml html = new WordHtml();
		html.createHtml(inputFile, outputFile);
		log.info("将word转换为html文件......ok");
		log.info("Generate " + outputFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
	}
	
	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		log.info("start convert Word to png,out file [" + outPutFile + ".png]......");
		long startTime = System.currentTimeMillis();
		AbstractPng png = new WordPng();
		png.createPng(fileName, outPutFile);
		log.info("将Word转换为png文件......ok");
		log.info("convert success! Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		log.info("start convert Word to txt,out file [" + outPutFile + ".txt]......");
		long startTime = System.currentTimeMillis();
		AbstractText text = new WordText();
		text.createTxt(fileName, outPutFile);
		log.info("将Word转换为Word文件......ok");
		log.info("convert success! Generate " + outPutFile + ".Word with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
		log.info("start convert Word to pdf,out file [" + outPutFile + ".pdf]......");
		long startTime = System.currentTimeMillis();
		AbstractPdf html = new WordPdf();
		html.createPdf(fileName, outPutFile);
		log.info("将Word转换为pdf文件......ok");
		log.info("convert success! Generate " + outPutFile + ".pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
		return false;
	}

}

	