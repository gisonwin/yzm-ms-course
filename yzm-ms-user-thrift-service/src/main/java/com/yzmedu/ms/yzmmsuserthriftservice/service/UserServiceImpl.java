package com.yzmedu.ms.yzmmsuserthriftservice.service;

import com.yzmedu.ms.yzmmsuserthriftservice.domain.User;
import com.yzmedu.ms.yzmmsuserthriftservice.mapper.UserMapper;
import com.yzmedu.protocol.user.UserInfo;
import com.yzmedu.protocol.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService.Iface {
    @Autowired(required = false)
    private UserMapper userMapper;
    /**
     * 根据id获取用户对象
     *
     * @param id
     */
    @Override
    public UserInfo getUserById(int id) throws TException {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        User user = userMapper.selectByPrimaryKey(userInfo);
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }

    /**
     * * 根据用户名称获取用户对象
     * *
     *
     * @param username
     */
    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    /**
     * * 注册新用户
     * *
     *
     * @param userInfo
     */
    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        int insert = userMapper.insert(user);
        log.info("insert "+insert + " record(s) success") ;
    }
}
