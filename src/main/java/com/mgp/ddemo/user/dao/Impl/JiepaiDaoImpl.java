package com.mgp.ddemo.user.dao.Impl;

import com.mgp.ddemo.user.bean.Jiepai;
import com.mgp.ddemo.user.dao.MongoDbBaseDao;
import org.springframework.stereotype.Repository;

@Repository("jiepaiDao")
public class JiepaiDaoImpl extends MongoDbBaseDao<Jiepai> {

    /**
     * 反射获取泛型类型
     *
     * @return
     */
    @Override
    protected Class<Jiepai> getEntityClass() {
        return Jiepai.class;
    }
}
