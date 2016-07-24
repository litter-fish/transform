package com.fish.fileparser.factory;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.ImageHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.ImagePdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.ImagePng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.ImageText;

public class ImageFactory implements AbstractFactory {

	private final static Logger log = Logger.getLogger(ImageFactory.class);
	
	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		log.info("将image转换为png文件开始,输出文件 [" + outPutFile + "]......");
		long startTime = System.currentTimeMillis();
		AbstractHtml html = new ImageHtml();
		html.createHtml(fileName, outPutFile);
		log.info("将image转换为png文件......ok");
		log.info("Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		log.info("将image转换为png文件开始,输出文件 [" + outPutFile + "]......");
		long startTime = System.currentTimeMillis();
		AbstractPng png = new ImagePng();
		png.createPng(fileName, outPutFile);
		log.info("将image转换为png文件......ok");
		log.info("Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		log.info("将image转换为txt文件开始,输出文件 [" + outPutFile + ".txt]......");
		long startTime = System.currentTimeMillis();
		AbstractText text = new ImageText();
		text.createTxt(fileName, outPutFile);
		log.info("将image转换为txt文件......ok");
		log.info("Generate " + outPutFile + ".txt with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
		log.info("将image转换为pdf文件开始,输出文件 [" + outPutFile + ".pdf]......");
		long startTime = System.currentTimeMillis();
		AbstractPdf html = new ImagePdf();
		html.createPdf(fileName, outPutFile);
		log.info("将image转换为pdf文件......ok");
		log.info("Generate " + outPutFile + ".pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
		return false;
	}
}

	