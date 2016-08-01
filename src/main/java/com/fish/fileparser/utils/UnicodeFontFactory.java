package com.fish.fileparser.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;

/**
 * 类描述:
 *       包括功能、用途、现存BUG，以及其它别人可能感兴趣的介绍。
 * @see	需要参见的其它类（可选）
 * @version 1.0 
 * @date 2016年8月1日
 * @author <a href="mailto:Ydm@nationsky.com">Ydm</a>
 * @since JDK 1.7
 */
public class UnicodeFontFactory implements FontProvider {

  @Override
  public boolean isRegistered(String paramString) {
    return false;


  }

  @Override
  public Font getFont(String paramString1, String paramString2, boolean paramBoolean,
      float paramFloat, int paramInt, BaseColor paramBaseColor) {
    return ItextUtils.setChineseFont();
  }

}

