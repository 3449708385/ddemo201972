package com.mgp.ddemo.user.bean;

import java.io.Serializable;

public class LcnTest implements Serializable {
    private Long id;

    private String accressId;

    private String lcnName;

    private static final long serialVersionUID = 1L;

    public LcnTest(Long id, String accressId, String lcnName) {
        this.id = id;
        this.accressId = accressId;
        this.lcnName = lcnName;
    }

    public LcnTest() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccressId() {
        return accressId;
    }

    public void setAccressId(String accressId) {
        this.accressId = accressId == null ? null : accressId.trim();
    }

    public String getLcnName() {
        return lcnName;
    }

    public void setLcnName(String lcnName) {
        this.lcnName = lcnName == null ? null : lcnName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accressId=").append(accressId);
        sb.append(", lcnName=").append(lcnName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}