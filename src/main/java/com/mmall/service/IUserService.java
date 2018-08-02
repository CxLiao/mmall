package com.mmall.service;


import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @author liaocx on 2017/10/16.
 */
public interface IUserService {

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    ServerResponse<User> register(User user);

    /**
     * 校验type类型的"str"值在数据库中是否存在
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 获取密码提示问题接口
     * @param username
     * @return
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 校验密码提示问题的答案、核对成功后返回一个forgetToken
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 未登录状态下通过forgetToken重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 登录状态下调用的重置密码接口
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 登录状态下更新个人信息
     * @param user
     * @return
     */
    ServerResponse<User> updateInformation(User user);

    /**
     * 登录状态下获取用户详细信息
     * @param userId
     * @return
     */
    ServerResponse<User> getInformation(Integer userId);

    /**
     * 校验是否为管理员
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(User user);

    /**
     * 赋予管理员权限
     */
    public ServerResponse<String> authorization(String username);
}
