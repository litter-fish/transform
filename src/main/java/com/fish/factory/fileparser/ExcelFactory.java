package com.fish.factory.fileparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.w3c.dom.Document;

import com.fish.factory.utils.FileHelper;
import com.fish.factory.utils.Xlsx2Xls;

/**
 * 
 * 类描述:
 *       excel文件转换工厂
 * @see	AbstractFactory
 * @version 1.0 
 * @date 2016年3月7日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class ExcelFactory implements AbstractFactory {

	private final static Logger log = Logger.getLogger(ExcelFactory.class);
	
	/**
	 * 将excel转换为html
	 * @param fileName 需要转换的word文档
	 * @param outPutFile 转换为html文件名称（全路径）
	 * @throws FileNotFoundException
	 * @see
	 * @since 1.7
	 */
	@Override
	public void convert2Html(String fileName, String outPutFile) throws Exception {
		log.info("将excel转换为html文件开始,输出文件 ["+ outPutFile +".html]......");
		long startTime = System.currentTimeMillis();
		InputStream is = null;
		/*File sourcefile = new File(fileName);
        is = new FileInputStream(sourcefile);*/
		
		try {
			File file = new File(fileName);
			if(!file.exists()) {
				log.error("file not found:" + fileName);
			}
			if(!FileHelper.isExcel2003(file)) {
				try {
					Xlsx2Xls xlsx2Xls = new Xlsx2Xls(file);
					xlsx2Xls.xlsx2XlsProgress();
					
					/*XLS2CSV xls2csv = new XLS2CSV("C:\\Time.xls","c:\\out.csv");  
			        xls2csv.process();  */
					
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file = new File(fileName.replace(".xlsx", ".xls"));
			is = new FileInputStream(file);
			convertExcel2Html(is, outPutFile);
		} finally {
			try {
				if(null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("将excel转换为html文件......ok");
		log.info("Generate " + outPutFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 将xls(03版本)文件转换为html文件
	 * @param fileName 需要转换的xls文件
	 * @param outPutFile 转换为html文件的名称
	 * @see
	 * @since 1.7
	 * @exception
	 */
	private static void convertExcel2Html(InputStream is, String outPutFile) {
		FileWriter out = null;
		try {
            //Document doc = ExcelToHtmlConverter.process( new File(fileName) );
            HSSFWorkbook workBook = new HSSFWorkbook(is);
            ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());  
            converter.setOutputColumnHeaders(false);
            converter.setOutputRowNumbers(false);
            converter.setOutputLeadingSpacesAsNonBreaking(false);
            converter.setOutputRowNumbers(false);
            converter.processWorkbook(workBook);  
            Document doc = converter.getDocument();
 
            out = new FileWriter( outPutFile + ".html" );
            DOMSource domSource = new DOMSource( doc );
            StreamResult streamResult = new StreamResult( out );
 
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            // TODO set encoding from a command argument
            serializer.setOutputProperty( OutputKeys.ENCODING, "GB2312" ); 			// 文件编码方式
            serializer.setOutputProperty( OutputKeys.INDENT, "no" ); 				// indent 指定了当输出结果树时，Transformer 是否可以添加额外的空白；其值必须为 yes 或 no
            serializer.setOutputProperty( OutputKeys.METHOD, "html" );				// 指定输出文件的后缀名
            serializer.transform(domSource, streamResult);
            
           // FileHelper.parseCharset(outPutFile + ".html");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
        	try {
	        	if(null != out) {
					out.close();
	        	}
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		String content = RTFFactory.parse(fileName);
		FileHelper.writeFile(content, outPutFile + ".txt");
		
	}
	
	public static void main(String[] args) {
		ExcelFactory excelFactory = new ExcelFactory();
		try {
			excelFactory.convert2Html("D:\\home\\RmadFile\\shujuxuqiujilu.xls", "D:\\home\\RmadFile\\shujuxuqiujilu");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean covert2Pdf(String fileName, String outPutFile) throws Exception {
		// TODO Auto-generated method stub
		return false;
		
			
	}


}

	