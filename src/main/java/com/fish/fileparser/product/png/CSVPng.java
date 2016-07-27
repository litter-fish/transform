package com.fish.fileparser.product.png;

import com.fish.fileparser.product.html.CSVHtml;
import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.ItextUtils;

public class CSVPng implements AbstractPng {

	@Override
	public void createPng(String inputFile, String outputFile) throws Exception {
		CSVHtml csvHtml = new CSVHtml();
		csvHtml.createHtml(inputFile, outputFile + ".html");
		
		FileHelper.checkHtmlEndTag(outputFile + ".html");
		ItextUtils.createPdf(outputFile + ".html", outputFile + ".pdf");
		
		PdfPng pdfPng = new PdfPng();
		pdfPng.createPng(outputFile + ".pdf", outputFile);
	}

}

	