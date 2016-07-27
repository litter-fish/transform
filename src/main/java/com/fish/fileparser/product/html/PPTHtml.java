package com.fish.fileparser.product.html;

import org.apache.commons.io.FilenameUtils;

import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.ImageUtils;

public class PPTHtml implements AbstractHtml {

	@Override
	public void createHtml(String inputFile, String outputFile) throws Exception {
		String baseName = FilenameUtils.getBaseName(inputFile);
		int size = ImageUtils.convertPpt2Png(inputFile, outputFile);
		FileHelper.writeHtmlFile(size, outputFile, baseName);
		FileHelper.parseH2(outputFile + ".html");
	}
	
}

	