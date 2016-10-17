package com.wechat.connect.service;

import com.wechat.connect.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lizhi on 2016/10/14.
 */
public interface UserService {
    /**
     * 用户
     *
     * @return
     * @throws Exception
     */

    User findUser() throws Exception;
}
