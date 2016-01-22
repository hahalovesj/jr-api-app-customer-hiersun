package com.hiersun.jewelry.api.service.user;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestResetpwd;
import com.hiersun.jewelry.api.entity.response.RespUser;
import com.hiersun.jewelry.api.entity.response.ResponseResetpwd;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.User;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.RandomStringUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunteng on 16/1/11.
 */
@Service("resetpwdAppService")
public class ResetpwdAppService implements BaseService {

    private static final Logger log = Logger.getLogger(ResetpwdAppService.class);

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

        log.info("resetpwd 1005 用户重置密码请求头信息: " + reqHead.toString());
        log.info("resetpwd 1005 用户重置密码请求消息体: " + bodyStr);

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

            String cacheveriCode = redisBaseServiceImpl.get(CatchKey.APP_MSG_KEY + acctionType + body.getMobile());
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
            User u = userService.getUserByMobile(userQuery);
            u.setUserPassword(body.getPassword());
            userService.modifyPassword(u);
            
            // 删除旧的token
         	String token = reqHead.getToken();
       		redisBaseServiceImpl.del(CatchKey.APP_USERID_CACH_KEY_START + token);
            
            UserInfo info = new UserInfo();
			info.setUserMobile(u.getMobile());
			UserInfo resultUserInfo = userService.getUserInfoByMobile(info);
			// 登陆成功 存token
       		String newToken = RandomStringUtil.randomString(16);
       		redisBaseServiceImpl.set(CatchKey.APP_USERID_CACH_KEY_START + newToken, resultUserInfo.getUserId().toString());
       		// 修改，删除缓存的验证码
            redisBaseServiceImpl.del(CatchKey.APP_MSG_KEY + acctionType + body.getMobile());
            
            // 配置返回信息
            ResponseResetpwd responseBody = new ResponseResetpwd();

			RespUser user = new RespUser();
			user.setMobile(resultUserInfo.getUserMobile());
			user.setToken(newToken);
			user.setBigIcon(Commons.PIC_DOMAIN + resultUserInfo.getBigIcon());
			user.setSmallIcon(Commons.PIC_DOMAIN +resultUserInfo.getSmallIcon());
			if (resultUserInfo.getSex() == null) {
				user.setSex(QualificationType.SEX_MAP.get("0"));
			} else {
				user.setSex(resultUserInfo.getSex().equals("0") ? "女" : "男");
			}
			if (resultUserInfo.getNickName() != null) {
				user.setNickName(resultUserInfo.getNickName());
			} else {
				user.setNickName(CommonUtils.mobileForNickName(body.getMobile()));
			}
			if (resultUserInfo.getBirthday() != null) {
				user.setBirthday(DateUtil.dateTypeToString(resultUserInfo.getBirthday(), "yyyy-MM-dd HH:mm:ss"));
			}
			BankCardNum bankCarNum = new BankCardNum();
			if (resultUserInfo.getCardNo() != null) {
				bankCarNum.setBankCardNum(resultUserInfo.getCardNo());
			}
			if (resultUserInfo.getBankName() != null) {
				bankCarNum.setBankName(resultUserInfo.getBankName());
			}
			if (resultUserInfo.getRealName() != null) {
				bankCarNum.setUserRealName(resultUserInfo.getRealName());
			}
			user.setBankCardNum(bankCarNum);
			responseBody.setUser(user);
            ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);

            return this.packageMsgMap(responseBody, respHead);
        } catch (Exception e) {
            log.error("resetpwd 1005 用户注册接口，发生异常，异常信息：" + e.getMessage());
            e.printStackTrace();

            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
            ResponseBody responseBody = new ResponseBody();
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
