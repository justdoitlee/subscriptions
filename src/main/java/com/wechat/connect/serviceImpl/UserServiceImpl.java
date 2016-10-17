package com.wechat.connect.serviceImpl;

import com.wechat.connect.dao.UserDao;
import com.wechat.connect.domain.User;
import com.wechat.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizhi on 2016/10/14.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userMapper;

    public User findUser() throws Exception {
        return this.userMapper.selectByPrimaryKey(1);
    }
}
