package com.fish.fileparser.factory;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.AbstractHtml;
import com.fish.fileparser.product.html.PPTHtml;
import com.fish.fileparser.product.pdf.AbstractPdf;
import com.fish.fileparser.product.pdf.PPTPdf;
import com.fish.fileparser.product.png.AbstractPng;
import com.fish.fileparser.product.png.PPTPng;
import com.fish.fileparser.product.txt.AbstractText;
import com.fish.fileparser.product.txt.PPTText;

public class PPTFactory implements AbstractFactory {
	
	private final static Logger log = Logger.getLogger(PPTFactory.class);

	@Override
	public void convert2Html(String inputFile, String outputFile) throws Exception {
		log.info("将PPT或PPTX转换为html文件开始,输出文件 [" + outputFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractHtml html = new PPTHtml();
		html.createHtml(inputFile, outputFile);
		log.info("将PPT或PPTX转换为html文件......ok");
		log.info("Generate " + outputFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		log.info("start convert PPT to png,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractPng png = new PPTPng();
		png.createPng(fileName, outPutFile);
		log.info("将PPT转换为png文件......ok");
		log.info("convert success! Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		log.info("start convert PPT to txt,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractText text = new PPTText();
		text.createTxt(fileName, outPutFile);
		log.info("将PPT转换为txt文件......ok");
		log.info("convert success! Generate " + outPutFile + ".txt with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
		log.info("start convert PPT to pdf,out file [" + outPutFile + ".html]......");
		long startTime = System.currentTimeMillis();
		AbstractPdf html = new PPTPdf();
		html.createPdf(fileName, outPutFile);
		log.info("将PPT转换为pdf文件......ok");
		log.info("convert success! Generate " + outPutFile + ".pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
		return false;
	}
	
}
	