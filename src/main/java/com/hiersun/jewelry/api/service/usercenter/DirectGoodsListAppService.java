package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.direct.domain.QueryGoodsByParamVo;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4023;
import com.hiersun.jewelry.api.entity.response.Response4023;
import com.hiersun.jewelry.api.entity.vo.ResponseJrdsGood;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("directGoodsListAppService")
public class DirectGoodsListAppService implements BaseService{

	@Resource
	private DirectGoodsService directGoodsService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		Request4023 body = JSON.parseObject(bodyStr, Request4023.class);
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		Request4023 body = JSON.parseObject(bodyStr, Request4023.class);
		Response4023 res = new Response4023();
		
		Integer goodsTypeCode = body.getGoodsTypeCode();
		Integer count = this.getListCount(goodsTypeCode,userId);
		// 记录为0 不做进一步查询，列表为空
		if (count == null || count == 0) {
			res.setIsEnd(true);
			res.setPageNO(body.getPageNO());
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
		}
		int pageNO = body.getPageNO();
		int pageSize = 20;
		int pageIndex = pageNO * 20 + 1;
		//分页查询
		List<QueryGoodsByParamVo> goodsList = this.getList(goodsTypeCode, userId, pageIndex, pageSize);
		//打包返回信息
		List<ResponseJrdsGood> responseJrdsGoodList = this.packageResponseJrdsGood(goodsList);
		//消息头
		ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
		//消息体
		int nowNumber = (pageNO + 1) * 20;//当前页先是的最后一条记录的坐标
		res.setIsEnd(nowNumber >= count ? true : false);//是否还有吓一条记录
		res.setOrderList(responseJrdsGoodList);//返回的列表
		res.setPageNO(pageNO);
		//消息
		return this.packageMsgMap(res, respHead);
	}
	
	
	//获得集合的数量
	private Integer getListCount(int goodsTypeCode,Long userId) throws Exception{
		Integer conunt = 0;
		//待审核
		if(goodsTypeCode==0){
			conunt = directGoodsService.getGoodsCountByAuditStatus(userId);
		}else if(goodsTypeCode==1){//售卖中
			conunt = directGoodsService.getGoodsCountBySale(userId);
		}else{//其他
			// 将客户端传入的状态翻译成数据库状态
			Integer[] statusArray = StatusMap.DIRECT_SALE_STAUTECODE_MAP.get(goodsTypeCode);
			QueryGoodsByParamVo queryGoodsByParamVo = new QueryGoodsByParamVo();
			queryGoodsByParamVo.setSellerMemberId(userId);
			queryGoodsByParamVo.setStatusList(statusArray);
			conunt = directGoodsService.getGoodsCountByOrderStatus(queryGoodsByParamVo);
		}
		return conunt;
	}
	//分页查询列表
	private List<QueryGoodsByParamVo> getList(int goodsTypeCode,Long userId,int pageIndex,int pageSize) throws Exception{
		List<QueryGoodsByParamVo> goodsList = new ArrayList<QueryGoodsByParamVo>();
		if(goodsTypeCode==0){
			goodsList = directGoodsService.getGoodsListByAuditStatus(userId,pageIndex,pageSize);
		}else if(goodsTypeCode==1){//售卖中
			goodsList = directGoodsService.getGoodsListBySale(userId,pageIndex,pageSize);
		}else{//其他
			// 将客户端传入的状态翻译成数据库状态
			Integer[] statusArray = StatusMap.DIRECT_SALE_STAUTECODE_MAP.get(goodsTypeCode);
			QueryGoodsByParamVo queryGoodsByParamVo = new QueryGoodsByParamVo();
			queryGoodsByParamVo.setSellerMemberId(userId);
			queryGoodsByParamVo.setStatusList(statusArray);
			goodsList = directGoodsService.getGoodsListByOrderStatus(queryGoodsByParamVo,pageIndex,pageSize);
		}
		return goodsList;
	}
	
	//打包
	private List<ResponseJrdsGood> packageResponseJrdsGood(List<QueryGoodsByParamVo> goodsList) throws Exception{
		
		List<ResponseJrdsGood> responseJrdsGoodList = new ArrayList<ResponseJrdsGood>();
		ResponseJrdsGood jesponseJrdsGood = null;
		// 封装订单列表集合
		for (QueryGoodsByParamVo vo : goodsList) {
			jesponseJrdsGood = new ResponseJrdsGood();
			jesponseJrdsGood.setCreateTime(DateUtil.dateToStr(vo.getCreated(), "yyyy-MM-dd HH:mm:ss"));
			jesponseJrdsGood.setGoodsName(vo.getGoodName());
			jesponseJrdsGood.setGoodsPic(Commons.PIC_DOMAIN + vo.getHostGragp());
			jesponseJrdsGood.setGoodsID(vo.getId());
			jesponseJrdsGood.setGoodsNO(vo.getGoodNo());
			jesponseJrdsGood.setGoodsPrice(vo.getDirectPrice().doubleValue());
			jesponseJrdsGood.setGoodsBuyPrice(vo.getBuyingPrice().doubleValue());
			//成交价
			jesponseJrdsGood.setSettlement(vo.getDirectPrice().doubleValue());
			
			//orderStatus 为空时 证明没有产生订单，状态需要看goodStatus goodStatus为空说明还没有审核
			Integer orderStatusCode = vo.getOrderStatus() !=null?vo.getOrderStatus().intValue():
				(vo.getGoodStatus()==null)?-2:-1;
			jesponseJrdsGood.setOrderStatusCode(orderStatusCode);
			jesponseJrdsGood.setOrderStatusDes(StatusMap.DIRECT_GOODS_STAUTEDES_APP_MAP.get(orderStatusCode));
		
			responseJrdsGoodList.add(jesponseJrdsGood);
		}
		return responseJrdsGoodList;
	}
	
	private Map<String,Object> packageMsgMap(Response4023 res,ResponseHeader respHead){
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}

}
