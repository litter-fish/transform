package com.fish.factory.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.ws.rs.core.NewCookie;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * ������:
 *       �ļ������࣬�����ж��ļ��汾����д�ļ��Ȳ���
 *       
 * @see	��Ҫ�μ�������ࣨ��ѡ��
 * @version 1.0 
 * @date 2016��5��16��
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class FileHelper {
	
	/**
	 * �ж�excel�ļ��Ƿ�Ϊ2003�汾
	 * @param file
	 * @return
	 */
	public static boolean isExcel2003(File file) {
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(file);
			wb = WorkbookFactory.create(is);
			if (wb instanceof XSSFWorkbook) {
				return false;
			} else if (wb instanceof HSSFWorkbook) {
				return true;
			}
		} catch (Exception e) {
	      return false;
	    } finally {
	    	try {
	    		if(null != is) is.close();
	    		if(null != wb) wb.close();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		}
	    return true;
	  }

	/**
	 * �ж�word�ļ��Ƿ�Ϊ2003�汾
	 * @param file
	 * @return
	 */
	public static boolean isWord2003(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			new HWPFDocument(is);
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean isWord2007(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			new XWPFDocument(is).close();
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * �ж�ppt�ļ��Ƿ�Ϊ2003�汾
	 * @param file
	 * @return
	 */
	public static boolean isPPT2003(File file) {
		InputStream is = null;
		HSLFSlideShow ppt = null;
		try {
			is = new FileInputStream(file);
			ppt = new HSLFSlideShow(is);
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(null != is) {
					is.close();
				}
				if(null != ppt) {
					ppt.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * ��ݴ�����ļ�·����ȡ�ļ����ݣ�
	 * @param file
	 * @return
	 */
	public static StringBuffer readFile(String path) {
		StringBuffer buffer = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		try {
			File file = new File(path);
			if(file.exists()) {
				is = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(is));
				String content = br.readLine();
				while(null != content) {
					buffer.append(content);
					content = br.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != is) {
					is.close();
				}
				if(null != br) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return buffer;
	}
	
	/**
	 * ��ݴ�����ļ�·����ȡ�ļ����ݣ�
	 * @param file
	 * @return
	 */
	public static StringBuffer readFile(String path, String split) {
		StringBuffer buffer = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		try {
			File file = new File(path);
			if(file.exists()) {
				is = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(is));
				String content = br.readLine();
				while(null != content) {
					buffer.append(content).append(split);
					content = br.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != is) {
					is.close();
				}
				if(null != br) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return buffer;
	}
	
	/**
	 * ��������ļ�����content��д�뵽path·���ļ���
	 * @param content
	 * @param path
	 */
	public static void writeFile(String content, String path) {
		OutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException ie) {
			}
		}
	}
	
	/**
	 * ��pdf/ppt�ȸ�ʽ�ļ�ת�ɵ�ͼƬ�����html�ļ�
	 * @param size
	 * @param path
	 * @param fileName
	 */
	public static void writeHtmlFile(int size, String path, String fileName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<!DOCTYPE html><html><head>");
		buffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
		buffer.append("<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no,minimal-ui\"/>");
		buffer.append("<meta name=\"format-detection\" content=\"telephone=no\"/>");
		buffer.append("<meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\"/>");
		buffer.append("<title>touch</title>");
		buffer.append("<meta name=\"keywords\" content=\"\"/>");
		buffer.append("<meta name=\"description\" content=\"\"/>");
		buffer.append("<style type=\"text/css\">body{width:100%;height:auto;position:relative;}img{max-width:100%;height:auto;margin:0 auto;}</style>");
		
		buffer.append("</head>");
		buffer.append("<body>");
		
		for(int offset = 0; offset < size; offset++) {
			buffer.append("<img src=\"" + fileName + "/" + (offset + 1) + ".png\" />");
			buffer.append("<br />");
		}
		buffer.append("</body></html>");
		//System.out.println(buffer.toString());
		writeFile(buffer.toString(), path + ".html");
	}
	
	public static void write2Html(StringBuffer content, String path) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><head><meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\"></head><body><div align=\"left\">");
		buffer.append("<p>" + content + "</p>");
		buffer.append("</div></body></html>");
		//System.out.println(buffer.toString());
		writeFile(buffer.toString(), path + ".html");
	}
	
	/**
	 * ����Ŀ¼
	 * @param filePath
	 * @param fileType
	 */
	public static void mkdirFiles(String filePath, String fileType) {
		File file = new File(filePath + "/" + fileType);
		if(!file.exists()) {
			file.mkdirs();
		} else {
			
		}
	}
	
	/**
	 * 删除文件空行
	 * @param content
	 * @param outPutFile
	 * @throws IOException
	 */
	public static void rmrBlankLines(String inputFile, String outPutFile) throws IOException {
		File htmFile = new File(inputFile);
		// 以GB2312读取文件
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(htmFile));
			bw = new BufferedWriter(new FileWriter(new File(outPutFile)));
			String result = null;
			while(null != (result = br.readLine()) ) {
				if(!"".equals(result.trim()))
					bw.write(result + "\r\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != br)
					br.close();
				if(null != bw)
					bw.close();
			} catch (Exception e) {
				
			}
		}
		
		
	}
	
	/**
	 * @param htmFilePath
	 * @throws IOException
	 */
	public static void parseH2(String htmFilePath) throws IOException{
		File htmFile = new File(htmFilePath);
		 Document doc = Jsoup.parse(htmFile, "UTF-8");
		 doc.getElementsByAttribute("h2");
         Elements content = doc.getElementsByTag("h2");
         for (Element meta : content) {
             meta.attr("style", "text-align:center");
         }
         FileUtils.writeStringToFile(htmFile, doc.html(),"UTF-8");
	}
	
	/**
	 * @param htmFilePath
	 * @throws IOException
	 */
	public static void parseCharset(String htmFilePath) throws IOException{
		File htmFile = new File(htmFilePath);
		//以GB2312读取文件
		 Document doc = Jsoup.parse(htmFile, "utf-8");
		 //获取html节点
         Elements content = doc.getElementsByAttributeValueStarting("content", "text/html;");
         for (Element meta : content) {
        	 //获取content节点，修改charset属性
             meta.attr("content", "text/html; charset=utf-8");
             break;
         }
         //转换成utf-8编码的文件写入
         System.out.println(doc.html());
         FileUtils.writeStringToFile(htmFile, doc.html(),"utf-8");
	}
	
	/**
	 * @param htmFilePath
	 * @throws IOException
	 */
	public static void parse(String htmFilePath) throws IOException{
		File htmFile = new File(htmFilePath);
		//以GB2312读取文件
		 Document doc = Jsoup.parse(htmFile, "utf-8");
		 String xmlns = doc.getElementsByTag("html").attr("xmlns");
		 if(null == xmlns || "".equals(xmlns)) {
			 return;
		 }
		 doc.getElementsByTag("html").removeAttr("xmlns");
		 Element head = doc.head();
		 /*Elements headChildren = head.children();
		 for(Element children : headChildren) {
			 Elements metas = children.getElementsByTag("meta");
			 for(Element meta : metas) {
				 meta.remove();
			 }
		 }*/
		 head.appendElement("meta").attr("name", "viewport").attr("content", "width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no");
		
		 //获取html节点
		 Element element = doc.body();
         Elements content = head.getElementsByAttributeValueStarting("name", "meta:page-count");
         for (Element meta : content) {
        	 
        	 String value = meta.attr("content");
        	 try {
        		 Integer count = Integer.valueOf(value);
        		 Elements ps = element.getElementsByTag("p");
        		 Iterator<Element> iterator = ps.iterator();
        		 while (iterator.hasNext()) {
        			 Element p = iterator.next();
        			 String text = p.text();
             		if(text.equals("- " + count +" -")) {
             			for(int offset = count; offset > 0; offset--) {
             				p.remove();
             				p = iterator.next();
             				text = p.text();
             			}
             		}
             		if(text.equals("")) {
            			p.remove();
            			p = iterator.next();
            		}
             		p.attr("align", "center");
             		p.attr("style", "font-size:1.5rem;");
             		break;
				}
        	 } catch(Exception e) {
        		 
        	 }
        	 //获取content节点，修改charset属性
        	 //meta.attr("content", "text/html; charset=utf-8");
             break;
         }
         //转换成utf-8编码的文件写入
         FileUtils.writeStringToFile(htmFile, "<!DOCTYPE html>" + doc.html(),"utf-8");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		try {
			//parse("D:/home/RmadFile/html/2016/07/19/word/2/2.html");
			rmrBlankLines("D:\\home\\RmadFile\\docx.txt", "D:\\home\\RmadFile\\docx2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

	