package com.fish.factory.fileparser;
/**
 * 
 * 类描述:
 *       文件转换抽象工厂类
 * @see	需要参见的其它类（可选）
 * @version 1.0 
 * @date 2016年3月4日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public interface AbstractFactory {
	
	/**
	 * 将文件转换为html文件
	 * @param fileName 需要转换的文件
	 * @param outPutFile 输出的文件
	 * @throws Exception
	 */
	public void convert2Html(String fileName, String outPutFile) throws Exception;
	
	/**
	 * 将文件转换为png文件
	 * @param fileName 需要转换的文件
	 * @param outPutFile 输出的文件
	 * @throws Exception
	 */
	public void convert2Png(String fileName, String outPutFile) throws Exception;
	/**
	 * 件文件转换为text文件
	 * @param fileName 需要转换的文件
	 * @param outPutFile 输出的文件
	 * @throws Exception
	 */
	public void convert2Text(String fileName, String outPutFile) throws Exception;
	
	public boolean covert2Pdf(String fileName, String outPutFile) throws Exception;
	
}
