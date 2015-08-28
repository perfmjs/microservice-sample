package example.mybatis.youyue.domain;

import com.github.dbutils.mybatis.extend.AbstractMyBatisEntity;
import com.github.dbutils.mybatis.extend.ExtendFeaturesMap;

import java.util.BitSet;
import java.util.Calendar;

public class CmsComment extends AbstractMyBatisEntity<Integer> {

    private Integer cmsId;
    private String userName;
    private String content;
    private Integer likeCount;
    private Calendar createTime;
    private BitSet bitFlag;
    private ExtendFeaturesMap extendFeatures;

    public Integer getCmsId() {
        return cmsId;
    }
    public void setCmsId(Integer cmsId) {
        this.cmsId = cmsId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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