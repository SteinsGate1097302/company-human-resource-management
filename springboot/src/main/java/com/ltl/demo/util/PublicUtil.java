package com.ltl.demo.util;

import java.util.HashMap;
import java.util.Map;

public class PublicUtil {
    //传入字符串如 "<=5"，分解成 "<=" 和 5
    public static Map<String,Object> splitSymbolAndNum(String str){
        String symbol = "";
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                symbol += str.charAt(i);
            } else {
                num = Integer.parseInt(str.substring(i));
                break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("symbol",symbol);
        map.put("num",num);
        return map;
    }


    /**
     * 判断数字是否在字符串的范围中
     * @param numRule
     * @param num
     * @return
     */
    public static boolean deliveryNumValidated(String numRule, double num) {
        //截取出数量规则的 符号和数字
        String symbolOfRuler = (String) splitSymbolAndNum(numRule).get("symbol");
        int ruleNum = (int) splitSymbolAndNum(numRule).get("num");
        String symbolOfNum = "";

        //获得参数数字和规则数字的比较符号
        if (num < ruleNum) {
            symbolOfNum = "<";
        } else if (num > ruleNum) {
            symbolOfNum = ">";
        } else {
            symbolOfNum = "=";
        }

        //判断符号是否相同，即可知道参数数字和规则是否匹配
        if (symbolOfRuler.contains(symbolOfNum)) {
            return true;
        } else {
            return false;
        }

    }
}
