package com.fish.fileparser.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

public class ColorCo {
  private static Map<Short, String> cMap = new HashMap<Short, String>();
  private static Map<String, Integer> hssfMap = new HashMap<String, Integer>();


  public static Map<Short, String> getcMap() {
    return cMap;
  }

  public static Map<String, Integer> getHssfMap() {
    return hssfMap;
  }

  static {
    IndexedColors[] indexedColors = IndexedColors.values();
    for (IndexedColors indexedColors2 : indexedColors) {
      Short index = indexedColors2.index;
      String name = indexedColors2.name();
      cMap.put(index, name);
    }

    Map<Integer, HSSFColor> map = HSSFColor.getIndexHash();
    Set<Entry<Integer, HSSFColor>> set = map.entrySet();
    for (Entry<Integer, HSSFColor> entry : set) {
      Integer key = entry.getKey();
      HSSFColor value = entry.getValue();
      String name = value.toString().split("\\$")[1].split("@")[0];

      hssfMap.put(name, key);

    }
  }
}

