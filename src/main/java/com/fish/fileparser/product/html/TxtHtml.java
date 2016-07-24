package com.fish.fileparser.product.html;

import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.TikaUtils;

public class TxtHtml implements AbstractHtml {

	@Override
	public void createHtml(String inputFile, String outputFile) throws Exception {
		TikaUtils.parseToHTML(inputFile, outputFile);
		FileHelper.parseH2(outputFile + ".html");
	}

}

	