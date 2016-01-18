package com.hiersun.jewelry.api.service.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.direct.pojo.JrdsGood;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2003;
import com.hiersun.jewelry.api.entity.response.Response2003;
import com.hiersun.jewelry.api.entity.vo.Goods;
import com.hiersun.jewelry.api.entity.vo.ResultUser;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.UserAllInfo;
import com.hiersun.jewelry.api.user.pojo.JrMemberAccount;
import com.hiersun.jewelry.api.user.pojo.JrMemberInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("goodsIndexAppService")
public class GoodsIndexAppService implements BaseService {

	private static final Logger log = Logger.getLogger(GoodsIndexAppService.class);

	@Resource
	private DirectGoodsService directGoodsService;

	@Resource
	UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2003 body = JSON.parseObject(bodyStr, Request2003.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("goodsIndex	2003	接口 请求消息体：" + reqHead.toString());
		log.info("goodsIndex	2003	接口 请求消息体：" + bodyStr);
		try {
			Request2003 body = JSON.parseObject(bodyStr, Request2003.class);
			Long goodsId = body.getGoodsID();
			JrdsGood goods = directGoodsService.getOneDirectGoods(goodsId, true);
			if (goods == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 200102);
				ResponseBody responseBody = new ResponseBody();

				return this.packageMsgMap(responseBody, respHeader);
			}
			Response2003 responseBody = new Response2003();
			Goods resultGoods = new Goods();
			resultGoods.setGoodsBuyPrice(goods.getBuyingPrice().doubleValue());
			resultGoods.setGoodsDesc(goods.getGoodDesc());
			resultGoods.setGoodsID(goods.getId());
			resultGoods.setGoodsNO(goods.getGoodNo());
			resultGoods.setGoodsName(goods.getGoodName());
			resultGoods.setVisitTimes(goods.getViews().toString());
			resultGoods.setGoodsPrice(goods.getDirectPrice().doubleValue());
			Long seleerUserID = goods.getSellerMemberId();
			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			// 返回的body
			UserAllInfo userInifo = userService.getUserAllInfoByID(seleerUserID);
			if (userInifo != null) {
				ResultUser user = new ResultUser();
				JrMemberAccount account = userInifo.getJrMemberAccount();
				JrMemberInfo jrMInfo = userInifo.getJrMemberInfo();
				user.setMobile(account == null ? "" : account.getUserMobile());
				user.setUserID(seleerUserID);
				if (jrMInfo == null) {
					user.setNickName("");
				} else {
					if (jrMInfo.getNickName() != null) {
						user.setNickName(jrMInfo.getNickName());
					} else {
						user.setNickName(account == null ? "" : CommonUtils.mobileForNickName(account.getUserMobile()));
					}
				}

				// user.setIcon(picDoMain +
				// "apiupload/uploadFile/bd6661231506e0fe52c15a3a77ecc1e7.jpg");
				// user.setIcon(Commons.HEAD_IOC);
				user.setBigIcon(jrMInfo.getBigIcon());
				user.setSmallIcon(jrMInfo.getSmallIcon());
				
				resultGoods.setUser(user);
			} else {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
				ResponseBody responseB = new ResponseBody();
				return this.packageMsgMap(responseB, respHeader);
			}

			List<Map<String, Object>> goodsMainPicList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			if (goods.getHostGragp() != null) {
				map.put("picUrl", Commons.PIC_DOMAIN + goods.getHostGragp());
			}
			goodsMainPicList.add(map);
			resultGoods.setGoodsMainPicList(goodsMainPicList);
			// responseBody.setGoods(goods);
			responseBody.setGoods(resultGoods);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("goodsIndex 2003接口，发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);

		}
	}

	private Map<String, Object> packageMsgMap(Response2003 res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}

	private Map<String, Object> packageMsgMap(ResponseBody res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}

}
