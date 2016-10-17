package com.wechat.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;

/**
 * Created by lizhi on 2016/10/16.
 */
public abstract class BaseController {
    /*日志记录器*/
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回ResponseEntity
     *
     * @param obj
     * @param <T>
     * @return
     */
    protected <T> ResponseEntity<T> makeResponseEntity(T obj) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mt = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mt);
        ResponseEntity<T> responseEntity =
                new ResponseEntity<T>(obj, headers, HttpStatus.OK);
        return responseEntity;
    }


}
