package com.hiersun.jewelry.api.service.user;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestResetpwd;
import com.hiersun.jewelry.api.entity.response.ResponseResetpwd;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.User;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunteng on 16/1/11.
 */
@Service("resetpwdAppService")
public class ResetpwdAppService implements BaseService {

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
        RequestResetpwd body = JSON.parseObject(bodyStr, RequestResetpwd.class);
        return body.volidateValue();
    }

    @Override
    public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
        try {
            RequestResetpwd body = JSON.parseObject(bodyStr, RequestResetpwd.class);
            int voliNumber = body.volidateValue();
            if (voliNumber != 0) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, voliNumber);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }

            String acctionType = body.getsMsgAcctionType();
            String veriCode = body.getVeriCode();

            String cacheveriCode = redisBaseServiceImpl.get("api" + acctionType + body.getMobile());
            if (cacheveriCode == null || cacheveriCode.trim().length() < 1) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }
            if (!cacheveriCode.equals(veriCode)) {
                ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
                ResponseBody responseBody = new ResponseBody();
                return this.packageMsgMap(responseBody, respHeader);
            }
            // 数据库操作
            User userQuery = new User();
            userQuery.setUserMobile(body.getMobile());
            User user = userService.getUserByMobile(userQuery);
            user.setUserPassword(body.getPassword());
            userService.modifyPassword(user);

            // 配置返回信息
            ResponseResetpwd responseBody = new ResponseResetpwd();
            responseBody.setMobile(body.getMobile());

            ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);

            return this.packageMsgMap(responseBody, respHead);
        } catch (Exception e) {
            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
            ResponseBody responseBody = new ResponseBody();
            e.printStackTrace();
            return this.packageMsgMap(responseBody, respHeader);
        }
    }

    private Map<String,Object> packageMsgMap(ResponseResetpwd res, ResponseHeader respHead){
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
