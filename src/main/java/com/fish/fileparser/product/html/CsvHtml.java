package com.fish.fileparser.product.html;

import java.io.File;

import org.apache.tika.Tika;

import com.fish.fileparser.utils.FileHelper;

public class CsvHtml implements AbstractHtml {

  @Override
  public void createHtml(String inputFile, String outputFile) throws Exception {
    String content = readFile(inputFile);
    String[] table = content.replaceAll("\n", "").split("\r");
    StringBuffer html = new StringBuffer();
    String[] split1 = inputFile.split("/");
    String[] split2 = split1[split1.length - 1].split("\\\\");
    html.append("<!DOCTYPE html><html><head>").append("<title>")
        .append(split2[split2.length - 1].substring(0, split2[split2.length - 1].length() - 4))
        .append("</title>")
        .append(
            "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\">")
        .append("<style type=\"text/css\">")
        .append("table.gridtable {font-family: verdana,arial,sans-serif;")
        .append(
            "font-size:11px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;}")
        .append(
            "table.gridtable th {border-width: 1px;	padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;}")
        .append(
            "table.gridtable td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}")
        .append("</style>").append(
            "</head><body><div style=\"width:595.0pt;margin-bottom:72.0pt;margin-top:72.0pt;margin-left:90.0pt;margin-right:90.0pt;\"><table class=\"gridtable\">");
    StringBuffer headHtml = new StringBuffer();
    headHtml.append("<tr>");

    String[] headTable = table[0].split(",");
    for (String value : headTable) {
      headHtml.append("<th>").append(value).append("</th>");
    }
    headHtml.append("</tr>");
    StringBuffer bodyHtml = new StringBuffer();
    for (int offset = 1; offset < table.length; offset++) {
      String[] bodyTable = table[offset].split(",");
      if (0 == bodyTable.length) {
        continue;
      }
      StringBuffer buffer = new StringBuffer();
      buffer.append("<tr>");
      for (String value : bodyTable) {
        buffer.append("<td>").append(value).append("</td>");
      }
      if (headTable.length - bodyTable.length > 0) {
        for (int diff = headTable.length - bodyTable.length; diff > 0; diff--) {
          buffer.append("<td>").append("").append("</td>");
        }
      }
      buffer.append("</tr>");
      bodyHtml.append(buffer);
    }
    html.append(headHtml).append(bodyHtml).append("</table></div></body></html>");

    FileHelper.writeFile(html.toString(), outputFile + ".html");
    FileHelper.parse(outputFile + ".html");
  }


  private String readFile(String fileName) throws Exception {
    Tika tika = new Tika();
    return tika.parseToString(new File(fileName));
  }
}

