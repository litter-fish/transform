package com.fish.fileparser.product.txt;

import com.fish.fileparser.utils.FileHelper;

public class TxtText implements AbstractText {

	@Override
	public void createTxt(String inputFile, String outputFile) throws Exception {
		FileHelper.copyFile(inputFile, outputFile, true);
	}

}

	