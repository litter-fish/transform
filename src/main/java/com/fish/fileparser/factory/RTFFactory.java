package com.fish.fileparser.factory;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.RTFHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.RTFPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.RTFPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.RTFText;

public class RTFFactory implements AbstractFactory {
	
	private final static Logger log = Logger.getLogger(RTFFactory.class);

	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		log.info("start convert RTF to html,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractHtml html = new RTFHtml();
		html.createHtml(fileName, outPutFile);
		log.info("将RTF转换为html文件......ok");
		log.info("convert success! Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}


	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		log.info("start convert RTF to png,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractPng png = new RTFPng();
		png.createPng(fileName, outPutFile);
		log.info("将RTF转换为png文件......ok");
		log.info("convert success! Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		log.info("start convert RTF to txt,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractText text = new RTFText();
		text.createTxt(fileName, outPutFile);
		log.info("将RTF转换为txt文件......ok");
		log.info("convert success! Generate " + outPutFile + ".txt with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
		log.info("start convert RTF to pdf,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractPdf html = new RTFPdf();
		html.createPdf(fileName, outPutFile);
		log.info("将RTF转换为pdf文件......ok");
		log.info("convert success! Generate " + outPutFile + ".pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
		return false;
	}
	
}

	