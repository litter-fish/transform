package com.fish.fileparser.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;

public class UnicodeFontFactory implements FontProvider {
	
	@Override
	public boolean isRegistered(String paramString) {
		return false;
		
			
	}

	@Override
	public Font getFont(String paramString1, String paramString2, boolean paramBoolean, float paramFloat, int paramInt,
			BaseColor paramBaseColor) {
		return ItextUtils.setChineseFont();
	}

}

	