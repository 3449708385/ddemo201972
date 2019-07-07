package com.mgp.ddemo.user.service.impl;

import com.mgp.ddemo.user.bean.Jiepai;
import com.mgp.ddemo.user.dao.Impl.JiepaiDaoImpl;
import com.mgp.ddemo.user.service.JiepaiService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;

@Service("jiepaiService")
public class JiepaiServiceImpl implements JiepaiService {

    private static final Logger logger = LoggerFactory.getLogger(JiepaiServiceImpl.class);

    @Autowired(required = false)@Qualifier("jiepaiDao")
    private JiepaiDaoImpl jiepaiDao;

    @Override
    public String saveObj(Jiepai jp) {
        jiepaiDao.save(jp);
        return "sucess";
    }

    @Override
    public List<Jiepai> findAll() {
        return null;
    }

    @Override
    public Jiepai getJiepaiById(ObjectId id) {
        return jiepaiDao.queryById(id);
    }

    @Override
    public List<Jiepai> getJiepaiByTitle(String name) {
        Jiepai jp = new Jiepai();
        jp.setTitle(name);
        return jiepaiDao.queryList(jp);
    }

    @Override
    public String updateJiepai(Jiepai jp) {
       // Query query = new Query(Criteria.where("_id").is(jp.getId()));
       // Update update = new Update().set("title", jp.getTitle()).set("updateTime", new Date());
        //updateFirst 更新查询返回结果集的第一条
       // mongoTemplate.updateFirst(query, update, Jiepai.class);
        //updateMulti 更新查询返回结果集的全部
//        mongoTemplate.updateMulti(query,update,Jiepai.class);
        //upsert 更新对象不存在则去添加
//        mongoTemplate.upsert(query,update,Jiepai.class);
        Jiepai jpquery = new Jiepai();
        jpquery.setId(jp.getId());
        jiepaiDao.updateFirst(jpquery,jp);
        return "success";
    }

    @Override
    public String deleteJiepai(Jiepai jp) {
        jiepaiDao.delete(jp);
        return "success";
    }

    @Override
    public String deleteJiepaiById(ObjectId id) {
        //findOne
        Jiepai jp = getJiepaiById(id);
        //delete
        deleteJiepai(jp);
        return "success";
    }
}
