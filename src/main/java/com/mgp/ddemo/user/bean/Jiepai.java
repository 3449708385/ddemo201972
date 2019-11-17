package com.mgp.ddemo.user.bean;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document("jiepai")
public class Jiepai implements Serializable {//对应数据库collection名字
    @Id
    private ObjectId id;

    @Field("title")
    private String title;

    @Field("iconUrl")
    private String iconUrl;

    @Field("createTime")
    private Date createTime;

    @Field("updateTime")
    private Date updateTime;

    @Field("iconList")
    private List<String> iconList;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getIconList() {
        return iconList;
    }

    public void setIconList(List<String> iconList) {
        this.iconList = iconList;
    }

    @Override
    public String toString() {
        return "Jiepai{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", iconList=" + iconList +
                '}';
    }
}
