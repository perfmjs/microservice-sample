package example.mybatis.youyue.domain;

import com.github.dbutils.mybatis.extend.AbstractMyBatisEntity;
import com.github.dbutils.mybatis.extend.ExtendFeaturesMap;

import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Cms extends AbstractMyBatisEntity<Integer> {

    private String channelName;
    private String title;
    private String img1;
    private String img2;
    private String img3;
    private String label;
    private String source;
    private String sourceUrl;
    private String content;
    private Integer likeCount;
    private Integer unlikeCount;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
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

    public Integer getUnlikeCount() {
        return unlikeCount;
    }

    public void setUnlikeCount(Integer unlikeCount) {
        this.unlikeCount = unlikeCount;
    }

    public boolean getHasBigImg() {
        return this.getExtendFeatures().get("hasBigImg")==null?false:(Boolean)this.getExtendFeatures().get("hasBigImg");
    }

    public void setHasBigImg(boolean hasBigImg) {
        this.getExtendFeatures().put("hasBigImg", hasBigImg);
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
    public void setExtendFeatures(ExtendFeaturesMap extendFeatures) {
        this.extendFeatures = extendFeatures;
    }
}