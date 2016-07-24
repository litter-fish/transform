package com.fish.fileparser.product.txt;

import org.apache.log4j.Logger;

import com.fish.fileparser.utils.FileHelper;
import com.fish.fileparser.utils.TikaUtils;

public class WordText implements AbstractText {

	private final static Logger log = Logger.getLogger(WordText.class);

	@Override
	public void createTxt(String inputFile, String outputFile) throws Exception {
		String content = TikaUtils.parse(inputFile);
		FileHelper.writeFile(content, outputFile + ".txt");
	}
	
}

	