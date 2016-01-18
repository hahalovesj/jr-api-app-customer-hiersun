package com.hiersun.jewelry.api.service.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.direct.pojo.JrdsGood;
import com.hiersun.jewelry.api.direct.pojo.JrdsGoodAudit;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2004;
import com.hiersun.jewelry.api.entity.response.Response2004;
import com.hiersun.jewelry.api.entity.vo.Appraisal;
import com.hiersun.jewelry.api.entity.vo.Goods;
import com.hiersun.jewelry.api.entity.vo.ResultUser;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.uploadresource.domain.AttachmentVo;
import com.hiersun.jewelry.api.user.domain.UserAllInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("goodsInfoAppService")
public class GoodsInfoAppService implements BaseService {

	private static final Logger log = Logger.getLogger(GoodsInfoAppService.class);

	@Resource
	DirectGoodsService directGoodsService;

	@Resource
	UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2004 body = JSON.parseObject(bodyStr, Request2004.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("goodsInfo	2004	接口 请求消息体：" + reqHead.toString());
		log.info("goodsInfo	2004	接口 请求消息体：" + bodyStr);

		try {
			Request2004 body = JSON.parseObject(bodyStr, Request2004.class);
			// 请求的信息
			// body.getGoodsID();
			// 。。。(根据传来的值做数据查询)
			long goodsId = body.getGoodsID();
			List<AttachmentVo> pciList = directGoodsService.getJrdsGoodPic(goodsId, "jrds_good", null);
			JrdsGood goods = directGoodsService.getOneDirectGoods(goodsId, false);
			if (goods == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 200102);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			// 返回的body
			Response2004 responseBody = new Response2004();
			Goods resultGoods = new Goods();
			// resultGoods.setGoodsBuyPrice(goodsBuyPrice);
			resultGoods.setGoodsBuyPrice(goods.getBuyingPrice().doubleValue());
			resultGoods.setGoodsDesc(goods.getGoodDesc());
			resultGoods.setGoodsID(goods.getId());
			resultGoods.setGoodsNO(goods.getGoodNo());
			resultGoods.setGoodsName(goods.getGoodName());
			resultGoods.setVisitTimes(goods.getViews().toString());
			resultGoods.setGoodsPrice(goods.getDirectPrice().doubleValue());
			resultGoods.setCreateTime(DateUtil.dateToStr(goods.getCreated(), "yyyy-MM-dd HH:mm:ss"));
			Long seleerUserID = goods.getSellerMemberId();

			UserAllInfo userInifo = userService.getUserAllInfoByID(seleerUserID);
			ResultUser user = new ResultUser();
			user.setMobile(userInifo.getJrMemberAccount().getUserMobile());
			user.setUserID(seleerUserID);
			if (userInifo.getJrMemberInfo() != null) {
				if (userInifo.getJrMemberInfo().getNickName() != null) {
					user.setNickName(userInifo.getJrMemberInfo().getNickName());
				} else {
					user.setNickName(CommonUtils.mobileForNickName(userInifo.getJrMemberAccount().getUserMobile()));
				}
			}
			user.setBigIcon(userInifo.getJrMemberInfo().getBigIcon());
			user.setSmallIcon(userInifo.getJrMemberInfo().getSmallIcon());
			// user.setIcon(Commons.HEAD_IOC);
			resultGoods.setUser(user);

			List<Map<String, Object>> goodsPicList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < pciList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (pciList.get(i).getFullName() != null) {
					map.put("picUrl", Commons.PIC_DOMAIN + pciList.get(i).getFullName());
				}
				if (pciList.get(i).getAttrDesc() != null) {
					map.put("picDesc", pciList.get(i).getAttrDesc());
				}

				goodsPicList.add(map);
			}

			resultGoods.setGoodsPicList(goodsPicList);
			// 鉴定信息
			JrdsGoodAudit jrdsGoodAudit = directGoodsService.getGoodAudit(goods.getId());
			Appraisal appraisal = new Appraisal();
			if (jrdsGoodAudit != null) {
				appraisal.setBrand(jrdsGoodAudit.getBrand());
				appraisal.setStyle(QualificationType.STYLE_TUPE_MAP.get(jrdsGoodAudit.getStyle().intValue()));
				int materialTag = 0;
				if (StringUtils.isEmpty(jrdsGoodAudit.getMaterialTag())) {
					materialTag = Integer.parseInt(jrdsGoodAudit.getMaterialTag());
				}
				appraisal.setMaterial(QualificationType.MATERIAL_TYPE.get(materialTag));
				if (jrdsGoodAudit.getTagetPeople() != null) {
					appraisal.setCrowd(jrdsGoodAudit.getTagetPeople() == true ? "女士" : "男士");
				} else {
					appraisal.setCrowd("男士");
				}
				appraisal.setWeight(goods.getWeightDesc().toString());
			}

			responseBody.setAppraisal(appraisal);
			responseBody.setGoods(resultGoods);
			return this.packageMsgMap(responseBody, respHead);

		} catch (Exception e) {
			log.error("goodsInfo 2004接口，发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response2004 res, ResponseHeader respHead) {
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
