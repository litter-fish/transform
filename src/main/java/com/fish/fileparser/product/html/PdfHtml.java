package com.fish.fileparser.product.html;

import org.apache.commons.io.FilenameUtils;

import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.Pdf2ImageUtils;

public class PdfHtml implements AbstractHtml {

	@Override
	public void createHtml(String inputFile, String outputFile) throws Exception  {
		String baseName = FilenameUtils.getBaseName(inputFile);
		int size = Pdf2ImageUtils.convertPdf2Png(inputFile, outputFile);
		FileHelper.writeHtmlFile(size, outputFile, baseName);
	}

}

	