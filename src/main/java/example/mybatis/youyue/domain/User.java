package example.mybatis.youyue.domain;

import com.github.dbutils.mybatis.extend.AbstractMyBatisEntity;
import com.github.dbutils.mybatis.extend.ExtendFeaturesMap;

import java.util.BitSet;
import java.util.Calendar;

public class User extends AbstractMyBatisEntity<Integer> {

    private String name;
    private String passwd;
    private String mobile;
    private String email;
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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