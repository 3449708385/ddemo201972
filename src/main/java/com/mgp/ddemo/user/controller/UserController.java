package com.mgp.ddemo.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgp.ddemo.commons.exceptionhandler.GlobalException;
import com.mgp.ddemo.commons.interceptor.NeedLogin;
import com.mgp.ddemo.commons.rabbit.RabbitSender;
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

    @NeedLogin
    @RequestMapping("/getMQData/{rkey}")
    public Map<String, Object> getMqData(@PathVariable("rkey") String rkey){
        Map<String, Object> map = new HashMap<String, Object>();
        //mqProducer.send();
            //rabbitSender.send00();
            //rabbitSender.send01();
            rabbitSender.send03();
           // rabbitSender.send2();
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

}
