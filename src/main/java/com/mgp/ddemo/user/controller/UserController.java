package com.mgp.ddemo.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgp.ddemo.commons.exceptionhandler.GlobalException;
import com.mgp.ddemo.commons.interceptor.NeedLogin;
import com.mgp.ddemo.commons.rabbit.RabbitSender;
import com.mgp.ddemo.commons.rabbit.TranRabbitSender;
import com.mgp.ddemo.commons.redis.RedisUtil;
import com.mgp.ddemo.commons.threadbind.ThreadLocalUtil;
import com.mgp.ddemo.commons.threadbind.ThreadUserInfo;
import com.mgp.ddemo.commons.token.JwtUtils;
import com.mgp.ddemo.commons.util.MyPageHelper;
import com.mgp.ddemo.user.bean.Jiepai;
import com.mgp.ddemo.user.bean.User;
import com.mgp.ddemo.user.service.JiepaiService;
import com.mgp.ddemo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired(required = false)@Qualifier("userService")
    private UserService userService;

    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;

    @Autowired(required = false)@Qualifier("redisUtil")
    private RedisUtil redisUtil;

    @Autowired(required = false)@Qualifier("jiepaiService")
    private JiepaiService jiepaiService;

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private TranRabbitSender tranRabbitSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/getUser")
    public Map<String, Object> getUser(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList = userService.queryUserList();
        map.put("abc",userList);
        return map;
    }

    @RequestMapping("/getExceptionInfo")
    public Map<String, Object> getExceptionInfo(){
        Map<String, Object> map = new HashMap<String, Object>();
        throw new GlobalException(505,"my exception");
    }

    @GetMapping("/getAllUser")
    public Map<String, Object> getAllUser(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,1);
        //分页插件total只会以第一个查询为主，后续查询会导致无法获取总数，需要用PageInfo承接
        List<User> list = userService.queryUserList();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @GetMapping("/getAllUser2")
    public Map<String, Object> getAllUser2(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        Map<String, Object> map = new HashMap<String, Object>();
        //分页插件total只会以第一个查询为主，后续查询会导致无法获取总数，需要用PageInfo承接
        List<User> list = userService.queryUserList();
        MyPageHelper<User> myPageHelper = new MyPageHelper<>(list, pageNum, 1);
        map.put("pageInfo",myPageHelper);
        return map;
    }

    @NeedLogin
    @RequestMapping(value="/getUser/{username}", produces="application/json")
    public Map<String, Object> getUserByName(@PathVariable("username") String username){
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList = userService.queryUserListByUserName(username);
        map.put("abc",userList);
        return map;
    }

    @NeedLogin
    @RequestMapping("/getData/{rkey}")
    public Map<String, Object> getData(@PathVariable("rkey") String rkey){
        Map<String, Object> map = new HashMap<String, Object>();
       // stringRedisTemplate.opsForValue().getAndSet(rkey,"222");
      ///  System.out.println(stringRedisTemplate.opsForValue().getAndSet(rkey,"333"));
       // map.put("abc",stringRedisTemplate.opsForValue().getAndSet(rkey,"333").toString().trim());
        redisUtil.set("1","11",10);
        System.out.println("key");
        //redisUtil.expire("abc1234",10);
        return map;
    }

    @NeedLogin
    @RequestMapping("/getMongoData/{rkey}")
    public Map<String, Object> getMongoData(@PathVariable("rkey") String rkey){
        Map<String, Object> map = new HashMap<String, Object>();
        //List<Jiepai> jiepaiList = jiepaiService.findAll();
        //List<Jiepai> jiepaiList = jiepaiService.getJiepaiByTitle("街拍：地铁站街拍气质美女");
        Jiepai jp = new Jiepai();
        jp.setCreateTime(new Date());
        jp.setUpdateTime(new Date());
        jp.setIconUrl("test");
        jp.setTitle("街拍：地铁站街拍气质美女");
        //jiepaiService.saveObj(jp);
        List<Jiepai> jpList = jiepaiService.getJiepaiByTitle("街拍：地铁站街拍气质美女");
        System.out.println("tt");
        map.put("data",jpList);
        return map;
    }

   // @NeedLogin
    @RequestMapping("/sendRibbitMQ")
    public Map<String, Object> sendRibbitMQ(){
        Map<String, Object> map = new HashMap<String, Object>();
        //mqProducer.send();
        //rabbitSender.send03();
        //rabbitSender.send01();
         // rabbitSender.send03();
         // rabbitSender.send2();
        //new 对象，对象里面包含的对象不能用spring注入，不然null
        //new TranRabbitSender().sendIngateQueue("test_pay");
        tranRabbitSender.sendIngateQueue("test_pay");
        return map;
    }

    @GetMapping("/setUserInfo")
    public Map<String, Object> setUserInfo(){
        Map<String, Object> map = new HashMap<String, Object>();
        ThreadUserInfo userInfo = new ThreadUserInfo();
        userInfo.setUserId(2L);
        userInfo.setToken("token");
        String token = JwtUtils.encode(userInfo, 5L * 3600L * 1000L);
        redisUtil.set(token,userInfo);
        map.put("token",token);
        return map;
    }

    @NeedLogin
    @GetMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(){
        Map<String, Object> map = new HashMap<String, Object>();
        ThreadUserInfo userInfo = ThreadLocalUtil.getThreadLocalUserInfo();
        map.put("token", userInfo);
        return map;
    }

    @GetMapping("/mongo/operate")
    public void mongoOperate(){

       this.jiepaiService.mongoOperate();

    }


    @GetMapping("/redisOperate")
    public void redisOperate() {

        //hash相当于java的map，也可以直接插入map
        /*this.redisTemplate.opsForHash().put("hashkeyTest", "key1", "val11");
        this.redisTemplate.opsForHash().put("hashkeyTest", "key2", "val21");*/

        //hash 模糊匹配key值
        //scan
        //获取所以匹配条件的Hash键中key的值。我查过一些资料，大部分写的是无法模糊匹配，
        //我自己尝试了一下，其实是可以的。如下，使用scan模糊匹配hash键的key中，带SCAN的key。


        //直接chu存入map
       /* Map<String, String> map = new HashMap<String, String>();
        map.put("key3","val3");
        map.put("key4","val4");
        this.redisTemplate.opsForHash().putAll("hashkeyTest", map);*/

        //得到单个map的值
        //Object obj = this.redisUtil.hget("hashkeyTest", "key1");

        //得到所有map的值
        //System.out.println(this.redisUtil.hmget("hashkeyTest"));

        //批量或单个删除map key
        //this.redisUtil.hdel("hashkeyTest", "key1", "key2");
        //this.redisUtil.hdel("hashkeyTest", "key1");

        //存在自加，不存在默认为加上要自加的值：11
        //this.redisUtil.hincr("hashkeyTest", "key7", 11);

        //存在自加，不存在默认为要减去的值：11
        // this.redisUtil.hdecr("hashkeyTest", "key7", 11);

        //set

        //list
        //当key存在更新，不存在无处理
        //leftPushIfPresent

        //list才有，用于截取list数组 void
        //this.redisTemplate.opsForList().trim();

        //移除最左边的一个元素
        //this.redisTemplate.opsForList().leftPop();

        //可以用range取所有元素，不可以用这个取所有元素
       /* this.redisTemplate.opsForList().rightPush("mgp",1);
        this.redisTemplate.opsForList().rightPush("mgp",2);
        this.redisTemplate.opsForList().rightPush("mgp",3);

        List<Object> list = this.redisTemplate.opsForList().range("mgp",0, -1);

        System.out.println(this.redisTemplate.opsForList().getOperations().boundListOps("mgp"));*/

        /*V leftPop(K key, long timeout, TimeUnit unit);
        移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。*/

        /*V rightPop(K key, long timeout, TimeUnit unit);
        移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。*/

       /* V rightPopAndLeftPush(K sourceKey, K destinationKey);
        用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。*/

        /*V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit);
        用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。*/


        //String
        //不存在插入 ，存在不插入
        //this.redisTemplate.opsForValue().setIfAbsent();


        //multiSetIfAbsent 批量存在修改，不存在就不添加，并返回false
       /* Map<String,String> maps = new HashMap<String, String>();
        maps.put("multi11","multi11");
        maps.put("multi22","multi22");
        maps.put("multi33","multi33");
        Map<String,String> maps2 = new HashMap<String, String>();
        maps2.put("multi11","multi1");
        maps2.put("multi22","multi2");
        maps2.put("multi33","multi3");
        maps2.put("multi44","multi4");
        //true
        System.out.println(this.redisTemplate.opsForValue().multiSetIfAbsent(maps));
        //false
        System.out.println(this.redisTemplate.opsForValue().multiSetIfAbsent(maps2));*/


        //getAndSet V getAndSet(K key, V value);  设置键的字符串值并返回其旧值


        //multiGet List<V> multiGet(Collection<K> keys); 为多个键分别取出它们的值
        //multiSet  string 用map批量设置
       /* Map<String,String> maps = new HashMap<String, String>();
        maps.put("multi1","multi1");
        maps.put("multi2","multi2");
        maps.put("multi3","multi3");
        template.opsForValue().multiSet(maps);
        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
        System.out.println(template.opsForValue().multiGet(keys));*/


        /*increment Long increment(K key, long delta);  支持整数
        使用：template.opsForValue().increment("increlong",1);
        System.out.println("***************"+template.opsForValue().get("increlong"));
        increment Double increment(K key, double delta);  也支持浮点数
        使用：template.opsForValue().increment("increlong",1.2);
        System.out.println("***************"+template.opsForValue().get("increlong"));*/


      /*  append Integer append(K key, String value);
        如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
        使用：template.opsForValue().append("appendTest","Hello");
        System.out.println(template.opsForValue().get("appendTest"));
        template.opsForValue().append("appendTest","world");
        System.out.println(template.opsForValue().get("appendTest"));
        结果：Hello
                Helloworld*/


        /*get String get(K key, long start, long end);
        截取key所对应的value字符串
        使用：appendTest对应的value为Helloworld
        System.out.println("*********"+template.opsForValue().get("appendTest",0,5));
        结果：*********Hellow
        使用：System.out.println("*********"+template.opsForValue().get("appendTest",0,-1));
        结果：*********Helloworld
        使用：System.out.println("*********"+template.opsForValue().get("appendTest",-3,-1));
        结果：*********rld*/


        /*setBit Boolean setBit(K key, long offset, boolean value);
        对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
        key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
        使用：template.opsForValue().set("bitTest","a");
        // 'a' 的ASCII码是 97。转换为二进制是：01100001
        // 'b' 的ASCII码是 98  转换为二进制是：01100010
        // 'c' 的ASCII码是 99  转换为二进制是：01100011
        //因为二进制只有0和1，在setbit中true为1，false为0，因此我要变为'b'的话第六位设置为1，第七位设置为0
        template.opsForValue().setBit("bitTest",6, true);
        template.opsForValue().setBit("bitTest",7, false);
        System.out.println(template.opsForValue().get("bitTest"));
        结果：b*/

        /*getBit Boolean getBit(K key, long offset);
        获取键对应值的ascii码的在offset处位值
        使用：System.out.println(template.opsForValue().getBit("bitTest",7));
        结果：false*/


        //通用删除
        //this.redisTemplate.delete("mgp");

        //delete hash专用

        //remove  list set专用

        // set  differenceAndStore 获取 key 和 集合  collections 中的 key 集合的差集 / 将 key 和 集合  collections 中的 key 集合的差集 添加到  newkey 集合中
        // set  difference 获取两个集合的差集
        // set  distinctRandomMembers  随机取N次key为"WETRANSN:SYSLAN"，组成一个set集合，不可以重复取出

        // set  union  返回 key 和 othere 的并集
        // set  unionAndStore  将 key 与 otherKey 的并集,保存到 destKey 中


        // zset  incrementScore  对指定的 zset 的 value 值 , socre 属性做增减操作

        // zset  rank  获取 key 中指定 value 的排名(从0开始,从小到大排序)
        // zset  reverseRank  获取 key 中指定 value 的排名(从0开始,从大到小排序)

        // set zset  intersectAndStore  key 和 otherKey 两个集合的交集,保存在 destKey 集合中

        // zset Long zCard(K key);  获取有序集合的成员数, https://www.cnblogs.com/shamo89/p/8622152.html

        /*Cursor<TypedTuple<V>> scan(K key, ScanOptions options);  可用于遍历或模糊查询hash
        遍历zset
        使用： Cursor<ZSetOperations.TypedTuple<Object>> cursor = template.opsForZSet().scan("zzset1", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }
        结果：zset-1:1.0
        zset-2:2.0
        zset-3:3.0
        zset-4:6.0*/

        //expireAt 在指定时间点失效


        //rank:zset value自然排序的索引
        /*this.redisTemplate.opsForZSet().add("mgpzset", 1, 1);
        this.redisTemplate.opsForZSet().add("mgpzset", 2, 2);
        this.redisTemplate.opsForZSet().add("mgpzset", 3, 3);
        this.redisTemplate.opsForZSet().add("mgpzset", 4, 4);
        this.redisTemplate.opsForZSet().add("mgpzset", 1, 5);
        System.out.println(this.redisTemplate.opsForZSet().range("mgpzset",0,-1)); // [2, 3, 4, 1]
        System.out.println(this.redisTemplate.opsForZSet().rank("mgpzset", 2));  // 0*/


        //this.redisTemplate.opsForHash().keys();

    }

}
