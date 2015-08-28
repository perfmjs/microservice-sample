package com.github.dbutils.mybatis.extend;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Calendar;

public abstract class AbstractMyBatisEntity<I extends Serializable> implements IMyBatisEntity<I> {

    private static final long serialVersionUID = 4151009872540236564L;
    protected I id;
    protected Calendar createTime;
    protected BitSet bitFlag;
    protected ExtendFeaturesMap extendFeatures;

    @Override
    public I getId() {
        return this.id;
    }
    @Override
    public void setId(I id) {
        this.id = id;
    }
    public Calendar getCreateTime() {
        return createTime = createTime==null?Calendar.getInstance():createTime;
    }
    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
    @Override
    public BitSet getBitFlag() {
        return this.bitFlag = bitFlag==null?new BitSet(0):bitFlag;
    }
    @Override
    public void setBitFlag(BitSet bitFlag) {
        this.bitFlag = bitFlag;
    }
    @Override
    public ExtendFeaturesMap getExtendFeatures() {
        if (this.extendFeatures == null) {
            this.extendFeatures = new ExtendFeaturesMap();
        }
        return this.extendFeatures;
    }
    @Override
    public void setExtendFeatures(ExtendFeaturesMap<?, ?> extendFeatures) {
        this.extendFeatures = extendFeatures;
    }
}