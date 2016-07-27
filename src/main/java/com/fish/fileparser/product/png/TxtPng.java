package com.fish.fileparser.product.png;

import com.fish.fileparser.product.html.TxtHtml;
import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.ItextUtils;

public class TxtPng implements AbstractPng {

	@Override
	public void createPng(String inputFile, String outputFile) throws Exception {
			TxtHtml txtHtml = new TxtHtml();
			txtHtml.createHtml(inputFile, outputFile + ".html");
			FileHelper.checkHtmlEndTag(outputFile + ".html");
			
			ItextUtils.createPdf(outputFile + ".html", outputFile + ".pdf");
			
			PdfPng pdfPng = new PdfPng();
			pdfPng.createPng(outputFile + ".pdf", outputFile);
	}

}

	