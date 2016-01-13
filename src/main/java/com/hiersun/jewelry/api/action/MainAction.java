package com.hiersun.jewelry.api.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hiersun.jewelry.api.direct.service.DirectGoodMessageService;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.expressinfo.service.ExpressInfoService;
import com.hiersun.jewelry.api.homescreen.service.KeyWordService;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.service.utils.UserUtil;
import com.hiersun.jewelry.api.user.service.MemberCentreMessageService;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.SpringContextUtil;
import com.hiersun.jewelry.api.util.ValiHeadUtil;

@Controller
@RequestMapping("/api")
public class MainAction{

	@Resource
	RedisBaseService redisBaseServiceImpl;

	@Resource
	UserService userService;

	@Resource
	OrderService orderService;

	@Resource
	KeyWordService keyWordService;

	@Resource
	DirectGoodsService directGoodsService;

	@Resource
	DirectGoodMessageService directGoodMessageService;

	@Resource
	DirectOrderService directOrderService;

	@Resource
	ExpressInfoService expressInfoService;

	@Resource
	MemberCentreMessageService memberCentreMessageService;


	@RequestMapping(value = "/admin", method = { RequestMethod.POST, RequestMethod.GET })
	public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	@RequestMapping(value = "/main", method = { RequestMethod.POST, RequestMethod.GET })
	public void main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=gbk");
		try{
			// 设置编码
			
			String msg = request.getParameter("msg");
			// 验证url参数
			boolean isRight = ValiHeadUtil.VeriParams(msg, response);
			if (!isRight) {
				return;
			}
			String newMsg = msg.replace(" ", "");
			// 获取接口编码
			String tranType = ValiHeadUtil.getTranTypeStr(newMsg);
			// 获取请求header的实体
			RequestHeader reqHead = ValiHeadUtil.getRequestHeader(newMsg);
			// 请求body
			String requestBodyStr = ValiHeadUtil.getBodyStr(newMsg);
		
			BaseService baseService = (BaseService)SpringContextUtil.getBean(tranType+"AppService",BaseService.class);
			//看是否需要登陆
			Long userId = null;
				userId = reqHead.getToken() == null?null : UserUtil.getUsetId(reqHead.getToken(),redisBaseServiceImpl);
				if(baseService.ifValidateLogin()){
				if (userId == null) {
					ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 900010);
					ResponseBody responseBody = new ResponseBody();
					ResponseUtil.ResponseWriter(respHeader, responseBody, response);
					return;
				}
			}
			//基础校验
			int resCode = baseService.baseValidateMsgBody(requestBodyStr,userId);
			if(resCode==0){
				Map<String,Object> map = baseService.doController(reqHead,requestBodyStr,userId);
				ResponseUtil.ResponseWriter(map, response);
			}else{
				ResponseHeader respHeader = new ResponseHeader(resCode);
				ResponseBody responseBody = new ResponseBody();
				ResponseUtil.ResponseWriter(respHeader, responseBody, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			ResponseHeader respHeader = new ResponseHeader(99999);
			ResponseBody responseBody = new ResponseBody();
			ResponseUtil.ResponseWriter(respHeader, responseBody, response);
			return;
		}
		
	}
	

}
