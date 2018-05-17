package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @author liaocx
 * @date 2017/10/16
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if (user == null){
            //用户名不存在已在上面检验过，逻辑到这用户肯定存在，因此这里user为null是因为密码的问题
             return ServerResponse.createByErrorMessage("密码错误");
        }
        //密码置空，需要将user对象中的密码置空 以免返回给前端
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<User> register(User user) {
        //校验参数、前端必须同时传用户username、password
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return  ServerResponse.createByErrorMessage("必填参数不完整!");
        }
        //校验用户名是否存在
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            //用户名存在
            return validResponse;
        }
        //校验email是否存在
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //所有注册用户只能是普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);
        int resultCount = userMapper.insert(user);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("注册成功", user);
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)) {
            //开始校验
            //username
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            //email
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0) {
                    return ServerResponse.createByErrorMessage("Email已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回的密码问题是空的");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username,String question,String answer) {
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if (resultCount > 0) {
            //说明问题及问题答案是这个用户的，并且正确
            //生成类似dc64a334-7812-4d23-ad7c-d8e13dc08d7f这样的值
            String forgetToken = UUID.randomUUID().toString();
            //将forgetToken放到本地cache中
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken) {
        /*校验参数*/
        // 检查forgetToken是否为空
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误,Token需要传递");
        }
        //检查username是否存在
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //从cache中获取token
        String getToken = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        //验证从cache中获取的token是否为空
        if (StringUtils.isBlank(getToken)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(forgetToken,getToken)) {
            /*开始重置密码*/
            //将参数中的passwordNew生成md5加密的字符串
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            //在数据库中重置密码,返回影响行数
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
            if (rowCount > 0) {
                //重置密码成功
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else {
            //StringUtils.equals(forgetToken,getToken)条件为false
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
        //rowCount > 0判断条件为false
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user) {
        //防止横向越权，要验证一下用户的旧密码；一定要指定是这个用户，因为我们会查询一个count(1)，如果不指定userId，那结果很可能为true
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        //username不能更新
        //email要进行校验，校验新的email是否存在，如果存在不能是当前用户的（逻辑错误）
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已存在，请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            //重新去获取了数据库的数据，用这个拥有完整信息的updateUser，去填充session中的current_user（密码已经置空）
            //确保session中信息更完整
            updateUser = userMapper.selectByPrimaryKey(updateUser.getId());
            //置空的是updateUser里的password的值 数据库中没变
            updateUser.setPassword(StringUtils.EMPTY);
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        //!!!密码置空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    //backend
    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 授权
     * @param username
     * @return
     */
    @Override
    public ServerResponse<String> authorization(String username) {
        User user = userMapper.selectUserByUsername(username);
        if (Objects.isNull(user)) {
            return ServerResponse.createByErrorMessage("被授权的账号不存在!");
        }
        user.setRole(Const.Role.ROLE_ADMIN);
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("授权成功!");
        }
        return ServerResponse.createByErrorMessage("更新用户信息出错!");
    }
}
