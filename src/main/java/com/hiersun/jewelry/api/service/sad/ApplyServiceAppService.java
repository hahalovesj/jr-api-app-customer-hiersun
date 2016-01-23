package com.hiersun.jewelry.api.service.sad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.response.Response3001;
import com.hiersun.jewelry.api.entity.vo.Material;
import com.hiersun.jewelry.api.entity.vo.MaterialMap;
import com.hiersun.jewelry.api.entity.vo.ServiceMoneyMap;
import com.hiersun.jewelry.api.entity.vo.Wight;
import com.hiersun.jewelry.api.entity.vo.WightMap;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("applyServiceAppService")
public class ApplyServiceAppService implements BaseService{

	@Resource
	private UserService userService;
	
	@Resource
	private RedisBaseService redisBaseServiceImpl;
	
	@Resource
	private DirectGoodsService directGoodsService;
	
	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		return 0;
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		try {

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			// 返回的body
			Response3001 responseBody = new Response3001();

			List<Material> firstTypeList = new ArrayList<Material>();

			firstTypeList.add(new Material(34L, "贵金属制品类"));
			firstTypeList.add(new Material(35L, "钻石类"));
			firstTypeList.add(new Material(103L, "宝石类"));

			List<MaterialMap> secondTypeList = new ArrayList<MaterialMap>();
			// 黄金LIST
			List<Material> hjList = new ArrayList<Material>();
			hjList.add(new Material(201L, "黄金"));
			hjList.add(new Material(202L, "铂金"));
			hjList.add(new Material(203L, "18K金"));
			// 钻石LIST
			List<Material> zsList = new ArrayList<Material>();
			zsList.add(new Material(211L, "裸钻"));
			zsList.add(new Material(212L, "镶嵌钻石"));
			zsList.add(new Material(213L, "彩钻"));
			// 宝石LIST
			List<Material> bsList = new ArrayList<Material>();
			bsList.add(new Material(221L, "祖母绿"));
			bsList.add(new Material(222L, "红宝石"));
			bsList.add(new Material(223L, "蓝宝石"));

			secondTypeList.add(new MaterialMap(34L, hjList));
			secondTypeList.add(new MaterialMap(35L, zsList));
			secondTypeList.add(new MaterialMap(103L, bsList));

			List<WightMap> wightMapList = new ArrayList<WightMap>();
			// 贵金属
			wightMapList.add(new WightMap(201l, Arrays.asList(new Wight[] {new Wight(301L, "1g<重量≤10g"),
					new Wight(311L, "10<重量≤20g"),new Wight(321L, "20<重量≤50g"),new Wight(331L, "50<重量≤100g"), 
					new Wight(341L, "100<重量≤500g")})));
			wightMapList.add(new WightMap(202l, Arrays.asList(new Wight[] { new Wight(301L, "1g<重量≤10g"),
					new Wight(311L, "10<重量≤20g"),new Wight(321L, "20<重量≤50g"),new Wight(331L, "50<重量≤100g"), 
					new Wight(341L, "100<重量≤500g")})));
			wightMapList.add(new WightMap(203l, Arrays.asList(new Wight[] { new Wight(301L, "1g<重量≤10g"),
					new Wight(311L, "10<重量≤20g"),new Wight(321L, "20<重量≤50g"),new Wight(331L, "50<重量≤100g"), 
					new Wight(341L, "100<重量≤500g")})));
			//钻石
			wightMapList.add(new WightMap(211l, Arrays.asList(new Wight[] { new Wight(302l, "0.10ct≤重量≤0.50ct"),
					new Wight(303l, "0.51ct≤重量≤1.00ct") })));

			wightMapList.add(new WightMap(212l, Arrays.asList(new Wight[] { new Wight(304L, "0.08ct≤重量≤0.99ct"),
					new Wight(305L, "1.00ct≤重量≤2.99ct") })));

			wightMapList.add(new WightMap(213l, Arrays.asList(new Wight[] { new Wight(306L, "0.10ct≤重量≤0.99ct"),
					new Wight(307L, "1.00ct≤重量≤1.99ct") })));
			//宝石
			wightMapList.add(new WightMap(221l, Arrays.asList(new Wight[] { new Wight(308L, "重量≤3ct") ,new Wight(309L, "10<重量≤3ct")})));

			wightMapList.add(new WightMap(222l, Arrays.asList(new Wight[] { new Wight(308L, "重量≤3ct"),new Wight(309L, "10<重量≤3ct") })));

			wightMapList.add(new WightMap(223l, Arrays.asList(new Wight[] { new Wight(308L, "重量≤3ct"),new Wight(309L, "10<重量≤3ct") })));

			List<ServiceMoneyMap> moneyList = new ArrayList<ServiceMoneyMap>();
			if(userId!=null && userId<100L){
				moneyList.add(new ServiceMoneyMap(301L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(311L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(321L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(331L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(341L, 0.01, 0.01, 0.01));
				
				moneyList.add(new ServiceMoneyMap(302L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(303L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(304L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(305L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(306L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(307L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(308L, 0.01, 0.01, 0.01));
				moneyList.add(new ServiceMoneyMap(309L, 0.01, 0.01, 0.01));
			}else{
				moneyList.add(new ServiceMoneyMap(301L, 50.00, 50.00, 100.00));
				moneyList.add(new ServiceMoneyMap(311L, 50.00, 100.00, 150.00));
				moneyList.add(new ServiceMoneyMap(321L, 50.00, 200.00, 250.00));
				moneyList.add(new ServiceMoneyMap(331L, 50.00, 300.00, 350.00));
				moneyList.add(new ServiceMoneyMap(341L, 50.00, 500.00, 550.00));
				
				moneyList.add(new ServiceMoneyMap(302L, 200.00, 80.00, 280.00));
				moneyList.add(new ServiceMoneyMap(303L, 300.00, 120.00, 420.00));
				moneyList.add(new ServiceMoneyMap(304L, 50.00, 80.00, 130.00));
				moneyList.add(new ServiceMoneyMap(305L, 150.00, 120.00, 270.00));
				moneyList.add(new ServiceMoneyMap(306L, 150.00, 80.00, 230.00));
				moneyList.add(new ServiceMoneyMap(307L, 350.00, 120.00, 470.00));
				moneyList.add(new ServiceMoneyMap(308L, 300.00, 1000.00, 1300.00));
				moneyList.add(new ServiceMoneyMap(309L, 800.00, 2000.00, 2800.00));
			}
			

			responseBody.setFirstTypeList(firstTypeList);
			responseBody.setSecondTypeList(secondTypeList);
			responseBody.setWightMapList(wightMapList);
			responseBody.setMoneyList(moneyList);

			responseMsg.put("body", responseBody);
			responseMsg.put("head", respHead);
			return this.packageMsgMap(responseBody, respHead);

		} catch (Exception e) {
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response3001 res,ResponseHeader respHead){
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
