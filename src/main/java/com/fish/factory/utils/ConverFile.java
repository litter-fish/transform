package com.fish.factory.utils;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.fish.factory.fileparser.AbstractFactory;
import com.fish.factory.fileparser.ExcelFactory;
import com.fish.factory.fileparser.PPTFactory;
import com.fish.factory.fileparser.PdfFactory;
import com.fish.factory.fileparser.RTFFactory;
import com.fish.factory.fileparser.TextFactory;
import com.fish.factory.fileparser.WordFactory;

public class ConverFile {
	
	private final static Logger log = Logger.getLogger(ConverFile.class);
	
	private final static String[] WORD_FORMAT = {"DOC", "doc", "DOCX", "docx", "WPS", "wps"};
	private final static String[] EXCEL_FORMAT = {"XLS", "xls", "XLSX", "xlsx", "ET", "et"};
	private final static String[] POWERPOINT_FORMAT = {"PPT", "ppt", "PPTX", "pptx", "DPS", "dps"};
	private final static String[] PDF_FORMAT = {"PDF", "pdf"};
	private final static String[] TXT_FORMAT = {"TXT", "txt"};
	private final static String[] RTF_FORMAT = {"RTF", "rtf"};
	
	public static final String converFile2Html(String baseOutPutFilePath, String fileUrls) throws Exception {
		
		File file = new File(fileUrls);
		String extension = FilenameUtils.getExtension(file.getName());
		String baseName = FilenameUtils.getBaseName(file.getName());

		String outPutFile = "";
		ConvertEngineer engineer = null;
		AbstractFactory abstractFactory = null;
		if(Arrays.asList(WORD_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "word" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "word" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new WordFactory(); // 创建word解析工厂
		} else if(Arrays.asList(POWERPOINT_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "ppt" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "ppt" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new PPTFactory(); // 创建ppt解析工厂
		} else if(Arrays.asList(EXCEL_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "excel" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "excel" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new ExcelFactory(); // 创建excel解析工厂
		} else if (Arrays.asList(PDF_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "pdf" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "pdf" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new PdfFactory(); // 创建pdf解析工厂
		} else if (Arrays.asList(TXT_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "txt" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "txt" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new TextFactory(); // 创建text解析工厂
		} else if (Arrays.asList(RTF_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "rtf" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "rtf" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new RTFFactory(); // 创建text解析工厂
		} else {
			log.info("文件解析工厂未创建");
			return null;
		}
		engineer = new ConvertEngineer(fileUrls, outPutFile);
		engineer.convert(abstractFactory);
		
		return outPutFile;
	}
}

	