package com.github.dbutils.mybatis.enums;

/**
 * 封装enum的标志性属性操作：index和label
 * @author tony.shen
 * <pre>
 * e.g.
 * 　public enum CommonEnum implements IEnum {
        ONE(1, "第1个"),
        .........
        //以下代码段可直接拷贝至子枚举类,应记得将CommonEnum构造函数名改成子枚举类名
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
        .....
    }
 * </pre>
 * @project dal-common
 * @date 2012-4-26 下午1:27:44
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public interface IEnum {

    /**
     * 索引，必须唯一,不可为空
     * @return
     * @create_time 2012-5-27 上午9:46:15
     */
    public int getIndex();
    /**
     * 标签(可以和索引一一对应)，必须唯一,不可为空
     * @return
     * @create_time 2012-5-27 上午9:46:43
     */
    public String getLabel();
    
}
