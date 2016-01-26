package com.hiersun.jewelry.api.service.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.direct.domain.QueryGoodsByParamVo;
import com.hiersun.jewelry.api.direct.pojo.JrdsActivity;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.VersionInfo;
import com.hiersun.jewelry.api.entity.request.Request2001;
import com.hiersun.jewelry.api.entity.response.Response2001;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.service.MemberCentreMessageService;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.ValiHeadUtil;

@Service("indexAppService")
public class IndexAppService implements BaseService {

	private static final Logger log = Logger.getLogger(IndexAppService.class);

	@Resource
	private UserService userService;

	@Resource
	private RedisBaseService redisBaseServiceImpl;

	@Resource
	private DirectGoodsService directGoodsService;

	@Resource
	private MemberCentreMessageService memberCentreMessageService;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2001 body = JSON.parseObject(bodyStr, Request2001.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("index	2001	接口 请求消息体：" + reqHead.toString());
		log.info("index	2001	接口 请求消息体：" + bodyStr);

		try {
			Request2001 body = JSON.parseObject(bodyStr, Request2001.class);
			Integer pageNo = body.getPageNo();
			String type = body.getType();
			// 是否需要版本信息和站内信息总数
			boolean isNeed = body.getIsNeed();
			// 逻辑处理
			QueryGoodsByParamVo vo = new QueryGoodsByParamVo();
			vo.setStart(pageNo * 20);
			vo.setEnd(20);
			// 默认按照访问量排序
			vo.setOrderBy("visit");
			vo.setOrderType("desc");
			List<QueryGoodsByParamVo> goodList = directGoodsService.getGoodsListByParam(vo);
			List<JrdsActivity> actList = null;
			if (type.equals("all")) {
				// 活动请求
				actList = directGoodsService.getActivity();
			}
			// 总数
			int countNumber = directGoodsService.getCountGoods(vo);
			int nowNumber = (pageNo + 1) * 20;

			// 返回的header
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			// 返回的body
			Response2001 responseBody = new Response2001();
			List<Object> goodsList = new ArrayList<Object>();
			for (int i = 0; i < goodList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (goodList.get(i).getHostGragp() != null) {
					map.put("goodsPicUrl", Commons.PIC_DOMAIN + goodList.get(i).getHostGragp());
				}
				if (goodList.get(i).getDirectPrice() != null) {
					map.put("goodsPrice", goodList.get(i).getDirectPrice());
				}
				if (goodList.get(i).getGoodName() != null) {
					map.put("goodsName", goodList.get(i).getGoodName());
				}
				if (goodList.get(i).getGoodDesc() != null && !goodList.get(i).getGoodDesc().equals("")) {
					map.put("goodsDesc", goodList.get(i).getGoodDesc());
				}
				if (goodList.get(i).getId() != null) {
					map.put("goodsID", goodList.get(i).getId());
				}
				map.put("visitTimes", goodList.get(i).getViews() == null ? 0 + "" : goodList.get(i).getViews()
						.toString());
				map.put("goodsMsgTimes", goodList.get(i).getMsgTimes() == null ? 0 + "" : goodList.get(i).getMsgTimes()
						.toString());

				Map<String, Object> userMap = new HashMap<String, Object>();

				if (goodList.get(i).getUser().getUserID() != null) {
					userMap.put("userID", goodList.get(i).getUser().getUserID());
				}
				if (goodList.get(i).getUser().getMobile() != null) {
					userMap.put("mobile", goodList.get(i).getUser().getMobile());
				}
				if (goodList.get(i).getUser().getNickName() != null) {
					userMap.put("nickName", goodList.get(i).getUser().getNickName());
				} else {
					userMap.put("nickName", CommonUtils.mobileForNickName(goodList.get(i).getUser().getMobile()));
				}
				userMap.put("icon", Commons.PIC_DOMAIN + goodList.get(i).getUser().getSmallIcon());

				map.put("user", userMap);
				goodsList.add(map);
			}
			List<Object> bannerList = new ArrayList<Object>();
			for (int i = 0; i < actList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (actList.get(i).getHostGraphLink() != null) {
					map.put("bannerLink", actList.get(i).getHostGraphLink());
				}
				if (actList.get(i).getDescription() != null) {
					map.put("bannerDes", actList.get(i).getDescription());
				}
				if (actList.get(i).getHostGraph() != null) {
					map.put("BannerUrl", Commons.PIC_DOMAIN + actList.get(i).getHostGraph());
				}
				bannerList.add(map);
			}
			if (goodsList != null) {
				responseBody.setGoodsList(goodsList);
			}
			if (bannerList != null) {
				responseBody.setBannerList(bannerList);
			}

			responseBody.setPageNO(body.getPageNo());
			// 是否有下一页
			if (countNumber > nowNumber) {
				responseBody.setIsEnd(false);
			} else {
				responseBody.setIsEnd(true);
			}
			if (isNeed) {
				// 验证版本号
				String version = reqHead.getVersion();
				String terminal = reqHead.getTerminal();
				VersionInfo info = ValiHeadUtil.checkVersion(version, terminal);
				responseBody.setVersionInfo(info);
				// 未读消息总数
				if (userId != null) {
					Integer count = memberCentreMessageService.getUnreadMessageCount(userId);
					responseBody.setUnreadMessageCount(count);
				}
			}

			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("index 2001接口，发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response2001 res, ResponseHeader respHead) {
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
