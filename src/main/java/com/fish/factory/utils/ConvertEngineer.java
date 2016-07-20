package com.fish.factory.utils;

import com.fish.factory.fileparser.AbstractFactory;

/**
 * 
 * ������:
 *       �ļ�ת������
 * @see	��Ҫ�μ�������ࣨ��ѡ��
 * @version 1.0 
 * @date 2016��3��4��
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class ConvertEngineer {
	
	private String fileName;
	
	private String outPutFile;
	
	public ConvertEngineer() {}
	
	public ConvertEngineer(String fileName, String outPutFile) {
		this.fileName = fileName;
		this.outPutFile = outPutFile;
	}
	
	public void convert(AbstractFactory abstractFactory) throws Exception {
		abstractFactory.convert2Html(fileName, outPutFile);
	}
}
