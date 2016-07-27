package com.fish.fileparser.product.pdf;

import org.apache.log4j.Logger;

import com.fish.fileparser.utils.ItextUtils;
import com.fish.fileparser.utils.TikaUtils;

public class ExcelPdf implements AbstractPdf {
	
	private final static Logger log = Logger.getLogger(ExcelPdf.class);

	@Override
	public void createPdf(String inputFile, String outputFile) throws Exception {
		String message = TikaUtils.parse(inputFile);
		ItextUtils.createSimplePdf(message, outputFile);
	}
}

	