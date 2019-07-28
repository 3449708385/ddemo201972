package com.mgp.ddemo.commons.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


/**
 * 自定义realm
 * @author https://blog.csdn.net/qq_36676077/article/details/80622786
 *
 */
public class UserRealm extends AuthorizingRealm{
  /*  @Autowired
    private UserService UserService;*/
    //执行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        // TODO Auto-generated method stub
       /* System.out.println("授权");
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //给资源授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission(user.getPerms());
        return simpleAuthorizationInfo;*/
        return null;
    }
    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        // TODO Auto-generated method stub
        System.out.println("认证");

        /*//shiro判断逻辑
        UsernamePasswordToken user = (UsernamePasswordToken) arg0;
        User realUser = new User();
        realUser.setName(user.getUsername());
        realUser.setPassword(String.copyValueOf(user.getPassword()));
        User newUser = UserService.findUser(realUser);
        //System.out.println(user.getUsername());
        if(newUser == null){
            //用户名错误
            //shiro会抛出UnknownAccountException异常
            return null;
        }

        return new SimpleAuthenticationInfo(newUser,newUser.getPassword(),"");*/
        return null;
    }
}
