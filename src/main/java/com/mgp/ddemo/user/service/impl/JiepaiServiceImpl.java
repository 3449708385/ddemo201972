package com.mgp.ddemo.user.service.impl;

import com.mgp.ddemo.commons.util.ConstUtil;
import com.mgp.ddemo.commons.util.DateUtil;
import com.mgp.ddemo.user.bean.Jiepai;
import com.mgp.ddemo.user.dao.Impl.JiepaiDaoImpl;
import com.mgp.ddemo.user.service.JiepaiService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service("jiepaiService")
public class JiepaiServiceImpl implements JiepaiService {

    private static final Logger logger = LoggerFactory.getLogger(JiepaiServiceImpl.class);

    @Autowired(required = false)@Qualifier("jiepaiDao")
    private JiepaiDaoImpl jiepaiDao;

    @Autowired
    private MongoTemplate mongoTemplate;

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

    //mongo测试实现方法
    @Override
    public void mongoOperate() {

        Query query = new Query();

        query.addCriteria(where("createTime").lte(DateUtil.dateToISODate(new Date())));

        query.with(Sort.by(
                Sort.Order.asc("createTime"),
                Sort.Order.desc("_id")
        ));

        query.skip(0).limit(10);

        //指定包含或不包含的字段
        //query.fields().include().include().include().exclude();

        List<Jiepai>  jiepaiList= this.mongoTemplate.find(query, Jiepai.class);

        //操作单条数据，有则更新或插入
        this.mongoTemplate.save(jiepaiList.get(0), ConstUtil.JIEPAI_TEST);

        for(Jiepai jiepai :jiepaiList){

            jiepai.setId(null);

        }

        jiepaiList.addAll(jiepaiList);
        //不能操作多条：org.bson.Document cannot be cast to java.util.Collection
        //this.mongoTemplate.save(jiepaiList);

        //存在就更新set,或没有则插入，看Update对象是addToSet
        //upsert

        //查询数据
        //查询单个数据和多个数据，模糊查询
        //查询单个数据
        // public SysUser findMongo() {
        // Pattern pattern = Pattern.compile("^.*8$",Pattern.CASE_INSENSITIVE);
        // Query query = new Query(Criteria.where("phone").regex(pattern));
        // SysUser findOne = mongoTemplate.findOne(query,SysUser.class,"userList");
        // return findOne;     }

        //    //查询多个数据
        // public List<SysUser> findListMongo() {
        // 模糊查询以 ^开始 以$结束 .*相当于Mysql中的%
        // Pattern pattern = Pattern.compile("^1.*$",Pattern.CASE_INSENSITIVE);
        // Query query = new Query(Criteria.where("phone").regex(pattern));
        // Query query = new Query(Criteria.where("status").is("1"));
        // query.with(new Sort(Direction.DESC,"phone"));
        // 按手机号码倒序
        // List<SysUser> findList = mongoTemplate.find(query,SysUser.class,"userList");
        // return findList;     }

        //多个条件查询(格式2)
        // public List<SysUser> findList2(){
        // Criteria criteria = new Criteria();
        // criteria.and("name").is("秦岚");
        // criteria.and("phone").is("12222222222");
        // Query query = new Query(criteria);
        // List<SysUser> findList = mongoTemplate.find(query, SysUser.class,"userList");
        // return findList; }

        //一个模糊关键字匹配多个字段
        // public List<SysUser> findList3(){
        // Pattern pattern = Pattern.compile("^.*222$",Pattern.CASE_INSENSITIVE);
        // Criteria  criteria = new Criteria();         //phone以222结尾的 或者 name以222结尾的
        // criteria.orOperator(Criteria.where("phone").regex(pattern), Criteria.where("name").regex(pattern));
        // 同时满足 phone以222结尾的，和name以222结尾的
        // criteria.andOperator(Criteria.where("phone").regex(pattern),  Criteria.where("name").regex(pattern));
        // Query query = new Query(criteria);
        // List<SysUser> find = mongoTemplate.find(query, SysUser.class,"userList");
        // return find;     }


        //更新
        // public int update() {
        // Query query = new Query();
        // query.addCriteria(Criteria.where("_id").is(1));
        // _id区分引号 "1"和1
        // Update update = Update.update("name", "zzzzz");
        // WriteResult upsert = mongoTemplate.updateMulti(query, update, "userList");
        // 查询到的全部更新
        // WriteResult upsert = mongoTemplate.updateFirst(query, update, "userList");
        // 查询更新第一条
        // WriteResult upsert = mongoTemplate.upsert(query, update, "userList");
        // 有则更新，没有则新增
        // return upsert.getN();返回执行的条数     }


        //添加内嵌文档数据（有则直接加入，没有则进行新增）
        // public int update1() {
        // Query query = Query.query(Criteria.where("_id").is("11"));
        // SysUser user = new SysUser(1,"1","lisi","19999998745");
        // Update update = new Update();
        // update.addToSet("users", user);
        // WriteResult upsert = mongoTemplate.upsert(query, update, "userList");
        // return upsert.getN();     }

        //修改内嵌文档中数据
        // public int update2() {
        // 查询_id为11并且其中userList文档的_id为1的
        // Query query = Query.query(Criteria.where("_id").is("11").and("users._id").is(1));
        // Update update = Update.update("users.$.name", "zhangsan");
        // WriteResult upsert = mongoTemplate.upsert(query, update, "userList");
        // return upsert.getN(); }


        //删除内嵌文档中数据
        // public int delete() {
        // 查询_id为11并且其中userList文档的_id为1的
        // Query query = Query.query(Criteria.where("_id").is("11").and("users._id").is(1));
        // Update update = new Update();
        // update.unset("users.$");
        // WriteResult upsert = mongoTemplate.updateFirst(query, update, "userList");
        // return upsert.getN(); }


        //nin 用于集合， not用于任何地方

        // mod
        // 取模(求余)运算 ,即：key对应的值%value==remainder（求余是否等于remainder）;
        // {key:15} {key2:15}
        // query.addCriteria(Criteria.where("key").mod(10,3));
        // key条件不成立；key2条件成立

        // all  与in的区别：key键对应的集合包含col(all是包含关系，in是被包含关系)
        // query.addCriteria(Criteria.where("key").all("n1","n2"));//此时key对应的值应该是集合
        // query.addCriteria(Criteria.where("nameList").all((Object[]) names)));

        //inc 自加减 1 -1

         //insertAll（批量） 与 insert（单个或批量）
       // this.mongoTemplate.insertAll(jiepaiList);
        this.mongoTemplate.insert(jiepaiList, Jiepai.class);

        //Update  push,操作数组，存在更新，不存在插入，跟 addToSet 有些不同

        //Update  pushAll,(批量)操作数组，存在更新，不存在插入，跟 addToSet 有些不同

        //Update addToSet 操作字段，存在更新，不存在插入

        //Update pop 删除数组 元素 1代表最后一个，-1代表第一个

        //Update pull 删除数组 元素，确定值

        //Update pullAll （批量）删除数组 元素，确定值列表

        //findAndModify  会返回修改后的数据

        //具体看这个： https://www.cnblogs.com/qingming/p/6419474.html


        //find:查询满足条件的数据  findOne：查询一条数据  findAll：查询所有数据


        //聚合查询：Aggregation聚合查询
        //https://www.jianshu.com/p/78b96ca40927
        //https://www.cnblogs.com/qingming/p/6419474.html


        //这里的BulkMode.UNORDERED：有错不停更新对的，ORDERED：有错报错，collectionName是mongo的集合名
        BulkOperations ops = this.mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Jiepai.class);

        for (Jiepai jiepai : jiepaiList) {

            Update update = new Update();
            update.set("title", "街拍美图");
            ops.updateOne(query(where("_id").is(jiepai.getId())), update);

            //我用的insert方法
            //注意此处的obj必须是一个DBObject，可以是json对象也可以的bson对象，entity没有试过
            //ops.insert(Object obj);
        }
        //循环插完以后批量执行提交一下ok！
        ops.execute();

    }
}
