package com.github.dbutils.mybatis.utils;

import java.util.Map;

import com.google.gson.Gson;

/**
 * ExtendFeaturesUtil
 * @project dal-common
 * @author tony.shen
 * @date 2012-4-27 上午9:20:35
 * Copyright (C) 2010-2012 www.aicai.com Inc. All rights reserved.
 */
public class ExtendFeaturesUtil {

    private static final Gson gson = new Gson();
    
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> convert(String json) {
        return gson.fromJson(json, Map.class);
    }
    
    public static String convert(Map<?,?> map) {
        return gson.toJson(map);
    }
    
    public static void main(String[] args) {
        String json = "{a:\"1\", b:\"沈\"}";
        System.out.println(convert(json));
        Map<String, String> map = convert(json);
        map.put("a", "good");
        System.out.println(convert(map));
        System.out.println(convert("{status:'OK'}"));
    }
}
