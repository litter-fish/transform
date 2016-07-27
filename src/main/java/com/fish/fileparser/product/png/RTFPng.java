package com.fish.fileparser.product.png;

import com.fish.fileparser.product.html.RTFHtml;
import com.fish.fileparser.utils.ItextUtils;

public class RTFPng implements AbstractPng {

	@Override
	public void createPng(String inputFile, String outputFile) throws Exception {
		RTFHtml rtfHtml = new RTFHtml();
		rtfHtml.createHtml(inputFile, outputFile + ".html");
		ItextUtils.createPdf(outputFile + ".html", outputFile + ".pdf");
		
		PdfPng pdfPng = new PdfPng();
		pdfPng.createPng(outputFile + ".pdf", outputFile);
	}


}

	