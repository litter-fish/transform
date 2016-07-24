package com.fish.factory.fileparser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.core.IXWPFConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import com.fish.factory.utils.FileHelper;
import com.fish.factory.utils.Pdf2ImageUtils;

public class WordFactory implements AbstractFactory {
	
	private final static Logger log = Logger.getLogger(WordFactory.class);

	/**
	 * 将word转换为html
	 * @param inputFile 需要转换的word文档
	 * @param outputFile 转换为html文件名称（全路径）
	 * @throws Exception 
	 * @see
	 * @since 1.7
	 */
	@Override
	public void convert2Html(String inputFile, String outputFile) throws Exception {
		log.info("将word转换为html文件开始,输出文件 ["+ outputFile +".html]......");
		long startTime = System.currentTimeMillis();
		InputStream is = null;
		try {
			File file = new File(outputFile);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			File input = new File(inputFile);
			if(!file.exists()) {
				log.error("file not found:" + inputFile);
			}
			
			is = new FileInputStream(input);
			if(FileHelper.isWord2003(input)) {
				convertDoc2Html(is, outputFile);
			} else if(FileHelper.isWord2007(input)) {
				convertDocx2Html(is, outputFile);
			} else {
				RTFFactory.parseToHTML(inputFile, outputFile);
				//log.error("file format error");
			}
		} finally {
			try {
				if(null != is) {
					is.close();
				}
			} catch(Exception e) {
				
			}
		}
		//convert2Png(inputFile, outputFile);
		log.info("将word转换为html文件......ok");
		log.info("Generate " + outputFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
	}
	
	@Override
	public void convert2Png(String fileName, String outPutFile) throws Exception {
		boolean isSuccess = covert2Pdf(fileName, outPutFile);
		if(isSuccess) {
			long startTime = System.currentTimeMillis();
			log.info("将pdf转换为html文件开始,输出文件 [" + outPutFile + ".html]......");
			String baseName = FilenameUtils.getBaseName(fileName);
			int size = Pdf2ImageUtils.convertPdf2Png(outPutFile + ".pdf", outPutFile);
			FileHelper.writeHtmlFile(size, outPutFile, baseName);
			log.info("Generate " + outPutFile + ".html with " + (System.currentTimeMillis() - startTime) + " ms.");
			log.info("将pdf转换为html文件......ok");
		}
	}
	
	/**
	 * 将docx文件转换为html
	 * @param is
	 * @param fileOutName 输出文件的具体路径
	 * @throws IOException
	 * @see
	 * @since 1.7
	 * @exception
	 */
	private static void convertDocx2Html(InputStream is, String fileOutName) throws IOException {
		OutputStream out = null;
		XWPFDocument document = null;
		try {
			//final String root = fileOutName.substring(fileOutName.lastIndexOf("/") + 1);
			
			String[] outPutFiles = fileOutName.split("\\\\");
			outPutFiles = outPutFiles[outPutFiles.length - 1].split("/");
			final String root = outPutFiles[outPutFiles.length - 1];
			
			long startTime = System.currentTimeMillis();
			// 获取解析处理类
			document = new XWPFDocument(is);
			
			XHTMLOptions options = XHTMLOptions.create();//.indent(4);
			// Extract image
			File imageFolder = new File(fileOutName);
			if (!imageFolder.exists()) {
				imageFolder.mkdirs();
			}
			// 设置图片保存路径
			options.setExtractor(new FileImageExtractor(imageFolder));
			// URI resolver
			options.URIResolver(new IURIResolver() {
				@Override
				public String resolve(String uri) {
					return root + File.separatorChar + uri;
				}
			});
			out = new FileOutputStream(new File(fileOutName + ".html"));
			IXWPFConverter<XHTMLOptions> xhtmlCoverter = XHTMLConverter.getInstance();
			xhtmlCoverter.convert(document, out, options);
			//FileHelper.parseCharset(fileOutName + ".html");
			log.info("Generate " + fileOutName + " with " + (System.currentTimeMillis() - startTime) + " ms.");
		} finally {
			try {
				if(null != out) {
					out.close();
				}
				if(null != document) {
					document.close();
				}
			} catch(Exception e) {
				
			}
		}
	}

	/**
	 * 将doc文档转换为html文件
	 * @param fileName 需要转换的doc文件
	 * @param outPutFile 输出html文件的全路径
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @see
	 * @since 1.7
	 */
	private static void convertDoc2Html(InputStream is, String outPutFile)
			throws TransformerException, IOException, ParserConfigurationException {
		StreamResult streamResult = null;
		ByteArrayOutputStream out = null;
		try {
			String[] outPutFiles = outPutFile.split("\\\\");
			outPutFiles = outPutFiles[outPutFiles.length - 1].split("/");
			final String root = outPutFiles[outPutFiles.length - 1];
			// 将文件转换为poi数据结构
			HWPFDocument wordDocument = new HWPFDocument(is);
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			// 获取word转换为html的句柄
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);
			// 设置html文件中图片引入路径
			wordToHtmlConverter.setPicturesManager(new PicturesManager() {
				public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
						float heightInches) {
					return  root + "/" + suggestedName;
				}
			});
			wordToHtmlConverter.processDocument(wordDocument);
			// #start save pictures
			List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
			if (pics != null) {
				for (int i = 0; i < pics.size(); i++) {
					Picture pic = (Picture) pics.get(i);
					try {
						// 指定doc文档中转换后图片保存的路径
						pic.writeImageContent(new FileOutputStream(outPutFile + "/" + pic.suggestFullFileName()));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			// #end save pictures
			Document htmlDocument = wordToHtmlConverter.getDocument();
			DOMSource domSource = new DOMSource(htmlDocument);
			out = new ByteArrayOutputStream();
			streamResult = new StreamResult(out);
	
			TransformerFactory tf = TransformerFactory.newInstance();
			// 创建执行从 Source 到 Result 的复制的新 Transformer。
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 	// 文件编码方式
			serializer.setOutputProperty(OutputKeys.INDENT, "yes"); 		// indent 指定了当输出结果树时，Transformer 是否可以添加额外的空白；其值必须为 yes 或 no
			serializer.setOutputProperty(OutputKeys.METHOD, "html"); 		// 指定输出文件的后缀名
			serializer.transform(domSource, streamResult);
			String content = new String(out.toByteArray());
			FileHelper.writeFile(content, outPutFile + ".html");
			//FileHelper.parseCharset(outPutFile + ".html");
			//System.out.println(new String(out.toByteArray()));
		} finally {
			if(null != out) {
				out.close();
			}
		}
	}

	@Override
	public void convert2Text(String fileName, String outPutFile) throws Exception {
		String content = RTFFactory.parse(fileName);
		FileHelper.writeFile(content, outPutFile + ".txt");
	}
	
	public void doc2pdf(String docFileName) throws Exception{
    }
	
	public static void main(String[] args) throws Exception {
		WordFactory wordFactory = new WordFactory();
		
		try {
			//wordFactory.convert2Html("D:\\home\\RmadFile\\doc.doc", "D:\\home\\RmadFile\\doc");
			wordFactory.convert2Text("D:\\home\\RmadFile\\docx.docx", "D:\\home\\RmadFile\\docx");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean covert2Pdf(String inputFile, String outputFile) throws Exception {
		
		return false;
	}


}

	