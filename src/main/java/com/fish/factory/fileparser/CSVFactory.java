package com.fish.factory.fileparser;

import java.io.File;

import org.apache.tika.Tika;

import com.fish.factory.utils.FileHelper;

public class CSVFactory implements AbstractFactory {

	public static void main(String[] args) {
		CSVFactory csvFactory = new CSVFactory();
		try {
			csvFactory.convert2Html("D://home/RmadFile//shujuxuqiujilu.csv", "D://home/RmadFile//html//shujuxuqiujilu");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		String content = readFile(fileName);
		
		String[] table = content.replaceAll("\n", "").split("\r");
		
		StringBuffer html = new StringBuffer();
		String[] split1 = fileName.split("/");
		
		String[] split2 = split1[split1.length - 1].split("\\\\");
		
		html
			.append("<!DOCTYPE html><html><head>")
			.append("<title>").append(split2[split2.length - 1].substring(0, split2[split2.length - 1].length() - 4)).append("</title>")
			.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\">")
			.append("<style type=\"text/css\">")
			.append("table.gridtable {font-family: verdana,arial,sans-serif;")
			.append("font-size:11px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;}")
			.append("table.gridtable th {border-width: 1px;	padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;}")
			.append("table.gridtable td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}")
			.append("</style>").append("</head><body><table class=\"gridtable\">");
		StringBuffer headHtml = new StringBuffer();
		headHtml.append("<tr>");
		
		String[] headTable = table[0].split(",");
		for(String value : headTable) {
			headHtml.append("<th>").append(value).append("</th>");
		}
		headHtml.append("</tr>");
		StringBuffer bodyHtml = new StringBuffer();
		for(int offset = 1; offset < table.length; offset++) {
			String[] bodyTable = table[offset].split(",");
			if(0 == bodyTable.length) {
				continue;
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("<tr>");
			for(String value : bodyTable) {
				buffer.append("<td>").append(value).append("</td>");
			}
			if(headTable.length - bodyTable.length > 0) {
				for(int diff = headTable.length - bodyTable.length; diff > 0; diff--) {
					buffer.append("<td>").append("").append("</td>");
				}
			}
			buffer.append("</tr>");
			bodyHtml.append(buffer);
		}
		html.append(headHtml).append(bodyHtml).append("</table></body></html>");
		
		FileHelper.writeFile(html.toString(), outPutFile + ".html");
		FileHelper.parse(outPutFile + ".html"); 
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		String content = readFile(fileName);
	}

	@Override
	public boolean covert2Pdf(String fileName, String outPutFile) throws Exception {
		return false;
	}
	
	private String readFile(String fileName)  throws Exception  {
		Tika tika = new Tika();
		return tika.parseToString(new File(fileName));
	}
	
	

}

	