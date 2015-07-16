package com.github.dbutils.mybatis.enums;

/**
 * CommonEnum
 * @project dal-common
 * @author tony.shen
 * @date 2012-4-25 下午5:10:28
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public enum CommonEnum implements IEnum {

    ONE(1, "第1个"),
    TWO(2,"第2个"),
    THREE(3, "第3个"),
    FIVE(55, "第5个"),
    FOUR(44, "第4个");
    
    private int index;
    private String label;
    private CommonEnum(int index, String label) {
        this.index = index;
        this.label = label;
    }
    @Override
    public int getIndex() {
        return this.index;
    }
    @Override
    public String getLabel() {
        return this.label;
    }
    
    public static void main(String[] args) {
        System.out.println(THREE.getLabel());
    }
    
}
