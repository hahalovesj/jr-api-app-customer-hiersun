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
import com.hiersun.jewelry.api.direct.domain.QueryGoodsByParamVo;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2002;
import com.hiersun.jewelry.api.entity.response.Response2002;
import com.hiersun.jewelry.api.homescreen.pojo.JrdsSearchKeyword;
import com.hiersun.jewelry.api.homescreen.service.KeyWordService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("searchAppService")
public class SearchAppService implements BaseService {

	private static final Logger log = Logger.getLogger(SearchAppService.class);
	@Resource
	private DirectGoodsService directGoodsService;

	@Resource
	KeyWordService keyWordService;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2002 body = JSON.parseObject(bodyStr, Request2002.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("search	2002	接口 请求消息体：" + reqHead.toString());
		log.info("search	2002	接口 请求消息体：" + bodyStr);
		try {
			Request2002 body = JSON.parseObject(bodyStr, Request2002.class);
			String keyWord = body.getKeyWord();
			String orderBy = body.getOrderBy();
			String orderType = body.getOrderType();
			Integer pageNo = body.getPageNo();
			QueryGoodsByParamVo vo = new QueryGoodsByParamVo();
			if (pageNo != null) {
				vo.setStart(pageNo * 20);
			}
			if (pageNo == null) {
				vo.setStart(0);
			}
			vo.setEnd(20);
			if (keyWord != null) {
				vo.setKeyWord(keyWord);
			}
			if (orderBy != null) {
				vo.setOrderBy(orderBy);
			}
			if (orderType != null) {
				vo.setOrderType(orderType);
			}

			List<QueryGoodsByParamVo> goodList = directGoodsService.getGoodsListByParam(vo);
			// 添加热词
			JrdsSearchKeyword jrdsSearchKeyword = new JrdsSearchKeyword();
			jrdsSearchKeyword.setKeyWord(keyWord);
			keyWordService.addSearchKeyWord(jrdsSearchKeyword);
			// 总数
			int countNumber = directGoodsService.getCountGoods(vo);
			int nowNumber = (pageNo + 1) * 20;
			// 返回的header
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			// 返回的body
			Response2002 responseBody = new Response2002();

			List<Object> goodsList = new ArrayList<Object>();
			for (int i = 0; i < goodList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("goodsPicUrl", Commons.PIC_DOMAIN + goodList.get(i).getHostGragp());
				map.put("goodsPrice", goodList.get(i).getDirectPrice());
				map.put("goodsName", goodList.get(i).getGoodName());
				if (goodList.get(i).getGoodDesc() != null && !goodList.get(i).getGoodDesc().equals("")) {
					map.put("goodsDesc", goodList.get(i).getGoodDesc());
				}
				map.put("visitTimes", goodList.get(i).getViews() == null ? 0 + "" : goodList.get(i).getViews()
						.toString());
				map.put("goodsMsgTimes", goodList.get(i).getMsgTimes() == null ? 0 + "" : goodList.get(i).getMsgTimes()
						.toString());
				map.put("createTime", DateUtil.dateToStr(goodList.get(i).getCreated(), "yyyy-MM-dd HH:mm:ss"));
				map.put("goodsID", goodList.get(i).getId());
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("userID", goodList.get(i).getUser().getUserID());
				userMap.put("mobile", goodList.get(i).getUser().getMobile());

				if (!StringUtils.isEmpty(goodList.get(i).getUser().getNickName())) {
					userMap.put("nickName", goodList.get(i).getUser().getNickName());
				} else {
					userMap.put("nickName", CommonUtils.mobileForNickName(goodList.get(i).getUser().getMobile()));
				}
//				userMap.put("icon", Commons.HEAD_IOC);
				userMap.put("icon", Commons.PIC_DOMAIN + goodList.get(i).getUser().getSmallIcon());
				map.put("user", userMap);
				goodsList.add(map);
			}
			if (goodsList != null) {
				responseBody.setGoodsList(goodsList);
			}
			responseBody.setPageNO(pageNo);
			responseBody.setKeyWord(body.getKeyWord());
			// responseBody.setPageInfo(new PageInfo(1, 1, 10, 2));
			if (countNumber > nowNumber) {
				responseBody.setIsEnd(false);
			} else {
				responseBody.setIsEnd(true);
			}

			return this.packageMsgMap(responseBody, respHead);

		} catch (Exception e) {
			log.error("search 2002接口，发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response2002 res, ResponseHeader respHead) {
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
