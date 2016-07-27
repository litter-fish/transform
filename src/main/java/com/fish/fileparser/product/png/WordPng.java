package com.fish.fileparser.product.png;

import org.apache.log4j.Logger;

import com.fish.fileparser.product.html.WordHtml;
import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.ItextUtils;

public class WordPng implements AbstractPng {

	private final static Logger log = Logger.getLogger(WordPng.class);

	@Override
	public void createPng(String inputFile, String outputFile) throws Exception {
			WordHtml wordHtml = new WordHtml();
			wordHtml.createHtml(inputFile, outputFile);
			
			FileHelper.checkHtmlEndTag(outputFile + ".html");
			
			ItextUtils.createPdf(outputFile + ".html", outputFile + ".pdf");
			
			PdfPng pdfPng = new PdfPng();
			pdfPng.createPng(outputFile + ".pdf", outputFile);
	}
	
	public static void main(String[] args) {
		WordPng wordPng = new WordPng();
		
		try {
			wordPng.createPng("D:\\home\\RmadFile\\html\\2016\\07\\27\\word\\1468896723716\\1468896723716.html", 
					"D:\\home\\RmadFile\\html\\2016\\07\\27\\word\\1468896723716\\1468896723716");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

	