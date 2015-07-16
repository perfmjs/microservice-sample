package com.github.dbutils.mybatis.extend;

import java.io.Serializable;
import java.util.BitSet;

public abstract class AbstractMyBatisEntity<I extends Serializable> implements IMyBatisEntity<I> {

    private static final long serialVersionUID = 4151009872540236564L;
    protected I id;
    protected BitSet bitFlag;
    protected ExtendFeaturesMap<?, ?> extendFeatures;
    
    @Override
    public I getId() {
        return this.id;
    }

    @Override
    public void setId(I id) {
        this.id = id;
    }

    @Override
    public BitSet getBitFlag() {
        return this.bitFlag;
    }

    @Override
    public void setBitFlag(BitSet bitFlag) {
        this.bitFlag = bitFlag;
    }

    @Override
    public ExtendFeaturesMap<?, ?> getExtendFeatures() {
        return this.extendFeatures;
    }

    @Override
    public void setExtendFeatures(ExtendFeaturesMap<?, ?> extendFeatures) {
        this.extendFeatures = extendFeatures;
    }
    
}