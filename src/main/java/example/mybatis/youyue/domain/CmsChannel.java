package example.mybatis.youyue.domain;

import com.github.dbutils.mybatis.extend.AbstractMyBatisEntity;
import com.github.dbutils.mybatis.extend.ExtendFeaturesMap;

import java.util.BitSet;
import java.util.Calendar;

public class CmsChannel extends AbstractMyBatisEntity<Integer> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
    @Override
    public void setId(Integer id) {
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
    public ExtendFeaturesMap<?, ?> getExtendFeatures() {
        return this.extendFeatures==null?new ExtendFeaturesMap():extendFeatures;
    }
    @Override
    public void setExtendFeatures(ExtendFeaturesMap<?, ?> extendFeatures) {
        this.extendFeatures = extendFeatures;
    }
}