package com.fish.fileparser.product.png;

import com.fish.fileparser.utils.ImageUtils;

public class PdfPng implements AbstractPng {

	@Override
	public void createPng(String inputFile, String outputFile) throws Exception {
		ImageUtils.convertPdf2Png(inputFile, outputFile);	
	}

}

	