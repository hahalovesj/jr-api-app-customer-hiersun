package com.hiersun.jewelry.api.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestRegist;
import com.hiersun.jewelry.api.entity.response.ResponseRegist;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.User;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.RandomStringUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

/**
 * Created by lijunteng on 16/1/11.
 */
@Service("registAppService")
public class RegistAppService implements BaseService {

    private static final Logger log = Logger.getLogger(RegistAppService.class);

    @Resource
    private RedisBaseService redisBaseServiceImpl;
    @Resource
    private UserService userService;

    @Override
    public boolean ifValidateLogin() {
        return false;
    }

    @Override
    public Integer baseValidateMsgBody(String bodyStr, Long userId) {

        RequestRegist body = JSON.parseObject(bodyStr, RequestRegist.class);
        return body.volidateValue();

    }

    @Override
    public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

        log.info("regist 1002 用户注册请求头信息: " + reqHead.toString());
        log.info("regist 1002 用户注册请求消息体: " + bodyStr);

        try {
            RequestRegist body = JSON.parseObject(bodyStr, RequestRegist.class);

            String veriCode = body.getVeriCode();
            String mobile = body.getMobile();
            String acctionType = body.getsMsgAcctionType();
            // 对比验证码
            String cacheNumber = redisBaseServiceImpl.get("api" + acctionType + mobile);
            if (cacheNumber == null || cacheNumber.trim().length() < 1) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }
            if (!veriCode.equals(cacheNumber)) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }
            // ... 操作数据库
            boolean isExis = userService.isExistMobile(mobile);
            // 手机号存在返回已存在
            if (isExis) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100101);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }

            User user = new User();
            user.setUserMobile(mobile);
            user.setUserPassword(body.getPassword());
            user.setImie(reqHead.getImei());
            user.setPushId(body.getPushMsgID());

            userId = userService.regist(user);
            String token = RandomStringUtil.randomString(16);

            // 注册成功，删除缓存的验证码
            redisBaseServiceImpl.del(CatchKey.APP_MSG_KEY + acctionType + mobile);
            // 存token
            redisBaseServiceImpl.set(CatchKey.APP_USERID_CACH_KEY_START + token, userId.toString());

            // 配置返回信息
            ResponseRegist responseBody = new ResponseRegist();
            responseBody.setMobile(body.getMobile());
            responseBody.setJumpTransaction(body.getJumpTransaction());
            responseBody.setUserId(userId.toString());
            responseBody.setToken(token);
            ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
            return this.packageMsgMap(responseBody, respHead);
        } catch (Exception e) {
            log.error("regist 1002 用户注册接口，发生异常，异常信息：" + e.getMessage());
            e.printStackTrace();

            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
            ResponseBody responseBody = new ResponseBody();
            return this.packageMsgMap(responseBody, respHeader);
        }
    }

    private Map<String,Object> packageMsgMap(RequestRegist res, ResponseHeader respHead){
        Map<String, Object> responseMsg = new HashMap<String, Object>();
        responseMsg.put("body", res);
        responseMsg.put("head", respHead);
        return responseMsg;
    }

    private Map<String,Object> packageMsgMap(ResponseBody res,ResponseHeader respHead){
        Map<String, Object> responseMsg = new HashMap<String, Object>();
        responseMsg.put("body", res);
        responseMsg.put("head", respHead);
        return responseMsg;
    }
}
