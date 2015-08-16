package com.github.dbutils.mybatis.extend;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Calendar;

/**
 * 
 * @project dal-common
 * @author tony.shen
 * @date 2012-5-27 下午10:53:49
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public interface IMyBatisEntity<I extends Serializable> extends Serializable {
    
    /**主键ID*/
    public I getId();
    public void setId(I id);
    /**创建时间*/
    public Calendar getCreateTime();
    public void setCreateTime(Calendar createTime);
    /**扩展位字段*/
    public BitSet getBitFlag();
    public void setBitFlag(BitSet bitFlag);
    /**扩展特性字段*/
    public ExtendFeaturesMap<?, ?> getExtendFeatures();
    public void setExtendFeatures(ExtendFeaturesMap<?, ?> extendFeatures);
}
