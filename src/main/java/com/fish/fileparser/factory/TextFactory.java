package com.fish.fileparser.factory;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.TxtHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.TxtPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.TxtPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.TxtText;

public class TextFactory implements AbstractFactory {
	
	private final static Logger log = Logger.getLogger(TextFactory.class);

	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		log.info("start convert Txt to png,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractHtml html = new TxtHtml();
		html.createHtml(fileName, outPutFile);
		log.info("将Txt转换为png文件......ok");
		log.info("convert success! Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		log.info("start convert Txt to png,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractPng png = new TxtPng();
		png.createPng(fileName, outPutFile);
		log.info("将Txt转换为png文件......ok");
		log.info("convert success! Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		log.info("start convert Txt to txt,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractText text = new TxtText();
		text.createTxt(fileName, outPutFile);
		log.info("将Txt转换为txt文件......ok");
		log.info("convert success! Generate " + outPutFile + ".txt with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
		log.info("start convert Txt to pdf,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractPdf html = new TxtPdf();
		html.createPdf(fileName, outPutFile);
		log.info("将Txt转换为pdf文件......ok");
		log.info("convert success! Generate " + outPutFile + ".pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
		return false;
	}

}
