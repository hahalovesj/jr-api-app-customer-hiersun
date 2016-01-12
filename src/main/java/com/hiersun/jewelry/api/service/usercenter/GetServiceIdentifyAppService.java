package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4017;
import com.hiersun.jewelry.api.entity.response.Response4017;
import com.hiersun.jewelry.api.orderservice.domain.QualificationPicVo;
import com.hiersun.jewelry.api.orderservice.pojo.JrasGoodInfoConfirm;
import com.hiersun.jewelry.api.orderservice.pojo.JrasGoodQualification;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.uploadresource.domain.AttachmentVo;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("getServiceIdentifyAppService")
public class GetServiceIdentifyAppService implements BaseService {

	@Resource
	OrderService orderService;

	@Resource
	DirectGoodsService directGoodsService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		try {
			Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
			// 根据订单查询goodsId
			long goodsId = orderService.selectGoodIdByOrderNO(body.getOrderNO());

			Map<String, Object> paramMap = new HashMap<String, Object>();
			// 鉴定结果
			paramMap.put("goodsId", goodsId);
			// 增值业务
			paramMap.put("businessType", 1);
			JrasGoodQualification qual = orderService.selectQualification(paramMap);

			if (qual == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 0);
				ResponseBody resp = new ResponseBody();
				return this.packageMsgMap(resp, respHeader);
			}
			Long id = qual.getId();
			// 商品确认信息
			JrasGoodInfoConfirm jrasGoodInfoConfirm = orderService.selectConfirm(goodsId);
			// 商品实物信息
			List<AttachmentVo> pciList = directGoodsService.getJrdsGoodPic(jrasGoodInfoConfirm.getId(),
					"jras_good_info_confirm", "jrasconfirm");

			List<Map<String, String>> resultO = new ArrayList<Map<String, String>>();
			Map<String, String> resultOM = new HashMap<String, String>();
			for (int i = 0; i < pciList.size(); i++) {
				resultOM.put("picUrl", pciList.get(i).getFullName());
				resultOM.put("picDesc", pciList.get(i).getAttrDesc());
				resultO.add(resultOM);
			}

			// 返回
			Response4017 resp = new Response4017();
			List<Map<String, String>> resultQ = null;
			// 证书类型
			if (qual.getCertificationType() != 0) {
				String qualificatonType = QualificationType.QUALIFICATION_TYPE.get(qual.getCertificationType()
						.intValue());
				resp.setCertificateType(qualificatonType);
				// 鉴定结果图片
				// 增值服务的查询条件
				// 表名：（table_name）jras_good_qualification
				// 文件类型（fileType）:jrasappraisal
				Map<String, Object> paramMapQ = new HashMap<String, Object>();
				paramMapQ.put("table_name", "jras_good_qualification");
				paramMapQ.put("file_type", "jrasappraisal");
				paramMapQ.put("data_id", id);
				List<QualificationPicVo> QPicList = orderService.selectQualificationPicList(paramMapQ);

				// 返回
				resultQ = new ArrayList<Map<String, String>>();
				Map<String, String> resultQM = new HashMap<String, String>();
				for (int i = 0; i < QPicList.size(); i++) {
					resultQM.put("Pic", QPicList.get(i).getPicUrl());
					resultQ.add(resultQM);
				}
				resp.setCertificatePicList(resultQ);
			}

			byte mNumber = jrasGoodInfoConfirm.getMatchedDegree();
			resp.setBeanInfo(QualificationType.MATCHED_DEGREE.get(mNumber));
			resp.setIdentifyResult(jrasGoodInfoConfirm.getSpecify());
			resp.setPicList(resultO);

			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(resp, respHeader);

		} catch (Exception e) {
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response4017 res, ResponseHeader respHead) {
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
