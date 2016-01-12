package com.hiersun.jewelry.api.service.sad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.domain.DirectGoodsInfoVo;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request3004;
import com.hiersun.jewelry.api.entity.response.Response5001;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("putSaleGoodsAppService")
public class PutSaleGoodsAppService implements BaseService {
	
	private static Logger log = Logger.getLogger(PutSaleGoodsAppService.class);

	@Resource
	DirectGoodsService directGoodsService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request3004 body = JSON.parseObject(bodyStr, Request3004.class);
		// TODO 实现此方法
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("正在处理提交直售订单接口putSaleGoods(3004),Header=" + reqHead.toString());
		log.info("正在处理提交直售订单接口putSaleGoods(3004),Body=" + bodyStr);
		try {
			Request3004 body = JSON.parseObject(bodyStr, Request3004.class);

			DirectGoodsInfoVo directGoodsInfo = new DirectGoodsInfoVo();
			List<Map> picList = body.getGoodsPicList();
			List<Long> picId = new ArrayList<Long>();
			for (int i = 0; i < picList.size(); i++) {
				// picId.add(picList.get(i).get("picID")));
				int id = (Integer) picList.get(i).get("picID");
				picId.add((long) id);
				boolean isMain = (Boolean) picList.get(i).get("isMain");
				if (isMain) {
					int mainId = (Integer) picList.get(i).get("picID");
					directGoodsInfo.setInputMainPicID((long) mainId);
				}
			}

			directGoodsInfo.setGoodsPrice(body.getGoodsPrice());
			directGoodsInfo.setGoodsBuyPrice(body.getGoodsBuyPrice());
			directGoodsInfo.setInputGoodsDec(body.getGoodsDec());
			directGoodsInfo.setInputGoodsName(body.getGoodsName());
			directGoodsInfo.setInputPicID(picId);
			directGoodsInfo.setWeightDesc(body.getWeight());
			// directGoodsInfo.setInputWeight(body.getWeight());
			directGoodsInfo.setUserId(userId);

			DirectGoodsInfoVo resultInfo = directGoodsService.createDirectGoods(directGoodsInfo);
			if (resultInfo == null) {
				log.error("生成直售商品对象失败，resultInfo=" + resultInfo);
				ResponseHeader respHead = new ResponseHeader(9999);
				ResponseBody responseBody = new ResponseBody();
				this.packageMsgMap(responseBody, respHead);
			}

			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTransactionType(reqHead.getTransactionType());

			Response5001 responseBody = new Response5001();
			responseBody.setOrderNO(resultInfo.getOutputGoodsNO());
			responseBody.setGoodsID(resultInfo.getOutputGoodsID());

			return this.packageMsgMap(responseBody, respHead);

		} catch (Exception e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				log.error("提交直售订单失败，异常类：\"" + e.getStackTrace()[i].getClassName() + "\"; 函数方法：\""
						+ e.getStackTrace()[i].getMethodName() + "()\"; Error：" + e.getMessage() + "; 错误行："
						+ e.getStackTrace()[i].getLineNumber());
			}
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response5001 res, ResponseHeader respHead) {
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
