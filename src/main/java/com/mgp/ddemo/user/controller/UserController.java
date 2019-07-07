package com.mgp.ddemo.user.controller;

import com.mgp.ddemo.commons.util.RedisUtil;
import com.mgp.ddemo.user.bean.Jiepai;
import com.mgp.ddemo.user.bean.User;
import com.mgp.ddemo.user.service.JiepaiService;
import com.mgp.ddemo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


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

    @RequestMapping("/getUser")
    public Map<String, Object> getUser(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList = userService.queryUserList();
        map.put("abc",userList);
        return map;
    }

    @RequestMapping(value="/getUser/{username}", produces="application/json")
    public Map<String, Object> getUserByName(@PathVariable("username") String username){
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList = userService.queryUserListByUserName(username);
        map.put("abc",userList);
        return map;
    }

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

}
