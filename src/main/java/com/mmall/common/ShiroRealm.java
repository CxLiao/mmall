package com.mmall.common;

import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.util.CryptoUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Function: Shiro权限验证实现类<br/>
 * @Reason: shiro的认证最终是交给Realm进行执行，所以我们需要自己重新实现一个Realm，继承AuthorizingRealm。<br/>
 * @author liaocx
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    public ShiroRealm() {
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        setCredentialsMatcher(getHashedCredentialsMatcher());
    }

    /**
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，防止密码在数据库里明码保存；
     * 在登陆认证的时候，这个类也负责对form里输入的密码进行编码。
     */
    private HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha384Hash.ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(CryptoUtil.HASH_ITERATIONS);
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return credentialsMatcher;
    }

    /**
     * 授权：权限认证（为当前登录的Subject授予角色和权限）.<br/>
     * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置）， 超过这个时间间隔再刷新页面，该方法会被执行
     * doGetAuthorizationInfo()是权限控制， 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行，
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可.<br/>
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证: 即登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userMapper.selectUserByUsername(token.getUsername());
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new UnknownAccountException("无此账户");
        }
        // info中存放的是数据库中正确的账号信息，token参数包含了登录表单传递进来的待认证的信息
        // shiro会自动进行对比校验：在SimpleCredentialsMatcher类的doCredentialsMatch()方法中做对比
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        //设置盐值
        info.setCredentialsSalt(ByteSource.Util.bytes(CryptoUtil.getSalt()));
        return info;
    }
}
