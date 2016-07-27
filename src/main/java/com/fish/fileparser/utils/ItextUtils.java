package com.fish.fileparser.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextUtils {
	
	public static final Document getDocument(String file) {
		Document document = null;
		try {
			// Step 1—Create a Document.
			document = new Document();
			// Step 2—Get a PdfWriter instance.
			PdfWriter.getInstance(document, new FileOutputStream(file + ".pdf"));
			// Step 3—Open the Document.
			document.open();
		} catch(Exception e) {
			try {
				if(null != document) {
					document.close();
				}
			} finally {
				
			}
		}
		
		return document;
	}

	public static void createSimplePdf(String content, String outputFile) throws DocumentException, IOException {
		Document document = null;
		try {
			document = getDocument(outputFile);
			
			// Step 4—Add content.
			document.add(new Paragraph(content, setChineseFont()));
		} finally {
			// Step 5—Close the Document.
			try {
				if(null != document) document.close();
			} catch(Exception e) {
				
			}
		}
	}
	
	public static void createImagePdf(String inputFile, String outputFile) throws MalformedURLException, IOException, DocumentException {
		Document document = null;
		try {
			document = getDocument(outputFile);
			Image img = Image.getInstance(inputFile);  
			document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
			if(!document.isOpen()) {
				document.open();
			}
			//Image对象  
			document.newPage();  
			img.setAlignment(Image.ALIGN_CENTER);  
			img.setBorder(Image.BOX);  
			img.setBorderWidth(10);
			img.setBorderWidthBottom(10);
			img.setBorderColor(BaseColor.WHITE);
			//img.scaleToFit(1000, 72);//大小  
			//img.setRotationDegrees(-30);//旋转  
			document.add(img); 
		} finally {
			// Step 5—Close the Document.
			try {
				if(null != document) document.close();
			} catch(Exception e) {
				
			}
		}
	}
	
	public static Font setFont() throws DocumentException, IOException {
		BaseFont baseFont = BaseFont.createFont("STSong-Light", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont);
		return font;
	}
	
	public static Font setChineseFont() {
		BaseFont bf = null;
		Font fontChinese = null;
		try {
			bf = BaseFont.createFont("SIMKAI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			fontChinese = new Font(bf, 12, Font.NORMAL);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fontChinese;
	}
	
	 /**
     * Creates a PDF with the words "Hello World"
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public static void createPdf(String HTML, String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        document.setPageSize(new Rectangle(1000, 800));
        
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML), Charset.forName("UTF-8"), new UnicodeFontFactory());
        // step 5
        document.close();
    }
 

	public static void main(String[] args) throws MalformedURLException, IOException, DocumentException {
		//createImagePdf("D://pic//IMG_0064.JPG", "D://pic//IMG_0064");
		try {
			createPdf("D:\\home\\RmadFile\\html\\2016\\07\\27\\word\\1468896723716\\1468896723716.html", "D:\\home\\RmadFile\\html\\2016\\07\\27\\word\\1468896723716\\1468896723716.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

	