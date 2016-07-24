package com.fish.fileparser.utils;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.fish.fileparser.factory.AbstractFactory;
import com.fish.fileparser.factory.CSVFactory;
import com.fish.fileparser.factory.ExcelFactory;
import com.fish.fileparser.factory.ImageFactory;
import com.fish.fileparser.factory.PPTFactory;
import com.fish.fileparser.factory.PdfFactory;
import com.fish.fileparser.factory.RTFFactory;
import com.fish.fileparser.factory.TextFactory;
import com.fish.fileparser.factory.WordFactory;

public class ConverFile {
	
	private final static Logger log = Logger.getLogger(ConverFile.class);
	
	private final static String[] WORD_FORMAT = {"DOC", "doc", "DOCX", "docx", "DOCM", "docm", "WPS", "wps"};
	private final static String[] EXCEL_FORMAT = {"XLS", "xls", "XLSX", "xlsx", "ET", "et"};
	private final static String[] POWERPOINT_FORMAT = {"PPT", "ppt", "PPTX", "pptx", "DPS", "dps"};
	private final static String[] PDF_FORMAT = {"PDF", "pdf"};
	private final static String[] TXT_FORMAT = {"TXT", "txt"};
	private final static String[] RTF_FORMAT = {"RTF", "rtf"};
	private final static String[] CSV_FORMAT = {"CSV", "csv"};
	private final static String[] IMAGE_FORMAT = {"PNG", "png", "JPG", "jpg", "JPEG", "jpeg", "BMP", "bmp"};
	
	
	public static final String converFile(String baseOutPutFilePath, String inputFile) throws Exception {
		
		File file = new File(inputFile);
		if(!file.exists()) {
			return "inputFile not found";
		}
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
		} else if(Arrays.asList(CSV_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "csv" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "csv" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new CSVFactory(); // 创建text解析工厂
		} else if(Arrays.asList(IMAGE_FORMAT).contains(extension)) {
			FileHelper.mkdirFiles(baseOutPutFilePath, "image" + File.separatorChar + baseName);
			outPutFile = baseOutPutFilePath + "image" + File.separatorChar + baseName + File.separatorChar + baseName;
			abstractFactory = new ImageFactory(); // 创建text解析工厂
		} else {
			log.info("文件解析工厂未创建");
			return null;
		}
		
		engineer = new ConvertEngineer(inputFile, outPutFile);
		engineer.convert(abstractFactory);
		
		return outPutFile;
	}
}

	