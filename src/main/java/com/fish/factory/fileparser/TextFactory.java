package com.fish.factory.fileparser;

import com.fish.factory.utils.FileHelper;

public class TextFactory implements AbstractFactory {

	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		StringBuffer buffer = FileHelper.readFile(fileName, "<br />");
		FileHelper.write2Html(buffer, outPutFile);
		FileHelper.parseH2(outPutFile);
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
			
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		String content = RTFFactory.parse(fileName);
		FileHelper.writeFile(content, outPutFile + ".txt");
	}

	@Override
	public boolean covert2Pdf(String fileName, String outPutFile) throws Exception {
		return false;
	}

}
