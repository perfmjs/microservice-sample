package com.github.dbutils.mybatis.enums;

import java.util.*;


/**
 * 封装enum的操作
 * @project dal-common
 * @author tony.shen
 * @date 2012-4-26 下午1:38:09
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public class EnumWrapper<E extends Enum<E>> {

    /**EnumMap按Enum#ordinal()字典顺序存放枚举元素*/
    private EnumMap<E, Integer> enumMap;
    /**indexMap按IEnum#getIndex()大小顺序存放枚举元素*/
    private Map<Integer, E> indexMap = new TreeMap<Integer, E>();
    /**labelMap按IEnum#getLabel()存放枚举元素*/
    private Map<String, E> labelMap = new LinkedHashMap<String, E>();
    
    public EnumWrapper(Class<E> enumClass) {
        if (!IEnum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(enumClass + "必须是" + IEnum.class + "子类型");
        }
        EnumSet<E> enumSet = EnumSet.allOf(enumClass);
        this.enumMap = new EnumMap<E, Integer>(enumClass);
        Iterator<E> iterator = enumSet.iterator();
        while(iterator.hasNext()) {
            E enumKey = iterator.next();
            Integer index = ((IEnum) enumKey).getIndex();
            String label = ((IEnum) enumKey).getLabel();
            this.enumMap.put(enumKey, index);
            this.indexMap.put(index, enumKey);
            this.labelMap.put(label, enumKey);
        }
    }
   
    public static <E extends Enum<E>> EnumWrapper<E> newEnumWrapper(Class<E> enumClass) {
        return new EnumWrapper<E>(enumClass);
    }
    
    public EnumMap<E, Integer> getEnumMap() {
        return this.enumMap;
    }
    
    public Map<Integer, E> getIndexMap() {
        return this.indexMap;
    }
    
    public IEnum getEnum(int index) {
        return (IEnum) this.indexMap.get(index);
    }
    
    public Map<String, E> getLabelMap() {
        return this.labelMap;
    }
    
    public IEnum getEnum(String label) {
        return (IEnum) this.labelMap.get(label);
    }
    
    public static <E> IEnum convertEnum(E enumObj) {
        if (!IEnum.class.isAssignableFrom(enumObj.getClass())) {
            throw new IllegalArgumentException(enumObj + "必须是" + IEnum.class + "子类型");
        }
        return (IEnum) enumObj;
    }

    public static void main(String[] args) {
        EnumWrapper<CommonEnum> enumWrapper = EnumWrapper.newEnumWrapper(CommonEnum.class);
        System.out.println(enumWrapper.getIndexMap());
        System.out.println(enumWrapper.getEnumMap().get(CommonEnum.ONE) + "/" 
                + (((IEnum) enumWrapper.getEnum(3)).getLabel())
                + "/" + EnumWrapper.convertEnum(CommonEnum.ONE).getIndex()
                + "/" + enumWrapper.getLabelMap()
                + "/" + enumWrapper.getEnum("第5个"));
    }
    
}
