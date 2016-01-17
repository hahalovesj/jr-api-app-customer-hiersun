package com.hiersun.jewelry.api.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

/**
 * Created by lijunteng on 16/1/11.
 */
@Service("logoutAppService")
public class LogoutAppService implements BaseService {

    private static final Logger log = Logger.getLogger(LogoutAppService.class);

    @Resource
    private RedisBaseService redisBaseServiceImpl;

    @Override
    public boolean ifValidateLogin() {
        return false;
    }

    @Override
    public Integer baseValidateMsgBody(String bodyStr, Long userId) {
        return 0;
    }

    @Override
    public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

        log.info("logout 1004 用户注销请求头信息: " + reqHead.toString());
        log.info("logout 1004 用户注销请求消息体: " + bodyStr);
        try {
            String token = reqHead.getToken();
            boolean isDel = redisBaseServiceImpl.del(CatchKey.APP_USERID_CACH_KEY_START + token);
            if (!isDel) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }
            // 配置返回信息
            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 0);
            ResponseBody responseBody = new ResponseBody();
            return this.packageMsgMap(responseBody, respHeader);
        } catch (Exception e) {
            log.error("logout 1004 用户注销接口，发生异常，异常信息：" + e.getMessage());
            e.printStackTrace();

            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
            ResponseBody responseBody = new ResponseBody();
            return this.packageMsgMap(responseBody, respHeader);
        }
    }

    private Map<String,Object> packageMsgMap(ResponseBody res,ResponseHeader respHead){
        Map<String, Object> responseMsg = new HashMap<String, Object>();
        responseMsg.put("body", res);
        responseMsg.put("head", respHead);
        return responseMsg;
    }
}
